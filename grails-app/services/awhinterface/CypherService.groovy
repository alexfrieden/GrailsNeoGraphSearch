package awhinterface

import grails.plugins.rest.client.RestBuilder
import groovy.json.JsonBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.URIBuilder

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import java.util.Map



class CypherService {





    def postquery(String statement, Myparams, String restLocation) {
        def rest = new RestBuilder( )
        def resp = rest.post( restLocation ) {
            headers.'X-Stream' = 'true'
            body(new JsonBuilder( query: statement ,params: Myparams).toString())
        }
        return resp.json;

    }
    def postquery(String statement, String restLocation) {
        def rest = new RestBuilder( )
        def resp = rest.post( restLocation ) {
            headers.'X-Stream' = 'true'
            query = statement
        }
        return resp.json;

    }


    static def postText(String baseUrl, String path, query, method = Method.POST) {
        try {
            def ret = null
            def http = new HTTPBuilder(baseUrl)

            // perform a POST request, expecting TEXT response
            http.request(method, ContentType.TEXT) {
                uri.path = path
                uri.query = query
                headers.'X-Stream' = true

                // response handler for a success response code
                response.success = { resp, reader ->
                    println "response status: ${resp.statusLine}"
                    println 'Headers: -----------'
                    resp.headers.each { h ->
                        println " ${h.name} : ${h.value}"
                    }

                    ret = reader.getText()

                    println 'Response data: -----'
                    println ret
                    println '--------------------'
                }
            }
            return ret

        } catch (groovyx.net.http.HttpResponseException ex) {
            ex.printStackTrace()
            return null
        } catch (java.net.ConnectException ex) {
            ex.printStackTrace()
            return null
        }
    }

    static def getText(String baseUrl, String path, query) {
        return postText(baseUrl, path, query, Method.GET)
    }

}

