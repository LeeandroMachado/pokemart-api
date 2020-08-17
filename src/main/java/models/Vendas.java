package models;

public class Vendas {
  private int id;
  private String dataVenda;
  private double valorTotal;
  private String documentId;
  private int fkFormaPagamentoId;
  private int fkEnderecoId;
  private int fkUsuarioId;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDataVenda() {
		return this.dataVenda;
	}

	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
	}

	public double getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public int getFkFormaPagamentoId() {
		return this.fkFormaPagamentoId;
	}

	public void setFkFormaPagamentoId(int fkFormaPagamentoId) {
		this.fkFormaPagamentoId = fkFormaPagamentoId;
	}

	public int getFkEnderecoId() {
		return this.fkEnderecoId;
	}

	public void setFkEnderecoId(int fkEnderecoId) {
		this.fkEnderecoId = fkEnderecoId;
	}

	public int getFkUsuarioId() {
		return this.fkUsuarioId;
	}

	public void setFkUsuarioId(int fkUsuarioId) {
		this.fkUsuarioId = fkUsuarioId;
	}

}