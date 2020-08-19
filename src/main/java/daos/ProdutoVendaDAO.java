package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.ProdutoVenda;
import interfaces.IDao;

public class ProdutoVendaDAO extends DAO implements IDao<ProdutoVenda> {
  public ProdutoVendaDAO() {
    super();
  }

  public void cadastrar(ProdutoVenda p) throws SQLException {
    String query = "INSERT INTO Produtos_venda (fk_produto_id, fk_venda_id) VALUES (?, ?)";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, p.getFkProdutoId());
    st.setInt(2, p.getFkVendaId());
    st.execute();
    st.close();

    con.close();
  }

  public List<ProdutoVenda> listar() throws SQLException {
    String query = "SELECT * FROM Produtos_venda";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  public ProdutoVenda listar(int idVenda) throws SQLException {
    String query = "SELECT * FROM Produtos_venda WHERE fk_venda_id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, idVenda);

    return buscar(st).get(0);
  }

  public void excluir(int id) { }

  public void atualizar(int idVenda, ProdutoVenda p) throws SQLException {
    String query = "UPDATE Produtos_venda SET fk_produto_id=?, fk_venda_id=? WHERE fk_venda_id=?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, p.getFkProdutoId());
    st.setInt(2, p.getFkVendaId());
    st.setInt(3, idVenda);
    st.executeUpdate();
    st.close();

    con.close();
  }

  public List<ProdutoVenda> buscar(PreparedStatement st) throws SQLException {
    List<ProdutoVenda> lista = new ArrayList<ProdutoVenda>();
    ResultSet rs = st.executeQuery();

    while(rs.next()) {
      ProdutoVenda p = new ProdutoVenda();

      p.setFkVendaId(rs.getInt("fk_venda_id"));
      p.setFkProdutoId(rs.getInt("fk_produto_id"));

      lista.add(p);
    }

    con.close();

    return lista;
  }

}