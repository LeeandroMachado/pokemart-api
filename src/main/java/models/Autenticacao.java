package models;

import com.google.gson.annotations.Expose;

public class Autenticacao {
	@Expose
	private String email;
	@Expose
	private String senha;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}