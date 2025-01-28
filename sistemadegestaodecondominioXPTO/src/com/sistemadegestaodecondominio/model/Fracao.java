package com.sistemadegestaodecondominio.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Ivandro Neto
 */

public abstract class Fracao implements Serializable{
  protected String id = UUID.randomUUID().toString();
  protected double area;
  protected double percentagem;
  protected String localizacao;
  protected Proprietario proprietario;

  public Fracao(){}
  /* Getters */
  public String getId() {
    return id;
  }
  public double getArea() {
    return area;
  }
  public double getPercentagem() {
    return percentagem;
  }
  public String getLocalizacao() {
    return localizacao;
  }
  public Proprietario getProprietario() {
    return proprietario;
  }

  /* Setters */
  public void setProprietario(Proprietario proprietario) {
    this.proprietario = proprietario;
  }
  public void setLocalizacao(String localizacao) {
    this.localizacao = localizacao;
  }
  public void setPercentagem(double percentagem) {
    this.percentagem = percentagem;
  }
  public void setArea(double area) {
    this.area = area;
  }
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public abstract String toString();
  public abstract double calcularQuota(double despesaGeral, double despesaElevadores);
}
