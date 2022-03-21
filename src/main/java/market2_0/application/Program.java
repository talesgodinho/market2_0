package market2_0.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import market2_0.connection.JpaConnectionFactory;
import market2_0.menus.Menus;
import market2_0.model.persistence.Categoria;
import market2_0.model.persistence.Cliente;
import market2_0.model.persistence.Produto;
import market2_0.services.ClienteService;
import market2_0.services.ProdutoService;

public class Program {

	public static void main(String[] args) {
		EntityManager entityManager = new JpaConnectionFactory().getEntityManager();

		executaMenus(entityManager);

	}

	private static void executaMenus(EntityManager em) {

		Scanner input = new Scanner(System.in);

		int table = Menus.optaTable();
		int actionCliente = 0;
		int actionProduto;

		if (table == 1) {

			ClienteService clienteService = new ClienteService(em);

			actionCliente = Menus.actionsClientes();

			switch (actionCliente) {

			case 1 -> {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

				System.out.print("Insira o nome do cliente: ");
				String nome = input.nextLine();

				System.out.print("Insira o CPF: ");
				String cpf = input.nextLine();

				System.out.print("Insira a data de nascimento ##-##-####: ");
				String dataNascimento = input.nextLine();
				LocalDate dataNascF = LocalDate.parse(dataNascimento, formatter);

				Cliente cliente = new Cliente(nome, cpf, dataNascF);

				clienteService.create(cliente);
				break;
			}

			case 2 -> {
				System.out.print("Insira o ID do cliente à ser deletado: ");
				Long id = Long.parseLong(input.nextLine());

				clienteService.delete(id);
				break;
			}

			case 3 -> {
				List<Cliente> clientes = clienteService.listAll();
				clientes.stream().forEach(p -> System.out.println(p));
				break;
			}

			case 4 -> {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

				System.out.print("Insira o ID do cliente que deseja atualizar: ");
				Long id = Long.parseLong(input.nextLine());

				System.out.print("Insira o nome do cliente: ");
				String nome = input.nextLine();

				System.out.print("Insira o CPF: ");
				String cpf = input.nextLine();

				System.out.print("Insira a data de nascimento ##-##-####: ");
				String dataNascimento = input.nextLine();
				LocalDate dataNascF = LocalDate.parse(dataNascimento, formatter);

				Cliente cliente = new Cliente(nome, cpf, dataNascF);

				clienteService.update(cliente, id);
				break;
			}

			case 5 -> {
				System.out.print("Insira o ID do cliente que deseja visualizar: ");
				Long id = Long.parseLong(input.nextLine());

				System.out.println(clienteService.getById(id));
				break;
			}
			case 6 -> {
				System.out.print("Insira um nome para buscar: ");
				String nome = input.nextLine();

				List<Cliente> clientes = clienteService.listByName(nome);
				clientes.stream().forEach(p -> System.out.println(p));
				break;
			}
			case 7 -> {
				executaMenus(em);
			}
			default -> {
				System.out.println("Opção inválida - Retornando ao menu inicial...");
				executaMenus(em);
			}
			}

		} else if (table == 2) {

			ProdutoService produtoService = new ProdutoService(em);

			actionProduto = Menus.actionsProdutos();

			switch (actionProduto) {

			case 1 -> {
				System.out.print("Insira o nome do produto: ");
				String nome = input.nextLine();

				System.out.print("Insira a descrição do produto: ");
				String descricao = input.nextLine();

				System.out.print("Insira o valor do produto: ");
				Double n = Double.parseDouble(input.nextLine());
				BigDecimal preco = new BigDecimal(n);

				System.out.print("Qual o nome da categoria: ");
				String categoria = input.nextLine();

				Produto produto = new Produto(nome, descricao, preco, new Categoria(categoria));

				produtoService.create(produto);
				break;
			}

			case 2 -> {
				System.out.print("Insira o ID do produto à ser deletado: ");
				Long id = Long.parseLong(input.nextLine());

				produtoService.delete(id);
				break;
			}

			case 3 -> {
				List<Produto> produtos = produtoService.listAll();
				produtos.stream().forEach(p -> System.out.println(p));
				break;
			}

			case 4 -> {
				System.out.print("Insira o ID do produto que deseja atualizar: ");
				Long id = Long.parseLong(input.nextLine());

				System.out.print("Insira o nome do produto: ");
				String nome = input.nextLine();

				System.out.print("Insira a descrição do produto: ");
				String descricao = input.nextLine();

				System.out.print("Insira o valor do produto: ");
				Double n = Double.parseDouble(input.nextLine());
				BigDecimal preco = new BigDecimal(n);

				System.out.print("Qual o nome da categoria: ");
				String categoria = input.nextLine();

				Produto produto = new Produto(nome, descricao, preco, new Categoria(categoria));

				produtoService.update(produto, id);
				break;
			}

			case 5 -> {
				System.out.print("Insira o ID do produto que deseja visualizar: ");
				Long id = Long.parseLong(input.nextLine());

				System.out.println(produtoService.getById(id));
				break;
			}
			case 6 -> {
				System.out.print("Insira um nome para buscar: ");
				String nome = input.nextLine();

				List<Produto> produtos = produtoService.listByName(nome);
				produtos.stream().forEach(p -> System.out.println(p));
				break;
			}
			case 7 -> {
				executaMenus(em);
			}
			default -> {
				System.out.println("Opção inválida - Retornando ao menu inicial...");
				executaMenus(em);
			}
			}

		} else {
			System.out.println("A aplicação está sendo encerrada...");
			System.exit(0);
		}

	}
}
