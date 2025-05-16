package server;

import config.ServiceLocator;
import controllers.AuthController;
import controllers.ColaboradoresController;
import controllers.ContribucionesController;
import controllers.HeladerasController;
import controllers.IncidentesController;
import controllers.IndexController;
import controllers.PersonaVulnerableController;
import controllers.ProductosController;
import controllers.ReporteFallaTecnicaController;
import controllers.ReportesController;
import controllers.SuscripcionesController;
import controllers.TecnicosController;
import controllers.ViandaController;
import controllers.VisitasTecnicasController;
import io.javalin.Javalin;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.entities.Direccion.Localidad;
import model.entities.TipoRol.TipoRol;
import model.repositories.RepositorioLocalidad;
import utils.Initializer;
import model.entities.Heladera.TestCronjobSensores;
import model.entities.Heladera.TestCronjobApertura;

public class Router {

  public static void init(Javalin app) {

    // Deshabilitar caché globalmente
    app.after(ctx -> {
      ctx.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
      ctx.header("Pragma", "no-cache");
      ctx.header("Expires", "0");
    });

    app.get("/guardar-en-sesion", ctx -> {
      ctx.sessionAttribute("nombre", ctx.queryParam("nombre"));
      ctx.result("Variable de sesion guardada");
    });

    app.get("/error_test", ctx -> {
      ctx.render("errores/403.hbs");
    });

    app.error(404, ctx -> {
      Map<String, Object> model = new HashMap<>();
      ctx.render("errores/404.hbs", model);
    });

    app.get("/", ServiceLocator.instanceOf(AuthController.class)::index);
    app.get("/login", ServiceLocator.instanceOf(AuthController.class)::indexLogin);
    app.post("/login", ServiceLocator.instanceOf(AuthController.class)::procesarLogin);
    app.get("/logout", ServiceLocator.instanceOf(AuthController.class)::logout);
    app.get("/registro_persona_humana", ServiceLocator.instanceOf(ColaboradoresController.class)::formRegistroHumano);
    app.post("/registro_persona_humana", ServiceLocator.instanceOf(ColaboradoresController.class)::saveRegistroHumano);
    app.get("/registro_persona_juridica",
        ServiceLocator.instanceOf(ColaboradoresController.class)::formRegistroJuridico);
    app.post("/registro_persona_juridica",
        ServiceLocator.instanceOf(ColaboradoresController.class)::saveRegistroJuridico);
    app.get("/registro_tecnico", ServiceLocator.instanceOf(TecnicosController.class)::formTecnicosRegistro);
    app.post("/registro_tecnico", ServiceLocator.instanceOf(TecnicosController.class)::saveRegistroTecnico);

    app.get("/panel", ServiceLocator.instanceOf(IndexController.class)::indexRender);

    app.get("/perfil/colaborador/{id}", ServiceLocator.instanceOf(ColaboradoresController.class)::perfilUsuario);
    app.get("/perfil/tecnico/{id}", ServiceLocator.instanceOf(TecnicosController.class)::perfilUsuario);

    app.get("/colaboradores", ServiceLocator.instanceOf(ColaboradoresController.class)::index, TipoRol.ADMIN);
    app.post("/colaboradores", ServiceLocator.instanceOf(ColaboradoresController.class)::save, TipoRol.ADMIN);
    app.get("/colaboradores/nuevo", ServiceLocator.instanceOf(ColaboradoresController.class)::create, TipoRol.ADMIN);

    app.get("/colaboradores/detalle_colaborador/{id}",
        ServiceLocator.instanceOf(ColaboradoresController.class)::mostrarDetalleColaborador, TipoRol.ADMIN);

    app.get("/suscripciones", ServiceLocator.instanceOf(SuscripcionesController.class)::index, TipoRol.PERSONA_HUMANA);
    app.post("/suscripciones", ServiceLocator.instanceOf(SuscripcionesController.class)::save, TipoRol.PERSONA_HUMANA);
    app.get("/suscripciones/nueva", ServiceLocator.instanceOf(SuscripcionesController.class)::create,
        TipoRol.PERSONA_HUMANA);
    app.post("/suscripciones/{id}/eliminacion", ServiceLocator.instanceOf(SuscripcionesController.class)::delete,
        TipoRol.PERSONA_HUMANA);
    app.get("/colaboraciones", ServiceLocator.instanceOf(ContribucionesController.class)::listaColaboracion);

    app.get("/colaboraciones/detalle_colaboracion/{id}",
        ServiceLocator.instanceOf(ContribucionesController.class)::detalleColaboracion);
    app.get("/colaboraciones/eliminar_colaboracion/{id}",
        ServiceLocator.instanceOf(ContribucionesController.class)::delete);

    app.get("/colaboraciones/formulario", ServiceLocator.instanceOf(ContribucionesController.class)::index);

    app.get("/colaboraciones/formulario/donacion_vianda",
        ServiceLocator.instanceOf(ContribucionesController.class)::formDonacionVianda, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);
    app.post("/colaboraciones/formulario/donacion_vianda",
        ServiceLocator.instanceOf(ContribucionesController.class)::saveDonacionVianda, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);

    app.get("/colaboraciones/formulario/donacion_dinero",
        ServiceLocator.instanceOf(ContribucionesController.class)::formDonacionDinero);
    app.post("/colaboraciones/formulario/donacion_dinero",
        ServiceLocator.instanceOf(ContribucionesController.class)::saveDonacionDinero);
    app.get("/colaboraciones/formulario/editar_donacion_dinero/{id}",
        ServiceLocator.instanceOf(ContribucionesController.class)::editarDonacionDinero);
    app.post("/colaboraciones/formulario/editar_donacion_dinero/{id}",
        ServiceLocator.instanceOf(ContribucionesController.class)::updateDonacionDinero);

    app.get("/colaboraciones/formulario/distribucion_viandas",
        ServiceLocator.instanceOf(ContribucionesController.class)::formDistribucionViandas, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);
    app.post("/colaboraciones/formulario/distribucion_viandas",
        ServiceLocator.instanceOf(ContribucionesController.class)::saveDistribuirViandas, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);

    app.get("/colaboraciones/formulario/hacerse_cargo_heladera",
        ServiceLocator.instanceOf(ContribucionesController.class)::formHacerseCargoHeladera, TipoRol.ADMIN,
        TipoRol.PERSONA_JURIDICA);
    app.post("/colaboraciones/formulario/hacerse_cargo_heladera",
        ServiceLocator.instanceOf(ContribucionesController.class)::saveFormHacerseCargoHeladera, TipoRol.ADMIN,
        TipoRol.PERSONA_JURIDICA);
    app.get("/colaboraciones/formulario/editar_hacerse_cargo_heladera/{id}",
        ServiceLocator.instanceOf(ContribucionesController.class)::editarHacerseCargoHeladera, TipoRol.ADMIN,
        TipoRol.PERSONA_JURIDICA);

    // TODO
    // app.post("/colaboraciones/formulario/editar_hacerse_cargo_heladera/{id}",
    // ServiceLocator.instanceOf(ContribucionesController.class)::updateHacerseCargoHeladera,
    // TipoRol.ADMIN, TipoRol.PERSONA_JURIDICA);

    app.get("/colaboraciones/formulario/realizar_oferta",
        ServiceLocator.instanceOf(ContribucionesController.class)::formRealizarOferta, TipoRol.ADMIN,
        TipoRol.PERSONA_JURIDICA);
    app.post("/colaboraciones/formulario/realizar_oferta",
        ServiceLocator.instanceOf(ContribucionesController.class)::savePublicarProducto, TipoRol.ADMIN,
        TipoRol.PERSONA_JURIDICA);

    app.get("/colaboraciones/formulario/registrar_vulnerable",
        ServiceLocator.instanceOf(ContribucionesController.class)::formRegistroPersonaVulnerable, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);
    app.post("/colaboraciones/formulario/registrar_vulnerable",
        ServiceLocator.instanceOf(ContribucionesController.class)::saveRegistrarPersonaVulnerable, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);

    app.get("/heladeras", ServiceLocator.instanceOf(HeladerasController.class)::index);
    app.get("/heladeras/detalle_heladera/{id}", ServiceLocator.instanceOf(HeladerasController.class)::detalleHeladera);
    app.get("/heladeras/editar_heladera/{id}",
        ServiceLocator.instanceOf(HeladerasController.class)::mostrarFormularioEdicion);
    app.put("/heladeras/editar_heladera/{id}", ServiceLocator.instanceOf(HeladerasController.class)::update);
    app.get("/heladeras/eliminar_heladera/{id}", ServiceLocator.instanceOf(HeladerasController.class)::delete,
        TipoRol.ADMIN, TipoRol.PERSONA_JURIDICA);
    app.get("/heladeras/mapa-heladeras", ServiceLocator.instanceOf(HeladerasController.class)::mostrarMapa);

    app.get("/viandas", ServiceLocator.instanceOf(ViandaController.class)::index);
    app.get("/viandas/detalle_vianda/{id}", ServiceLocator.instanceOf(ViandaController.class)::detalleVianda);
    app.post("/viandas/detalle_vianda/{id}", ServiceLocator.instanceOf(ViandaController.class)::marcarEntregada,
        TipoRol.ADMIN, TipoRol.PERSONA_HUMANA);
    app.get("/viandas/editar_vianda/{id}", ServiceLocator.instanceOf(ViandaController.class)::formEdicionVianda,
        TipoRol.ADMIN, TipoRol.PERSONA_HUMANA);
    app.post("/viandas/editar_vianda/{id}", ServiceLocator.instanceOf(ViandaController.class)::update, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);
    app.get("/viandas/eliminar_vianda/{id}", ServiceLocator.instanceOf(ViandaController.class)::delete, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);

    app.get("/persona_vulnerable", ServiceLocator.instanceOf(PersonaVulnerableController.class)::index);
    app.get("/persona_vulnerable/detalle_vulnerable/{id}",
        ServiceLocator.instanceOf(PersonaVulnerableController.class)::detalleVulnerable);
    app.get("/persona_vulnerable/editar_vulnerable/{id}",
        ServiceLocator.instanceOf(PersonaVulnerableController.class)::mostrarFormularioEdicion, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);
    app.post("/persona_vulnerable/editar_vulnerable/{id}",
        ServiceLocator.instanceOf(PersonaVulnerableController.class)::update, TipoRol.ADMIN, TipoRol.PERSONA_HUMANA);
    app.get("/persona_vulnerable/eliminar_vulnerable/{id}",
        ServiceLocator.instanceOf(PersonaVulnerableController.class)::delete, TipoRol.ADMIN, TipoRol.PERSONA_HUMANA);

    app.get("/suscripcion", ServiceLocator.instanceOf(SuscripcionesController.class)::index, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA);
    app.get("/suscripcion/formulario_suscripcion", ServiceLocator.instanceOf(SuscripcionesController.class)::create,
        TipoRol.ADMIN, TipoRol.PERSONA_HUMANA);
    app.post("/suscripcion/formulario_suscripcion", ServiceLocator.instanceOf(SuscripcionesController.class)::save,
        TipoRol.ADMIN, TipoRol.PERSONA_HUMANA);
    app.get("/suscripcion/detalle_suscripcion/{id}", ServiceLocator.instanceOf(SuscripcionesController.class)::show,
        TipoRol.ADMIN, TipoRol.PERSONA_HUMANA);
    app.get("/suscripcion/eliminar_suscripcion/{id}", ServiceLocator.instanceOf(SuscripcionesController.class)::delete,
        TipoRol.ADMIN, TipoRol.PERSONA_HUMANA);

    app.get("/reportes", ServiceLocator.instanceOf(ReportesController.class)::index, TipoRol.ADMIN,
        TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.post("/reportes", ServiceLocator.instanceOf(ReportesController.class)::generarReportes, TipoRol.ADMIN);

    app.get("/incidentes", ServiceLocator.instanceOf(IncidentesController.class)::index);
    app.get("/incidentes/{id}", ServiceLocator.instanceOf(IncidentesController.class)::show);

    app.get("/visitasTecnicas", ServiceLocator.instanceOf(VisitasTecnicasController.class)::index);
    app.get("/visitasTecnicas/formulario_visitaTecnica",
        ServiceLocator.instanceOf(VisitasTecnicasController.class)::create);
    app.post("/visitasTecnicas/formulario_visitaTecnica",
        ServiceLocator.instanceOf(VisitasTecnicasController.class)::save);
    app.get("/visitasTecnicas/{id}", ServiceLocator.instanceOf(VisitasTecnicasController.class)::show);

    app.get("/reportar_falla_tecnia", ServiceLocator.instanceOf(ReporteFallaTecnicaController.class)::index,
        TipoRol.ADMIN, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.post("/reportar_falla_tecnia", ServiceLocator.instanceOf(ReporteFallaTecnicaController.class)::save,
        TipoRol.ADMIN, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);

    app.get("/tienda", ServiceLocator.instanceOf(ProductosController.class)::index);
    app.get("/productos", ServiceLocator.instanceOf(ProductosController.class)::allProductsOwned);
    app.get("/producto/{id}", ServiceLocator.instanceOf(ProductosController.class)::show);
    app.post("/producto/{id}", ServiceLocator.instanceOf(ProductosController.class)::buy);

    app.get("/solicitudApertura/formulario_solicitudApertura",
        ServiceLocator.instanceOf(HeladerasController.class)::mostrarFormularioApertura, TipoRol.PERSONA_HUMANA);
    app.post("/solicitudApertura/formulario_solicitudApertura",
        ServiceLocator.instanceOf(HeladerasController.class)::saveSolicitudApertura, TipoRol.PERSONA_HUMANA);

    // Para obtener las localidades de una provincia
    app.get("/api/localidades/{provincia}", ctx -> {
      String provinciaNombre = ctx.pathParam("provincia");
      System.out.println(provinciaNombre);

      List<Localidad> localidades = new RepositorioLocalidad().buscarPorProvincia(provinciaNombre);
      List<String> nombresLocalidades = localidades.stream()
          .filter(localidad -> !localidad.getTecnicos().isEmpty())
          .map(localidad -> localidad.getNombre()).collect(Collectors.toList());

      ctx.json(nombresLocalidades);
    });

    // Ruta para cargar el archivo CSV de colaboradores
    app.post("/subir-csv", ServiceLocator.instanceOf(ColaboradoresController.class)::procesarCSV, TipoRol.ADMIN);

    // Esto es para que no se cacheen los reportes viejos y se vean los actualizados
    app.get("/reportes/{reporte}", ctx -> {
      String reporte = ctx.pathParam("reporte");
      String rutaArchivo = "/src/main/resources/public/reportes/" + reporte;

      File archivo = new File(System.getProperty("user.dir") + rutaArchivo);
      if (archivo.exists()) {
        ctx.header("Cache-Control", "no-store, no-cache, must-revalidate, proxy-revalidate");
        ctx.header("Pragma", "no-cache");
        ctx.header("Expires", "0");
        ctx.header("Surrogate-Control", "no-store");

        ctx.contentType("application/pdf");
        ctx.result(new FileInputStream(archivo));
      } else {
        ctx.status(404).result("Reporte no encontrado");
      }
    });

    // Esto es para la imagenes cargadas
    app.get("/fotos_reportes/{filename}", ctx -> {
      String filename = ctx.pathParam("filename");
      File file = new File("src/main/resources/public/fotos_reportes/" + filename);
      if (file.exists()) {
        ctx.result(new FileInputStream(file)).contentType("image/jpeg");
      } else {
        ctx.status(404).result("File not found");
      }
    });

    app.get("/fotos_visita_tecnica/{filename}", ctx -> {
      String filename = ctx.pathParam("filename");
      File file = new File("src/main/resources/public/fotos_visita_tecnica/" + filename);

      if (file.exists()) {
        ctx.result(new FileInputStream(file)).contentType("image/jpeg");
      } else {
        ctx.status(404).result("File not found");
      }
    });

    app.get("/fotos_productos/{filename}", ctx -> {
      String filename = ctx.pathParam("filename");
      File file = new File("src/main/resources/public/fotos_productos/" + filename);

      if (file.exists()) {
        ctx.result(new FileInputStream(file)).contentType("image/jpeg");
      } else {
        ctx.status(404).result("File not found");
      }
    });

    app.get("/init", ctx -> {
      if (Initializer.isInitialized()) {
        ctx.status(400).result("La inicializacion ya se realizo. No se puede ejecutar de nuevo.");
        return;
      }

      Initializer.init();
      ctx.result("Base de datos inicializada");
    });

    app.get("/init/restart", ctx -> {
      Initializer.restart();
      ctx.result("Se puede volver a inicializar la base de datos (se debe hacer un drop antes, sino, va a agregar nuevos datos)");
    });

    app.get("/testcronjobsensores", ctx -> {
      String heladera1Id = ctx.queryParam("heladera1Id");
      String mensajeMovimiento = ctx.queryParam("mensajeMovimiento");
      String heladera2Id = ctx.queryParam("heladera2Id");
      String mensajeTemperatura = ctx.queryParam("mensajeTemperatura");


      TestCronjobSensores.main(new String[] {heladera1Id, mensajeMovimiento, heladera2Id, mensajeTemperatura});
      ctx.result("Cronjob ejecutado");
    });

    app.get("/testcronjobapertura", ctx -> {
      String colaboradorId = ctx.queryParam("colaboradorId");
      String heladeraId = ctx.queryParam("heladeraId");
      String viandaId = ctx.queryParam("viandaId");

      TestCronjobApertura.main(new String[] {colaboradorId, heladeraId, viandaId});
      ctx.result("Cronjob ejecutado");
    });

  }
}
