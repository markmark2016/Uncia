/**
 * @author liming
 */

define(['app', 'constants', 'angular'], function (app, constants, angular) {

    return app.controller('AdminCommunityController',
                          ['AdminDataService', '$uibModal', AdminCommunityController]);

    function AdminCommunityController(adminDataService, $uibModal) {

        var self = this;

        self.communityCategoryBean = [];
        self.modalInstance = null;

        adminDataService.getCommunities()
            .then(function (data) {
                      self.communityCategoryBean = data || [];
                  }
            );

        self.editCommunity = function (communityCategoryBean) {

            self.modalInstance = $uibModal.open(
                {
                    controller: function ($scope) {

                        $scope.communityCategoryBean = communityCategoryBean;

                        $scope.currentCategory = $scope.communityCategoryBean.category;

                        $scope.categories = [];

                        adminDataService.getCategories()
                            .then(function (categoriesData) {
                                $scope.categories = categoriesData;
                            });

                        this.save = function () {
                            adminDataService.saveCommunity($scope.communityCategoryBean)
                                .then(function (result) {
                                    if (result === 'SUCCESS') {
                                        adminDataService.getCommunities()
                                            .then(function (data) {
                                                      self.communityCategoryBean =
                                                          data || [];
                                                  }
                                            );
                                        self.cancel();
                                    }
                                })
                        };

                        $scope.$watch(function () {
                            return $scope.currentCategory;
                        }, function (newVal) {

                            if ($scope.communityCategoryBean) {
                                $scope.communityCategoryBean.category = newVal;
                            }
                        });

                        return this;
                    },
                    controllerAs: 'communityEditCtrl',
                    openedClass: 'mark-modal-body',
                    size: 'lg',
                    templateUrl: constants.resource(
                        '/admin/tpl/edit-community.html'),
                    windowClass: 'mark-modal'
                })

        };

        self.addCommunity = function () {

            self.modalInstance = $uibModal.open(
                {
                    controller: function ($scope) {

                        $scope.communityCategoryBean = {
                            community: {
                                id: null,
                                name: '',
                                slogan: '',
                                description: '',
                                image: null,
                                deleteStatus: false
                            },
                            category: {
                            }
                        };

                        $scope.currentCategory = $scope.communityCategoryBean.category;

                        $scope.categories = [];

                        adminDataService.getCategories()
                            .then(function (categoriesData) {
                                $scope.categories = categoriesData;
                            });

                        this.save = function () {
                            adminDataService.saveCommunity($scope.communityCategoryBean)
                                .then(function (result) {
                                    if (result === 'SUCCESS') {
                                        adminDataService.getCommunities()
                                            .then(function (data) {
                                                      self.communityCategoryBean =
                                                          data || [];
                                                  }
                                            );
                                        self.cancel();
                                    }
                                })
                        };

                        $scope.$watch(function () {
                            return $scope.currentCategory;
                        }, function (newVal) {

                            if ($scope.communityCategoryBean) {
                                $scope.communityCategoryBean.category = newVal;
                            }
                        });

                        return this;
                    },
                    controllerAs: 'communityAddCtrl',
                    openedClass: 'mark-modal-body',
                    size: 'lg',
                    templateUrl: constants.resource(
                        '/admin/tpl/add-community.html'),
                    windowClass: 'mark-modal'
                })

        };

        self.deleteCommunity = function (communityId) {
            self.modalInstance = $uibModal.open(
                {
                    controller: function () {

                        this.confirmDelete = function () {
                            adminDataService.deleteCommunity(communityId)
                                .then(function (result) {
                                    if (result === 'SUCCESS') {
                                        adminDataService.getCommunities()
                                            .then(function (data) {
                                                      self.communityCategoryBean =
                                                          data || [];
                                                  }
                                            );
                                        self.cancel();
                                    }
                                })
                        }

                    },
                    controllerAs: 'communityDelCtrl',
                    openedClass: 'mark-modal-body',
                    size: 'md',
                    templateUrl: constants.resource(
                        '/admin/tpl/delete-community.html'),
                    windowClass: 'mark-modal'
                }
            );
        };

        self.cancel = function () {
            self.modalInstance && self.modalInstance.close();
        }

    }

});