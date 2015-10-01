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

app.controller('ViewerProfileCtrl', ['$scope', '$stateParams', 'ViewerIdFactory', function($scope, $stateParams, ViewerIdFactory) {
	var viewerId = $stateParams.id;
	$scope.viewer = {};
	$scope.editViewer = {};

	$scope.refreshViewer = function() {
		ViewerIdFactory.get({id: viewerId}, function(viewer) {
			$scope.viewer.id = viewer.id;
			$scope.viewer.first_name = viewer.first_name;
			$scope.viewer.last_name = viewer.last_name;
			$scope.viewer.age = viewer.age;
			$scope.viewer.gender = viewer.gender;
			$scope.viewer.country = viewer.country;
		});
	};

	$scope.delete = function() {
		ViewerIdFactory.delete({id: viewerId});
	};

	$scope.edit = function() {
		if (Object.keys($scope.editViewer).length != 5) {
			return;
		}

		var viewer = new Viewer($scope.editViewer.firstName, $scope.editViewer.lastName, $scope.editViewer.country, $scope.editViewer.age, $scope.editViewer.gender);

		// reset
		$scope.editViewer = {};

		ViewerIdFactory.update({id: viewerId}, viewer);
		console.log("clicked");
		// refresh
		$scope.refreshViewer();
	};

	// initially load Viewer details
	ViewerIdFactory.get({id: viewerId}, function(viewer) {
		$scope.viewer.id = viewer.id;
		$scope.viewer.first_name = viewer.first_name;
		$scope.viewer.last_name = viewer.last_name;
		$scope.viewer.age = viewer.age;
		$scope.viewer.gender = viewer.gender;
		$scope.viewer.country = viewer.country;
	});

}]);

app.controller('LogCtrl', ['$scope', '$stateParams', 'LogsFactory', function($scope, $stateParams, LogsFactory) {
	$scope.limit = 15;

	var viewerId = $stateParams.id;


	// initially load log details
	LogsFactory.get({id: viewerId}, function(logs) {
		$scope.logs = logs.log;
		console.log(logs);
	});
}]);

app.controller('RecommendedCtrl', ['$scope', '$stateParams', 'RecommendedFactory', function($scope, $stateParams, RecommendedFactory) {
	var viewerId = $stateParams.id;

	RecommendedFactory.get({id: viewerId}, function(recommended) {
		console.log(recommended);
	});
}]);

app.controller('MoviesCtrl', ['$scope', function($scope) {
	console.log("sdf");
}]);