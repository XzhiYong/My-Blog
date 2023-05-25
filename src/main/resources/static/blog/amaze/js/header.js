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
            , '<tr><td>0-5</td><td>5</td></tr>'
            , '<tr><td>5-10</td><td>10</td></tr>'
            , '<tr><td>10-15</td><td>15</td></tr>'
            , '<tr><td>15-20</td><td>20</td></tr>'
            , '<tr><td>20-30</td><td>25</td></tr>'
            , '<tr><td>≥30</td><td>30</td></tr>'
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
