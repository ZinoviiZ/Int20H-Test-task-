document.addEventListener("DOMContentLoaded", postTeleprogramm);

function postTeleprogramm() {
     App = {};
    (function (App) {
        const connectionParams = {
            client_id: 5884363,
            display: 'page',
            redirect_uri: 'http://localhost:8080/create',
            scope: 'groups,wall,photos',
            response_type: 'code',
            v: 5.62
        };
        App.init = () => {
            App.$container = document.getElementById('container');
            App.$createButton = document.getElementById('create-btn');
            App.$postButton = document.getElementById('post-btn');
            App.$img = document.createElement('img');
            App.$img.src = 'http://localhost:8080/get_tv_program';

            App.$createButton.addEventListener("click", App.loadImg);
            App.$postButton.addEventListener("click", App.postImg);
        };

        App.loadImg = () => {
            App.$container.appendChild(App.$img);
            App.$container.removeChild(App.$createButton);
            App.$postButton.hidden = false;
        };

        App.postImg = () => {
            location.href = 'https://oauth.vk.com/authorize' + App.formatParams(connectionParams);
        };

        App.formatParams = (params) => `?${Object.keys(params).map((key) => `${key}=${params[key]}`).join('&')}`;

        App.init();
    })(App, document);
}