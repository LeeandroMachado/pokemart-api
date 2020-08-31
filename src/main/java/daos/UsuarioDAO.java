package daos;

import interfaces.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO extends DAO implements IDao<Usuario> {
  public UsuarioDAO() {
    super();
  }

  @Override
  public void cadastrar(Usuario u) throws SQLException, ParseException {
    String query = "INSERT INTO Usuarios (nome,sexo,cpf,data_nascimento,telefone,email,senha,tipo_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String hashed = BCrypt.hashpw(u.getSenha(), BCrypt.gensalt());
    Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(u.getDataNascimento());
    java.sql.Date dataSql = new java.sql.Date(dataNascimento.getTime());

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, u.getNome());
    st.setInt(2, u.getSexo());
    st.setString(3, u.getCpf());
    st.setDate(4, dataSql);
    st.setString(5, u.getTelefone());
    st.setString(6, u.getEmail());
    st.setString(7, hashed);
    st.setInt(8, u.getTipoUsuario());
    st.execute();
    st.close();

    con.close();
  }

  @Override
  public List<Usuario> listar() throws SQLException {
    String query = "SELECT * FROM Usuarios";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  @Override
  public Usuario listar(int id) throws SQLException {
    String query = "SELECT * FROM Usuarios WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st).get(0);
  }

  @Override
  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Usuarios WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    st.executeUpdate();

    con.close();
  }

  @Override
  public void atualizar(int id, Usuario u) throws SQLException, ParseException {
    String query = "UPDATE Usuarios SET nome=?,sexo=?,cpf=?,data_nascimento=?,telefone=? WHERE id = ?";

    Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(u.getDataNascimento());
    java.sql.Date dataSql = new java.sql.Date(dataNascimento.getTime());

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, u.getNome());
    st.setInt(2, u.getSexo());
    st.setString(3, u.getCpf());
    st.setDate(4, dataSql);
    st.setString(5, u.getTelefone());
    st.setInt(6, id);
    st.executeUpdate();

    con.close();
  }

  @Override
  public List<Usuario> buscar(PreparedStatement st) throws SQLException {
    List<Usuario> lista = new ArrayList<Usuario>();
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

      lista.add(u);
    }

    con.close();

    return lista;
  }
}