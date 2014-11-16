/**
 * SignupController
 *
 *
 * The controller for signing up for the website, handles validation.
 *
 *
 *
 *
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 *
 *
 * @module Home
 */
define([],function(){
    /**
     * @class SignupController
     */
    return [
        '$scope',
        '$state',
        'AuthService',
        function($scope,$state,AuthService){
            /**
             * Posts to the REST api to create a new account.
             * @method createAccount
             */
            $scope.createAccount = function(){
                AuthService.createAccount($scope.form)
                    .success(function(data, status, headrs, config){
                        $state.go('userdash');
                    })
                    .error(function(data, status, headrs, config){
                        $scope.message = "";
                    });
            };
        }
    ];
});