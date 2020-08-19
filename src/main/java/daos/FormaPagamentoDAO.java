package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.FormaPagamento;
import interfaces.IDao;

public class FormaPagamentoDAO extends DAO implements IDao<FormaPagamento> {
  public FormaPagamentoDAO() {
    super();
  }

  public void cadastrar(FormaPagamento f) throws SQLException {
    String query = "INSERT INTO Enderecos (descricao) VALUES (?)";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, f.getDescricao());
    st.execute();
    st.close();

    con.close();
  }

  public List<FormaPagamento> listar() throws SQLException {
    String query = "SELECT * FROM Formas_pagamento";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  public FormaPagamento listar(int id) throws SQLException {
    String query = "SELECT * FROM Formas_pagamento WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st).get(0);
  }

  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Formas_pagamento WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    st.executeUpdate();

    con.close();
  }

  public void atualizar(int id, FormaPagamento f) throws SQLException {
    String query = "UPDATE Formas_pagamento SET descricao=? WHERE id=?";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, f.getDescricao());
    st.setInt(2, id);
    st.executeUpdate();
    st.close();

    con.close();
  }

  public List<FormaPagamento> buscar(PreparedStatement st) throws SQLException {
    List<FormaPagamento> lista = new ArrayList<FormaPagamento>();
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      FormaPagamento f = new FormaPagamento();

      f.setId(rs.getInt("id"));
      f.setDescricao(rs.getString("descricao"));

      lista.add(f);
    }

    con.close();

    return lista;
  }

}