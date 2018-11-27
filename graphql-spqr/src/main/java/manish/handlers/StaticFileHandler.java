package manish.handlers;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mmaheshwari on 26/11/18.
 */
public class StaticFileHandler {
    static final Logger LOGGER = LoggerFactory.getLogger(StaticFileHandler.class);

    private Vertx vertx;

    public StaticFileHandler(Vertx vertx) {
        this.vertx = vertx;
    }

    public void handlesStatic(RoutingContext routingContext) {
        LOGGER.debug("Entering handlesStatic.");

        routingContext.response().sendFile("play.html");

    }

    public void handleFailure(RoutingContext routingContext) {
    }
}
