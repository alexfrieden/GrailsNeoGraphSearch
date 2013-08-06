package awhinterface

import grails.converters.JSON
import grails.test.mixin.*
import grails.web.JSONBuilder
import groovy.json.JsonSlurper
import org.codehaus.groovy.grails.web.json.JSONObject
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CypherService)
class CypherServiceTests {

    void testquery() {
//        fail "Implement me"
        def cypherService = new CypherService()
        String myjson = cypherService.query("START n=node(170) RETURN n","http://localhost:7474/db/data/cypher")
//        println(myjson)
        def slurper = new JsonSlurper()
//        def result = slurper.parseText('{"person":{"name":"Guillaume","age":33,"pets":["dog","cat"]}}')

//        println(result.person.name)
//        println(result.person.age)
//        println(result.person.pets.size())
//        println(result.person.pets[0])
//        println(result.person.pets[1])
//
//        assert result.person.name == "Guillaume"
//        assert result.person.age == 33
//        assert result.person.pets.size() == 2
//        assert result.person.pets[0] == "dog"
//        assert result.person.pets[1] == "cat"

        def result = slurper.parseText(myjson)
        println(result)
        println(result.data.data)
    }


}
