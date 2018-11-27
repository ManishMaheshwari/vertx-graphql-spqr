package manish.handlers;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mmaheshwari on 22/11/18.
 */
public class GqlHandler {

    static final Logger LOGGER = LoggerFactory.getLogger(GqlHandler.class);

    private Vertx vertx;

    public GqlHandler(Vertx vertx) {
        this.vertx = vertx;
    }

    public void handleGql(RoutingContext routingContext) {
        LOGGER.debug("Entering handleGql");

        String schema = "type Query{hello: String} schema{query: Query}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello}");

        LOGGER.debug(executionResult.getData().toString());
        routingContext.response().end(executionResult.getData().toString());

    }

    public void handleFailure(RoutingContext routingContext) {
        LOGGER.debug("Entering handleFailure");
    }
}
