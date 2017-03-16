(function($) {
	$.WebSocket = function(url, options) {
		// 如果没有传递options，使用默认的
		var opts = options;
		var ws = null;
		var isOpen = false;
		var wsServer = "ws://localhost:8080/websocket.ws";
		if(url && url.trim() != "") {
			wsServer = url;
		}
		
		var wsEvents = {
			onInit: function() {
				if("WebSocket" in window) {
					ws = new WebSocket(wsServer);
				} else if("MozWebSocket" in window) {
					ws = new MozWebSocket(wsServer);
				} else {
					alert("The current brower is unsupport websocket!")
					return false;
				}

				// 如果opts里边有用户传递的onInit方法，执行，注意:该回调方法不是说取代默认的初始化方法，
				// 而是说在初始化完成后(实例化ws后)，执行一些用户自定义的代码
				if(opts.onInit) {
					opts.onInit();
				}
			},
			onMessage: function(event) {
				// 接收到服务器的消息回传后，会调用该方法
				if(opts.onMessage) {
					opts.onMessage(event.data);
				}
			},
			onOpen: function(event) {
				isOpen = true;
				// 执行用户传递的打开链路事件的回调方法
				if(opts.onOpen) {
					opts.onOpen(event);
				}
			},
			onError: function(event) {
				// 发生错误的时候调用
				if(opts.onError) {
					opts.onError(event);
				}
			},
			onSend: function(data) {
				// 该函数可以在第一次握手成功后，向服务器发送一些初始的数据
				if(!isOpen) {
					alert("Connection has been disconnected!")
					return false;
				}
				if(opts.onSend) {
					opts.onSend(data);
				}

				ws.send(data);
				return true;
			},
			onClose: function() {
				
				if(opts.onClose) {
					opts.onClose();
				}
			}
		}; // end of wsEvents
		wsEvents.onInit();
		ws.onopen = wsEvents.onOpen;
		ws.onmessage = wsEvents.onMessage;
		ws.onerror = wsEvents.onError;
		ws.onclose = wsEvents.onClose;
		// 对外提供的send方法，用来发送数据
		this.send = function(data) {
			return wsEvents.onSend(data);
		}
		// 对外提供的close方法，用来关闭链路
		this.close = function() {
			if(ws != null) {
				ws.close();
				ws = null;
				isOpen = false;
			}
		}
		return this;
	};
})(jQuery)