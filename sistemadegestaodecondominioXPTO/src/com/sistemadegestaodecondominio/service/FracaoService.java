package com.sistemadegestaodecondominio.service;

import java.util.ArrayList;

import com.sistemadegestaodecondominio.model.Apartamento;
import com.sistemadegestaodecondominio.model.Arrecadacao;
import com.sistemadegestaodecondominio.model.Fracao;
import com.sistemadegestaodecondominio.model.Garagem;
import com.sistemadegestaodecondominio.model.Loja;

public class FracaoService{
  private ArrayList<Fracao> _fracoes;
  private IStorage _storageService;
  private final String path = "Fracao.txt";
  public FracaoService(IStorage storage) 
  {
    _storageService = storage;
    _fracoes = new ArrayList<Fracao>();
  }

  public Fracao getFracaoPorId(String id)
  {
    for(Fracao fracao : _fracoes)
    {
      if(fracao.getId().equals(id))
        return fracao;
    }
    return null;
  }

  public Fracao getFracaoPeloProprietario(String nomeProprietario){
    for(Fracao fracao : _fracoes){
      if(fracao.getProprietario().getNome().equals(nomeProprietario))
        return fracao;
    }
    return null;
  }
  public void listarFracoes(){
    for(Fracao fracao : _fracoes){
      System.out.println(fracao.toString());
    }
  }

  public ArrayList<String> toStringFormat(){
    ArrayList<String> newList = new ArrayList<String>();

    for(Fracao fracao : _fracoes)
      newList.add(fracao.toString());
    return newList;
  }

  public void adicionarFracao(Fracao fracao){
    _fracoes.add(fracao);
  }
  public void removerFracao(Fracao fracao){
    _fracoes.remove(fracao);
  }

  public Fracao criarFracao(String tipo){
    switch (tipo.toLowerCase()) {
      case "loja": return new Loja();
      case "apartamento" : return new Apartamento();  
      case "garagem" : return new Garagem();  
      case "arrecadacao" : return new Arrecadacao();
      default: return null;
    }
  }

  @SuppressWarnings("unchecked")
  public void CarregarDados(){
    ArrayList<String> data = _storageService.Carregar(path);

    for (String line : data) {
        if (line.startsWith("Loja")) {
            Loja loja = new Loja();
            loja = loja.Deserialization(line); // Deserializa a linha para um objeto Loja
            loja.setProprietario(null);
            _fracoes.add(loja); // Adiciona ao array de frações
        } else if (line.startsWith("Garagem")) {
            Garagem garagem = new Garagem();
            garagem = garagem.Deserialization(line); // Deserializa a linha para um objeto Garagem
            _fracoes.add(garagem); // Adiciona ao array de frações
        } else if (line.startsWith("Apartamento")) {
            Apartamento apartamento = new Apartamento();
            apartamento = apartamento.Deserialization(line); // Deserializa a linha para um objeto Apartamento
            _fracoes.add(apartamento); // Adiciona ao array de frações
        } else if (line.startsWith("Arrecadacao")) {
            Arrecadacao arrecadacao = new Arrecadacao();
            arrecadacao = arrecadacao.Deserialization(line); // Deserializa a linha para um objeto Arrecadacao
            _fracoes.add(arrecadacao); // Adiciona ao array de frações
        }
        
    }
}

  @SuppressWarnings("unchecked")
  public void SalvarDados(){
    _storageService.Salvar(toStringFormat(), path);
  }
}