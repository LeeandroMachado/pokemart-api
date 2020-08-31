package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.EnderecoDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Endereco;
import models.Usuario;
import utils.ServletCRUD;

@WebServlet("/usuarios/enderecos")
public class ServletEndereco extends ServletPermissoes {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(Endereco.class, new EnderecoDAO());
    Usuario u = usuarioLogado(req);
    String id = req.getParameter("fkUsuarioId");

    if (isIndex(u, id)) {
      crud.index(req, resp);
    } else if (u.isAdmin() || isUserInParams(u, id)) {
      crud.show(req, resp, Integer.parseInt(id));
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(Endereco.class, new EnderecoDAO());
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Endereco e = parser.fromJson(req.getReader(), Endereco.class);
    String id = String.valueOf(e.getFkUsuarioId());

    if (u.isAdmin() || isUserInParams(u, id)) {
      crud.create(req, resp, e);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(Endereco.class, new EnderecoDAO());
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
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(Endereco.class, new EnderecoDAO());
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Endereco e = parser.fromJson(req.getReader(), Endereco.class);
    String id = String.valueOf(e.getFkUsuarioId());

    if (u.isAdmin() || isUserInParams(u, id)) {
      crud.update(req, resp, e.getId(), e);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }
}