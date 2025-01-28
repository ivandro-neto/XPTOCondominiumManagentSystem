
package com.sistemadegestaodecondominio.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ivandro Neto
 */
public class Apartamento extends Fracao {
  private String _tipo;
  private int _nCasasDeBanho;
  private int _nVarandas;
  private boolean _temTerraco;

  /* Getters */
  public String getTipo() {
    return _tipo;
  }

  public int getNCasasDeBanho() {
    return _nCasasDeBanho;
  }

  public int getNVarandas() {
    return _nVarandas;
  }

  public boolean isTemTerraco() {
    return _temTerraco;
  }

  /* Setters */
  public void setTipo(String tipo) {
    _tipo = tipo;
  }

  public void setNCasasDeBanho(int nCasasDeBanho) {
    _nCasasDeBanho = nCasasDeBanho;
  }

  public void setNVarandas(int nVarandas) {
    _nVarandas = nVarandas;
  }

  public void setTemTerraco(boolean temTerraco) {
    _temTerraco = temTerraco;
  }

  @Override
  public double calcularQuota(double despesaGeral, double despesaElevadores) {
    return (percentagem / 100) * (despesaGeral + despesaElevadores);
  }

  @Override
  public String toString() {
    String obj = "Apartamento{id='%s', area='%f', percentagem='%f', localizacao='%s', proprietarioId='%s', tipologia='%s', numeroVarandas='%d', numeroCasaDeBanho='%d', temTerraco='%b'}";
    return String.format(obj, this.id, this.area, this.percentagem, this.localizacao, this.proprietario.getId(),
        this._tipo, this._nVarandas, this._nCasasDeBanho, this._temTerraco);
  }

  @Override
  public void apresentarDados() {
    System.out.println(String.format(
        "ID : %s\nTIPO : Apartamento\nAREA(m2) : %.2f\nPERCENT. : %.2f\nLOCALIZACAO : %s\nPROPRIETARIO : %s\nTIPOLOGIA : %s\nCASAS DE BANHO : %d\nVARANDAS : %d\nTERRACO : %b\n",
        this.id, this.area, this.percentagem, this.localizacao, this.proprietario.getNome(), _tipo, _nCasasDeBanho,
        _nVarandas, _temTerraco));

  }

  @Override
  public Fracao deserializacao(String object) {
    // Usando regex para separar os campos baseados no padrão de 'key=value'
    String regex = "Apartamento\\{id='([^']+)', area='([^']+)', percentagem='([^']+)', localizacao='([^']+)', proprietarioId='([^']+)', tipologia='([^']+)', numeroVarandas='([^']+)', numeroCasaDeBanho='([^']+)', temTerraco='([^']+)'\\}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(object);

    if (!matcher.find()) {
      throw new IllegalArgumentException("Erro ao tentar deserializar a string: " + object);
    }
    Apartamento apartamento = new Apartamento();

    apartamento.setId(matcher.group(1)); // id
    apartamento.setArea(Double.parseDouble(matcher.group(2))); // area
    apartamento.setPercentagem(Double.parseDouble(matcher.group(3))); // percentagem
    apartamento.setLocalizacao(matcher.group(4)); // localizacao
    Proprietario proprietario = new Proprietario(); // Criar o objeto Proprietario
    proprietario.setId(matcher.group(5)); // proprietarioId
    apartamento.setProprietario(proprietario);

    apartamento.setTipo(matcher.group(6)); // tipologia
    apartamento.setNCasasDeBanho(Integer.parseInt(matcher.group(7))); // numeroCasasDeBanho
    apartamento.setNCasasDeBanho(Integer.parseInt(matcher.group(8))); // numeroVarandas
    apartamento.setTemTerraco(Boolean.parseBoolean(matcher.group(9))); // temTerraco

    return apartamento;
  }
}
