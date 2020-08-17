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

public class ServletUsuario extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter output = resp.getWriter();
    Gson parser = new Gson();
    UsuarioDAO udao = new UsuarioDAO();
    List<Usuario> list = new ArrayList<Usuario>();
    String response = "";

    try {
      if (req.getParameter("id") != null) {
          list = udao.listar(Integer.parseInt(req.getParameter("id")));
          response = parser.toJson(list.get(0));
      } else {
          list = udao.listar();
          response = parser.toJson(list);
      }
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    output.println(response);
    output.flush();
    output.close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter output = resp.getWriter();
    Gson parser = new Gson();
    UsuarioDAO udao = new UsuarioDAO();
    Usuario u = parser.fromJson(req.getReader(), Usuario.class);
    String response = "";

    try {
      udao.cadastrar(u);
      response = parser.toJson(u);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    output.println(response);
    output.flush();
    output.close();
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter output = resp.getWriter();
    UsuarioDAO udao = new UsuarioDAO();
    String response = "";

    try {
      udao.excluir(Integer.parseInt(req.getParameter("id")));
      response = "Usuário excluido com sucesso";
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    output.println(response);
    output.flush();
    output.close();
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter output = resp.getWriter();
    Gson parser = new Gson();
    UsuarioDAO udao = new UsuarioDAO();
    Usuario u = parser.fromJson(req.getReader(), Usuario.class);
    String response = "";

    try {
      udao.atualizar(Integer.parseInt(req.getParameter("id")), u);
      response = parser.toJson(u);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    output.println(response);
    output.flush();
    output.close();
  }
}