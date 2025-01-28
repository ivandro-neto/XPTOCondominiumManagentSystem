package com.sistemadegestaodecondominio.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sistemadegestaodecondominio.service.FracaoService;
import com.sistemadegestaodecondominio.service.IStorage;
import com.sistemadegestaodecondominio.service.StorageService;

/**
 *
 * @author Ivandro Neto
 */
public class Condominio implements Serializable {
  private String _id = UUID.randomUUID().toString();
  private String _morada;
  private double _despesaGeral;
  private double _despesaElevadores;
  private Date _dataConstrucao;
  private int _nFracoes;
  private FracaoService _fracaoService;
  private StorageService _storageService;
  private final String path = "Condominio.txt";

  public Condominio(FracaoService fracaoService, StorageService storage) {
    _fracaoService = fracaoService;
    _storageService = storage;
  }

 public Condominio() {}
  /* Getters */
  public String getId() {
    return _id;
  }

  public String getMorada() {
    return _morada;
  }

  public double getDespesaGeral() {
    return _despesaGeral;
  }

  public double getDespesaElevadores() {
    return _despesaElevadores;
  }

  public void setDespesaElevadores(double despesaElevadores) {
    _despesaElevadores = despesaElevadores;
  }

  public Date getDataConstrucao() {
    return _dataConstrucao;
  }

  public int getNFracoes() {
    return _nFracoes;
  }

  /* Setters */

  public void setNFracoes(int nFracoes) {
    _nFracoes = nFracoes;
  }

  public void setDataConstrucao(Date dataConstrucao) {
    _dataConstrucao = dataConstrucao;
  }

  public void setDespesaGeral(double despesaGeral) {
    _despesaGeral = despesaGeral;
  }

  public void setMorada(String morada) {
    _morada = morada;
  }

  public void setId(String id) {
    _id = id;
  }

  public void setFracaoService(FracaoService fracaoService){
    _fracaoService = fracaoService;
  }

  public void setStorageService(StorageService storage){
    _storageService = storage;
  }
  /* Methods */
  public void inserirFracao(Fracao fracao) {
    _fracaoService.adicionarFracao(fracao);
    _nFracoes++;
    System.out.println("Fracao adicionada com successo.");
  }
  
  public void removerFracao(Fracao fracao) {
    _fracaoService.removerFracao(fracao);
    _nFracoes--;
    System.out.println("Fracao removida com successo.");
  }

  public void recalcularPercentagem() {
    double areaTotal = calcularAreaTotal();
    for (Fracao fracao : _fracaoService.getFracoes()) {
      double percentagem = (fracao.getArea() / areaTotal) * 100;
      fracao.setPercentagem(percentagem);
    }
    System.out.println("Percentagens recalculadas.");
  }

  public void listarFracao() {
    for (Fracao fracao : _fracaoService.getFracoes()) {
      System.out.println("===============================\n");
      fracao.apresentarDados();
      System.out.println("===============================\n");
    }
  }

  public boolean verificarPercentagemTotal() {
    double somaPercentagens = 0;
    for (Fracao fracao : _fracaoService.getFracoes()) {
      somaPercentagens += fracao.getPercentagem();
    }
    return Math.abs(somaPercentagens - 100) < 0.001; // tolerância para arredondamento
  }

  public double calcularAreaTotal() {
    double areaTotal = 0;
    for (Fracao fracao : _fracaoService.getFracoes()) {
      areaTotal += fracao.getArea();
    }
    return areaTotal;
  }

  public void calcularQuotas() {
    for (Fracao fracao : _fracaoService.getFracoes()) {
      double quota = fracao.calcularQuota(_despesaGeral, _despesaElevadores);
      System.out.println(String.format("FRACAO : %s, QUOTA : %.2f", fracao.getClass(), quota));
    }
  }
  public double calcularQuotasTotal() {
    double quotaTotal = 0;
    for (Fracao fracao : _fracaoService.getFracoes()) {
      quotaTotal += fracao.calcularQuota(_despesaGeral, _despesaElevadores);
    }
    return quotaTotal;
  }

  public void apresentarDados() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    System.out.println(String.format(
            "ID : %s\\n" + //
            "AREA(m2) : %.2f\\n" + //
            "DESPESAS GERAL : %.2f\\n" + //
            "DESPESAS ELEVADORES : %.2f\\n" + //
            "LOCALIZACAO : %s\\n" + //
            "FRACOES : %d\\n" + //
            "DATA DE FUNDACAO : %s",
        _id, calcularAreaTotal(), _despesaGeral, _despesaElevadores, _morada, _nFracoes,
        dateFormat.format(_dataConstrucao)));
  }

  @Override
  public String toString() {
    String obj = "Condominio{id='%s', morada='%s', despesaGeral='%.2f', despesaElevadores='%.2f', dataConstrucao='%s', nFracoes='%d'}";
    return String.format(obj, this._id, this._morada, this._despesaGeral, this._despesaElevadores, this._dataConstrucao,
        this._nFracoes);
  }

  @SuppressWarnings("unchecked")
  public Condominio CarregarDados() {
    try {

      ArrayList<String> data = _storageService.Carregar(path);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      String regex = "Condominio\\{id='([^']+)', morada='([^']+)', despesaGeral='([^']+)', despesaElevadores='([^']+)', dataConstrucao='([^']+)', nFracoes='([^']+)'\\}";
      Pattern pattern = Pattern.compile(regex);

      for (String register : data) {
        Matcher matcher = pattern.matcher(register);

        if (!matcher.find()) {
          throw new IllegalArgumentException("Erro ao tentar deserializar a string: " + register);
        }

        Condominio condominio = new Condominio(_fracaoService, _storageService);
        condominio.setId(matcher.group(1)); // id
        condominio.setMorada(matcher.group(2)); // Morada
        condominio.setDespesaGeral(Double.parseDouble(matcher.group(3))); // Despesas Geral
        condominio.setDespesaElevadores(Double.parseDouble(matcher.group(4))); // Despesas Elevadores
        condominio.setNFracoes(Integer.parseInt(matcher.group(5))); // id
        condominio.setDataConstrucao(dateFormat.parse(matcher.group(6))); // id
        return condominio;
      }
      return null;
    } catch (Exception e) {
      System.out.println("Falha ao carregar os dados do Condominio.");
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public void SalvarDados() {
    _storageService.Salvar(this, path);
  }
}
