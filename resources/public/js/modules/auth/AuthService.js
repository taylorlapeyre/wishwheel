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

                /**
                 * Logs the user in
                 * @param {object} form the user's login form (email & password)
                 * @returns {HttpPromise} resolves when the user receives a valid token,
                 * rejects when the credentials are bad.
                 */
                this.login = function (form) {
                    return $http.post(routes.login, form);
                };

                /**
                 * Attempts to create an account.
                 * @param form holds the user's information like email and password.
                 * @returns {HttpPromise} resolves if the user was created, rejects if the user was not created.
                 */
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
