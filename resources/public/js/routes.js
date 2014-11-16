/**
 * Routes definition
 *
 * All the possible states the application can be in, as well as their views,urls,and controllers.
 *
 *
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 */

define([], function () {
'use strict';
    var baseUrl = 'js/modules/';

    return [
        '$stateProvider',
        '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.otherwise("/");

            $stateProvider
                .state({
                    name: 'home',
                    url: '/',
                    views: {
                        main: {
                            templateUrl: baseUrl + 'home/home.html'
                        }
                    }
                })
                .state({
                    name: 'signup',
                    url: '/signup',
                    views: {
                        main: {
                            templateUrl: baseUrl + 'home/signup.html'
                        }
                    }
                })
                .state({
                    name:"userdash",
                    url:'/dashboard',
                    views:{
                        main:{
                            templateUrl:baseUrl+"userdashboard/userdash.html",
                            controller:[function(){
                                console.log('Not checking if user is authenticated before allowing navigation to userdash');
                            }]
                        }
                    }
                })
                .state({
                    name:"userdash.groups",
                    url:'/groups',
                    views:{
                        main:{
                            templateUrl:baseUrl+"userdashboard/userdash.groups.html"
                        }
                    }
                })
                .state({
                    name:"userdash.creategroup",
                    url:'/creategroup',
                    views:{
                        main:{
                            templateUrl:baseUrl+"userdashboard/userdash.creategroup.html"
                        }
                    }
                })
                .state({
                    name:"userdash.feedback",
                    url:'/feedback',
                    views:{
                        main:{
                            templateUrl:baseUrl+"misc/feedback.html"
                        }
                    }
                })
                .state({
                    name:"userdash.myaccount",
                    url:'/myaccount',
                    views:{
                        main:{
                            templateUrl:baseUrl+"userdashboard/userdash.myaccount.html"
                        }
                    }
                })
                .state({
                    name:"group",
                    url:'/group/{groupId:[0-9]*}',
                    views:{
                        main:{
                            templateUrl:baseUrl+"groupdashboard/dash.html"
                        }
                    }
                })
                .state({
                    name:"wishwheel",
                    url:"/wishwheel",
                    views:{
                        main:{
                            templateUrl:baseUrl+"wishwheel/wishwheel.html"
                        }
                    }
                })




            ;


        }


    ];
});