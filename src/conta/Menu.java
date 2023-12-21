package conta;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;

import conta.controller.ContaController;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;
import conta.util.Cores;
import java.util.Scanner;

public class Menu {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);	
		
		ContaController contas = new ContaController();
		
				
		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		String titular;
		float saldo, limite, valor;	
		
		//Criando contas
		ContaCorrente cc1 = new ContaCorrente(contas.gerarNumero(), 123, 1, "João da Silva", 1000f, 100.0f);
		contas.cadastrar(cc1);
		
		ContaCorrente cc2 = new ContaCorrente(contas.gerarNumero(), 124, 1, "Maria da Silva", 2000f, 100.0f);
		contas.cadastrar(cc2);
		
		ContaPoupanca cp1 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Mariana dos Santos", 4000f, 12);
		contas.cadastrar(cp1);
		
		ContaPoupanca cp2 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Juliana Ramos", 8000f, 15);
		contas.cadastrar(cp2);
		
		contas.listarTodas();
		
		
		while (true) {
			System.out.println(Cores.TEXT_YELLOW + Cores.ANSI_BLACK_BACKGROUND
							 + "*******************************************************");
			System.out.println("                                                       ");
			System.out.println("                 BANCO DO BRAZIL COM Z                 ");
			System.out.println("                                                       ");
			System.out.println("*******************************************************");
			System.out.println("                                                       ");
			System.out.println("                 1 - Criar Conta                       ");
			System.out.println("                 2 - Listar Todas as Contas            ");
			System.out.println("                 3 - Buscar Conta por Número           ");
			System.out.println("                 4 - Atualizar Dados da Conta          ");
			System.out.println("                 5 - Apagar Conta                      ");
			System.out.println("                 6 - Sacar                             ");
			System.out.println("                 7 - Deposiar                          ");
			System.out.println("                 8 - Transferir Valores Entre Contas   ");
			System.out.println("                 9 - Sair                              ");
			System.out.println("                                                       ");
			System.out.println("*******************************************************");
			System.out.println("Entre com a opção desejada:                            ");
			System.out.println("                                                       " + Cores.TEXT_RESET);
			
			try {
			opcao = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\nDigite valores inteiros!");
				sc.nextLine();
				opcao = 0;
			}
			
			if (opcao == 9) {
				System.out.println(Cores.TEXT_WHITE_BOLD + "\nBanco do Brazil com Z - O seu futuro começa aqui! ");
				sobre();
				sc.close();
				System.exit(0);
			}
			switch (opcao) {
			case 1:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Criar conta\n\n");
				
				System.out.print("Digite o número da agência: ");
				agencia = sc.nextInt();
				System.out.print("\nDigite o nome do titular: ");
				sc.skip("\\R?");
				titular = sc.nextLine();
				
				do {
					System.out.print("\nDigite o tipo da conta (1- CC ou 2- CP): ");
					tipo = sc.nextInt();
					} while (tipo < 1 && tipo > 2);
				
				System.out.print("\nDigite o saldo da conta (R$): ");
				saldo = sc.nextFloat();
				
				switch (tipo) {
					case 1 -> {
						System.out.print("\nDigite o limite de crédito (R$): ");
						limite = sc.nextFloat();
						contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
					}
					case 2 -> {
						System.out.print("\nDigite o aniversário da conta: ");
						aniversario = sc.nextInt();
						contas.cadastrar(new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
					}
				}				
				
				keyPress();
				break;
			case 2:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Listar todas as contas\n\n");
				contas.listarTodas();
				keyPress();
				
				break;
			case 3:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Consultar dados da conta - por número\n\n");
				
				System.out.print("Digite o número da conta: ");
				numero = sc.nextInt();
				
				contas.procurarPorNumero(numero);
				
				keyPress();
				
				break;
			case 4:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Atualizar dados da Conta\n\n");
				
				System.out.println("Digite o número da conta: ");
				numero = sc.nextInt();
				
				var buscaConta = contas.buscarNaCollection(numero);
				
				if (buscaConta != null) {
					
					tipo = buscaConta.getTipo();
					
					System.out.print("\nDigite o número da agência: ");
					agencia = sc.nextInt();
					System.out.print("\nDigite o nome do titular: ");
					sc.skip("\\R?");
					titular = sc.nextLine();
					
					System.out.print("\nDigite o saldo da conta (R$): ");
					saldo = sc.nextFloat();
					
					switch (tipo) {
						case 1 -> {
							System.out.print("\nDigite o limite de crédito (R$): ");
							limite = sc.nextFloat();
							
							contas.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
						}
						case 2 -> {
							System.out.print("\nDigite o dia do aniversário da conta: ");
							aniversario = sc.nextInt();
							
							contas.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
						}
						default -> {
							System.out.print("\nTipo de conta inválido");
						}
					}
				}
				else {
					System.out.println("A conta não foi encontrada!");
				}
				
				keyPress();				
				break;
			case 5:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Apagar a Conta\n\n");
				
				System.out.print("\nDigite o número da conta: ");
				numero = sc.nextInt();
				
				contas.deletar(numero);
				
				keyPress();				
				break;
			case 6:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Saque\n\n");
				
				System.out.print("\nDigite o número da conta: ");
				numero = sc.nextInt();
				
				do {
					System.out.print("\nDigite o valor do saque (R$): ");
					valor = sc.nextFloat();
				} while (valor <= 0);
				
				contas.sacar(numero, valor);
				
				keyPress();				
				break;
			case 7:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Depósito\n\n");
				
				System.out.print("\nDigite o número da conta: ");
				numero = sc.nextInt();
				
				do {
					System.out.print("\nDigite o valor do depósito (R$): ");
					valor = sc.nextFloat();
				} while (valor <= 0);
				
				contas.depositar(numero, valor);
				
				keyPress();				
				break;
			case 8:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Transferência entre Contas\n\n");
				
				System.out.println("Digite o numero da conta de origem: ");
				numero = sc.nextInt();
				System.out.println("Digite o numero da conta de destino: ");
				numeroDestino = sc.nextInt();
				
				do {
					System.out.println("Digite o valor da transferência (R$): ");
					valor = sc.nextFloat();
				} while (valor <= 0);
				
				contas.transferir(numero, numeroDestino, valor);
				
				keyPress();				
				break;
			default:
				System.out.println(Cores.TEXT_RED_BOLD + "\nOpção Inválida!\n");
				System.out.println(Cores.TEXT_RESET);
				
				keyPress();				
				break;
			
			}
		}
	}

	private static void sobre() {
		System.out.println("\n*********************************************************");
		System.out.println("Projeto Desenvolvido por: Anderson Silva");
		System.out.println("Generation Brasil - generation@generation.org");
		System.out.println("https://github.com/andersoncaarlos/contabancaria");
		System.out.println("***********************************************************");
		
	}
	
	public static void keyPress() {
		try {
			
			System.out.println(Cores.TEXT_RESET + "\n\nPressione Enter para Continuar...");
			System.in.read();
			
		} catch (IOException e) {
			
			System.out.println("Você pressionou uma tecla diferente de enter!");
		}
	}

}
