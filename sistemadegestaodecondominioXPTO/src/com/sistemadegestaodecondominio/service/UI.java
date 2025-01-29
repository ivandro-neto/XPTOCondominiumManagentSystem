package com.sistemadegestaodecondominio.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.sistemadegestaodecondominio.exceptions.messages.error.SistemaErrorMensagem;
import com.sistemadegestaodecondominio.model.Apartamento;
import com.sistemadegestaodecondominio.model.Arrecadacao;
import com.sistemadegestaodecondominio.model.Condominio;
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
  private Condominio _condominio;

  public UI(Condominio condominio, FracaoService fracaoService, ProprietarioService proprietarioService,
      Scanner scanner) {
    _fracaoService = fracaoService;
    _proprietarioService = proprietarioService;
    _condominio = condominio;
    _Scanner = scanner;
  }
  
  public FracaoService getFracaoService(){
      return _fracaoService;
  }
  public ProprietarioService getProprietarioService(){
      return _proprietarioService;
  }
  public Condominio getCondomino(){
      return _condominio;
  }
  
  public void setCondominio(Condominio condominio){
      _condominio = condominio;
  }
  
  public void setFracaoService(FracaoService fracaoService){
      _fracaoService = fracaoService;
  }
  
  public void setProprietarioService(ProprietarioService proprietarioService){
      _proprietarioService = proprietarioService;
  }

  public int exibirMenuPrincipal() {
    String[] mainMenu = {
        "=== MENU PRINCIPAL ===",
        "1 - Gerenciar Frações",
        "2 - Gerenciar Condomínio",
        "3 - Gerenciar Despesas",
        "4 - Apagar Dados da Memoria",
        "5 - Sair"
    };
    Scanner input = _Scanner;
    System.out.println("Gerenciador de Condominios XPTO");
    exibirOpcoes(mainMenu);
    int option = input.nextInt();
    input.nextLine();
    return option;
  }

  public int exibirMenuFracoes() {
    String[] fracoesMenu = {
        "=== GERENCIAR FRAÇÕES ===",
        "1 - Inserir Fração",
        "2 - Remover Fração",
        "3 - Listar Frações",
        "4 - Recalcular Percentagens",
        "5 - Verificar Soma de Percentagens",
        "6 - Voltar ao Menu Principal"
    };
    Scanner input = _Scanner;
    exibirOpcoes(fracoesMenu);
    int option = input.nextInt();
    input.nextLine();
    return option;
  }

  public int exibirMenuCondominio() {
    String[] condominioMenu = {
        "=== GERENCIAR CONDOMÍNIO ===",
        "1 - Alterar Morada",
        "2 - Alterar Despesa Geral",
        "3 - Alterar Despesa com Elevadores",
        "4 - Ver Informações do Condomínio",
        "5 - Registrar Cliente",
        "6 - Listar Clientes",
        "7 - Voltar ao Menu Principal"
    };
    Scanner input = _Scanner;
    exibirOpcoes(condominioMenu);
    int option = input.nextInt();
    input.nextLine();
    return option;
  }

  public int exibirMenuDespesas() {
    String[] despesasMenu = {
        "=== GERENCIAR DESPESAS ===",
        "1 - Calcular Quotas das Frações",
        "2 - Relatorio de Rendimento",
        "3 - Voltar ao Menu Principal"
    };
    Scanner input = _Scanner;
    exibirOpcoes(despesasMenu);
    int option = input.nextInt();
    input.nextLine();
    return option;
  }

  public void exibirOpcoes(String[] options) {
    for (String option : options) {
      System.out.println(option);
    }
    System.out.print("Escolha: ");
  }

  public void navegarMenu(Supplier<Integer> menuExibir, int opcaoVoltar, Consumer<Integer> funcionalidades) {

    boolean continuarMenu = true;
    while (continuarMenu) {
      int opcao = menuExibir.get(); // Exibe o menu e pega a opção selecionada

      if (opcao == opcaoVoltar) {
        continuarMenu = false; // Volta ao menu principal
      } else {
        funcionalidades.accept(opcao); // Chama a funcionalidade correspondente
      }
    }
  }

  public void telaRegistrarCliente() {
    try {
      var input = _Scanner;
      Proprietario proprietario = new Proprietario();
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      dateFormat.setLenient(false);
      System.out.println("Registrar Cliente: ");
      System.out.println("Qual e o nome do cliente?");
      proprietario.setNome(input.nextLine());
      System.out.print("Infrome a Data de nascimeto: (dd/MM/yyyy)");
      proprietario.setDataNascimento(dateFormat.parse(input.nextLine()));
      String email = input.nextLine();
      while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
        System.out.println("Formato de e-mail inválido. Tente novamente.");
        email = input.nextLine();
      }
      proprietario.setEmail(email);
      System.out.print("Infrome a morada: ");
      proprietario.setMorada(input.nextLine());
      System.out.print("Infrome o numero de telefone: ");
      proprietario.setTelefone(input.nextLine());
      System.out.print("Infrome o numero de telemovel: (Opcional)");
      proprietario.setTelemovel(input.nextLine());

      _proprietarioService.adicionarProprietario(proprietario);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Erro ao tentar converter para data. Verifique o formato (dd/MM/yyyy).", e);
    }

  }

  public void telaRegistrarFracao() {
    var input = _Scanner;
    System.out.println("Registrar Fracao: ");
    System.out.println("Qual e o tipo de fracao que pretende criar?");
    Fracao fracao = _fracaoService.criarFracao(input.nextLine());

    // Captura de dados e confirmação após cada entrada
    System.out.print("Infrome a area: ");
    while (!input.hasNextDouble()) {
      System.out.println("Por favor, insira um valor válido para a área.");
      input.nextLine(); // Limpa a linha inválida
    }
    fracao.setArea(input.nextDouble());
    input.nextLine(); // Limpa o newline restante

    System.out.print("Infrome a localizacao: ");
    fracao.setLocalizacao(input.nextLine());

    System.out.print("Infrome o nome do proprietario: ");
    var prop = _proprietarioService.getProprietarioPeloNome(input.nextLine());
    while(prop == null){
      System.out.println("Erro ao encontrar o cliente. Verifique o nome inserido.");
      prop = _proprietarioService.getProprietarioPeloNome(input.nextLine());
    }
    fracao.setProprietario(prop);

    // Processamento de Garagem
    if (fracao instanceof Garagem) {
      Garagem garagem = (Garagem) fracao;
      System.out.print("Infome a capacidade de ligeiros: ");
      while (!input.hasNextInt()) {
        System.out.println("Por favor, insira um valor válido para a capacidade.");
        input.nextLine();
      }
      garagem.setNCapacidadeLigeiros(input.nextInt());
      input.nextLine(); // Limpa o newline
      System.out.println("Tem serviços de Lavagem?(S-Sim | N-Nao)");
      String resposta = input.nextLine();
      garagem.setTemLavagem(resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("Sim"));
    }
    // Processamento de Arrecadacao
    else if (fracao instanceof Arrecadacao) {
      Arrecadacao arrecadacao = (Arrecadacao) fracao;
      System.out.println("Tem Porta Blindada?(S-Sim | N-Nao)");
      String resposta = input.nextLine();
      arrecadacao.setTemPortaBlindada(resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("Sim"));
    }
    // Processamento de Apartamento
    else if (fracao instanceof Apartamento) {
      Apartamento apartamento = (Apartamento) fracao;
      System.out.println("Qual é a tipologia do apartamento?");
      apartamento.setTipo(input.nextLine());
      System.out.println("Informe o número de casas de banho:");
      while (!input.hasNextInt()) {
        System.out.println("Por favor, insira um número válido de casas de banho.");
        input.nextLine();
      }
      apartamento.setNCasasDeBanho(input.nextInt());
      input.nextLine(); // Limpa o newline
      System.out.println("Informe o número de varandas:");
      while (!input.hasNextInt()) {
        System.out.println("Por favor, insira um número válido de varandas.");
        input.nextLine();
      }
      apartamento.setNCasasDeBanho(input.nextInt());
      input.nextLine(); // Limpa o newline
      System.out.println("Possui um terraço?(S-Sim | N-Nao)");
      String resposta = input.nextLine();
      apartamento.setTemTerraco( resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("Sim"));
    }
    _condominio.inserirFracao(fracao);
  }

  public void telaRemoverFracao() {
    Scanner input = _Scanner;
    System.out.println("Remover Fracao: ");
    System.out.println("Insira o ID da fracao que pretende remover: ");
    String id = input.nextLine();
    _condominio.removerFracao(_fracaoService.getFracaoPorId(id));
  }

  public void telaListarFracoes() {
    System.out.println("Listar Fracoes:");
    _condominio.listarFracao();
  }

  public void telaRecalcularPercentagens() {
    System.out.println("Recalcular percentagens:");
    _condominio.recalcularPercentagem();
  }

  public void telaVerificarPercentagemTotal() {
    System.out.println("Verificar percentagens:");
    if (_condominio.verificarPercentagemTotal()) {
      System.out.println("Muito bem! Nao existem flutuacoes nas percentagens.");
    }
    System.out.println("Hmmm! Precisamos recalcular as percentagens.");
  }

  public void gerenciarFracoes(int opcao) {
    switch (opcao) {
      case 1:
        telaRegistrarFracao();
        break;
      case 2:
        telaRemoverFracao();
        break;
      case 3:
        telaListarFracoes();
        break;
      case 4:
        telaRecalcularPercentagens();
        break;
      case 5:
        telaVerificarPercentagemTotal();
        break;
      default:
        break;
    }
  }

  public void telaAtualizarMorada() {
    Scanner input = _Scanner;
    System.out.println("Atualizar morada:");
    _condominio.setMorada(input.nextLine());
  }

  public void telaAtualizarDespesasGerais() {
    Scanner input = _Scanner;
    System.out.println("Atualizar despesas gerais:");
    _condominio.setDespesaGeral(input.nextDouble());
  }

  public void telaAtualizarDespesasElevadores() {
    Scanner input = _Scanner;
    System.out.println("Atualizar despesas de elevadores:");
    _condominio.setDespesaElevadores(input.nextDouble());
  }

  public void telaApresentarInformacoesCondominio() {
    System.out.println("Informacoes gerais do condominio:");
    _condominio.apresentarDados();
  }

  public void telaListarClientes() {
    System.out.println("Listar clientes:");
    _proprietarioService.listarProprietarios();
  }

  public Condominio telaCriarCondominio() {
    Scanner input = _Scanner;
    System.out.println("Criar Condomínio: ");
    Condominio condominio = new Condominio();
    // Entrada para morada
    System.out.print("Informe a morada do condomínio: ");
    String morada = input.nextLine();
    while (morada.isEmpty()) {
        System.out.println("A morada não pode estar vazia. Tente novamente.");
        morada = input.nextLine();
    }
    condominio.setMorada(morada);

    // Entrada para despesas gerais
    System.out.print("Informe a despesa geral do condomínio (AOA): ");
    while (!input.hasNextDouble()) {
        System.out.println("Por favor, insira um valor válido para a despesa geral.");
        input.nextLine(); // Limpa a entrada inválida
    }
    double despesaGeral = input.nextDouble();
    condominio.setDespesaGeral(despesaGeral);
    input.nextLine(); // Limpa o newline restante

    // Entrada para despesas com elevadores
    System.out.print("Informe a despesa com elevadores do condomínio (AOA): ");
    while (!input.hasNextDouble()) {
        System.out.println("Por favor, insira um valor válido para a despesa com elevadores.");
        input.nextLine(); // Limpa a entrada inválida
    }
    double despesaElevadores = input.nextDouble();
    condominio.setDespesaElevadores(despesaElevadores);
    input.nextLine(); // Limpa o newline restante

    // Entrada para data de construção
    System.out.print("Informe a data de construção (dd/MM/yyyy): ");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    Date dataConstrucao = null;
    while (dataConstrucao == null) {
        try {
            String dataStr = input.nextLine();
            dataConstrucao = dateFormat.parse(dataStr);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Tente novamente.");
        }
    }
    condominio.setDataConstrucao(dataConstrucao);
    // Após a coleta de todos os dados, você pode criar o condomínio
    
    System.out.println("Condomínio criado com sucesso!");
    return condominio;    
}
  // Funções de gerenciamento de condomínio

  public void gerenciarCondominio(int opcao) {
    switch (opcao) {
      case 1:
        telaAtualizarMorada();
        break;
      case 2:
        telaAtualizarDespesasGerais();
        break;
      case 3:
        telaAtualizarDespesasElevadores();
        break;
      case 4:
        telaApresentarInformacoesCondominio();
        break;
      case 5:
        telaRegistrarCliente();
        break;
      case 6:
        telaListarClientes();
        break;
      default:
        break;
    }
  }

  public void telaCalcularQuotasFracoes() {
    System.out.println("Caluclar quotas das fracoes:");
    _condominio.calcularQuotas();
  }

  public void telaListarQuotasMensaisAnuais() {
    System.out.println("Relatorio de rendimento:");
    double quotaTotal = _condominio.calcularQuotasTotal();
    System.out.println(String.format("QUOTA TOTAL MENSAL (AOA) : %.2f\n QUOTA TOTAL ANUAL (AOA) : %.2f\n", quotaTotal,
        quotaTotal * 12));
  }

  // Funções de gerenciamento de despesas
  public void gerenciarDespesas(int opcao) {
    switch (opcao) {
      case 1:
        telaCalcularQuotasFracoes();
        break;
      case 2:
        telaListarQuotasMensaisAnuais();
        break;
      default:
        break;
    }
  }

@SuppressWarnings("rawtypes")
public void telaAvisoAcaoArriscada(){
  Scanner input = _Scanner;
  System.out.println("Deseja realmente deletar todos os dados em Memoria?");
  System.out.println("Nota : (* Acao inreversivel)");
  System.out.println("Pretende avancar?(S-Sim | N-Nao)");
  String resposta = input.nextLine();
  if(resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("Sim")){
    StorageService storageService = new StorageService<>("presistence");
    System.out.println(storageService.removerFile("Condominio.txt") ? SistemaErrorMensagem.SUCESSO_APAGAR_FICHEIRO : SistemaErrorMensagem.ERRO_APAGAR_FICHEIRO);
    System.out.println(storageService.removerFile("Fracao.txt") ? SistemaErrorMensagem.SUCESSO_APAGAR_FICHEIRO : SistemaErrorMensagem.ERRO_APAGAR_FICHEIRO);
    System.out.println(storageService.removerFile("Proprietario.txt") ? SistemaErrorMensagem.SUCESSO_APAGAR_FICHEIRO : SistemaErrorMensagem.ERRO_APAGAR_FICHEIRO);
    _fracaoService.limpar();
    _proprietarioService.limpar();
    _condominio = telaCriarCondominio();
    _condominio.setFracaoService(_fracaoService);
    
  }
  iniciarAplicacao();
}

  public boolean iniciarAplicacao() {
    boolean continuar = true;

    while (continuar) {
      int opcaoMenuPrincipal = exibirMenuPrincipal();

      switch (opcaoMenuPrincipal) {
        case 1:
          navegarMenu(this::exibirMenuFracoes, 6, this::gerenciarFracoes);
          break;
        case 2:
          navegarMenu(this::exibirMenuCondominio, 7, this::gerenciarCondominio);
          break;
        case 3:
          navegarMenu(this::exibirMenuDespesas, 3, this::gerenciarDespesas);
          break;
        case 4:
          telaAvisoAcaoArriscada();
          break;
        case 5:
          continuar = false; // Encerra o programa
          break;
        default:
          break;
      }
    }
    return continuar;
  }
}
