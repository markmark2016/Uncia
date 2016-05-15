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
                .state('admin', {
                    url: '/',
                    templateUrl: constants.resource('/admin/tpl/index.html'),
                    controller: 'AdminController',
                    controllerAs: 'aCtrl'
                })
                .state('admin.users', {
                    url: 'users',
                    templateUrl: constants.resource('/admin/tpl/users.html'),
                    controller: 'AdminController',
                    controllerAs: 'aCtrl'
                })
                .state('admin.remark', {
                    url: 'remark',
                    templateUrl: constants.resource('/admin/tpl/remark.html'),
                    controller: 'AdminController',
                    controllerAs: 'aCtrl'
                })
        }

        app.config(['$stateProvider', '$urlRouterProvider', config]);

    });