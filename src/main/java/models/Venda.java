package models;

import com.google.gson.annotations.Expose;

public class Venda {
	@Expose
	private int id;
	@Expose
	private String dataVenda;
	@Expose
	private double valorTotal;
	@Expose
	private String documentoId;
	@Expose
	private int fkFormaPagamentoId;
	@Expose
	private int fkEnderecoId;
	@Expose
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

	public String getDocumentoId() {
		return this.documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
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