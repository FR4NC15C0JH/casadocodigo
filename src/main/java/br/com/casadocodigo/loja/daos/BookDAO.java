package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Book;

public class BookDAO {

	@PersistenceContext
	// injeção de um EM
	private EntityManager manager;

	public void save(Book product) {
		manager.persist(product);
	}
	//carrega a lista de livros
	public List<Book> list() {
		return manager.createQuery(
				"select distinct(b) from Book b join fetch b.authors",
				Book.class).getResultList();
	}
}
