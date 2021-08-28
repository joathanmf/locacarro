package locacarro;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		ArrayList<Cliente> clientes = new ArrayList<>();
		
		String nome = "Joathan";
		String cpf = "0123456789";
		String endereco = "Rua da Rosa";
		String telefone = "9876543210";
		double divida = 0.0;
		
		ClienteFisico cf = new ClienteFisico(nome, cpf, endereco, telefone, divida);
		
		clientes.add(cf);
		
		Cliente.salvaArquivo(clientes);

	}
}
