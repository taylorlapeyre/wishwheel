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

            $scope.showSideBar = function () {
                $scope.RsideBar = !$scope.RsideBar;
            };


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
