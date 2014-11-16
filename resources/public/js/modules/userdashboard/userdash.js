/**
 * CreateGroupController
 *
 * The controller for the dialog to create a group.
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 *
 */

define(['angular','userdash/CreateGroupController'], function (angular,CreateGroupController) {
'use strict';
    var mod = angular.module('userdash',[])
        .controller('CreateGroupController',CreateGroupController);
    return mod;
});