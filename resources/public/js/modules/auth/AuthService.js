define([], function () {
    var routes = {
        login: '/api/auth',
        createAccount: '/api/users'
    };
    return [
        '$http',
        function ($http) {

            var srvc = function () {

                this.login = function (form) {
                    return $http.post(routes.login, form);
                };

                this.createAccount = function (form) {
                    var req = {
                        user:form
                    };
                    return $http.post(routes.createAccount, req);
                };

            };

            return new srvc();
        }
    ];

});
