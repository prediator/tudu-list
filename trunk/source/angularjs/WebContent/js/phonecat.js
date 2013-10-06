'use strict';

/* App Module */

angular.module('phonecat', ['phonecatFilters', 'phonecatServices']).
    config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/phones', {templateUrl:'partials/phone-list.html', controller:PhoneListCtrl}).
        when('/phones/:phoneId', {templateUrl:'partials/phone-detail.html', controller:PhoneDetailCtrl}).
        otherwise({redirectTo:'/phones'});
}]);


/* Services */

angular.module('phonecatServices', ['ngResource']).factory('Phone', function ($resource) {
    return $resource('phones/:phoneId.json', {}, {
        query:{method:'GET', params:{phoneId:'phones'}, isArray:true}
    });
});


/* Filters */

angular.module('phonecatFilters', []).filter('checkmark', function () {
    return function (input) {
        return input ? '\u2713' : '\u2718';
    };
});


/* Controller */

function PhoneListCtrl($scope, Phone) {
    $scope.phones = Phone.query();
    $scope.orderProp = 'age';
    $scope.getTotalPhones = function () {
        return $scope.phones.length;
    };
    $scope.removeQuery = function () {
        $scope.query = '';
    };
}
//PhoneListCtrl.$inject = ['$scope', '$http'];

function PhoneDetailCtrl($scope, $routeParams, Phone) {
    $scope.phone = Phone.get({phoneId:$routeParams.phoneId}, function (phone) {
        $scope.mainImageUrl = phone.images[0];
    });
    $scope.setImage = function (imageUrl) {
        $scope.mainImageUrl = imageUrl;
    }
}
//PhoneDetailCtrl.$inject = ['$scope', '$routeParams', '$http'];
