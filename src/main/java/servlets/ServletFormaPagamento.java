package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.FormaPagamentoDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.FormaPagamento;
import models.Usuario;
import utils.ServletCRUD;

@WebServlet("/formas_pagamento")
public class ServletFormaPagamento extends ServletPermissoes {
  private String NOME_PARAMETRO_ID = "id";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<FormaPagamento, FormaPagamentoDAO> crud = new ServletCRUD<FormaPagamento, FormaPagamentoDAO>(
      FormaPagamento.class, new FormaPagamentoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    String id = req.getParameter(NOME_PARAMETRO_ID);

    if (!u.isAdmin()) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (id != null) {
      crud.show(Integer.parseInt(id));
    } else {
      crud.index();
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<FormaPagamento, FormaPagamentoDAO> crud = new ServletCRUD<FormaPagamento, FormaPagamentoDAO>(
      FormaPagamento.class, new FormaPagamentoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    FormaPagamento p = parser.fromJson(req.getReader(), FormaPagamento.class);

    if (u.isAdmin()) {
      crud.create(p);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<FormaPagamento, FormaPagamentoDAO> crud = new ServletCRUD<FormaPagamento, FormaPagamentoDAO>(
      FormaPagamento.class, new FormaPagamentoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);

    if (u.isAdmin()) {
      crud.destroy(Integer.parseInt(req.getParameter(NOME_PARAMETRO_ID)));
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ServletCRUD<FormaPagamento, FormaPagamentoDAO> crud = new ServletCRUD<FormaPagamento, FormaPagamentoDAO>(
      FormaPagamento.class, new FormaPagamentoDAO(), req, resp
    );
    Usuario u = usuarioLogado(req);
    Gson parser = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    FormaPagamento p = parser.fromJson(req.getReader(), FormaPagamento.class);
    String id = req.getParameter(NOME_PARAMETRO_ID);

    if (u.isAdmin()) {
      crud.update(Integer.parseInt(id), p);
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }
}