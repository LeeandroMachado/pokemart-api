package daos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import models.Autenticacao;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import utils.ConexaoBanco;
import utils.JWT;

public class AutenticacaoDAO {
  String ISSUER = "pokemart.api";
  String SUBJECT = "usuarios";
  long TTL = 25000000 * 2500;
  protected Connection con;

  public AutenticacaoDAO() {
    this.con = new ConexaoBanco().getConnection();
  }

  public String autenticar(HttpServletRequest req) throws IOException, SQLException, Exception {
    Usuario u;
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Autenticacao auth = parser.fromJson(req.getReader(), Autenticacao.class);
    String token;
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
        token = JWT.encode(String.valueOf(u.getId()), ISSUER, SUBJECT, TTL);
        u.setToken(token);
      } else {
        throw new Exception("Senha inválida");
      }

      con.close();
      return parser.toJson(u);
    } else {
      throw new Exception("Usuário não existe");
    }
  }
}