package src.db;

import java.sql.SQLException;

import javafx.collections.ObservableList;

public class FillComboBox extends Database {
    public FillComboBox(String connectionUrl) {
    super(connectionUrl);
  }

  public void fillListFromDataBaseString(
      ObservableList<String> list,
      String column1,
      String table
    ) throws Exception {
      String sql = "SELECT " +column1+ " FROM " + table;
      try {
        connect();

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
          list.add(resultSet.getString(column1));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    public void fillListFromDataBaseInteger(
      ObservableList<Integer> list,
      String column,
      String table
    ) throws Exception {
      String sql = "SELECT " + column + " FROM " + table;
      try {
        connect();

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
          list.add(resultSet.getInt(column));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
