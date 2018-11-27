package manish.handlers;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mmaheshwari on 22/11/18.
 */
public class SpqrHandler {

    static final Logger LOGGER = LoggerFactory.getLogger(SpqrHandler.class);

    private Vertx vertx;

    private GraphQL graphql;

    public SpqrHandler(Vertx vertx, GraphQL graphql) {
        this.vertx = vertx;
        this.graphql = graphql;
    }

    public void handleGql(RoutingContext routingContext) {
        LOGGER.debug("Entering SpqrHandler handleGql");

        String query = routingContext.getBodyAsString();
        LOGGER.debug("request body: {}", query);

        if(query.contains("IntrospectionQuery")){

            LOGGER.debug(">>>> INTROSPECTION QUERY");

            JsonObject json = routingContext.getBodyAsJson();
            String introSpection = json.getString("query");
            LOGGER.debug("Introspection element: {}", introSpection);
            ExecutionResult executionResult = graphql.execute(introSpection);
            LOGGER.debug("executionResult data: {}", executionResult.getData().toString());

            Map<String, Object> mapData = executionResult.getData();
           // String introspectionResult = executionResult.getData().toString();

            Map<String, Object> result = new HashMap<>();
            result.put("data", mapData); // you have to wrap it into a data json key!
            LOGGER.debug("REsult data - {}" , result);
            JsonObject jsonResult = new JsonObject(result);
//            JsonObject jsonResult = new JsonObject(introspectionResult);
            //JSONObject jsonResult = new JSONObject(result);
            LOGGER.debug("Json REsult data - {}" , jsonResult.encodePrettily());
            routingContext.response().end(jsonResult.toString());
        } else if(query.contains("operationName")){
            LOGGER.debug(">>>> Playground QUERY");

            JsonObject json = routingContext.getBodyAsJson();
            String introSpection = json.getString("query");
            LOGGER.debug("Introspection element: {}", introSpection);
            ExecutionResult executionResult = graphql.execute(introSpection);
            LOGGER.debug("executionResult data: {}", executionResult.getData().toString());

            Map<String, Object> mapData = executionResult.getData();
            // String introspectionResult = executionResult.getData().toString();

            Map<String, Object> result = new HashMap<>();
            result.put("data", mapData); // you have to wrap it into a data json key!
            LOGGER.debug("REsult data - {}" , result);
            JsonObject jsonResult = new JsonObject(result);
//            JsonObject jsonResult = new JsonObject(introspectionResult);
            //JSONObject jsonResult = new JSONObject(result);
            LOGGER.debug("Json REsult data - {}" , jsonResult.encodePrettily());
            routingContext.response().end(jsonResult.toString());
        }else{
            LOGGER.debug(">>>> NORMAL QUERY");
            ExecutionResult executionResult = graphql.execute(query);
            LOGGER.debug("Execution result: {}", executionResult.getData().toString());
            routingContext.response().end(executionResult.getData().toString());
        }



//        if (query.has("query") && query.getString("query").indexOf("IntrospectionQuery") != -1) {
//            String introSpection = query.getString("query");
//            Map<String, Object> result = new HashMap<>();
//            result.put("data", graphQLService.query(introSpection)); // you have to wrap it into a data json key!
//            return new JSONObject(result);
//        }


//    ExecutionResult executionResult = graphql.execute(ExecutionInput.newExecutionInput()
//            .query(query)
//            .operationName(json.getString("operationName"))
//            .context(routingContext.getBodyAsString())
//            .build());
    //executionResult.toSpecification();


      //  ExecutionResult executionResult = graphql.execute(query);


    }

    public void handleFailure(RoutingContext routingContext) {
        LOGGER.debug("Entering SpqrHandler handleFailure");
    }
}
