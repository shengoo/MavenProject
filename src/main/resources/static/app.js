
$(function(){
	var socket = new WebSocket('ws://' + location.host + '/webSocketServer');
	socket.addEventListener('open', function (event) {
		$('#status').text('connected');
		console.log('opened');
	    socket.send('Hello Server!');
	});

	// Listen for messages
	socket.addEventListener('message', function (event) {
	    console.log('Message from server', event.data);
		$('#data').text(event.data);
	});
});