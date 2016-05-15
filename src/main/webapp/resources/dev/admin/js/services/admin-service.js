/**
 * @file 管理数据Service
 * @author liming
 */
define(['app', 'constants'], function (app, constants) {

    app.service('AdminDataService', ['$http', AdminDataService]);

    function AdminDataService($http) {

        var self = this;

        /**
         * 获取当前登录企业账号下的组,用户信息
         * @returns {*}
         */
        self.getUsers = function () {
            return $http.get(constants.api('api/users'))
                .success(function (users) {
                    return users;
                }).error(function (err) {
                    console.log(err);
                    return null;
                })
        };
    }

});