/**
 * CreateGroupController
 *
 * The service which makes REST calls to the server to receive the user's security token.
 *
 *
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 *
 */
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
