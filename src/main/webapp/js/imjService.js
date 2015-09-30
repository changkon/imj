var services = angular.module('app');

services.factory('ViewerIdFactory', function($resource) {
	return $resource('/services/viewer/:id');
});

services.factory('ViewerFactory', function($resource) {
	return $resource('/services/viewer', {});
});

services.factory('MovieFactory', function($resource) {
	return $resource('/services/movie', {});
});