package manish.handlers;

import graphql.ExecutionResult;
import graphql.GraphQL;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

//    ExecutionResult executionResult = graphql.execute(ExecutionInput.newExecutionInput()
//            .query(query)
//            .operationName(routingContext.request().get("operationName"))
//            .context(raw)
//            .build());
//    return executionResult.toSpecification();


        ExecutionResult executionResult = graphql.execute(query);

        LOGGER.debug("Execution result: {}", executionResult.getData().toString());
        routingContext.response().end(executionResult.getData().toString());
    }

    public void handleFailure(RoutingContext routingContext) {
        LOGGER.debug("Entering SpqrHandler handleFailure");
    }
}
