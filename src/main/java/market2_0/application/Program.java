package market2_0.application;

import java.util.List;

import javax.persistence.EntityManager;

import market2_0.connection.JpaConnectionFactory;
import market2_0.model.dao.ProdutoDAO;
import market2_0.model.persistence.Produto;

public class Program {

	public static void main(String[] args) {
		EntityManager entityManager = new JpaConnectionFactory().getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);

//		Produto produto = new Produto("Notebook", "Thinkpad T430", 
//				new BigDecimal(3599.99), new Categoria("Informática"));

		entityManager.getTransaction().begin();

		List<Produto> produtos = produtoDAO.ListByName("book");
		produtos.stream().forEach(p -> System.out.println(p));

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
