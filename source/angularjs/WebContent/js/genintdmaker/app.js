'use strict';

var module = angular.module('genintdmaker', []);

module.directive('prettify', function () {
    return {
        restrict:'A',
        link:function (scope, element, attrs) {
            scope.$watch(attrs.prettify, function (value) {
                element.text(value);
                window.prettyPrint && prettyPrint();
            });
        }
    };
});

module.run(function ($rootScope) {
    $rootScope.init = [];

    /**
     *  Log Levels
     *     key : integer
     *     name : ENUM
     */
    $rootScope.init.log_levels = [
        { key:0, name:'NONE' },
        { key:1, name:'ERROR' },
        { key:2, name:'WARNING' },
        { key:3, name:'TRACE' }
    ];

    /**
     * Nodes
     *    name : string
     *    display-name : string
     *    description : string
     *    type : sink_node | source_node | processing_node
     *    index : int
     *    factory : boolean
     *    library-name : string
     *    parameter : array
     *       name : string
     *       display-name : string
     *       default : string
     *       required : boolean
     *       description : string
     *       dependencies : comma-separated list
     */
    $rootScope.init.nodes = [
        // XML Sink Node
        { 'name':'xml_sink_node', 'display_name':'XML Sink Node', 'description':'The XML Sink Node recursively goes through the given data and builds a hierarchical XML.', 'type':'sink_node', 'index':0, 'factory':true, 'library_name':'xmlout-plugin', 'parameters':[
            { 'name':'filename', 'default':'', 'required':true, 'display_name':'Filename', 'description':'Name of the XML output file. The file will be created if not already existing' },
            { 'name':'rollover-size', 'default':'1024', 'required':false, 'display_name':'Max. File Size', 'description':'The file size limit in Kilo Bytes (KB), which needs to be exceeded in order to start the rollover mechanism' },
            { 'name':'rollover-count', 'default':'5', 'required':false, 'display_name':'Max. Files', 'description':'Number of old files to keep' },
            { 'name':'rollover-wait', 'default':'30', 'required':false, 'display_name':'Wait Time (s)', 'description':'Minimum amount of time (in seconds) to wait before retrying the rollover, in case it failed' },
            { 'name':'root-element', 'default':'message', 'required':false, 'display_name':'XML Root Name', 'description':'Name of the XML root element' }
        ] },
        // Shared Memory Source Node
        { 'name':'shm_source_node', 'display_name':'Shared Memory Source Node', 'description':'The shared memory plugin comes with a source node implementation, which is able to receive messages from a shared memory queue.', 'type':'source_node', 'index':0, 'factory':true, 'library_name':'shm-plugin', 'parameters':[
            { 'name':'queue-name', 'default':'genint_shm_plugin_default', 'required':false, 'display_name':'Queue Name', 'description':'The name of the shared memory queue to use' }
        ] }
    ];

    /**
     * Collection of utility functions
     */
    $rootScope.utils = {
        store:function (namespace, data) {
            if (arguments.length > 1) {
                return localStorage.setItem(namespace, JSON.stringify(data));
            } else {
                var store = localStorage.getItem(namespace);
                return (store && JSON.parse(store)) || [];
            }
        },
        isEmpty:function (text) {
            return (!text || 0 === text.length);
        },
        isBlank:function (text) {
            return (!text || /^\s*$/.test(text));
        }
    };
});
