/**
 * Authentication module
 *
 * The module which handles all authentication.
 *
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 */
define(['angular','modules/auth/AuthService'],
	function(angular,AuthService){
		var auth = angular.module('auth',[]);
		auth.service('AuthService',AuthService);
		return auth;
});
