package com.sistemadegestaodecondominio.service;

import java.util.ArrayList;

import com.sistemadegestaodecondominio.model.Proprietario;

public class ProprietarioService {
  private ArrayList<Proprietario> _proprietarios; 

  public ProprietarioService(){
    _proprietarios = new ArrayList<Proprietario>();
  }

  public Proprietario getProprietarioPeloNome(String nome){
    for(Proprietario proprietario : _proprietarios){
      if(proprietario.getNome().equals(nome))
        return proprietario;
    }
    return null;
  }
  public Proprietario getProprietarioPeloId(String Id){
    for(Proprietario proprietario : _proprietarios){
      if(proprietario.getId().equals(Id))
        return proprietario;
    }
    return null;
  }

  public void adicionarProprietario(Proprietario proprietario){
    _proprietarios.add(proprietario);
  }
  public void removerProprietario(Proprietario proprietario){
    _proprietarios.remove(proprietario);
  }
}
