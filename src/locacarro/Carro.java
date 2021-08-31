package locacarro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Carro implements ValorDiaria {
	private String placa;
	private int ano;
	private String modelo;
	private String descricao;
	private double km;
	private boolean situacao;
	private double diaria;
	private double taxaDistancia;
	private String observacoes;

	public Carro(String placa, int ano, String modelo, String descricao, double km, boolean situacao, double diaria,
			double taxaDistancia, String observacoes) {
		this.placa = placa.toUpperCase();
		this.ano = ano;
		this.modelo = modelo;
		this.descricao = descricao;
		this.km = km;
		this.situacao = situacao;
		this.diaria = calculaValorDiaria(diaria);
		this.taxaDistancia = taxaDistancia;
		this.observacoes = observacoes;
	}

	private Carro(String linha) {
		Scanner linhaScanner = new Scanner(linha);
		linhaScanner.useDelimiter("!");

		this.placa = linhaScanner.next();
		this.ano = Integer.parseInt(linhaScanner.next());
		this.modelo = linhaScanner.next();
		this.descricao = linhaScanner.next();
		this.km = Double.parseDouble(linhaScanner.next());
		if (linhaScanner.next().equals("true")) {
			this.situacao = true;
		} else {
			this.situacao = false;
		}
		this.diaria = Double.parseDouble(linhaScanner.next());
		this.taxaDistancia = Double.parseDouble(linhaScanner.next());
		this.observacoes = linhaScanner.next();
	}

	@Override
	public double calculaValorDiaria(double diaria) {
		int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

		if (anoAtual - this.ano == 0) {
			diaria = diaria * 1.5;
		} else if (anoAtual - this.ano == 2 || anoAtual - this.ano == 1) {
			diaria = diaria * 1.2;
		}
		return diaria;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getKm() {
		return km;
	}

	public void setKm(double km) {
		this.km = km;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public double getDiaria() {
		return diaria;
	}

	public void setDiaria(double diaria) {
		this.diaria = calculaValorDiaria(diaria);
	}

	public double getTaxaDistancia() {
		return taxaDistancia;
	}

	public void setTaxaDistancia(double taxaDistancia) {
		this.taxaDistancia = taxaDistancia;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Override
	public String toString() {
		String str = ("Placa: " + this.placa + "\nAno: " + this.ano + "\nModelo: " + this.modelo + "\nDescrição: "
				+ this.descricao + "\nQuilometragem: " + this.km);
		if (this.situacao) {
			str += "\nSituação: Disponível";
		} else {
			str += "\nSituação: Não Disponível";
		}
		str += ("\nDiária: " + this.diaria + "\nTaxa Distância: " + this.getTaxaDistancia() + "\nObservações: "
				+ this.observacoes);

		return str;
	}

	public static Carro validaCarro(ArrayList<Carro> c, String placa) {
		for (Carro x : c) {
			if (placa.toUpperCase().equals(x.getPlaca())) {
				return x;
			}
		}
		return null;
	}

	public static void abreArquivo(ArrayList<Carro> c) throws IOException {
		String arqNome = "Carros.txt";
		File arquivo = new File(arqNome);

		if (arquivo.exists()) {
			BufferedReader leitor = new BufferedReader(new FileReader(arqNome));
			String linha;

			while ((linha = leitor.readLine()) != null) {
				c.add(new Carro(linha));
			}
			leitor.close();
		} else {
			System.out.println("Não há carros cadastrados...");
		}
	}

	public static void salvaArquivo(ArrayList<Carro> c) throws IOException {
		String arqNome = "Carros.txt";
		File arquivo = new File(arqNome);

		PrintWriter pw;
		if (arquivo.exists()) {
			pw = new PrintWriter(arquivo);
		} else {
			arquivo.createNewFile();
			pw = new PrintWriter(arquivo);
		}
		c.forEach(x -> {
			pw.println(x.getPlaca() + "!" + x.getAno() + "!" + x.getModelo() + "!" + x.getDescricao() + "!" + x.getKm()
					+ "!" + x.isSituacao() + "!" + x.getDiaria() + "!" + x.getTaxaDistancia() + "!"
					+ x.getObservacoes());
		});
		pw.close();
	}
}
