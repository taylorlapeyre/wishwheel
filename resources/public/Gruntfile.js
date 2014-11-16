/**
 * Gruntfile
 *
 *
 *
 * The build file, similar to a makefile, pom.xml, etc.
 *
 * DATE            BY        CHANGE REF  DESCRIPTION
 * ========    ==========    =========== =============
 * 11/7/14     Alex Clavelle 9057e41     initial
 *
 *
 */

module.exports = function (grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        less: {
            dev: {
                files: [
                    {
                        expand: true,
                        cwd: 'styles/',
                        src: ['*.less'],
                        dest: 'styles/',
                        ext: '.css'
                    }
                ]
            }
        },
        watch: {
            assets: {
                // Assets to watch:
                files: ['styles/**/*', 'js/modules/**/*', 'js/*'],
                // When assets are changed:
                tasks: ['default']
            }
        }
    });

    grunt.registerTask('default', [
        'compile',
        'watch'
    ]);
    grunt.registerTask('compile', [
        'less:dev'
    ]);

    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-watch');
};