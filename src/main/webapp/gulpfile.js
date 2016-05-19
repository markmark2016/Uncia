/**
 * Created by baidu on 16/5/14.
 */
var gulp = require('gulp');
var bower = require('gulp-bower');
var clean = require('gulp-clean');
var runSequence = require('gulp-run-sequence');
gulp.task('bower', function () {
    return bower('./bower_components');
});
gulp.task('clean', function () {
    return gulp.src(['./bower_components/*', './resources/dev/common/lib/*'], {read: false})
        .pipe(clean({force: true}));
});
gulp.task('build_lib', function () {

    gulp.src('./bower_components/angular/angular.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular/js/'));

    gulp.src('./bower_components/angular-animate/angular-animate.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular-animate/js/'));

    gulp.src('./bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular-bootstrap/js/'));

    gulp.src('./bower_components/angular-cookies/angular-cookies.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular-cookies/js/'));

    gulp.src('./bower_components/angular-local-storage/dist/angular-local-storage.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular-local-storage/js/'));

    gulp.src('./bower_components/angular-resource/angular-resource.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular-resource/js/'));

    gulp.src('./bower_components/angular-sanitize/angular-sanitize.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular-sanitize/js/'));

    gulp.src('./bower_components/angular-ui-router/release/angular-ui-router.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular-ui-router/js/'));

    gulp.src('./bower_components/angular-ui-router/release/angular-ui-router.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/angular-ui-router/js/'));

    gulp.src('./bower_components/bootstrap/dist/css/bootstrap.min.css')
        .pipe(gulp.dest('./resources/dev/common/lib/bootstrap/css/'));
    gulp.src('./bower_components/bootstrap/dist/fonts/*')
        .pipe(gulp.dest('./resources/dev/common/lib/bootstrap/fonts/'));

    gulp.src('./bower_components/clipboard/dist/clipboard.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/clipboard/js/'));

    gulp.src('./bower_components/font-awesome/css/font-awesome.min.css')
        .pipe(gulp.dest('./resources/dev/common/lib/font-awesome/css/'));
    gulp.src('./bower_components/font-awesome/fonts/*')
        .pipe(gulp.dest('./resources/dev/common/lib/font-awesome/fonts/'));

    gulp.src('./bower_components/jquery/dist/jquery.min.js')
        .pipe(gulp.dest('./resources/dev/common/lib/jquery/js/'));

    gulp.src('./bower_components/requirejs/require.js')
        .pipe(gulp.dest('./resources/dev/common/lib/requirejs/js/'));

    gulp.src('./bower_components/ng-file-upload/ng-file-upload.all.min.js')
     .pipe(gulp.dest('./resources/dev/common/lib/ng-file-upload/js/'));

});

gulp.task('install', function () {
    runSequence('clean', 'bower', 'build_lib');
});