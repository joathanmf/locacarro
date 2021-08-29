package locacarro;

import java.util.Scanner;

public class ClienteFisico extends Cliente {
	private String nome;
	private String cpf;

	public ClienteFisico(String nome, String cpf, String endereco, String telefone, double divida) {
		super(endereco, telefone, divida);
		this.nome = nome;
		this.cpf = cpf;
	}

	public ClienteFisico(String nome, String cpf, String endereco, String telefone, double divida, String data) {
		super(endereco, telefone, divida, data);
		this.nome = nome;
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return ("Nome: " + this.nome + "\nCPF: " + this.cpf + "\nEndereco: " + this.getEndereco() + "\nTelefone: "
				+ this.getTelefone() + "\nDívida: " + this.getDivida() + "\nData de Cadastro: "
				+ this.getDataCadastro());
	}

	public static Cliente auxLinha(String linha) {
		Scanner linhaScanner = new Scanner(linha);
		linhaScanner.useDelimiter("!");

		String f = linhaScanner.next();
		String cpf = linhaScanner.next();
		String nome = linhaScanner.next();
		String endereco = linhaScanner.next();
		String telefone = linhaScanner.next();
		double divida = Double.parseDouble(linhaScanner.next());
		String data = linhaScanner.next();

		linhaScanner.close();

		return (Cliente) new ClienteFisico(nome, cpf, endereco, telefone, divida, data);
	}

	public static String getStringToSave(ClienteFisico c) {
		return ("f!" + c.cpf + "!" + c.nome + "!" + c.getEndereco() + "!" + c.getTelefone() + "!" + c.getDivida() + "!"
				+ c.getDataCadastro() + "!");
	}
}
