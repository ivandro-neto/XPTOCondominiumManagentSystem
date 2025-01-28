/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemadegestaodecondominio.application;

import java.util.ArrayList;
import java.util.Scanner;

import com.sistemadegestaodecondominio.model.Condominio;
import com.sistemadegestaodecondominio.model.Fracao;
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
    Condominio condominio = new Condominio(fracaoService, storage);
    
    UI applicaUi = new UI(condominio, fracaoService, proprietarioService, scanner);

    proprietarioService.CarregarDados();
    fracaoService.CarregarDados();
    condominio.CarregarDados();
    if(!storage.pathExiste("Condominio.txt")){
        storage.deleteFile("Proprietario.txt");
        storage.deleteFile("Fracao.txt");

        condominio = applicaUi.telaCriarCondominio();
    }
  
    applicaUi.iniciarAplicacao();


    fracaoService.SalvarDados();
    proprietarioService.SalvarDados();
    condominio.SalvarDados();
    scanner.close();
  }

  
}
