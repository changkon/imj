var services = angular.module('app');

services.factory('ViewerAllFactory', function($resource) {
	return $resource('/services/viewer', {});
});

services.factory('MovieAllFactory', function($resource) {
	return $resource('/services/movie', {});
});