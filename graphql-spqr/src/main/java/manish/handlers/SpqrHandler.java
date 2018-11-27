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

        if (query.contains("operationName")) {
            //Playground queries are Json based
            servePlaygroundQuery(routingContext);
        } else {
            //Others could be normal GQL
            serveOtherQueries(routingContext, query);
        }
    }

    private void serveOtherQueries(RoutingContext routingContext, String query) {
        ExecutionResult executionResult = graphql.execute(query);
        LOGGER.debug("Execution result: {}", executionResult.getData().toString());
        routingContext.response().end(executionResult.getData().toString());
    }

    private void servePlaygroundQuery(RoutingContext routingContext) {
        JsonObject json = routingContext.getBodyAsJson();
        String queryString = json.getString("query");
        String operationName = json.getString("operationName");
        ExecutionResult executionResult = graphql.execute(ExecutionInput.newExecutionInput()
                .query(queryString)
                .operationName(operationName)
                .context(routingContext.getBodyAsString())
                .build());
        executionResult.toSpecification();
        Map<String, Object> mapData = executionResult.getData();
        Map<String, Object> result = new HashMap<>();
        result.put("data", mapData);
        JsonObject jsonResult = new JsonObject(result);
        LOGGER.debug("Json Result data - {}", jsonResult.encodePrettily());
        routingContext.response().end(jsonResult.toString());
    }

    public void handleFailure(RoutingContext routingContext) {
        LOGGER.debug("Entering SpqrHandler handleFailure");
    }
}
