package market2_0.menus;

import java.util.Scanner;

public class Menus {

	Scanner input = new Scanner(System.in);

	public static int optaTable() {
		System.out.println("*———————————————————————————————————————————*");
		System.out.println("|             Esolha uma opção:             |");
		System.out.println("*———————————————————————————————————————————*");
		System.out.println("| 1 - Acessar Clientes                      |");
		System.out.println("| 2 - Acessar Produtos                      |");
		System.out.println("| 3 - SAIR                                  |");
		System.out.println("—————————————————————————————————————————————");
		return verificaOp(3);
	}

	public static int actionsClientes() {
		System.out.println("*———————————————————————————————————————————*");
		System.out.println("|             Esolha uma opção:             |");
		System.out.println("*———————————————————————————————————————————*");
		System.out.println("| 1 - Cadastrar novo cliente                |");
		System.out.println("| 2 - Deletar um cliente                    |");
		System.out.println("| 3 - Listar todos os clientes              |");
		System.out.println("| 4 - Atualizar clientes (update)           |");
		System.out.println("| 5 - Buscar cliente por ID                 |");
		System.out.println("| 6 - Listar clientes por nome              |");
		System.out.println("| 7 - VOLTAR                                |");
		System.out.println("—————————————————————————————————————————————");
		return verificaOp(7);
	}

	public static int actionsProdutos() {
		System.out.println("*———————————————————————————————————————————*");
		System.out.println("|             Esolha uma opção:             |");
		System.out.println("*———————————————————————————————————————————*");
		System.out.println("| 1 - Cadastrar novo produto                |");
		System.out.println("| 2 - Deletar um produto                    |");
		System.out.println("| 3 - Listar todos os produtos              |");
		System.out.println("| 4 - Atualizar produtos (update)           |");
		System.out.println("| 5 - Buscar produto por ID                 |");
		System.out.println("| 6 - Listar produtos por nome              |");
		System.out.println("| 7 - VOLTAR                                |");
		System.out.println("—————————————————————————————————————————————");
		return verificaOp(7);
	}

	public static int verificaOp(int n) {

		// Implementar tratamento
		Scanner input = new Scanner(System.in);

		System.out.print("Digite o número correspondente à sua opção: ");
		int op = Integer.parseInt(input.nextLine());

		while (op < 1 || op > n) {
			System.out.print("Opção inválida! Digite o número correspondente à sua opção: ");
			op = Integer.parseInt(input.nextLine());
		}
		return op;
	}
}
