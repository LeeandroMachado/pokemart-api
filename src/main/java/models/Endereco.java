package models;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Endereco {
	@Expose
	private int id;
	@Expose
	private String cep;
	@Expose
	private String rua;
	@Expose
	private String num;
	@Expose
	private String bairro;
	@Expose
	private String cidade;
	@Expose
	private String estado;
	@Expose
	private String complemento;
	@Expose
	private String descricao;
	@Expose
	private int cobranca;
	@Expose
	private int fkUsuarioId;

	public int getCobranca() {
		return this.cobranca;
	}

	public void setCobranca(int cobranca) {
		this.cobranca = cobranca;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return this.rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getFkUsuarioId() {
		return this.fkUsuarioId;
	}

	public void setFkUsuarioId(int fkUsuarioId) {
		this.fkUsuarioId = fkUsuarioId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}