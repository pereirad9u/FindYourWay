var app = angular.module('findYourWay', ['ngRoute', 'ngResource', 'leaflet-directive']);
app.constant('api', {'url': 'localhost:8080/API_FindYourWay/api/'});


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
                controller: 'AdminController'
            })
            .when('/signup', {
                templateUrl: 'templates/inscription.html'
            })
            .when('/homeCo', {
                templateUrl: 'templates/homeCo.html'
            })
            .when('/game', {
                templateUrl: 'templates/game.html',
                controller: 'GameController'
            })
            .otherwise({
                redirectTo: '/home'
            });
    }

]);

app.factory('Lieux', ['$resource', 'api', function ($resource, api) {
    return $resource(api.url + "/lieux/:id", {id: '@_id'},
        {
            update: {method: "POST"}
        });
}]);

app.factory('Destination', ['$resource', 'api', function ($resource, api) {
    return $resource(api.url + "/destinationFinales/:id", {id: '@_id'},
        {
            update: {method: "POST"}
        });
}]);


app.controller("AdminController", ["$scope", "Lieux", "Destination", "leafletMarkerEvents", function ($scope, Lieux, Destination, leafletMarkerEvents) {

    $scope.choixPoint = true;

    $scope.markers = {
        point: {
            lat: 48.8587741,
            lng: 2.32,
            draggable: true,
            message: "Déplacez moi !",
            focus: true,
            icon: {
                type: "awesomeMarker",
                icon: "star",
                markerColor: "red"
            }
        }
    };

    $scope.lat = $scope.markers.point.lat;
    $scope.lng = $scope.markers.point.lng;

    $scope.choixCreation = function () {
        $scope.choixPoint = !$scope.choixPoint;
    };

    $scope.ajouterLieux = function() {
        $scope.newLieux = new Lieux({
            description : $scope.indication,
            lat : $scope.lat,
            lng : $scope.lng
        });
        $scope.newLieux.$save(function (success) {
            console.log(success);
        }, function (error) {
            console.log(error);
        });
    };

    $scope.ajouterDestination = function () {
      $scope.newDestination = new Destination({
          nom : $scope.nom,
          description : $scope.description,
          lat : $scope.lat,
          lng : $scope.lng,
          indices : [{description : $scope.indice1},
              {description : $scope.indice2},
              {description : $scope.indice3},
              {description : $scope.indice4},
              {description : $scope.indice5}]
      });
      $scope.newDestination.$save(function (success) {
            console.log(success);
        }, function (error) {
            console.log(error);
        });
    };

    $scope.center = {
        lat: 48.8587741,
        lng: 2.2074741,
        zoom: 6
    };


    $scope.layers = {
        baselayers: {
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

    $scope.$on("leafletDirectiveMarker.dragend", function (event, args) {
        $scope.lat = args.model.lat;
        $scope.lng = args.model.lng;
    });

}]);