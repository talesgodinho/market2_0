package market2_0.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import market2_0.model.persistence.Cliente;

public class ClienteDAO {

	private EntityManager entityManager;

	public ClienteDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Cliente cliente) {
		this.entityManager.persist(cliente);
	}

	public void delete(Cliente cliente) {
		this.entityManager.remove(convertToMerge(cliente));
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listAll() {
		String sql = "SELECT * FROM Cliente";
		return this.entityManager.createNativeQuery(sql, Cliente.class).getResultList();
	}

	public Cliente update(Cliente cliente) {
		return convertToMerge(cliente);
	}

	public Cliente getById(Long id) {
		return this.entityManager.find(Cliente.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> ListByName(String nome) {
		String sql = "SELECT * FROM Cliente WHERE nome LIKE :nome";
		return this.entityManager.createNativeQuery(sql, Cliente.class)
				.setParameter("nome", ("%" + nome.toLowerCase() + "%"))
				.getResultList();
	}

	private Cliente convertToMerge(Cliente cliente) {
		return this.entityManager.merge(cliente);
	}
}
