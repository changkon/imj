var app = angular.module('app', ['ui.router', 'ngResource', 'angularUtils.directives.dirPagination']);

app.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
	$urlRouterProvider.otherwise('/');

	$stateProvider.state('home', {
		url: '/',
		templateUrl: 'partials/index.html',
		controller: 'HomeCtrl'
	}).state('viewer', {
		url: '^/viewer',
		templateUrl: 'partials/viewers.html',
		controller: 'ViewersCtrl'
	}).state('movie', {
		url: '^/movie',
		templateUrl: 'partials/movies.html',
		controller: 'MoviesCtrl'
	}).state('viewerProfile', {
		url: '^/viewer/{id}',
		templateUrl: 'partials/viewerProfile.html',
		controller: 'ViewerProfileCtrl'
	}).state('movieProfile', {
		url: '^/movie/{id}',
		templateUrl: 'partials/movieProfile.html',
		controller: 'MovieProfileCtrl'
	});
});