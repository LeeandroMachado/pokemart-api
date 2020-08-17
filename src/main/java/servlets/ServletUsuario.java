package servlets;

import com.google.gson.Gson;
import daos.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Usuario;
import utils.ServletCRUD;

public class ServletUsuario extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());

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
    crud.destroy(req, resp);
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Usuario, UsuarioDAO> crud = new ServletCRUD<Usuario, UsuarioDAO>(Usuario.class, new UsuarioDAO());
    crud.update(req, resp);
  }
}