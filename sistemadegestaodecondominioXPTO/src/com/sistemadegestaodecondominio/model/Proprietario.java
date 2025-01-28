package com.sistemadegestaodecondominio.model;

import java.util.Date;
import java.util.UUID;

public class Proprietario {
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
}
