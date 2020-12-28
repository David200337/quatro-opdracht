package src.db;

import java.sql.Date;
import java.sql.SQLException;


import javafx.collections.*;
// import src.domain.DatePickerConverter;
import src.domain.Student;

public class DatabaseStudent extends Database {
    private ObservableList<Student> students;
    
    
    public DatabaseStudent(String connectionUrl) {
        super(connectionUrl);
        this.students = FXCollections.observableArrayList();
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
        } 
        //finally {
        //     if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
        //     if (statement != null) try { statement.close(); } catch(Exception e) {}
        //     if (connection != null) try { connection.close(); } catch(Exception e) {}
        // }
    }

    public ObservableList<Student> getStudents() {
        // Return students array
        return students;
    }

    public void insertStudent(int id, String name, String email, Date dateOfBirth, String gender, String address, String postalCode, String city, String country) throws SQLException{
       statement.executeUpdate("INSERT INTO Student(StudentId, StudentName, StudentEmail, DateOfBirth, Gender, Address, PostalCode, City, Country) VALUES ('"+id+"','"+name+"','"+email+"','"+dateOfBirth+"','"+gender+"','"+address+"','"+postalCode+"','"+city+"','"+country+"')");
    }

    public void deleteStudent(Student selectedItem) throws SQLException{
        statement.executeUpdate("DELETE FROM Student WHERE StudentId = '"+selectedItem.getStudentId()+"'");
    }
}
