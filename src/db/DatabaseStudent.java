package src.db;

import java.sql.Date;
import java.sql.SQLException;

import javafx.collections.*;
import src.domain.Student;

public class DatabaseStudent extends Database {
    private ObservableList<Student> students;
    private int numberOfFemales;
    private int numberOfMales;
    
    
    public DatabaseStudent(String connectionUrl) {
        super(connectionUrl);
        this.students = FXCollections.observableArrayList();
    }

    //Load all the students
    public void loadStudents() {
        try {
            connect();

            // Define SQL query
            String sql = "SELECT * FROM Student";
            statement = connection.createStatement();

            // Run query
            resultSet = statement.executeQuery(sql);

            // Save data
            while (resultSet.next()) {
                // Store attributes
                int studentId = resultSet.getInt("StudentId");
                String name = resultSet.getString("StudentName");
                String email = resultSet.getString("StudentEmail");
                Date dateOfBirth = resultSet.getDate("DateOfBirth");
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
    }

    //Return the list of students
    public ObservableList<Student> getStudents() {
        return students;
    }

    //Insert a student
    public void insertStudent(String name, String email, Date dateOfBirth, String gender, String address, String postalCode, String city, String country) throws SQLException{
       statement.executeUpdate("INSERT INTO Student(StudentName, StudentEmail, DateOfBirth, Gender, Address, PostalCode, City, Country) VALUES ('"+name+"','"+email+"','"+dateOfBirth+"','"+gender+"','"+address+"','"+postalCode+"','"+city+"','"+country+"')");
    }

    //Delete a student
    public void deleteStudent(Student selectedItem) throws SQLException{
        statement.executeUpdate("DELETE FROM Student WHERE StudentId = '"+selectedItem.getStudentId()+"'");
    }

    //Update a given column in the Student table
    public void updateStudentString(String column, String newValue, int id){
        try{
            statement.executeUpdate("UPDATE Student SET "+column+" = '"+newValue+"' WHERE StudentId = "+id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
 
    //Return the number of students with the gender Female    
    public int getNumberOfFemales() throws Exception {
        connect();

        String sql = "SELECT COUNT(*) AS NumberOfFemales FROM Student WHERE Gender = 'Female'";
        statement = connection.createStatement();

        // Run query
        resultSet = statement.executeQuery(sql);

        // Save data
        while (resultSet.next()) {
            numberOfFemales = resultSet.getInt("NumberOfFemales");
        }

        return numberOfFemales;
    }

    //Return the number of students with the gender Female  
    public int getNumberOfMales() throws Exception {
        connect();

        String sql = "SELECT COUNT(*) AS NumberOfMales FROM Student WHERE Gender = 'Male'";
        statement = connection.createStatement();

        // Run query
        resultSet = statement.executeQuery(sql);

        // Save data
        while (resultSet.next()) {
            numberOfMales = resultSet.getInt("NumberOfMales");
        }

        return numberOfMales;
    }
}
