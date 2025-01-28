package com.sistemadegestaodecondominio.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Garagem extends Fracao{
  private int _nCapacidadeLigeiros;
  private boolean _temLavagem;

  public int getNCapacidadeLigeiros() {
    return _nCapacidadeLigeiros;
  }
  public boolean TemLavagem() {
    return _temLavagem;
  }
  public void setTemLavagem(boolean temLavagem) {
    _temLavagem = temLavagem;
  }
  public void setNCapacidadeLigeiros(int nCapacidadeLigeiros) {
    _nCapacidadeLigeiros = nCapacidadeLigeiros;
  }

  @Override
  public double calcularQuota(double despesaGeral, double despesaElevadores) {
    throw new UnsupportedOperationException("Unimplemented method 'calcularQuota'");
  }
  @Override
  public String toString() {
    String obj = "Garagem{id='%s', area='%f', percentagem='%f', localizacao='%s', proprietarioId='%s', capacidade='%d', temLavagem='%b'}"; 
    return String.format(obj,this.id,this.area, this.percentagem, this.localizacao, this.proprietario.getId(), this._nCapacidadeLigeiros, this._temLavagem);
  }

  public Garagem Deserialization(String object) {
    // Regex para capturar os valores da string formatada
    String regex = "Garagem\\{id='([^']+)', area='([^']+)', percentagem='([^']+)', localizacao='([^']+)', proprietarioId='([^']+)', capacidade='([^']+)', temLavagem='([^']+)'\\}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(object);

    if (matcher.find()) {
        Garagem garagem = new Garagem();
        
        // Atribuindo os valores extraídos da string aos campos do objeto
        garagem.setId(matcher.group(1));  // id
        garagem.setArea(Double.parseDouble(matcher.group(2)));  // area
        garagem.setPercentagem(Double.parseDouble(matcher.group(3)));  // percentagem
        garagem.setLocalizacao(matcher.group(4));  // localizacao
        
        // Criar e configurar o objeto Proprietario
        Proprietario proprietario = new Proprietario();
        proprietario.setId(matcher.group(5));  // proprietarioId
        garagem.setProprietario(proprietario);
        
        garagem.setNCapacidadeLigeiros(Integer.parseInt(matcher.group(6)));  // capacidade
        garagem.setTemLavagem(Boolean.parseBoolean(matcher.group(7)));  // temLavagem
        
        return garagem;
    } else {
        throw new IllegalArgumentException("Erro ao tentar deserializar a string: " + object);
    }
}

}