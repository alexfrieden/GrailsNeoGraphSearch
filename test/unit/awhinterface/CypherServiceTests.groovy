package awhinterface

import grails.converters.JSON
import grails.test.mixin.*
import grails.web.JSONBuilder
import groovy.json.JsonSlurper
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import org.junit.*
import sun.security.tools.Pair

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CypherService)
class CypherServiceTests {

    void testquery() {
        def cypherService = new CypherService()
        String cypherRest = ConfigurationHolder.getConfig().getProperty('cypherRest')
        String myjson = cypherService.postquery("START n=node(170) RETURN id(n)",cypherRest)
        def slurper = new JsonSlurper()



        def result = slurper.parseText(myjson)
        println("full response")
        println(result.data[0][0])
//        println("---DATA---")
//        println(result.data.data[0][0])
//        try{
//            def labels = CypherService.getQuery(result.data.labels[0][0])
//            println("---LABEL---")
//            println(labels)
//        }
//        catch(groovy.lang.MissingPropertyException mpe)
//        {
//            println("no label in query")
//        }
    }



    void testListLabel() {
        String cypherRest = ConfigurationHolder.getConfig().getProperty('cypherRest')
        JSONArray jsonArray = new JSONArray()
        for(int i=0; i<4; i++)
        {
            JSONObject listlabel = new JSONObject()
            listlabel.put('category' , 'Gene')
            listlabel.put('label' , 'Gene')
            jsonArray.add(listlabel)
        }
        println(jsonArray)


    }
    void testLabelGrabber()
    {
        def mlist = CypherService.getLabels()
        mlist.each{
            println(it)
        }
    }
    void testGetProperties()
    {
        def myproperties = CypherService.getProperties("VcfRecord")
        myproperties.each {
            println(it)
        }
    }

    void testGetValues()
    {
        def mvalues = CypherService.getValues("VcfRecord", "Contig")
        mvalues.each{
            println(it)

        }
    }
    void testSplit() {
        String unparsed = "[thing.1][thin2g]";
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(unparsed);
        while(matcher.find()){
            System.out.println(matcher.group(1));
        }
    }

    void testConnectedFacetTraversal()
    {
        //HashMap<Integer, Tuple> facetMap
        // unparsed key is facets[0][Sample.name]
//        parsed value is PAT1-UDN1
//        List of keys: 0 0
//        List of keys: 1 Sample.name
        HashMap<Integer, java.util.Map.Entry<String,Object>> facetMap = new HashMap<Integer,java.util.Map.Entry<String,Object>>()
        java.util.Map.Entry<String,Object> pair1=new java.util.AbstractMap.SimpleEntry<String,Object>("Sample.name","PAT1-UDN1");
//        pair.fst("Sample.name")
//        pair.snd("PAT1-UDN1")
        facetMap.put(0,pair1)
        def test = CypherService.getConnectedFacets(facetMap)
        println(test)
    }
    void testConnectedValueTraversal()
    {
        HashMap<Integer, java.util.Map.Entry<String,Object>> facetMap = new HashMap<Integer,java.util.Map.Entry<String,Object>>()
        java.util.Map.Entry<String,Object> pair1=new java.util.AbstractMap.SimpleEntry<String,Object>("Sample.name","PAT1-UDN1");
        java.util.Map.Entry<String,Object> pair2=new java.util.AbstractMap.SimpleEntry<String,Object>("Patient.name","");
        facetMap.put(0,pair1)
        facetMap.put(1,pair2)

        def test = CypherService.getConnectedValues(facetMap)
        println(test)
    }

    void testgetValuesFromQuery()
    {
        String query = "MATCH p=(node0:Sample)--(node1:Patient) WHERE node0.name? = 'PAT1-UDN1' RETURN LAST(p)"
        def test = CypherService.getValuesFromQuery(query)
        println(test.data[0])
    }


}
