var module = angular
    .module('animate', [])
    .directive('animate', function ($timeout) {
        return {
            restrict:'A',
            link:function (scope, element, attrs) {
                scope.$watch('shape', function (val) {
                    var changes = {
                        left:val.x + 'px',
                        top:val.y + 'px',
                        backgroundColor:val.color
                    };
                    element.css(changes);
                }, true);
            }
        };
    });

function animator(shapes, $timeout) {
    (function tick() {
        var i;
        var now = new Date().getTime();
        var maxX = 600;
        var maxY = 600;

        for (i = 0; i < shapes.length; i++) {
            var shape = shapes[i];
            var elapsed = (shape.timestamp || now) - now;

            shape.timestamp = now;
            shape.x += elapsed * shape.velX / 1000;
            shape.y += elapsed * shape.velY / 1000;

            if (shape.x > maxX) {
                shape.x = 2 * maxX - shape.x;
                shape.velX *= -1;
            }
            if (shape.x < 30) {
                shape.x = 30;
                shape.velX *= -1;
            }

            if (shape.y > maxY) {
                shape.y = 2 * maxY - shape.y;
                shape.velY *= -1;
            }
            if (shape.y < 20) {
                shape.y = 20;
                shape.velY *= -1;
            }
        }

        $timeout(tick, 30);
    })();
}

function AnimateCtrl($scope, $timeout) {

    function buildShape() {
        var maxVelocity = 200;
        return {
            color:'#' + (Math.random() * 0xFFFFFF << 0).toString(16),
            x:Math.min(380, Math.max(20, (Math.random() * 380))),
            y:Math.min(180, Math.max(20, (Math.random() * 180))),

            velX:(Math.random() * maxVelocity),
            velY:(Math.random() * maxVelocity)
        };
    }

    // Publish list of shapes on the $scope/presentationModel
    // Then populate the list with 100 shapes randomized in position
    // and color
    $scope.shapes = [];
    for (var i = 0; i < 100; i++) {
        $scope.shapes.push(buildShape());
    }

    // Start timer-based, changes of the shape properties
    animator($scope.shapes, $timeout);
}