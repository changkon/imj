var services = angular.module('app');

// Viewers
services.factory('ViewerIdFactory', function($resource) {
	return $resource('/services/viewer/:id', {}, {
		'update': { method: "PUT" }
	});
});

services.factory('ViewerFactory', function($resource) {
	return $resource('/services/viewer', {});
});

// Logs
services.factory('LogsFactory', function($resource) {
	return $resource('/services/viewer/:id/log');
});

// Recommended movies
services.factory('RecommendedFactory', function($resource) {
	return $resource('/services/viewer/:id/recommended');
});

// Movies
services.factory('MovieFactory', function($resource) {
	return $resource('/services/movie', {});
});

services.factory('MovieIdFactory', function($resource) {
	return $resource('/services/movie/:id', {}, {
		'update': { method: "PUT" }
	});
});

services.factory('MovieDescriptionFactory', function($resource) {
	return $resource('/services/movie/:id/description', {});
});

services.factory('MoviePosterFactory', function($resource) {
	return $resource('/services/movie/:id/poster', {});
});

services.factory('MovieCastFactory', function($resource) {
	return $resource('/services/movie/:id/cast', {});
});

// OMDB
services.factory('OMDBFactory', function($resource) {
	return $resource('/services/omdb/:id', {});
});