define(['angular','modules/auth/AuthService'],
	function(angular,AuthService){
		var auth = angular.module('auth',[]);
		auth.service('AuthService',AuthService);	
		
		return auth;

});
