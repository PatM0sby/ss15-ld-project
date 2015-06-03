var training = angular.module('training', ['ui.bootstrap']);

training.controller('TrainingController', function ($scope, $http) {

    $scope.loadMovie = function (film) {
        //var parameter = document.getElementById('eingabe').value;

        $http.get('/api/movie?titel=' + film).success(function (data) {
            $scope.movie = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

    $scope.updateMovie = function() {
      $scope.loadMovie($scope.film);
    };

    $scope.reloadSongs = function () {
        var parameter2 = document.getElementById('eingabe').value;

        $http.get('/api/songs?titel=' + parameter2).success(function (data) {
            $scope.songs = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

    $scope.reloadActors = function () {
        var parameter3 = document.getElementById('eingabe').value;

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
