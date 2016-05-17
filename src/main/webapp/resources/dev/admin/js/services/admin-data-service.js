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
                .then(function(userData){
                    return userData.data;
                });
        };

        self.getBooks = function () {
            return $http.get(constants.api('api/books'))
                .then(function(booksData){
                    return booksData.data;
                });
        };

        self.saveBook = function(book){
            return $http.post(constants.api('api/books/save'), book)
                .then(function(result){
                    return result.data;
                })
        };

        self.deleteBook = function(bookId){
            return $http.get(constants.api('api/books/delete/'+bookId))
                .then(function(result){
                    return result.data;
                });
        }

    }

});