package locacarro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Cliente {
	private String endereco;
	private String telefone;
	private double divida;
	private String dataCadastro;

	public Cliente(String endereco, String telefone, double divida) {
		this.endereco = endereco;
		this.telefone = telefone;
		this.divida = divida;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date agora = new Date();
		this.dataCadastro = sdf.format(agora);
	}

	public Cliente(String endereco, String telefone, double divida, String data) {
		this.endereco = endereco;
		this.telefone = telefone;
		this.divida = divida;
		this.dataCadastro = data;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public double getDivida() {
		return divida;
	}

	public void setDivida(double divida) {
		this.divida = divida;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public static Cliente validaCliente(ArrayList<Cliente> c, String idCliente) {
		for(Cliente x : c) {
			if (x instanceof ClienteFisico) {
				if (idCliente.equals(((ClienteFisico) x).getCpf())) {
					return x;
				}
			} else {
				if (idCliente.equals(((ClienteJuridico) x).getCnpj())) {
					return x;
				}
			}
		}
		return null;
	}

	public static void abreArquivo(ArrayList<Cliente> c) throws IOException {
		String arqNome = "Clientes.txt";
		File arquivo = new File(arqNome);

		if (arquivo.exists()) {
			BufferedReader leitor = new BufferedReader(new FileReader(arqNome));
			String linha;

			while ((linha = leitor.readLine()) != null) {
				if (linha.charAt(0) == 'f') {
					c.add(ClienteFisico.auxLinha(linha));
				} else if (linha.charAt(0) == 'j') {
					c.add(ClienteJuridico.auxLinha(linha));
				}
			}
			leitor.close();
		} else {
			System.out.println("Não há clientes cadastrados...");
		}
	}

	public static void salvaArquivo(ArrayList<Cliente> c) throws IOException {
		String arqNome = "Clientes.txt";
		File arquivo = new File(arqNome);

		PrintWriter pw;
		if (arquivo.exists()) {
			pw = new PrintWriter(arquivo);
		} else {
			arquivo.createNewFile();
			pw = new PrintWriter(arquivo);
		}
		c.forEach(x -> {
			if (x instanceof ClienteFisico) {
				pw.println(ClienteFisico.getStringToSave((ClienteFisico) x));
			} else {
				pw.println(ClienteJuridico.getStringToSave((ClienteJuridico) x));
			}
		});
		pw.close();
	}

}
