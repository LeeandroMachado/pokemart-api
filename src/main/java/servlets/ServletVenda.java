package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import daos.ProdutoVendaDAO;
import daos.VendaDAO;
import daos.VendaDAOMongo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import models.Usuario;
import models.Venda;
import org.bson.Document;
import org.json.JSONObject;

@WebServlet("/api/v1/vendas")
public class ServletVenda extends ServletPermissoes {
  private final String ID_PARAMETER = "id";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    List<Document> notas = new ArrayList<Document>();
    Document nota = new Document();
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    PrintWriter output = resp.getWriter();
    VendaDAOMongo vdaom = new VendaDAOMongo();
    Usuario u = usuarioLogado(req);

    if (!u.isAdmin()) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (req.getParameter(ID_PARAMETER) == null) {
      notas = vdaom.listar();
      output.println(parser.toJson(notas));
    } else {
      nota = vdaom.listarUm(Integer.parseInt(req.getParameter(ID_PARAMETER)));
      output.println(parser.toJson(nota));
    }

    output.flush();
    output.close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    String response = "Ocorreu um erro interno";
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    PrintWriter output = resp.getWriter();
    JSONObject jsonObject = new JSONObject(getJsonString(req));
    Type produtosType = new TypeToken<ArrayList<Produto>>(){}.getType();
    List<Produto> produtosList = parser.fromJson(jsonObject.getJSONArray("produtos").toString(), produtosType);
    VendaDAO vdao = new VendaDAO();
    VendaDAOMongo vdaom = new VendaDAOMongo();
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

      vdaom.cadastrar(fkVendaId);

      response = "Sucesso";
    } catch (ParseException e) {
      System.out.println(e.toString());
    } catch (SQLException e) {
      System.out.println(e.toString());
    }

    output.println(parser.toJson(response));
    output.flush();
    output.close();
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