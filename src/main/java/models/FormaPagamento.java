package models;

import com.google.gson.annotations.Expose;

public class FormaPagamento {
	@Expose
	private int id;
	@Expose
  private String descricao;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}