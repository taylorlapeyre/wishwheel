define([],function(){
    return [
        '$scope',
        'AuthService',
        function($scope,AuthService){
            $scope.createAccount = function(){
                AuthService.createAccount($scope.form)
                    .success(function(data, status, headrs, config){
                        debugger;
                    })
                    .error(function(data, status, headrs, config){

                        debugger;
                    });
            };
        }
    ];
});