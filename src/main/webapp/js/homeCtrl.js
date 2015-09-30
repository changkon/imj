var app = angular.module('app');

function Viewer(firstName, lastName, country, age, gender) {
	this.first_name = firstName;
	this.last_name = lastName;
	this.country = country;
	this.age = age;
	this.gender = gender;
}

function Movie(title, director, genre, release, country, language, runtime) {
	this.title = title;
	this.director = director;
	this.genre = genre;
	this.country = country;
	this.language = language;
	this.runtime = runtime;
}

app.controller('HomeCtrl', ['$scope', '$state', 'ViewerFactory', 'MovieFactory', function($scope, $state, ViewerFactory, MovieFactory) {
	$scope.state = $state;
	$scope.limit = 10;

	ViewerFactory.get({startId: 1, size: $scope.limit}, function(viewers) {
		$scope.viewers = viewers.viewer;
	});

	MovieFactory.get({start:1, size: $scope.limit}, function(movies) {
		$scope.movies = movies.movie;
	});
}]);

app.controller('ViewersCtrl', ['$scope', 'ViewerFactory', function($scope, ViewerFactory) {
	$scope.addViewer = function(firstName, lastName, country, age, gender) {
		Viewer viewer = new Viewer(firstName, lastName, country, age, gender);
		viewer.$save();
	};
}]);

app.controller('MoviesCtrl', ['$scope', function($scope) {
	console.log("sdf");
}]);