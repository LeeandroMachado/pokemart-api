package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.IDao;
import models.Usuario;

public class ServletCRUD<M, D extends IDao<M>> {
  private D dao;
  private Class<M> model;

  public ServletCRUD(Class<M> model, D dao) {
    this.model = model;
    this.dao = dao;
  }

  public void show(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Gson parser = new Gson();
    M values;
    String response;

    try {
      values = this.dao.listar(Integer.parseInt(req.getParameter("id")));
      response = parser.toJson(values);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    respond(resp, response);
  }

  public void index(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Gson parser = new Gson();
    List<M> list = new ArrayList<M>();
    String response;

    try {
      list = this.dao.listar();
      response = parser.toJson(list);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    respond(resp, response);
  }

  public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    M model = parser.fromJson(req.getReader(), this.model);
    String response;

    try {
      this.dao.cadastrar(model);
      response = parser.toJson(model);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }
    catch (ParseException e) {
      response = e.getMessage();
    }

    respond(resp, response);
  }

  public void destroy(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String response;

    try {
      this.dao.excluir(Integer.parseInt(req.getParameter("id")));
      response = "Usuário excluido com sucesso";
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    respond(resp, response);
  }

  public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Gson parser = new Gson();
    M model = parser.fromJson(req.getReader(), this.model);
    String response = "";

    try {
      this.dao.atualizar(Integer.parseInt(req.getParameter("id")), model);
      response = parser.toJson(model);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }
    catch (ParseException e) {
      response = e.getMessage();
    }

    respond(resp, response);
  }

  private void respond(HttpServletResponse resp, String response) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter output = resp.getWriter();
    output.println(response);
    output.flush();
    output.close();
  }
}