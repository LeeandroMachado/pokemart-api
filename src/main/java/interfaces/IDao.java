package interfaces;

import java.text.ParseException;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public interface IDao<M> {
  void cadastrar(M m) throws SQLException, ParseException;
  List<M> listar() throws SQLException;
  M listar(int id) throws SQLException;
  void excluir(int id) throws SQLException;
  void atualizar(int id, M m) throws SQLException, ParseException;
  List<M> buscar(PreparedStatement st) throws SQLException;
}