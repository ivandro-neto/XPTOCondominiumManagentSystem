package com.sistemadegestaodecondominio.service;

import java.util.ArrayList;

import com.sistemadegestaodecondominio.model.Fracao;
import com.sistemadegestaodecondominio.model.Proprietario;

public class ProprietarioService {
  private ArrayList<Proprietario> _proprietarios;
  @SuppressWarnings("rawtypes")
  private IStorage _storageService;
  private final String path = "Proprietario.txt";

  public ProprietarioService(@SuppressWarnings("rawtypes") StorageService storage) {
    _storageService = storage;
    _proprietarios = new ArrayList<Proprietario>();
  }

  public Proprietario getProprietarioPeloNome(String nome) {
    for (Proprietario proprietario : _proprietarios) {
      if (proprietario.getNome().equals(nome))
        return proprietario;
    }
    return null;
  }

  public Proprietario getProprietarioPeloId(String Id) {
    for (Proprietario proprietario : _proprietarios) {
      if (proprietario.getId().equals(Id))
        return proprietario;
    }
    return null;
  }

  public ArrayList<String> toStringFormat() {
    ArrayList<String> newList = new ArrayList<String>();

    for (Proprietario proprietario : _proprietarios)
      newList.add(proprietario.toString());
    return newList;
  }

  public void adicionarProprietario(Proprietario proprietario) {
    _proprietarios.add(proprietario);
  }

  public void removerProprietario(Proprietario proprietario) {
    _proprietarios.remove(proprietario);
  }

  public void listarProprietarios() {
    for (Proprietario proprietario : _proprietarios) {
      proprietario.apresentarDados();
    }
  }

  @SuppressWarnings("unchecked")
  public void CarregarDados() {
    ArrayList<String> data = _storageService.Carregar(path);

    for (String item : data) {
      Proprietario proprietario = new Proprietario();
      proprietario = proprietario.deserializacao(item);
      _proprietarios.add(proprietario);
    }
  }

  @SuppressWarnings("unchecked")
  public void SalvarDados() {
    _storageService.Salvar(toStringFormat(), path);
  }
}
