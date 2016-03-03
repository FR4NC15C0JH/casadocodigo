package br.com.casadocodigo.loja.infra;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
/*
 * Classe criada para o isolamento de mensagens 
 */
@ApplicationScoped
public class MessagesHelper {
	
	@Inject
	private FacesContext facesContext;

	public void addFlash(FacesMessage facesMessage) {
		//diz ao JSF para manter as msg até o próximo request
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		facesContext.addMessage("messages",facesMessage);
	}

}
