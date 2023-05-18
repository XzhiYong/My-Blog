$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/user/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'adminUserId', index: 'adminUserId', width: 50, key: true, hidden: true},
            {
                label: '头像', name: 'headPortrait', index: 'headPortrait', width: 140, align: "center",
                formatter: function (value, options, rowObject) {
                    return '<img src=' + value + ' alt="" id="picture" style="height: 50px;width: 100px">';
                }
            },
            {label: '账号', name: 'loginUserName', index: 'loginUserName', width: 200, align: "center"},
            {label: '昵称', name: 'nickName', index: 'nickName', width: 120, align: "center"},
            {label: '手机号', name: 'mobile', index: 'mobile', width: 120, align: "center"},
            {label: '邮箱', name: 'email', index: 'email', width: 120, align: "center"},
            {
                label: '状态',
                name: 'locked',
                index: 'locked',
                width: 120,
                align: "center",
                formatter: function (value, options, rowObject) {
                    if (value === 0) {
                        return '<span class="label label-warning" >正常</span>';
                    } else {
                        return '<span class="label label-warning">锁定</span>';
                    }
                }
            },
            {label: 'ip', name: 'ip', index: 'ip', width: 120, align: "center"},
            {label: '登录次数', name: 'loginCount', index: 'loginCount', width: 120, align: "center"},
            {label: '上次登录时间', name: 'lastLoginTime', index: 'lastLoginTime', width: 120, align: "center"},
            {
                label: '操作',
                name: 'Operate',
                width: 80,
                align: 'center',
                edittype: "button",
                formatter: function (cellvalue, options, rowObject) {

                    return "<button class='btn btn-danger' style='padding:3px 12px; background: #0C9A9A; border-color : #0C9A9A' data-rowid='" + options.rowId + "' onclick='update(" + options.rowId + ")'>修改</button>"
                        + '&nbsp;&nbsp;' +
                        "<button class='btn btn-danger' style='padding:3px 12px; background: #0C9A9A; border-color : #0C9A9A' '  data-rowid='" + options.rowId + "' onclick='selectRole(" + options.rowId + ")'>授权</button>"
                        + '&nbsp;&nbsp;' +
                        "<button class='btn btn-danger' style='padding:3px 12px;'  data-rowid='" + options.rowId + "' onclick='DelFile(" + options.rowId + ")'>删除</button>";
                }
            },


        ],
        height: 560,
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
            page: "data.pageNum",
            total: "data.pageSize",
            records: "data.total"
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
});

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function addUser() {
    $('.modal-title').html('友链添加');
    $('#linkModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var userId = $("#adminUserId").val();
    var userName = $("#adminUserName").val();
    var nickName = $("#nickName").val();
    var locked = $("#locked").val();
    var password = $("#loginPassword").val();
    var mobile = $("#mobile").val();
    var email = $("#email").val();
    var $edit = $('#edit-error-msg');
    if (isNull(userName)) {

        $edit.css("display", "block");
        $edit.html("登录账号不能为空!");
        return;
    }
    if (isNull(nickName)) {
        $edit.css("display", "block");
        $edit.html("昵称不能为空!");
        return;
    }
    if (isNull(locked)) {
        $edit.css("display", "block");
        $edit.html("状态不能为空！");
        return;
    }

    var params = {
        "adminUserId": userId,
        "loginUserName": userName,
        "nickName": nickName,
        "locked": locked,
        "loginPassword": password,
        "mobile": mobile,
        "email": email
    }
    var url = '/admin/user';
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (result) {
            console.log(result)
            if (result.resultCode === 200 && result.data) {
                $('#myModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#myModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });

});

//事件
function DelFile(id) {
    $.ajax({
        type: 'DELETE',//方法类型
        url: "/admin/user/" + id,
        success: function (result) {
            console.log(result)
            if (result.resultCode === 200 && result.data) {
                swal("删除成功", {
                    icon: "success",
                });
                reload();
            } else {
                swal("删除失败", {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
}

function update(id) {
    //请求数据
    $.get("/admin/user/" + id, function (r) {

        if (r.resultCode === 200 && r.data != null) {
            //填充数据至modal
            $("#adminUserName").val(r.data.loginUserName);
            $("#nickName").val(r.data.nickName);
            $("#locked").val(r.data.locked);
            $("#adminUserId").val(r.data.adminUserId);
            $("#mobile").val(r.data.mobile);
            $("#email").val(r.data.email);
            //根据原linkType值设置select选择器为选中状态
            if (r.data.locked !== 0) {
                $("#locked option:eq(0)").prop("selected", 'selected');
            } else {
                $("#locked option:eq(1)").prop("selected", 'selected');
            }

        }
    });
    $('.modal-title').html('用户修改');
    $('#myModal').modal('show');
    $("#myModal").val(id);

}

var id;

function selectRole(id) {
    this.id = id;
    $('#roleModel').modal('show');
    $.ajax({
        type: 'GET',//方法类型
        url: "/admin/roles/userRoleList/" + id,
        success: function (result) {
            console.log(result)
            if (result.resultCode === 200 && result.data) {
                var rightArr = result.data.roles
                var leftArr = result.data.notRoles
                $("#shuttleBox").transferItem({
                    'leftName': '未授权',
                    'rightName': '已授权',
                    data: {
                        leftArr,
                        rightArr
                    }
                });
            } else {
                swal("删除失败", {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });

    return id;

}

function addRole() {
    var roleIds = [];
    var right = $("#right span")
    for (let i = 0; i < right.length; i++) {
        roleIds.push(right[i].id)
    }
    const data = {
        "userId": id,
        "roleIds": roleIds
    };
    $.ajax({
        type: 'POST',//方法类型
        url: "/admin/roles/saveUserRole",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode === 200 && result.data) {
                $('#roleModel').modal('hide');

                swal("保存成功", {
                    icon: "success",
                });

            } else {
                swal("保存失败", {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    })
    ;

}


