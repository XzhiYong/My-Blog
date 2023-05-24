var devUrl = "ws://192.168.3.182:5689/";
var prodUrl = "ws://127.0.0.1:9898/api/websocket/100";
var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    //改成你的地址
    websocket = new WebSocket(devUrl);
} else {
    alert('当前浏览器 Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function () {
    console.log("WebSocket连接发生错误")
};

//连接成功建立的回调方法
websocket.onopen = function () {
    console.log("WebSocket连接成功");
}
//接收到消息的回调方法
websocket.onmessage = function (event) {

    $.ajax({
        type: 'GET',//方法类型
        url: "/msg/count",
        success: function (result) {
            $(".count").text(result);
        }
    });
}

//连接关闭的回调方法
websocket.onclose = function () {
    console.log("WebSocket连接关闭");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}
