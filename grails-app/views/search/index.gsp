<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->
<html>
<head>


    %{--style sheets--}%
    <link rel="stylesheet" href="${resource(dir:'js/lib/css',file:'reset.css')}" media="screen" type="text/css" />
    <link rel="stylesheet" href="${resource(dir:'js/lib/css',file:'icons.css')}" media="screen" type="text/css" />
    <link rel="stylesheet" href="${resource(dir:'js/lib/css',file:'workspace.css')}" media="screen" type="text/css" />

    %{--<script type="text/javascript" src="${resource(dir:'js', file:'jquery-1.7.1.js')}"></script>--}%
    %{--javascript--}%
    <script type="text/javascript" src="${resource(dir:'js/vendor', file:'jquery-1.9.0.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/vendor', file:'jquery.ui.core.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/vendor', file:'jquery.ui.widget.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/vendor', file:'jquery.ui.position.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/vendor', file:'jquery.ui.menu.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/vendor', file:'jquery.ui.autocomplete.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/vendor', file:'underscore-1.4.3.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/vendor', file:'backbone-0.9.10.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js', file:'visualsearch.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/views', file:'search_box.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/views', file:'search_facet.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/views', file:'search_input.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/models', file:'search_facets.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/models', file:'search_query.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/utils', file:'backbone_extensions.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/utils', file:'hotkeys.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/utils', file:'jquery_extensions.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/utils', file:'search_parser.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/utils', file:'inflector.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/lib/js/templates', file:'templates.js')}"></script>



</head>
<body>


<div class="container" id="VS">



    <div id="search_box_container"></div>
    <div id="search_query">&nbsp;</div>

    <script type="text/javascript" charset="utf-8">
        $(document).ready(function() {
            window.visualSearch = VS.init({
                container  : $('#search_box_container'),
                query      : 'gene: "CFTR" variant: c.24G>A  "Sample": PATID1-UDN1',
                showFacets : true,
                unquotable : [
                    'text',
                    'account',
                    'filter',
                    'access'
                ],
                callbacks  : {
                    search : function(query, searchCollection) {
                        var $query = $('#search_query');
                        $query.stop().animate({opacity : 1}, {duration: 300, queue: false});
                        $query.html('<span class="raquo">&raquo;</span> You searched for: <b>' + searchCollection.serialize() + '</b>');
                        clearTimeout(window.queryHideDelay);
                        window.queryHideDelay = setTimeout(function() {
                            $query.animate({
                                opacity : 0
                            }, {
                                duration: 1000,
                                queue: false
                            });
                        }, 2000);
                    },
                    valueMatches : function(category, searchTerm, callback) {
                        switch (category) {
                            case 'patient':
                                callback([
                                    { value: 'PAT1', label: 'PAT1' },
                                    { value: 'PAT2', label: 'PAT2' },
                                    { value: 'PAT3', label: 'PAT3' }
                                ]);
                                break;
                            case 'publish':
                                callback(['published', 'unpublished', 'draft']);
                                break;
//                            case 'gsg':
//                                callback(['public', 'private', 'protected']);
//                                break;
//                            case 'title':
//                                callback([
//                                    'Pentagon Papers',
//                                    'CoffeeScript Manual',
//                                    'Laboratory for Object Oriented Thinking',
//                                    'A Repository Grows in Brooklyn'
//                                ]);
//                                break;
                            case 'file':
                                callback([
                                    'File1',
                                    'File2',
                                    'File3',
                                    'File4',
                                    'File5',
                                    'File6',
                                    'File7',
                                    'File8'

                                ])
                                break;
                            case 'Sample':
                                callback([
                                    "PATID1-UDN1",
                                    "PATID1-UDN2",
                                    "PATID1-UDN3",
                                    "PATID2-UDN4",
                                    "PATID2-UDN5",
                                    "PATID2-UDN6"
                                ]);
                                break
                            case 'Gene':
                                callback([
                                    "CFTR",
                                    "HEXA",
                                    "BLM",
                                    "SMPD1",
                                    "SMN1"
                                ], {
                                    preserveOrder: true // Otherwise the selected value is brought to the top
                                });
                                break;
                        }
                    },
                    facetMatches : function(callback) {
                        callback([
                            'patient',
                            'publish',
                            'file',
//                            'title',
                            { label: 'Gene',        category: 'Genome' },
                            { label: 'Sample',     category: 'Genome' }
                        ]);
                    }
                }
            });
        });
    </script>





</div>


<g:form method="post">
    <g:remoteLink value="test" controller="Search" action="HttpTest">hsfdjfsda</g:remoteLink>
</g:form>

</body>
</html>
