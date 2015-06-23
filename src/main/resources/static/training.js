var training = angular.module('training', ['ui.bootstrap']);

training.controller('TrainingController', function ($scope, $http, $compile, $sce) {


    $scope.loadMovie = function (film) {
        //var parameter = document.getElementById('eingabe').value;

        $http.get('/api/movie?titel=' + film).success(function (data) {
            $scope.movie = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
        $scope.updateSong();
    };

    $scope.loadActorPic = function (name){

    }


    $scope.updateMovie = function() {
        $scope.loadMovie($scope.film);
    };

    $scope.loadSong = function (film) {
        //var parameter2 = document.getElementById('eingabe').value;

        $http.get('/api/song?name=' + film).success(function (data) {
            $scope.song = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
        $scope.updateActor();
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
        $scope.updateAll().delay(3000);
    };

    $scope.updateActor = function() {
        $scope.loadActor($scope.film);
    };

    $scope.updateAll = function() {
        if ($scope.movie.uri===null){document.getElementById("tableMovies").style.display = "none";}else{document.getElementById("tableMovies").style.display = "inline";};
        if ($scope.actor.uri===null){document.getElementById("tableActors").style.display = "none";}else{document.getElementById("tableActors").style.display = "inline";};
        if ($scope.song.uri===null){document.getElementById("tableSongs").style.display = "none";}else{document.getElementById("tableSongs").style.display = "inline";};
    };

    /* $scope.myFunction = function() {
     if (movie.uri === null) {
     document.getElementById("tableMovies").style.display = "none";
     }
     if ($scope.actor.uri === null) {
     document.getElementById("tableActors").style.display = "none";
     }
     if (song.uri === null) {
     document.getElementById("tableSongs").style.display = "none";
     }
     };*/

    $scope.loadLatestMovies = function () {
        /*TO DO
         Infos via crawler Abrufen und Antwortstring generieren.
         $scope.news="Dummy Element f�r viele tolle Filme";

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

    $scope.addYoutube = function () {
        var parameter = document.getElementById('eingabe').value;

        $http.get('/api/youtube?watch=' + parameter).success(function (data){yt= data;})
            .error(function (data, status){window.alert('Status '+ status);
            });



        $scope.youtube=$sce.trustAsResourceUrl(yt.id);

    };

    $scope.addPersonPic = function () {
        //var parameter = document.getElementById('eingabe').value;
        var parameter = "Moritz Bleibtreu"
        $http.get('/api/personpic?name=' + parameter).success(function (data){actorpic= data;})
            .error(function (data, status){window.alert('Status '+ status);
            });

    };





        //
});
