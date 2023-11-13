
package com.dg.bu.viewmodel;

public class BookViewModel {
  private int id;
  private String creador;
  private String archivoDoc;
  private String titulo;
  private String origen;
  

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getCreador() {
    return creador;
  }

  public void setCreador(String creador) {
    this.creador = creador;
  }
  
  public String getArchivoDoc() {
    return archivoDoc;
  }

  public void setArchivoDoc(String archivoDoc) {
    this.archivoDoc = archivoDoc;
  }

  public String getOrigen() {
    return origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("BookViewModel{id=").append(id);
    sb.append(", creador='").append(creador).append('\'');
    sb.append(", archivoDoc='").append(archivoDoc).append('\'');
    sb.append(", titulo='").append(titulo).append('\'');
    sb.append(", origen='").append(origen).append('\'');
    sb.append('}');
    return sb.toString();
  }

  
    
}
