define(['angular','userdash/CreateGroupController'], function (angular,CreateGroupController) {
    var mod = angular.module('userdash',[])
        .controller('CreateGroupController',CreateGroupController);
    return mod;
});