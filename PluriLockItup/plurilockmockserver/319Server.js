var WebSocketServer = require('websocket').server;
var http = require('http');

var server = http.createServer(function(request, response) {
    // process HTTP request. Since we're writing just WebSockets server
    // we don't have to implement anything.
});
server.listen(8001, function() {console.log((new Date()) + " Server is listening on port " + 8001)});

// create the server
wsServer = new WebSocketServer({
    httpServer: server
});

// WebSocket server
wsServer.on('request', function(request) {
   console.log((new Date()) + ' Connection from origin ' + request.origin + '.');
   var connection = request.accept(null, request.origin);

    // This is the most important callback for us, we'll handle
    // all messages from users here.
    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            // process WebSocket message
	    connection.sendUTF(JSON.stringify({confidenceLevel: 0.1234}))
    }});

    connection.on('close', function(connection) {
        // close user connection
    });
});
