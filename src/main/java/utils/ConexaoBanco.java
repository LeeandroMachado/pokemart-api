package utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoBanco {
  private Connection conexao = null;
  private final String BANCO = "pokemartdb";
  private final String LOGIN = "root";
  private final String SENHA = "";
  private final String HOST = "127.0.0.1";

  public Connection getConnection() {
    try {
      MysqlDataSource ds = new MysqlDataSource();

      ds.setServerName(HOST);
      ds.setDatabaseName(BANCO);
      ds.setUser(LOGIN);
      ds.setPassword(SENHA);

      ds.setConnectTimeout(2000);

      conexao = ds.getConnection();
    }
    catch (SQLException sqlerror) {
      System.out.println("FALHA NA CONEXÃO: " + sqlerror.getMessage());
    }

    return conexao;
  }
}