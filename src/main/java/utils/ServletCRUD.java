package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfaces.IDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletCRUD<M, D extends IDao<M>> {
  private D dao;
  private Class<M> model;
  private HttpServletRequest req;
  private HttpServletResponse resp;

  public ServletCRUD(Class<M> model, D dao, HttpServletRequest req, HttpServletResponse resp) {
    this.model = model;
    this.dao = dao;
    this.req = req;
    this.resp = resp;
  }

  public void show(int id) throws IOException {
    Gson parser = new Gson();
    M values;
    String response;

    try {
      values = this.dao.listarUm(id);
      response = parser.toJson(values);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    respond(response);
  }

  public void index() throws IOException {
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

    respond(response);
  }

  public void index(int id) throws IOException {
    Gson parser = new Gson();
    List<M> list = new ArrayList<M>();
    String response;

    try {
      list = this.dao.listar(id);
      response = parser.toJson(list);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    respond(response);
  }

  public void create(M model) throws IOException {
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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

    respond(response);
  }

  public void destroy(int id) throws IOException {
    String response;

    try {
      this.dao.excluir(id);
      response = "Sucesso";
    }
    catch (SQLException e) {
      response = e.getMessage();
    }

    respond(response);
  }

  public void update(int id, M model) throws IOException {
    Gson parser = new Gson();
    String response = "";

    try {
      this.dao.atualizar(id, model);
      response = parser.toJson(model);
    }
    catch (SQLException e) {
      response = e.getMessage();
    }
    catch (ParseException e) {
      response = e.getMessage();
    }

    respond(response);
  }

  private void respond(String response) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter output = resp.getWriter();
    output.println(response);
    output.flush();
    output.close();
  }
}