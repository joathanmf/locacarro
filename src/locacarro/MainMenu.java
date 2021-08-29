package locacarro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
	private ArrayList<Cliente> clientes;

	public MainMenu() {
		this.clientes = new ArrayList<>();
	}

	private void abreArquivos(ArrayList<Cliente> c) throws IOException {
		Cliente.abreArquivo(c);
	}

	private void salvaArquivos(ArrayList<Cliente> c) throws IOException {
		Cliente.salvaArquivo(c);
	}

	public void menuPrincipal() throws IOException {
		this.abreArquivos(this.clientes);
		Scanner leitor = new Scanner(System.in);
		char escolha;

		do {
			System.out.println("[1] Cliente");
			System.out.println("[5] SAIR");
			System.out.print("Escolha uma opção: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				this.menuCliente();
			} else if (escolha == '5') {
				this.salvaArquivos(this.clientes);
			}
		} while (escolha != '5');

		leitor.close();
		System.out.println("Arquivos salvos...");
		System.out.println("Programa encerrado...");
	}

	private void menuCliente() {
		Scanner leitor = new Scanner(System.in);
		char escolha;

		do {
			this.limpaTela();
			System.out.println("[1] Cadastro");
			System.out.println("[2] Lista de Clientes");
			System.out.println("[3] Editar Cadastro do Cliente");
			System.out.println("[4] Buscar Cliente");
			System.out.println("[5] Pagar Débito");
			System.out.println("[6] VOLTAR");
			System.out.print("Escolha uma opção: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				this.limpaTela();
				this.cadastraCliente(this.clientes);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '2') {
				this.limpaTela();
				this.mostraRegistros(this.clientes);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '3') {
				this.limpaTela();
				this.editaCadastroCliente(this.clientes);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '4') {
				this.limpaTela();
				System.out.println("\nDigite seu CPF ou CNPJ: ");
				Cliente cliente = Cliente.validaCliente(clientes, leitor.nextLine());
				
				if (cliente != null) {
					System.out.println(cliente);
				} else {
					System.out.println("Cliente não encontrado...");
				}
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '5') {
				this.limpaTela();
				this.quitaDebitosCliente(this.clientes);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			}
		} while (escolha != '6');
	}

	private void cadastraCliente(ArrayList<Cliente> c) {
		System.out.print("Digite seu CPF ou CNPJ: ");
		Scanner leitor = new Scanner(System.in);
		String idCliente = leitor.nextLine();

		if (Cliente.validaCliente(c, idCliente) == null) {
			char escolha;

			do {
				System.out.println("[1] Cliente Físico");
				System.out.println("[2] Cliente Jurídico");
				System.out.print("Escolha uma opção: ");
				escolha = leitor.nextLine().charAt(0);

			} while ((escolha != '1') && (escolha != '2'));

			if (escolha == '1') {
				System.out.print("Nome: ");
				String nome = leitor.nextLine();
				System.out.print("Endereço: ");
				String endereco = leitor.nextLine();
				System.out.print("Telefone: ");
				String telefone = leitor.nextLine();

				c.add(new ClienteFisico(nome, idCliente, endereco, telefone, 0.0));
			} else {
				System.out.print("Razão Social: ");
				String razaoSocial = leitor.nextLine();
				System.out.print("Nome Fantasia: ");
				String nomeFantasia = leitor.nextLine();
				System.out.print("Endereço: ");
				String endereco = leitor.nextLine();
				System.out.print("Telefone: ");
				String telefone = leitor.nextLine();

				c.add(new ClienteJuridico(nomeFantasia, idCliente, razaoSocial, endereco, telefone, 0.0));
			}
		} else {
			System.out.println("Cliente já está cadastrado...");
		}
	}

	private void mostraRegistros(ArrayList<Cliente> c) {
		c.forEach(x -> {
			System.out.println("------------------------------");
			System.out.println("Endereço: " + x.getEndereco());
			System.out.println("Telefone: " + x.getTelefone());
			System.out.println("Dívida: " + x.getDivida());
			System.out.println("Data de Cadastro: " + x.getDataCadastro());

			if (x instanceof ClienteFisico) {
				System.out.println("CPF: " + ((ClienteFisico) x).getCpf());
				System.out.println("Nome: " + ((ClienteFisico) x).getNome());
			} else {
				System.out.println("CNPJ: " + ((ClienteJuridico) x).getCnpj());
				System.out.println("Nome Fantasia: " + ((ClienteJuridico) x).getNomeFantasia());
				System.out.println("Razão Social: " + ((ClienteJuridico) x).getRazaoSocial());
			}
		});
	}

	private void editaCadastroCliente(ArrayList<Cliente> cliente) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Digite seu CPF ou CNPJ: ");
		String idCliente = scan.nextLine();

		Cliente c = Cliente.validaCliente(cliente, idCliente);

		if (c != null) {
			char escolha;
			Scanner leitor = new Scanner(System.in);

			if (c instanceof ClienteFisico) {
				System.out.println("------------------------------");
				System.out.println(c);
				System.out.println("------------------------------");

				System.out.print("Editar o CPF? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);
				this.limpaTela();

				if (escolha == '1') {
					System.out.println("Seu CPF atual: " + ((ClienteFisico) c).getCpf());
					System.out.println("Novo CPF: ");
					((ClienteFisico) c).setCpf(leitor.nextLine());
					this.limpaTela();
				}

				System.out.println("Editar seu nome? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);
				this.limpaTela();

				if (escolha == '1') {
					System.out.println("Seu nome atual: " + ((ClienteFisico) c).getNome());
					System.out.println("Novo nome: ");
					((ClienteFisico) c).setNome(leitor.nextLine());
					this.limpaTela();
				}

				this.editaCadastroClienteAux(c);

			} else {
				System.out.println("------------------------------");
				System.out.println(c);
				System.out.println("------------------------------");

				System.out.print("Editar o CNPJ? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);
				this.limpaTela();

				if (escolha == '1') {
					System.out.println("Seu CNPJ atual: " + ((ClienteJuridico) c).getCnpj());
					System.out.println("Novo CNPJ: ");
					((ClienteJuridico) c).setCnpj(leitor.nextLine());
					this.limpaTela();
				}

				System.out.println("Editar seu nome fantasia? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);
				this.limpaTela();

				if (escolha == '1') {
					System.out.println("Seu nome fantasia atual: " + ((ClienteJuridico) c).getNomeFantasia());
					System.out.println("Novo nome fantasia: ");
					((ClienteJuridico) c).setNomeFantasia(leitor.nextLine());
					this.limpaTela();
				}

				System.out.println("Editar a razão social? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);
				this.limpaTela();

				if (escolha == '1') {
					System.out.println("Sua razão social atual: " + ((ClienteJuridico) c).getRazaoSocial());
					System.out.println("Nova razão social: ");
					((ClienteJuridico) c).setRazaoSocial(leitor.nextLine());
					this.limpaTela();
				}

				this.editaCadastroClienteAux(c);

			}
		} else {
			System.out.println("Cliente não encontrado...");
		}
	}

	private void editaCadastroClienteAux(Cliente c) {
		Scanner leitor = new Scanner(System.in);

		System.out.print("Editar seu endereço? [1] Sim [2] Não: ");
		char escolha = leitor.nextLine().charAt(0);
		this.limpaTela();

		if (escolha == '1') {
			System.out.println("Seu endereço atual: " + c.getEndereco());
			System.out.print("Novo endereço: ");
			c.setEndereco(leitor.nextLine());
			this.limpaTela();
		}

		System.out.print("Editar seu telefone? [1] Sim [2] Não: ");
		escolha = leitor.nextLine().charAt(0);
		this.limpaTela();

		if (escolha == '1') {
			System.out.println("Seu telefone atual: " + c.getTelefone());
			System.out.print("Novo telefone: ");
			c.setTelefone(leitor.nextLine());
			this.limpaTela();
		}

		System.out.print("Editar a dívida? [1] Sim [2] Não: ");
		escolha = leitor.nextLine().charAt(0);
		this.limpaTela();

		if (escolha == '1') {
			System.out.println("Sua dívida atual: R$" + c.getDivida());
			System.out.print("Nova dívida: ");
			c.setDivida(leitor.nextDouble());
			this.limpaTela();
		}
	}
	
	private void quitaDebitosCliente(ArrayList<Cliente> cliente) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Digite seu CPF ou CNPJ: ");
		String idCliente = scan.nextLine();

		Cliente c = (Cliente) ClienteFisico.validaCliente(cliente, idCliente);
		
		if (c == null) {
			c = (Cliente) ClienteJuridico.validaCliente(cliente, idCliente);
		}
		
		if ((c != null) && (c.getDivida() > 0.0)) {
			System.out.println("------------------------------");
			System.out.println(c);
			System.out.println("------------------------------");
			
			char escolha;
			Scanner leitor = new Scanner(System.in);
			System.out.println("Pagar dívida? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);
							
			if (escolha == '1') {
				double divida = c.getDivida();
				System.out.println("Dívida atual: " + divida);
				System.out.print("Total a pagar: ");
				double totalPagar = leitor.nextDouble();
				
				divida -= totalPagar;
				
				if (divida <= 0) {
					System.out.println("Troco: R$ " + -divida);
					c.setDivida(0.0);
				} else {
					c.setDivida(divida);
				}
			}
		} else if (c == null) {
			System.out.println("Cliente não encontrado...");
		} else {
			System.out.println("Cliente sem dívidas...");
		}
	}

	private void limpaTela() {
		for (int count = 0; count < 10; count++)
			System.out.println("");
	}
}
