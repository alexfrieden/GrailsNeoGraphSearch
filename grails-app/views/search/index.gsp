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
    <script type="text/javascript" src="${resource(dir:'js/lib/js', file:'neo_visualsearch.js')}"></script>
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


<section id='content'>
    <div id='search_query'>
        <br>
    </div>
    <div id='search_box_container'></div>
</section>



<g:form method="post">
    <g:remoteLink value="test" controller="Search" action="HttpTest">test facets</g:remoteLink>
</g:form>

</body>
</html>
