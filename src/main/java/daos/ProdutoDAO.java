package daos;

import interfaces.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Produto;

public class ProdutoDAO extends DAO implements IDao<Produto> {
  public ProdutoDAO() {
    super();
  }

  @Override
  public void cadastrar(Produto p) throws SQLException {
    String query = "INSERT INTO Produtos (nome, peso, preco, qtd_estoque) VALUES (?, ?, ?, ?)";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, p.getNome());
    st.setDouble(2, p.getPeso());
    st.setDouble(3, p.getPreco());
    st.setInt(4, p.getQtdEstoque());
    st.execute();
    st.close();

    con.close();
  }

  @Override
  public List<Produto> listar() throws SQLException {
    String query = "SELECT * FROM Produtos";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  @Override
  public List<Produto> listar(int fk) throws SQLException {
    return listar();
  }

  @Override
  public Produto listarUm(int id) throws SQLException {
    String query = "SELECT * FROM Produtos WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);

    return buscar(st).get(0);
  }

  @Override
  public void excluir(int id) throws SQLException {
    String query = "DELETE FROM Produtos WHERE id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, id);
    st.executeUpdate();

    con.close();
  }

  @Override
  public void atualizar(int id, Produto p) throws SQLException {
    String query = "UPDATE Produtos SET nome=?, peso=?, preco=?, qtd_estoque=? WHERE id=?";

    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, p.getNome());
    st.setDouble(2, p.getPeso());
    st.setDouble(3, p.getPreco());
    st.setInt(4, p.getQtdEstoque());
    st.setInt(5, id);
    st.executeUpdate();
    st.close();

    con.close();
  }

  @Override
  public List<Produto> buscar(PreparedStatement st) throws SQLException {
    List<Produto> lista = new ArrayList<Produto>();
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      Produto p = new Produto();

      p.setId(rs.getInt("id"));
      p.setNome(rs.getString("nome"));
      p.setPeso(rs.getDouble("peso"));
      p.setPreco(rs.getDouble("preco"));
      p.setQtdEstoque(rs.getInt("qtd_estoque"));

      lista.add(p);
    }

    con.close();

    return lista;
  }

}