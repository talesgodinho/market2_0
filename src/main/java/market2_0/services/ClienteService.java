package market2_0.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market2_0.model.dao.ClienteDAO;
import market2_0.model.persistence.Cliente;

public class ClienteService {

	private final Logger LOG = LogManager.getLogger(ProdutoService.class);

	private EntityManager entityManager;

	private ClienteDAO clienteDAO;

	public ClienteService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.clienteDAO = new ClienteDAO(entityManager);
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

	public void create(Cliente cliente) {
		this.LOG.info("Preparando para o cadastro de um cliente");

		if (cliente == null) {
			this.LOG.error("O cliente informado está nulo!");
			throw new RuntimeException("Product Null!");
		}

		try {
			getBeginTransaction();
			this.clienteDAO.create(cliente);
			commitAndCloseTransaction();
		} catch (Exception e) {
			this.LOG.error("Erro ao cadastrar um cliente, causado por: " + e.getMessage());
			throw new RuntimeException(e);
		}
		this.LOG.info("cliente foi cadastrado com sucesso!");
	}

	public void delete(Long id) {
		this.LOG.info("Preparando para encontrar o cliente");
		if (id == null) {
			this.LOG.error("O ID do cliente informado está nulo!");
			throw new RuntimeException("The ID is Null");
		}

		Cliente cliente = this.clienteDAO.getById(id);
		validateClienteIsNull(cliente);

		this.LOG.info("Cliente encontrado com sucesso!");

		getBeginTransaction();
		this.clienteDAO.delete(cliente);
		commitAndCloseTransaction();
		this.LOG.info("Cliente deletado com sucesso!");
	}

	public void update(Cliente newCliente, Long clienteId) {
		this.LOG.info("Preparando para Atualizar o cadastro do cliente");
		if (newCliente == null || clienteId == null) {
			this.LOG.error("Um dos parâmetros está nulo!");
			throw new RuntimeException("The parameter is null");
		}

		Cliente cliente = this.clienteDAO.getById(clienteId);
		validateClienteIsNull(cliente);

		this.LOG.info("Cliente encontrado no banco!");

		getBeginTransaction();
		cliente.setNome(newCliente.getNome());
		cliente.setCpf(newCliente.getCpf());
		cliente.setDataNascimentoDate(newCliente.getDataNascimentoDate());
		commitAndCloseTransaction();
		this.LOG.info("Cliente atualizado com sucesso!");
	}

	public String getById(Long id) {
		this.LOG.info("Preparando para buscar o cliente...");
		if (id == null) {
			this.LOG.error("O ID está nulo!");
			throw new RuntimeException("The parameter is null!");
		}

		getBeginTransaction();
		Cliente cliente = this.clienteDAO.getById(id);
		entityManager.close();
		if (cliente == null) {
			this.LOG.error("Não foi encontrado o cliente de id " + id);
			throw new EntityNotFoundException("cliente not found!");
		}
		return cliente.toString();
	}

	public List<Cliente> listAll() {
		this.LOG.info("Preparando para listar os clientes");
		List<Cliente> clientes = this.clienteDAO.listAll();

		if (clientes == null) {
			this.LOG.info("Não foram encontrados clientes");
			return new ArrayList<Cliente>();
		}
		this.LOG.info("Foram encontrados " + clientes.size() + " clientes.");
		return clientes;
	}

	public List<Cliente> listByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.info("O parametro nome está vazio");
			throw new RuntimeException("The parameter name is null");
		}

		this.LOG.info("Preparando para buscar os clientes de nome: " + name);
		List<Cliente> clientes = this.clienteDAO.ListByName(name.toLowerCase());

		if (clientes == null) {
			this.LOG.info("Não foram encontrados clientes");
			return new ArrayList<Cliente>();
		}

		this.LOG.info("Foram encontrados " + clientes.size() + " clientes.");
		return clientes;
	}

	private void validateClienteIsNull(Cliente cliente) {
		if (cliente == null) {
			this.LOG.error("O cliente não Existe!");
			throw new EntityNotFoundException("cliente not Found!");
		}
	}

}