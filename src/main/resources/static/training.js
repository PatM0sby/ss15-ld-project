var training = angular.module('training', ['ui.bootstrap']).config(function($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist([
        'self',
        'https://www.youtube.com/**']);
});

training.controller('TrainingController', function ($scope, $http, $compile, $sce) {


    $scope.loadMovie = function (film) {
        //var parameter = document.getElementById('eingabe').value;

        $http.get('/api/movie?titel=' + film).success(function (data) {
            $scope.movie = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

    $scope.loadActorPic = function (name){

    }


    $scope.updateMovie = function() {
        $scope.loadMovie($scope.film);
    };

    $scope.loadSong = function (film) {
        //var parameter = document.getElementById('eingabe').value;

        $http.get('/api/song?name=' + film).success(function (data) {
            $scope.song = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

    $scope.updateSong = function() {
        $scope.loadSong($scope.film);
    };

    $scope.loadActor = function (film) {
        //var parameter3 = document.getElementById('eingabe').value;

        $http.get('/api/actor?name=' + film).success(function (data) {
            $scope.actor = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

    $scope.updateActor = function() {
        $scope.loadActor($scope.film);
    };

    $scope.updateAll = function() {
        if ($scope.movie.uri===null || $scope.movie.uri===""){document.getElementById("tableMovies").style.display = "none";}else{document.getElementById("tableMovies").style.display = "inline";};
        if ($scope.actor.uri===null){document.getElementById("tableActors").style.display = "none";}else{document.getElementById("tableActors").style.display = "inline";};
        if ($scope.song.uri===null){document.getElementById("tableSongs").style.display = "none";}else{document.getElementById("tableSongs").style.display = "inline";};
    };

    $scope.loadLatestMovies = function () {

        $scope.latestMovies="Loading latest movies...";
        $http.get('/api/latest').success(function (data){$scope.latestMovies = data;})
            .error(function (data, status){window.alert('Status '+ status);
            });




    };
    $scope.loadLatestMovies();

    $scope.addYoutube = function () {
        var parameter = document.getElementById('eingabe').value;

        $http.get('/api/youtube?watch=' + parameter).success(function (data){$scope.youtube= data;})
            .error(function (data, status){window.alert('Status '+ status);
            });

    };

    $scope.reloadYoutube = function () {
        $scope.addYoutube();
        document.getElementById('ytiframe').location.reload();
    };

    $scope.addYoutube();


    $scope.addPersonPic = function () {
        //var parameter = document.getElementById('eingabe').value;
        var parameter = "Moritz Bleibtreu"
        $http.get('/api/personpic?name=' + parameter).success(function (data){actorpic= data;})
            .error(function (data, status){window.alert('Status '+ status);
            });

    };


    $scope.updateEverything = function(){
      $scope.loadEverything($scope.film);
    };

    $scope.loadEverything = function (film) {
        //var parameter3 = document.getElementById('eingabe').value;

        $http.get('/api/everything?titel=' + film).success(function (data) {
            $scope.everything = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
        $scope.updateAll().delay(3000);
        $scope.addPersonPic();
    };



        //
});
