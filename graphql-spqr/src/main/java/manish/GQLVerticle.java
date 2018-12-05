package manish;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import manish.handlers.GqlHandler;
import manish.handlers.SpqrHandler;
import manish.handlers.StaticFileHandler;
import manish.service.UserService;
import manish.util.DataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mmaheshwari on 22/11/18.
 */
public class GQLVerticle extends AbstractVerticle {

    static final Logger LOGGER = LoggerFactory.getLogger(GQLVerticle.class);

    public static void main(String[] args) {
        Runner.runExample(GQLVerticle.class);
    }

    private SpqrHandler spqrHandler;
    private GqlHandler gqlHandler;
    private StaticFileHandler staticFileHandlerHandler;

    @Override
    public void start() throws Exception {

//        prepareConfig();

        GraphQL graphql = prepareEngine();
        spqrHandler = new SpqrHandler(vertx, graphql);
        gqlHandler = new GqlHandler(vertx);
        staticFileHandlerHandler = new StaticFileHandler(vertx);

        Router router = Router.router(vertx);

        router.postWithRegex("/graphql.*")
                .handler(BodyHandler.create())
                .handler(spqrHandler::handleGql)
                .failureHandler(spqrHandler::handleFailure);

        router.getWithRegex("/playground.*")
                .handler(BodyHandler.create())
                .handler(staticFileHandlerHandler::handlesStatic)
                .failureHandler(staticFileHandlerHandler::handleFailure);

        router.postWithRegex("/test.*")
                .handler(BodyHandler.create())
                .handler(gqlHandler::handleGql)
                .failureHandler(gqlHandler::handleFailure);

        DataBuilder.build();

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);

    }

    private GraphQL prepareEngine() {
        UserService userService = new UserService();
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withBasePackages("manish")
                .withOperationsFromSingleton(userService)
                .generate();
        GraphQL graphql = new GraphQL.Builder(schema)
               //    .queryExecutionStrategy(null)
                .build();
        return graphql;
    }

    private void prepareConfig() {
        LOGGER.debug("Preparing config...");

        System.setProperty("vertx-config-path", "config.json");
        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.getConfig(ar -> {
            if (ar.failed()) {
                LOGGER.debug("Failed to retrieve configs");
            } else {
                JsonObject config = ar.result();
                LOGGER.debug(config.encodePrettily());
            }
        });
    }

}
