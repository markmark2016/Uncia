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
                .then(function (userData) {
                    return userData.data;
                });
        };

        self.searchUsers = function (query) {
            return $http.get(constants.api('api/users/search?query=' + query))
                .then(function (userData) {
                    return userData.data;
                });
        };

        self.getEnterpriseUsers = function () {
            return $http.get(constants.api('api/enterprise/users'))
                .then(function (userData) {
                    return userData.data;
                });
        };

        self.getBooks = function () {
            return $http.get(constants.api('api/books'))
                .then(function (booksData) {
                    return booksData.data;
                });
        };

        self.saveBook = function (book) {
            return $http.post(constants.api('admin/books/save'), book)
                .then(function (result) {
                    return result.data;
                })
        };

        self.deleteBook = function (bookId) {
            return $http.get(constants.api('admin/books/delete/' + bookId))
                .then(function (result) {
                    return result.data;
                });
        };

        self.searchBooksInDouban = function (q, limits) {
            return $http.get(
                constants.api('api/douban/books/search?query=' + q + '&limits=' + limits))
                .then(function (result) {
                    return result.data;
                });
        };

        self.getCommunities = function () {
            return $http.get(
                constants.api('api/communities'))
                .then(function (result) {
                    return result.data;
                });
        };

        self.getCategories = function () {
            return $http.get(
                constants.api('api/categories'))
                .then(function (result) {
                    return result.data;
                });
        };

        self.saveCommunity = function (communityCategoryBean) {
            return $http.post(
                constants.api('admin/community/save'), communityCategoryBean)
                .then(function (result) {
                    return result.data;
                });
        };

        self.deleteCommunity = function (communityId) {
            return $http.get(
                constants.api('admin/community/delete/'+communityId))
                .then(function (result) {
                    return result.data;
                });
        };

        self.getCategoryById = function (CommunityId) {

        };

    }

});