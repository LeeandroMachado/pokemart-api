package daos;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
import static com.mongodb.client.model.Filters.eq;

public class VendaDAOMongo {
  private final String COLLECTION_NAME = "compras";
  private ConexaoMongo cm;
  private MongoCollection collection;

  public VendaDAOMongo() {
    this.cm = new ConexaoMongo();
    this.collection = this.cm.getMongoDatabase().getCollection(COLLECTION_NAME);
  }

  public List<Document> listar() {
    FindIterable<Document> result = this.collection.find();
    MongoCursor<Document> cursor = result.iterator();
    List<Document> list = new ArrayList<Document>();

    while (cursor.hasNext()) {
      Document document = cursor.next();
      list.add(document);
    }

    return list;
  }

  public Document listarUm(int id) {
    FindIterable<Document> result = this.collection.find(eq("compra_id", id));
    MongoCursor<Document> cursor = result.iterator();
    List<Document> list = new ArrayList<Document>();

    while (cursor.hasNext()) {
      Document document = cursor.next();
      list.add(document);
    }

    return list.get(0);
  }

  public void cadastrar(int idVenda) {
    VendaDAO vdao = new VendaDAO();

    try {
      Venda v = vdao.getVenda(idVenda);
      Endereco e = vdao.getEndereco(idVenda);
      List<Produto> produtos = vdao.getProdutos(idVenda);
      Usuario u = vdao.getCliente(idVenda);

      this.collection.insertOne(buildar(v, e, produtos, u));
    } catch (SQLException err) {
      System.out.println(err.toString());
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
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

    compra.append("compra_id", u.getId());
    compra.append("cliente_nome", u.getNome());
    compra.append("endereco_entrega", e.getRua() + ", Nº" + e.getNum() + " - " + e.getBairro() + " - " + e.getCidade() + " - " + e.getEstado());
    compra.append("valor_total", v.getValorTotal());
    compra.append("data_compra", v.getDataVenda());
    compra.append("produtos", produtos);
    compra.append("pagamento", pagamento);

    return compra;
  }
}