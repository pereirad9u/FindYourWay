var app = angular.module('findYourWay', ['ngRoute','leaflet-directive']);

app.config(['$routeProvider',
    function ($routeProvider) {
        // Système de routage
        $routeProvider
            .when('/home', {
                templateUrl: 'templates/home.html'
            })
            .when('/login', {
                templateUrl: 'templates/login.html'
            })
            .when('/admin', {
                templateUrl: 'templates/admin.html',
                controller : 'AdminController'
            })
            .when('/signup', {
                templateUrl: 'templates/inscription.html'
            })
            .when('/homeCo', {
                templateUrl: 'templates/homeCo.html'
            })
            .otherwise({
                redirectTo: '/home'
            });
    }

]);


app.controller("AdminController", [ "$scope", "leafletMarkerEvents", function($scope, leafletMarkerEvents) {

    $scope.center = {
        lat: 51.505,
        lng: -0.09,
        zoom: 8
    };

    $scope.markers = {
        london: {
            lat: 51.505,
            lng: -0.09,
            draggable: true,
            message: "I'm a draggable marker",
            focus: true,
            icon: {
                type : "awesomeMarker",
                markerColor : "red"
            }
        }
    }

    $scope.layers = {
        baselayers : {
            xyz: {
                name: 'OpenStreetMap',
                url: 'https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}@2x?access_token=pk.eyJ1IjoicGVyZWlyYWQ5dSIsImEiOiJjaXl4MXJodDcwMDMyMzNwZmJwc2JxbnFpIn0.Lvolt_wjqpvnfVBRJG-lAA',
                type: 'xyz',
                id: 'mapbox.streets',
            }
        }
    };

    $scope.events = {
        markers: {
            enable: leafletMarkerEvents.getAvailableEvents(),
        }
    };

    $scope.eventDetected = "No events yet...";
    var markerEvents = leafletMarkerEvents.getAvailableEvents();
    for (var k in markerEvents){
        var eventName = 'leafletDirectiveMarker.' + markerEvents[k];
        $scope.$on(eventName, function(event, args){
            $scope.eventDetected = event.name;
        });
    }
}]);