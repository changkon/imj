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
	this.id = null;
	this.title = title;
	this.director = director;
	this.genre = genre;
	this.release = release;
	this.country = country;
	this.language = language;
	this.runtime = runtime;
}

function Log(movie, date, geo_location) {
	this.id = null;
	this.movie = movie;
	this.date = date;
	this.geo_location = geo_location;
}

app.controller('BaseCtrl', ['$scope', '$state', function($scope, $state) {
	$scope.state = $state;
}]);

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
	$scope.movie = {};
	$scope.geo_location = {};

	var viewerId = $stateParams.id;

	$scope.add = function() {
		if (Object.keys($scope.movie).length != 7) {
			return;
		}

		if (Object.keys($scope.geo_location).length != 2) {
			return;
		}

		if (typeof $scope.date === 'undefined') {
    		return;
		}

		var log = new Log($scope.movie, $scope.date, $scope.geo_location);

		// post to database
		LogsFactory.save({id: viewerId}, log);

		delete $scope.date;
		$scope.movie = {};
		$scope.geo_location = {};
	};

	$scope.refresh = function() {
		LogsFactory.get({id: viewerId}, function(logs) {
			$scope.logs = logs.log;
		});
	};

	// initially load log details
	LogsFactory.get({id: viewerId}, function(logs) {
		$scope.logs = logs.log;
	});
}]);

app.controller('RecommendedCtrl', ['$scope', '$stateParams', 'RecommendedFactory', function($scope, $stateParams, RecommendedFactory) {
	$scope.limit = 5;
	var viewerId = $stateParams.id;

	RecommendedFactory.get({id: viewerId}, function(recommended) {
		$scope.recommended = recommended.recommended;
	});
}]);

app.controller('MoviesCtrl', ['$scope', 'MovieFactory', function($scope, MovieFactory) {
	$scope.limit = 30;
	$scope.movie = {};

	$scope.createMovie = function() {
		if (Object.keys($scope.movie).length != 7) {
			return;
		}

		var movie = new Movie($scope.movie.title, $scope.movie.director, $scope.movie.genre, $scope.movie.release, $scope.movie.country, $scope.movie.language, $scope.movie.runtime);

		// reset
		$scope.movie = {};

		// add to database
		MovieFactory.save(movie);
	};

	$scope.refresh = function() {
		MovieFactory.get({}, function(movies) {
			$scope.movies = movies.movie;
		});
	};

	// Initially load data
	MovieFactory.get({}, function(movies) {
		$scope.movies = movies.movie;
	});
}]);

app.controller('MovieProfileCtrl',
	['$scope', '$stateParams', '$q', 'MovieIdFactory', 'MovieDescriptionFactory', 'MoviePosterFactory', 'MovieCastFactory', 'OMDBFactory',
	function($scope, $stateParams, $q, MovieIdFactory, MovieDescriptionFactory, MoviePosterFactory, MovieCastFactory, OMDBFactory) {

		var movieId = $stateParams.id;
		$scope.movie = {};
		$scope.editMovie = {};

		$scope.refreshMovie = function() {
			MovieIdFactory.get({id: movieId}, function(movie) {
				$scope.movie.id = movie.id;
				$scope.movie.title = movie.title;
				$scope.movie.director = movie.director;
				$scope.movie.genre = movie.genre;
				$scope.movie.release = movie.release;
				$scope.movie.country = movie.country;
				$scope.movie.language = movie.language;
				$scope.movie.runtime = movie.runtime;
			});
		};

		$scope.delete = function() {
			MovieIdFactory.delete({id: movieId});
		};

		$scope.edit = function() {
			if (Object.keys($scope.editMovie).length != 7) {
				return;
			}

			var movie = new Movie($scope.editMovie.title, $scope.editMovie.director, $scope.editMovie.genre, $scope.editMovie.release, $scope.editMovie.country, $scope.editMovie.language, $scope.editMovie.runtime);

			// reset
			$scope.editViewer = {};

			MovieIdFactory.update({id: movieId}, movie);
			// refresh
			$scope.refreshMovie();
		};

		$q.all([
			MovieIdFactory.get({id: movieId}, function(movie) {
				$scope.movie.id = movie.id;
				$scope.movie.title = movie.title;
				$scope.movie.director = movie.director;
				$scope.movie.genre = movie.genre;
				$scope.movie.release = movie.release;
				$scope.movie.country = movie.country;
				$scope.movie.language = movie.language;
				$scope.movie.runtime = movie.runtime;
			}),
			MovieDescriptionFactory.get({id: movieId}, function(movie) {
				$scope.movie.description = movie.description;
			}),
			MoviePosterFactory.get({id: movieId}, function(movie) {
				$scope.movie.poster = movie.poster;
			}),
			MovieCastFactory.get({id: movieId}, function(movie) {
				$scope.movie.cast = movie.cast;
			})
			]).then(function() {
				console.log('all')
			});
}]);