/**
 * Home module
 *
 *
 * The module which packages together the homepage.
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 */
define(['angular','router','modules/home/HomeController','modules/home/SignupController','routes','userdash/userdash'],function(angular,router,homeCtrl,signupCtrl,routes){

	var home = angular.module('home',['auth','userdash','ui.router']);
	home
        .controller('HomeController',homeCtrl)
        .controller('SignupController',signupCtrl)
        .config(routes);

    return home;
});
