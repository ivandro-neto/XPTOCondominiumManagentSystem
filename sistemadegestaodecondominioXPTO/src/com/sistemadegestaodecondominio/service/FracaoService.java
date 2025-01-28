package com.sistemadegestaodecondominio.service;

import java.util.ArrayList;

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
    _fracoes = new ArrayList<Fracao>();
  }

  public Fracao getFracaoPorId(String id) {
    for (Fracao fracao : _fracoes) {
      if (fracao.getId().equals(id))
        return fracao;
    }
    return null;
  }

  public ArrayList<Fracao> getFracoes() {
    return _fracoes;
  }

  public Fracao getFracaoPeloProprietario(String nomeProprietario) {
    for (Fracao fracao : _fracoes) {
      if (fracao.getProprietario().getNome().equals(nomeProprietario))
        return fracao;
    }
    return null;
  }

  public ArrayList<String> toStringFormat() {
    ArrayList<String> newList = new ArrayList<String>();

    for (Fracao fracao : _fracoes)
      newList.add(fracao.toString());
    return newList;
  }

  public void adicionarFracao(Fracao fracao) {
    _fracoes.add(fracao);
  }

  public void removerFracao(Fracao fracao) {
    _fracoes.remove(fracao);
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
  public void CarregarDados() {
    ArrayList<String> data = _storageService.Carregar(path);

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
  }

  @SuppressWarnings("unchecked")
  public void SalvarDados() {
    _storageService.Salvar(toStringFormat(), path);
  }
}