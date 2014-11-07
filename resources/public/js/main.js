require.config({

	paths:{
		angular:"lib/angular/angular",
        router:'lib/angular-ui-router/release/angular-ui-router.min',
		ngAnimate:'lib/angular-animate/angular-animate',
        userdash:'modules/userdashboard',
        auth:'modules/auth',
        groupdash:'modules/groupdashboard',
        home:'modules/home',
        wheel:'modules/wheel',
        wishwheel:'modules/wishwheel'
	},
	shim:{
		angular:{exports:'angular'},
        router:['angular'],
		ngAnimate:{
			deps:['angular'],
			exports:'animate'
		}
	}
	
});

require(['app','angular', 'ngAnimate'],function(app,angular, ngAnimate){
	
	
	

	angular.element(document).ready(function(){
		angular.bootstrap(document,['app','ngAnimate']);
	});

});
