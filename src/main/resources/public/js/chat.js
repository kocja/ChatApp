let users = [];
let messages = [];

const storagePath = "/chat/user/local/";

function getAll() {
    Promise.all([
        getAllUsers(),
        getAllMessages()
    ]).then((values) => {
        initData(values[0], values[1])
    }).catch(console.error.bind(console))
}

// Wartet bis die Seite geladen ist, um die Funktion getAll anzuzeigen
document.addEventListener('DOMContentLoaded', () => {
    startWebSocket();
    getAll();

    const userId = localStorage.getItem('user-id');
    if (userId && userId !== '') {
        getUserById(userId)
            .catch(() => window.location.href = 'index.html');
    } else {
        window.location.href = 'index.html';
    }

    const messagesForm = document.getElementById('messageForm');
    if (messagesForm) {
        messagesForm.addEventListener('submit', ev => {
            ev.preventDefault();

            const textField = document.getElementById('message');
            const userId = localStorage.getItem('user-id');

            if (textField) {
                const newMessage = textField.value;
                if (newMessage) {
                    createMessageWS(userId, newMessage);
                }
            }
        })
    }
});

function initData(_users, _messages) {
    users = _users;
    messages = _messages;
    messages.forEach(message => {

    });
    users.forEach(addUser);
    messages.forEach(addMessages);
}

// Users
function addUser(user) {
    const li = document.createElement("li");
    li.setAttribute("id", user.id);
    if (localStorage.getItem('user-id') === user.id) {
        li.className = 'list-group-item d-flex active';
    } else {
        li.className = 'list-group-item d-flex';
    }

    const avatar = document.createElement("img")
    avatar.setAttribute('src', '/images/avatar_icon_' + user.avatar + '.svg');
    avatar.setAttribute('alt', 'Avatar' + user.avatar);
    avatar.setAttribute('style', 'width: 24px; height: 24px; filter: ' + (filtersByStatus[user.status] || ''));
    avatar.className = 'mr-1';
    li.appendChild(avatar);

    const nickname = document.createElement("span");
    nickname.appendChild(document.createTextNode(user.nickname));
    li.appendChild(nickname);

    document.getElementById("userList").appendChild(li);
}

function getUsersById(id) {
    return users.find(user => user.id === id).nickname;
}

//Messages
function addMessages(message) {
    const date = new Date(message.timestamp);
    message.timestamp = date.getDate() + "." + (date.getMonth() + 1) + " , " + date.getHours() + ":" + date.getMinutes();

    //Container with ID
    const box = document.createElement('div');
    box.setAttribute('id', message.id);
    box.setAttribute('class', 'card mb-2');

    // Header
    const header = document.createElement("div");
    header.setAttribute('class', 'card-header lead d-flex justify-content-between');
    box.appendChild(header);

    const userName = document.createElement("strong");
    userName.appendChild(document.createTextNode(getUsersById(message.user_id)));
    header.appendChild(userName);

    const timeStamp = document.createElement("small");
    timeStamp.appendChild(document.createTextNode(message.timestamp));
    timeStamp.setAttribute('class', 'text-muted');
    header.appendChild(timeStamp);

    const messageText = document.createElement("span");
    messageText.appendChild(document.createTextNode(message.message));
    messageText.setAttribute('class', 'card-body')
    box.appendChild(messageText);

    document.getElementById("messageList").appendChild(box);
    document.getElementById('message').value = '';
}

function getMessagesByUserId(userId) {
    return messages.filter(message => message.user_id === userId)
}

function messageDelete(user) {
    for (let i = 0; i < messages.length; i++) {
        if (messages[i].user_id === user.id) {
            document.getElementById(messages[i].id).remove();
        }
    }
}

// Update text when connected
function updateFooter(text) {
    const element = document.getElementById('pfooter');
    if (element) {
        element.textContent = text;
    }
}

function loginBanner(text) {
    const element = document.getElementById('loginbanner');
    if (element) {
        element.textContent = text;
    }
}

// Websocket
const ws = new WebSocket('ws://localhost:8080/ws');

function startWebSocket() {
    ws.onerror = event => console.error('WebSocket Error', event);
    ws.onmessage = event => handleMessage(event.data);
    ws.onopen = () => {
        updateFooter('Websocket connected!');
        loginBanner('Change username');
    }
    ws.onclose = () => updateFooter("Not connected!");
}

function handleMessage(input) {
    const jsonObject = JSON.parse(input);
    const action = jsonObject.action;
    switch (action) {
        case 'message_added':
            addMessages({
                id: jsonObject.data.id,
                timestamp: jsonObject.data.timestamp,
                user_id: jsonObject.data.user_id,
                message: jsonObject.data.message
            });
            break;
        case 'user_added':
            const newUser = {
                id: jsonObject.data.id,
                status: jsonObject.data.status,
                avatar: jsonObject.data.avatar,
                nickname: jsonObject.data.nickname
            }
            users.push(newUser);
            addUser(newUser);
            break;
        case 'user_updated':
            // Update username
            const rootElement = document.getElementById(jsonObject.data.id);
            const nicknameElement = rootElement.getElementsByTagName('span')[0];
            nicknameElement.textContent = jsonObject.data.nickname;
            // Update status
            const avatarElement = rootElement.getElementsByTagName('img')[0];
            avatarElement.setAttribute('style', 'width: 24px; height: 24px; filter: ' + (filtersByStatus[jsonObject.data.status] || ''));
            // Update avatar
            avatarElement.setAttribute('src', '/images/avatar_icon_' + jsonObject.data.avatar + '.svg');
            // TODO: Users push user updated
            break;
        case 'user_deleted':
            const element = document.getElementById(jsonObject.data.id);
            const elementUser = document.getElementById(jsonObject.data);
            const getMessage = getMessagesByUserId(element);

            if (element) {
                element.remove();
                messageDelete(elementUser);
            }
            // Delete his/hers messages
            break;
    }
}

// Adjust filter for status
const filtersByStatus = {
    'ONLINE': 'invert(21%) sepia(88%) saturate(3552%) hue-rotate(96deg) brightness(97%) contrast(103%)',
    'OFFLINE': 'invert(11%) sepia(67%) saturate(3947%) hue-rotate(353deg) brightness(94%) contrast(117%)',
    'AWAY': 'invert(56%) sepia(25%) saturate(6340%) hue-rotate(1deg) brightness(103%) contrast(105%)'
};
