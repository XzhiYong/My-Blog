$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/blogs/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'blogId', index: 'blogId', width: 50, key: true, align: "center", hidden: true},
            {label: '标题', name: 'blogTitle', index: 'blogTitle', width: 140, align: "center"},
            {
                label: '预览图',
                name: 'blogCoverImage',
                index: 'blogCoverImage',
                width: 120,
                formatter: coverImageFormatter,
                align: "center"
            },
            {label: '浏览量', name: 'blogViews', index: 'blogViews', width: 60, align: "center",},
            {
                label: '状态',
                name: 'blogStatus',
                index: 'blogStatus',
                width: 60,
                formatter: statusFormatter,
                align: "center"
            },
            {label: '作者', name: 'adminUser.loginUserName', index: 'loginUserName', width: 60, align: "center"},
            {label: '博客分类', name: 'blogCategoryName', index: 'blogCategoryName', width: 60, align: "center"},
            {label: '添加时间', name: 'createTime', index: 'createTime', width: 90, align: "center"}
        ],
        height: 700,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"120\" width=\"160\" alt='coverImage'/>";
    }

    function statusFormatter(cellvalue) {
        //0-草稿 1-待审核 2-审核通过 3-已拒绝 4-已下架
        if (cellvalue === 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">草稿</button>";
        } else if (cellvalue === 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%; background: #0C9A9A\">待审核</button>";
        } else if (cellvalue === 2) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">审核通过</button>";
        } else if (cellvalue === 3) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%; background: #9d1e15\">已拒绝</button>";
        } else if (cellvalue === 4) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%; background: #9d1e15\">已下架</button>";
        }
    }

});

/**
 * 搜索功能
 */
function search() {
    alert("111")
    //标题关键字
    var keyword = $('#keyword').val();
    if (!validLength(keyword, 20)) {
        swal("搜索字段长度过大!", {
            icon: "error",
        });
        return false;
    }
    //数据封装
    var searchData = {keyword: keyword};
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/admin/blogs/list'}).trigger("reloadGrid");
}

/**
 * jqGrid重新加载
 */
function reload() {
    console.log(111)
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function addBlog() {
    window.location.href = "/admin/blogs/edit";
}

function editBlog() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/blogs/edit/" + id;
}

function deleteBlog() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/blogs/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}

function updateStatus() {

    const options = $("#locked option:selected");
    var status = options.val();
    var ids = getSelectedRows();
    var data = {"ids": ids, "status": status}
    $.ajax({
        type: "POST",
        url: "/admin/blogs/updateStatus",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (r) {
            if (r.resultCode === 200) {
                swal("修改成功", {
                    icon: "success",
                });
                $('#updateStatusModel').modal('hide');
                reload();
            } else {
                swal(r.message, {
                    icon: "error",
                });
            }
        }
    });

}


