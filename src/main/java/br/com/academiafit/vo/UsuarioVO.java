package br.com.academiafit.vo;

import java.io.Serializable;

public class UsuarioVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nickname;
	private String senha;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
}
