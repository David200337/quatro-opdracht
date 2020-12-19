import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseStudent extends Database {
    private ArrayList<Student> students;
    private ObservableList<Student> data = FXCollections.observableArrayList();;
    
    public DatabaseStudent(String connectionUrl) {
        super(connectionUrl);
        this.students = new ArrayList<>();
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void loadStudents() {
        try {
            connect();

            // Define SQL query
            String SQL = "SELECT * FROM Student";
            statement = connection.createStatement();

            // Run query
            resultSet = statement.executeQuery(SQL);

            // Save data
            while (resultSet.next()) {
                // Store attributes
                int studentId = resultSet.getInt("StudentId");
                String name = resultSet.getString("StudentName");
                String email = resultSet.getString("StudentEmail");
                String dateOfBirth = resultSet.getString("DateOfBirth");
                String gender = resultSet.getString("Gender");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("PostalCode");
                String city = resultSet.getString("City");
                String country = resultSet.getString("Country");

                // Add Student object to students arraylist
                students.add(new Student(studentId, name, email, dateOfBirth, gender, address, postalCode, city, country));
            }
            
        } catch (Exception e) {
            System.out.println("ERROR:\n\n" + e);
        } finally {
            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
            if (statement != null) try { statement.close(); } catch(Exception e) {}
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }
    }

    public List<Student> getStudents() {
        // Return students array
        return students;
    }
}
