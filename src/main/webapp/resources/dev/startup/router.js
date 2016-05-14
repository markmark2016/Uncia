/**
 * @file 前端路由
 *
 * @author liming
 */

define(
    [
        'app',
        'constants',
        'angular',
        'angular-ui-router',
        'ctrls'
    ],
    function (app, constants) {
        'use strict';
        function config($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.otherwise('/');


            $stateProvider
                .state('default', {
                    url: '/',
                    controller: 'MarkMarkController',
                    controllerAs:'markController'
                })
                .state('admin', {
                    url: '/admin/{account:id}',
                    templateUrl: constants.resource('/admin/tpl/index.html'),
                    controller: 'AdminController',
                    controllerAs: 'aCtrl'
                })
                .state('login', {
                    url: '/login',
                    templateUrl: constants.resource('/login/tpl/index.html'),
                    controller: 'LoginController',
                    controllerAs: 'lCtrl'
                })
        }

        app.config(['$stateProvider', '$urlRouterProvider', config]);

    });