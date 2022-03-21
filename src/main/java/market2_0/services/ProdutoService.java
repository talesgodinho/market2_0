package market2_0.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market2_0.model.dao.ProdutoDAO;
import market2_0.model.persistence.Categoria;
import market2_0.model.persistence.Produto;

public class ProdutoService {
	private final Logger LOG = LogManager.getLogger(ProdutoService.class);

	private EntityManager entityManager;

	private ProdutoDAO produtoDAO;

	public ProdutoService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.produtoDAO = new ProdutoDAO(entityManager);
	}

	private void getBeginTransaction() {
		this.LOG.info("Abrindo Transação com o banco de dados...");
		entityManager.getTransaction().begin();
	}

	private void commitAndCloseTransaction() {
		this.LOG.info("Commitando e Fechando transação com o banco de dados");
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void create(Produto produto) {
		this.LOG.info("Preparando para a Criação de um Produto");

		if (produto == null) {
			this.LOG.error("O Produto informado está nulo!");
			throw new RuntimeException("Product Null!");
		}
		String nomeCategoria = produto.getCategoria().getNome();

		this.LOG.info("Buscando se já existe a Categoria: " + nomeCategoria);
		Categoria categoria = this.findByCategoria(nomeCategoria);

		if (categoria != null) {
			this.LOG.info("Categoria " + nomeCategoria + " encontrada no banco.");
			produto.setCategoria(categoria);
		}

		try {
			getBeginTransaction();
			this.produtoDAO.create(produto);
			commitAndCloseTransaction();
		} catch (Exception e) {
			this.LOG.error("Erro ao criar um Produto, causado por: " + e.getMessage());
			throw new RuntimeException(e);
		}
		this.LOG.info("Produto foi criado com sucesso!");
	}

	public void delete(Long id) {
		this.LOG.info("Preparando para encontrar o Produto");
		if (id == null) {
			this.LOG.error("O ID do Produto informado está nulo!");
			throw new RuntimeException("The ID is Null");
		}

		Produto produto = this.produtoDAO.getById(id);
		validateProductIsNull(produto);

		this.LOG.info("Produto encontrado com sucesso!");

		getBeginTransaction();
		this.produtoDAO.delete(produto);
		commitAndCloseTransaction();
		this.LOG.info("Produto deletado com sucesso!");
	}

	public void update(Produto newProduct, Long productId) {
		this.LOG.info("Preparando para Atualizar o Produto");
		if (newProduct == null || productId == null) {
			this.LOG.error("Um dos parâmetros está nulo!");
			throw new RuntimeException("The parameter is null");
		}

		Produto product = this.produtoDAO.getById(productId);
		validateProductIsNull(product);

		this.LOG.info("Produto encontrado no banco!");

		getBeginTransaction();
		product.setNome(newProduct.getNome());
		product.setDescricao(newProduct.getDescricao());
		product.setPreco(newProduct.getPreco());
		product.setCategoria(this.findByCategoria(newProduct.getCategoria().getNome()));

		commitAndCloseTransaction();
		this.LOG.info("Produto atualizado com sucesso!");
	}

	public String getById(Long id) {
		this.LOG.info("Preparando para buscar o produto...");
		if (id == null) {
			this.LOG.error("O ID está nulo!");
			throw new RuntimeException("The parameter is null!");
		}
		
		getBeginTransaction();
		Produto produto = this.produtoDAO.getById(id);
		entityManager.close();
		if (produto == null) {
			this.LOG.error("Não foi encontrado o produto de id " + id);
			throw new EntityNotFoundException("Product not found!");
		}
		return produto.toString();
	}

	public List<Produto> listAll() {
		this.LOG.info("Preparando para listar os produtos");
		List<Produto> products = this.produtoDAO.listAll();

		if (products == null) {
			this.LOG.info("Não foram encontrados Produtos");
			return new ArrayList<Produto>();
		}
		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
	}

	public List<Produto> listByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.info("O parametro nome está vazio");
			throw new RuntimeException("The parameter name is null");
		}

		this.LOG.info("Preparando para buscar os produtos com o nome: " + name);
		List<Produto> products = this.produtoDAO.ListByName(name.toLowerCase());

		if (products == null) {
			this.LOG.info("Não foram encontrados Produtos");
			return new ArrayList<Produto>();
		}

		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
	}

	private void validateProductIsNull(Produto produto) {
		if (produto == null) {
			this.LOG.error("O Produto não Existe!");
			throw new EntityNotFoundException("Product not Found!");
		}
	}

	public Categoria findByCategoria(String nome) {
		if (nome == null || nome.isEmpty()) {
			this.LOG.error("O nome não pode ser Nulo!");
			throw new RuntimeException("The name is null!");
		}
		try {
			String sql = "SELECT * FROM Categoria WHERE nome =:nome";
			return (Categoria) this.entityManager.createNativeQuery(sql, Categoria.class)
					.setParameter("nome", nome.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			this.LOG.info("Não foi encontrado Categoria, será criada!");
			return null;
		}
	}

}