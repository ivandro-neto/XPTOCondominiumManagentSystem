package com.sistemadegestaodecondominio.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loja extends Fracao {

  @Override
  public double calcularQuota(double despesaGeral, double despesaElevadores) {
    return (percentagem / 100) * despesaGeral;
  }

  @Override
  public String toString() {
    String obj = "Loja{id='%s', area='%f', percentagem='%f', localizacao='%s', proprietarioId='%s'}";
    return String.format(obj, this.id, this.area, this.percentagem, this.localizacao, this.proprietario.getId());
  }

  @Override
  public void apresentarDados() {
    System.out.println(
        String.format("ID : %s\nTIPO : Loja\nAREA(m2) : %.2f\nPERCENT. : %.2f\nLOCALIZACAO : %s\nPROPRIETARIO : %s\n",
            this.id, this.area, this.percentagem, this.localizacao, this.proprietario.getNome()));
  }

  @Override
  public Fracao deserializacao(String object) {
    // Regex para capturar os valores da string formatada
    String regex = "Loja\\{id='([^']+)', area='([^']+)', percentagem='([^']+)', localizacao='([^']+)', proprietarioId='([^']+)'\\}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(object);

    if (!matcher.find()) {
      throw new IllegalArgumentException("Erro ao tentar deserializar a string: " + object);
    }
    Loja loja = new Loja();

    // Atribuindo os valores extraídos da string aos campos do objeto
    loja.setId(matcher.group(1)); // id
    loja.setArea(Double.parseDouble(matcher.group(2))); // area
    loja.setPercentagem(Double.parseDouble(matcher.group(3))); // percentagem
    loja.setLocalizacao(matcher.group(4)); // localizacao

    // Criar e configurar o objeto Proprietario
    Proprietario proprietario = new Proprietario();
    proprietario.setId(matcher.group(5)); // proprietarioId
    loja.setProprietario(proprietario);

    return loja;
  }

}