package config;


import controllers.AuthController;
import controllers.BaseController;
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
import java.util.HashMap;
import java.util.Map;
import model.repositories.RepositorioCalle;
import model.repositories.RepositorioCiudad;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioComida;
import model.repositories.RepositorioContribucion;
import model.repositories.RepositorioDeDirecciones;
import model.repositories.RepositorioDeIncidentes;
import model.repositories.RepositorioDeModelos;
import model.repositories.RepositorioDeProductos;
import model.repositories.RepositorioDeRubros;
import model.repositories.RepositorioHeladera;
import model.repositories.RepositorioLocalidad;
import model.repositories.RepositorioMedioDeContacto;
import model.repositories.RepositorioPersonaVulnerable;
import model.repositories.RepositorioProvincia;
import model.repositories.RepositorioPuntoGeografico;
import model.repositories.RepositorioRegistroActividadHeladera;
import model.repositories.RepositorioRegistroMensaje;
import model.repositories.RepositorioRegistroViandasHeladera;
import model.repositories.RepositorioRegistroVisitaTecnica;
import model.repositories.RepositorioSuscripcion;
import model.repositories.RepositorioTarjeta;
import model.repositories.RepositorioTecnico;
import model.repositories.RepositorioTipoOrganizacion;
import model.repositories.RepositorioTipoPersona;
import model.repositories.RepositorioTipoSuscripcion;
import model.repositories.RepositorioUsuario;
import model.repositories.RepositorioViandas;
import utils.GeneradorDeReportes;

public class ServiceLocator {
  private static final Map<String, Object> instances = new HashMap<>();


  @SuppressWarnings("unchecked")
  public static <T> T instanceOf(Class<T> componentClass) {
    String componentName = componentClass.getName();

    if (!instances.containsKey(componentName)) {
      /****************************** CONTROLLERS ******************************/
      if (componentName.equals(ColaboradoresController.class.getName())) {
        ColaboradoresController instance = new ColaboradoresController(
            instanceOf(RepositorioColaborador.class),
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioProvincia.class),
            instanceOf(RepositorioCiudad.class),
            instanceOf(RepositorioLocalidad.class),
            instanceOf(RepositorioTipoPersona.class),
            instanceOf(RepositorioDeDirecciones.class),
            instanceOf(RepositorioCalle.class),
            instanceOf(RepositorioMedioDeContacto.class),
            instanceOf(RepositorioDeRubros.class),
            instanceOf(RepositorioContribucion.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(HeladerasController.class.getName())) {
        HeladerasController instance = new HeladerasController(
            instanceOf(RepositorioHeladera.class),
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioProvincia.class),
            instanceOf(RepositorioCiudad.class),
            instanceOf(RepositorioLocalidad.class),
            instanceOf(RepositorioDeIncidentes.class),
            instanceOf(RepositorioColaborador.class),
            instanceOf(RepositorioCalle.class),
            instanceOf(RepositorioDeDirecciones.class),
            instanceOf(RepositorioPuntoGeografico.class),
            instanceOf(RepositorioViandas.class),
            instanceOf(RepositorioDeModelos.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(ContribucionesController.class.getName())) {
        ContribucionesController instance = new ContribucionesController(
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioContribucion.class),
            instanceOf(RepositorioHeladera.class),
            instanceOf(RepositorioPersonaVulnerable.class),
            instanceOf(RepositorioTarjeta.class),
            instanceOf(RepositorioProvincia.class),
            instanceOf(RepositorioCiudad.class),
            instanceOf(RepositorioLocalidad.class),
            instanceOf(RepositorioCalle.class),
            instanceOf(RepositorioColaborador.class),
            instanceOf(RepositorioDeRubros.class),
            instanceOf(RepositorioComida.class),
            instanceOf(RepositorioViandas.class),
            instanceOf(RepositorioDeProductos.class),
            instanceOf(RepositorioPuntoGeografico.class),
            instanceOf(RepositorioDeDirecciones.class),
            instanceOf(RepositorioDeModelos.class),
            instanceOf(RepositorioRegistroActividadHeladera.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(ViandaController.class.getName())) {
        ViandaController instance = new ViandaController(
            instanceOf(RepositorioViandas.class),
            instanceOf(RepositorioComida.class),
            instanceOf(RepositorioHeladera.class),
            instanceOf(RepositorioColaborador.class),
            instanceOf(RepositorioUsuario.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(PersonaVulnerableController.class.getName())) {
        PersonaVulnerableController instance = new PersonaVulnerableController(
            instanceOf(RepositorioPersonaVulnerable.class),
            instanceOf(RepositorioDeDirecciones.class),
            instanceOf(RepositorioTarjeta.class),
            instanceOf(RepositorioColaborador.class),
            instanceOf(RepositorioCiudad.class),
            instanceOf(RepositorioLocalidad.class),
            instanceOf(RepositorioProvincia.class),
            instanceOf(RepositorioCalle.class),
            instanceOf(RepositorioUsuario.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(SuscripcionesController.class.getName())) {
        SuscripcionesController instance = new SuscripcionesController(
            instanceOf(RepositorioSuscripcion.class),
            instanceOf(RepositorioHeladera.class),
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioTipoSuscripcion.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(ReporteFallaTecnicaController.class.getName())) {
        ReporteFallaTecnicaController instance = new ReporteFallaTecnicaController(
            instanceOf(RepositorioHeladera.class),
            instanceOf(RepositorioDeIncidentes.class),
            instanceOf(RepositorioUsuario.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(AuthController.class.getName())) {
        AuthController instance = new AuthController(
            instanceOf(RepositorioUsuario.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(BaseController.class.getName())) {
        BaseController instance = new BaseController();
        instances.put(componentName, instance);
      } else if (componentName.equals(IndexController.class.getName())) {
        IndexController instance = new IndexController(
            instanceOf(RepositorioColaborador.class),
            instanceOf(RepositorioViandas.class),
            instanceOf(RepositorioHeladera.class),
            instanceOf(RepositorioPersonaVulnerable.class),
            instanceOf(RepositorioUsuario.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(ReportesController.class.getName())) {
        ReportesController instance = new ReportesController(
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioContribucion.class),
            instanceOf(RepositorioRegistroViandasHeladera.class),
            instanceOf(RepositorioDeIncidentes.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(TecnicosController.class.getName())) {
        TecnicosController instance = new TecnicosController(
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioLocalidad.class),
            instanceOf(RepositorioMedioDeContacto.class),
            instanceOf(RepositorioTecnico.class),
            instanceOf(RepositorioProvincia.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(IncidentesController.class.getName())) {
        IncidentesController instance = new IncidentesController(
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioLocalidad.class),
            instanceOf(RepositorioDeIncidentes.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(VisitasTecnicasController.class.getName())) {
        VisitasTecnicasController instance = new VisitasTecnicasController(
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioLocalidad.class),
            instanceOf(RepositorioRegistroVisitaTecnica.class),
            instanceOf(RepositorioDeIncidentes.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(ProductosController.class.getName())) {
        ProductosController instance = new ProductosController(
            instanceOf(RepositorioDeProductos.class),
            instanceOf(RepositorioDeRubros.class),
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioColaborador.class)
        );
        instances.put(componentName, instance);
      }

      /****************************** REPOSITORIOS ******************************/
      else if (componentName.equals(RepositorioColaborador.class.getName())) {
        RepositorioColaborador instance = new RepositorioColaborador();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioTipoPersona.class.getName())) {
        RepositorioTipoPersona instance = new RepositorioTipoPersona();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioMedioDeContacto.class.getName())) {
        RepositorioMedioDeContacto instance = new RepositorioMedioDeContacto();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioHeladera.class.getName())) {
        RepositorioHeladera instance = new RepositorioHeladera();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioDeDirecciones.class.getName())) {
        RepositorioDeDirecciones instance = new RepositorioDeDirecciones();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioCalle.class.getName())) {
        RepositorioCalle instance = new RepositorioCalle();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioCiudad.class.getName())) {
        RepositorioCiudad instance = new RepositorioCiudad();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioProvincia.class.getName())) {
        RepositorioProvincia instance = new RepositorioProvincia();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioLocalidad.class.getName())) {
        RepositorioLocalidad instance = new RepositorioLocalidad();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioPuntoGeografico.class.getName())) {
        RepositorioPuntoGeografico instance = new RepositorioPuntoGeografico();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioTarjeta.class.getName())) {
        RepositorioTarjeta instance = new RepositorioTarjeta();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioViandas.class.getName())) {
        RepositorioViandas instance = new RepositorioViandas();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioComida.class.getName())) {
        RepositorioComida instance = new RepositorioComida();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioPersonaVulnerable.class.getName())) {
        RepositorioPersonaVulnerable instance = new RepositorioPersonaVulnerable();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioUsuario.class.getName())) {
        RepositorioUsuario instance = new RepositorioUsuario();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioSuscripcion.class.getName())) {
        RepositorioSuscripcion instance = new RepositorioSuscripcion();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioTipoSuscripcion.class.getName())) {
        RepositorioTipoSuscripcion instance = new RepositorioTipoSuscripcion();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioDeIncidentes.class.getName())) {
        RepositorioDeIncidentes instance = new RepositorioDeIncidentes();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioDeRubros.class.getName())) {
        RepositorioDeRubros instance = new RepositorioDeRubros();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioContribucion.class.getName())) {
        RepositorioContribucion instance = new RepositorioContribucion();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioDeProductos.class.getName())) {
        RepositorioDeProductos instance = new RepositorioDeProductos();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioTecnico.class.getName())) {
        RepositorioTecnico instance = new RepositorioTecnico();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioRegistroVisitaTecnica.class.getName())) {
        RepositorioRegistroVisitaTecnica instance = new RepositorioRegistroVisitaTecnica();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioDeModelos.class.getName())) {
        RepositorioDeModelos instance = new RepositorioDeModelos();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioRegistroActividadHeladera.class.getName())) {
        RepositorioRegistroActividadHeladera instance = new RepositorioRegistroActividadHeladera();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioRegistroViandasHeladera.class.getName())) {
        RepositorioRegistroViandasHeladera instance = new RepositorioRegistroViandasHeladera();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioRegistroMensaje.class.getName())) {
        RepositorioRegistroMensaje instance = new RepositorioRegistroMensaje();
        instances.put(componentName, instance);
      }

      /****************************** GeneradorDeReportes ******************************/
      else if (componentName.equals(GeneradorDeReportes.class.getName())) {
        GeneradorDeReportes instance = new GeneradorDeReportes(
            instanceOf(RepositorioContribucion.class),
            instanceOf(RepositorioRegistroViandasHeladera.class),
            instanceOf(RepositorioDeIncidentes.class)
        );
        instances.put(componentName, instance);
      }
    }

    return (T) instances.get(componentName);
  }
}
