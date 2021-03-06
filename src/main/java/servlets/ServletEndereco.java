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

@WebServlet("/api/v1/usuarios/enderecos")
public class ServletEndereco extends ServletPermissoes {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(
      Endereco.class, new EnderecoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    int id = u.getId();

    if (req.getParameter("id") != null) {
      crud.show(Integer.parseInt(req.getParameter("id")));
    } else {
      crud.index(id);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(
      Endereco.class, new EnderecoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Endereco e = parser.fromJson(req.getReader(), Endereco.class);

    e.setFkUsuarioId(u.getId());
    crud.create(e);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(
      Endereco.class, new EnderecoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);

    if (u.isAdmin()) {
      crud.destroy(Integer.parseInt(req.getParameter("id")));
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(
      Endereco.class, new EnderecoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Endereco e = parser.fromJson(req.getReader(), Endereco.class);
    String id = req.getParameter("id");

    if (u.isAdmin() || isUserInParams(u, id)) {
      crud.update(e.getId(), e);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }
}