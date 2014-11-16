/**
 * CreateGroupController
 *
 * The controller of the very first page the user hits, the homepage.
 *
 *
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 */

'use strict';
define([], function () {
    var statusCodes = {
        forbidden: 403
    };
    var messages = {
        forbidden: "Incorrect email or password"
    };
    return [
        "$scope",
        "$state",
        "$animate",
        'AuthService',

        function ($scope, $state, $animate, AuthService) {
            $scope.form = {};
            $scope.title = "Wish-list";
            $scope.RsideBar = false;

            /**
             * toggles the side-menu
             */
            $scope.showSideBar = function () {
                $scope.RsideBar = !$scope.RsideBar;
            };


            /**
             * Reads the form it's attached to and uses AuthService.login to log the user in.
             * If login was successful and a token is received,
             *  navigate to the user's dash
             * else
             *  show an error
             */
            $scope.login = function () {
                AuthService
                    .login($scope.form)
                    .success(function (data, status, headrs, config) {
                        //TODO: if successfull, route to next page, else route to error

                        $state.go("userdash", {}, {});

                    })
                    .error(function (data, status, headrs, config) {
                        if (status === statusCodes.forbidden) {
                            $scope.message = messages.forbidden;
                        }
                    });



            };




        }
    ];

});
