/**
 * @file 管理数据Service
 * @author liming
 */
define(['app', 'constants', 'angular'], function (app,constants, angular) {

    return app.service('AdminUploadService', ['$http', AdminUploadService]);

    function AdminUploadService($http) {

        var self = this;

        self.uploadFileToUrl = function (normalFormData, file, uploadUrl) {
            var fd = new FormData();
            for (var prop in normalFormData) {
                if (normalFormData.hasOwnProperty(prop)) {
                    fd.append(prop, normalFormData[prop]);
                }
            }
            fd.append('file', file);
            return $http.post(constants.api(uploadUrl), fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (result) {
                return result.data;
            })
        }

    }

});