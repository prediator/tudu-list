var module = angular.module('domman', []);

module.directive('micWidget', function () {
    var linker = function (scope, element, attrs) {
        var oneBox = element.children()[0];
        var twoBox = element.children()[1];
        var animateRight = function () {
            $(this).animate({
                left:'+=50'
            })
        };
        var animateDown = function () {
            $(this).animate({
                top:'+=50'
            })
        };
        $(oneBox).click(animateDown);
        $(twoBox).click(animateRight);
    };
    return {
        restrict:'E',
        link:linker
    }
});