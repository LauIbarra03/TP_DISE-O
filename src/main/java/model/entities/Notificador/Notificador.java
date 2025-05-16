package model.entities.Notificador;

import config.ServiceLocator;
import java.io.IOException;
import java.util.logging.Logger;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.Suscripcion.RegistroMensaje;
import model.repositories.RepositorioRegistroMensaje;


public class Notificador {
  public static final EnviadorMailAdapter enviadorMail = EnviadorMailAdapter.getInstance();
  public static final EnviadorWPPAdapter enviadorWPP = EnviadorWPPAdapter.getInstance();
  public static final EnviadorTelegramAdapter enviadorTelegram = EnviadorTelegramAdapter.getInstance();
  private static final Logger logger = Logger.getLogger(Notificador.class.getName());
  private static Notificador instancia = null;

  public static Notificador getInstance() {
    if (instancia == null) {
      instancia = new Notificador();
    }
    return instancia;
  }

  public void enviarWPP(String nroDestino, String mensaje) throws IOException {
    enviadorWPP.enviar(nroDestino, mensaje);
  }

  public void enviarMail(String destino, String mensaje) {
    enviadorMail.enviar(destino, mensaje);
  }

  public void enviarTelegram(String nroDestino, String mensaje) {
    enviadorTelegram.enviar(nroDestino, mensaje);
  }

  public void asunto(String asunto) {
    enviadorMail.setAsunto(asunto);
  }

  public void notificar(MedioDeContacto medioDeContacto, String mensaje) throws IOException {
    RegistroMensaje registroMensaje = new RegistroMensaje();
    registroMensaje.setMensaje(mensaje);
    registroMensaje.setMedioDeContacto(medioDeContacto);
    RepositorioRegistroMensaje repositorioRegistroMensaje = ServiceLocator.instanceOf(RepositorioRegistroMensaje.class);
    repositorioRegistroMensaje.guardar(registroMensaje);
    switch (medioDeContacto.getTipoContacto()) {
      case CORREO:
        this.enviarMail(medioDeContacto.getContacto(), mensaje);
        break;
      case TELEGRAM:
        this.enviarTelegram(medioDeContacto.getContacto(), mensaje);
        break;
      case WHATSAPP:
        this.enviarWPP(medioDeContacto.getContacto(), mensaje);
        break;
      default:
        logger.warning("El tipo de contacto no es valido");
    }
  }

}
