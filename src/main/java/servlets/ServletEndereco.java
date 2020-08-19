package servlets;

import com.google.gson.Gson;
import daos.EnderecoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Endereco;
import utils.ServletCRUD;

public class ServletEndereco extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(Endereco.class, new EnderecoDAO());

    if (req.getParameter("id") != null) {
      crud.show(req, resp);
    } else {
      crud.index(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(Endereco.class, new EnderecoDAO());
    crud.create(req, resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(Endereco.class, new EnderecoDAO());
    crud.destroy(req, resp);
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<Endereco, EnderecoDAO> crud = new ServletCRUD<Endereco, EnderecoDAO>(Endereco.class, new EnderecoDAO());
    crud.update(req, resp);
  }
}