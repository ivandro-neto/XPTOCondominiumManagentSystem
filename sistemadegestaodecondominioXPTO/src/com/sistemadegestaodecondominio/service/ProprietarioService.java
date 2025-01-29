package com.sistemadegestaodecondominio.service;

import java.util.ArrayList;

import com.sistemadegestaodecondominio.exceptions.ProprietarioException;
import com.sistemadegestaodecondominio.exceptions.messages.error.ProprietarioErrorMensagem;
import com.sistemadegestaodecondominio.exceptions.messages.sucess.ProprietarioSucessoMensagem;
import com.sistemadegestaodecondominio.model.Proprietario;

public class ProprietarioService {
  private ArrayList<Proprietario> _proprietarios;
  @SuppressWarnings("rawtypes")
  private IStorage _storageService;
  private final String path = "Proprietario.txt";

  public ProprietarioService(@SuppressWarnings("rawtypes") StorageService storage) {
    _storageService = storage;
    _proprietarios = new ArrayList<>();
  }

  public Proprietario getProprietarioPeloNome(String nome) {
    for (Proprietario proprietario : _proprietarios) {
      if (proprietario.getNome().equalsIgnoreCase(nome)) {
        return proprietario;
      }
    }
    return null;
  }

  public Proprietario getProprietarioPeloId(String id) {
    for (Proprietario proprietario : _proprietarios) {
      if (proprietario.getId().equals(id)) {
        return proprietario;
      }
    }
    return null;
  }

  public ArrayList<String> toStringFormat() {
    ArrayList<String> newList = new ArrayList<>();
    for (Proprietario proprietario : _proprietarios) {
      newList.add(proprietario.toString());
    }
    return newList;
  }

  public void adicionarProprietario(Proprietario proprietario) {
    try {
      if (proprietario == null) {
        throw new ProprietarioException(ProprietarioErrorMensagem.ERRO_CRIAR_PROPRIETARIO);
      }
      _proprietarios.add(proprietario);
      System.out.println(ProprietarioSucessoMensagem.SUCESSO_ADICIONAR_PROPRIETARIO);
    } catch (ProprietarioException e) {
      System.out.println(e.getMessage());
    }
  }

  public void removerProprietario(Proprietario proprietario) {
    try {
      if (!_proprietarios.remove(proprietario)) {
        throw new ProprietarioException(ProprietarioErrorMensagem.ERRO_REMOVER_PROPRIETARIO);
      }
      System.out.println(ProprietarioSucessoMensagem.SUCESSO_REMOVER_PROPRIETARIO);
    } catch (ProprietarioException e) {
      System.out.println(e.getMessage());
    }
  }

  public void listarProprietarios() {
    if (_proprietarios.isEmpty()) {
      System.out.println(ProprietarioErrorMensagem.ERRO_LISTAR_PROPRIETARIOS);
      return;
    }
    for (Proprietario proprietario : _proprietarios) {
      proprietario.apresentarDados();
    }
  }

  @SuppressWarnings("unchecked")
  public void carregarDados() {
    try {
      ArrayList<String> data = _storageService.Carregar(path);
      if (data.isEmpty()) {
        throw new ProprietarioException(ProprietarioErrorMensagem.ERRO_CARREGAR_DADOS);
      }

      for (String item : data) {
        Proprietario proprietario = new Proprietario();
        proprietario = proprietario.deserializacao(item);
        _proprietarios.add(proprietario);
      }
      System.out.println(ProprietarioSucessoMensagem.SUCESSO_CARREGAR_DADOS);
    } catch (ProprietarioException e) {
      System.out.println(e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  public void salvarDados() {
    try {
      if (_proprietarios.isEmpty()) {
        throw new ProprietarioException(ProprietarioErrorMensagem.ERRO_SALVAR_DADOS);
      }
      _storageService.Salvar(toStringFormat(), path);
      System.out.println(ProprietarioSucessoMensagem.SUCESSO_SALVAR_DADOS);
    } catch (ProprietarioException e) {
      System.out.println(e.getMessage());
    }
  }
}
