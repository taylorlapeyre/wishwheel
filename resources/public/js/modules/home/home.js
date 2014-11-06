define(['angular','router','modules/home/HomeController','modules/home/SignupController','routes'],function(angular,router,homeCtrl,signupCtrl,routes){

	var home = angular.module('home',['auth','ui.router']);
	home
        .controller('HomeController',homeCtrl)
        .controller('SignupController',signupCtrl)
        .config(routes);

    return home;
});
