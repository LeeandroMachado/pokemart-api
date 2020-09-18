package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Endereco;
import models.Produto;
import models.Usuario;
import models.Venda;
import utils.ConexaoBanco;

public class VendaDAO {
  protected Connection con;

  public VendaDAO() {
    this.con = new ConexaoBanco().getConnection();
  }

  public Venda buildar(Usuario usuario, List<Produto> produtos, int fkFormaPagamentoId, int fkEnderecoId) throws SQLException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    String dataVenda = formatter.format(date);
    double valorTotal = 0;

    for (Produto produto : produtos) {
      valorTotal += produto.getPreco() * produto.getQtd();
    }

    Venda v = new Venda();
    v.setValorTotal(valorTotal);
    v.setDataVenda(dataVenda);
    v.setFkEnderecoId(fkEnderecoId);
    v.setFkUsuarioId(usuario.getId());
    v.setFkFormaPagamentoId(fkFormaPagamentoId);

    return v;
  }

  public int adicionar(Venda v) throws SQLException, ParseException {
    int lastInsertedId = 0;
    String query = "INSERT INTO Vendas (data_venda, valor_total, documento_id, fk_forma_pagamento_id, fk_endereco_id, fk_usuario_id) VALUES (?, ?, ?, ?, ?, ?)";

    Date dataVenda = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(v.getDataVenda());
    java.sql.Date dataSql = new java.sql.Date(dataVenda.getTime());

    PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    st.setDate(1, dataSql);
    st.setDouble(2, v.getValorTotal());
    st.setString(3, v.getDocumentoId());
    st.setInt(4, v.getFkFormaPagamentoId());
    st.setInt(5, v.getFkEnderecoId());
    st.setInt(6, v.getFkUsuarioId());
    st.execute();

    ResultSet rs = st.getGeneratedKeys();

    if (rs.next()) {
      lastInsertedId = rs.getInt(1);
    }

    st.close();
    con.close();

    return lastInsertedId;
  }

  public Venda getVenda(int idVenda) throws SQLException {
    List<Venda> vendas = new ArrayList<Venda>();
    String query = "SELECT * FROM Vendas WHERE id = ?";
    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, idVenda);

    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      Venda v = new Venda();

      v.setId(rs.getInt("id"));
      v.setValorTotal(rs.getDouble("valor_total"));
      v.setDataVenda(rs.getString("data_venda"));
      v.setFkFormaPagamentoId(rs.getInt("fk_forma_pagamento_id"));
      v.setFkEnderecoId(rs.getInt("fk_endereco_id"));
      v.setFkUsuarioId(rs.getInt("fk_usuario_id"));

      vendas.add(v);
    }

    return vendas.get(0);
  }

  public Endereco getEndereco(int fkIdVenda) throws SQLException {
    List<Endereco> enderecos = new ArrayList<Endereco>();
    String query = "SELECT * FROM Enderecos e INNER JOIN Vendas v ON v.fk_endereco_id = e.id WHERE v.id = ?";
    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, fkIdVenda);
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      Endereco e = new Endereco();

      e.setId(rs.getInt("id"));
      e.setCep(rs.getString("cep"));
      e.setRua(rs.getString("rua"));
      e.setNum(rs.getString("num"));
      e.setBairro(rs.getString("bairro"));
      e.setCidade(rs.getString("cidade"));
      e.setEstado(rs.getString("estado"));
      e.setComplemento(rs.getString("complemento"));
      e.setDescricao(rs.getString("descricao"));
      e.setCobranca(rs.getInt("cobranca"));
      e.setFkUsuarioId(rs.getInt("fk_usuario_id"));

      enderecos.add(e);
    }

    return enderecos.get(0);
  }

  public Usuario getCliente(int fkIdVenda) throws SQLException {
    List<Usuario> usuarios = new ArrayList<Usuario>();
    String query = "SELECT * FROM Usuarios u INNER JOIN Vendas v ON v.fk_usuario_id = u.id WHERE v.id = ?";
    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, fkIdVenda);
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      Usuario u = new Usuario();

      u.setId(rs.getInt("id"));
      u.setNome(rs.getString("nome"));
      u.setSexo(rs.getInt("sexo"));
      u.setCpf(rs.getString("cpf"));
      u.setDataNascimento(rs.getString("data_nascimento"));
      u.setTelefone(rs.getString("telefone"));
      u.setEmail(rs.getString("email"));
      u.setTipoUsuario(rs.getInt("tipo_usuario"));

      usuarios.add(u);
    }

    return usuarios.get(0);
  }

  public List<Produto> getProdutos(int fkIdVenda) throws SQLException {
    List<Produto> produtos = new ArrayList<Produto>();
    String query = "SELECT * FROM Produtos p INNER JOIN Produtos_venda pv ON pv.fk_produto_id = p.id WHERE fk_venda_id = ?";
    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, fkIdVenda);
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      Produto p = new Produto();

      p.setId(rs.getInt("id"));
      p.setNome(rs.getString("nome"));
      p.setPeso(rs.getDouble("peso"));
      p.setPreco(rs.getDouble("preco"));
      p.setQtdEstoque(rs.getInt("qtd_estoque"));

      produtos.add(p);
    }

    return produtos;
  }
}