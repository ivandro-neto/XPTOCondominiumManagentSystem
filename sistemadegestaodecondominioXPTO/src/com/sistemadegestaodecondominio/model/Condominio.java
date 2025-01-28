package com.sistemadegestaodecondominio.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.sistemadegestaodecondominio.service.FracaoService;

/**
 *
 * @author Ivandro Neto
 */
public class Condominio implements Serializable{
  private String _id = UUID.randomUUID().toString();
  private String _morada;
  private double _despesaGeral;
  private double _despesaElevadores;
  private Date _dataConstrucao;
  private int _nFracoes;
  private FracaoService _fracaoService;  

  public Condominio(FracaoService fracaoService){
    _fracaoService = fracaoService;
  }
  /* Getters */
  public String getId() {
    return _id;
  }
  public String getMorada() {
    return _morada;
  }
  public double getDespesaGeral() {
    return _despesaGeral;
  }
  public double getDespesaElevadores() {
    return _despesaElevadores;
  }
  public void setDespesaElevadores(double despesaElevadores) {
    _despesaElevadores = despesaElevadores;
  }
  public Date getDataConstrucao() {
    return _dataConstrucao;
  }
  public int getNFracoes() {
    return _nFracoes;
  }

  /* Setters */

  public void setNFracoes(int nFracoes) {
    _nFracoes = nFracoes;
  }
  public void setDataConstrucao(Date dataConstrucao) {
    _dataConstrucao = dataConstrucao;
  }
  public void setDespesaGeral(double despesaGeral) {
    _despesaGeral = despesaGeral;
  }
  public void setMorada(String morada) {
    _morada = morada;
  }
  public void setId(String id) {
    _id = id;
  }

  /* Methods */
  public void inserirFracao(Fracao fracao){
    _fracaoService.adicionarFracao(fracao);
    _nFracoes++;
  }

  public void removerFracao(Fracao fracao){
    _fracaoService.removerFracao(fracao);
    _nFracoes--;
  }

  public void recalcularPercentagem(){

  }

  public void listarFracao(){

  }

  public boolean verificarPercentagemTotal(){
    return true;
  }

  public double  calcularQuotas() {
    return 0;
  }

  @Override
  public String toString() {
    String obj = "Condominio{id='%s', morada='%s', despesaGeral='%f', despesaElevadores='%f', dataConstrucao='%s', nFracoes='%d'}"; 
    return String.format(obj,this._id,this._morada, this._despesaGeral, this._despesaElevadores, this._dataConstrucao, this._nFracoes);
  }
}
