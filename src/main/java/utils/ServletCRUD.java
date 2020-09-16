package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfaces.ICrud;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletCRUD<M, D extends ICrud<M>> {
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
    int status = HttpServletResponse.SC_OK;

    try {
      values = this.dao.listarUm(id);
      response = parser.toJson(values);
    }
    catch (SQLException e) {
      response = e.getMessage();
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    } catch (IndexOutOfBoundsException e) {
      response = this.model.getName() + " não cadastrado";
      status = HttpServletResponse.SC_NO_CONTENT;
    }

    respond(response, status);
  }

  public void index() throws IOException {
    Gson parser = new Gson();
    List<M> list = new ArrayList<M>();
    String response;
    int status = HttpServletResponse.SC_OK;

    try {
      list = this.dao.listar();
      response = parser.toJson(list);
    }
    catch (SQLException e) {
      response = e.getMessage();
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    respond(response, status);
  }

  public void index(int id) throws IOException {
    Gson parser = new Gson();
    List<M> list = new ArrayList<M>();
    String response;
    int status = HttpServletResponse.SC_OK;

    try {
      list = this.dao.listar(id);
      response = parser.toJson(list);
    }
    catch (SQLException e) {
      response = e.getMessage();
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    respond(response, status);
  }

  public void create(M model) throws IOException {
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    String response;
    int status = HttpServletResponse.SC_CREATED;

    try {
      this.dao.cadastrar(model);
      response = parser.toJson(model);
    }
    catch (SQLException e) {
      response = e.getMessage();
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
    catch (ParseException e) {
      response = e.getMessage();
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    respond(response, status);
  }

  public void destroy(int id) throws IOException {
    String response;
    int status = HttpServletResponse.SC_OK;

    try {
      this.dao.excluir(id);
      response = "Sucesso";
    }
    catch (SQLException e) {
      response = e.getMessage();
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    respond(response, status);
  }

  public void update(int id, M model) throws IOException {
    Gson parser = new Gson();
    String response = "";
    int status = HttpServletResponse.SC_OK;

    try {
      this.dao.atualizar(id, model);
      response = parser.toJson(model);
    }
    catch (SQLException e) {
      response = e.getMessage();
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
    catch (ParseException e) {
      response = e.getMessage();
      status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    respond(response, status);
  }

  private void respond(String response, int status) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(status);

    PrintWriter output = resp.getWriter();
    output.println(response);
    output.flush();
    output.close();
  }
}