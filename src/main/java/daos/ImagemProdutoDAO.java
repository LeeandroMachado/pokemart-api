package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.ImagemProduto;
import interfaces.IDao;

public class ImagemProdutoDAO extends DAO implements IDao<ImagemProduto> {
  public ImagemProdutoDAO() {
    super();
  }

  public void cadastrar(ImagemProduto i) throws SQLException {
    String query = "INSERT INTO Imagens_produto (link, fk_produto_id) VALUES (?, ?)";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, i.getLink());
    st.setInt(2, i.getFkProdutoId());
    st.execute();
    st.close();

    con.close();
  }

  public List<ImagemProduto> listar() throws SQLException {
    String query = "SELECT * FROM Imagens_produto";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  public ImagemProduto listar(int id) throws SQLException {
    String query = "SELECT * FROM Imagens_produto WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st).get(0);
  }

  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Imagens_produto WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    st.executeUpdate();

    con.close();
  }

  public void atualizar(int id, ImagemProduto i) throws SQLException {
    String query = "UPDATE Imagens_produto SET link=?, fk_produto_id=? WHERE id=?";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, i.getLink());
    st.setInt(2, i.getFkProdutoId());
    st.setInt(3, id);
    st.executeUpdate();
    st.close();

    con.close();
  }

  public List<ImagemProduto> buscar(PreparedStatement st) throws SQLException {
    List<ImagemProduto> lista = new ArrayList<ImagemProduto>();
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      ImagemProduto i = new ImagemProduto();

      i.setId(rs.getInt("id"));
      i.setLink(rs.getString("link"));
      i.setFkProdutoId(rs.getInt("fk_produto_id"));

      lista.add(i);
    }

    con.close();

    return lista;
  }
}