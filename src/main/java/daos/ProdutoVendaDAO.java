package daos;

import interfaces.IDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.ProdutoVenda;
import utils.ConexaoBanco;

public class ProdutoVendaDAO implements IDao<ProdutoVenda> {
  protected Connection con;

  public ProdutoVendaDAO() {
    this.con = new ConexaoBanco().getConnection();
  }

  @Override
  public void cadastrar(ProdutoVenda p) throws SQLException {
    String query = "INSERT INTO Produtos_venda (fk_produto_id, fk_venda_id) VALUES (?, ?)";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, p.getFkProdutoId());
    st.setInt(2, p.getFkVendaId());
    st.execute();
  }

  @Override
  public List<ProdutoVenda> listar() throws SQLException {
    String query = "SELECT * FROM Produtos_venda";

    PreparedStatement st = con.prepareStatement(query);

    return buscar(st);
  }

  @Override
  public List<ProdutoVenda> listar(int fk) throws SQLException {
    return listar();
  }

  @Override
  public ProdutoVenda listarUm(int idVenda) throws SQLException {
    String query = "SELECT * FROM Produtos_venda WHERE fk_venda_id = ?";

    PreparedStatement st = con.prepareStatement(query);
    st.setInt(1, idVenda);

    return buscar(st).get(0);
  }

  @Override
  public void excluir(int id) { }

  @Override
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

  @Override
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