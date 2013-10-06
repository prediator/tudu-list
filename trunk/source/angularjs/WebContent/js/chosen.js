var module = angular.module('chosen', []);

module.directive('chosen', function () {
    var linker = function (scope, element, attr) {
        scope.$watch('recipientsList', function () {
            element.trigger("liszt:updated");
        });
        element.chosen();
    };
    return {
        restrict:'A',
        link:linker
    }
});

function RecipientsCtrl($scope, $http) {
    $scope.url = "recipients/recipients.json";
    $scope.recipientsList = [];

    $scope.fetchRecipients = function () {
        $http.get($scope.url).then(function (result) {
            $scope.recipientsList = result.data;
        })
    };

    $scope.fetchRecipients();
}