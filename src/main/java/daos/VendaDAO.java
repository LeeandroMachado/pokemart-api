package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Venda;
import interfaces.IDao;

public class VendaDAO extends DAO implements IDao<Venda> {
  public VendaDAO() {
    super();
  }

  public void cadastrar(Venda v) throws SQLException, ParseException {
    String query = "INSERT INTO Vendas (data_venda, valor_total, documento_id, fk_forma_pagamento_id, fk_endereco_id, fk_usuario_id) VALUES (?, ?, ?, ?, ?, ?)";

    Date dataVenda = new SimpleDateFormat("dd/MM/yyyy").parse(v.getDataVenda());
    java.sql.Date dataSql = new java.sql.Date(dataVenda.getTime());

    PreparedStatement st = con.prepareStatement(query);
    st.setDate(1, dataSql);
    st.setDouble(2, v.getValorTotal());
    st.setString(3, v.getDocumentoId());
    st.setInt(4, v.getFkFormaPagamentoId());
    st.setInt(5, v.getFkEnderecoId());
    st.setInt(6, v.getFkUsuarioId());
    st.execute();
    st.close();

    con.close();
  }

  public List<Venda> listar() throws SQLException {
    String query = "SELECT * FROM Vendas";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  public Venda listar(int id) throws SQLException {
    String query = "SELECT * FROM Vendas WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st).get(0);
  }

  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Vendas WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    st.executeUpdate();

    con.close();
  }

  public void atualizar(int id, Venda v) throws SQLException , ParseException {
    String query = "UPDATE Vendas SET data_venda=?, valor_total=?, documento_id=?, fk_forma_pagamento_id=?, fk_endereco_id=?, fk_usuario_id=? WHERE id=?";

    Date dataVenda = new SimpleDateFormat("dd/MM/yyyy").parse(v.getDataVenda());
    java.sql.Date dataSql = new java.sql.Date(dataVenda.getTime());

    PreparedStatement st = con.prepareStatement(query);
    st.setDate(1, dataSql);
    st.setDouble(2, v.getValorTotal());
    st.setString(3, v.getDocumentoId());
    st.setInt(4, v.getFkFormaPagamentoId());
    st.setInt(5, v.getFkEnderecoId());
    st.setInt(6, v.getFkUsuarioId());
    st.setInt(7, id);
    st.executeUpdate();
    st.close();

    con.close();
  }

  public List<Venda> buscar(PreparedStatement st) throws SQLException {
    List<Venda> lista = new ArrayList<Venda>();
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      Venda v = new Venda();

      v.setId(rs.getInt("id"));
      v.setDataVenda(rs.getString("data_venda"));
      v.setDocumentoId(rs.getString("document_id"));
      v.setFkFormaPagamentoId(rs.getInt("fk_forma_pagamento_id"));
      v.setFkEnderecoId(rs.getInt("fk_endereco_id"));
      v.setFkUsuarioId(rs.getInt("fk_usuario_id"));

      lista.add(v);
    }

    con.close();

    return lista;
  }

}