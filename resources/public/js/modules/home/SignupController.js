define([],function(){
    return [
        '$scope',
        '$state',
        'AuthService',
        function($scope,$state,AuthService){
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