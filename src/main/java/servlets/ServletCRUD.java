package servlets;

import com.google.gson.Gson;
import daos.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import interfaces.IDao;

public class ServletCRUD<M, D extends IDao<M>> extends HttpServlet {
  protected Class<M> modelo;
  protected Class<D> dao;

  D createContents(Class<D> clazz) throws NoSuchMethodException , InstantiationException , IllegalAccessException , InvocationTargetException {
    return clazz.getConstructor().newInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    PrintWriter output = resp.getWriter();
    Gson parser = new Gson();
    List<M> list = new ArrayList<M>();
    String response = "";

    System.out.println(modelo);
    System.out.println(dao);

    try {
      D odao = createContents(dao);
      System.out.println(odao);
      if (req.getParameter("id") != null) {
        list = odao.listar(Integer.parseInt(req.getParameter("id")));
        response = parser.toJson(list.get(0));
      } else {
          list = odao.listar();
          response = parser.toJson(list);
      }
    }
    catch (NoSuchMethodException e) {

    }
    catch (InvocationTargetException e) {

    }
    catch (InstantiationException e) {
      System.out.println(e.getMessage());
    }
    catch (IllegalAccessException e) {
      System.out.println(e.getMessage());
    }
    catch(SQLException e) {
      System.out.println(e.getMessage());
    }

    output.println(response);
    output.flush();
    output.close();
  }
}