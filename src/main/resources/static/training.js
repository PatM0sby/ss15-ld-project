var training = angular.module('training', ['ui.bootstrap']);

training.controller('TrainingController', function ($scope, $http) {

    $scope.getMovies = function () {
        $http.get('/api/movies').success(function (data) {
            $scope.movies = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };
    $scope.getMovies();

    $scope.reloadMovies = function () {
        var parameter =  document.getElementById('input1').value;

        $http.get('/api/movies?titel='+parameter).success(function (data) {
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
    $scope.getSongs();

    $scope.reloadSongs = function () {
        var parameter =  document.getElementById('input2').value;

        $http.get('/api/songs?titel='+parameter).success(function (data) {
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
    $scope.getActors();

    $scope.reloadActors = function () {
        var parameter =  document.getElementById('input3').value;

        $http.get('/api/actors?name='+parameter).success(function (data) {
            $scope.actors = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

});
