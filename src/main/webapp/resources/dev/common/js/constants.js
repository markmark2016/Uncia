/**
 * @file 常量
 */
/* global MarkMark */
define([], function () {
    'use strict';
    var formatUrl = function (url) {
        return url && url.replace(/([^:])(\/{2,})/g, '$1/');
    };
    var exports = {
        markmark: MarkMark || {
            constants: {
                CONTEXT: '',
                STATIC_PATH: 'resources/dev'
            }
        },
        resource: function (path) {
            return formatUrl(exports.markmark.constants.CONTEXT + exports.markmark.constants.STATIC_PATH + path
                + '?v=' + 1461155590693);
        },
        api: function (path) {
            return formatUrl(exports.markmark.constants.CONTEXT + path);
        }
    };
    return exports;
});
