/**
 * CreateGroupController
 *
 * The controller for the dialog to create a group.
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 */
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