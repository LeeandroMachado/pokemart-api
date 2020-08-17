package models;

public class Endereco {
  private int id;
  private String cep;
  private String rua;
  private String num;
  private String bairro;
  private String cidade;
  private String estado;
  private String complemento;
  private String descricao;
  private boolean cobranca;
  private int fkUsuarioId;

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

	public boolean isCobranca() {
		return this.cobranca;
	}

	public void setCobranca(boolean cobranca) {
		this.cobranca = cobranca;
	}

	public int getFkUsuarioId() {
		return this.fkUsuarioId;
	}

	public void setFkUsuarioId(int fkUsuarioId) {
		this.fkUsuarioId = fkUsuarioId;
	}

}