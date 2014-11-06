define([],function(){
    var routes = {
        login:'/api/auth'
    };
return [
    '$http',
	function($http){
		
		var srvc = function(){

            this.login = function(form){
                return $http.post(routes.login,form);
            };

		};
		
		return new srvc();
	}
];

});
