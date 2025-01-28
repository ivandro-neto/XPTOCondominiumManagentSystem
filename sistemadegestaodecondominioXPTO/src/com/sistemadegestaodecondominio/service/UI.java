package com.sistemadegestaodecondominio.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.sistemadegestaodecondominio.model.Apartamento;
import com.sistemadegestaodecondominio.model.Arrecadacao;
import com.sistemadegestaodecondominio.model.Fracao;
import com.sistemadegestaodecondominio.model.Garagem;
import com.sistemadegestaodecondominio.model.Proprietario;

/**
 *
 * @author Ivandro Neto
 */
public class UI {
  private FracaoService _fracaoService;
  private ProprietarioService _proprietarioService;
  private Scanner _Scanner;
  public UI(FracaoService fracaoService, ProprietarioService proprietarioService, Scanner scanner) {
    _fracaoService = fracaoService;
    _proprietarioService = proprietarioService;
    _Scanner = scanner;
  }

  public int HomeScreen(ArrayList<String> options) {
    var input = new Scanner(System.in);
    System.out.println("Gerenciador de Condominios XPTO");

    for (String option : options) {
      System.out.println(option);
    }
    System.out.print("Escolha: ");
    int choice = input.nextInt();
    input.close();
    return choice;
  }

  public void RegistrarFracaoScreen() {
    var input = _Scanner;
    System.out.println("Registrar Fracao: ");
    System.out.println("Qual e o tipo de fracao que pretende criar?");
    Fracao fracao = _fracaoService.criarFracao(input.nextLine());
    System.out.print("Infrome a area: ");
    fracao.setArea(input.nextDouble());
    input.nextLine();
    System.out.print("Infrome a localizacao: ");
    fracao.setLocalizacao(input.nextLine());
    System.out.print("Infrome o nome do proprietario: ");
    fracao.setProprietario(_proprietarioService.getProprietarioPeloNome(input.nextLine()));

    if (fracao instanceof Garagem) {
      Garagem garagem = (Garagem) fracao;
      System.out.print("Infome a capacidade de ligeiros: ");
      garagem.setNCapacidadeLigeiros(input.nextInt());
      input.nextLine();
      System.out.println("Tem serviços de Lavagem?");
      garagem.setTemLavagem(input.nextBoolean());
      input.nextLine();
    } else if (fracao instanceof Arrecadacao) {
      Arrecadacao arrecadacao = (Arrecadacao) fracao;
      System.out.println("Tem Porta Blindada?");
      arrecadacao.setTemPortaBlindada(input.nextBoolean());
      input.nextLine();

    } else if (fracao instanceof Apartamento) {
      Apartamento apartamento = (Apartamento) fracao;
      System.out.println("Qual é a tipologia do apartamento?");
      apartamento.setTipo(input.nextLine());
      System.out.println("Informe o número de casas de banho:");
      apartamento.setNCasasDeBanho(input.nextInt());
      input.nextLine();
      System.out.println("Informe o número de varandas:");
      apartamento.setNCasasDeBanho(input.nextInt());
      input.nextLine();
      System.out.println("Possui um terraço?");
      apartamento.setTemTerraco(input.nextBoolean());
      input.nextLine();
    }
    _fracaoService.adicionarFracao(fracao);
    
  }

  public void RegistrarClienteScreen() {
    var input = _Scanner;
    Proprietario proprietario = new Proprietario();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    System.out.println("Registrar Cliente: ");
    System.out.println("Qual e o nome do cliente?");
    proprietario.setNome(input.nextLine());
    System.out.print("Infrome a Data de nascimeto: (dd/MM/yyyy)");
    try {
      proprietario.setDataNascimento(dateFormat.parse(input.nextLine()));
    } catch (ParseException e) {
      input.close();
      throw new IllegalArgumentException("Erro ao tentar converter para data. Verifique o formato (dd/MM/yyyy).", e);
    }
    System.out.print("Infrome o e-mail: ");
    proprietario.setEmail(input.nextLine());
    System.out.print("Infrome o numero de telefone: ");
    proprietario.setTelefone(input.nextLine());
    System.out.print("Infrome o numero de telemovel: (Opcional)");
    proprietario.setTelemovel(input.nextLine());

    _proprietarioService.adicionarProprietario(proprietario);
    
  }

}
