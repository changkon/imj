var app = angular.module('app', ['ui.router', 'ngResource', 'angularUtils.directives.dirPagination']);

app.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
	$urlRouterProvider.otherwise('/');

	$stateProvider.state('home', {
		url: '/',
		templateUrl: 'partials/index.html',
		controller: 'HomeCtrl'
	}).state('viewers', {
		url: '^/viewers',
		templateUrl: 'partials/viewers.html',
		controller: 'ViewersCtrl'
	}).state('movies', {
		url: '^/movies',
		templateUrl: 'partials/movies.html',
		controller: 'MoviesCtrl'
	});
});