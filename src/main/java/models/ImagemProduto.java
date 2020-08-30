package models;

import com.google.gson.annotations.Expose;

public class ImagemProduto {
	@Expose
	private int id;
	@Expose
	private String link;
	@Expose
  private int fkProdutoId;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getFkProdutoId() {
		return this.fkProdutoId;
	}

	public void setFkProdutoId(int fkProdutoId) {
		this.fkProdutoId = fkProdutoId;
	}

}