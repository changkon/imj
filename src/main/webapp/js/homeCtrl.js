var app = angular.module('app');

app.controller('HomeCtrl', ['$scope', '$state', 'ViewerAllFactory', 'MovieAllFactory', function($scope, $state, ViewerAllFactory, MovieAllFactory) {
	$scope.state = $state;
	$scope.limit = 10;

	ViewerAllFactory.get({}, function(viewers) {
		$scope.viewers = viewers.viewer;
	});

	MovieAllFactory.get({}, function(movies) {
		$scope.movies = movies.movie;
	});
}]);

app.controller('ViewersCtrl', ['$scope', function($scope) {
	console.log("hello");
}]);

app.controller('MoviesCtrl', ['$scope', function($scope) {
	console.log("sdf");
}]);