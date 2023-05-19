$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/sys-sign/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '名称', name: 'name', index: 'name', width: 180},
            {
                label: '重置条件',
                name: 'reset',
                index: 'reset',
                width: 200,
                formatter: function (value, options, rowObject) {
                    if (value === 1) {
                        return '<span class="label label-warning" >重置为第一天</span>';
                    } else {
                        return '<span class="label label-warning">按最后一天</span>';
                    }
                }
            },
            {label: '触发天数', name: 'day', index: 'day', width: 120,},
            {
                label: '状态', name: 'state', index: 'state', width: 120,
                formatter: function (value, options, rowObject) {
                    if (value === 1) {
                        return '<span class="label label-warning" >生效</span>';
                    } else {
                        return '<span class="label label-warning">禁用</span>';
                    }
                }
            },
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 120,},
            {
                label: '操作',
                name: 'Operate',
                width: 80,
                align: 'center',
                edittype: "button",
                formatter: function (cellvalue, options, rowObject) {

                    return "<button class='btn btn-danger' style='padding:3px 12px; background: #0C9A9A; border-color : #0C9A9A' data-rowid='" + options.rowId + "' onclick='update(" + options.rowId + ")'>修改</button>"
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


