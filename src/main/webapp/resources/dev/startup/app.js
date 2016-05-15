/**
 * @file angular 入口
 *
 * @author liming
 */

define(['angular', 'angular-ui-router', 'angular-bootstrap'], function (angular) {
    'use strict';
    angular.module('app', ['ui.router', 'ui.bootstrap'])
        .factory('mark403Interceptor', ['$q', function ($q) {
            return {
                responseError: function (rejection) {
                    if (rejection.status === 403) {
                        document.write(rejection.data);
                        document.close();
                        return;
                    }
                    return $q.reject(rejection);
                }
            };
        }]).config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('mark403Interceptor');
    }]);
    return angular.module('app');
});