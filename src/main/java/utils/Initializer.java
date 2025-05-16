package utils;

import config.ServiceLocator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.entities.CalculadoraDePuntos.CalculadoraDePuntos;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.FormaDeContribucion;
import model.entities.Colaborador.TipoOrganizacion;
import model.entities.Colaborador.TipoPersona;
import model.entities.Contribucion.DistribucionVianda.DistribucionViandas;
import model.entities.Contribucion.DonacionDinero.DonacionDinero;
import model.entities.Contribucion.DonacionVianda.DonacionVianda;
import model.entities.Contribucion.HacerseCargoHeladera.HacerseCargoHeladera;
import model.entities.Contribucion.RealizarOfertas.RealizarOfertas;
import model.entities.Contribucion.RegistroPersonaEnSituacionVulnerable.RegistroPersonaVulnerable;
import model.entities.Direccion.Calle;
import model.entities.Direccion.Ciudad;
import model.entities.Direccion.Direccion;
import model.entities.Direccion.Localidad;
import model.entities.Direccion.Provincia;
import model.entities.Heladera.EstadoHeladera;
import model.entities.Heladera.Heladera;
import model.entities.Heladera.Modelo;
import model.entities.Heladera.RegistroHeladera;
import model.entities.Heladera.RegistroViandasHeladera;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.MedioDeContacto.TipoContacto;
import model.entities.PersonaVulnerable.PersonaVulnerable;
import model.entities.Producto.Producto;
import model.entities.Producto.Rubro;
import model.entities.PuntoGeografico.PuntoGeografico;
import model.entities.Suscripcion.DesperfectoHeladera.DesperfectoHeladera;
import model.entities.Suscripcion.MuchasViandas.MuchasViandas;
import model.entities.Suscripcion.PocasViandas.PocasViandas;
import model.entities.Suscripcion.Suscripcion;
import model.entities.Tarjeta.Tarjeta;
import model.entities.Tecnico.Tecnico;
import model.entities.TipoRol.TipoRol;
import model.entities.Usuario.Usuario;
import model.entities.Utils.TipoDocumento;
import model.entities.Vianda.Comida;
import model.entities.Vianda.EstadoVianda;
import model.entities.Vianda.Vianda;
import model.repositories.RepositorioCalle;
import model.repositories.RepositorioCiudad;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioComida;
import model.repositories.RepositorioContribucion;
import model.repositories.RepositorioDeDirecciones;
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
import model.repositories.RepositorioSuscripcion;
import model.repositories.RepositorioTarjeta;
import model.repositories.RepositorioTecnico;
import model.repositories.RepositorioTipoPersona;
import model.repositories.RepositorioUsuario;
import model.repositories.RepositorioViandas;

public class Initializer {
    private static boolean initialize = false;

    public static void init() {

        /** CALCULADORA DE PUNTOS **/
        CalculadoraDePuntos calculadoraDePuntos = new CalculadoraDePuntos();

        /****************************** TIPO DE PERSONA ******************************/
        RepositorioTipoPersona repositorioTipoPersona = ServiceLocator.instanceOf(RepositorioTipoPersona.class);

        List<FormaDeContribucion> formasDeContribuirJuridica = Arrays.asList(FormaDeContribucion.DonacionDinero,
                FormaDeContribucion.HaceseCargoHeladera, FormaDeContribucion.RealizarOfertas);

        List<FormaDeContribucion> formasDeContribuirHumana = Arrays.asList(
                FormaDeContribucion.DonacionVianda,
                FormaDeContribucion.DistribucionDeViandas,
                FormaDeContribucion.DonacionDinero,
                FormaDeContribucion.RegistroPersonaEnSituacionVulnerable);

        TipoPersona tipoPersonaJuridica = new TipoPersona("Jurídica", formasDeContribuirJuridica);
        TipoPersona tipoPersonaHumana = new TipoPersona("Humana", formasDeContribuirHumana);

        repositorioTipoPersona.guardar(tipoPersonaJuridica);
        repositorioTipoPersona.guardar(tipoPersonaHumana);

        /******************************
         * MEDIO DE CONTACTO
         ******************************/
        RepositorioMedioDeContacto repositorioMedioDeContacto = ServiceLocator
                .instanceOf(RepositorioMedioDeContacto.class);

        MedioDeContacto medioDeContacto1 = new MedioDeContacto(TipoContacto.CORREO, "mail@mail.com");
        MedioDeContacto medioDeContacto2 = new MedioDeContacto(TipoContacto.TELEFONO, "+54 9 11 5555-5555");
        MedioDeContacto medioDeContacto3 = new MedioDeContacto(TipoContacto.CORREO, "gmarolda@frba.utn.edu.ar");
        MedioDeContacto medioDeContacto4 = new MedioDeContacto(TipoContacto.WHATSAPP, "+54 9 11 4444-4444");
        MedioDeContacto medioDeContacto5 = new MedioDeContacto(TipoContacto.TELEFONO, "+54 9 11 5555-0125");
        MedioDeContacto medioDeContacto6 = new MedioDeContacto(TipoContacto.CORREO, "example1@example.com");
        MedioDeContacto medioDeContacto7 = new MedioDeContacto(TipoContacto.CORREO, "info@company.com");
        MedioDeContacto medioDeContacto8 = new MedioDeContacto(TipoContacto.TELEGRAM, "@userhandle");
        MedioDeContacto medioDeContacto9 = new MedioDeContacto(TipoContacto.WHATSAPP, "+44 7712 345678");
        MedioDeContacto medioDeContacto10 = new MedioDeContacto(TipoContacto.TELEFONO, "+91 98765-43210");
        MedioDeContacto medioDeContacto11 = new MedioDeContacto(TipoContacto.CORREO, "support@service.com");
        MedioDeContacto medioDeContacto12 = new MedioDeContacto(TipoContacto.TELEGRAM, "@anotherUser");
        MedioDeContacto medioDeContacto13 = new MedioDeContacto(TipoContacto.WHATSAPP, "+33 6 12 34 56 78");

        repositorioMedioDeContacto.guardar(medioDeContacto1);
        repositorioMedioDeContacto.guardar(medioDeContacto2);
        repositorioMedioDeContacto.guardar(medioDeContacto4);
        repositorioMedioDeContacto.guardar(medioDeContacto5);
        repositorioMedioDeContacto.guardar(medioDeContacto6);
        repositorioMedioDeContacto.guardar(medioDeContacto7);
        repositorioMedioDeContacto.guardar(medioDeContacto8);
        repositorioMedioDeContacto.guardar(medioDeContacto9);
        repositorioMedioDeContacto.guardar(medioDeContacto10);
        repositorioMedioDeContacto.guardar(medioDeContacto11);
        repositorioMedioDeContacto.guardar(medioDeContacto12);
        repositorioMedioDeContacto.guardar(medioDeContacto13);

        /****************************** TECNICO ******************************/
        RepositorioTecnico repositorioTecnico = ServiceLocator.instanceOf(RepositorioTecnico.class);

        Tecnico tecnico1 = Tecnico.builder()
                .nombre("Lucio")
                .apellido("Sosa")
                .medioDeContacto(medioDeContacto3)
                .build();
        Tecnico tecnico2 = Tecnico.builder()
                .nombre("Martina")
                .apellido("Perez")
                .medioDeContacto(medioDeContacto4)
                .build();

        Tecnico tecnico3 = Tecnico.builder()
                .nombre("Carlos")
                .apellido("Gomez")
                .medioDeContacto(medioDeContacto5)
                .build();

        Tecnico tecnico4 = Tecnico.builder()
                .nombre("Andrea")
                .apellido("Lopez")
                .medioDeContacto(medioDeContacto6)
                .build();

        Tecnico tecnico5 = Tecnico.builder()
                .nombre("Joaquin")
                .apellido("Diaz")
                .medioDeContacto(medioDeContacto7)
                .build();

        Tecnico tecnico6 = Tecnico.builder()
                .nombre("Sofia")
                .apellido("Martinez")
                .medioDeContacto(medioDeContacto8)
                .build();

        Tecnico tecnico7 = Tecnico.builder()
                .nombre("Leonel")
                .apellido("Fernandez")
                .medioDeContacto(medioDeContacto9)
                .build();

        repositorioTecnico.guardar(tecnico1);
        repositorioTecnico.guardar(tecnico1);
        repositorioTecnico.guardar(tecnico2);
        repositorioTecnico.guardar(tecnico3);
        repositorioTecnico.guardar(tecnico4);
        repositorioTecnico.guardar(tecnico5);
        repositorioTecnico.guardar(tecnico6);
        repositorioTecnico.guardar(tecnico7);

        /****************************** CALLE ******************************/
        RepositorioCalle repositorioCalle = ServiceLocator.instanceOf(RepositorioCalle.class);

        Calle calle1 = new Calle("Avenida Rivadavia");
        Calle calle2 = new Calle("Juncal");
        Calle calle3 = new Calle("Avenida Medrano");
        Calle calle4 = new Calle("Avenida Santa Fe");
        Calle calle5 = new Calle("Avenida Jujuy");
        Calle calle6 = new Calle("Sarmiento");
        Calle calle7 = new Calle("Avenida Crisólogo Larralde");
        Calle calle8 = new Calle("Avenida 25 de Mayo");
        Calle calle9 = new Calle("Avenida Alicia Moreau de Justo");

        repositorioCalle.guardar(calle1);
        repositorioCalle.guardar(calle2);
        repositorioCalle.guardar(calle3);
        repositorioCalle.guardar(calle4);
        repositorioCalle.guardar(calle5);
        repositorioCalle.guardar(calle6);
        repositorioCalle.guardar(calle7);
        repositorioCalle.guardar(calle8);
        repositorioCalle.guardar(calle9);

        /****************************** CIUDAD ******************************/
        RepositorioCiudad repositorioCiudad = ServiceLocator.instanceOf(RepositorioCiudad.class);

        Ciudad ciudad1 = new Ciudad("Buenos Aires");
        Ciudad ciudad2 = new Ciudad("Lanús");
        Ciudad ciudad3 = new Ciudad("Avellaneda");

        repositorioCiudad.guardar(ciudad1);
        repositorioCiudad.guardar(ciudad2);
        repositorioCiudad.guardar(ciudad3);

        /****************************** PROVINCIA ******************************/
        RepositorioProvincia repositorioProvincia = ServiceLocator.instanceOf(RepositorioProvincia.class);

        Provincia buenosAires = new Provincia("Buenos Aires");
        Provincia catamarca = new Provincia("Catamarca");
        Provincia chaco = new Provincia("Chaco");
        Provincia chubut = new Provincia("Chubut");
        Provincia cordoba = new Provincia("Córdoba");
        Provincia corrientes = new Provincia("Corrientes");
        Provincia entreRios = new Provincia("Entre Ríos");
        Provincia formosa = new Provincia("Formosa");
        Provincia jujuy = new Provincia("Jujuy");
        Provincia laPampa = new Provincia("La Pampa");
        Provincia laRioja = new Provincia("La Rioja");
        Provincia mendoza = new Provincia("Mendoza");
        Provincia misiones = new Provincia("Misiones");
        Provincia neuquen = new Provincia("Neuquén");
        Provincia rioNegro = new Provincia("Río Negro");
        Provincia salta = new Provincia("Salta");
        Provincia sanJuan = new Provincia("San Juan");
        Provincia sanLuis = new Provincia("San Luis");
        Provincia santaCruz = new Provincia("Santa Cruz");
        Provincia santaFe = new Provincia("Santa Fe");
        Provincia santiagoDelEstero = new Provincia("Santiago del Estero");
        Provincia tierraDelFuego = new Provincia("Tierra del Fuego");
        Provincia tucuman = new Provincia("Tucumán");
        Provincia ciudadDeBuenosAires = new Provincia("Ciudad Autónoma de Buenos Aires");

        repositorioProvincia.guardar(ciudadDeBuenosAires);
        repositorioProvincia.guardar(buenosAires);
        repositorioProvincia.guardar(catamarca);
        repositorioProvincia.guardar(chaco);
        repositorioProvincia.guardar(chubut);
        repositorioProvincia.guardar(cordoba);
        repositorioProvincia.guardar(corrientes);
        repositorioProvincia.guardar(entreRios);
        repositorioProvincia.guardar(formosa);
        repositorioProvincia.guardar(jujuy);
        repositorioProvincia.guardar(laPampa);
        repositorioProvincia.guardar(laRioja);
        repositorioProvincia.guardar(mendoza);
        repositorioProvincia.guardar(misiones);
        repositorioProvincia.guardar(neuquen);
        repositorioProvincia.guardar(rioNegro);
        repositorioProvincia.guardar(salta);
        repositorioProvincia.guardar(sanJuan);
        repositorioProvincia.guardar(sanLuis);
        repositorioProvincia.guardar(santaCruz);
        repositorioProvincia.guardar(santaFe);
        repositorioProvincia.guardar(santiagoDelEstero);
        repositorioProvincia.guardar(tierraDelFuego);
        repositorioProvincia.guardar(tucuman);

        /****************************** LOCALIDAD ******************************/
        RepositorioLocalidad repositorioLocalidad = ServiceLocator.instanceOf(RepositorioLocalidad.class);

        Localidad localidad3 = new Localidad("Avellaneda", buenosAires);
        Localidad localidad4 = new Localidad("Palermo", ciudadDeBuenosAires);
        Localidad localidad5 = new Localidad("Balvanera", ciudadDeBuenosAires);
        Localidad localidad6 = new Localidad("Almagro", ciudadDeBuenosAires);
        Localidad localidad7 = new Localidad("Lanús Oeste", buenosAires);
        Localidad localidad8 = new Localidad("Puerto Madero", ciudadDeBuenosAires);
        Localidad localidad9 = new Localidad("Recoleta", ciudadDeBuenosAires);

        localidad9.agregarTecnico(tecnico7);
        localidad5.agregarTecnico(tecnico1);
        localidad3.agregarTecnico(tecnico2);
        localidad4.agregarTecnico(tecnico3);
        localidad5.agregarTecnico(tecnico4);
        localidad3.agregarTecnico(tecnico5);
        localidad3.agregarTecnico(tecnico6);
        localidad6.agregarTecnico(tecnico1);

        repositorioLocalidad.guardar(localidad3);
        repositorioLocalidad.guardar(localidad4);
        repositorioLocalidad.guardar(localidad5);
        repositorioLocalidad.guardar(localidad6);
        repositorioLocalidad.guardar(localidad7);
        repositorioLocalidad.guardar(localidad8);
        repositorioLocalidad.guardar(localidad9);

        /****************************** DIRECCIONES ******************************/
        RepositorioDeDirecciones repositorioDeDirecciones = ServiceLocator.instanceOf(RepositorioDeDirecciones.class);

        Direccion direccion1 = Direccion.builder()
                .calle(calle1) // Avenida Rivadavia
                .altura(2566)
                .codigoPostal("C1034")
                .ciudad(ciudad1)
                .localidad(localidad5) // Balvanera
                .build();

        Direccion direccion2 = Direccion.builder()
                .calle(calle2) // Juncal
                .altura(1635)
                .codigoPostal("C1061")
                .ciudad(ciudad1)
                .localidad(localidad9) // Recoleta
                .build();

        Direccion direccion3 = Direccion.builder()
                .calle(calle3) // Avenida Medrano
                .altura(966)
                .codigoPostal("C1179")
                .ciudad(ciudad1)
                .localidad(localidad6) // Almagro
                .build();

        Direccion direccion4 = Direccion.builder()
                .calle(calle4) // Avenida Santa Fe
                .altura(4568)
                .codigoPostal("C1425")
                .ciudad(ciudad1)
                .localidad(localidad4) // Palermo
                .build();

        Direccion direccion5 = Direccion.builder()
                .calle(calle5) // Avenida Jujuy
                .altura(335)
                .codigoPostal("C1083")
                .ciudad(ciudad1)
                .localidad(localidad5) // Balvanera
                .build();

        Direccion direccion6 = Direccion.builder()
                .calle(calle6) // Sarmiento
                .altura(3568)
                .codigoPostal("C1196")
                .ciudad(ciudad1)
                .localidad(localidad6) // Almagro
                .build();

        Direccion direccion7 = Direccion.builder()
                .calle(calle7) // Avenida Crisólogo Larralde
                .altura(1100)
                .codigoPostal("B1873")
                .ciudad(ciudad3)
                .localidad(localidad3)
                .build();

        Direccion direccion8 = Direccion.builder()
                .calle(calle7) // Avenida Crisólogo Larralde
                .altura(1150)
                .codigoPostal("B1873")
                .ciudad(ciudad3)
                .localidad(localidad3)
                .build();

        Direccion direccion9 = Direccion.builder()
                .calle(calle7) // Avenida Crisólogo Larralde
                .altura(1200)
                .codigoPostal("B1873")
                .ciudad(ciudad3)
                .localidad(localidad3)
                .build();

        Direccion direccion10 = Direccion.builder()
                .calle(calle8) // Avenida 25 de Mayo
                .altura(155)
                .codigoPostal("B1826")
                .ciudad(ciudad2)
                .localidad(localidad7)
                .build();

        Direccion direccion11 = Direccion.builder()
                .calle(calle8) // Avenida 25 de Mayo
                .altura(163)
                .codigoPostal("B1826")
                .ciudad(ciudad1)
                .localidad(localidad8)
                .build();

        Direccion direccion12 = Direccion.builder()
                .calle(calle9) // Avenida Alicia Moreau de Justo
                .altura(1050)
                .codigoPostal("C1107")
                .ciudad(ciudad2)
                .localidad(localidad7)
                .build();

        repositorioDeDirecciones.guardar(direccion1);
        repositorioDeDirecciones.guardar(direccion2);
        repositorioDeDirecciones.guardar(direccion3);
        repositorioDeDirecciones.guardar(direccion4);
        repositorioDeDirecciones.guardar(direccion5);
        repositorioDeDirecciones.guardar(direccion6);
        repositorioDeDirecciones.guardar(direccion7);
        repositorioDeDirecciones.guardar(direccion8);
        repositorioDeDirecciones.guardar(direccion9);
        repositorioDeDirecciones.guardar(direccion10);
        repositorioDeDirecciones.guardar(direccion11);
        repositorioDeDirecciones.guardar(direccion12);

        /****************************** PUNTO GEOGRAFICO ******************************/
        RepositorioPuntoGeografico repositorioPuntoGeografico = ServiceLocator
                .instanceOf(RepositorioPuntoGeografico.class);

        PuntoGeografico puntoGeografico1 = new PuntoGeografico(-58.4027f, -34.6101f, direccion1,
                "Avenida Rivadavia 2566, C1034");
        PuntoGeografico puntoGeografico2 = new PuntoGeografico(-58.3908f, -34.5941f, direccion2, "Juncal 1635, C1061");
        PuntoGeografico puntoGeografico3 = new PuntoGeografico(-58.4206f, -34.5983f, direccion3,
                "Avenida Medrano 966, C1179");
        PuntoGeografico puntoGeografico4 = new PuntoGeografico(-58.4251f, -34.5790f, direccion4,
                "Avenida Santa Fe 4568, C1425");
        PuntoGeografico puntoGeografico5 = new PuntoGeografico(-58.4052f, -34.6139f, direccion5,
                "Avenida Jujuy 335, C1083");
        PuntoGeografico puntoGeografico6 = new PuntoGeografico(-58.4155f, -34.6058f, direccion6,
                "Sarmiento 3568, C1196");
        PuntoGeografico puntoGeografico7 = new PuntoGeografico(-58.3690f, -34.6779f, direccion7,
                "Avenida Crisólogo Larralde 1100, B1873");
        PuntoGeografico puntoGeografico8 = new PuntoGeografico(-58.3686f, -34.6783f, direccion8,
                "Avenida Crisólogo Larralde 1150, B1873");
        PuntoGeografico puntoGeografico9 = new PuntoGeografico(-58.3682f, -34.6786f, direccion9,
                "Avenida Crisólogo Larralde 1200, B1873");
        PuntoGeografico puntoGeografico10 = new PuntoGeografico(-58.3937f, -34.7078f, direccion10,
                "Avenida 25 de Mayo 155, B1826");
        PuntoGeografico puntoGeografico11 = new PuntoGeografico(-58.3937f, -34.7079f, direccion11,
                "Avenida 25 de Mayo 163, B1826");
        PuntoGeografico puntoGeografico12 = new PuntoGeografico(-58.3661f, -34.6098f, direccion12,
                "Avenida Alicia Moreau de Justo 170, C1107");

        repositorioPuntoGeografico.guardar(puntoGeografico1);
        repositorioPuntoGeografico.guardar(puntoGeografico2);
        repositorioPuntoGeografico.guardar(puntoGeografico3);
        repositorioPuntoGeografico.guardar(puntoGeografico4);
        repositorioPuntoGeografico.guardar(puntoGeografico5);
        repositorioPuntoGeografico.guardar(puntoGeografico6);
        repositorioPuntoGeografico.guardar(puntoGeografico7);
        repositorioPuntoGeografico.guardar(puntoGeografico8);
        repositorioPuntoGeografico.guardar(puntoGeografico9);
        repositorioPuntoGeografico.guardar(puntoGeografico10);
        repositorioPuntoGeografico.guardar(puntoGeografico11);
        repositorioPuntoGeografico.guardar(puntoGeografico12);

        /****************************** MODELO ******************************/

        RepositorioDeModelos repositorioDeModelos = ServiceLocator.instanceOf(RepositorioDeModelos.class);

        Modelo modeloCompacto = Modelo.builder()
                .nombre("Compacto-100")
                .tempMaxima(2)
                .tempMinima(12)
                .build();
        modeloCompacto.setNombre(modeloCompacto.getNombre() + " (" + modeloCompacto.getTempMaxima() + "°C a "
                + modeloCompacto.getTempMinima() + "°C)");

        Modelo modeloIndustrial = Modelo.builder()
                .nombre("Industrial-FrostX")
                .tempMaxima(-10)
                .tempMinima(0)
                .build();
        modeloIndustrial.setNombre(modeloIndustrial.getNombre() + " (" + modeloIndustrial.getTempMaxima() + "°C a "
                + modeloIndustrial.getTempMinima() + "°C)");

        Modelo modeloDomestico = Modelo.builder()
                .nombre("Doméstico-Family")
                .tempMaxima(4)
                .tempMinima(15)
                .build();
        modeloDomestico.setNombre(modeloDomestico.getNombre() + " (" + modeloDomestico.getTempMaxima() + "°C a "
                + modeloDomestico.getTempMinima() + "°C)");

        Modelo modeloUltraFrio = Modelo.builder()
                .nombre("UltraFrío-Max")
                .tempMaxima(-20)
                .tempMinima(-5)
                .build();
        modeloUltraFrio.setNombre(modeloUltraFrio.getNombre() + " (" + modeloUltraFrio.getTempMaxima() + "°C a "
                + modeloUltraFrio.getTempMinima() + "°C)");

        Modelo modeloCavaVinos = Modelo.builder()
                .nombre("Cava-Vinos")
                .tempMaxima(8)
                .tempMinima(15)
                .build();
        modeloCavaVinos.setNombre(modeloCavaVinos.getNombre() + " (" + modeloCavaVinos.getTempMaxima() + "°C a "
                + modeloCavaVinos.getTempMinima() + "°C)");

        Modelo modeloTienda = Modelo.builder()
                .nombre("Tienda-Fresh")
                .tempMaxima(-2)
                .tempMinima(6)
                .build();
        modeloTienda.setNombre(modeloTienda.getNombre() + " (" + modeloTienda.getTempMaxima() + "°C a "
                + modeloTienda.getTempMinima() + "°C)");

        Modelo modeloEcoCompact = Modelo.builder()
                .nombre("Eco-Compact")
                .tempMaxima(3)
                .tempMinima(10)
                .build();
        modeloEcoCompact.setNombre(modeloEcoCompact.getNombre() + " (" + modeloEcoCompact.getTempMaxima() + "°C a "
                + modeloEcoCompact.getTempMinima() + "°C)");

        Modelo modeloFreezerPro = Modelo.builder()
                .nombre("Freezer-Pro")
                .tempMaxima(-18)
                .tempMinima(-5)
                .build();
        modeloFreezerPro.setNombre(modeloFreezerPro.getNombre() + " (" + modeloFreezerPro.getTempMaxima() + "°C a "
                + modeloFreezerPro.getTempMinima() + "°C)");

        Modelo modeloCoolerBox = Modelo.builder()
                .nombre("Cooler-Box")
                .tempMaxima(5)
                .tempMinima(8)
                .build();
        modeloCoolerBox.setNombre(modeloCoolerBox.getNombre() + " (" + modeloCoolerBox.getTempMaxima() + "°C a "
                + modeloCoolerBox.getTempMinima() + "°C)");

        Modelo modeloArcticBlast = Modelo.builder()
                .nombre("Arctic-Blast")
                .tempMaxima(-25)
                .tempMinima(-15)
                .build();
        modeloArcticBlast.setNombre(modeloArcticBlast.getNombre() + " (" + modeloArcticBlast.getTempMaxima() + "°C a "
                + modeloArcticBlast.getTempMinima() + "°C)");

        Modelo modeloWineChiller = Modelo.builder()
                .nombre("Wine-Chiller")
                .tempMaxima(10)
                .tempMinima(16)
                .build();
        modeloWineChiller.setNombre(modeloWineChiller.getNombre() + " (" + modeloWineChiller.getTempMaxima() + "°C a "
                + modeloWineChiller.getTempMinima() + "°C)");

        Modelo modeloFreshMarket = Modelo.builder()
                .nombre("Fresh-Market")
                .tempMaxima(-3)
                .tempMinima(4)
                .build();
        modeloFreshMarket.setNombre(modeloFreshMarket.getNombre() + " (" + modeloFreshMarket.getTempMaxima() + "°C a "
                + modeloFreshMarket.getTempMinima() + "°C)");

        repositorioDeModelos.guardar(modeloCompacto);
        repositorioDeModelos.guardar(modeloIndustrial);
        repositorioDeModelos.guardar(modeloDomestico);
        repositorioDeModelos.guardar(modeloUltraFrio);
        repositorioDeModelos.guardar(modeloCavaVinos);
        repositorioDeModelos.guardar(modeloTienda);
        repositorioDeModelos.guardar(modeloEcoCompact);
        repositorioDeModelos.guardar(modeloFreezerPro);
        repositorioDeModelos.guardar(modeloCoolerBox);
        repositorioDeModelos.guardar(modeloArcticBlast);
        repositorioDeModelos.guardar(modeloWineChiller);
        repositorioDeModelos.guardar(modeloFreshMarket);

        /****************************** HELADERA ******************************/
        RepositorioHeladera repositorioHeladera = ServiceLocator.instanceOf(RepositorioHeladera.class);
        RepositorioRegistroActividadHeladera repositorioRegistroActividadHeladera = ServiceLocator
                .instanceOf(RepositorioRegistroActividadHeladera.class);

        Heladera heladera1 = Heladera.builder()
                .puntoGeografico(puntoGeografico1)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(15)
                .modelo(modeloCompacto)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera2 = Heladera.builder()
                .puntoGeografico(puntoGeografico2)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(100)
                .modelo(modeloCavaVinos)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera3 = Heladera.builder()
                .puntoGeografico(puntoGeografico3)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(15)
                .modelo(modeloDomestico)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera4 = Heladera.builder()
                .puntoGeografico(puntoGeografico4)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(100)
                .modelo(modeloIndustrial)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera5 = Heladera.builder()
                .puntoGeografico(puntoGeografico5)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(15)
                .modelo(modeloCompacto)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera6 = Heladera.builder()
                .puntoGeografico(puntoGeografico6)
                .estadoHeladera(EstadoHeladera.NO_ACTIVA)
                .capacidadViandas(100)
                .modelo(modeloCavaVinos)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera7 = Heladera.builder()
                .puntoGeografico(puntoGeografico7)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(15)
                .modelo(modeloDomestico)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera8 = Heladera.builder()
                .puntoGeografico(puntoGeografico8)
                .estadoHeladera(EstadoHeladera.NO_ACTIVA)
                .capacidadViandas(100)
                .modelo(modeloIndustrial)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera9 = Heladera.builder()
                .puntoGeografico(puntoGeografico9)
                .estadoHeladera(EstadoHeladera.NO_ACTIVA)
                .capacidadViandas(15)
                .modelo(modeloCompacto)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera10 = Heladera.builder()
                .puntoGeografico(puntoGeografico10)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(100)
                .modelo(modeloCavaVinos)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera11 = Heladera.builder()
                .puntoGeografico(puntoGeografico11)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(15)
                .modelo(modeloDomestico)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        Heladera heladera12 = Heladera.builder()
                .puntoGeografico(puntoGeografico12)
                .estadoHeladera(EstadoHeladera.ACTIVA)
                .capacidadViandas(100)
                .modelo(modeloIndustrial)
                .cantViandas(0)
                .fechaDeInicio(LocalDate.now())
                .registrosViandasHeladera(new ArrayList<>())
                .build();

        RegistroViandasHeladera registroViandasHeladera1 = new RegistroViandasHeladera(
                LocalDate.now(),
                -1,
                heladera3);
        RegistroViandasHeladera registroViandasHeladera2 = new RegistroViandasHeladera(
                LocalDate.now(),
                -5,
                heladera1);
        RegistroViandasHeladera registroViandasHeladera3 = new RegistroViandasHeladera(
                LocalDate.now(),
                57,
                heladera2);
        RegistroViandasHeladera registroViandasHeladera4 = new RegistroViandasHeladera(
                LocalDate.now(),
                10,
                heladera1);

        heladera1.agregarRegistroViandas(registroViandasHeladera1);
        heladera1.agregarRegistroViandas(registroViandasHeladera4);
        heladera2.agregarRegistroViandas(registroViandasHeladera2);
        heladera3.agregarRegistroViandas(registroViandasHeladera3);

        RegistroHeladera registroHeladera1 = RegistroHeladera.builder()
                .estado(EstadoHeladera.ACTIVA)
                .fecha(LocalDate.now())
                .build();

        RegistroHeladera registroHeladera2 = RegistroHeladera.builder()
                .estado(EstadoHeladera.ACTIVA)
                .fecha(LocalDate.now())
                .build();

        RegistroHeladera registroHeladera3 = RegistroHeladera.builder()
                .estado(EstadoHeladera.ACTIVA)
                .fecha(LocalDate.now())
                .build();

        heladera1.agregarRegistroHeladera(registroHeladera1);
        heladera2.agregarRegistroHeladera(registroHeladera2);
        heladera3.agregarRegistroHeladera(registroHeladera3);

        repositorioHeladera.guardar(heladera1);
        repositorioHeladera.guardar(heladera2);
        repositorioHeladera.guardar(heladera3);
        repositorioHeladera.guardar(heladera4);
        repositorioHeladera.guardar(heladera5);
        repositorioHeladera.guardar(heladera6);
        repositorioHeladera.guardar(heladera7);
        repositorioHeladera.guardar(heladera8);
        repositorioHeladera.guardar(heladera9);
        repositorioHeladera.guardar(heladera10);
        repositorioHeladera.guardar(heladera11);
        repositorioHeladera.guardar(heladera12);

        repositorioRegistroActividadHeladera.guardar(registroHeladera1);
        repositorioRegistroActividadHeladera.guardar(registroHeladera2);
        repositorioRegistroActividadHeladera.guardar(registroHeladera3);

        /****************************** TARJETA ******************************/
        RepositorioTarjeta repositorioTarjeta = ServiceLocator.instanceOf(RepositorioTarjeta.class);

        Tarjeta tarjeta1 = new Tarjeta();
        Tarjeta tarjeta2 = new Tarjeta();
        Tarjeta tarjeta3 = new Tarjeta();
        Tarjeta tarjeta4 = new Tarjeta();

        repositorioTarjeta.guardar(tarjeta1);
        repositorioTarjeta.guardar(tarjeta2);
        repositorioTarjeta.guardar(tarjeta3);
        repositorioTarjeta.guardar(tarjeta4);

        /****************************** RUBRO ******************************/
        RepositorioDeRubros repositorioDeRubros = ServiceLocator.instanceOf(RepositorioDeRubros.class);

        Rubro rubroGastronomia = new Rubro("Gastronomía");
        Rubro rubroElectronica = new Rubro("Electrónica");
        Rubro rubroArticulosHogar = new Rubro("Artículos para el hogar");
        Rubro rubroAutomotriz = new Rubro("Automotriz");
        Rubro rubroSaludBelleza = new Rubro("Salud y belleza");
        Rubro rubroDeportesFitness = new Rubro("Deportes y fitness");
        Rubro rubroTecnologiaInformatica = new Rubro("Tecnología e informática");
        Rubro rubroViajesTurismo = new Rubro("Viajes y turismo");
        Rubro rubroOtro = new Rubro("Otro");

        repositorioDeRubros.guardar(rubroGastronomia);
        repositorioDeRubros.guardar(rubroElectronica);
        repositorioDeRubros.guardar(rubroArticulosHogar);
        repositorioDeRubros.guardar(rubroAutomotriz);
        repositorioDeRubros.guardar(rubroDeportesFitness);
        repositorioDeRubros.guardar(rubroTecnologiaInformatica);
        repositorioDeRubros.guardar(rubroSaludBelleza);
        repositorioDeRubros.guardar(rubroViajesTurismo);
        repositorioDeRubros.guardar(rubroOtro);

        /****************************** PRODUCTO ******************************/
        Producto producto1 = Producto
                .builder()
                .nombre("Café")
                .cantidadDePuntos(14000)
                .rubro(rubroGastronomia)
                .imagenIlustrativa(
                        "/fotos_productos/El-Archivo-de-las-Tormentas-Viento-y-Verdad-ilustracion-para-la-portada-USA-arte-de-Michael-Whelan.jpg")
                .build();

        Producto producto2 = Producto
                .builder()
                .nombre("Yerba")
                .cantidadDePuntos(5500)
                .rubro(rubroGastronomia)
                .imagenIlustrativa(
                        "/fotos_productos/El-Archivo-de-las-Tormentas-Viento-y-Verdad-ilustracion-para-la-portada-USA-arte-de-Michael-Whelan.jpg")
                .build();

        Producto producto3 = Producto
                .builder()
                .nombre("Té de boldo")
                .cantidadDePuntos(300)
                .rubro(rubroGastronomia)
                .imagenIlustrativa(
                        "/fotos_productos/El-Archivo-de-las-Tormentas-Viento-y-Verdad-ilustracion-para-la-portada-USA-arte-de-Michael-Whelan.jpg")
                .build();

        Producto producto4 = Producto
                .builder()
                .nombre("PRODUCTO")
                .cantidadDePuntos(300)
                .rubro(rubroGastronomia)
                .imagenIlustrativa(
                        "/fotos_productos/El-Archivo-de-las-Tormentas-Viento-y-Verdad-ilustracion-para-la-portada-USA-arte-de-Michael-Whelan.jpg")
                .build();

        RepositorioDeProductos repositorioDeProductos = ServiceLocator.instanceOf(RepositorioDeProductos.class);

        repositorioDeProductos.guardar(producto1);
        repositorioDeProductos.guardar(producto2);
        repositorioDeProductos.guardar(producto3);
        repositorioDeProductos.guardar(producto4);

        /****************************** COLABORADOR ******************************/
        RepositorioColaborador repositorioColaborador = ServiceLocator.instanceOf(RepositorioColaborador.class);

        Set<FormaDeContribucion> formaDeContribucion1 = new HashSet<>();
        Set<FormaDeContribucion> formaDeContribucion2 = new HashSet<>();
        Set<FormaDeContribucion> formaDeContribucion3 = new HashSet<>();

        formaDeContribucion1.add(FormaDeContribucion.DonacionVianda);
        formaDeContribucion1.add(FormaDeContribucion.DonacionDinero);

        formaDeContribucion2.add(FormaDeContribucion.DonacionDinero);
        formaDeContribucion2.add(FormaDeContribucion.HaceseCargoHeladera);
        formaDeContribucion2.add(FormaDeContribucion.RealizarOfertas);

        formaDeContribucion3.add(FormaDeContribucion.DonacionDinero);

        List<Producto> productos1 = new ArrayList<>();
        productos1.add(producto3);

        Colaborador colaborador1 = Colaborador.builder()
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .nombre("Juan")
                .apellido("Gonzalez")
                .tarjeta(tarjeta1)
                .tipoOrganizacion(TipoOrganizacion.Empresa)
                .tipoPersona(tipoPersonaHumana)
                // .contactos(Arrays.asList(new MedioDeContacto(TipoContacto.CORREO,
                // "juan.perez@example.com")))
                .productosComprados(productos1)
                .formasDeContribuirSeleccionadas(formaDeContribucion1)
                .direccion(direccion1)
                .build();

        Double gastado1 = calculadoraDePuntos.calcularPuntosGastados(colaborador1);
        colaborador1.setPuntosGastados((int) Math.ceil(gastado1));

        List<MedioDeContacto> contactosColaborador2 = new ArrayList<>();
        contactosColaborador2.add(medioDeContacto1);
        contactosColaborador2.add(medioDeContacto2);

        Colaborador colaborador2 = Colaborador.builder()
                .fechaNacimiento(LocalDate.of(1990, 3, 22))
                .nombre("Santiago")
                .apellido("Rodriguez")
                .tarjeta(tarjeta2)
                .razonSocial("Garcia Importaciones")
                .tipoOrganizacion(TipoOrganizacion.ONG)
                .tipoPersona(tipoPersonaJuridica)
                .contactos(contactosColaborador2)
                .formasDeContribuirSeleccionadas(formaDeContribucion2)
                .puntosTotales(1000)
                .direccion(direccion2)
                .build();

        Double gastado2 = calculadoraDePuntos.calcularPuntosGastados(colaborador2);
        colaborador2.setPuntosGastados((int) Math.ceil(gastado2));

        List<MedioDeContacto> contactosColaborador3 = new ArrayList<>();
        contactosColaborador3.add(medioDeContacto3);

        Colaborador colaborador3 = Colaborador.builder()
                .nombre("Luquita")
                .apellido("Rodriguez")
                .tarjeta(tarjeta3)
                .fechaNacimiento(LocalDate.of(1990, 3, 22))
                .tipoPersona(tipoPersonaHumana)
                .puntosTotales(10000)
                .formasDeContribuirSeleccionadas(formaDeContribucion3)
                .contactos(contactosColaborador3)
                .direccion(direccion3)
                .build();

        Double gastado3 = calculadoraDePuntos.calcularPuntosGastados(colaborador3);
        colaborador3.setPuntosGastados((int) Math.ceil(gastado3));

        repositorioColaborador.guardar(colaborador1);
        repositorioColaborador.guardar(colaborador2);
        repositorioColaborador.guardar(colaborador3);

        /******************************
         * PERSONA VULNERABLE
         ******************************/
        RepositorioPersonaVulnerable repositorioPersonaVulnerable = ServiceLocator
                .instanceOf(RepositorioPersonaVulnerable.class);

        PersonaVulnerable personaVulnerable1 = PersonaVulnerable.builder()
                .nombre("Juan Pérez")
                .fechaDeNacimiento(LocalDate.of(1990, 1, 1))
                .direccion(direccion1)
                .tipoDocumento(TipoDocumento.DNI)
                .numeroDocumento("12345678")
                .tarjeta(tarjeta1)
                .build();

        PersonaVulnerable personaVulnerable2 = PersonaVulnerable.builder()
                .nombre("María López")
                .fechaDeNacimiento(LocalDate.of(1985, 5, 20))
                .direccion(direccion2)
                .tipoDocumento(TipoDocumento.LC)
                .numeroDocumento("A1234567")
                .tarjeta(tarjeta2)
                .build();

        PersonaVulnerable personaVulnerable3 = PersonaVulnerable.builder()
                .nombre("Pedro González")
                .fechaDeNacimiento(LocalDate.of(2000, 12, 15))
                .direccion(direccion3)
                .tipoDocumento(TipoDocumento.LE)
                .numeroDocumento("87654321")
                .build();

        repositorioPersonaVulnerable.guardar(personaVulnerable1);
        repositorioPersonaVulnerable.guardar(personaVulnerable2);
        repositorioPersonaVulnerable.guardar(personaVulnerable3);

        /****************************** COMIDA ******************************/
        RepositorioComida repositorioComida = ServiceLocator.instanceOf(RepositorioComida.class);

        Comida comida1 = new Comida("Milanesa de ternera");
        Comida comida2 = new Comida("Fideos con tuco");
        Comida comida3 = new Comida("Arroz blanco");
        Comida comida4 = new Comida("Pollo al spiedo");

        repositorioComida.guardar(comida1);
        repositorioComida.guardar(comida2);
        repositorioComida.guardar(comida3);
        repositorioComida.guardar(comida4);

        /****************************** VIANDAS ******************************/
        RepositorioViandas repositorioViandas = ServiceLocator.instanceOf(RepositorioViandas.class);

        Vianda vianda1 = Vianda.builder()
                .comida(comida1)
                .fechaCaducidad(LocalDate.of(2024, 10, 20))
                .fechaDonacion(LocalDate.of(2024, 10, 1))
                .colaborador(colaborador1)
                .heladera(heladera1)
                .calorias(500.0)
                .peso(1.2)
                .estadoVianda(EstadoVianda.Entregada)
                .build();

        Vianda vianda2 = Vianda.builder()
                .comida(comida2)
                .fechaCaducidad(LocalDate.of(2024, 10, 25))
                .fechaDonacion(LocalDate.of(2024, 9, 28))
                .colaborador(colaborador2)
                .heladera(heladera2)
                .calorias(700.0)
                .peso(1.5)
                .estadoVianda(EstadoVianda.Entregada)
                .build();

        Vianda vianda3 = Vianda.builder()
                .comida(comida3)
                .fechaCaducidad(LocalDate.of(2024, 11, 5))
                .fechaDonacion(LocalDate.of(2024, 10, 3))
                .colaborador(colaborador2)
                .heladera(heladera2)
                .calorias(450.0)
                .peso(1.1)
                .estadoVianda(EstadoVianda.Entregada)
                .build();

        Vianda vianda4 = Vianda.builder()
                .comida(comida4)
                .fechaCaducidad(LocalDate.of(2024, 12, 10))
                .fechaDonacion(LocalDate.of(2024, 10, 8))
                .colaborador(colaborador1)
                .heladera(heladera1)
                .calorias(800.0)
                .peso(2.0)
                .estadoVianda(EstadoVianda.NoEntregada)
                .build();

        Vianda vianda5 = Vianda.builder()
                .comida(comida4)
                .fechaCaducidad(LocalDate.of(2024, 12, 10))
                .fechaDonacion(LocalDate.of(2024, 10, 8))
                .calorias(800.0)
                .peso(2.0)
                .heladera(heladera3)
                .estadoVianda(EstadoVianda.NoEntregada)
                .colaborador(colaborador2)
                .build();

        repositorioViandas.guardar(vianda1);
        repositorioViandas.guardar(vianda2);
        repositorioViandas.guardar(vianda3);
        repositorioViandas.guardar(vianda4);
        repositorioViandas.guardar(vianda5);

        /****************************** COLABORACION ******************************/
        RepositorioContribucion repositorioContribucion = ServiceLocator.instanceOf(RepositorioContribucion.class);

        DonacionDinero donacionDinero = new DonacionDinero();
        donacionDinero.setCantidadDinero(1500);
        donacionDinero.setFrecuencia(2);
        donacionDinero.setColaborador(colaborador1);
        donacionDinero.setFechaHora(LocalDateTime.now());

        HacerseCargoHeladera hacerseCargoHeladera1 = new HacerseCargoHeladera();
        hacerseCargoHeladera1.setColaborador(colaborador1);
        hacerseCargoHeladera1.setFechaHora(LocalDateTime.now());
        hacerseCargoHeladera1.setNuevaHeladera(heladera1);
        hacerseCargoHeladera1.agregarHeladera(heladera1);

        HacerseCargoHeladera hacerseCargoHeladera2 = new HacerseCargoHeladera();
        hacerseCargoHeladera2.setColaborador(colaborador1);
        hacerseCargoHeladera2.setFechaHora(LocalDateTime.now());
        hacerseCargoHeladera2.setNuevaHeladera(heladera2);
        hacerseCargoHeladera1.agregarHeladera(heladera2);

        HacerseCargoHeladera hacerseCargoHeladera3 = new HacerseCargoHeladera();
        hacerseCargoHeladera3.setColaborador(colaborador1);
        hacerseCargoHeladera3.setFechaHora(LocalDateTime.now());
        hacerseCargoHeladera3.setNuevaHeladera(heladera3);
        hacerseCargoHeladera1.agregarHeladera(heladera3);

        List<Vianda> viandas = new ArrayList<>();
        viandas.add(vianda1);
        viandas.add(vianda2);

        DistribucionViandas distribucionViandas = new DistribucionViandas();
        distribucionViandas.setColaborador(colaborador1);
        distribucionViandas.setCantidadViandas(viandas.size());
        distribucionViandas.setHeladeraOrigen(heladera1);
        distribucionViandas.setHeladeraDestino(heladera2);
        distribucionViandas.setMotivo("falta_vianda");
        distribucionViandas.setViandas(viandas);

        RealizarOfertas realizarOfertas = new RealizarOfertas();
        realizarOfertas.setColaborador(colaborador3);
        realizarOfertas.setNuevaProducto(producto1);

        List<PersonaVulnerable> personasVulnerables = new ArrayList<>();
        personasVulnerables.add(personaVulnerable1);

        RegistroPersonaVulnerable registroPersonaVulnerable = new RegistroPersonaVulnerable();
        registroPersonaVulnerable.setColaborador(colaborador1);
        registroPersonaVulnerable.setNuevoVulnerable(personaVulnerable1);
        registroPersonaVulnerable.setPersonasVulnerables(personasVulnerables);

        DonacionVianda donacionVianda1 = new DonacionVianda();
        donacionVianda1.setColaborador(colaborador1);
        donacionVianda1.setFechaHora(LocalDateTime.now());
        donacionVianda1.agregarVianda(vianda1);
        DonacionVianda donacionVianda2 = new DonacionVianda();
        donacionVianda2.setColaborador(colaborador1);
        donacionVianda2.setFechaHora(LocalDateTime.now());
        donacionVianda2.agregarVianda(vianda4);
        DonacionVianda donacionVianda3 = new DonacionVianda();
        donacionVianda3.setColaborador(colaborador2);
        donacionVianda3.setFechaHora(LocalDateTime.now());
        donacionVianda3.agregarVianda(vianda2);
        DonacionVianda donacionVianda4 = new DonacionVianda();
        donacionVianda4.setColaborador(colaborador2);
        donacionVianda4.setFechaHora(LocalDateTime.now());
        donacionVianda4.agregarVianda(vianda3);

        repositorioContribucion.guardar(donacionDinero);

        repositorioContribucion.guardar(hacerseCargoHeladera1);
        repositorioContribucion.guardar(hacerseCargoHeladera2);
        repositorioContribucion.guardar(hacerseCargoHeladera3);

        repositorioContribucion.guardar(distribucionViandas);

        repositorioContribucion.guardar(realizarOfertas);

        repositorioContribucion.guardar(registroPersonaVulnerable);

        repositorioContribucion.guardar(donacionVianda1);
        repositorioContribucion.guardar(donacionVianda2);
        repositorioContribucion.guardar(donacionVianda3);
        repositorioContribucion.guardar(donacionVianda4);

        /******************************
         * ACTUALIZADO COLABORADORES
         ******************************/
        colaborador1.agregarContribucion(donacionDinero);
        colaborador1.agregarContribucion(hacerseCargoHeladera1);
        colaborador1.agregarContribucion(hacerseCargoHeladera2);
        colaborador1.agregarContribucion(hacerseCargoHeladera3);
        colaborador1.agregarContribucion(distribucionViandas);
        colaborador1.agregarContribucion(registroPersonaVulnerable);
        colaborador1.agregarContribucion(donacionVianda1);
        colaborador1.agregarContribucion(donacionVianda2);

        Double totalPuntos1 = calculadoraDePuntos.calcularPuntosContribuidor(colaborador1);
        colaborador1.setPuntosTotales((int) Math.ceil(totalPuntos1));

        colaborador2.agregarContribucion(donacionVianda3);
        colaborador2.agregarContribucion(donacionVianda4);

        Double totalPuntos2 = calculadoraDePuntos.calcularPuntosContribuidor(colaborador2);
        colaborador2.setPuntosTotales((int) Math.ceil(totalPuntos2));

        colaborador3.agregarContribucion(realizarOfertas);

        Double totalPuntos3 = calculadoraDePuntos.calcularPuntosContribuidor(colaborador3);
        colaborador3.setPuntosTotales((int) Math.ceil(totalPuntos3));

        repositorioColaborador.modificar(colaborador1);
        repositorioColaborador.modificar(colaborador2);
        repositorioColaborador.modificar(colaborador3);

        /****************************** SUSCRIPCION ******************************/
        RepositorioSuscripcion repositorioSuscripcion = ServiceLocator.instanceOf(RepositorioSuscripcion.class);

        Suscripcion suscripcion1 = Suscripcion
                .builder()
                .heladera(heladera1)
                .medioDeContacto(medioDeContacto1)
                .tipoSuscripcion(new MuchasViandas(50))
                .colaborador(colaborador1)
                .build();

        Suscripcion suscripcion2 = Suscripcion
                .builder()
                .heladera(heladera2)
                .medioDeContacto(medioDeContacto2)
                .tipoSuscripcion(new PocasViandas(50))
                .colaborador(colaborador2)
                .build();

        Suscripcion suscripcion3 = Suscripcion
                .builder()
                .heladera(heladera1)
                .medioDeContacto(medioDeContacto1)
                .tipoSuscripcion(new DesperfectoHeladera())
                .colaborador(colaborador1)
                .build();

        repositorioSuscripcion.guardar(suscripcion1);
        repositorioSuscripcion.guardar(suscripcion2);
        repositorioSuscripcion.guardar(suscripcion3);

        /****************************** INCIDENTE ******************************/
        // RepositorioDeIncidentes repositorioDeIncidentes =
        // ServiceLocator.instanceOf(RepositorioDeIncidentes.class);
        //
        // Incidente incidente1 = Incidente.builder()
        // .fechaYHora(LocalDateTime.now())
        // .tipoIncidente(TipoIncidente.FallaTecnica)
        // .descripcion("Descripcion incidente 1")
        // .estaResuelto(false)
        // .foto("incendios.jpg")
        // .heladera(heladera1)
        // .build();
        //
        // Incidente incidente2 = Incidente.builder()
        // .fechaYHora(LocalDateTime.now())
        // .tipoIncidente(TipoIncidente.Alerta)
        // .tipoAlerta(TipoAlerta.Fraude)
        // .descripcion("Descripcion incidente 2")
        // .estaResuelto(false)
        // .foto("incendios.jpg")
        // .heladera(heladera1)
        // .build();
        //
        // Incidente incidente3 = Incidente.builder()
        // .fechaYHora(LocalDateTime.now())
        // .tipoIncidente(TipoIncidente.FallaTecnica)
        // .descripcion("Descripcion incidente 3")
        // .estaResuelto(false)
        // .foto("incendios.jpg")
        // .heladera(heladera3)
        // .build();
        //
        // Incidente incidente4 = Incidente.builder()
        // .fechaYHora(LocalDateTime.now())
        // .tipoIncidente(TipoIncidente.FallaTecnica)
        // .descripcion("Descripcion incidente 4")
        // .estaResuelto(false)
        // .foto("incendios.jpg")
        // .heladera(heladera1)
        // .build();
        //
        // repositorioDeIncidentes.guardar(incidente1);
        // repositorioDeIncidentes.guardar(incidente2);
        // repositorioDeIncidentes.guardar(incidente3);
        // repositorioDeIncidentes.guardar(incidente4);

        /****************************** VISITA TECNICA ******************************/
        // RepositorioRegistroVisitaTecnica repositorioRegistroVisitaTecnica =
        // ServiceLocator
        // .instanceOf(RepositorioRegistroVisitaTecnica.class);
        //
        // RegistroVisitaTecnica visitaTecnica = RegistroVisitaTecnica.builder()
        // .fecha(LocalDate.now())
        // .incidenteSolucionado(false)
        // .descripcion("Descripcion visita")
        // .tecnico(tecnico1)
        // .incidente(incidente1)
        // .build();
        //
        // RegistroVisitaTecnica registroVisitaTecnica2 =
        // RegistroVisitaTecnica.builder()
        // .fecha(LocalDate.now())
        // .foto("/fotos_visita_tecnica/El-Archivo-de-las-Tormentas-Viento-y-Verdad-ilustracion-para-la-portada-USA-arte-de-Michael-Whelan.jpg")
        // .incidenteSolucionado(false)
        // .descripcion("Descripcion visita")
        // .tecnico(tecnico1)
        // .incidente(incidente1)
        // .build();
        //
        // repositorioRegistroVisitaTecnica.guardar(visitaTecnica);
        // repositorioRegistroVisitaTecnica.guardar(registroVisitaTecnica2);

        /****************************** USUARIO ******************************/
        RepositorioUsuario repositorioUsuario = ServiceLocator.instanceOf(RepositorioUsuario.class);

        Usuario usuario1 = Usuario.builder()
                .username("user1")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .colaborador(colaborador1)
                .tipoRol(TipoRol.PERSONA_HUMANA)
                .build();

        Usuario usuario2 = Usuario.builder()
                .username("admin1")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .colaborador(colaborador2)
                .tipoRol(TipoRol.ADMIN)
                .build();

        Usuario usuario3 = Usuario.builder()
                .username("juri1")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .colaborador(colaborador3)
                .tipoRol(TipoRol.PERSONA_JURIDICA)
                .build();

        Usuario usuario4 = Usuario.builder()
                .username("tecni1")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .tecnico(tecnico1)
                .tipoRol(TipoRol.TECNICO)
                .build();

        Usuario usuario5 = Usuario.builder()
                .username("tecni2")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .tecnico(tecnico2)
                .tipoRol(TipoRol.TECNICO)
                .build();

        Usuario usuario6 = Usuario.builder()
                .username("tecni3")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .tecnico(tecnico3)
                .tipoRol(TipoRol.TECNICO)
                .build();

        Usuario usuario7 = Usuario.builder()
                .username("tecni4")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .tecnico(tecnico4)
                .tipoRol(TipoRol.TECNICO)
                .build();

        Usuario usuario8 = Usuario.builder()
                .username("tecni5")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .tecnico(tecnico5)
                .tipoRol(TipoRol.TECNICO)
                .build();

        Usuario usuario9 = Usuario.builder()
                .username("tecni6")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .tecnico(tecnico6)
                .tipoRol(TipoRol.TECNICO)
                .build();

        Usuario usuario10 = Usuario.builder()
                .username("tecni7")
                .password("$2y$10$ovpXuIT7GxjwGTv8J7CqS.kZldnMintqXU/YOq5NVg6ksyXfdu7m6")
                .tecnico(tecnico7)
                .tipoRol(TipoRol.TECNICO)
                .build();

        repositorioUsuario.guardar(usuario1);
        repositorioUsuario.guardar(usuario2);
        repositorioUsuario.guardar(usuario3);
        repositorioUsuario.guardar(usuario4);
        repositorioUsuario.guardar(usuario5);
        repositorioUsuario.guardar(usuario6);
        repositorioUsuario.guardar(usuario7);
        repositorioUsuario.guardar(usuario8);
        repositorioUsuario.guardar(usuario9);
        repositorioUsuario.guardar(usuario10);


        initialize = true;
        System.out.println("Inicialización completada.");
    }
    
    public static boolean isInitialized() {
        return initialize;
    }

    public static void restart(){
        initialize = false;
    }
}