package br.com.casadocodigo.loja.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.builder.ReturnBuilder;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import org.jboss.security.annotation.Authorization;

import br.com.casadocodigo.loja.daos.AuthorDAO;
import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.infra.MessagesHelper;
import br.com.casadocodigo.loja.models.Author;
import br.com.casadocodigo.loja.models.Book;

@Model
// anotação CDI - objetos serão criados no escopo do request
public class AdminBooksBean {

	private Book product = new Book();// PQ nao usar injeção de dependencias?
	@Inject
	private BookDAO productDAO;
	@Inject
	private AuthorDAO authorDAO;
	@Inject
	private MessagesHelper messagesHelper;
	private List<Integer> selectedAuthorsIds = new ArrayList<>();
	private List<Author> authors = new ArrayList<Author>();
	private Part summary;//guarda o arquivo enviado do formulario
	@Inject
	private FileSaver fileSaver;
	
	public Part getSummary() {
		return summary;
	}

	public void setSummary(Part summary) {
		this.summary = summary;
	}

	@PostConstruct
	// executa apos o construtor
	public void loadObjects() {
		this.authors = authorDAO.list();
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public List<Integer> getSelectedAuthorsIds() {
		return selectedAuthorsIds;
	}

	public void setSelectedAuthorsIds(List<Integer> selectedAuthorsIds) {
		this.selectedAuthorsIds = selectedAuthorsIds;
	}

	@Transactional
	public String save() {
		String summaryPath = fileSaver.write("summaries", summary);
		
		productDAO.save(product);
		
		messagesHelper.addFlash(new FacesMessage("Livro gravado com sucesso"));

		// faz com que o JSF retorne status 302 e faz um redirect
		return "/livros/list?faces-redirect=true";
	}

	// limpa os dados no input apos o request
	private void clearObjects() {
		this.product = new Book();
		this.selectedAuthorsIds.clear();
	}

	public Book getProduct() {
		return product;
	}

	// metodo usa partes dos lambdas do java 8
	private void populateBookAuthor() {
		selectedAuthorsIds.stream().map((id) -> {
			return new Author(id);
		}).forEach(product::add);
	}

}
