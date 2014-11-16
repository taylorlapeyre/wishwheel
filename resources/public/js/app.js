/**
 * App
 *
 * The module which sets up filepaths for loading via define calls and bootstraps the application.
 *
 *
 *
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 *
 * @module app
 */

define(['angular','modules/home/home','modules/auth/auth'],function(angular,home,auth){
	
	angular.module('app',['home','auth']);

});
