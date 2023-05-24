layui.use(function () {
    var dropdown = layui.dropdown;

    // 自定义事件
    dropdown.render({
        elem: '.demo-dropdown-on',
        // trigger: 'click' // trigger 已配置在元素 `lay-options` 属性上
        data: [{
            title: '个人设置',
            id: 102,
            href: '/set'
        }, {
            title: '我的主页',
            id: 100,
            href: '/home'
        }, {
            title: '我的消息',
            id: 101
        }]
    });

});
