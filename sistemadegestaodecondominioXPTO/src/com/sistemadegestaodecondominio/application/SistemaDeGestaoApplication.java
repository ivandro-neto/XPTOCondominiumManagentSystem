/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemadegestaodecondominio.application;

import java.util.Scanner;

import com.sistemadegestaodecondominio.model.Condominio;
import com.sistemadegestaodecondominio.service.FracaoService;
import com.sistemadegestaodecondominio.service.ProprietarioService;
import com.sistemadegestaodecondominio.service.StorageService;
import com.sistemadegestaodecondominio.service.UI;

/**
 *
 * @author .me
 */
public class SistemaDeGestaoApplication {

  public static void main(String[] args) {
    @SuppressWarnings("rawtypes")
    StorageService storage = new StorageService<>("presistence");
    Scanner scanner = new Scanner(System.in);

    ProprietarioService proprietarioService = new ProprietarioService(storage);
    FracaoService fracaoService = new FracaoService(storage, proprietarioService);
    Condominio condominio = new Condominio();
    
    condominio = condominio.CarregarDados();
    proprietarioService.carregarDados();
    fracaoService.carregarDados();
    
    
    UI applicaUi = new UI(condominio, fracaoService, proprietarioService, scanner);
    
    if(!storage.pathExiste("Condominio.txt")){
        storage.removerFile("Proprietario.txt");
        storage.removerFile("Fracao.txt");

        condominio = applicaUi.telaCriarCondominio();
    }
    condominio.setFracaoService(fracaoService);
    condominio.setStorageService(storage);
    
    applicaUi.setCondominio(condominio);

    applicaUi.iniciarAplicacao();

    fracaoService = applicaUi.getFracaoService();
    proprietarioService = applicaUi.getProprietarioService();
    condominio = applicaUi.getCondomino();
    
    fracaoService.salvarDados();
    proprietarioService.salvarDados();
    condominio.SalvarDados();
    scanner.close();
  }

  
}
