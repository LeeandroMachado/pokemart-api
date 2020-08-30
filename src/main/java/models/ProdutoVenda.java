package models;

import com.google.gson.annotations.Expose;

public class ProdutoVenda {
	@Expose
	private int fkProdutoId;
	@Expose
  private int fkVendaId;

	public int getFkProdutoId() {
		return this.fkProdutoId;
	}

	public void setFkProdutoId(int fkProdutoId) {
		this.fkProdutoId = fkProdutoId;
	}

	public int getFkVendaId() {
		return this.fkVendaId;
	}

	public void setFkVendaId(int fkVendaId) {
		this.fkVendaId = fkVendaId;
	}

}