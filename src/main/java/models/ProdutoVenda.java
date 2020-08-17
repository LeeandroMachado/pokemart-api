package models;

public class ProdutoVenda {
  private int fkProdutoId;
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