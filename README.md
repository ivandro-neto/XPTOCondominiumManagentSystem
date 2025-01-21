# XPTOCondominiumManagentSystem

# Condomínio XPTO - Lista de Tarefas e Distribuição

## Introdução
Este documento descreve todas as tarefas necessárias para o desenvolvimento do software de gestão do Condomínio XPTO, bem como a distribuição entre dois programadores.

---

## Tarefas

### **1. Estrutura Inicial do Projeto**
- Configurar o ambiente de desenvolvimento Java.
- Criar a estrutura de pacotes e arquivos.
- Definir o modelo UML do sistema.

### **2. Modelagem de Classes**
#### Condomínio
- Implementar a classe Condomínio com os seguintes atributos:
  - Identificador
  - Morada
  - Total de despesas gerais
  - Total de despesas com elevadores
  - Data de construção
  - Número de frações
- Criar métodos:
  - Inserir e retirar fração
  - Calcular soma de percentagens
  - Verificar equilíbrio entre receitas e despesas

#### Frações
- Criar classe base "Fracao" com atributos:
  - Identificador
  - Área
  - Percentagem sobre a área total
  - Localização
- Implementar métodos:
  - Getters e setters
  - Calcular quota mensal

#### Tipos de Fração
- Criar classes derivadas de "Fracao":
  - **Apartamento**
    - Atributos: Tipo (T0-T5), número de casas de banho, número de varandas, terraço.
  - **Loja**
    - Atributos: Diferenciar cálculo de despesas (sem elevador).
  - **Garagem**
    - Atributos: Capacidade de viaturas, serviço de lavagem.
  - **Arrecadação**
    - Atributos: Porta blindada.

### **3. Persistência de Dados**
- Implementar leitura e gravação de dados em arquivos.
- Garantir que dados sejam carregados na inicialização.
- Criar método para salvar os dados ao encerrar o programa.

### **4. Interface com o Usuário**
- Criar menu em modo texto para executar as seguintes ações:
  - Inserir/editar/remover frações.
  - Exibir lista de frações.
  - Consultar despesas e receitas.
  - Calcular quotas mensais.
- Tornar o menu intuitivo e legível.

### **5. Testes e Validação**
- Testar:
  - Cálculos de percentagens e quotas.
  - Inserção e remoção de frações.
  - Persistência de dados.
- Corrigir erros encontrados.

### **6. Documentação Final**
- Escrever relatório com:
  - Resumo dos algoritmos implementados.
  - Organização dos arquivos e pacotes.
  - Justificativas técnicas.

---

## Distribuição de Tarefas

### **Josué**
1. Estrutura Inicial do Projeto
2. Modelagem da classe **Condomínio**
3. Implementação da classe base **Fracao**
4. Persistência de Dados

### **Ivandro Neto**
1. Modelagem das classes derivadas de **Fracao**:
   - Apartamento
   - Loja
   - Garagem
   - Arrecadação
2. Implementação da Interface com o Usuário
3. Testes e Validação
4. Documentação Final

---
Se decidires trocar as tarefas altere os nomes que depois virei verificar.

## Observações
- Ambos os programadores devem colaborar na definição do UML e revisão de código.
- O progresso de cada tarefa deve ser registrado em commits regulares no repositório.

**May The Code Be With You!**


Bom jovem, eu perdi o meu telemóvel...
No entanto, poderás entrar em contacto comigo por e-mail ou Skype por agora.
https://join.skype.com/invite/GSk6sWmopyyZ

ivandro.neto@outlook.com
ivandroneto1@outlook.com
ou
20210429@isptec.co.ao
