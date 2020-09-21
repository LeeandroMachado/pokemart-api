package servlets;

import daos.AutenticacaoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/v1/login")
public class ServletAutenticacao extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    String response = "Ocorreu um erro interno";
    PrintWriter output = resp.getWriter();

    AutenticacaoDAO auth = new AutenticacaoDAO();

    try {
      response = auth.autenticar(req);
    } catch (SQLException e) {
      System.out.println("ERRO: " + e.getMessage());
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      System.out.println("ERRO: " + e.getMessage());
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    output.println(response);
    output.flush();
    output.close();
  }
}