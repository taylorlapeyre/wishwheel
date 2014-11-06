define(['angular','router','modules/home/HomeController','routes'],function(angular,router,homeCtrl,routes){

	var home = angular.module('home',['auth','ui.router']);
	home
        .controller('HomeController',homeCtrl)
        .config(routes);

    return home;
});
