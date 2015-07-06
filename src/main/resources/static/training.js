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
            if($scope.movie.label!=""){
                document.getElementById("tableMovies").style.display = "inline";
                $scope.addYoutube($scope.movie.label);

                //$scope.addMovieCover($scope.movie.label);
            }else{
                document.getElementById("tableMovies").style.display = "none";
            }
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
            if($scope.song.label!=""){
                document.getElementById("tableSongs").style.display = "inline";
                $scope.addPersonPic($scope.song.interpretName);

            }else{
                document.getElementById("tableSongs").style.display = "none";
            }
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
            if($scope.actor.label!=""){
                document.getElementById("tableActors").style.display = "inline";
                $scope.addPersonPic($scope.actor.label);
                if($scope.actor.movies.length!=0) {
                    $scope.addYoutube($scope.actor.movies[0].label);
                }
            }else{
                document.getElementById("tableActors").style.display = "none";
            }
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

    $scope.addYoutube = function (eingabe) {
        //var parameter = document.getElementById('eingabe').value;

        $http.get('/api/youtube?watch=' + eingabe).success(function (data){$scope.youtube= data;})
            .error(function (data, status){window.alert('Status '+ status);
            });

    };
/*
    $scope.reloadYoutube = function () {
        $scope.addYoutube();
        document.getElementById('ytiframe').location.reload();
    };
*/
    //$scope.addYoutube();


    $scope.addPersonPic = function (eingabe) {

        $http.get('/api/personpic?name=' + eingabe).success(function (data){$scope.actorpic= data;})
            .error(function (data, status){window.alert('Status '+ status);
            });

    };
    $scope.addMovieCover = function (eingabe) {

        $http.get('/api/moviecover?name=' + eingabe).success(function (data){$scope.actorpic= data;})
            .error(function (data, status){window.alert('Status '+ status);
            });

    };
/*
    $scope.reloadPic = function () {
        $scope.addPersonPic();
        document.getElementById('bild').location.reload();
    };
*/
   // $scope.addPersonPic();



        //
});

training.directive('onEnter', function() {
    return {
        scope: {onEnter: '&'},
        link: function(scope, element) {
            console.log(scope);
            element.bind("keydown keypress", function(event) {
                if(event.which === 13) {
                    scope.onEnter();
                    scope.$apply();

                }

            });
        }
    }
});