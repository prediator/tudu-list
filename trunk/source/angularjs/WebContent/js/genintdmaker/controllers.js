'use strict';

function MainCtrl($scope) {

    $scope.finalOutput = '';

    $scope.general = {};
    $scope.general.platform = 'windows';
    $scope.general.library_path = '';
    $scope.general.log_file = '';
    $scope.general.log_level = _.findWhere($scope.init.log_levels, {name:'ERROR'});

    $scope.nodes = [];

    $scope.make = function () {
        // TODO
        $scope.finalOutput = '';
        $scope.finalOutput += JSON.stringify($scope.general, null, 2);
        $scope.finalOutput += '\n';
        $scope.finalOutput += JSON.stringify($scope.nodes, null, 2);
    };
}
