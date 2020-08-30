package daos;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import models.Autenticacao;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import utils.JWT;

public class AutenticacaoDAO extends DAO {
  String ISSUER = "pokemart.api";
  String SUBJECT = "usuarios";
  long TTL = 25000000 * 2500;

  public AutenticacaoDAO() {
    super();
  }

  public String autenticar(HttpServletRequest req) throws IOException, SQLException {
    Usuario u;
    Gson parser = new Gson();
    Autenticacao auth = parser.fromJson(req.getReader(), Autenticacao.class);
    String query = "SELECT * FROM Usuarios WHERE email = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, auth.getEmail());
    ResultSet rs = st.executeQuery();

    if(rs.next()) {
      u = new Usuario();

      u.setId(rs.getInt("id"));
      u.setNome(rs.getString("nome"));
      u.setSexo(rs.getInt("sexo"));
      u.setCpf(rs.getString("cpf"));
      u.setDataNascimento(rs.getString("data_nascimento"));
      u.setTelefone(rs.getString("telefone"));
      u.setEmail(rs.getString("email"));
      u.setTipoUsuario(rs.getInt("tipo_usuario"));
      u.setSenha(rs.getString("senha"));

      if (BCrypt.checkpw(auth.getSenha(), new String(u.getSenha()))) {
        return JWT.encode(String.valueOf(u.getId()), ISSUER, SUBJECT, TTL);
      } else {
        return "Senha inválida";
      }
    }

    con.close();

    return "Usuário não existe";
  }
}