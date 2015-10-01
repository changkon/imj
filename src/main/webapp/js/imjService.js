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