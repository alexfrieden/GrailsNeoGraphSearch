package Query




class SearchController {

    def cypherService

    def index() {}


    def GetFacets() {}


    def GetConnectedFacts() {}

    def HttpTest = {
        def result = cypherService.query("MATCH (v:VcfRecord) RETURN v;", "http://localhost:7474/db/data/cypher")
        println(result)
        redirect(controller: "Search", action: "index")
    }

}