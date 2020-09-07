package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.ImagemProdutoDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ImagemProduto;
import models.Usuario;
import utils.ServletCRUD;

@WebServlet("/produtos/imagens")
public class ServletImagemProduto extends ServletPermissoes {
  private String NOME_PARAMETRO_ID = "id";
  private String NOME_PARAMETRO_FK = "fkProdutoId";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<ImagemProduto, ImagemProdutoDAO> crud = new ServletCRUD<ImagemProduto, ImagemProdutoDAO>(
      ImagemProduto.class, new ImagemProdutoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    String id = req.getParameter(NOME_PARAMETRO_ID);
    String fk = req.getParameter(NOME_PARAMETRO_FK);

    if (u.isAdmin() && fk != null) {
      crud.index(Integer.parseInt(fk));
    } else if (u.isAdmin() && id != null) {
      crud.show(Integer.parseInt(id));
    } else if (u.isAdmin()) {
      crud.index();
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<ImagemProduto, ImagemProdutoDAO> crud = new ServletCRUD<ImagemProduto, ImagemProdutoDAO>(
      ImagemProduto.class, new ImagemProdutoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    ImagemProduto ip = parser.fromJson(req.getReader(), ImagemProduto.class);

    if (u.isAdmin()) {
      crud.create(ip);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<ImagemProduto, ImagemProdutoDAO> crud = new ServletCRUD<ImagemProduto, ImagemProdutoDAO>(
      ImagemProduto.class, new ImagemProdutoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);

    if (u.isAdmin()) {
      crud.destroy(Integer.parseInt(req.getParameter(NOME_PARAMETRO_ID)));
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<ImagemProduto, ImagemProdutoDAO> crud = new ServletCRUD<ImagemProduto, ImagemProdutoDAO>(
      ImagemProduto.class, new ImagemProdutoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    ImagemProduto ip = parser.fromJson(req.getReader(), ImagemProduto.class);
    String id = req.getParameter(NOME_PARAMETRO_ID);

    if (u.isAdmin()) {
      crud.update(Integer.parseInt(id), ip);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }
}