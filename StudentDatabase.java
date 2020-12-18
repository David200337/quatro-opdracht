import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager ;

public class StudentDatabase{
    // Connection beheert informatie over de connectie met de database.
    Connection con = null;

    public StudentDatabase(String connectionUrl) throws SQLException, ClassNotFoundException{
        // Dit zijn de instellingen voor de verbinding. Vervang de databaseName indien deze voor jou anders is.
        // String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;";
        // 'Importeer' de driver die je gedownload hebt.
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // Maak de verbinding met de database.
        con = DriverManager.getConnection(connectionUrl);
    }


    public List<Student> getStudents() {
        List<Student> listStudents = new ArrayList<>();
        try (
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Student");
        ){

            while(rs.next()){
                int studentId = rs.getInt("StudentId");
                String name = rs.getString("StudentName");
                String email = rs.getString("StudentEmail");
                String dateOfBirth = rs.getString("DateOfBirth");
                String gender = rs.getString("Gender");
                String address = rs.getString("Address");
                String postalCode = rs.getString("PostalCode");
                String city = rs.getString("City");
                String country = rs.getString("Country");
                Student student = new Student(studentId, name, email, dateOfBirth, gender, address, postalCode, city, country);
                listStudents.add(student);
            }
            
        } catch(SQLException ex){
        }
        return listStudents;
    }
}
