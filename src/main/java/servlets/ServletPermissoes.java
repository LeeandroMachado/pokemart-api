package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.Usuario;

public class ServletPermissoes extends HttpServlet {
  protected Usuario usuarioLogado(HttpServletRequest req) {
    HttpSession session = req.getSession();
    Usuario u =  (Usuario) session.getAttribute("usuario");

    return u;
  }

  protected boolean isIndex(Usuario u, String id) {
    return u.isAdmin() && id == null;
  }

  protected boolean isUserInParams(Usuario u, String id) {
    if (id == null) {
      return false;
    }

    return u.getId() == Integer.parseInt(id);
  }
}