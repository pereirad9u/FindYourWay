var app = angular.module('findYourWay', ['ngRoute', 'ngResource', 'leaflet-directive']);
app.constant('api', {'url': 'http://127.0.0.1:8080/lp_cisiie/api/'});


app.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {
        // Système de routage
        $routeProvider
            .when('/home', {
                templateUrl: 'templates/home.html'
            })
            .when('/login', {
                templateUrl: 'templates/login.html',
                controller: 'AdminController'
            })
            .when('/admin', {
                templateUrl: 'templates/admin.html',
                controller: 'AdminController'
            })
            .when('/startGame', {
                templateUrl: 'templates/startGame.html',
                controller: 'GameController'
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
    return $resource(api.url + "lieux/:id", {id: '@_id'},
        {
            update: {method: "POST"},
            get: {method: "GET", url: api.url + "lieux"}
        });
}]);

app.factory('Destination', ['$resource', 'api', function ($resource, api) {
    return $resource(api.url + "destinationFinales/:id", {id: '@_id'},
        {
            update: {method: "POST"},
            get: {method : "GET", url: api.url + "destinationFinales"}
        });
}]);

app.factory('Admin', ['$resource', 'api', function ($resource, api) {
    return $resource(api.url + "authentification",{},
        {
            auth: {method : "POST", url : api.url+"authentification"}
        });
}]);

app.controller("AdminController", ["$scope", "$location", "Admin", "Lieux", "Destination", "leafletMarkerEvents", function ($scope, $location, Admin, Lieux, Destination, leafletMarkerEvents) {

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

    $scope.login = function() {
        Admin.auth({
            username : $scope.username,
            password : $scope.password
        },function (s) {
            console.log(s)
        },function (e) {
            console.log(e)
        });

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

app.controller("GameController", ["$scope", "$location", "$anchorScroll", "Lieux", "Destination", "leafletMarkerEvents", function ($scope, $location, $anchorScroll, Lieux, Destination, leafletMarkerEvents) {

    $scope.username = "";
    $scope.jeuFini = false;
    $scope.indices = [];
    $scope.pointEnCour = 0;
    $scope.D = 1000;
    $scope.couleur = ["purple", "green", "cadetblue", "orange", "blue"];
    $scope.destinationFinale = {};
    $scope.points = [];
    $scope.indication = $scope.points[$scope.pointEnCour].description;

    $scope.paths = {

    };

    $scope.markers = {
        0: {
            lat: 48,
            lng: 2,
            draggable: true,
            message: "Point 1, Déplacez moi !",
            focus: true,
            icon: {
                type: "awesomeMarker",
                prefix : "glyphicon",
                icon: "asterisk",
                markerColor: "red"
            }
        }
    };

    $scope.lat = $scope.markers[$scope.pointEnCour].lat;
    $scope.lng = $scope.markers[$scope.pointEnCour].lng;

    $scope.center = {
        lat: 45,
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

    $scope.startGame = function() {
        lieux = Lieux.query();
        destinationFinale = Destination.query();
        lieux.sort(function() { return [1, -1, 0][Math.random() *3 |0];});
        destinationFinale.sort(function() { return [1, -1, 0][Math.random() *3 |0];});
        $scope.points.push(lieux[0]);
        $scope.points.push(lieux[1]);
        $scope.points.push(lieux[2]);
        $scope.points.push(lieux[3]);
        $scope.points.push(lieux[4]);
        $scope.destinationFinale = destinationFinale[0];

        $location.path("/game");
        $location.replace();
    };
    
    $scope.validerPoint = function () {
        var dist = L.latLng($scope.lat, $scope.lng).distanceTo(L.latLng($scope.points[$scope.pointEnCour].lat, $scope.points[$scope.pointEnCour].lng))
        if (dist < $scope.D) {
            $scope.indices[$scope.pointEnCour] = $scope.destinationFinale.indices[$scope.pointEnCour];
        }else{
            $scope.indices[$scope.pointEnCour] = {description : "Pas d'indice, votre point était trop loin ..."};
        }

        if ($scope.pointEnCour > 0) {
            $scope.paths[$scope.pointEnCour - 1 ] = {
                type : "polyline",
                weight : 3,
                color : "blue",
                latlngs: [{ lat: $scope.lat, lng: $scope.lng }, { lat: $scope.markers[$scope.pointEnCour -1].lat, lng: $scope.markers[$scope.pointEnCour -1 ].lng }]
            }
        }

        $scope.markers[$scope.pointEnCour].draggable = false;
        $scope.markers[$scope.pointEnCour].lat = $scope.lat;
        $scope.markers[$scope.pointEnCour].lng = $scope.lng;
        $scope.markers[$scope.pointEnCour].message = "Point "+($scope.pointEnCour+1);
        $scope.pointEnCour++;
        if ($scope.pointEnCour < 5) {
            $scope.indication = $scope.points[$scope.pointEnCour].description;
            $scope.markers[$scope.pointEnCour] = {
                lat: 48,
                lng: 2,
                draggable: true,
                message: "Point " + ($scope.pointEnCour + 1) + ", Déplacez moi !",
                focus: true,
                icon: {
                    type: "awesomeMarker",
                    prefix : "glyphicon",
                    icon: "asterisk",
                    markerColor: $scope.couleur[$scope.pointEnCour - 1]
                }
            }
        }
        else {
            $scope.markers[$scope.pointEnCour] = {
                lat: 48,
                lng: 2,
                draggable: true,
                message: "Destination finale, Déplacez moi !",
                focus: true,
                icon: {
                    type: "awesomeMarker",
                    icon: "star",
                    markerColor: $scope.couleur[$scope.pointEnCour - 1]
                }
            }
        }
        $scope.lat = $scope.markers[$scope.pointEnCour].lat;
        $scope.lng = $scope.markers[$scope.pointEnCour].lng;

    };

    $scope.validerDestinationFinale = function() {
        $scope.markers[$scope.pointEnCour] = {
            lat: $scope.lat,
            lng: $scope.lng,
            draggable: false,
            message: "Destination finale",
            focus: true,
            icon: {
                type: "awesomeMarker",
                icon: "star",
                markerColor: $scope.couleur[$scope.pointEnCour-1]
            }
        };
        $scope.jeuFini = true;

        

        var dist = L.latLng($scope.lat, $scope.lng).distanceTo(L.latLng($scope.destinationFinale.lat, $scope.destinationFinale.lng));
        if (dist > 1000)
            $scope.distance = (dist/1000).toFixed(2) + " km";
        else
            $scope.distance = Math.round(dist).toFixed(2) + " m";
        if ( dist < $scope.D)
            $scope.score = $scope.D;
        else if (dist > $scope.D && dist < 2*$scope.D)
            $scope.score = $scope.D*.75;
        else if (dist > 2*$scope.D && dist < 4*$scope.D)
            $scope.score = $scope.D*.55;
        else if (dist > 4*$scope.D && dist < 6*$scope.D)
            $scope.score = $scope.D*.35;
        else if (dist > 6*$scope.D && dist < 8*$scope.D)
            $scope.score = $scope.D*.15;
        else if (dist > 8*$scope.D)
            $scope.score = $scope.D*.05;
        $scope.paths[$scope.pointEnCour - 1 ] = {
            type : "polyline",
            weight : 3,
            color : "blue",
            latlngs: [{ lat: $scope.lat, lng: $scope.lng }, { lat: $scope.markers[$scope.pointEnCour -1].lat, lng: $scope.markers[$scope.pointEnCour -1 ].lng }]
        }
    }
}]);
