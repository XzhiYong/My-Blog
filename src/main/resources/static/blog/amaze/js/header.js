layui.use(function () {
    var dropdown = layui.dropdown;

    // 自定义事件
    dropdown.render({
        elem: '.demo-dropdown-on',
        // trigger: 'click' // trigger 已配置在元素 `lay-options` 属性上
        data: [{
            title: '我的主页',
            id: 100,
            templet: '<span><i class="layui-icon">&#xe68e;</i>&nbsp;&nbsp;&nbsp;我的主页</span>',
            href: '/home'
        }, {
            title: '我的消息',
            templet: '<span><i class="layui-icon">&#xe667;</i>&nbsp;&nbsp;&nbsp;我的消息</span>',
            id: 101,
            href: '/msg/index'
        }, {
            title: '个人设置',
            id: 102,
            templet: '<span><i class="layui-icon">&#xe620;</i>&nbsp;&nbsp;&nbsp;个人设置</span>',
            href: '/set'
        }, {
            title: '积分商城',
            id: 103,
            templet: '<span><i class="layui-icon">&#xe65e;</i>&nbsp;&nbsp;&nbsp;积分商城</span>',
            href: '/shopping'
        }, {
            title: '我的资源',
            id: 104,
            templet: '<span><i class="layui-icon">&#xe674;</i>&nbsp;&nbsp;&nbsp;我的资源</span>',
            href: '/shopping?type=2'
        }, {
            title: '退出登录',
            id: 105,
            templet: '<span><i class="layui-icon">&#x1007;</i>&nbsp;&nbsp;&nbsp;退出登录</span>',
            href: '/admin/logout'
        }]
    });

});

//签到说明
function signinHelp() {
    layer.open({
        type: 1
        , title: '签到说明'
        , area: '300px'
        , shade: 0.8
        , shadeClose: true
        , content: ['<div class="layui-text" style="padding: 20px;">'
            , '<blockquote class="layui-elem-quote">“签到”可获得社区飞吻，规则如下</blockquote>'
            , '<table class="layui-table">'
            , '<thead>'
            , '<tr><th>连续签到天数</th><th>每天可获飞吻</th></tr>'
            , '</thead>'
            , '<tbody>'
            , '<tr><td>0-5</td><td>50</td></tr>'
            , '<tr><td>5-10</td><td>100</td></tr>'
            , '<tr><td>10-15</td><td>150</td></tr>'
            , '<tr><td>15-20</td><td>200</td></tr>'
            , '<tr><td>20-30</td><td>250</td></tr>'
            , '<tr><td>≥30</td><td>300</td></tr>'
            , '</tbody>'
            , '</table>'
            , '<ul>'
            , '<li>中间若有间隔，则连续天数重新计算</li>'
            , '<li style="color: #9d1e15">这个玩意可以用来干嘛呢，可以换网页背景，鼠标点击效果等等..在哪换？敬请期待</li>'
            // , '<li style="color: #FF5722;">不可利用程序自动签到，否则飞吻清零</li>'
            , '</ul>'
            , '</div>'].join('')
    });
}

function signinLog() {

    layui.layer.open({  // 打开弹出框
        type: 1,  // 这个很关键！1为页面层
        title: '签到记录',
        shadeClose: true,  //开启遮罩关闭
        // shade: 0.8,
        area: ['30%', '60%'],
        maxmin: true,
        content: '<div class="layui-container" style="width: 98%"><table id="transfer" lay-filter="transfer" class=""></table></div>',
        // 弹出层表格定义
        success: function () {
            layui.table.render({
                elem: '#transfer'  // 弹出层表格id
                , url: "/sign/log"// 后端请求URL地址
                // ,method: 'post'
                , page: true
                // 以下为transfer表格的定义
                , cols: [[
                    {
                        field: 'createTime',
                        title: '打卡时间',
                        width: 200,
                        templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy年MM月dd日 HH:mm:ss')}}</div>"
                    }
                    , {field: 'point', title: '奖励积分'}

                ]]
                , response: {
                    statusName: 'resultCode' //规定数据状态的字段名称，默认：code
                    , statusCode: 200 //规定成功的状态码，默认：0
                    , msgName: 'message' //规定状态信息的字段名称，默认：msg
                    , countName: 'total' //规定数据总数的字段名称，默认：count
                    , dataName: 'data' //规定数据列表的字段名称，默认：data
                }
            })
        }
    });
}

function signIn() {
    $.ajax({
        type: "POST",
        url: "/sign/in",
        success: function (r) {
            if (r.resultCode === 200) {
                layer.msg("签到成功", {icon: 1});
                location.reload();
            } else {
                console.log("111")
                layer.msg(r.message, {icon: 5});

            }
        }
    });
}
