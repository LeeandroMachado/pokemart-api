package interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface ICrud<M> {
  void cadastrar(M m) throws SQLException, ParseException;
  List<M> listar() throws SQLException;
  List<M> listar(int id) throws SQLException;
  M listarUm(int id) throws SQLException;
  void excluir(int id) throws SQLException;
  void atualizar(int id, M m) throws SQLException, ParseException;
  List<M> buscar(PreparedStatement st) throws SQLException;
}