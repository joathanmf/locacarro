package locacarro;

import java.util.Scanner;

public class ClienteJuridico extends Cliente {

	private String nomeFantasia;
	private String cnpj;
	private String razaoSocial;

	public ClienteJuridico(String nomeFantasia, String cnpj, String razaoSocial, String endereco, String telefone,
			double divida) {
		super(endereco, telefone, divida);
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
	}

	public ClienteJuridico(String nomeFantasia, String cnpj, String razaoSocial, String endereco, String telefone,
			double divida, String data) {
		super(endereco, telefone, divida, data);
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public static Cliente auxLinha(String linha) {
		Scanner linhaScanner = new Scanner(linha);
		linhaScanner.useDelimiter("!");

		String cnpj = linhaScanner.next();
		String nomeFantasia = linhaScanner.next();
		String razaoSocial = linhaScanner.next();
		String endereco = linhaScanner.next();
		String telefone = linhaScanner.next();
		double divida = Double.parseDouble(linhaScanner.next());
		String data = linhaScanner.next();

		linhaScanner.close();

		return (Cliente) new ClienteJuridico(nomeFantasia, cnpj, razaoSocial, endereco, telefone, divida, data);
	}

	public static String getStringToSave(ClienteJuridico c) {
		return ("j!" + c.cnpj + "!" + c.nomeFantasia + "!" + c.razaoSocial + "!" + c.getEndereco() + "!"
				+ c.getTelefone() + "!" + c.getDivida() + "!" + c.getDataCadastro() + "!");
	}

}
