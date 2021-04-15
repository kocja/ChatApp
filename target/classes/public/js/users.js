const users_endpoint = 'http://localhost:8080/api/users';

function mapResponseIfNoError(response) {
    return response.ok
        ? response.json()
        : response.json().then(error => Promise.reject(error));
}

function getAllUsers() {
    return fetch(users_endpoint)
        .then(mapResponseIfNoError);
}

function getUserById(userId) {
    return userId
        ? fetch(users_endpoint + '/' + userId)
            .then(mapResponseIfNoError)
        : Promise.reject('userId mustn\'t be empty');
}

// New user via WebSocket
function createUser(nickName) {
    const body = {
        nickname: nickName,
        status: 'ONLINE',
        avatar: 1,
        updated: Date.now()
    };
    ws.send(JSON.stringify(body));
}


// New user via REST
function createUser(nickName) {
    console.log("createUser");
    const body = {
        nickname: nickName,
        status: 'ONLINE',
        avatar: 1,
        updated: Date.now()
    };
    return fetch(users_endpoint, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(body)
    })
        .then(mapResponseIfNoError)
        .then(user => user.id);
}

function updateUser(userUpdate) {
    return fetch(users_endpoint + '/' + userUpdate.id, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'PUT',
            body: JSON.stringify({
                id: userUpdate.id,
                status: userUpdate.status,
                avatar: userUpdate.avatar,
                nickname: userUpdate.nickname
            })
        }
    ).then(mapResponseIfNoError);
}
