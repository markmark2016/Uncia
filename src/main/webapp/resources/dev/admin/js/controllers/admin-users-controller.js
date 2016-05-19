/**
 * @author liming
 */

define(['app', 'constants', 'angular'], function (app, constants, angular) {

    return app.controller('AdminUsersController',
                          ['AdminDataService', '$uibModal', AdminUsersController]);

    function AdminUsersController(adminDataService, $uibModal) {

        var self = this;

        self.users = [];
        self.modalInstance = null;
        self.query = "";

        adminDataService.getEnterpriseUsers()
            .then(function (data) {
                      self.users = data || [];
                  }
            );
        
        self.searchUsers = function(){
            adminDataService.searchUsers(self.query)
                .then(function (data) {
                          self.users = data || [];
                      }
                );
        }


    }

});