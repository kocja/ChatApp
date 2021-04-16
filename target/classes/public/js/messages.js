const messages_endpoint ='http://localhost:8080/api/message';

function mapResponseIfNoError(response) {
    return response.ok ? response.json() : response.json().then(error => Promise.reject(error));
}

function getAllMessages() {
    return fetch(messages_endpoint)
        .then(mapResponseIfNoError);
}
// Creates a message via WebSocket

function createMessageWS(userId, newMessage) {
    const body = {
        user_id: userId,
        message: newMessage
    };
    ws.send(JSON.stringify(body));
}

// Creates a message via REST
function createMessage(userId, newMessage) {
    const body = {
        user_id: userId,
        message: newMessage
    };

    return fetch(messages_endpoint, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(body)
    })
        .then(response => response.ok ? response.json() : response.json().then(Promise.reject))
        .then(message => message.id)
}
