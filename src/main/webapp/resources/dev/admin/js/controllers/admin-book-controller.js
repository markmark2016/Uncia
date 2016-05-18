/**
 * @author liming
 */

define(['app', 'constants', 'angular'], function (app, constants, angular) {

    return app.controller('AdminBookController',
                          ['AdminDataService', '$uibModal', AdminBookController]);

    function AdminBookController(adminDataService, $uibModal) {

        var self = this;

        self.books = [];
        self.modalInstance = null;

        adminDataService.getBooks()
            .then(function (data) {
                      self.books = data || [];
                  }
            );

        self.editBook = function (book) {
            self.modalInstance = $uibModal.open(
                {
                    controller: function () {
                        this.book = book;

                        this.save = function () {
                            adminDataService.saveBook(book)
                                .then(function (result) {
                                    if (result === 'SUCCESS') {
                                        adminDataService.getBooks()
                                            .then(function (data) {
                                                      self.books = data || [];
                                                  }
                                            );
                                        self.cancel();
                                    }
                                })
                        };

                        return this;
                    },
                    controllerAs: 'bookEditCtrl',
                    openedClass: 'mark-modal-body',
                    size: 'lg',
                    templateUrl: constants.resource('/admin/tpl/edit-book.html'),
                    windowClass: 'mark-modal'
                }
            );
        };

        self.addBook = function () {

            self.modalInstance = $uibModal.open(
                {
                    controller: function ($scope) {

                        this.query = "";
                        $scope.searchedBooks = ['helo'];

                        this.search = function () {
                            adminDataService.searchBooksInDouban(this.query, 1)
                                .then(function (booksData) {
                                    $scope.searchedBooks = booksData;
                                });
                        };
                        this.save = function (book) {
                            adminDataService.saveBook(book)
                                .then(function (result) {
                                    if (result === 'SUCCESS') {
                                        adminDataService.getBooks()
                                            .then(function (data) {
                                                      self.books = data || [];
                                                  }
                                            );
                                        self.cancel();
                                    }
                                })
                        };

                       

                        return this;
                    },
                    controllerAs: 'bookAddCtrl',
                    openedClass: 'mark-modal-body',
                    size: 'lg',
                    templateUrl: constants.resource('/admin/tpl/add-book.html'),
                    windowClass: 'mark-modal'
                }
            );
        };

        self.deleteBook = function (bookId) {

            adminDataService.deleteBook(bookId)
                .then(function (result) {
                          if (result === 'SUCCESS') {
                              adminDataService.getBooks()
                                  .then(function (data) {
                                            self.books = data || [];
                                        }
                                  );
                          }
                      }
                );
        };

        self.cancel = function () {
            self.modalInstance && self.modalInstance.close();
        }

    }

});