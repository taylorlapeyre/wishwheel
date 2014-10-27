'use strict';
define([], function () {

    return [
        "$scope",
        "$state",
        function ($scope,$state) {
            $scope.title = "Wish-list";



            $scope.login = function(){
                console.log("loginbtn clicked");

                var frm = $scope.form;

                //TODO: post the user's credentials to the api
                //TODO: if successfull, route to next page, else route to error

                $state.go("userdash",{},{});


            };





        }
    ];

});
