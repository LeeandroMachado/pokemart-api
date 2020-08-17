package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import interfaces.IDao;

public class UsuarioDAO extends DAO implements IDao<Usuario> {
  public UsuarioDAO() {
    super();
  }

  public void cadastrar(Usuario u) throws SQLException {
    String query = "INSERT INTO Usuarios (nome,sexo,cpf,data_nascimento,telefone,email,senha,tipo_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String hashed = BCrypt.hashpw(u.getSenha(), BCrypt.gensalt());

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, u.getNome());
    st.setString(2, u.getSexo());
    st.setString(3, u.getCpf());
    st.setString(4, u.getDataNascimento());
    st.setString(5, u.getTelefone());
    st.setString(6, u.getEmail());
    st.setString(7, hashed);
    st.setString(8, u.getTipoUsuario());
    st.execute();
    st.close();

    con.close();
  }

  public List<Usuario> listar() throws SQLException {
    String query = "SELECT * FROM Usuarios";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  public List<Usuario> listar(int id) throws SQLException {
    String query = "SELECT * FROM Usuarios WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st);
  }

  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Usuarios WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    st.executeUpdate();

    con.close();
  }

  public void atualizar(int id, Usuario u) throws SQLException {
    String query = "UPDATE Usuarios SET nome=?,sexo=?,cpf=?,data_nascimento=?,telefone=?,email=?,tipo_usuario=? WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, u.getNome());
    st.setString(2, u.getSexo());
    st.setString(3, u.getCpf());
    st.setString(4, u.getDataNascimento());
    st.setString(5, u.getTelefone());
    st.setString(6, u.getEmail());
    st.setString(7, u.getTipoUsuario());
    st.setInt(8, id);
    st.executeUpdate();

    con.close();
  }

  public List<Usuario> buscar(PreparedStatement st) throws SQLException {
    List<Usuario> lista = new ArrayList<Usuario>();
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      Usuario u = new Usuario();

      u.setId(rs.getInt("id"));
      u.setNome(rs.getString("nome"));
      u.setSexo(rs.getString("sexo"));
      u.setCpf(rs.getString("cpf"));
      u.setDataNascimento(rs.getString("data_nascimento"));
      u.setTelefone(rs.getString("telefone"));
      u.setEmail(rs.getString("email"));
      u.setTipoUsuario(rs.getString("tipo_usuario"));

      lista.add(u);
    }

    con.close();

    return lista;
  }
}