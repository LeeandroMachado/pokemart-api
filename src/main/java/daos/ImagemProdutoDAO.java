package daos;

import interfaces.ICrud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.ImagemProduto;
import utils.ConexaoBanco;

public class ImagemProdutoDAO implements ICrud<ImagemProduto> {
  protected Connection con;

  public ImagemProdutoDAO() {
    this.con = new ConexaoBanco().getConnection();
  }

  @Override
  public void cadastrar(ImagemProduto i) throws SQLException {
    String query = "INSERT INTO Imagens_produto (link, fk_produto_id) VALUES (?, ?)";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, i.getLink());
    st.setInt(2, i.getFkProdutoId());
    st.execute();
    st.close();

    con.close();
  }

  @Override
  public List<ImagemProduto> listar() throws SQLException {
    String query = "SELECT * FROM Imagens_produto";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  @Override
  public List<ImagemProduto> listar(int fk) throws SQLException {
    String query = "SELECT * FROM Imagens_produto WHERE fk_produto_id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, fk);

    return buscar(st);
  }

  @Override
  public ImagemProduto listarUm(int id) throws SQLException {
    String query = "SELECT * FROM Imagens_produto WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st).get(0);
  }

  @Override
  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Imagens_produto WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    st.executeUpdate();

    con.close();
  }

  @Override
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

  @Override
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