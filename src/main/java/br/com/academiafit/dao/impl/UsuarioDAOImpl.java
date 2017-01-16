package br.com.academiafit.dao.impl;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.academiafit.dao.UsuarioDAO;
import br.com.academiafit.entidade.Usuario;

@Repository
public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO{

	// Salvar: salva o novo usu�rio no banco de dados
	@Override
	public void salvar(Usuario usuario){
		// verifica se o usuario existe no banco de dados
		if (super.getEntityManager().find(Usuario.class, usuario.getNickname()) != null){

			// se exisir, ent�o n�o aceita o login colocado
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login j� est� sendo utilizado por outro usu�rio!"));
		}
		else{
			// se n�o existir, insere no banco
			super.getEntityManager().persist(usuario);

			// mostra alerta de inser��o na tela do usu�rio
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario cadastrado com sucesso!"));
		}
	}

	// listarTodos: obtem todos os Usuarios a partir do banco
	@Override
	public List<Usuario> listarTodos(){
		// cria uma query com o select na tabela usuario
		Query query = super.getEntityManager().createQuery("select u from Usuario u");

		// armazena o resultado da busca numa collection de usu�rios
		List<Usuario> listaUsuario = query.getResultList();

		// fecha a conex�o
		super.getEntityManager().close();

		return listaUsuario;
	}

	// excluir: exclui um usu�rio do banco
	@Override
	public void excluir(String nickname) {
		// verifica se o nickname fornecido existe no banco de dados
		Usuario usuarioExcluir = super.getEntityManager().find(Usuario.class, nickname);

		if (usuarioExcluir != null){ // se existir

			// exclui o usuario correspondente
			super.getEntityManager().remove(usuarioExcluir);

			// imprime alerta na tela
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario excluido com sucesso!"));
		}
		else{	// se n�o existir

			// imprime alerta de usuario n�o encontrado na tela do usu�rio
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario n�o encontrado!"));
		}

		// fecha a conex�o
		super.getEntityManager().close();
	}

	// alterarSenha: altera a senha de um determinado usu�rio
	@Override
	public void alterarSenha(Usuario usuario){
		boolean status = (super.getEntityManager().find(Usuario.class, usuario.getNickname()) != null);
		
		if (status){
			// cria uma query de atualiza��o para a senha do usu�rio
			Query query = super.getEntityManager().createQuery("update Usuario u set u.senha = :senha where u.nickname like :nickname", Usuario.class);
	
			// determina os par�metros da query
			query.setParameter("nickname", usuario.getNickname());
			query.setParameter("senha", usuario.getSenha());
	
			// executa a query
			query.executeUpdate();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha alterada com sucesso"));
		}
		else{
			// usuario n�o existe
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario n�o encontrado!"));
		}

		// fecha a conex�o
		super.getEntityManager().close();
	}

	@Override
	public void consultar(String nickname) {
		// verifica se o nickname fornecido existe no banco de dados
		Usuario usuario = super.getEntityManager().find(Usuario.class, nickname);

		if (usuario != null){ // se existir
			// mostra um alerta na tela do usu�rio
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nickname: "+usuario.getNickname() + " Senha: " + usuario.getSenha()));	
		}
		else{
			// mostra um alerta na tela do usu�rio
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario n�o encontrado."));
		}
		
		// fecha a conex�o
		super.getEntityManager().close();
	}	
}
