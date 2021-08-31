package locacarro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Aluguel {
	private double dividaValor;
	private String idCliente;
	private String idCarro;
	private boolean tipoAluguel; // true > diaria | false > km
	private boolean situacao;
	private String dataInicio;
	private String dataFim;

	public Aluguel() {
		this.dividaValor = 0.0;
		this.idCliente = "";
		this.idCarro = "";
		this.tipoAluguel = false;
		this.situacao = true;
		this.dataInicio = "";
		this.dataFim = "";
	}

	private Aluguel(String idCliente, String idCarro, boolean tipoAluguel) {
		this.dividaValor = 0.0;
		this.idCliente = idCliente;
		this.idCarro = idCarro;
		this.tipoAluguel = tipoAluguel;
		this.situacao = true;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataAtual = new Date();
		this.dataInicio = sdf.format(dataAtual);
		this.dataFim = "";
	}

	private Aluguel(String linha) {
		Scanner linhaScanner = new Scanner(linha);
		linhaScanner.useDelimiter("!");

		this.idCliente = linhaScanner.next();
		this.idCarro = linhaScanner.next();
		if (linhaScanner.next().equals("true")) {
			this.tipoAluguel = true;
		} else {
			this.tipoAluguel = false;
		}
		if (linhaScanner.next().equals("true")) {
			this.situacao = true;
		} else {
			this.situacao = false;
		}
		this.dataInicio = linhaScanner.next();
		this.dataFim = linhaScanner.next();
	}

	public double getDividaValor() {
		return dividaValor;
	}

	public void setDividaValor(double dividaValor) {
		this.dividaValor = dividaValor;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdCarro() {
		return idCarro;
	}

	public void setIdCarro(String idCarro) {
		this.idCarro = idCarro;
	}

	public boolean isTipoAluguel() {
		return tipoAluguel;
	}

	public void setTipoAluguel(boolean tipoAluguel) {
		this.tipoAluguel = tipoAluguel;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public void alugar(ArrayList<Cliente> cliente, ArrayList<Carro> carro, ArrayList<Aluguel> aluguel) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Digite seu CPF ou CNPJ: ");
		String idCliente = scan.nextLine();

		MainMenu menu = new MainMenu();
		menu.mostraRegistrosCarro(carro);

		System.out.print("\nDigite a placa do carro para alugar: ");
		String placaCarro = scan.nextLine();

		Cliente cl = Cliente.validaCliente(cliente, idCliente);
		Carro ca = Carro.validaCarro(carro, placaCarro);

		if (cl.getDivida() == 0 && cl != null) {
			if (ca.isSituacao()) {
				Scanner leitor = new Scanner(System.in);
				System.out.print("Por distância ou diária? [1] Distância (Km) [2] Diária: ");
				char escolha = leitor.nextLine().charAt(0);
				ca.setSituacao(false);

				// true > diaria | false > km
				if (escolha == '1') {
					aluguel.add(new Aluguel(idCliente, placaCarro.toUpperCase(), false));
				} else if (escolha == '2') {
					aluguel.add(new Aluguel(idCliente, placaCarro.toUpperCase(), true));
				}
			} else {
				System.out.println("Carro não disponível...");
			}
		} else if (cl == null) {
			System.out.println("Cliente não cadastrado...");
		} else {
			System.out.println("Cliente está devendo: R$ " + cl.getDivida());
		}
	}

	public void devolucao(ArrayList<Cliente> cliente, ArrayList<Carro> carro, ArrayList<Aluguel> aluguel,
			String idCliente, boolean vaiPagar) throws Exception {
		Cliente cl = Cliente.validaCliente(cliente, idCliente);
		Carro ca;
		Scanner leitor = new Scanner(System.in);

		System.out.print("Quilometragem atual do carro: ");
		double kmsAtual = leitor.nextDouble();

		if (cl != null) {
			System.out.println(this.idCliente);
			aluguel.forEach(x -> {
				if (idCliente.equals(this.idCliente)) {
					System.out.println("------------------------------");
					System.out.println(aluguel);
					System.out.println("");
				}
			});

			leitor.nextLine();
			System.out.print("Placa do carro à devolver: ");
			String placa = leitor.nextLine();
			ca = Carro.validaCarro(carro, placa.toUpperCase());

			if (ca != null) {
				double divida = 0.0;
				double kms = 0.0;

				// true > diaria | false > km
				for (Aluguel x : aluguel) {
					if (placa.equals(this.idCarro) && this.situacao == true) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date agora = new Date();
						this.dataFim = sdf.format(agora);

						if (x.isTipoAluguel()) {
							divida = ca.getDiaria() * this.diasEntreDatas();
						} else {
							kms = kmsAtual - ca.getKm();
							divida = kms * ca.getTaxaDistancia();
						}

						this.dividaValor = divida;
						System.out.println("Dívida atual: " + divida);

						if (vaiPagar) {
							System.out.print("Total a pagar: ");
							double totalPagar = leitor.nextDouble();

							divida -= totalPagar;

							if (divida <= 0) {
								System.out.println("Troco: R$ " + -divida);
								cl.setDivida(0.0);
								System.out.println("Dívida totalmente paga...");
							} else {
								cl.setDivida(divida);
							}
						} else {
							cl.setDivida(divida);
						}
					}
				}
			} else {
				System.out.println("\nPlaca errada...");
			}
		} else {
			System.out.println("Cliente não existe...");
		}
	}

	public int diasEntreDatas() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dInicio = Calendar.getInstance();
		Calendar dFim = Calendar.getInstance();

		dInicio.setTime(sdf.parse(this.dataInicio));
		dFim.setTime(sdf.parse(this.dataFim));

		int x = dFim.get(Calendar.DAY_OF_YEAR) - dInicio.get(Calendar.DAY_OF_YEAR);

		if (x == 0) {
			x = 1;
		}

		return x;
	}

	@Override
	public String toString() {
		String str = ("IdCliente: " + this.idCliente + "\nIdCarro (Placa): " + this.idCarro
				+ "\nData Inicial do Aluguel: " + this.dataInicio + "\nTipo de Aluguel: ");
		if (this.tipoAluguel) {
			str += "Diária";
		} else {
			str += "Distância";
		}

		return str;
	}

	public static void abreArquivo(ArrayList<Aluguel> a) throws IOException {
		String arqNome = "Aluguel.txt";
		File arquivo = new File(arqNome);

		if (arquivo.exists()) {
			BufferedReader leitor = new BufferedReader(new FileReader(arqNome));
			String linha;

			while ((linha = leitor.readLine()) != null) {
				a.add(new Aluguel(linha));
			}
			leitor.close();
		} else {
			System.out.println("Não há alugueis...");
		}
	}

	public static void salvaArquivo(ArrayList<Aluguel> a) throws IOException {
		String arqNome = "Aluguel.txt";
		File arquivo = new File(arqNome);

		PrintWriter pw;
		if (arquivo.exists()) {
			pw = new PrintWriter(arquivo);
		} else {
			arquivo.createNewFile();
			pw = new PrintWriter(arquivo);
		}
		a.forEach(x -> {
			pw.println(x.idCliente + "!" + x.idCarro + "!" + x.tipoAluguel + "!" + x.situacao + "!" + x.dataInicio + "!"
					+ x.dataFim + "!" + x.dividaValor + "!");
		});
		pw.close();
	}
}
