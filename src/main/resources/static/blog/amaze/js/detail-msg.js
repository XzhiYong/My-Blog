var messageWall_range;  // 定义最后光标对象

function reply(obj) {
    var commentId = $(obj).data('commentid');
    var parentCommentId = $(obj).data('parentcommentid');
    var nickName = $(obj).data('nickname');
    console.log(commentId, parentCommentId)
    // alert(commentId)
    $("#parentCommentId").val(parentCommentId);
    $("#replyCommentId").val(commentId);
    $("#content_in_messageWall").attr("placeholder", "@" + nickName).focus();
}

$(function () {

    $("#back-top").hide();
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('#back-top').fadeIn();
        } else {
            $('#back-top').fadeOut();
        }
    });
    // scroll body to 0px on click
    $('#back-top a').click(function () {
        $('body,html').animate({
            scrollTop: 0
        }, 800);
        return false;
    });

    $('pre code').each(function (i, block) {
        hljs.highlightBlock(block);
    });

    $('article h1, article h2, article h3, article h4, article h5').find('a').removeAttr('target')

    $(".reply").click(function () {
        alert('addasfasf')
    })

    //初始化 emoji功能
    messageWall_initEmoji();

    //获取输入框的光标位置
    var editor = document.querySelector('#content_in_messageWall')
    editor.onclick = function () {
        // 获取选定对象
        var sel = window.getSelection();
        if (sel.rangeCount && sel.getRangeAt) {
            messageWall_range = sel.getRangeAt(0);
            messageWall_range.deleteContents()
        }
    }
    editor.onkeyup = function () {
        var sel = window.getSelection();
        if (sel.rangeCount && sel.getRangeAt) {
            // 设置最后光标对象
            messageWall_range = sel.getRangeAt(0);
            messageWall_range.deleteContents()
        }
    }
})

function deletecomment(obj) {

    var cid = $(obj).data('commentid');
    if (confirm("确定要删除改评论吗")) {
        $.ajax({
            type: "DELETE",
            url: "comment/delete/" + cid,
            success: function (r) {
                if (r.resultCode === 200) {
                    swal("删除成功", {
                        icon: "success",
                    }).then(() => {
                        location.reload();
                    })
                } else {
                    swal(r.message, {
                        icon: "error",
                    })

                }
            }
        });
    }

}

function nextpage(obj) {
    var page = $(obj).data('page');
    var pages = $(obj).data('pages');
    var state = $(obj).data('state');
    if (page >= pages) {
        swal(state === 1 ? "上面" : "下面" + "没有了，别再点了", {
            icon: "error"
        })
    }

    $.ajax({
        type: "GET",
        url: "comment/commentlist?blogId=" + [[${blogDetailVO.blogId}]] + "/page=" + page
    });
}

function messageWall_initEmoji() {
    //源码地址https://github.com/eshengsky/jQuery-emoji
    //初始化 emoji功能
    $("#content_in_messageWall").emoji({
        button: "#emoji_in_messageWall",
        showTab: false,
        animation: 'slide',
        position: 'topLeft',
        icons: [{
            name: "QQ表情",
            path: "/blog/img/emoji/",
            maxNum: 91,
            excludeNums: [30, 56, 68, 69],  //排除的表情
            file: ".gif"
        }]
    });
}

//保持原有的光标位置
function messageWall_keepOldRange() {
    messageWall_range.collapse(false);
    let sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(messageWall_range);
}

function messageWall_sendMessage(obj) {
    console.log(obj)

    var content = $("#content_in_messageWall").html();
    if (content == null || content.trim() === '') {
        alert('请填写内容');
        return;
    }
    var r = confirm("确定要发表此留言吗!");
    if (r === true) {
        var parentCommentId = $("#parentCommentId").val();
        var replyCommentId = $("#replyCommentId").val();
        var remind = $("#remind").prop("checked");
        var data = {
            'content': content,
            'parentCommentId': parentCommentId,
            'replyCommentId': replyCommentId,
            'remind': remind,
            'blogId': [[${blogDetailVO.blogId}]],
            'type': obj
        };
        $.ajax({
            type: "POST",
            url: "comment/postcomment",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (r) {
                if (r.resultCode === 200) {
                    swal("评论成功", {
                        icon: "success",
                    }).then((result) => {
                        location.reload();
                        clear();
                    })
                } else {
                    if (r.message === '未登录') {
                        location.href = "/admin/login";
                    }

                }
            }
        });
    }

    function clear() {
        $("#content").val('');
        $("#parentCommentId").val('-1');
        $('#replyCommentId').val('-1');
        $('#remind').removeAttr('checked');
        $("#content").attr('placeholder', '说点什么。。');
    }
}
