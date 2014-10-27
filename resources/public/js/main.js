require.config({

	paths:{
		angular:"lib/angular/angular",
        router:'lib/angular-ui-router/release/angular-ui-router.min'
	},
	shim:{
		angular:{exports:'angular'},
        router:['angular']
	}
	
});

require(['app','angular'],function(app,angular){
	
	
	

	angular.element(document).ready(function(){
		angular.bootstrap(document,['app']);	
	});

});
