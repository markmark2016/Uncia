/**
 * @author liming
 */

define(['app', 'constants', 'angular'], function (app, constants, angular) {

    return app.controller('AdminGroupController',
                          ['AdminDataService', 'AdminUploadService', '$uibModal',
                           AdminGroupController]);

    function AdminGroupController(adminDataService, uploadService, $uibModal) {

        var self = this;

        self.groups = [];
        self.modalInstance = null;

        adminDataService.getGroups()
            .then(function (data) {
                      self.groups = data || [];
                  }
            );

        self.editGroup = function (group) {

            self.modalInstance = $uibModal.open(
                {
                    controller: function ($scope, $filter) {

                        $scope.group = group || {
                                id: null,
                                description: '',
                                slogan: '',
                                startTime: new Date(),
                                endTime: new Date(),
                                deadlineTime: new Date(),
                                frequency: 1,
                                addMemberType: true,
                                visiable: true,
                                remarkVisiable: false
                            };
                        $scope.startTime = group ? group.startTime : new Date();
                        $scope.endTime = group ? group.endTime : new Date();
                        $scope.deadlineTime = group ? group.deadlineTime : new Date();

                        $scope.startTimePopup = {
                            open: false
                        };
                        $scope.endTimePopup = {
                            open: false
                        };
                        $scope.deadlineTimePopup = {
                            open: false
                        };
                        $scope.openStartTime = function () {
                            $scope.startTimePopup.open = true;
                        };
                        $scope.openEndTime = function () {
                            $scope.endTimePopup.open = true;
                        };
                        $scope.openDeadlineTime = function () {
                            $scope.deadlineTimePopup.open = true;
                        };

                        $scope.datePickerOption = {
                            dateDisabled: function (dateData) {
                                if (dateData.date < new Date()) {
                                    return true;
                                }
                                if (dateData.date > new Date(2020, 1, 1)) {
                                    return true;
                                }
                            },
                            maxDate: new Date(2020, 1, 1),
                            minDate: new Date()
                        };

                        this.save = function () {
                            adminDataService.saveGroup($scope.group)
                                .then(function (result) {
                                    if (result === "SUCCESS") {
                                        adminDataService.getGroups()
                                            .then(function (groups) {
                                                self.groups = groups;
                                            });
                                        self.cancel();
                                    }
                                })

                        };
                        $scope.$watch(function () {
                            return $scope.startTime;
                        }, function (newVal) {
                            $scope.group.startTime = $filter('date')(newVal, "yyyy-MM-dd HH:mm:ss");
                        });
                        $scope.$watch(function () {
                            return $scope.endTime;
                        }, function (newVal) {
                            $scope.group.endTime = $filter('date')(newVal, "yyyy-MM-dd HH:mm:ss");
                        });
                        $scope.$watch(function () {
                            return $scope.deadlineTime;
                        }, function (newVal) {
                            $scope.group.deadlineTime =
                                $filter('date')(newVal, "yyyy-MM-dd HH:mm:ss");
                        });
                        return this;
                    },
                    controllerAs: 'groupEditCtrl',
                    openedClass: 'mark-modal-body',
                    size: 'lg',
                    templateUrl: constants.resource(
                        '/admin/tpl/edit-group.html'),
                    windowClass: 'mark-modal'
                })

        };

        self.addGroup = function () {
            self.editGroup(null);
        };

        self.deleteGroup = function (groupId) {
            self.modalInstance = $uibModal.open(
                {
                    controller: function () {

                        this.confirmDelete = function () {
                            adminDataService.deleteGroup(groupId)
                                .then(function (result) {
                                    if (result === 'SUCCESS') {
                                        adminDataService.getGroups()
                                            .then(function (data) {
                                                      self.groups =
                                                          data || [];
                                                  }
                                            );
                                        self.cancel();
                                    }
                                })
                        }

                    },
                    controllerAs: 'groupDelCtrl',
                    openedClass: 'mark-modal-body',
                    size: 'md',
                    templateUrl: constants.resource(
                        '/admin/tpl/delete-group.html'),
                    windowClass: 'mark-modal'
                }
            );
        };

        self.editBookInGroup = function (groupId) {
            self.modalInstance = $uibModal.open(
                {
                    controller: function ($scope) {
                        $scope.currentGroupBookRel = {
                            id: null,
                            groupId: groupId || null,
                            bookId: null
                        };

                        $scope.books = [];
                        $scope.currentBook = {};

                        adminDataService.getGroupBookRelByGroupId(groupId)
                            .then(function (result) {
                                $scope.currentGroupBookRel = result || {
                                        id: null,
                                        groupId: groupId || null,
                                        bookId: null
                                    };
                            }).then(function () {
                            adminDataService.getBooks()
                                .then(function (result) {
                                    $scope.books = result || [];
                                })
                                .then(function () {
                                    $scope.currentBook = getBook($scope.currentGroupBookRel.bookId);
                                })
                        });

                        function getBook(bookId) {
                            for (var i = 0; i < $scope.books.length; i++) {
                                if ($scope.books[i].id
                                    === bookId) {
                                    return $scope.books[i];
                                }
                            }
                        }

                        this.delete = function () {
                            adminDataService.deleteGroupBook($scope.currentGroupBookRel.id)
                                .then(function (result) {
                                    if (result === "SUCCESS") {
                                        $scope.currentBook = {};
                                        $scope.currentGroupBookRel = {
                                            id: null,
                                            groupId: groupId || null,
                                            bookId: null
                                        };
                                    }
                                })
                        };

                        this.save = function () {
                            adminDataService.saveGroupBook($scope.currentGroupBookRel)
                                .then(function (result) {
                                    if (result === "SUCCESS") {
                                        self.cancel();
                                    }
                                })
                        };

                        $scope.$watch(function () {
                            return $scope.currentBook;
                        }, function (newVal) {
                            if ($scope.currentGroupBookRel && $scope.currentBook) {
                                $scope.currentGroupBookRel.bookId = newVal.id;
                            }
                        })

                    },
                    controllerAs: 'groupBookCtrl',
                    openedClass: 'mark-modal-body',
                    size: 'md',
                    templateUrl: constants.resource(
                        '/admin/tpl/edit-book-for-group.html'),
                    windowClass: 'mark-modal'
                }
            );

        };

        self.cancel = function () {
            self.modalInstance && self.modalInstance.close();
        }

    }

});