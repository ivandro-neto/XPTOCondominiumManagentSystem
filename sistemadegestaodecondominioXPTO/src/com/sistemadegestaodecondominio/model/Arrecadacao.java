package com.sistemadegestaodecondominio.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Arrecadacao extends Fracao {

  private boolean _temPortaBlindada;

  public boolean TemPortaBlindada() {
    return _temPortaBlindada;
  }

  public void setTemPortaBlindada(boolean temPortaBlindada) {
    _temPortaBlindada = temPortaBlindada;
  }

  @Override
  public double calcularQuota(double despesaGeral, double despesaElevadores) {
    return (percentagem / 100) * (despesaGeral + despesaElevadores);
  }

  @Override
  public void apresentarDados() {
    System.out.println(String.format(
        "ID : %s\nTIPO : Arrecadacao\nAREA(m2) : %.2f\nPERCENT. : %.2f\nLOCALIZACAO : %s\nPROPRIETARIO : %s\nPORTA BLINDADA : %b\n",
        this.id, this.area, this.percentagem, this.localizacao, this.proprietario.getNome(), _temPortaBlindada));

  }

  @Override
  public String toString() {
    String obj = "Arrecadacao{id='%s', area='%f', percentagem='%f', localizacao='%s', proprietarioId='%s', temPortaBlindada='%b'}";
    return String.format(obj, this.id, this.area, this.percentagem, this.localizacao, this.proprietario.getId(),
        this._temPortaBlindada);
  }

  @Override
  public Fracao deserializacao(String object) {
    // Regex para capturar os valores da string formatada
    String regex = "Arrecadacao\\{id='([^']+)', area='([^']+)', percentagem='([^']+)', localizacao='([^']+)', proprietarioId='([^']+)', temPortaBlindada='([^']+)'\\}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(object);

    if (!matcher.find()) {
      throw new IllegalArgumentException("Erro ao tentar deserializar a string: " + object);
    }
    Arrecadacao arrecadacao = new Arrecadacao();

    arrecadacao.setId(matcher.group(1)); // id
    arrecadacao.setArea(Double.parseDouble(matcher.group(2))); // area
    arrecadacao.setPercentagem(Double.parseDouble(matcher.group(3))); // percentagem
    arrecadacao.setLocalizacao(matcher.group(4)); // localizacao

    // Criar e configurar o objeto Proprietario
    Proprietario proprietario = new Proprietario();
    proprietario.setId(matcher.group(5)); // proprietarioId
    arrecadacao.setProprietario(proprietario);

    // Configurar o campo temPortaBlindada
    arrecadacao.setTemPortaBlindada(Boolean.parseBoolean(matcher.group(6))); // temPortaBlindada

    return arrecadacao;
  }

}