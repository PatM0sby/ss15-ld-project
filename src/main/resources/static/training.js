var training = angular.module('training', ['ui.bootstrap']);

training.controller('TrainingController', function ($scope, $http, $compile, $sce) {


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




       /* var makeIframe = document.createElement("iframe");
        makeIframe.setAttribute("src", "http://www.youtube.com/embed/LJP1DphOWPs?autoplay=0");
        makeIframe.setAttribute("id", "player");
        makeIframe.setAttribute("width", "420");
        makeIframe.setAttribute("height", "315");*/
       /* var frame=$compile('<iframe id="player" width="420" height="315" ng-src="http://www.youtube.com/embed/LJP1DphOWPs?autoplay=0" </iframe>')($scope)
        angular.element(document.getElementById('YoutubePlayer')).append
        (frame);*/


    };






        //
});
