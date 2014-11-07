define([], function () {
    'use strict';
    return [
        '$scope',
        function($scope){
            $scope.groupmembers = [];

            $scope.addMember = function () {
                $scope.groupmembers.push({
                    name:$scope.form.name
                });
                $scope.form.name = "";
            };

            $scope.removeMember = function($index){
                $scope.groupmembers.splice($index,1);
            };
        }
    ];
});