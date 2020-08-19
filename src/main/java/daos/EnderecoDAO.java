package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Endereco;
import interfaces.IDao;

public class EnderecoDAO extends DAO implements IDao<Endereco> {
  public EnderecoDAO() {
    super();
  }

  public void cadastrar(Endereco e) throws SQLException {
    String query = "INSERT INTO Enderecos (cep,rua,num,bairro,cidade,estado,complemento,descricao,cobranca,fk_usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, e.getCep());
    st.setString(2, e.getRua());
    st.setString(3, e.getNum());
    st.setString(4, e.getBairro());
    st.setString(5, e.getCidade());
    st.setString(6, e.getEstado());
    st.setString(7, e.getComplemento());
    st.setString(8, e.getDescricao());
    st.setInt(9, e.getCobranca());
    st.setInt(10, e.getFkUsuarioId());
    st.execute();
    st.close();

    con.close();
  }

  public List<Endereco> listar() throws SQLException {
    String query = "SELECT * FROM Enderecos";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  public Endereco listar(int id) throws SQLException {
    String query = "SELECT * FROM Enderecos WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st).get(0);
  }

  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Enderecos WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    st.executeUpdate();

    con.close();
  }

  public void atualizar(int id, Endereco e) throws SQLException {
    String query = "UPDATE Enderecos SET cep=?,rua=?,num=?,bairro=?,cidade=?,estado=?,complemento=?,descricao=?,cobranca=?,fk_usuario_id=? WHERE id=?";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, e.getCep());
    st.setString(2, e.getRua());
    st.setString(3, e.getNum());
    st.setString(4, e.getBairro());
    st.setString(5, e.getCidade());
    st.setString(6, e.getEstado());
    st.setString(7, e.getComplemento());
    st.setString(8, e.getDescricao());
    st.setInt(9, e.getCobranca());
    st.setInt(10, e.getFkUsuarioId());
    st.setInt(11, id);
    st.executeUpdate();
    st.close();

    con.close();
  }

  public List<Endereco> buscar(PreparedStatement st) throws SQLException {
    List<Endereco> lista = new ArrayList<Endereco>();
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

      lista.add(e);
    }

    con.close();

    return lista;
  }
}