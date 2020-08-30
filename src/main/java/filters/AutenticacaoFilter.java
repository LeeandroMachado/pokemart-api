package filters;

import daos.UsuarioDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Usuario;
import org.apache.commons.lang3.ArrayUtils;
import utils.JWT;

public class AutenticacaoFilter implements Filter {
  protected String[] getPrivateMethods() {
    return new String[]{};
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    String method = httpServletRequest.getMethod();

    if (ArrayUtils.contains(this.getPrivateMethods(), method)) {
      try {
        String token = httpServletRequest.getHeader("authorization").replace("Bearer ", "");
        Claims claim = JWT.decodeJWT(token);

        if(claim.getSubject() != null) {
          HttpSession session = httpServletRequest.getSession();
          UsuarioDAO udao = new UsuarioDAO();
          Usuario u = udao.listar(Integer.parseInt(claim.getId()));
          session.setAttribute("usuario", u);

          chain.doFilter(request, response);

          session.invalidate();
        }
      } catch (ExpiredJwtException e) {
        System.out.println(e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      } catch (SignatureException e) {
        System.out.println(e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
    } else {
      chain.doFilter(request, response);
    }
  }
}