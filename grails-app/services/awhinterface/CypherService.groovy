package awhinterface

import grails.plugins.rest.client.RestBuilder
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.URIBuilder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import sun.security.tools.Pair

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import java.util.Map



class CypherService {


    static String cypherRest = ConfigurationHolder.getConfig().getProperty('cypherRest')
    static def slurper = new JsonSlurper()



    static def postquery(String statement, Myparams, String restLocation) {
        def rest = new RestBuilder( )
        def resp = rest.post( restLocation ) {
            headers.'X-Stream' = 'true'
            body(new JsonBuilder( query: statement ,params: Myparams).toString())
            auth("GsgNeo", "HjdfDcfaUHJ828aZOjcg")
        }
        return resp.json;

    }
    static def postquery(String statement, String restLocation) {
        def rest = new RestBuilder( )
        def resp = rest.post( restLocation ) {
            headers.'X-Stream' = 'true'
            query = statement
            auth("GsgNeo", "HjdfDcfaUHJ828aZOjcg")
        }
        return resp.json;

    }


//    static def postText(String baseUrl, String path, query, method = Method.POST) {
//        try {
//            def ret = null
//            def http = new HTTPBuilder(baseUrl)
//
//            // perform a POST request, expecting TEXT response
//            http.request(method, ContentType.TEXT) {
//                uri.path = path
//                uri.query = query
//                headers.'X-Stream' = true
//
//                // response handler for a success response code
//                response.success = { resp, reader ->
//                    println "response status: ${resp.statusLine}"
//                    println 'Headers: -----------'
//                    resp.headers.each { h ->
//                        println " ${h.name} : ${h.value}"
//                    }
//
//                    ret = reader.getText()
//
//                    println 'Response data: -----'
//                    println ret
//                    println '--------------------'
//                }
//            }
//            return ret
//
//        } catch (groovyx.net.http.HttpResponseException ex) {
//            ex.printStackTrace()
//            return null
//        } catch (java.net.ConnectException ex) {
//            ex.printStackTrace()
//            return null
//        }
//    }

//    static def getQuery(String restLocation)
//    {
//        def rest = new RestBuilder()
//        def resp = rest.get(restLocation){
//
//        }
//        return resp.json;
//    }

    static List<String> getLabels()
    {
        def cypherService = new CypherService()
        String myjson = cypherService.postquery("START n=node(*) RETURN DISTINCT labels(n)",cypherRest)
        def result = slurper.parseText(myjson)
        List<String> labels = new ArrayList<>()
        result.data.each {
            String item = it[0][0]
            labels.add(item)
        }
        return labels
    }
    static List<String> getLabels(String query)
    {
        def cypherService = new CypherService()
        String myjson = cypherService.postquery(query,cypherRest)
        def result = slurper.parseText(myjson)
        List<String> labels = new ArrayList<>()
        result.data.each {
            String item = it[0][0]
            labels.add(item)
        }
        return labels
    }




    static def getProperties(String category)
    {
        String cypher = "MATCH n:${category} RETURN n LIMIT 1"
        String myjson = CypherService.postquery(cypher,cypherRest)
        def result = slurper.parseText(myjson)
        return result.data.data[0][0]

    }
    static List<String> getValuesFromQuery(String query)
    {
        String myJson = CypherService.postquery(query, cypherRest)
        def result = slurper.parseText(myJson)
        List<String> myValues = new ArrayList<String>()
        result.data.each {
            myValues.add(it[0])
        } as List<String>
        return myValues
    }



    static List<String> getValues(String label, String key)
    {
        String cypherString = "MATCH node:${label}  WHERE HAS(node.${key}) RETURN DISTINCT node.${key} AS value ORDER BY value LIMIT 25"
        String myJson = CypherService.postquery(cypherString, cypherRest)
        def result = slurper.parseText(myJson)
        List<String> myValues = new ArrayList<String>()
        result.data.each {
            myValues.add(it[0])
        } as List<String>
        return myValues
    }

    static def getConnectedFacets(HashMap<Integer,Map<String,Object>> facetMap)
    {
        StringBuilder CypherString = new StringBuilder()
        java.util.Map.Entry<String,Object> pair = facetMap[0]
        List<Object> filterValues = new ArrayList<Object>()
        List<String> traversalFilters = new ArrayList<String>()
        List<String> traversalProperties = new ArrayList<String>()

        for(Integer key : facetMap.keySet())
        {
            java.util.Map.Entry<String,Object> kvp = facetMap[key]
            String[] splitter = kvp.key.split("\\.")


            if (kvp.value.toString().isInteger())
            {
                filterValues.add(kvp.value.toString().toInteger())
                traversalFilters.add(splitter[0])  //added label
                traversalProperties.add(splitter[1])  //added property name
            }
            else
            {
                filterValues.add(kvp.value.toString())
                traversalFilters.add(splitter[0])  //added label
                traversalProperties.add(splitter[1])  //added property name
            }
        }
        //now create queries
        StringBuilder MATCH = new StringBuilder("MATCH p=")
        for(int i=0;i<traversalFilters.size();i++)
        {
            MATCH.append("(node${i}:${traversalFilters[i]})--")
        }
        MATCH.append("(m)")


        StringBuilder WHERE = new StringBuilder(" WHERE ")
        for(int j = 0;j<traversalProperties.size();j++)
        {

            if (filterValues[j].toString().isInteger())
            {
                WHERE.append("node${j}.${traversalProperties[j]}? = ${filterValues[j]} AND ")
            }
            else
            {
                WHERE.append("node${j}.${traversalProperties[j]}? = '${filterValues[j]}' AND ")
            }

//            }

        }
        WHERE.delete(WHERE.size() - 4,WHERE.size())

        String RETURN = "RETURN DISTINCT labels(LAST(p))"

        CypherString.append(MATCH.toString())
        CypherString.append(WHERE.toString())
        CypherString.append(RETURN)

        return CypherString.toString()

    }
    static def getConnectedValues(HashMap<Integer,Map<String,Object>> facetMap)
    {
        StringBuilder CypherString = new StringBuilder()
        List<Object> filterValues = new ArrayList<Object>()
        List<String> traversalFilters = new ArrayList<String>()
        List<String> traversalProperties = new ArrayList<String>()

        for(Integer key : facetMap.keySet())
        {
            java.util.Map.Entry<String,Object> kvp = facetMap[key]
            String[] splitter = kvp.key.split("\\.")
            if (kvp.value.toString().isInteger())
            {
                filterValues.add(kvp.value.toString().toInteger())
                traversalFilters.add(splitter[0])  //added label
                traversalProperties.add(splitter[1])  //added property name
            }
            else
            {
                filterValues.add(kvp.value.toString())
                traversalFilters.add(splitter[0])  //added label
                traversalProperties.add(splitter[1])  //added property name
            }
        }
        //now create queries
        StringBuilder MATCH = new StringBuilder("MATCH p=")
        for(int i=0;i<traversalFilters.size();i++)
        {
            MATCH.append("(node${i}:${traversalFilters[i]})--")
        }
        MATCH.delete(MATCH.size()-2,MATCH.size())
        StringBuilder WHERE = new StringBuilder(" WHERE ")
        for(int j = 0;j<traversalProperties.size()-1;j++)
        {

            if (filterValues[j].toString().isInteger())
            {
                WHERE.append("node${j}.${traversalProperties[j]}? = ${filterValues[j]} AND ")
            }
            else
            {
                WHERE.append("node${j}.${traversalProperties[j]}? = '${filterValues[j]}' AND ")
            }
        }
        String returnedProperty = traversalProperties[traversalProperties.size()-1]
        WHERE.delete(WHERE.size() - 4,WHERE.size())
        // RETURN LAST(EXTRACT(n in NODES(p) : n.Lower?)) AS value;
        String RETURN = "RETURN LAST(EXTRACT(n in NODES(p) : n.${returnedProperty}?)) AS value"
        CypherString.append(MATCH.toString())
        CypherString.append(WHERE.toString())
        CypherString.append(RETURN)

        return CypherString.toString()
    }

    static def getDataFromMap(HashMap<Integer,String> labelMap)
    {
        StringBuilder query = new StringBuilder()
        StringBuilder MATCH = new StringBuilder("MATCH ")
        StringBuilder RETURN = new StringBuilder("RETURN ")
        for(Integer key : labelMap.keySet())
        {
            MATCH.append("(node${key}:${labelMap[key]})--")
            RETURN.append("node${key},")
        }
        MATCH.delete(MATCH.size()-2,MATCH.size())
        RETURN.delete(RETURN.size()-1,RETURN.size())
        RETURN.append(";")

//        String myjson = CypherService.postquery(cypher,cypherRest)
        query.append(MATCH)
        query.append(" ")
        query.append(RETURN)
//        return query.toString()
        String myjson = CypherService.postquery(query.toString(),cypherRest)
        def result = slurper.parseText(myjson)
        return result;

    }

    @Deprecated
    static Integer getIDFromFacet(String propertyName, Object value)
    {
        String myjson = CypherService.postquery("START n=node(170) RETURN id(n)",cypherRest)
        def result = slurper.parseText(myjson)
        return result.data[0][0].toInteger()
    }

}

