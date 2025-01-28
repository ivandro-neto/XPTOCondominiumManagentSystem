/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemadegestaodecondominio.application;

import java.util.Scanner;

import com.sistemadegestaodecondominio.model.Fracao;
import com.sistemadegestaodecondominio.service.FracaoService;
import com.sistemadegestaodecondominio.service.IStorage;
import com.sistemadegestaodecondominio.service.ProprietarioService;
import com.sistemadegestaodecondominio.service.StorageService;
import com.sistemadegestaodecondominio.service.UI;

/**
 *
 * @author .me
 */
public class SistemaDeGestaoApplication {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
    StorageService storage = new StorageService<>("presistence");
    FracaoService fracaoService = new FracaoService(storage);
    ProprietarioService proprietarioService = new ProprietarioService();
    proprietarioService.CarregarDados();
    fracaoService.CarregarDados();
    Fracao farc = fracaoService.getFracaoPorId("c1347ac9-59e3-4f7e-8d70-f73ce9ca4948");
    System.out.println(farc.getLocalizacao());
    fracaoService.SalvarDados();
    scanner.close();
  }
}
