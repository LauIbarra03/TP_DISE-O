package server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.config.JavalinConfig;
import java.io.IOException;
import java.util.function.Consumer;
import midware.AuthMiddleware;
import model.entities.CronJob.SchedulerConfig;
import org.quartz.SchedulerException;
import utils.IfEqHelper;
import utils.Initializer;
import utils.JavalinRenderer;
import utils.JsonHelper;
import utils.PrettyProperties;

public class Server implements WithSimplePersistenceUnit {
  private static Javalin app = null;


  public static Javalin app() {
    if (app == null)
      throw new RuntimeException("App no inicializada");
    return app;
  }

  private static Consumer<JavalinConfig> config() {
    Handlebars handlebars = new Handlebars();

    return config -> {
      config.staticFiles.add(staticFiles -> {
        staticFiles.hostedPath = "/";
        staticFiles.directory = "/public";
      });

      handlebars.registerHelper("ifEq", new IfEqHelper());
      handlebars.registerHelper("json", new JsonHelper());

      config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {

        Template template = null;
        try {
          template = handlebars.compile(
              "templates/" + path.replace(".hbs", ""));
          return template.apply(model);
        } catch (IOException e) {
          e.printStackTrace();
          context.status(HttpStatus.NOT_FOUND);
          return "No se encuentra la página indicada...";
        }
      }));

    };

  }

  public void init() throws SchedulerException {
    if (app == null) {
      Integer port = Integer.parseInt(PrettyProperties.getInstance().propertyFromName("server_port"));
      app = Javalin.create(config()).start(port);

      AuthMiddleware.apply(app);

      app.after((ctx) -> {
        entityManager().clear();
        entityManager().close();
      });

      Router.init(app);

      if (Boolean.parseBoolean(PrettyProperties.getInstance().propertyFromName("dev_mode"))) {
        //Initializer.init();
      }

      SchedulerConfig.iniciarScheduler();
    }
  }
}
