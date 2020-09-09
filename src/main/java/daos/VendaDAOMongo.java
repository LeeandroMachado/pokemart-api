package daos;

import com.mongodb.client.MongoCollection;
import daos.VendaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Endereco;
import models.Produto;
import models.Usuario;
import models.Venda;
import org.bson.Document;
import utils.ConexaoMongo;

public class VendaDAOMongo {
  private final String COLLECTION_NAME = "compras";
  private ConexaoMongo cm;
  private MongoCollection collection;

  public VendaDAOMongo() {
    this.cm = new ConexaoMongo();
    this.collection = this.cm.getMongoDatabase().getCollection(COLLECTION_NAME);
  }

  public void cadastrar(int idVenda) {
    Venda v = getVenda(idVenda);
    Endereco e = getEndereco(idVenda);
    List<Produto> produtos = getProdutos(v);
    Usuario u = getUsuario(idVenda);

    this.collection.insertOne(buildar(v, e, produtos, u));
  }

  private Document buildar(Venda v, Endereco e, List<Produto> p, Usuario u) {
    List<Document> produtos = new ArrayList<Document>();
    Document compra = new Document();
    Document pagamento = new Document();

    pagamento.append("pagamento_id", v.getFkFormaPagamentoId());
    pagamento.append("faturas", new Document().append("valor", v.getValorTotal()));

    for (Produto produto: p) {
      Document prod = new Document();
      prod.append("produto_id", produto.getId());
      prod.append("nome", produto.getNome());
      prod.append("peso", produto.getPeso());
      prod.append("valor", produto.getPreco());

      produtos.add(prod);
    }

    compra.append("cliente_nome", u.getNome());
    compra.append("endereco_entrega", e.getRua() + ", Nº" + e.getNum() + " - " + e.getBairro() + " - " + e.getCidade() + " - " + e.getEstado());
    compra.append("valor_total", v.getValorTotal());
    compra.append("data_compra", v.getDataVenda());
    compra.append("produtos", produtos);
    compra.append("pagamento", pagamento);

    return compra;
  }

  private Usuario getUsuario(int idVenda) {
    Usuario u = new Usuario();
    VendaDAO vdao = new VendaDAO();

    try {
      u = vdao.getCliente(idVenda);
    } catch (SQLException err) {
      System.out.println(err.toString());
    }

    return u;
  }

  private Endereco getEndereco(int idVenda) {
    Endereco e = new Endereco();
    VendaDAO vdao = new VendaDAO();

    try {
      e = vdao.getEndereco(idVenda);
    } catch (SQLException err) {
      System.out.println(err.toString());
    }

    return e;
  }

  private Venda getVenda(int idVenda) {
    Venda v = new Venda();
    VendaDAO vdao = new VendaDAO();

    try {
      v = vdao.getVenda(idVenda);
    } catch (SQLException e) {
      System.out.println(e.toString());
    }

    return v;
  }

  private List<Produto> getProdutos(Venda v) {
    List<Produto> produtos = new ArrayList<Produto>();
    List<Document> documentos = new ArrayList<Document>();
    VendaDAO vdao = new VendaDAO();

    try {
      produtos = vdao.getProdutos(v.getId());
    } catch (SQLException e) {
      System.out.println(e.toString());
    }

    return produtos;
  }
}