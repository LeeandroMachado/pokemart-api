package daos;

import interfaces.IDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Endereco;
import utils.ConexaoBanco;

public class EnderecoDAO implements IDao<Endereco> {
  protected Connection con;

  public EnderecoDAO() {
    this.con = new ConexaoBanco().getConnection();
  }

  @Override
  public void cadastrar(Endereco e) throws SQLException {
    String query = "INSERT INTO Enderecos (cep,rua,num,bairro,cidade,estado,complemento,descricao,cobranca,fk_usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    System.out.println(e.toString());
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
    System.out.println(st.toString());
    st.execute();
    st.close();


    con.close();
  }

  @Override
  public List<Endereco> listar() throws SQLException {
    String query = "SELECT * FROM Enderecos";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  @Override
  public List<Endereco> listar(int fk) throws SQLException {
    return listar();
  }

  @Override
  public Endereco listarUm(int id) throws SQLException {
    String query = "SELECT * FROM Enderecos WHERE fk_usuario_id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st).get(0);
  }

  @Override
  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Enderecos WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    System.out.println(st.toString());
    st.executeUpdate();

    con.close();
  }

  @Override
  public void atualizar(int id, Endereco e) throws SQLException {
    String query = "UPDATE Enderecos SET cep=?,rua=?,num=?,bairro=?,cidade=?,estado=?,complemento=?,descricao=?,cobranca=? WHERE fk_usuario_id=?";

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
    st.executeUpdate();
    st.close();

    con.close();
  }

  @Override
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