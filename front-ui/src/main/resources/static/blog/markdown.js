var Markdown;
if (typeof exports === "object" && typeof require === "function")
    Markdown = exports;
else
    Markdown = {};
(function () {

    function identity(x) {
        return x;
    }

    function returnFalse(x) {
        return false;
    }

    function HookCollection() {
    }

    HookCollection.prototype = {

        chain: function (hookname, func) {
            var original = this[hookname];
            if (!original)
                throw new Error("unknown hook " + hookname);

            if (original === identity)
                this[hookname] = func;
            else
                this[hookname] = function (text) {
                    var args = Array.prototype.slice.call(arguments, 0);
                    args[0] = original.apply(null, args);
                    return func.apply(null, args);
                };
        },
        set: function (hookname, func) {
            if (!this[hookname])
                throw new Error("unknown hook " + hookname);
            this[hookname] = func;
        },
        addNoop: function (hookname) {
            this[hookname] = identity;
        },
        addFalse: function (hookname) {
            this[hookname] = returnFalse;
        }
    };

    Markdown.HookCollection = HookCollection;

    function SaveHash() {
    }

    SaveHash.prototype = {
        set: function (key, value) {
            this["s_" + key] = value;
        },
        get: function (key) {
            return this["s_" + key];
        }
    };

    Markdown.Converter = function () {
        var pluginHooks = this.hooks = new HookCollection();
        pluginHooks.addNoop("plainLinkText");
        pluginHooks.addNoop("preConversion");
        pluginHooks.addNoop("postNormalization");
        pluginHooks.addNoop("preBlockGamut");
        pluginHooks.addNoop("postBlockGamut");
        pluginHooks.addNoop("preSpanGamut");
        pluginHooks.addNoop("postSpanGamut");
        pluginHooks.addNoop("postConversion");
        var g_urls;
        var g_titles;
        var g_html_blocks;
        var g_list_level;
        this.makeHtml = function (text) {
            if (g_urls)
                throw new Error("Recursive call to converter.makeHtml");
            g_urls = new SaveHash();
            g_titles = new SaveHash();
            g_html_blocks = [];
            g_list_level = 0;
            text = pluginHooks.preConversion(text);
            text = text.replace(/~/g, "~T");
            text = text.replace(/\$/g, "~D");
            text = text.replace(/\r\n/g, "\n");
            text = text.replace(/\r/g, "\n");
            text = "\n\n" + text + "\n\n";
            text = _Detab(text);
            text = text.replace(/^[ \t]+$/mg, "");
            text = pluginHooks.postNormalization(text);
            text = _HashHTMLBlocks(text);
            text = _StripLinkDefinitions(text);
            text = _RunBlockGamut(text);
            text = _UnescapeSpecialChars(text);
            text = text.replace(/~D/g, "$$");
            text = text.replace(/~T/g, "~");
            text = pluginHooks.postConversion(text);
            g_html_blocks = g_titles = g_urls = null;
            return text;
        };

        function _StripLinkDefinitions(text) {
            text = text.replace(/^[ ]{0,3}\[(.+)\]:[ \t]*\n?[ \t]*<?(\S+?)>?(?=\s|$)[ \t]*\n?[ \t]*((\n*)["(](.+?)[")][ \t]*)?(?:\n+)/gm,
                function (wholeMatch, m1, m2, m3, m4, m5) {
                    m1 = m1.toLowerCase();
                    g_urls.set(m1, _EncodeAmpsAndAngles(m2));
                    if (m4) {
                        return m3;
                    } else if (m5) {
                        g_titles.set(m1, m5.replace(/"/g, "&quot;"));
                    }
                    return "";
                }
            );

            return text;
        }

        function _HashHTMLBlocks(text) {
            var block_tags_a = "p|div|h[1-6]|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math|ins|del"
            var block_tags_b = "p|div|h[1-6]|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math"
            text = text.replace(/^(<(p|div|h[1-6]|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math|ins|del)\b[^\r]*?\n<\/\2>[ \t]*(?=\n+))/gm, hashElement);
            text = text.replace(/^(<(p|div|h[1-6]|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math)\b[^\r]*?.*<\/\2>[ \t]*(?=\n+)\n)/gm, hashElement);
            text = text.replace(/\n[ ]{0,3}((<(hr)\b([^<>])*?\/?>)[ \t]*(?=\n{2,}))/g, hashElement);
            text = text.replace(/\n\n[ ]{0,3}(<!(--(?:|(?:[^>-]|-[^>])(?:[^-]|-[^-])*)--)>[ \t]*(?=\n{2,}))/g, hashElement);
            text = text.replace(/(?:\n\n)([ ]{0,3}(?:<([?%])[^\r]*?\2>)[ \t]*(?=\n{2,}))/g, hashElement);

            return text;
        }

        function hashElement(wholeMatch, m1) {
            var blockText = m1;
            blockText = blockText.replace(/^\n+/, "");
            blockText = blockText.replace(/\n+$/g, "");
            blockText = "\n\n~K" + (g_html_blocks.push(blockText) - 1) + "K\n\n";
            return blockText;
        }

        var blockGamutHookCallback = function (t) {
            return _RunBlockGamut(t);
        }

        function _RunBlockGamut(text, doNotUnhash) {
            text = pluginHooks.preBlockGamut(text, blockGamutHookCallback);
            text = _DoHeaders(text);
            var replacement = "<hr />\n";
            text = text.replace(/^[ ]{0,2}([ ]?\*[ ]?){3,}[ \t]*$/gm, replacement);
            text = text.replace(/^[ ]{0,2}([ ]?-[ ]?){3,}[ \t]*$/gm, replacement);
            text = text.replace(/^[ ]{0,2}([ ]?_[ ]?){3,}[ \t]*$/gm, replacement);
            text = _DoLists(text);
            text = _DoCodeBlocks(text);
            text = _DoBlockQuotes(text);
            text = pluginHooks.postBlockGamut(text, blockGamutHookCallback);
            text = _HashHTMLBlocks(text);
            text = _FormParagraphs(text, doNotUnhash);
            return text;
        }

        function _RunSpanGamut(text) {
            text = pluginHooks.preSpanGamut(text);
            text = _DoCodeSpans(text);
            text = _EscapeSpecialCharsWithinTagAttributes(text);
            text = _EncodeBackslashEscapes(text);
            text = _DoImages(text);
            text = _DoAnchors(text);
            text = _DoAutoLinks(text);
            text = text.replace(/~P/g, "://");
            text = _EncodeAmpsAndAngles(text);
            text = _DoItalicsAndBold(text);
            text = text.replace(/  +\n/g, " <br>\n");
            text = pluginHooks.postSpanGamut(text);
            return text;
        }

        function _EscapeSpecialCharsWithinTagAttributes(text) {
            var regex = /(<[a-z\/!$]("[^"]*"|'[^']*'|[^'">])*>|<!(--(?:|(?:[^>-]|-[^>])(?:[^-]|-[^-])*)--)>)/gi;
            text = text.replace(regex, function (wholeMatch) {
                var tag = wholeMatch.replace(/(.)<\/?code>(?=.)/g, "$1`");
                tag = escapeCharacters(tag, wholeMatch.charAt(1) == "!" ? "\\`*_/" : "\\`*_");
                return tag;
            });

            return text;
        }

        function _DoAnchors(text) {
            text = text.replace(/(\[((?:\[[^\]]*\]|[^\[\]])*)\][ ]?(?:\n[ ]*)?\[(.*?)\])()()()()/g, writeAnchorTag);
            text = text.replace(/(\[((?:\[[^\]]*\]|[^\[\]])*)\]\([ \t]*()<?((?:\([^)]*\)|[^()\s])*?)>?[ \t]*((['"])(.*?)\6[ \t]*)?\))/g, writeAnchorTag);
            text = text.replace(/(\[([^\[\]]+)\])()()()()()/g, writeAnchorTag);
            return text;
        }

        function writeAnchorTag(wholeMatch, m1, m2, m3, m4, m5, m6, m7) {
            if (m7 == undefined) m7 = "";
            var whole_match = m1;
            var link_text = m2.replace(/:\/\//g, "~P");
            var link_id = m3.toLowerCase();
            var url = m4;
            var title = m7;

            if (url == "") {
                if (link_id == "") {
                    link_id = link_text.toLowerCase().replace(/ ?\n/g, " ");
                }
                url = "#" + link_id;
                if (g_urls.get(link_id) != undefined) {
                    url = g_urls.get(link_id);
                    if (g_titles.get(link_id) != undefined) {
                        title = g_titles.get(link_id);
                    }
                }
                else {
                    if (whole_match.search(/\(\s*\)$/m) > -1) {
                        url = "";
                    } else {
                        return whole_match;
                    }
                }
            }
            url = encodeProblemUrlChars(url);
            url = escapeCharacters(url, "*_");
            var result = "<a href=\"" + url + "\"";

            if (title != "") {
                title = attributeEncode(title);
                title = escapeCharacters(title, "*_");
                result += " title=\"" + title + "\"";
            }
            result += ">" + link_text + "</a>";
            return result;
        }

        function _DoImages(text) {
            text = text.replace(/(!\[(.*?)\][ ]?(?:\n[ ]*)?\[(.*?)\])()()()()/g, writeImageTag);
            text = text.replace(/(!\[(.*?)\]\s?\([ \t]*()<?(\S+?)>?[ \t]*((['"])(.*?)\6[ \t]*)?\))/g, writeImageTag);
            return text;
        }

        function attributeEncode(text) {
            return text.replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/"/g, "&quot;");
        }

        function writeImageTag(wholeMatch, m1, m2, m3, m4, m5, m6, m7) {
            var whole_match = m1;
            var alt_text = m2;
            var link_id = m3.toLowerCase();
            var url = m4;
            var title = m7;
            if (!title) title = "";

            if (url == "") {
                if (link_id == "") {
                    link_id = alt_text.toLowerCase().replace(/ ?\n/g, " ");
                }
                url = "#" + link_id;

                if (g_urls.get(link_id) != undefined) {
                    url = g_urls.get(link_id);
                    if (g_titles.get(link_id) != undefined) {
                        title = g_titles.get(link_id);
                    }
                }
                else {
                    return whole_match;
                }
            }

            alt_text = escapeCharacters(attributeEncode(alt_text), "*_[]()");
            url = escapeCharacters(url, "*_");
            var result = "<img src=\"" + url + "\" alt=\"" + alt_text + "\"";

            title = attributeEncode(title);
            title = escapeCharacters(title, "*_");
            result += " title=\"" + title + "\"";
            result += " />";
            return result;
        }

        function _DoHeaders(text) {
            text = text.replace(/^(.+)[ \t]*\n=+[ \t]*\n+/gm,
                function (wholeMatch, m1) {
                    return "<h1>" + _RunSpanGamut(m1) + "</h1>\n\n";
                }
            );
            text = text.replace(/^(.+)[ \t]*\n-+[ \t]*\n+/gm,
                function (matchFound, m1) {
                    return "<h2>" + _RunSpanGamut(m1) + "</h2>\n\n";
                }
            );
            text = text.replace(/^(\#{1,6})[ \t]*(.+?)[ \t]*\#*\n+/gm,
                function (wholeMatch, m1, m2) {
                    var h_level = m1.length;
                    return "<h" + h_level + ">" + _RunSpanGamut(m2) + "</h" + h_level + ">\n\n";
                }
            );

            return text;
        }

        function _DoLists(text, isInsideParagraphlessListItem) {
            text += "~0";
            var whole_list = /^(([ ]{0,3}([*+-]|\d+[.])[ \t]+)[^\r]+?(~0|\n{2,}(?=\S)(?![ \t]*(?:[*+-]|\d+[.])[ \t]+)))/gm;
            if (g_list_level) {
                text = text.replace(whole_list, function (wholeMatch, m1, m2) {
                    var list = m1;
                    var list_type = (m2.search(/[*+-]/g) > -1) ? "ul" : "ol";
                    var result = _ProcessListItems(list, list_type, isInsideParagraphlessListItem);
                    result = result.replace(/\s+$/, "");
                    result = "<" + list_type + ">" + result + "</" + list_type + ">\n";
                    return result;
                });
            } else {
                whole_list = /(\n\n|^\n?)(([ ]{0,3}([*+-]|\d+[.])[ \t]+)[^\r]+?(~0|\n{2,}(?=\S)(?![ \t]*(?:[*+-]|\d+[.])[ \t]+)))/g;
                text = text.replace(whole_list, function (wholeMatch, m1, m2, m3) {
                    var runup = m1;
                    var list = m2;

                    var list_type = (m3.search(/[*+-]/g) > -1) ? "ul" : "ol";
                    var result = _ProcessListItems(list, list_type);
                    result = runup + "<" + list_type + ">\n" + result + "</" + list_type + ">\n";
                    return result;
                });
            }
            text = text.replace(/~0/, "");
            return text;
        }

        var _listItemMarkers = {ol: "\\d+[.]", ul: "[*+-]"};

        function _ProcessListItems(list_str, list_type, isInsideParagraphlessListItem) {
            g_list_level++;
            list_str = list_str.replace(/\n{2,}$/, "\n");
            list_str += "~0";
            var marker = _listItemMarkers[list_type];
            var re = new RegExp("(^[ \\t]*)(" + marker + ")[ \\t]+([^\\r]+?(\\n+))(?=(~0|\\1(" + marker + ")[ \\t]+))", "gm");
            var last_item_had_a_double_newline = false;
            list_str = list_str.replace(re,
                function (wholeMatch, m1, m2, m3) {
                    var item = m3;
                    var leading_space = m1;
                    var ends_with_double_newline = /\n\n$/.test(item);
                    var contains_double_newline = ends_with_double_newline || item.search(/\n{2,}/) > -1;
                    if (contains_double_newline || last_item_had_a_double_newline) {
                        item = _RunBlockGamut(_Outdent(item), /* doNotUnhash = */true);
                    }
                    else {
                        item = _DoLists(_Outdent(item), /* isInsideParagraphlessListItem= */ true);
                        item = item.replace(/\n$/, "");
                        if (!isInsideParagraphlessListItem)
                            item = _RunSpanGamut(item);
                    }
                    last_item_had_a_double_newline = ends_with_double_newline;
                    return "<li>" + item + "</li>\n";
                }
            );

            list_str = list_str.replace(/~0/g, "");

            g_list_level--;
            return list_str;
        }

        /**
         * 插入的代码处理
         * @param text
         * @returns {string|*}
         * @private
         */
        function _DoCodeBlocks(text) {
            text += "~0";
            text = text.replace(/(?:\n\n|^\n?)((?:(?:[ ]{4}|\t).*\n+)+)(\n*[ ]{0,3}[^ \t\n]|(?=~0))/g,
                function (wholeMatch, m1, m2) {
                    var codeblock = m1;
                    var nextChar = m2;
                    codeblock = _EncodeCode(_Outdent(codeblock));
                    codeblock = _Detab(codeblock);
                    codeblock = codeblock.replace(/^\n+/g, "");
                    codeblock = codeblock.replace(/\n+$/g, "");
                    codeblock = "<pre><code>" + codeblock + "\n</code></pre>";
                    return "\n\n" + codeblock + "\n\n" + nextChar;
                }
            );
            text = text.replace(/~0/, "");
            return text;
        }

        function hashBlock(text) {
            text = text.replace(/(^\n+|\n+$)/g, "");
            return "\n\n~K" + (g_html_blocks.push(text) - 1) + "K\n\n";
        }

        function _DoCodeSpans(text) {
            text = text.replace(/(^|[^\\])(`+)([^\r]*?[^`])\2(?!`)/gm,
                function (wholeMatch, m1, m2, m3, m4) {
                    var c = m3;
                    c = c.replace(/^([ \t]*)/g, "");
                    c = c.replace(/[ \t]*$/g, "");
                    c = _EncodeCode(c);
                    c = c.replace(/:\/\//g, "~P");
                    return m1 + "<code>" + c + "</code>";
                }
            );
            return text;
        }

        function _EncodeCode(text) {
            text = text.replace(/&/g, "&amp;");
            text = text.replace(/</g, "&lt;");
            text = text.replace(/>/g, "&gt;");
            text = escapeCharacters(text, "\*_{}[]\\", false);
            return text;
        }

        /**
         *
         * 斜体事件
         * @param text
         * @returns {XML|string|*}
         * @private
         */
        function _DoItalicsAndBold(text) {
            text = text.replace(/([\W_]|^)(\*\*|__)(?=\S)([^\r]*?\S[\*_]*)\2([\W_]|$)/g, "$1<strong>$3</strong>$4");
            text = text.replace(/([\W_]|^)(\*|_)(?=\S)([^\r\*_]*?\S)\2([\W_]|$)/g,
                "$1<em>$3</em>$4");

            return text;
        }

        /**
         * 引用文字
         * @param text
         * @returns {XML|string|void|*}
         * @private
         */
        function _DoBlockQuotes(text) {
            text = text.replace(/((^[ \t]*>[ \t]?.+\n(.+\n)*\n*)+)/gm,
                function (wholeMatch, m1) {
                    var bq = m1;
                    bq = bq.replace(/^[ \t]*>[ \t]?/gm, "~0");
                    bq = bq.replace(/~0/g, "");
                    bq = bq.replace(/^[ \t]+$/gm, "");
                    bq = _RunBlockGamut(bq);
                    bq = bq.replace(/(^|\n)/g, "$1  ");
                    bq = bq.replace(
                        /(\s*<pre>[^\r]+?<\/pre>)/gm,
                        function (wholeMatch, m1) {
                            var pre = m1;
                            pre = pre.replace(/^  /mg, "~0");
                            pre = pre.replace(/~0/g, "");
                            return pre;
                        });
                    return hashBlock("<blockquote>\n" + bq + "\n</blockquote>");
                }
            );
            return text;
        }

        function _FormParagraphs(text, doNotUnhash) {
            text = text.replace(/^\n+/g, "");
            text = text.replace(/\n+$/g, "");
            var grafs = text.split(/\n{2,}/g);
            var grafsOut = [];
            var markerRe = /~K(\d+)K/;
            var end = grafs.length;
            for (var i = 0; i < end; i++) {
                var str = grafs[i];
                if (markerRe.test(str)) {
                    grafsOut.push(str);
                }
                else if (/\S/.test(str)) {
                    str = _RunSpanGamut(str);
                    str = str.replace(/^([ \t]*)/g, "<p>");
                    str += "</p>"
                    grafsOut.push(str);
                }

            }
            if (!doNotUnhash) {
                end = grafsOut.length;
                for (var i = 0; i < end; i++) {
                    var foundAny = true;
                    while (foundAny) {
                        foundAny = false;
                        grafsOut[i] = grafsOut[i].replace(/~K(\d+)K/g, function (wholeMatch, id) {
                            foundAny = true;
                            return g_html_blocks[id];
                        });
                    }
                }
            }
            return grafsOut.join("\n\n");
        }

        function _EncodeAmpsAndAngles(text) {
            text = text.replace(/&(?!#?[xX]?(?:[0-9a-fA-F]+|\w+);)/g, "&amp;");
            text = text.replace(/<(?![a-z\/?!]|~D)/gi, "&lt;");
            return text;
        }

        function _EncodeBackslashEscapes(text) {
            text = text.replace(/\\(\\)/g, escapeCharacters_callback);
            text = text.replace(/\\([`*_{}\[\]()>#+-.!])/g, escapeCharacters_callback);
            return text;
        }

        var charInsideUrl = "[-A-Z0-9+&@#/%?=~_|[\\]()!:,.;]",
            charEndingUrl = "[-A-Z0-9+&@#/%=~_|[\\])]",
            autoLinkRegex = new RegExp("(=\"|<)?\\b(https?|ftp)(://" + charInsideUrl + "*" + charEndingUrl + ")(?=$|\\W)", "gi"),
            endCharRegex = new RegExp(charEndingUrl, "i");

        function handleTrailingParens(wholeMatch, lookbehind, protocol, link) {
            if (lookbehind)
                return wholeMatch;
            if (link.charAt(link.length - 1) !== ")")
                return "<" + protocol + link + ">";
            var parens = link.match(/[()]/g);
            var level = 0;
            for (var i = 0; i < parens.length; i++) {
                if (parens[i] === "(") {
                    if (level <= 0)
                        level = 1;
                    else
                        level++;
                }
                else {
                    level--;
                }
            }
            var tail = "";
            if (level < 0) {
                var re = new RegExp("\\){1," + (-level) + "}$");
                link = link.replace(re, function (trailingParens) {
                    tail = trailingParens;
                    return "";
                });
            }
            if (tail) {
                var lastChar = link.charAt(link.length - 1);
                if (!endCharRegex.test(lastChar)) {
                    tail = lastChar + tail;
                    link = link.substr(0, link.length - 1);
                }
            }
            return "<" + protocol + link + ">" + tail;
        }

        function _DoAutoLinks(text) {
            text = text.replace(autoLinkRegex, handleTrailingParens);
            var replacer = function (wholematch, m1) {
                return "<a href=\"" + m1 + "\">" + pluginHooks.plainLinkText(m1) + "</a>";
            }
            text = text.replace(/<((https?|ftp):[^'">\s]+)>/gi, replacer);
            return text;
        }

        function _UnescapeSpecialChars(text) {
            text = text.replace(/~E(\d+)E/g,
                function (wholeMatch, m1) {
                    var charCodeToReplace = parseInt(m1);
                    return String.fromCharCode(charCodeToReplace);
                }
            );
            return text;
        }

        function _Outdent(text) {
            text = text.replace(/^(\t|[ ]{1,4})/gm, "~0");
            text = text.replace(/~0/g, "")
            return text;
        }

        function _Detab(text) {
            if (!/\t/.test(text))
                return text;
            var spaces = ["    ", "   ", "  ", " "],
                skew = 0,
                v;
            return text.replace(/[\n\t]/g, function (match, offset) {
                if (match === "\n") {
                    skew = offset + 1;
                    return match;
                }
                v = (offset - skew) % 4;
                skew = offset + 1;
                return spaces[v];
            });
        }

        var _problemUrlChars = /(?:["'*()[\]:]|~D)/g;

        function encodeProblemUrlChars(url) {
            if (!url) return "";
            var len = url.length;

            return url.replace(_problemUrlChars, function (match, offset) {
                if (match == "~D")
                    return "%24";
                if (match == ":") {
                    if (offset == len - 1 || /[0-9\/]/.test(url.charAt(offset + 1)))
                        return ":"
                }
                return "%" + match.charCodeAt(0).toString(16);
            });
        }

        function escapeCharacters(text, charsToEscape, afterBackslash) {
            var regexString = "([" + charsToEscape.replace(/([\[\]\\])/g, "\\$1") + "])";
            if (afterBackslash) {
                regexString = "\\\\" + regexString;
            }
            var regex = new RegExp(regexString, "g");
            text = text.replace(regex, escapeCharacters_callback);
            return text;
        }

        function escapeCharacters_callback(wholeMatch, m1) {
            var charCodeToEscape = m1.charCodeAt(0);
            return "~E" + charCodeToEscape + "E";
        }
    };

})();

//Sanitizer
//Sanitizer
//Sanitizer

(function () {
    var output, Converter;
    if (typeof exports === "object" && typeof require === "function") {
        output = exports;
        Converter = require("./Markdown.Converter").Converter;
    } else {
        output = window.Markdown;
        Converter = output.Converter;
    }

    output.getSanitizingConverter = function () {
        var converter = new Converter();
        converter.hooks.chain("postConversion", sanitizeHtml);
        converter.hooks.chain("postConversion", balanceTags);
        return converter;
    }

    function sanitizeHtml(html) {
        return html.replace(/<[^>]*>?/gi, sanitizeTag);
    }

    var basic_tag_whitelist = /^(<\/?(b|blockquote|code|del|dd|dl|dt|em|h1|h2|h3|i|kbd|li|ol|p|pre|s|sup|sub|strong|strike|ul)>|<(br|hr)\s?\/?>)$/i;
    var a_white = /^(<a\shref="((https?|ftp):\/\/|\/)[-A-Za-z0-9+&@#\/%?=~_|!:,.;\(\)]+"(\stitle="[^"<>]+")?\s?>|<\/a>)$/i;
    var img_white = /^(<img\ssrc="(https?:\/\/|\/)[-A-Za-z0-9+&@#\/%?=~_|!:,.;\(\)]+"(\swidth="\d{1,3}")?(\sheight="\d{1,3}")?(\salt="[^"<>]*")?(\stitle="[^"<>]*")?\s?\/?>)$/i;

    function sanitizeTag(tag) {
        if (tag.match(basic_tag_whitelist) || tag.match(a_white) || tag.match(img_white))
            return tag;
        else
            return "";
    }

    function balanceTags(html) {

        if (html == "")
            return "";

        var re = /<\/?\w+[^>]*(\s|$|>)/g;
        var tags = html.toLowerCase().match(re);
        var tagcount = (tags || []).length;
        if (tagcount == 0) return html;
        var tagname, tag;
        var ignoredtags = "<p><img><br><li><hr>";
        var match;
        var tagpaired = [];
        var tagremove = [];
        var needsRemoval = false;

        for (var ctag = 0; ctag < tagcount; ctag++) {
            tagname = tags[ctag].replace(/<\/?(\w+).*/, "$1");
            if (tagpaired[ctag] || ignoredtags.search("<" + tagname + ">") > -1)
                continue;
            tag = tags[ctag];
            match = -1;

            if (!/^<\//.test(tag)) {
                for (var ntag = ctag + 1; ntag < tagcount; ntag++) {
                    if (!tagpaired[ntag] && tags[ntag] == "</" + tagname + ">") {
                        match = ntag;
                        break;
                    }
                }
            }

            if (match == -1) needsRemoval = tagremove[ctag] = true;
            else tagpaired[match] = true;
        }

        if (!needsRemoval)
            return html;

        var ctag = 0;
        html = html.replace(re, function (match) {
            var res = tagremove[ctag] ? "" : match;
            ctag++;
            return res;
        });
        return html;
    }
})();

//Editor
//Editor
//Editor
(function () {
    var util = {},
        position = {},
        ui = {},
        doc = window.document,
        re = window.RegExp,
        nav = window.navigator,
        SETTINGS = {lineLength: 72},

        uaSniffed = {
            isIE: /msie/.test(nav.userAgent.toLowerCase()),
            isIE_5or6: /msie 6/.test(nav.userAgent.toLowerCase()) || /msie 5/.test(nav.userAgent.toLowerCase()),
            isOpera: /opera/.test(nav.userAgent.toLowerCase())
        };

    var defaultsStrings = {
        //加粗
        bold: "Strong <strong> Ctrl+B",
        boldexample: "strong text",
        //斜体
        italic: "Emphasis <em> Ctrl+I",
        italicexample: "emphasized text",
        //链接
        link: "Hyperlink <a> Ctrl+L",
        linkdescription: "enter link description here",
        linkdialog: "<p><b>Insert Hyperlink</b></p><p>http://example.com/ \"optional title\"</p>",
        //引用
        quote: "Blockquote <blockquote> Ctrl+Q",
        quoteexample: "Blockquote",
        //代码
        code: "Code Sample <pre><code> Ctrl+K",
        codeexample: "enter code here",
        //图片
        image: "Image <img> Ctrl+G",
        imagedescription: "enter image description here",
        imagedialog: "<p><b>Insert Image</b></p><p>http://example.com/images/diagram.jpg \"optional title\"<br><br>Need <a href='http://www.google.com/search?q=free+image+hosting' target='_blank'>free image hosting?</a></p>",
        //列表
        olist: "Numbered List <ol> Ctrl+O",
        ulist: "Bulleted List <ul> Ctrl+U",
        litem: "List item",
        //标题
        heading: "Heading <h1>/<h2> Ctrl+H",
        headingexample: "Heading",
        fullscreen: "FullScreen Ctrl+J",
        exitFullscreen: "Exit FullScreen Ctrl+E",
        fullscreenUnsupport: "Sorry, the browser dont support fullscreen api",
        hr: "Horizontal Rule <hr> Ctrl+R",
        undo: "Undo - Ctrl+Z",
        redo: "Redo - Ctrl+Y",
        redomac: "Redo - Ctrl+Shift+Z",

        ok: "OK",
        cancel: "Cancel",

        help: "Markdown Editing Help"
    };
    var imageDefaultText = "";
    var linkDefaultText = "http://";
    Markdown.Editor = function (markdownConverter, idPostfix, options) {
        options = options || {};
        if (typeof options.handler === "function") {
            options = {helpButton: options};
        }
        options.strings = options.strings || {};
        if (options.helpButton) {
            options.strings.help = options.strings.help || options.helpButton.title;
        }
        var getString = function (identifier) {
            return options.strings[identifier] || defaultsStrings[identifier];
        }
        idPostfix = idPostfix || "";
        var hooks = this.hooks = new Markdown.HookCollection();
        hooks.addNoop("onPreviewRefresh");
        hooks.addNoop("postBlockquoteCreation");
        hooks.addFalse("insertImageDialog");
        hooks.addFalse("insertLinkDialog");
        hooks.addNoop("enterFullScreen");
        hooks.addNoop("enterFakeFullScreen");
        hooks.addNoop("exitFullScreen");
        this.getConverter = function () {
            return markdownConverter;
        };
        var that = this,
            panels;
        this.run = function () {
            if (panels) return;
            panels = new PanelCollection(idPostfix);
            var commandManager = new CommandManager(hooks, getString);
            var previewManager = new PreviewManager(markdownConverter, panels, function () {
                hooks.onPreviewRefresh();
            });
            var undoManager, uiManager;
            if (!/\?noundo/.test(doc.location.href)) {
                undoManager = new UndoManager(function () {
                    previewManager.refresh();
                    if (uiManager)
                        uiManager.setUndoRedoButtonStates();
                }, panels);
                this.textOperation = function (f) {
                    undoManager.setCommandMode();
                    f();
                    that.refreshPreview();
                }
            }
            var fullScreenManager = new FullScreenManager(hooks, getString);
            uiManager = new UIManager(idPostfix, panels, undoManager, previewManager,
                commandManager, fullScreenManager, options.helpButton, getString);
            uiManager.setUndoRedoButtonStates();
            var forceRefresh = that.refreshPreview = function () {
                previewManager.refresh(true);
            };
            forceRefresh();
        };

    };

    function Chunks() {
    }

    Chunks.prototype.findTags = function (startRegex, endRegex) {
        var chunkObj = this;
        var regex;
        if (startRegex) {
            regex = util.extendRegExp(startRegex, "", "$");
            this.before = this.before.replace(regex, function (match) {
                chunkObj.startTag = chunkObj.startTag + match;
                return "";
            });
            regex = util.extendRegExp(startRegex, "^", "");

            this.selection = this.selection.replace(regex, function (match) {
                chunkObj.startTag = chunkObj.startTag + match;
                return "";
            });
        }

        if (endRegex) {
            regex = util.extendRegExp(endRegex, "", "$");
            this.selection = this.selection.replace(regex,
                function (match) {
                    chunkObj.endTag = match + chunkObj.endTag;
                    return "";
                });

            regex = util.extendRegExp(endRegex, "^", "");

            this.after = this.after.replace(regex,
                function (match) {
                    chunkObj.endTag = match + chunkObj.endTag;
                    return "";
                });
        }
    };

    Chunks.prototype.trimWhitespace = function (remove) {
        var beforeReplacer, afterReplacer, that = this;
        if (remove) {
            beforeReplacer = afterReplacer = "";
        } else {
            beforeReplacer = function (s) {
                that.before += s;
                return "";
            }
            afterReplacer = function (s) {
                that.after = s + that.after;
                return "";
            }
        }

        this.selection = this.selection.replace(/^(\s*)/, beforeReplacer).replace(/(\s*)$/, afterReplacer);
    };

    Chunks.prototype.skipLines = function (nLinesBefore, nLinesAfter, findExtraNewlines) {

        if (nLinesBefore === undefined) {
            nLinesBefore = 1;
        }

        if (nLinesAfter === undefined) {
            nLinesAfter = 1;
        }

        nLinesBefore++;
        nLinesAfter++;

        var regexText;
        var replacementText;

        if (navigator.userAgent.match(/Chrome/)) {
            "X".match(/()./);
        }

        this.selection = this.selection.replace(/(^\n*)/, "");
        this.startTag = this.startTag + re.$1;
        this.selection = this.selection.replace(/(\n*$)/, "");
        this.endTag = this.endTag + re.$1;
        this.startTag = this.startTag.replace(/(^\n*)/, "");
        this.before = this.before + re.$1;
        this.endTag = this.endTag.replace(/(\n*$)/, "");
        this.after = this.after + re.$1;
        if (this.before) {
            regexText = replacementText = "";
            while (nLinesBefore--) {
                regexText += "\\n?";
                replacementText += "\n";
            }

            if (findExtraNewlines) {
                regexText = "\\n*";
            }
            this.before = this.before.replace(new re(regexText + "$", ""), replacementText);
        }

        if (this.after) {

            regexText = replacementText = "";

            while (nLinesAfter--) {
                regexText += "\\n?";
                replacementText += "\n";
            }
            if (findExtraNewlines) {
                regexText = "\\n*";
            }

            this.after = this.after.replace(new re(regexText, ""), replacementText);
        }
    };

    function PanelCollection(postfix) {
        this.buttonBar = doc.getElementById("md-button-bar" + postfix);
        this.preview = doc.getElementById("md-preview" + postfix);
        this.input = doc.getElementById("text");
    };

    util.isVisible = function (elem) {
        if (window.getComputedStyle) {
            return window.getComputedStyle(elem, null).getPropertyValue("display") !== "none";
        }
        else if (elem.currentStyle) {
            return elem.currentStyle["display"] !== "none";
        }
    };

    util.addEvent = function (elem, event, listener) {
        if (elem.attachEvent) {
            elem.attachEvent("on" + event, listener);
        }
        else {
            elem.addEventListener(event, listener, false);
        }
    };
    util.removeEvent = function (elem, event, listener) {
        if (elem.detachEvent) {
            elem.detachEvent("on" + event, listener);
        }
        else {
            elem.removeEventListener(event, listener, false);
        }
    };

    util.fixEolChars = function (text) {
        text = text.replace(/\r\n/g, "\n");
        text = text.replace(/\r/g, "\n");
        return text;
    };

    util.extendRegExp = function (regex, pre, post) {
        if (pre === null || pre === undefined) {
            pre = "";
        }
        if (post === null || post === undefined) {
            post = "";
        }

        var pattern = regex.toString();
        var flags;

        pattern = pattern.replace(/\/([gim]*)$/, function (wholeMatch, flagsPart) {
            flags = flagsPart;
            return "";
        });

        pattern = pattern.replace(/(^\/|\/$)/g, "");
        pattern = pre + pattern + post;
        return new re(pattern, flags);
    }


    position.getTop = function (elem, isInner) {
        var result = elem.offsetTop;
        if (!isInner) {
            while (elem = elem.offsetParent) {
                result += elem.offsetTop;
            }
        }
        return result;
    };

    position.getHeight = function (elem) {
        return elem.offsetHeight || elem.scrollHeight;
    };

    position.getWidth = function (elem) {
        return elem.offsetWidth || elem.scrollWidth;
    };

    position.getPageSize = function () {

        var scrollWidth, scrollHeight;
        var innerWidth, innerHeight;
        if (self.innerHeight && self.scrollMaxY) {
            scrollWidth = doc.body.scrollWidth;
            scrollHeight = self.innerHeight + self.scrollMaxY;
        } else if (doc.body.scrollHeight > doc.body.offsetHeight) {
            scrollWidth = doc.body.scrollWidth;
            scrollHeight = doc.body.scrollHeight;
        } else {
            scrollWidth = doc.body.offsetWidth;
            scrollHeight = doc.body.offsetHeight;
        }

        if (self.innerHeight) {
            innerWidth = self.innerWidth;
            innerHeight = self.innerHeight;
        } else if (doc.documentElement && doc.documentElement.clientHeight) {
            innerWidth = doc.documentElement.clientWidth;
            innerHeight = doc.documentElement.clientHeight;
        } else if (doc.body) {
            innerWidth = doc.body.clientWidth;
            innerHeight = doc.body.clientHeight;
        }

        var maxWidth = Math.max(scrollWidth, innerWidth);
        var maxHeight = Math.max(scrollHeight, innerHeight);
        return [maxWidth, maxHeight, innerWidth, innerHeight];
    };

    function UndoManager(callback, panels) {
        var undoObj = this;
        var undoStack = [];
        var stackPtr = 0;
        var mode = "none";
        var lastState;
        var timer;
        var inputStateObj;
        var setMode = function (newMode, noSave) {
            if (mode != newMode) {
                mode = newMode;
                if (!noSave) {
                    saveState();
                }
            }

            if (!uaSniffed.isIE || mode != "moving") {
                timer = setTimeout(refreshState, 1);
            }
            else {
                inputStateObj = null;
            }
        };
        var refreshState = function (isInitialState) {
            inputStateObj = new TextareaState(panels, isInitialState);
            timer = undefined;
        };
        this.setCommandMode = function () {
            mode = "command";
            saveState();
            timer = setTimeout(refreshState, 0);
        };
        this.canUndo = function () {
            return stackPtr > 1;
        };

        this.canRedo = function () {
            if (undoStack[stackPtr + 1]) {
                return true;
            }
            return false;
        };
        this.undo = function () {

            if (undoObj.canUndo()) {
                if (lastState) {
                    lastState.restore();
                    lastState = null;
                }
                else {
                    undoStack[stackPtr] = new TextareaState(panels);
                    undoStack[--stackPtr].restore();

                    if (callback) {
                        callback();
                    }
                }
            }
            mode = "none";
            panels.input.focus();
            refreshState();
        };
        this.redo = function () {
            if (undoObj.canRedo()) {
                undoStack[++stackPtr].restore();
                if (callback) {
                    callback();
                }
            }
            mode = "none";
            panels.input.focus();
            refreshState();
        };
        var saveState = function () {
            var currState = inputStateObj || new TextareaState(panels);

            if (!currState) {
                return false;
            }
            if (mode == "moving") {
                if (!lastState) {
                    lastState = currState;
                }
                return;
            }
            if (lastState) {
                if (undoStack[stackPtr - 1].text != lastState.text) {
                    undoStack[stackPtr++] = lastState;
                }
                lastState = null;
            }
            undoStack[stackPtr++] = currState;
            undoStack[stackPtr + 1] = null;
            if (callback) {
                callback();
            }
        };

        var handleCtrlYZ = function (event) {
            var handled = false;
            if ((event.ctrlKey || event.metaKey) && !event.altKey) {
                var keyCode = event.charCode || event.keyCode;
                var keyCodeChar = String.fromCharCode(keyCode);
                switch (keyCodeChar.toLowerCase()) {
                    case "y":
                        undoObj.redo();
                        handled = true;
                        break;
                    case "z":
                        if (!event.shiftKey) {
                            undoObj.undo();
                        }
                        else {
                            undoObj.redo();
                        }
                        handled = true;
                        break;
                }
            }

            if (handled) {
                if (event.preventDefault) {
                    event.preventDefault();
                }
                if (window.event) {
                    window.event.returnValue = false;
                }
                return;
            }
        };

        var handleModeChange = function (event) {
            if (!event.ctrlKey && !event.metaKey) {
                var keyCode = event.keyCode;
                if ((keyCode >= 33 && keyCode <= 40) || (keyCode >= 63232 && keyCode <= 63235)) {
                    setMode("moving");
                }
                else if (keyCode == 8 || keyCode == 46 || keyCode == 127) {
                    setMode("deleting");
                }
                else if (keyCode == 13) {
                    setMode("newlines");
                }
                else if (keyCode == 27) {
                    setMode("escape");
                }
                else if ((keyCode < 16 || keyCode > 20) && keyCode != 91) {
                    setMode("typing");
                }
                else if (keyCode == 32) {
                    setMode("spaces");
                }
            }
        };

        var setEventHandlers = function () {
            util.addEvent(panels.input, "keypress", function (event) {
                if ((event.ctrlKey || event.metaKey) && !event.altKey && (event.keyCode == 89 || event.keyCode == 90)) {
                    event.preventDefault();
                }
            });

            var handlePaste = function () {
                if (uaSniffed.isIE || (inputStateObj && inputStateObj.text != panels.input.value)) {
                    if (timer == undefined) {
                        mode = "paste";
                        saveState();
                        refreshState();
                    }
                }
            };

            util.addEvent(panels.input, "keydown", handleCtrlYZ);
            util.addEvent(panels.input, "keydown", handleModeChange);
            util.addEvent(panels.input, "mousedown", function () {
                setMode("moving");
            });
            panels.input.onpaste = handlePaste;
            panels.input.ondrop = handlePaste;
        };

        var init = function () {
            setEventHandlers();
            refreshState(true);
            saveState();
        };

        init();
    }

    function TextareaState(panels, isInitialState) {
        var stateObj = this;
        var inputArea = panels.input;
        this.init = function () {
            if (!util.isVisible(inputArea)) {
                return;
            }
            if (!isInitialState && doc.activeElement && doc.activeElement !== inputArea) {
                return;
            }
            this.setInputAreaSelectionStartEnd();
            this.scrollTop = inputArea.scrollTop;
            if (!this.text && inputArea.selectionStart || inputArea.selectionStart === 0) {
                this.text = inputArea.value;
            }
        }
        this.setInputAreaSelection = function () {
            if (!util.isVisible(inputArea)) {
                return;
            }
            if (inputArea.selectionStart !== undefined && !uaSniffed.isOpera) {
                inputArea.focus();
                inputArea.selectionStart = stateObj.start;
                inputArea.selectionEnd = stateObj.end;
                inputArea.scrollTop = stateObj.scrollTop;
            }
            else if (doc.selection) {
                if (doc.activeElement && doc.activeElement !== inputArea) {
                    return;
                }
                inputArea.focus();
                var range = inputArea.createTextRange();
                range.moveStart("character", -inputArea.value.length);
                range.moveEnd("character", -inputArea.value.length);
                range.moveEnd("character", stateObj.end);
                range.moveStart("character", stateObj.start);
                range.select();
            }
        };

        this.setInputAreaSelectionStartEnd = function () {
            if (!panels.ieCachedRange && (inputArea.selectionStart || inputArea.selectionStart === 0)) {
                stateObj.start = inputArea.selectionStart;
                stateObj.end = inputArea.selectionEnd;
            }
            else if (doc.selection) {
                stateObj.text = util.fixEolChars(inputArea.value);
                var range = panels.ieCachedRange || doc.selection.createRange();
                var fixedRange = util.fixEolChars(range.text);
                var marker = "\x07";
                var markedRange = marker + fixedRange + marker;
                range.text = markedRange;
                var inputText = util.fixEolChars(inputArea.value);
                range.moveStart("character", -markedRange.length);
                range.text = fixedRange;
                stateObj.start = inputText.indexOf(marker);
                stateObj.end = inputText.lastIndexOf(marker) - marker.length;
                var len = stateObj.text.length - util.fixEolChars(inputArea.value).length;
                if (len) {
                    range.moveStart("character", -fixedRange.length);
                    while (len--) {
                        fixedRange += "\n";
                        stateObj.end += 1;
                    }
                    range.text = fixedRange;
                }
                if (panels.ieCachedRange)
                    stateObj.scrollTop = panels.ieCachedScrollTop;
                panels.ieCachedRange = null;

                this.setInputAreaSelection();
            }
        };

        this.restore = function () {
            if (stateObj.text != undefined && stateObj.text != inputArea.value) {
                inputArea.value = stateObj.text;
            }
            this.setInputAreaSelection();
            inputArea.scrollTop = stateObj.scrollTop;
        };

        this.getChunks = function () {
            var chunk = new Chunks();
            chunk.before = util.fixEolChars(stateObj.text.substring(0, stateObj.start));
            chunk.startTag = "";
            chunk.selection = util.fixEolChars(stateObj.text.substring(stateObj.start, stateObj.end));
            chunk.endTag = "";
            chunk.after = util.fixEolChars(stateObj.text.substring(stateObj.end));
            chunk.scrollTop = stateObj.scrollTop;

            return chunk;
        };

        this.setChunks = function (chunk) {
            chunk.before = chunk.before + chunk.startTag;
            chunk.after = chunk.endTag + chunk.after;
            this.start = chunk.before.length;
            this.end = chunk.before.length + chunk.selection.length;
            this.text = chunk.before + chunk.selection + chunk.after;
            this.scrollTop = chunk.scrollTop;
        };
        this.init();
    };

    function PreviewManager(converter, panels, previewRefreshCallback) {
        var managerObj = this;
        var timeout;
        var elapsedTime;
        var oldInputText;
        var maxDelay = 3000;
        var startType = "delayed";
        var setupEvents = function (inputElem, listener) {
            util.addEvent(inputElem, "input", listener);
            inputElem.onpaste = listener;
            inputElem.ondrop = listener;
            util.addEvent(inputElem, "keypress", listener);
            util.addEvent(inputElem, "keydown", listener);
        };

        var getDocScrollTop = function () {
            var result = 0;
            if (window.innerHeight) {
                result = window.pageYOffset;
            }
            else if (doc.documentElement && doc.documentElement.scrollTop) {
                result = doc.documentElement.scrollTop;
            }
            else if (doc.body) {
                result = doc.body.scrollTop;
            }
            return result;
        };
        /**
         * 输出预览页面
         */
        var makePreviewHtml = function () {
            hljs.initHighlighting();
            if (!panels.preview) return;
            var text = panels.input.value;
            if (text && text == oldInputText) {
                return;
            } else {
                oldInputText = text;
            }
            var prevTime = new Date().getTime();
            text = converter.makeHtml(text);
            var currTime = new Date().getTime();
            elapsedTime = currTime - prevTime;
            pushPreviewHtml(text);
        };

        var applyTimeout = function () {
            if (timeout) {
                clearTimeout(timeout);
                timeout = undefined;
            }
            if (startType !== "manual") {
                var delay = 0;
                if (startType === "delayed") {
                    delay = elapsedTime;
                }
                if (delay > maxDelay) {
                    delay = maxDelay;
                }
                timeout = setTimeout(makePreviewHtml, delay);
            }
        };
        var getScaleFactor = function (panel) {
            if (panel.scrollHeight <= panel.clientHeight) {
                return 1;
            }
            return panel.scrollTop / (panel.scrollHeight - panel.clientHeight);
        };
        var setPanelScrollTops = function () {
            if (panels.preview) {
                panels.preview.scrollTop = (panels.preview.scrollHeight - panels.preview.clientHeight) * getScaleFactor(panels.preview);
            }
        };
        this.refresh = function (requiresRefresh) {
            if (requiresRefresh) {
                oldInputText = "";
                makePreviewHtml();
            }
            else {
                applyTimeout();
            }
        };
        this.processingTime = function () {
            return elapsedTime;
        };
        var isFirstTimeFilled = true;

        var ieSafePreviewSet = function (text) {
            var preview = panels.preview;
            var parent = preview.parentNode;
            var sibling = preview.nextSibling;
            parent.removeChild(preview);
            preview.innerHTML = text;
            if (!sibling)
                parent.appendChild(preview);
            else
                parent.insertBefore(preview, sibling);
        }
        var nonSuckyBrowserPreviewSet = function (text) {
            panels.preview.innerHTML = text;
        }

        var previewSetter;

        var previewSet = function (text) {
            if (previewSetter)
                return previewSetter(text);

            try {
                nonSuckyBrowserPreviewSet(text);
                previewSetter = nonSuckyBrowserPreviewSet;
            } catch (e) {
                previewSetter = ieSafePreviewSet;
                previewSetter(text);
            }
        };

        var pushPreviewHtml = function (text) {
            var emptyTop = position.getTop(panels.input) - getDocScrollTop();
            if (panels.preview) {
                previewSet(text);
                previewRefreshCallback();
            }
            setPanelScrollTops();
            if (isFirstTimeFilled) {
                isFirstTimeFilled = false;
                return;
            }
            var fullTop = position.getTop(panels.input) - getDocScrollTop();
            if (uaSniffed.isIE) {
                setTimeout(function () {
                    window.scrollBy(0, fullTop - emptyTop);
                }, 0);
            }
            else {
                window.scrollBy(0, fullTop - emptyTop);
            }
        };
        var init = function () {
            setupEvents(panels.input, applyTimeout);
            makePreviewHtml();

            if (panels.preview) {
                panels.preview.scrollTop = 0;
            }
        };
        init();
    };


    ui.createBackground = function () {
        var background = doc.createElement("div"),
            style = background.style;
        background.className = "md-prompt-background";
        style.position = "absolute";
        style.top = "0";
        style.zIndex = "1000";
        if (uaSniffed.isIE) {
            style.filter = "alpha(opacity=50)";
        }
        else {
            style.opacity = "0.5";
        }
        var pageSize = position.getPageSize();
        style.height = pageSize[1] + "px";
        if (uaSniffed.isIE) {
            style.left = doc.documentElement.scrollLeft;
            style.width = doc.documentElement.clientWidth;
        }
        else {
            style.left = "0";
            style.width = "100%";
        }
        doc.body.appendChild(background);
        return background;
    };

    ui.prompt = function (text, defaultInputText, callback, yes, cancel) {
        var dialog;
        var input;
        var image;
        if (defaultInputText === undefined) {
            defaultInputText = "";
        }
        if (defaultInputText == 'image') {
            image = true;
        }
        var checkEscape = function (key) {
            var code = (key.charCode || key.keyCode);
            if (code === 27) {
                close(true, null);
            }
        };
        var close = function (isCancel, success) {
            util.removeEvent(doc.body, "keydown", checkEscape);
            var text = input.value;

            if (isCancel) {
                text = null;
            }
            else {
                text = text.replace(/^http:\/\/(https?|ftp):\/\//, '$1://');
                // if (!/^(?:https?|ftp):\/\//.test(text))
                //     text = 'http://' + text;
            }
            dialog.parentNode.removeChild(dialog);


            if (success && success.success) {
                if (image) {
                    // if (text != null && text.indexOf("\\")) {
                    //     var arr = text.split("\\");
                    //     var length = arr.length;
                    //     if (length < 2) {
                    //         text = text;
                    //     } else {
                    //         text = "/image/article/upload/" + arr[length - 1];
                    //     }
                    // }
                    text=success.msg;
                }
                callback(text);
            } else if (success != null && success.msg != null) {
                callback(success.msg);
            }else {
                callback(null);
            }
            return false;
        };

        var createDialog = function () {
            dialog = doc.createElement("div");
            dialog.className = "md-prompt-dialog";
            var question = doc.createElement("div");
            question.innerHTML = text;
            dialog.appendChild(question);
            var form = doc.createElement("form"),
                style = form.style;
            form.name = "uploadForm";
            form.id = "uploadForm";
            form.onsubmit = function () {
                return close(false, null);
            };
            dialog.appendChild(form);
            input = doc.createElement("input");
            if (image) {
                form.enctype = "multipart/form-data";
                input.type = "text";
                input.name = "textField";
                input.id = "textField";
                input.className = "md-textfield";
                form.innerHTML = "<input type='hidden' name='idField' id='idField'/>" +
                    "<input type='file' name='file' class='md-file' id='file' " +
                    " onchange=\"document.getElementById('textField').value=this.value\" />";
            } else {
                input.type = "input";
                input.style.width = "320px";
                input.value = defaultInputText;
            }
            form.appendChild(input);
            var success;
            var okButton = doc.createElement("button");
            okButton.type = "button";
            okButton.className = "col-md-offset-4 btn btn-success";
            okButton.style.marginTop = '10px';
            //增加一个提交功能
            okButton.onclick = function () {
                 var id = $('#id').val();
                $('#uploadForm #idField').val(id);
                $.ajax({
                    type: 'POST',
                    url: "/read/admin/article/add/picture",
                    data: new FormData($("#uploadForm")[0]),
                    async: false,
                    processData: false,
                    contentType: false,
                    dataType: 'json',
                    success: function (result) {
                        success = result;
                    },
                    error: function () {
                    }
                });
                return close(false, success);
            };
            if (image) {
                okButton.innerHTML = "上传";
            } else {
                okButton.innerHTML = yes;
            }
            var cancelButton = doc.createElement("button");
            cancelButton.type = "button";
            cancelButton.className = "btn btn-warning";
            cancelButton.style.marginTop = '10px';
            cancelButton.onclick = function () {
                return close(true, null);
            };
            cancelButton.innerHTML = cancel;
            form.appendChild(okButton);
            form.appendChild(cancelButton);
            util.addEvent(doc.body, "keydown", checkEscape);
            doc.body.appendChild(dialog);
        };

        setTimeout(function () {
            createDialog();
            var defTextLen = defaultInputText.length;
            if (!image && input.selectionStart !== undefined) {
                input.selectionStart = 0;
                input.selectionEnd = defTextLen;
            } else if (input.createTextRange) {
                var range = input.createTextRange();
                range.collapse(false);
                range.moveStart("character", -defTextLen);
                range.moveEnd("character", defTextLen);
                range.select();
            }
            input.focus();
        }, 0);
    };

    function UIManager(postfix, panels, undoManager, previewManager, commandManager, fullScreenManager, helpOptions, getString) {
        var inputBox = panels.input,
            buttons = {};
        makeSpritedButtonRow();
        var keyEvent = "keydown";
        if (uaSniffed.isOpera) {
            keyEvent = "keypress";
        }
        util.addEvent(inputBox, keyEvent, function (key) {
            if ((key.ctrlKey || key.metaKey) && !key.altKey && !key.shiftKey) {

                var keyCode = key.charCode || key.keyCode;
                var keyCodeStr = String.fromCharCode(keyCode).toLowerCase();
                switch (keyCodeStr) {
                    case "b":
                        doClick(buttons.bold);
                        break;
                    case "i":
                        doClick(buttons.italic);
                        break;
                    case "l":
                        doClick(buttons.link);
                        break;
                    case "q":
                        doClick(buttons.quote);
                        break;
                    case "k":
                        doClick(buttons.code);
                        break;
                    case "g":
                        doClick(buttons.image);
                        break;
                    case "o":
                        doClick(buttons.olist);
                        break;
                    case "u":
                        doClick(buttons.ulist);
                        break;
                    case"f":
                        doClick(buttons.fullscreen);
                        break;
                    case"e":
                        doClick(buttons.exitFullscreen);
                        break;
                    case "h":
                        doClick(buttons.heading);
                        break;
                    case "r":
                        doClick(buttons.hr);
                        break;
                    case "y":
                        doClick(buttons.redo);
                        break;
                    case "z":
                        if (key.shiftKey) {
                            doClick(buttons.redo);
                        }
                        else {
                            doClick(buttons.undo);
                        }
                        break;
                    default:
                        return;
                }

                if (key.preventDefault) {
                    key.preventDefault();
                }

                if (window.event) {
                    window.event.returnValue = false;
                }
            } else {
                if (key.keyCode == 9) {
                    var button = {};
                    button.textOp = bindCommand("doTab");
                    doClick(p);
                    if (key.preventDefault) {
                        key.preventDefault();
                    }
                    if (window.event) {
                        window.event.returnValue = false;
                    }
                }
            }
        });

        util.addEvent(inputBox, "keyup", function (key) {
            if (key.shiftKey && !key.ctrlKey && !key.metaKey) {
                var keyCode = key.charCode || key.keyCode;
                // Character 13 is Enter
                if (keyCode === 13) {
                    var fakeButton = {};
                    fakeButton.textOp = bindCommand("doAutoindent");
                    doClick(fakeButton);
                }
            }
        });

        if (uaSniffed.isIE) {
            util.addEvent(inputBox, "keydown", function (key) {
                var code = key.keyCode;
                if (code === 27) {
                    return false;
                }
            });
        }


        function doClick(button) {

            inputBox.focus();

            if (button.textOp) {

                if (undoManager) {
                    undoManager.setCommandMode();
                }

                var state = new TextareaState(panels);

                if (!state) {
                    return;
                }

                var chunks = state.getChunks();

                var fixupInputArea = function () {

                    inputBox.focus();

                    if (chunks) {
                        state.setChunks(chunks);
                    }

                    state.restore();
                    previewManager.refresh();
                };

                var noCleanup = button.textOp(chunks, fixupInputArea);

                if (!noCleanup) {
                    fixupInputArea();
                }

            }

            if (button.execute) {
                button.execute(undoManager);
            }
        };

        function setupButton(button, isEnabled) {
            if (isEnabled) {

                if (uaSniffed.isIE) {
                    button.onmousedown = function () {
                        if (doc.activeElement && doc.activeElement !== panels.input) {
                            return;
                        }
                        panels.ieCachedRange = document.selection.createRange();
                        panels.ieCachedScrollTop = panels.input.scrollTop;
                    };
                }

                if (!button.isHelp) {
                    button.onclick = function () {
                        doClick(this);
                        return false;
                    }
                }
            } else {
                button.onmouseover = button.onmouseout = button.onclick = function () {
                };
            }
        }

        function bindCommand(method) {
            if (typeof method === "string")
                method = commandManager[method];
            return function () {
                method.apply(commandManager, arguments);
            }
        }

        function makeSpritedButtonRow() {
            var buttonBar = panels.buttonBar;
            //创建button行
            var buttonRow = document.createElement("ul");
            buttonRow.id = "md-button-row" + postfix;
            buttonRow.className = 'md-button-row';
            //ul列表中插入button行
            buttonRow = buttonBar.appendChild(buttonRow);
            var xPosition = 0;
            var makeButton = function (id, className, title, textOp) {
                var button = document.createElement("li");
                button.className = "md-button " + className;
                button.style.left = xPosition + "px";
                xPosition += 25;
                var buttonImage = document.createElement("span");
                button.id = id + postfix;
                button.appendChild(buttonImage);
                button.title = title;
                if (textOp)
                    button.textOp = textOp;
                setupButton(button, true);
                buttonRow.appendChild(button);
                return button;
            };

            var makeSpacer = function (num) {
                var spacer = document.createElement("li");
                spacer.className = "md-spacer md-spacer" + num;
                spacer.id = "md-spacer" + num + postfix;
                buttonRow.appendChild(spacer);
                xPosition += 25;
            }

            buttons.bold = makeButton("md-bold-button", "fa fa-bold", getString("bold"), bindCommand("doBold"));
            buttons.italic = makeButton("md-italic-button", "fa fa-italic", getString("italic"), bindCommand("doItalic"));
            // makeSpacer(1);
            buttons.link = makeButton("md-link-button", "fa fa-chain", getString("link"), bindCommand(function (chunk, postProcessing) {
                return this.doLinkOrImage(chunk, postProcessing, false);
            }));
            buttons.quote = makeButton("md-quote-button", "fa fa-quote-left", getString("quote"), bindCommand("doBlockquote"));
            buttons.code = makeButton("md-code-button", "fa fa-code", getString("code"), bindCommand("doCode"));
            buttons.image = makeButton("md-image-button", "fa fa-photo", getString("image"), bindCommand(function (chunk, postProcessing) {
                return this.doLinkOrImage(chunk, postProcessing, true);
            }));
            // makeSpacer(2);
            buttons.olist = makeButton("md-olist-button", "fa fa-list-ol", getString("olist"), bindCommand(function (chunk, postProcessing) {
                this.doList(chunk, postProcessing, true);
            }));
            buttons.ulist = makeButton("md-ulist-button", "fa fa-list-ul", getString("ulist"), bindCommand(function (chunk, postProcessing) {
                this.doList(chunk, postProcessing, false);
            }));

            buttons.heading = makeButton("md-heading-button", "fa fa-header", getString("heading"), bindCommand("doHeading"));
            buttons.hr = makeButton("md-hr-button", "fa fa-minus", getString("hr"), bindCommand("doHorizontalRule"));
            // makeSpacer(3);
            buttons.undo = makeButton("md-undo-button", "fa fa-undo", getString("undo"), null);
            buttons.undo.execute = function (manager) {
                if (manager) manager.undo();
            };

            var redoTitle = /win/.test(nav.platform.toLowerCase()) ? getString("redo") : getString("redomac");

            buttons.redo = makeButton("md-redo-button", "fa fa-repeat", redoTitle, null);
            buttons.redo.execute = function (manager) {
                if (manager) manager.redo();
            };
            buttons.fullscreen = makeButton("md-fullscreen-button", "fa fa-columns", getString("fullscreen"), null);
            buttons.fullscreen.execute = function () {
                fullScreenManager.doFullScreen(buttons, true)
            };
            buttons.exitFullscreen = makeButton("md-exit-fullscreen-button", " fa fa-square-o", getString("exitFullscreen"), null);
            buttons.exitFullscreen.style.display = "none";
            buttons.exitFullscreen.execute = function () {
                fullScreenManager.doFullScreen(buttons, false)
            };
            buttons.save = makeButton("md-save-button", " fa fa-save", getString("save"), null);

            if (helpOptions) {
                var helpButton = document.createElement("li");
                var helpButtonImage = document.createElement("span");
                helpButton.appendChild(helpButtonImage);
                helpButton.className = "md-button wmd-help-button";
                helpButton.id = "md-help-button" + postfix;
                helpButton.isHelp = true;
                helpButton.style.right = "0px";
                helpButton.title = getString("help");
                helpButton.onclick = helpOptions.handler;

                setupButton(helpButton, true);
                buttonRow.appendChild(helpButton);
                buttons.help = helpButton;
            }

            setUndoRedoButtonStates();
        }

        function setUndoRedoButtonStates() {
            if (undoManager) {
                setupButton(buttons.undo, undoManager.canUndo());
                setupButton(buttons.redo, undoManager.canRedo());
            }
        };
        this.setUndoRedoButtonStates = setUndoRedoButtonStates;

    }

    function CommandManager(pluginHooks, getString) {
        this.hooks = pluginHooks;
        this.getString = getString;
    }

    var commandProto = CommandManager.prototype;
    commandProto.prefixes = "(?:\\s{4,}|\\s*>|\\s*-\\s+|\\s*\\d+\\.|=|\\+|-|_|\\*|#|\\s*\\[[^\n]]+\\]:)";
    commandProto.unwrap = function (chunk) {
        var txt = new re("([^\\n])\\n(?!(\\n|" + this.prefixes + "))", "g");
        chunk.selection = chunk.selection.replace(txt, "$1 $2");
    };
    commandProto.wrap = function (chunk, len) {
        this.unwrap(chunk);
        var regex = new re("(.{1," + len + "})( +|$\\n?)", "gm"),
            that = this;

        chunk.selection = chunk.selection.replace(regex, function (line, marked) {
            if (new re("^" + that.prefixes, "").test(line)) {
                return line;
            }
            return marked + "\n";
        });

        chunk.selection = chunk.selection.replace(/\s+$/, "");
    };
    commandProto.doBold = function (chunk, postProcessing) {
        return this.doBorI(chunk, postProcessing, 2, this.getString("boldexample"));
    };
    commandProto.doItalic = function (chunk, postProcessing) {
        return this.doBorI(chunk, postProcessing, 1, this.getString("italicexample"));
    };
    commandProto.doBorI = function (chunk, postProcessing, nStars, insertText) {
        chunk.trimWhitespace();
        chunk.selection = chunk.selection.replace(/\n{2,}/g, "\n");
        var starsBefore = /(\**$)/.exec(chunk.before)[0];
        var starsAfter = /(^\**)/.exec(chunk.after)[0];
        var prevStars = Math.min(starsBefore.length, starsAfter.length);
        if ((prevStars >= nStars) && (prevStars != 2 || nStars != 1)) {
            chunk.before = chunk.before.replace(re("[*]{" + nStars + "}$", ""), "");
            chunk.after = chunk.after.replace(re("^[*]{" + nStars + "}", ""), "");
        }
        else if (!chunk.selection && starsAfter) {
            chunk.after = chunk.after.replace(/^([*_]*)/, "");
            chunk.before = chunk.before.replace(/(\s?)$/, "");
            var whitespace = re.$1;
            chunk.before = chunk.before + starsAfter + whitespace;
        }
        else {
            if (!chunk.selection && !starsAfter) {
                chunk.selection = insertText;
            }
            var markup = nStars <= 1 ? "*" : "**";
            chunk.before = chunk.before + markup;
            chunk.after = markup + chunk.after;
        }
        return;
    };

    commandProto.stripLinkDefs = function (text, defsToAdd) {
        text = text.replace(/^[ ]{0,3}\[(\d+)\]:[ \t]*\n?[ \t]*<?(\S+?)>?[ \t]*\n?[ \t]*(?:(\n*)["(](.+?)[")][ \t]*)?(?:\n+|$)/gm,
            function (totalMatch, id, link, newlines, title) {
                defsToAdd[id] = totalMatch.replace(/\s*$/, "");
                if (newlines) {
                    defsToAdd[id] = totalMatch.replace(/["(](.+?)[")]$/, "");
                    return newlines + title;
                }
                return "";
            });
        return text;
    };

    commandProto.addLinkDef = function (chunk, linkDef) {
        var refNumber = 0;
        var defsToAdd = {};
        chunk.before = this.stripLinkDefs(chunk.before, defsToAdd);
        chunk.selection = this.stripLinkDefs(chunk.selection, defsToAdd);
        chunk.after = this.stripLinkDefs(chunk.after, defsToAdd);
        var defs = "";
        var regex = /(\[)((?:\[[^\]]*\]|[^\[\]])*)(\][ ]?(?:\n[ ]*)?\[)(\d+)(\])/g;

        var addDefNumber = function (def) {
            refNumber++;
            def = def.replace(/^[ ]{0,3}\[(\d+)\]:/, "  [" + refNumber + "]:");
            defs += "\n" + def;
        };

        var getLink = function (wholeMatch, before, inner, afterInner, id, end) {
            inner = inner.replace(regex, getLink);
            if (defsToAdd[id]) {
                addDefNumber(defsToAdd[id]);
                return before + inner + afterInner + refNumber + end;
            }
            return wholeMatch;
        };
        chunk.before = chunk.before.replace(regex, getLink);
        if (linkDef) {
            addDefNumber(linkDef);
        } else {
            chunk.selection = chunk.selection.replace(regex, getLink);
        }
        var refOut = refNumber;
        chunk.after = chunk.after.replace(regex, getLink);
        if (chunk.after) {
            chunk.after = chunk.after.replace(/\n*$/, "");
        }
        if (!chunk.after) {
            chunk.selection = chunk.selection.replace(/\n*$/, "");
        }
        chunk.after += "\n\n" + defs;
        return refOut;
    };

    /**
     * url转码
     * @param linkdef
     * @returns {XML|string|void}
     */
    function properlyEncoded(linkdef) {
        return linkdef.replace(/^\s*(.*?)(?:\s+"(.+)")?\s*$/, function (wholematch, link, title) {
            link = link.replace(/\?.*$/, function (querypart) {
                return querypart.replace(/\+/g, " ");
            });
            link = decodeURIComponent(link);
            link = encodeURI(link).replace(/'/g, '%27').replace(/\(/g, '%28').replace(/\)/g, '%29');
            link = link.replace(/\?.*$/, function (querypart) {
                return querypart.replace(/\+/g, "%2b");
            });
            if (title) {
                title = title.trim ? title.trim() : title.replace(/^\s*/, "").replace(/\s*$/, "");
                title = title.replace(/"/g, "quot;").replace(/\(/g, "&#40;").replace(/\)/g, "&#41;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
            }
            return title ? link + ' "' + title + '"' : link;
        });
    };

    commandProto.doLinkOrImage = function (chunk, postProcessing, isImage) {
        chunk.trimWhitespace();
        chunk.findTags(/\s*!?\[/, /\][ ]?(?:\n[ ]*)?(\[.*?\])?/);
        var background;

        if (chunk.endTag.length > 1 && chunk.startTag.length > 0) {
            chunk.startTag = chunk.startTag.replace(/!?\[/, "");
            chunk.endTag = "";
            this.addLinkDef(chunk, null);

        } else {
            chunk.selection = chunk.startTag + chunk.selection + chunk.endTag;
            chunk.startTag = chunk.endTag = "";
            if (/\n\n/.test(chunk.selection)) {
                this.addLinkDef(chunk, null);
                return;
            }
            var that = this;
            var linkEnteredCallback = function (link) {
                background.parentNode.removeChild(background);
                if (link !== null) {
                    chunk.selection = (" " + chunk.selection).replace(/([^\\](?:\\\\)*)(?=[[\]])/g, "$1\\").substr(1);
                    if (!isImage) {
                        var linkDef = " [999]: " + properlyEncoded(link);
                    } else {
                        var linkDef = " [999]: " + link;
                    }
                    var num = that.addLinkDef(chunk, linkDef);
                    chunk.startTag = isImage ? "![" : "[";
                    chunk.endTag = "][" + num + "]";
                    if (!chunk.selection) {
                        if (isImage) {
                            chunk.selection = that.getString("imagedescription");
                        }
                        else {
                            chunk.selection = that.getString("linkdescription");
                        }
                    }
                }
                postProcessing();
            };

            background = ui.createBackground();

            if (isImage) {
                if (!this.hooks.insertImageDialog(linkEnteredCallback))
                    ui.prompt(this.getString("imagedialog"), 'image', linkEnteredCallback,
                        this.getString("ok"), this.getString("cancel"));
            }
            else {
                ui.prompt(this.getString("linkdialog"), linkDefaultText, linkEnteredCallback,
                    this.getString("ok"), this.getString("cancel"));
            }
            return true;
        }
    };


    commandProto.doAutoindent = function (chunk, postProcessing) {
        var commandMgr = this,
            fakeSelection = false;

        chunk.before = chunk.before.replace(/(\n|^)[ ]{0,3}([*+-]|\d+[.])[ \t]*\n$/, "\n\n");
        chunk.before = chunk.before.replace(/(\n|^)[ ]{0,3}>[ \t]*\n$/, "\n\n");
        chunk.before = chunk.before.replace(/(\n|^)[ \t]+\n$/, "\n\n");

        if (!chunk.selection && !/^[ \t]*(?:\n|$)/.test(chunk.after)) {
            chunk.after = chunk.after.replace(/^[^\n]*/, function (wholeMatch) {
                chunk.selection = wholeMatch;
                return "";
            });
            fakeSelection = true;
        }

        if (/(\n|^)[ ]{0,3}([*+-]|\d+[.])[ \t]+.*\n$/.test(chunk.before)) {
            if (commandMgr.doList) {
                commandMgr.doList(chunk);
            }
        }
        if (/(\n|^)[ ]{0,3}>[ \t]+.*\n$/.test(chunk.before)) {
            if (commandMgr.doBlockquote) {
                commandMgr.doBlockquote(chunk);
            }
        }
        if (/(\n|^)(\t|[ ]{4,}).*\n$/.test(chunk.before)) {
            if (commandMgr.doCode) {
                commandMgr.doCode(chunk);
            }
        }
        if (fakeSelection) {
            chunk.after = chunk.selection + chunk.after;
            chunk.selection = "";
        }
    };
    commandProto.doBlockquote = function (chunk, postProcessing) {
        chunk.selection = chunk.selection.replace(/^(\n*)([^\r]+?)(\n*)$/,
            function (totalMatch, newlinesBefore, text, newlinesAfter) {
                chunk.before += newlinesBefore;
                chunk.after = newlinesAfter + chunk.after;
                return text;
            });

        chunk.before = chunk.before.replace(/(>[ \t]*)$/,
            function (totalMatch, blankLine) {
                chunk.selection = blankLine + chunk.selection;
                return "";
            });

        chunk.selection = chunk.selection.replace(/^(\s|>)+$/, "");
        chunk.selection = chunk.selection || this.getString("quoteexample");

        var match = "", leftOver = "", line;
        if (chunk.before) {
            var lines = chunk.before.replace(/\n$/, "").split("\n");
            var inChain = false;
            for (var i = 0; i < lines.length; i++) {
                var good = false;
                line = lines[i];
                inChain = inChain && line.length > 0;
                if (/^>/.test(line)) {
                    good = true;
                    if (!inChain && line.length > 1)
                        inChain = true;
                } else if (/^[ \t]*$/.test(line)) {
                    good = true;
                } else {
                    good = inChain;
                }
                if (good) {
                    match += line + "\n";
                } else {
                    leftOver += match + line;
                    match = "\n";
                }
            }
            if (!/(^|\n)>/.test(match)) {
                leftOver += match;
                match = "";
            }
        }

        chunk.startTag = match;
        chunk.before = leftOver;

        if (chunk.after) {
            chunk.after = chunk.after.replace(/^\n?/, "\n");
        }

        chunk.after = chunk.after.replace(/^(((\n|^)(\n[ \t]*)*>(.+\n)*.*)+(\n[ \t]*)*)/, function (totalMatch) {
                chunk.endTag = totalMatch;
                return "";
            }
        );

        var replaceBlanksInTags = function (useBracket) {

            var replacement = useBracket ? "> " : "";

            if (chunk.startTag) {
                chunk.startTag = chunk.startTag.replace(/\n((>|\s)*)\n$/,
                    function (totalMatch, markdown) {
                        return "\n" + markdown.replace(/^[ ]{0,3}>?[ \t]*$/gm, replacement) + "\n";
                    });
            }
            if (chunk.endTag) {
                chunk.endTag = chunk.endTag.replace(/^\n((>|\s)*)\n/,
                    function (totalMatch, markdown) {
                        return "\n" + markdown.replace(/^[ ]{0,3}>?[ \t]*$/gm, replacement) + "\n";
                    });
            }
        };

        if (/^(?![ ]{0,3}>)/m.test(chunk.selection)) {
            this.wrap(chunk, SETTINGS.lineLength - 2);
            chunk.selection = chunk.selection.replace(/^/gm, "> ");
            replaceBlanksInTags(true);
            chunk.skipLines();
        } else {
            chunk.selection = chunk.selection.replace(/^[ ]{0,3}> ?/gm, "");
            this.unwrap(chunk);
            replaceBlanksInTags(false);

            if (!/^(\n|^)[ ]{0,3}>/.test(chunk.selection) && chunk.startTag) {
                chunk.startTag = chunk.startTag.replace(/\n{0,2}$/, "\n\n");
            }

            if (!/(\n|^)[ ]{0,3}>.*$/.test(chunk.selection) && chunk.endTag) {
                chunk.endTag = chunk.endTag.replace(/^\n{0,2}/, "\n\n");
            }
        }

        chunk.selection = this.hooks.postBlockquoteCreation(chunk.selection);

        if (!/\n/.test(chunk.selection)) {
            chunk.selection = chunk.selection.replace(/^(> *)/,
                function (wholeMatch, blanks) {
                    chunk.startTag += blanks;
                    return "";
                });
        }
    };

    commandProto.doCode = function (chunk, postProcessing) {

        var hasTextBefore = /\S[ ]*$/.test(chunk.before);
        var hasTextAfter = /^[ ]*\S/.test(chunk.after);
        if ((!hasTextAfter && !hasTextBefore) || /\n/.test(chunk.selection)) {
            chunk.before = chunk.before.replace(/[ ]{4}$/, function (totalMatch) {
                chunk.selection = totalMatch + chunk.selection;
                return "";
            });

            var nLinesBack = 1;
            var nLinesForward = 1;

            if (/(\n|^)(\t|[ ]{4,}).*\n$/.test(chunk.before)) {
                nLinesBack = 0;
            }
            if (/^\n(\t|[ ]{4,})/.test(chunk.after)) {
                nLinesForward = 0;
            }
            chunk.skipLines(nLinesBack, nLinesForward);

            if (!chunk.selection) {
                chunk.startTag = "```\n";
                chunk.selection = this.getString("codeexample");
                chunk.endTag = "\n```";
            } else {
                if (/^[ ]{0,3}\S/m.test(chunk.selection)) {
                    if (/\n/.test(chunk.selection))
                        chunk.selection = chunk.selection.replace(/^/gm, "    ");
                    else {
                        chunk.before += "```\n";
                        chunk.endTag += "\n```";
                    }
                } else {
                    chunk.selection = chunk.selection.replace(/^(?:[ ]{4}|[ ]{0,3}\t)/gm, "");
                }
            }
        } else {
            chunk.trimWhitespace();
            chunk.findTags(/`/, /`/);
            if (!chunk.startTag && !chunk.endTag) {
                chunk.startTag = chunk.endTag = "`";
                if (!chunk.selection) {
                    chunk.selection = this.getString("codeexample");
                }
            }
            else if (chunk.endTag && !chunk.startTag) {
                chunk.before += chunk.endTag;
                chunk.endTag = "";
            }
            else {
                chunk.startTag = chunk.endTag = "";
            }
        }
    };

    commandProto.doList = function (chunk, postProcessing, isNumberedList) {

        var previousItemsRegex = /(\n|^)(([ ]{0,3}([*+-]|\d+[.])[ \t]+.*)(\n.+|\n{2,}([*+-].*|\d+[.])[ \t]+.*|\n{2,}[ \t]+\S.*)*)\n*$/;
        var nextItemsRegex = /^\n*(([ ]{0,3}([*+-]|\d+[.])[ \t]+.*)(\n.+|\n{2,}([*+-].*|\d+[.])[ \t]+.*|\n{2,}[ \t]+\S.*)*)\n*/;

        var bullet = "-";
        var num = 1;
        var getItemPrefix = function () {
            var prefix;
            if (isNumberedList) {
                prefix = " " + num + ". ";
                num++;
            }
            else {
                prefix = " " + bullet + " ";
            }
            return prefix;
        };

        var getPrefixedItem = function (itemText) {

            if (isNumberedList === undefined) {
                isNumberedList = /^\s*\d/.test(itemText);
            }

            itemText = itemText.replace(/^[ ]{0,3}([*+-]|\d+[.])\s/gm,
                function (_) {
                    return getItemPrefix();
                });

            return itemText;
        };

        chunk.findTags(/(\n|^)*[ ]{0,3}([*+-]|\d+[.])\s+/, null);

        if (chunk.before && !/\n$/.test(chunk.before) && !/^\n/.test(chunk.startTag)) {
            chunk.before += chunk.startTag;
            chunk.startTag = "";
        }

        if (chunk.startTag) {
            var hasDigits = /\d+[.]/.test(chunk.startTag);
            chunk.startTag = "";
            chunk.selection = chunk.selection.replace(/\n[ ]{4}/g, "\n");
            this.unwrap(chunk);
            chunk.skipLines();
            if (hasDigits) {
                chunk.after = chunk.after.replace(nextItemsRegex, getPrefixedItem);
            }
            if (isNumberedList == hasDigits) {
                return;
            }
        }

        var nLinesUp = 1;
        chunk.before = chunk.before.replace(previousItemsRegex,
            function (itemText) {
                if (/^\s*([*+-])/.test(itemText)) {
                    bullet = re.$1;
                }
                nLinesUp = /[^\n]\n\n[^\n]/.test(itemText) ? 1 : 0;
                return getPrefixedItem(itemText);
            });

        if (!chunk.selection) {
            chunk.selection = this.getString("litem");
        }
        var prefix = getItemPrefix();
        var nLinesDown = 1;
        chunk.after = chunk.after.replace(nextItemsRegex,
            function (itemText) {
                nLinesDown = /[^\n]\n\n[^\n]/.test(itemText) ? 1 : 0;
                return getPrefixedItem(itemText);
            });
        chunk.trimWhitespace(true);
        chunk.skipLines(nLinesUp, nLinesDown, true);
        chunk.startTag = prefix;
        var spaces = prefix.replace(/./g, " ");
        this.wrap(chunk, SETTINGS.lineLength - spaces.length);
        chunk.selection = chunk.selection.replace(/\n/g, "\n" + spaces);
    };
    commandProto.doHeading = function (chunk, postProcessing) {

        chunk.selection = chunk.selection.replace(/\s+/g, " ");
        chunk.selection = chunk.selection.replace(/(^\s+|\s+$)/g, "");

        if (!chunk.selection) {
            chunk.startTag = "## ";
            chunk.selection = this.getString("headingexample");
            chunk.endTag = " ##";
            return;
        }

        var headerLevel = 0;
        chunk.findTags(/#+[ ]*/, /[ ]*#+/);
        if (/#+/.test(chunk.startTag)) {
            headerLevel = re.lastMatch.length;
        }
        chunk.startTag = chunk.endTag = "";

        chunk.findTags(null, /\s?(-+|=+)/);
        if (/=+/.test(chunk.endTag)) {
            headerLevel = 1;
        }
        if (/-+/.test(chunk.endTag)) {
            headerLevel = 2;
        }

        chunk.startTag = chunk.endTag = "";
        chunk.skipLines(1, 1);
        var headerLevelToCreate = headerLevel == 0 ? 2 : headerLevel - 1;
        if (headerLevelToCreate > 0) {
            var headerChar = headerLevelToCreate >= 2 ? "-" : "=";
            var len = chunk.selection.length;
            if (len > SETTINGS.lineLength) {
                len = SETTINGS.lineLength;
            }
            chunk.endTag = "\n";
            while (len--) {
                chunk.endTag += headerChar;
            }
        }
    };
    commandProto.doHorizontalRule = function (chunk, postProcessing) {
        chunk.startTag = "----------\n";
        chunk.selection = "";
        chunk.skipLines(2, 1, true);
    }

    commandProto.doTab = function (chunk, postProcessing) {
        chunk.startTag = "    ";
        chunk.selection = ""
    };

    function FullScreenManager(chunk, postProcessing) {
        this.hooks = chunk;
        this.getString = postProcessing;
    }

    FullScreenManager.prototype.doFullScreen = function (buttons, full) {
        if (full) {
            hljs.initHighlighting();
            $(".md-button-bar").parent().removeClass("col-md-8")
            $("#articleForm .title-categories").addClass("md-hidetab");
            $("#articleForm .keywords").addClass("md-hidetab");
            preview.removeClass("md-hidetab");
            preview.addClass("col-md-6");
            textarea.parent().addClass("col-md-6");
            textarea.height(700);
            preview.height(690);
            buttons.fullscreen.style.display = "none";
            buttons.exitFullscreen.style.display = "";
        } else {
            $(".md-button-bar").parent().removeClass("col-md-6")
            $("#articleForm .title-categories").removeClass("md-hidetab");
            $("#articleForm .keywords").removeClass("md-hidetab");
            preview.addClass("md-hidetab");
            textarea.parent().addClass("col-md-8");
            textarea.height(450);
            preview.height(450);
            buttons.fullscreen.style.display = "";
            buttons.exitFullscreen.style.display = "none";
        }
    }
})();

//Extra
//Extra
//Extra

(function () {
    var inlineTags = new RegExp(['^(<\\/?(a|abbr|acronym|applet|area|b|basefont|',
        'bdo|big|button|cite|code|del|dfn|em|figcaption|',
        'font|i|iframe|img|input|ins|kbd|label|map|',
        'mark|meter|object|param|progress|q|ruby|rp|rt|s|',
        'samp|script|select|small|span|strike|strong|',
        'sub|sup|textarea|time|tt|u|var|wbr)[^>]*>|',
        '<(br)\\s?\\/?>)$'].join(''), 'i');

    if (!Array.indexOf) {
        Array.prototype.indexOf = function (obj) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == obj) {
                    return i;
                }
            }
            return -1;
        };
    }

    function trim(str) {
        return str.replace(/^\s+|\s+$/g, '');
    }

    function rtrim(str) {
        return str.replace(/\s+$/g, '');
    }

    function outdent(text) {
        return text.replace(new RegExp('^(\\t|[ ]{1,4})', 'gm'), '');
    }

    function contains(str, substr) {
        return str.indexOf(substr) != -1;
    }

    function sanitizeHtml(html, whitelist) {
        return html.replace(/<[^>]*>?/gi, function (tag) {
            return tag.match(whitelist) ? tag : '';
        });
    }

    function union(x, y) {
        var obj = {};
        for (var i = 0; i < x.length; i++)
            obj[x[i]] = x[i];
        for (i = 0; i < y.length; i++)
            obj[y[i]] = y[i];
        var res = [];
        for (var k in obj) {
            if (obj.hasOwnProperty(k))
                res.push(obj[k]);
        }
        return res;
    }

    function addAnchors(text) {
        if (text.charAt(0) != '\x02')
            text = '\x02' + text;
        if (text.charAt(text.length - 1) != '\x03')
            text = text + '\x03';
        return text;
    }

    function removeAnchors(text) {
        if (text.charAt(0) == '\x02')
            text = text.substr(1);
        if (text.charAt(text.length - 1) == '\x03')
            text = text.substr(0, text.length - 1);
        return text;
    }


    function convertSpans(text, extra) {
        return sanitizeHtml(convertAll(text, extra), inlineTags);
    }

    function convertAll(text, extra) {
        var result = extra.blockGamutHookCallback(text);
        result = unescapeSpecialChars(result);
        result = result.replace(/~D/g, "$$").replace(/~T/g, "~");
        result = extra.previousPostConversion(result);
        return result;
    }

    function processEscapesStep1(text) {
        return text.replace(/\\\|/g, '~I').replace(/\\:/g, '~i');
    }

    function processEscapesStep2(text) {
        return text.replace(/~I/g, '|').replace(/~i/g, ':');
    }

    function unescapeSpecialChars(text) {
        text = text.replace(/~E(\d+)E/g, function (wholeMatch, m1) {
            var charCodeToReplace = parseInt(m1);
            return String.fromCharCode(charCodeToReplace);
        });
        return text;
    }

    function slugify(text) {
        return text.toLowerCase()
            .replace(/\s+/g, '-')
            .replace(/[^\w\-]+/g, '')
            .replace(/\-\-+/g, '-')
            .replace(/^-+/, '')
            .replace(/-+$/, '');
    }


    Markdown.Extra = function () {

        this.converter = null;
        this.hashBlocks = [];
        this.footnotes = {};
        this.usedFootnotes = [];
        this.attributeBlocks = false;
        this.googleCodePrettify = false;
        this.highlightJs = false;
        this.tableClass = '';
        this.tabWidth = 4;
    };

    Markdown.Extra.init = function (converter, options) {
        var extra = new Markdown.Extra();
        var postNormalizationTransformations = [];
        var preBlockGamutTransformations = [];
        var postSpanGamutTransformations = [];
        var postConversionTransformations = ["unHashExtraBlocks"];

        options = options || {};
        options.extensions = options.extensions || ["all"];
        if (contains(options.extensions, "all")) {
            options.extensions = ["tables", "fenced_code_gfm", "def_list", "attr_list", "footnotes", "smartypants", "strikethrough", "newlines"];
        }
        preBlockGamutTransformations.push("wrapHeaders");
        if (contains(options.extensions, "attr_list")) {
            postNormalizationTransformations.push("hashFcbAttributeBlocks");
            preBlockGamutTransformations.push("hashHeaderAttributeBlocks");
            postConversionTransformations.push("applyAttributeBlocks");
            extra.attributeBlocks = true;
        }
        if (contains(options.extensions, "fenced_code_gfm")) {
            preBlockGamutTransformations.push("fencedCodeBlocks");
            postNormalizationTransformations.push("fencedCodeBlocks");
        }
        if (contains(options.extensions, "tables")) {
            preBlockGamutTransformations.push("tables");
        }
        if (contains(options.extensions, "def_list")) {
            preBlockGamutTransformations.push("definitionLists");
        }
        if (contains(options.extensions, "footnotes")) {
            postNormalizationTransformations.push("stripFootnoteDefinitions");
            preBlockGamutTransformations.push("doFootnotes");
            postConversionTransformations.push("printFootnotes");
        }
        if (contains(options.extensions, "smartypants")) {
            postConversionTransformations.push("runSmartyPants");
        }
        if (contains(options.extensions, "strikethrough")) {
            postSpanGamutTransformations.push("strikethrough");
        }
        // if (contains(options.extensions, "newlines")) {
        postSpanGamutTransformations.push("newlines");
        // }
        converter.hooks.chain("postNormalization", function (text) {
            return extra.doTransform(postNormalizationTransformations, text) + '\n';
        });
        converter.hooks.chain("preBlockGamut", function (text, blockGamutHookCallback) {
            extra.blockGamutHookCallback = blockGamutHookCallback;
            text = processEscapesStep1(text);
            text = extra.doTransform(preBlockGamutTransformations, text) + '\n';
            text = processEscapesStep2(text);
            return text;
        });
        converter.hooks.chain("postSpanGamut", function (text) {
            return extra.doTransform(postSpanGamutTransformations, text);
        });
        extra.previousPostConversion = converter.hooks.postConversion;
        converter.hooks.chain("postConversion", function (text) {
            text = extra.doTransform(postConversionTransformations, text);
            extra.hashBlocks = [];
            extra.footnotes = {};
            extra.usedFootnotes = [];
            return text;
        });

        if ("highlighter" in options) {
            extra.googleCodePrettify = options.highlighter === 'prettify';
            extra.highlightJs = options.highlighter === 'highlight';
        }

        if ("table_class" in options) {
            extra.tableClass = options.table_class;
        }

        extra.converter = converter;
        return extra;
    };

    Markdown.Extra.prototype.doTransform = function (transformations, text) {
        for (var i = 0; i < transformations.length; i++)
            text = this[transformations[i]](text);
        return text;
    };

    Markdown.Extra.prototype.hashExtraBlock = function (block) {
        return '\n<p>~X' + (this.hashBlocks.push(block) - 1) + 'X</p>\n';
    };
    Markdown.Extra.prototype.hashExtraInline = function (block) {
        return '~X' + (this.hashBlocks.push(block) - 1) + 'X';
    };

    Markdown.Extra.prototype.unHashExtraBlocks = function (text) {
        var self = this;

        function recursiveUnHash() {
            var hasHash = false;
            text = text.replace(/(?:<p>)?~X(\d+)X(?:<\/p>)?/g, function (wholeMatch, m1) {
                hasHash = true;
                var key = parseInt(m1, 10);
                return self.hashBlocks[key];
            });
            if (hasHash === true) {
                recursiveUnHash();
            }
        }

        recursiveUnHash();
        return text;
    };
    Markdown.Extra.prototype.wrapHeaders = function (text) {
        function wrap(text) {
            return '\n' + text + '\n';
        }

        text = text.replace(/^.+[ \t]*\n=+[ \t]*\n+/gm, wrap);
        text = text.replace(/^.+[ \t]*\n-+[ \t]*\n+/gm, wrap);
        text = text.replace(/^\#{1,6}[ \t]*.+?[ \t]*\#*\n+/gm, wrap);
        return text;
    };

    var attrBlock = "\\{[ \\t]*((?:[#.][-_:a-zA-Z0-9]+[ \\t]*)+)\\}";
    var hdrAttributesA = new RegExp("^(#{1,6}.*#{0,6})[ \\t]+" + attrBlock + "[ \\t]*(?:\\n|0x03)", "gm");
    var hdrAttributesB = new RegExp("^(.*)[ \\t]+" + attrBlock + "[ \\t]*\\n (?=[\\-|=]+\\s*(?:\\n|0x03))", "gm");
    var fcbAttributes = new RegExp("^(```[ \\t]*[^{\\s]*)[ \\t]+" + attrBlock + "[ \\t]*\\n" +
        "(?=([\\s\\S]*?)\\n```[ \\t]*(\\n|0x03))", "gm");

    Markdown.Extra.prototype.hashHeaderAttributeBlocks = function (text) {

        var self = this;

        function attributeCallback(wholeMatch, pre, attr) {
            return '<p>~XX' + (self.hashBlocks.push(attr) - 1) + 'XX</p>\n' + pre + "\n";
        }

        text = text.replace(hdrAttributesA, attributeCallback);  // ## headers
        text = text.replace(hdrAttributesB, attributeCallback);  // underline headers
        return text;
    };

    Markdown.Extra.prototype.hashFcbAttributeBlocks = function (text) {

        var self = this;

        function attributeCallback(wholeMatch, pre, attr) {
            return '<p>~XX' + (self.hashBlocks.push(attr) - 1) + 'XX</p>\n' + pre + "\n";
        }

        return text.replace(fcbAttributes, attributeCallback);
    };

    Markdown.Extra.prototype.applyAttributeBlocks = function (text) {
        var self = this;
        var blockRe = new RegExp('<p>~XX(\\d+)XX</p>[\\s]*' +
            '(?:<(h[1-6]|pre)(?: +class="(\\S+)")?(>[\\s\\S]*?</\\2>))', "gm");
        text = text.replace(blockRe, function (wholeMatch, k, tag, cls, rest) {
            if (!tag)
                return '';

            var key = parseInt(k, 10);
            var attributes = self.hashBlocks[key];
            var id = attributes.match(/#[^\s#.]+/g) || [];
            var idStr = id[0] ? ' id="' + id[0].substr(1, id[0].length - 1) + '"' : '';
            var classes = attributes.match(/\.[^\s#.]+/g) || [];
            for (var i = 0; i < classes.length; i++) classes[i] = classes[i].substr(1, classes[i].length - 1);
            var classStr = '';
            if (cls)
                classes = union(classes, [cls]);

            if (classes.length > 0)
                classStr = ' class="' + classes.join(' ') + '"';

            return "<" + tag + idStr + classStr + rest;
        });

        return text;
    };


    Markdown.Extra.prototype.tables = function (text) {
        var self = this;

        var leadingPipe = new RegExp(
            ['^', '[ ]{0,3}', '[|]', '(.+)\\n', '[ ]{0,3}', '[|]([ ]*[-:]+[-| :]*)\\n',
                '(', '(?:[ ]*[|].*\\n?)*', ')', '(?:\\n|$)'].join(''), 'gm'
        );

        var noLeadingPipe = new RegExp(
            ['^', '[ ]{0,3}', '(\\S.*[|].*)\\n', '[ ]{0,3}', '([-:]+[ ]*[|][-| :]*)\\n',
                '(', '(?:.*[|].*\\n?)*', ')', '(?:\\n|$)'].join(''), 'gm'
        );

        text = text.replace(leadingPipe, doTable);
        text = text.replace(noLeadingPipe, doTable);


        function doTable(match, header, separator, body, offset, string) {
            header = header.replace(/^ *[|]/m, '');
            separator = separator.replace(/^ *[|]/m, '');
            body = body.replace(/^ *[|]/gm, '');
            header = header.replace(/[|] *$/m, '');
            separator = separator.replace(/[|] *$/m, '');
            body = body.replace(/[|] *$/gm, '');
            alignspecs = separator.split(/ *[|] */);
            align = [];
            for (var i = 0; i < alignspecs.length; i++) {
                var spec = alignspecs[i];
                if (spec.match(/^ *-+: *$/m))
                    align[i] = ' style="text-align:right;"';
                else if (spec.match(/^ *:-+: *$/m))
                    align[i] = ' style="text-align:center;"';
                else if (spec.match(/^ *:-+ *$/m))
                    align[i] = ' style="text-align:left;"';
                else align[i] = '';
            }

            var headers = header.split(/ *[|] */);
            var colCount = headers.length;

            var cls = self.tableClass ? ' class="' + self.tableClass + '"' : '';
            var html = ['<table', cls, '>\n', '<thead>\n', '<tr>\n'].join('');

            for (i = 0; i < colCount; i++) {
                var headerHtml = convertSpans(trim(headers[i]), self);
                html += ["  <th", align[i], ">", headerHtml, "</th>\n"].join('');
            }
            html += "</tr>\n</thead>\n";
            var rows = body.split('\n');
            for (i = 0; i < rows.length; i++) {
                if (rows[i].match(/^\s*$/))
                    continue;
                var rowCells = rows[i].split(/ *[|] */);
                var lenDiff = colCount - rowCells.length;
                for (var j = 0; j < lenDiff; j++)
                    rowCells.push('');

                html += "<tr>\n";
                for (j = 0; j < colCount; j++) {
                    var colHtml = convertSpans(trim(rowCells[j]), self);
                    html += ["  <td", align[j], ">", colHtml, "</td>\n"].join('');
                }
                html += "</tr>\n";
            }

            html += "</table>\n";

            return self.hashExtraBlock(html);
        }

        return text;
    };


    Markdown.Extra.prototype.stripFootnoteDefinitions = function (text) {
        var self = this;

        text = text.replace(
            /\n[ ]{0,3}\[\^(.+?)\]\:[ \t]*\n?([\s\S]*?)\n{1,2}((?=\n[ ]{0,3}\S)|$)/g,
            function (wholeMatch, m1, m2) {
                m1 = slugify(m1);
                m2 += "\n";
                m2 = m2.replace(/^[ ]{0,3}/g, "");
                self.footnotes[m1] = m2;
                return "\n";
            });

        return text;
    };


    Markdown.Extra.prototype.doFootnotes = function (text) {
        var self = this;
        if (self.isConvertingFootnote === true) {
            return text;
        }

        var footnoteCounter = 0;
        text = text.replace(/\[\^(.+?)\]/g, function (wholeMatch, m1) {
            var id = slugify(m1);
            var footnote = self.footnotes[id];
            if (footnote === undefined) {
                return wholeMatch;
            }
            footnoteCounter++;
            self.usedFootnotes.push(id);
            var html = '<a href="#fn:' + id + '" id="fnref:' + id
                + '" title="See footnote" class="footnote">' + footnoteCounter
                + '</a>';
            return self.hashExtraInline(html);
        });

        return text;
    };

    Markdown.Extra.prototype.printFootnotes = function (text) {
        var self = this;

        if (self.usedFootnotes.length === 0) {
            return text;
        }

        text += '\n\n<div class="footnotes">\n<hr>\n<ol>\n\n';
        for (var i = 0; i < self.usedFootnotes.length; i++) {
            var id = self.usedFootnotes[i];
            var footnote = self.footnotes[id];
            self.isConvertingFootnote = true;
            var formattedfootnote = convertSpans(footnote, self);
            delete self.isConvertingFootnote;
            text += '<li id="fn:'
                + id
                + '">'
                + formattedfootnote
                + ' <a href="#fnref:'
                + id
                + '" title="Return to article" class="reversefootnote">&#8617;</a></li>\n\n';
        }
        text += '</ol>\n</div>';
        return text;
    };


    Markdown.Extra.prototype.fencedCodeBlocks = function (text) {
        function encodeCode(code) {
            code = code.replace(/&/g, "&amp;");
            code = code.replace(/</g, "&lt;");
            code = code.replace(/>/g, "&gt;");
            code = code.replace(/~D/g, "$$");
            code = code.replace(/~T/g, "~");
            return code;
        }

        var self = this;
        text = text.replace(/(?:^|\n)```(.*)\n([\s\S]*?)\n```/g, function (match, m1, m2) {
            var language = m1, codeblock = m2;
            var preclass = self.googleCodePrettify ? ' class="prettyprint"' : '';
            var codeclass = '';
            if (language) {
                if (self.googleCodePrettify || self.highlightJs) {
                    codeclass = ' class="language-' + language + '"';
                } else {
                    codeclass = ' class="' + language + '"';
                }
            }
            var html = ['<pre', preclass, '><code', codeclass, '>',
                encodeCode(codeblock), '</code></pre>'].join("");
            return self.hashExtraBlock(html);
        });
        return text;
    };


    Markdown.Extra.prototype.educatePants = function (text) {
        var self = this;
        var result = '';
        var blockOffset = 0;
        text.replace(/(?:<!--[\s\S]*?-->)|(<)([a-zA-Z1-6]+)([^\n]*?>)([\s\S]*?)(<\/\2>)/g, function (wholeMatch, m1, m2, m3, m4, m5, offset) {
            var token = text.substring(blockOffset, offset);
            result += self.applyPants(token);
            self.smartyPantsLastChar = result.substring(result.length - 1);
            blockOffset = offset + wholeMatch.length;
            if (!m1) {
                result += wholeMatch;
                return;
            }
            if (!/code|kbd|pre|script|noscript|iframe|math|ins|del|pre/i.test(m2)) {
                m4 = self.educatePants(m4);
            }
            else {
                self.smartyPantsLastChar = m4.substring(m4.length - 1);
            }
            result += m1 + m2 + m3 + m4 + m5;
        });
        var lastToken = text.substring(blockOffset);
        result += self.applyPants(lastToken);
        self.smartyPantsLastChar = result.substring(result.length - 1);
        return result;
    };

    function revertPants(wholeMatch, m1) {
        var blockText = m1;
        blockText = blockText.replace(/&\#8220;/g, "\"");
        blockText = blockText.replace(/&\#8221;/g, "\"");
        blockText = blockText.replace(/&\#8216;/g, "'");
        blockText = blockText.replace(/&\#8217;/g, "'");
        blockText = blockText.replace(/&\#8212;/g, "---");
        blockText = blockText.replace(/&\#8211;/g, "--");
        blockText = blockText.replace(/&\#8230;/g, "...");
        return blockText;
    }

    Markdown.Extra.prototype.applyPants = function (text) {
        text = text.replace(/``/g, "&#8220;").replace(/''/g, "&#8221;");
        text = text.replace(/---/g, "&#8212;").replace(/--/g, "&#8211;");
        text = text.replace(/\.\.\./g, "&#8230;").replace(/\.\s\.\s\./g, "&#8230;");
        if (/^'$/.test(text)) {
            // Special case: single-character ' token
            if (/\S/.test(this.smartyPantsLastChar)) {
                return "&#8217;";
            }
            return "&#8216;";
        }
        if (/^"$/.test(text)) {
            // Special case: single-character " token
            if (/\S/.test(this.smartyPantsLastChar)) {
                return "&#8221;";
            }
            return "&#8220;";
        }
        text = text.replace(/^'(?=[!"#\$\%'()*+,\-.\/:;<=>?\@\[\\]\^_`{|}~]\B)/, "&#8217;");
        text = text.replace(/^"(?=[!"#\$\%'()*+,\-.\/:;<=>?\@\[\\]\^_`{|}~]\B)/, "&#8221;");
        text = text.replace(/"'(?=\w)/g, "&#8220;&#8216;");
        text = text.replace(/'"(?=\w)/g, "&#8216;&#8220;");
        text = text.replace(/'(?=\d{2}s)/g, "&#8217;");
        text = text.replace(/(\s|&nbsp;|--|&[mn]dash;|&\#8211;|&\#8212;|&\#x201[34];)'(?=\w)/g, "$1&#8216;");
        text = text.replace(/([^\s\[\{\(\-])'/g, "$1&#8217;");
        text = text.replace(/'(?=\s|s\b)/g, "&#8217;");
        text = text.replace(/'/g, "&#8216;");
        text = text.replace(/(\s|&nbsp;|--|&[mn]dash;|&\#8211;|&\#8212;|&\#x201[34];)"(?=\w)/g, "$1&#8220;");
        text = text.replace(/([^\s\[\{\(\-])"/g, "$1&#8221;");
        text = text.replace(/"(?=\s)/g, "&#8221;");
        text = text.replace(/"/ig, "&#8220;");
        return text;
    };


    Markdown.Extra.prototype.runSmartyPants = function (text) {
        this.smartyPantsLastChar = '';
        text = this.educatePants(text);
        text = text.replace(/(<([a-zA-Z1-6]+)\b([^\n>]*?)(\/)?>)/g, revertPants);
        return text;
    };


    Markdown.Extra.prototype.definitionLists = function (text) {
        var wholeList = new RegExp(
            ['(\\x02\\n?|\\n\\n)', '(?:', '(', '(', '[ ]{0,3}', '((?:[ \\t]*\\S.*\\n)+)',
                '\\n?', '[ ]{0,3}:[ ]+', ')', '([\\s\\S]+?)', '(', '(?=\\0x03)', '|', '(?=',
                '\\n{2,}', '(?=\\S)', '(?!', '[ ]{0,3}', '(?:\\S.*\\n)+?', '\\n?',
                '[ ]{0,3}:[ ]+', ')', '(?!', '[ ]{0,3}:[ ]+', ')', ')', ')', ')', ')'
            ].join(''), 'gm'
        );

        var self = this;
        text = addAnchors(text);
        text = text.replace(wholeList, function (match, pre, list) {
            var result = trim(self.processDefListItems(list));
            result = "<dl>\n" + result + "\n</dl>";
            return pre + self.hashExtraBlock(result) + "\n\n";
        });

        return removeAnchors(text);
    };

    Markdown.Extra.prototype.processDefListItems = function (listStr) {
        var self = this;

        var dt = new RegExp(
            ['(\\x02\\n?|\\n\\n+)', '(', '[ ]{0,3}', '(?![:][ ]|[ ])', '(?:\\S.*\\n)+?', ')',
                '(?=\\n?[ ]{0,3}:[ ])'].join(''), 'gm'
        );

        var dd = new RegExp(
            ['\\n(\\n+)?', '(', '[ ]{0,3}', '[:][ ]+', ')', '([\\s\\S]+?)', '(?=\\n*', '(?:',
                '\\n[ ]{0,3}[:][ ]|', '<dt>|\\x03', ')', ')'
            ].join(''), 'gm'
        );

        listStr = addAnchors(listStr);
        listStr = listStr.replace(/\n{2,}(?=\\x03)/, "\n");
        listStr = listStr.replace(dt, function (match, pre, termsStr) {
            var terms = trim(termsStr).split("\n");
            var text = '';
            for (var i = 0; i < terms.length; i++) {
                var term = terms[i];
                term = convertSpans(trim(term), self);
                text += "\n<dt>" + term + "</dt>";
            }
            return text + "\n";
        });

        listStr = listStr.replace(dd, function (match, leadingLine, markerSpace, def) {
            if (leadingLine || def.match(/\n{2,}/)) {
                def = Array(markerSpace.length + 1).join(' ') + def;
                def = outdent(def) + "\n\n";
                def = "\n" + convertAll(def, self) + "\n";
            } else {
                def = rtrim(def);
                def = convertSpans(outdent(def), self);
            }
            return "\n<dd>" + def + "</dd>\n";
        });

        return removeAnchors(listStr);
    };


    Markdown.Extra.prototype.strikethrough = function (text) {
        return text.replace(/([\W_]|^)~T~T(?=\S)([^\r]*?\S[\*_]*)~T~T([\W_]|$)/g,
            "$1<del>$2</del>$3");
    };

    Markdown.Extra.prototype.newlines = function (text) {
        return text.replace(/(<(?:br|\/li)>)?\n/g, function (wholeMatch, previousTag) {
            return previousTag ? wholeMatch : " </p><p>";
        });
    };
})();


