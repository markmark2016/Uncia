/**
 * @file 启动angular
 * @author liming
 */
define(
    [
        'jquery',
        'angular',
        'angular-ui-router',
        'angular-cookies',
        'angular-sanitize',
        'angular-resource',
        'angular-animate',
        'angular-bootstrap',
        'clipboard',
        'app',
        'router',
        'ctrls',
        'dirs',
        'servs'
    ],
    function ($, angular) {
        'use strict';
        $(document).ready(function () {
            angular.bootstrap(document, ['app']);
        });

    });
