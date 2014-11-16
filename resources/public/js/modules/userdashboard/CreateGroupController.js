/**
 * CreateGroupController
 *
 * The controller for the dialog to create a group.
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 *
 * @module userdashboard
 */
define([], function () {
    'use strict';
    return [
        '$scope',
        /**
         * @class CreateGroupController
         * @param $scope
         */
        function($scope){
            $scope.groupmembers = [];

            /***
             * Adds a member to the list of members in the new group.
             * @param {object} none, reads the form from the document.
             * @method addMember
             */
            $scope.addMember = function () {
                $scope.groupmembers.push({
                    name:$scope.form.name
                });
                $scope.form.name = "";
            };

            /***
             * removes the member from the members array
             * @param {int} $index the index of the user to remove from the new group.
             * @method removeMember
             */
            $scope.removeMember = function($index){
                $scope.groupmembers.splice($index,1);
            };
        }
    ];
});