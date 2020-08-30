package servlets;

import daos.UsuarioDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Usuario;
import utils.ServletCRUD;

@WebServlet("/usuarios")
public class ServletUsuario extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
    HttpSession session = req.getSession();
    Usuario u =  (Usuario) session.getAttribute("usuario");

    if (u.isAdmin() == false && Integer.parseInt(req.getParameter("id")) != u.getId()) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (req.getParameter("id") != null) {
      crud.show(req, resp);
    } else {
      crud.index(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
    crud.create(req, resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
    HttpSession session = req.getSession();
    Usuario u =  (Usuario) session.getAttribute("usuario");

    if (u.isAdmin()) {
      crud.destroy(req, resp);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
    HttpSession session = req.getSession();
    Usuario u =  (Usuario) session.getAttribute("usuario");

    if (u.isAdmin() || Integer.parseInt(req.getParameter("id")) == u.getId()) {
      crud.update(req, resp);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}