package Query

import awhinterface.CypherService
import grails.converters.JSON
import groovy.json.JsonSlurper
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

import java.util.regex.Matcher
import java.util.regex.Pattern




class SearchController {

    def cypherService

    def working() {}

    def index() {}


    def connectedFacets()
    {
        println("CONNECTED FACETS")
        HashMap<Integer, Tuple> facetMap = new HashMap<Integer, Tuple>()    //id, name, value
        def stuff = params
//        String food
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        stuff.each { myParam ->

            if(myParam.toString().startsWith("facets"))
            {
                def thing = myParam
                if (!thing.value.toString().endsWith("]"))
                {
                    Matcher matcher = pattern.matcher(thing.key.toString());
                    List<String> parsedKeys = new ArrayList<String>()
                    while(matcher.find()){
                        parsedKeys.add(matcher.group(1))
                    }
                    java.util.Map.Entry<String,Object> pair1=new java.util.AbstractMap.SimpleEntry<String,Object>(parsedKeys[1],thing.value.toString());
                    facetMap.put(parsedKeys[0].toInteger(),pair1)
                }
            }
        }

        String query = CypherService.getConnectedFacets(facetMap)
        JSONArray jsonArray = new JSONArray()
        List<String> myLabels = cypherService.getLabels(query)
        myLabels.each
                { label->
                    def myproperties = CypherService.getProperties(label)
                    myproperties.each { unparsed ->
                        JSONObject listlabel = new JSONObject()
                        String[] _parsedKVP = unparsed.toString().split('=')
                        String property = _parsedKVP[0]
                        String jsonLabel = "${label}.${property}"
                        listlabel.put('category' , label )
                        listlabel.put('label' , jsonLabel)
                        jsonArray.add(listlabel)
                    }
                }
        render jsonArray as JSON
    }


    def connectedValues()
    {
        println("CONNECTED VALUES")
        HashMap<Integer, Tuple> facetMap = new HashMap<Integer, Tuple>()    //id, name, value
        def stuff = params
        String food
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");

        stuff.each { myParam ->

            if(myParam.toString().startsWith("facets"))
            {
                def kvp = myParam

                if (!kvp.value.toString().endsWith("]"))
                {

                    if (kvp.value.toString() == "")
                    {
//                        println("key is ${kvp.key.toString()}")
//                        println("value is blank")
                        Matcher matcher = pattern.matcher(kvp.key.toString());
                        List<String> parsedKeys = new ArrayList<String>()
                        while(matcher.find()){
                            parsedKeys.add(matcher.group(1))
                        }
                        java.util.Map.Entry<String,Object> pair1=new java.util.AbstractMap.SimpleEntry<String,Object>(parsedKeys[1],kvp.value.toString());
                        facetMap.put(parsedKeys[0].toInteger(),pair1)

                    }
                    else
                    {
                        Matcher matcher = pattern.matcher(kvp.key.toString());
                        List<String> parsedKeys = new ArrayList<String>()
                        while(matcher.find()){
                            parsedKeys.add(matcher.group(1))
                        }
                        java.util.Map.Entry<String,Object> pair1=new java.util.AbstractMap.SimpleEntry<String,Object>(parsedKeys[1],kvp.value.toString());
                        facetMap.put(parsedKeys[0].toInteger(),pair1)
                    }



                }
            }
        }
        String query = CypherService.getConnectedValues(facetMap)
        println(query)
        def myvalues = CypherService.getValuesFromQuery(query)
        JSONArray jsonArray = new JSONArray()
        myvalues.data[0].each { value ->
            JSONObject jsonObject = new JSONObject()
            jsonObject.put("label",value.value)
            jsonObject.put("value",value.value)
            jsonArray.add(jsonObject)
            println(jsonObject)
        }

        println(jsonArray)
        render jsonArray as JSON

    }

    def facets()
    {
        String cypherRest = ConfigurationHolder.getConfig().getProperty('cypherRest')
        JSONArray jsonArray = new JSONArray()
        List<String> myLabels = cypherService.getLabels()
        myLabels.each
        { label->
            def myproperties = CypherService.getProperties(label)
            myproperties.each { unparsed ->
                JSONObject listlabel = new JSONObject()
                String[] _parsedKVP = unparsed.toString().split('=')
                String property = _parsedKVP[0]
                String jsonLabel = "${label}.${property}"
                listlabel.put('category' , label )
                listlabel.put('label' , jsonLabel)
                jsonArray.add(listlabel)
            }
        }
        //println(jsonArray)
        render jsonArray as JSON
    }


    def values()
    {
        String search = params.id
        String[] splitter = search.split('\\.')
        String myLabel = splitter[0]
        String myKey = splitter[1]
        def myvalues = CypherService.getValues(myLabel, myKey)
        JSONArray jsonArray = new JSONArray()
        myvalues.each { value ->
            JSONObject jsonObject = new JSONObject()
            jsonObject.put("label",value)
            jsonObject.put("value",value)
            jsonArray.add(jsonObject)
        }

        //println(jsonObject)
        render jsonArray as JSON

    }

    def GetConnectedFacets() {}

    def HttpTest = {
        String myjson = cypherService.postquery("MATCH (v:VcfRecord) RETURN v;", "http://localhost:7474/db/data/cypher")
        def slurper = new JsonSlurper()
        def result = slurper.parseText(myjson)
//        println(result)
        JSON mydata = result.data["data"]
//        println(result.data["data"])
        println(mydata)
        redirect(controller: "Search", action: "index")
    }


    def GetProperties(category)
    {
        String cypher = "MATCH n:${category} RETURN n LIMIT 1"
//        cypherService.postquery(cypher)["data"].first.first["data"].keys
        cypherService.postquery(cypher,)["data"].first.first["data"].keys

    }

    def download()
    {
        def stuff = params
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        HashMap<Integer,Map<String,Object>> facetMap = new HashMap<Integer,Map<String,Object>>()
        stuff.each { myParam ->

            if(myParam.toString().startsWith("facets"))
            {
                def thing = myParam
                if (!thing.value.toString().endsWith("]"))
                {
                    Matcher matcher = pattern.matcher(thing.key.toString());
                    List<String> parsedKeys = new ArrayList<String>()
                    while(matcher.find()){
                        parsedKeys.add(matcher.group(1))
                    }
                    java.util.Map.Entry<String,Object> pair1=new java.util.AbstractMap.SimpleEntry<String,Object>(parsedKeys[1],thing.value.toString());
                    facetMap.put(parsedKeys[0].toInteger(),pair1)
                }
            }
        }
        String deleteme

        //loop through facet map
        HashMap<Integer, String> myLabels = new HashMap<Integer, String>()
        for(Integer myKey : facetMap)
        {
            myLabels.put(myKey,facetMap.key)

        }
    }

}