
$(function(){
    var me = {};

    var socket = new WebSocket('ws://' + location.host + '/chatServer');
    socket.addEventListener('open', function (event) {
        me.id = event.data;
        $('#start').prop('disabled',false);
        console.log(event);
        // socket.send('Hello Server!');
        // socket.send(JSON.stringify({type:'Login',content:'content'}));
    });

    // Listen for messages
    socket.addEventListener('message', function (event) {
        console.log('Message from server', event.data);
        var message = JSON.parse(event.data);
        switch (message.to){
            case 'setid':
                me.id = message.content;
                console.log(me)
                break;
            case me.id:
                show(message.from,message.content);
            case 'All':
                show(message.from,message.content);

        }
    });

    $('#start').click(function () {
        var name = $('#nameInput').val();
        me.name = name;
        socket.send(JSON.stringify({type:'Login',content:name}));
        $('#login').hide();
    });

    function show(from,content) {
        console.log(content)
    }
});