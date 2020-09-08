package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import daos.ProdutoVendaDAO;
import daos.VendaDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Produto;
import models.ProdutoVenda;
import models.Venda;
import org.json.JSONObject;

@WebServlet("/vendas")
public class ServletVenda extends ServletPermissoes {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    JSONObject jsonObject = new JSONObject(getJsonString(req));
    Type produtosType = new TypeToken<ArrayList<Produto>>(){}.getType();
    List<Produto> produtosList = parser.fromJson(jsonObject.getJSONArray("produtos").toString(), produtosType);
    VendaDAO vdao = new VendaDAO();
    ProdutoVendaDAO pvdao = new ProdutoVendaDAO();

    try {
      Venda v = vdao.buildar(usuarioLogado(req), produtosList, jsonObject.getInt("fkFormaPagamentoId"));
      int fkVendaId = vdao.adicionar(v);

      for (Produto produto: produtosList) {
        ProdutoVenda pv = new ProdutoVenda();
        pv.setFkProdutoId(produto.getId());
        pv.setFkVendaId(fkVendaId);
        pvdao.cadastrar(pv);
      }
    } catch (ParseException e) {
      System.out.println(e.toString());
    } catch (SQLException e) {
      System.out.println(e.toString());
    }
  }

  private String getJsonString(HttpServletRequest req) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = req.getReader();
    String str;

    while((str = br.readLine()) != null){
      sb.append(str);
    }

    return sb.toString();
  }
}