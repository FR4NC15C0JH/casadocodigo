package br.com.casadocodigo.loja.infra;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
/* 
 * Classe que ensina o CDI a criar um objeto FacesContext
 */
@ApplicationScoped//diz que so é necessaria uma instancia da classe 
public class FacesContextProducer {
	
	@Produces//usado para nao gerenciamento do CDI
	@RequestScoped//usa o metodo uma vez a cd request
	public FacesContext get(){
		return FacesContext.getCurrentInstance();
	}
}
