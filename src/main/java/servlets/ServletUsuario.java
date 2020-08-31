package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.UsuarioDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Usuario;
import utils.ServletCRUD;

@WebServlet("/usuarios")
public class ServletUsuario extends ServletPermissoes {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
    Usuario u = usuarioLogado(req);
    String id = req.getParameter("id");

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
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Usuario reqUsuario = parser.fromJson(req.getReader(), Usuario.class);

    crud.create(req, resp, reqUsuario);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
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
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Usuario reqUsuario = parser.fromJson(req.getReader(), Usuario.class);
    String id = req.getParameter("id");

    if (u.isAdmin() || isUserInParams(u, id)) {
      crud.update(req, resp, Integer.parseInt(id), reqUsuario);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }
}