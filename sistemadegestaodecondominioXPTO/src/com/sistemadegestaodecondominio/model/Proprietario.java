package com.sistemadegestaodecondominio.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Proprietario implements Serializable {
  private String _id = UUID.randomUUID().toString();
  private String _nome;
  private String _morada;
  private String _telefone;
  private String _telemovel = null;
  private String _email;
  private Date _dataNascimento;

  /* Getters */
  public String getId() {
    return _id;
  }

  public String getNome() {
    return _nome;
  }

  public String getMorada() {
    return _morada;
  }

  public String getTelefone() {
    return _telefone;
  }

  public String getTelemovel() {
    return _telemovel;
  }

  public String getEmail() {
    return _email;
  }

  public Date getDataNascimento() {
    return _dataNascimento;
  }

  /* Setters */
  public void setId(String Id) {
    _id = Id;
  }

  public void setNome(String nome) {
    _nome = nome;
  }

  public void setMorada(String morada) {
    _morada = morada;
  }

  public void setTelefone(String telefone) {
    _telefone = telefone;
  }

  public void setTelemovel(String telemovel) {
    _telemovel = telemovel;
  }

  public void setEmail(String email) {
    _email = email;
  }

  public void setDataNascimento(Date dataNascimento) {
    _dataNascimento = dataNascimento;
  }

  public void apresentarDados() {
    System.out.println(String.format(
        "ID : %s\nNOME : %s\nMORADA : %s\nTELEFONE : %s\nTELEMOVEL : %s\nEMAIL : %s\nDATA DE NASCIMENTO : %s\n",
        _id,
        _nome != null ? _nome : "N/A",
        _morada != null ? _morada : "N/A",
        _telefone != null ? _telefone : "N/A",
        _telemovel != null ? _telemovel : "N/A",
        _email != null ? _email : "N/A",
        _dataNascimento != null ? _dataNascimento.toString() : "N/A"));
  }

  @Override
  public String toString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String obj = "Proprietario{id='%s', nome='%s', morada='%s', telefone='%s', telemovel='%s', email='%s', dataNascimento='%s'}";
    return String.format(obj, _id, _nome, _morada, _telefone, _telemovel, _email, dateFormat.format(_dataNascimento));
  }

  public Proprietario deserializacao(String data) {

    try {
      String regex = "Proprietario\\{id='([^']+)', nome='([^']+)', morada='([^']*)', telefone='([^']*)', telemovel='([^']*)', email='([^']*)', dataNascimento='([^']+)'\\}";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(data);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

      if (!matcher.find()) {
        throw new IllegalArgumentException("Erro ao tentar deserializar a string: " + data);
      }
      Proprietario proprietario = new Proprietario();
      proprietario.setId(matcher.group(1)); // id
      proprietario.setNome(matcher.group(2)); // Nome
      proprietario.setMorada(matcher.group(3));// Morada
      proprietario.setTelefone(matcher.group(4));// Telefone
      proprietario.setTelemovel(matcher.group(5));// Telemovel
      proprietario.setEmail(matcher.group(6));// Email
      proprietario.setDataNascimento(dateFormat.parse(matcher.group(7)));// DataNascimento
      return proprietario;

    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }
}
