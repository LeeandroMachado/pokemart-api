package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.ProdutoDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Produto;
import models.Usuario;
import utils.ServletCRUD;

@WebServlet("/produtos")
public class ServletProduto extends ServletPermissoes {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Produto, ProdutoDAO> crud = new ServletCRUD<Produto, ProdutoDAO>(Produto.class, new ProdutoDAO());
    Usuario u = usuarioLogado(req);
    String id = req.getParameter("id");

    if (!u.isAdmin()) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (id != null) {
      crud.show(req, resp, Integer.parseInt(id));
    } else {
      crud.index(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Produto, ProdutoDAO> crud = new ServletCRUD<Produto, ProdutoDAO>(Produto.class, new ProdutoDAO());
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Produto p = parser.fromJson(req.getReader(), Produto.class);

    if (u.isAdmin()) {
      crud.create(req, resp, p);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Produto, ProdutoDAO> crud = new ServletCRUD<Produto, ProdutoDAO>(Produto.class, new ProdutoDAO());
    Usuario u = usuarioLogado(req);

    if (u.isAdmin()) {
      crud.destroy(req, resp);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Produto, ProdutoDAO> crud = new ServletCRUD<Produto, ProdutoDAO>(Produto.class, new ProdutoDAO());
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Produto p = parser.fromJson(req.getReader(), Produto.class);
    String id = req.getParameter("id");

    if (u.isAdmin()) {
      crud.update(req, resp, Integer.parseInt(id), p);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }
}