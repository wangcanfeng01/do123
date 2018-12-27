/**
 * 显示选中标签
 */
$(".select2").select2({
    width: '100%',
    maximumSelectionLength: 1,
    message: "只能选择一个分类"
});

$("#keywords").tagsInput({
    width: '100%',
    height: '45px',
    defaultText: '请输入文章关键字'
});

var tale = new $.tale();

var textarea = $('#text');
var toolbar = $('<div class="md-button-bar" id="md-button-bar" />').insertBefore(textarea);
var preview = $('<div id="md-preview" class="md-hidetab" />').insertAfter(textarea.parent());
var isCode = false;
markdown(textarea, toolbar, preview, isCode);

function markdown(textarea, toolbar, preview, isCode) {
    var options = {};
    options.strings = {
        bold: '加粗 <strong> Ctrl+B',
        boldexample: '加粗文字',

        italic: '斜体 <em> Ctrl+I',
        italicexample: '斜体文字',

        link: '链接 <a> Ctrl+L',
        linkdescription: '请输入链接描述',

        quote: '引用 <blockquote> Ctrl+Q',
        quoteexample: '引用文字',

        code: '代码 <pre><code> Ctrl+K',
        codeexample: '请输入代码',

        image: '图片 <img> Ctrl+G',
        imagedescription: '请输入图片描述',

        olist: '数字列表 <ol> Ctrl+O',
        ulist: '普通列表 <ul> Ctrl+U',
        litem: '列表项目',

        heading: '标题 <h1>/<h2> Ctrl+H',
        headingexample: '标题文字',
        hr: '分割线 <hr> Ctrl+R',
        undo: '撤销 - Ctrl+Z',
        redo: '重做 - Ctrl+Y',
        redomac: '重做 - Ctrl+Shift+Z',
        imagedialog: '请选择需要上传的图片',
        linkdialog: '<p><b>插入链接</b></p><p>请在下方的输入框内输入要插入的链接地址</p>',
        ok: '确定',
        cancel: '取消',
        save: '保存',
        fullscreen: '切换到预览模式',
        exitFullscreen: '退出预览模式'
    };
    var converter = new Markdown.Converter(),
        editor = new Markdown.Editor(converter, '', options);
    var span = '<span class="diff" />';
    // 设置markdown
    Markdown.Extra.init(converter, {
        extensions: ["tables", "fenced_code_gfm", "def_list", "attr_list", "footnotes"], highlighter: "highlight"
    });
    editor.run();

    $(".md-button-row li").click(function () {
        //保存功能
        var selected_tab = $(this).attr("id");
        if (selected_tab == "md-save-button") {
            subArticle('')
        }
    });
}


/**
 * 提交文章
 * @param status
 */
function subArticle(status) {
    var categories = $('#multiple-sel').val();
    if (categories == null || categories == '') {
        tale.alertWarn('请选择分类标签');
        return;
    }
    $('#articleForm #categories').val(categories);
    var title = $('#title').val();
    if (title == null || title == '') {
        tale.alertWarn('请输入文章标题');
        return;
    }

    var text = $('#text').val();
    if (text == null || text == '') {
        tale.alertWarn('文章内容不能为空');
        return;
    }

    var allowComment = $('#comment').prop("checked");
    var allowSee = $('#see').prop("checked");

    if (allowComment && allowComment == true) {
        $('#articleForm #allowComment').val(allowComment);
    } else {
        $('#articleForm #allowComment').val('false');
    }

    if (allowSee && allowSee == true) {
        $('#articleForm #allowSee').val('public');
    } else {
        $('#articleForm #allowSee').val('private');
    }
    var url;
    //获取文章状态
    var isSave;
    var articleStatus = $('#articleForm #status').val();
    if (articleStatus == null || articleStatus == '') {
        url = '/read/admin/article/write';
    } else {
        if(status==null||status==''){
            status=articleStatus;
            isSave=true;
        }
        url = '/read/admin/article/modify';
    }
    $('#articleForm #status').val(status);

    var params = $('#articleForm').serialize();
    tale.post({
        url: url,
        data: params,
        success: function (result) {
            if (result && result.success) {
                tale.alertOk({
                    text: '文章发布成功',
                    then: function () {
                        setTimeout(function () {
                            if (isSave) {
                                window.location.reload();
                            } else {
                                window.location.href = '/read/admin/management';
                            }
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '文章发布失败');
            }
        }
    });
};