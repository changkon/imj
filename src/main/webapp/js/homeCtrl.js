var app = angular.module('app');

function Viewer(firstName, lastName, country, age, gender) {
	this.id = null;
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
	$scope.limit = 30;
	$scope.viewer = {};

	$scope.createViewer = function() {
		if (Object.keys($scope.viewer).length != 5) {
			return;
		}

		var viewer = new Viewer($scope.viewer.firstName, $scope.viewer.lastName, $scope.viewer.country, $scope.viewer.age, $scope.viewer.gender);

		// reset
		$scope.viewer = {};

		// add to database
		ViewerFactory.save(viewer);
	};

	$scope.refresh = function() {
		ViewerFactory.get({}, function(viewers) {
			$scope.viewers = viewers.viewer;
		});
	};

	// Initially load data
	ViewerFactory.get({}, function(viewers) {
		$scope.viewers = viewers.viewer;
	});
}]);

app.controller('MoviesCtrl', ['$scope', function($scope) {
	console.log("sdf");
}]);