package com.sistemadegestaodecondominio.service;

import java.util.ArrayList;

import com.sistemadegestaodecondominio.exceptions.FracaoException;
import com.sistemadegestaodecondominio.exceptions.messages.error.FracaoErrorMensagem;
import com.sistemadegestaodecondominio.exceptions.messages.sucess.FracaoSucessoMensagem;
import com.sistemadegestaodecondominio.model.Apartamento;
import com.sistemadegestaodecondominio.model.Arrecadacao;
import com.sistemadegestaodecondominio.model.Fracao;
import com.sistemadegestaodecondominio.model.Garagem;
import com.sistemadegestaodecondominio.model.Loja;

public class FracaoService {
  private ArrayList<Fracao> _fracoes;
  private StorageService _storageService;
  private ProprietarioService _ProprietarioService;
  private final String path = "Fracao.txt";

  public FracaoService(StorageService storage, ProprietarioService proprietarioService) {
    _storageService = storage;
    _ProprietarioService = proprietarioService;
    _fracoes = new ArrayList<>();
  }

  public Fracao getFracaoPorId(String id) {
    try {
      for (Fracao fracao : _fracoes) {
        if (fracao.getId().equals(id))
          return fracao;
      }
      throw new FracaoException(FracaoErrorMensagem.ERRO_FRACAO_NAO_ENCONTRADA);
    } catch (FracaoException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public ArrayList<Fracao> getFracoes() {
    try {
      if (_fracoes.isEmpty()) {
        throw new FracaoException(FracaoErrorMensagem.ERRO_LISTAR_FRACOES);
      }
      return _fracoes;
    } catch (FracaoException e) {
      System.out.println(e.getMessage());
      return new ArrayList<>();
    }
  }

  public Fracao getFracaoPeloProprietario(String nomeProprietario) {
    for (Fracao fracao : _fracoes) {
      if (fracao.getProprietario().getNome().equals(nomeProprietario))
        return fracao;
    }
    return null;
  }

  public ArrayList<String> toStringFormat() {
    ArrayList<String> newList = new ArrayList<>();
    for (Fracao fracao : _fracoes)
      newList.add(fracao.toString());
    return newList;
  }

  public void adicionarFracao(Fracao fracao) {
    try {
      if (fracao == null) {
        throw new FracaoException(FracaoErrorMensagem.ERRO_CRIAR_FRACAO);
      }
      _fracoes.add(fracao);
      System.out.println(FracaoSucessoMensagem.SUCESSO_CRIAR_FRACAO);
    } catch (FracaoException e) {
      System.out.println(e.getMessage());
    }
  }

  public void removerFracao(Fracao fracao) {
    try {
      if (!_fracoes.remove(fracao)) {
        throw new FracaoException(FracaoErrorMensagem.ERRO_REMOVER_FRACAO);
      }
      System.out.println(FracaoSucessoMensagem.SUCESSO_REMOVER_FRACAO);
    } catch (FracaoException e) {
      System.out.println(e.getMessage());
    }
  }

  public Fracao criarFracao(String tipo) {
    switch (tipo.toLowerCase()) {
      case "loja":
        return new Loja();
      case "apartamento":
        return new Apartamento();
      case "garagem":
        return new Garagem();
      case "arrecadacao":
        return new Arrecadacao();
      default:
        return null;
    }
  }

  @SuppressWarnings("unchecked")
  public void carregarDados() {
    try {
      ArrayList<String> data = _storageService.Carregar(path);
      if (data.isEmpty()) {
        throw new FracaoException(FracaoErrorMensagem.ERRO_CARREGAR_DADOS);
      }

      for (String line : data) {
        if (line.startsWith("Loja")) {
          Loja loja = new Loja();
          loja = (Loja) loja.deserializacao(line);
          loja.setProprietario(_ProprietarioService.getProprietarioPeloId(loja.getProprietario().getId()));
          _fracoes.add(loja);
        } else if (line.startsWith("Garagem")) {
          Garagem garagem = new Garagem();
          garagem = (Garagem) garagem.deserializacao(line);
          garagem.setProprietario(_ProprietarioService.getProprietarioPeloId(garagem.getProprietario().getId()));
          _fracoes.add(garagem);
        } else if (line.startsWith("Apartamento")) {
          Apartamento apartamento = new Apartamento();
          apartamento = (Apartamento) apartamento.deserializacao(line);
          apartamento.setProprietario(_ProprietarioService.getProprietarioPeloId(apartamento.getProprietario().getId()));
          _fracoes.add(apartamento);
        } else if (line.startsWith("Arrecadacao")) {
          Arrecadacao arrecadacao = new Arrecadacao();
          arrecadacao = (Arrecadacao) arrecadacao.deserializacao(line);
          arrecadacao.setProprietario(_ProprietarioService.getProprietarioPeloId(arrecadacao.getProprietario().getId()));
          _fracoes.add(arrecadacao);
        }
      }
      System.out.println(FracaoSucessoMensagem.SUCESSO_CARREGAR_DADOS);
    } catch (FracaoException e) {
      System.out.println(e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  public void salvarDados() {
    try {
      if (_fracoes.isEmpty()) {
        throw new FracaoException(FracaoErrorMensagem.ERRO_SALVAR_DADOS);
      }
      _storageService.Salvar(toStringFormat(), path);
      System.out.println(FracaoSucessoMensagem.SUCESSO_SALVAR_DADOS);
    } catch (FracaoException e) {
      System.out.println(e.getMessage());
    }
  }
}
