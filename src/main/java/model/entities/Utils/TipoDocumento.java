package model.entities.Utils;

public enum TipoDocumento {
  DNI("Documento Nacional de Identidad"),
  LE("Libreta de Enrolamiento"),
  LC("Licencia de Conducir"),
  ;

  private final String descripcion;

  TipoDocumento(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return descripcion;
  }
}