package market2_0.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import market2_0.model.persistence.Produto;

public class ProdutoDAO {

	private EntityManager entityManager;

	public ProdutoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Produto produto) {
		this.entityManager.persist(produto);
	}

	public void delete(Produto produto) {
		this.entityManager.remove(convertToMerge(produto));
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listAll() {
		String sql = "SELECT * FROM Produto";
		return this.entityManager.createNativeQuery(sql, Produto.class).getResultList();
	}

	public Produto update(Produto produto) {
		return convertToMerge(produto);
	}

	public Produto getById(int id) {
		return this.entityManager.find(Produto.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Produto> ListByName(String nome) {
		String sql = "SELECT * FROM Produto WHERE nome LIKE :nome";
		return this.entityManager.createNativeQuery(sql, Produto.class)
				.setParameter("nome", ("%" + nome.toLowerCase() + "%"))
				.getResultList();
	}

	private Produto convertToMerge(Produto produto) {
		return this.entityManager.merge(produto);
	}

}
