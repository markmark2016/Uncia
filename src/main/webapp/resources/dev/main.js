/**
 * @file angular 入口
 *
 * @author liming16
 */
'use strict';

require.config({
    paths: {
        'app': 'startup/app',
        'router': 'startup/router',
        'start': 'startup/start',
        'ctrls': 'startup/controllers',
        'servs': 'startup/services',
        'dirs': 'startup/directives',
        'constants': 'common/js/constants',
        'angular': 'common/lib/angular/js/angular.min',
        'angular-ui-router': 'common/lib/angular-ui-router/js/angular-ui-router.min',
        'angular-animate': 'common/lib/angular-animate/js/angular-animate.min',
        'angular-cookies': 'common/lib/angular-cookies/js/angular-cookies.min',
        'angular-resource': 'common/lib/angular-resource/js/angular-resource.min',
        'angular-sanitize': 'common/lib/angular-sanitize/js/angular-sanitize.min',
        'angular-bootstrap': 'common/lib/angular-bootstrap/js/ui-bootstrap-tpls.min',
        'clipboard': 'common/lib/clipboard/js/clipboard.min',
        'jquery': 'common/lib/jquery/js/jquery.min'
    },
    shim: {
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'angular-animate': {
            deps: ['angular'],
            exports: 'angularAnimate'
        },
        'angular-resource': {
            deps: ['angular'],
            exports: 'angularResource'
        },
        'angular-cookies': {
            deps: ['angular'],
            exports: 'angularCookies'
        },
        'angular-sanitize': {
            deps: ['angular'],
            exports: 'angularSanitize'
        },
        'angular-ui-router': {
            deps: ['angular'],
            exports: 'angularUIRouter'
        },
        'angular-bootstrap': {
            deps: ['angular'],
            exports: 'angularBootstrap'
        },
        'bootstrap': {
            deps: ['jquery'],
            exports: 'bootstrap'
        }
    },
    deps: ['start']
});