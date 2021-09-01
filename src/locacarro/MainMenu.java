package locacarro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
	private ArrayList<Cliente> clientes;
	private ArrayList<Carro> carros;
	private ArrayList<Aluguel> alugueis;

	public MainMenu() {
		this.clientes = new ArrayList<>();
		this.carros = new ArrayList<>();
		this.alugueis = new ArrayList<>();
	}

	private void abreArquivos(ArrayList<Cliente> cliente, ArrayList<Carro> carro, ArrayList<Aluguel> aluguel)
			throws IOException {
		Cliente.abreArquivo(cliente);
		Carro.abreArquivo(carro);
		Aluguel.abreArquivo(aluguel);
	}

	private void salvaArquivos(ArrayList<Cliente> cliente, ArrayList<Carro> carro, ArrayList<Aluguel> aluguel)
			throws IOException {
		Cliente.salvaArquivo(cliente);
		Carro.salvaArquivo(carro);
		Aluguel.salvaArquivo(aluguel);
	}

	public void menuPrincipal() throws Exception {
		this.abreArquivos(this.clientes, this.carros, this.alugueis);
		Scanner leitor = new Scanner(System.in);
		char escolha;

		do {
			this.limpaTela();
			System.out.println("[1] Cliente");
			System.out.println("[2] Carros");
			System.out.println("[3] Aluguel");
			System.out.println("[4] Relatórios");
			System.out.println("[5] SAIR");
			System.out.print("Escolha uma opção: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				this.menuCliente();
			} else if (escolha == '2') {
				this.menuCarro();
			} else if (escolha == '3') {
				this.menuAluguel();
			} else if (escolha == '4') {
				this.menuRelatorio();
			} else if (escolha == '5') {
				this.salvaArquivos(this.clientes, this.carros, this.alugueis);
			}
		} while (escolha != '5');

		System.out.println("\nArquivos salvos...");
		System.out.println("Programa encerrado...");
	}

	private void menuCliente() {
		Scanner leitor = new Scanner(System.in);
		char escolha;

		do {
			this.limpaTela();
			System.out.println("[1] Cadastrar Cliente");
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
				this.mostraRegistrosCliente(this.clientes);
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
				this.quitaDebitosCliente(this.clientes, this.alugueis);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			}
		} while (escolha != '6');
	}

	private void menuCarro() {
		Scanner leitor = new Scanner(System.in);
		char escolha;

		do {
			this.limpaTela();
			System.out.println("[1] Cadastrar Carro");
			System.out.println("[2] Lista de Carros");
			System.out.println("[3] Editar Cadastro de Carro");
			System.out.println("[4] Buscar Carro");
			System.out.println("[5] VOLTAR");
			System.out.print("Escolha uma opção: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				this.limpaTela();
				this.cadastraCarro(this.carros);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '2') {
				this.limpaTela();
				this.mostraRegistrosCarro(this.carros);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '3') {
				this.limpaTela();
				this.editaCadastroCarro(this.carros);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '4') {
				this.limpaTela();
				System.out.print("\nDigite a Placa do Carro: ");
				Carro carro = Carro.validaCarro(carros, leitor.nextLine());

				if (carro != null) {
					System.out.println(carro);
				} else {
					System.out.println("Carro não encontrado...");
				}

				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			}

		} while (escolha != '5');
	}

	private void menuAluguel() throws Exception {
		Scanner leitor = new Scanner(System.in);
		char escolha;

		do {
			this.limpaTela();
			System.out.println("[1] Alugar");
			System.out.println("[2] Devolver");
			System.out.println("[3] Lista de Aluguéis Ativos");
			System.out.println("[4] Histórico de Aluguéis");
			System.out.println("[5] VOLTAR");
			System.out.print("Escolha uma opção: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				this.limpaTela();
				if (this.carros.size() != 0) {
					Aluguel al = new Aluguel();
					al.alugar(this.clientes, this.carros, this.alugueis);
				} else {
					System.out.println("Não há carros disponíveis...");
				}
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '2') {
				this.limpaTela();
				Aluguel al = new Aluguel();
				System.out.print("Digite seu CPF ou CNPJ: ");
				String idCliente = leitor.nextLine();
				Cliente cl = Cliente.validaCliente(this.clientes, idCliente);

				if (cl != null) {
					System.out.print("Pagar? [1] Sim [2] Não: ");
					escolha = leitor.nextLine().charAt(0);

					if (escolha == '1') {
						al.devolucao(this.clientes, this.carros, this.alugueis, idCliente, true);
					} else {
						al.devolucao(this.clientes, this.carros, this.alugueis, idCliente, false);
					}
				} else {
					System.out.println("Digitou errado ou não está na base de dados...");
				}
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '3') {
				this.limpaTela();
				alugueis.forEach(x -> {
					if (x.isSituacao()) {
						System.out.println("------------------------------");
						System.out.println(x);
					}
				});
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '4') {
				this.limpaTela();
				alugueis.forEach(x -> {
					System.out.println("------------------------------");
					System.out.println(x);
				});
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			}

		} while (escolha != '5');
	}

	private void menuRelatorio() throws Exception {
		Scanner leitor = new Scanner(System.in);
		char escolha;

		do {
			this.limpaTela();
			System.out.println("[1] Carros Alugados por Período");//
			System.out.println("[2] Faturamento por Período");//
			System.out.println("[3] Clientes Devedores");
			System.out.println("[4] VOLTAR");
			System.out.print("Escolha uma opção: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				this.limpaTela();
				Aluguel.mostraCarrosAlugadosPorPeriodo(this.clientes, this.carros, this.alugueis);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '2') {
				this.limpaTela();
				Aluguel.mostraFaturamentoPorPeriodo(this.alugueis);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			} else if (escolha == '3') {
				this.limpaTela();
				this.mostraDevedores(this.clientes);
				System.out.println("\nAperte ENTER para continuar...");
				leitor.nextLine();
				this.limpaTela();
			}

		} while (escolha != '4');
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

	private void cadastraCarro(ArrayList<Carro> c) {
		Scanner leitor = new Scanner(System.in);
		System.out.print("Informe a Placa: ");
		String placa = leitor.nextLine();

		if (Carro.validaCarro(c, placa) == null) {
			System.out.print("Modelo: ");
			String modelo = leitor.nextLine();
			System.out.print("Descrição: ");
			String descricao = leitor.nextLine();
			System.out.print("Observações: ");
			String obs = leitor.nextLine();
			boolean situacao = true;
			System.out.print("Ano: ");
			int ano = leitor.nextInt();
			System.out.print("Quilometragem: ");
			double km = leitor.nextDouble();
			System.out.print("Diária: ");
			double diaria = leitor.nextDouble();
			System.out.print("Taxa Distância: ");
			double taxa = leitor.nextDouble();

			c.add(new Carro(placa, ano, modelo, descricao, km, situacao, diaria, taxa, obs));
		}
	}

	public void mostraRegistrosCliente(ArrayList<Cliente> c) {
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

	public void mostraRegistrosCarro(ArrayList<Carro> c) {
		c.forEach(x -> {
			System.out.println("------------------------------");
			System.out.println(x);
		});
	}

	public void mostraRegistrosAluguel(ArrayList<Aluguel> a) {
		a.forEach(x -> {
			System.out.println("------------------------------");
			System.out.println(x);
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

				if (escolha == '1') {
					System.out.println("Seu CPF atual: " + ((ClienteFisico) c).getCpf());
					System.out.print("Novo CPF: ");
					((ClienteFisico) c).setCpf(leitor.nextLine());
				}

				System.out.print("Editar seu nome? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);

				if (escolha == '1') {
					System.out.println("Seu nome atual: " + ((ClienteFisico) c).getNome());
					System.out.print("Novo nome: ");
					((ClienteFisico) c).setNome(leitor.nextLine());
				}

				this.editaCadastroClienteAux(c);

			} else {
				System.out.println("------------------------------");
				System.out.println(c);
				System.out.println("------------------------------");

				System.out.print("Editar o CNPJ? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);

				if (escolha == '1') {
					System.out.println("Seu CNPJ atual: " + ((ClienteJuridico) c).getCnpj());
					System.out.print("Novo CNPJ: ");
					((ClienteJuridico) c).setCnpj(leitor.nextLine());
				}

				System.out.print("Editar seu nome fantasia? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);

				if (escolha == '1') {
					System.out.println("Seu nome fantasia atual: " + ((ClienteJuridico) c).getNomeFantasia());
					System.out.print("Novo nome fantasia: ");
					((ClienteJuridico) c).setNomeFantasia(leitor.nextLine());
				}

				System.out.println("Editar a razão social? [1] Sim [2] Não: ");
				escolha = leitor.nextLine().charAt(0);

				if (escolha == '1') {
					System.out.println("Sua razão social atual: " + ((ClienteJuridico) c).getRazaoSocial());
					System.out.print("Nova razão social: ");
					((ClienteJuridico) c).setRazaoSocial(leitor.nextLine());
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

		if (escolha == '1') {
			System.out.println("Seu endereço atual: " + c.getEndereco());
			System.out.print("Novo endereço: ");
			c.setEndereco(leitor.nextLine());
		}

		System.out.print("Editar seu telefone? [1] Sim [2] Não: ");
		escolha = leitor.nextLine().charAt(0);

		if (escolha == '1') {
			System.out.println("Seu telefone atual: " + c.getTelefone());
			System.out.print("Novo telefone: ");
			c.setTelefone(leitor.nextLine());
		}

		System.out.print("Editar a dívida? [1] Sim [2] Não: ");
		escolha = leitor.nextLine().charAt(0);

		if (escolha == '1') {
			System.out.println("Sua dívida atual: R$" + c.getDivida());
			System.out.print("Nova dívida: ");
			c.setDivida(leitor.nextDouble());
		}
	}

	private void editaCadastroCarro(ArrayList<Carro> carro) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Digite a Placa do Carro: ");
		String placa = scan.nextLine();

		Carro c = Carro.validaCarro(carro, placa);

		if (c != null) {
			Scanner leitor = new Scanner(System.in);
			System.out.println("------------------------------");
			System.out.println(c);
			System.out.println("------------------------------");

			System.out.print("Editar a placa? [1] Sim [2] Não: ");
			char escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				System.out.println("Placa atual: " + c.getPlaca());
				System.out.print("Nova placa: ");
				c.setPlaca(leitor.nextLine());
			}

			System.out.print("Editar ano? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				System.out.println("Ano atual: " + c.getAno());
				System.out.print("Novo ano: ");
				c.setAno(leitor.nextInt());
			}

			System.out.print("Editar modelo? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				System.out.println("Modelo atual: " + c.getModelo());
				System.out.print("Novo modelo: ");
				c.setModelo(leitor.nextLine());
			}

			System.out.print("Editar descrição? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				System.out.println("Descrição atual: " + c.getDescricao());
				System.out.print("Nova descrição: ");
				c.setDescricao(leitor.nextLine());
			}

			System.out.print("Editar quilometragem? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				System.out.println("Quilometragem atual: " + c.getKm());
				System.out.print("Nova quilometragem: ");
				c.setKm(leitor.nextDouble());
			}

			System.out.print("Editar situação? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				if (c.isSituacao()) {
					System.out.println("Situacao atual: Disponível");
				} else {
					System.out.println("Situacao atual: Não Disponível");
				}

				System.out.print("Nova situação: [1] Disponível [2] Não Disponível");
				escolha = leitor.nextLine().charAt(0);

				if (escolha == '1') {
					c.setSituacao(true);
				} else {
					c.setSituacao(false);
				}
			}

			System.out.print("Editar diaria? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				System.out.println("Diária atual: " + c.getDiaria());
				System.out.print("Nova diária: ");
				c.setDiaria(leitor.nextDouble());
			}

			System.out.print("Editar observações? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				System.out.println("Observações atuais: " + c.getObservacoes());
				System.out.print("Nova observação: ");
				c.setObservacoes(leitor.nextLine());
			}
		} else {
			System.out.println("Carro não encontrado...");
		}
	}

	private void quitaDebitosCliente(ArrayList<Cliente> cliente, ArrayList<Aluguel> aluguel) {
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
			System.out.print("Pagar dívida? [1] Sim [2] Não: ");
			escolha = leitor.nextLine().charAt(0);

			if (escolha == '1') {
				aluguel.forEach(al -> {
					if (al.getIdCliente().equals(idCliente) && al.isSituacao()) {
						System.out.println("------------------------------");
						System.out.println(al);
					}
				});

				System.out.print("\nEscolha a placa para quitar a dívida: ");
				String placa = leitor.nextLine();

				double divida, d, auxDivida;

				for (Aluguel al : aluguel) {
					if (placa.toUpperCase().equals(al.getIdCarro()) && al.isSituacao()) {
						divida = al.getDividaValor();
						double totalPagar;
						
						do {
							System.out.println("Dívida atual: R$ " + divida);
							System.out.print("Pagamento (À vista): R$ ");
							totalPagar = leitor.nextDouble();
	
							d = divida;
							
						} while (totalPagar < d);
							
						divida -= totalPagar;

						auxDivida = c.getDivida();

						if (divida <= 0) {
							System.out.println("Troco: R$ " + -divida);
							c.setDivida(auxDivida-d);
							System.out.println("Dívida totalmente paga...");
							al.setSituacao(false);
						}
						
						emitirRecibo(c, d);
					}
				}
			}
		} else if (c == null) {
			System.out.println("Cliente não encontrado...");
		} else {
			System.out.println("Cliente sem dívidas...");
		}
	}

	private void mostraDevedores(ArrayList<Cliente> cl) {
		int xxx = 0;
		for (Cliente x : cl) {
			if (x.getDivida() > 0.0) {
				System.out.println("------------------------------");
				System.out.println(x);
				xxx = 1;
			}
		}
		if (xxx == 1) {
			System.out.println("------------------------------");
		} else {
			System.out.println("Não há clientes com dívidas...");
		}
	}

	private void emitirRecibo(Cliente c, double d) {
		System.out.println("\n---------- Recibo ------------\n");
		if (c instanceof ClienteFisico) {
			System.out.println("Cliente: " + ((ClienteFisico) c).getNome());
		} else {
			System.out.println("Cliente: " + ((ClienteJuridico) c).getRazaoSocial());
		}
		System.out.println("\nValor: R$ " + d);
		System.out.println("------------------------------");
	}

	private void limpaTela() {
		for (int count = 0; count < 1; count++)
			System.out.println("");
	}
}
