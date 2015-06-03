var training = angular.module('training', ['ui.bootstrap']);

training.controller('TrainingController', function ($scope, $http) {

    $scope.getMovies = function () {
        $http.get('/api/movies').success(function (data) {
            $scope.movies = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };
    //$scope.getMovies();

    $scope.reloadMovies = function () {
        var parameter = document.getElementById('eingabe').value;

        $http.get('/api/movie?titel=' + parameter).success(function (data) {
            $scope.movies = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

    $scope.getSongs = function () {
        $http.get('/api/songs').success(function (data) {
            $scope.songs = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };
    //$scope.getSongs();

    $scope.reloadSongs = function () {
        var parameter2 = document.getElementById('input2').value;

        $http.get('/api/songs?titel=' + parameter2).success(function (data) {
            $scope.songs = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

    $scope.getActors = function () {
        $http.get('/api/actors').success(function (data) {
            $scope.actors = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };
    //$scope.getActors();

    $scope.reloadActors = function () {
        var parameter3 = document.getElementById('input3').value;

        $http.get('/api/actors?film=' + parameter3).success(function (data) {
            $scope.actors = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };
    $scope.loadLatestMovies = function () {
        /*TO DO
         Infos via crawler Abrufen und Antwortstring generieren.
         $scope.news="Dummy Element für viele tolle Filme";

            $scope.latestMovies = 'default';
        $http.get('/api/latest').success(function (data) {
            $scope.latestMovies= data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });*/
        $scope.latestMovies="Loading latest movies...";
        $http.get('/api/latest').success(function (data){$scope.latestMovies = data;})
            .error(function (data, status){window.alert('Status '+ status);
            });




    };
    $scope.loadLatestMovies();
});
