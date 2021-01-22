package src.db;

import java.sql.Date;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.domain.Registration;

public class DatabaseRegistration extends Database {
    //Student, Course, Registration & Certificate
    private ObservableList<Registration> completedRegistrationList;
    private ObservableList<Registration> registrationList;
    private int numberOfCertificates;
    private int numberOfNoneCertificates;
    
    public DatabaseRegistration(String connectionUrl) {
        super(connectionUrl);
        this.completedRegistrationList = FXCollections.observableArrayList();
        this.registrationList = FXCollections.observableArrayList();
	}
    
    public void loadCompletedRegistrations(int studentId) {
        try {
            connect();

            String sql = "SELECT Course.CourseName, Registration.CourseId, Registration.CertificateId, Registration.RegistrationDate, Certificate.StaffName, Certificate.Grade FROM Registration INNER JOIN Course ON Registration.CourseId = Course.CourseId INNER JOIN Certificate ON Registration.CertificateId = Certificate.CertificateId WHERE Registration.StudentId = '"+studentId+"';";
            // INNER JOIN Student ON Registration.StudentId = Student.StudentId INNER JOIN Course ON Registration.CourseId = Course.CourseId INNER JOIN Certificate ON Registration.CertificateId = Certificate.CertificateId";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                String courseName = resultSet.getString("CourseName");
                int courseId = resultSet.getInt("CourseId");
                int certificateId = resultSet.getInt("CertificateId");
                Date registrationDate = resultSet.getDate("RegistrationDate");

                String staffName = resultSet.getString("StaffName");
                double grade = resultSet.getDouble("Grade");

                Registration registration = new Registration(registrationDate, courseId, studentId, certificateId, courseName, staffName, grade);
                completedRegistrationList.add(registration);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    } 

    public ObservableList<Registration> getCompletedRegistrations(){
            return completedRegistrationList;
    }

     public void loadRegistrations(int studentId){
            try{
                connect();

                String sql = "SELECT Course.CourseName, Registration.CourseId, Registration.CertificateId, Registration.RegistrationDate FROM Registration INNER JOIN Course ON Registration.CourseId = Course.CourseId WHERE CertificateId IS NULL AND Registration.StudentId = '"+studentId+"';";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                while(resultSet.next()){
                    String courseName = resultSet.getString("CourseName");
                    int courseId = resultSet.getInt("CourseId");
                    
                    int certificateId = resultSet.getInt("CertificateId");
                    Date registrationDate = resultSet.getDate("RegistrationDate");
                    

                    Registration registration = new Registration(registrationDate, courseId, studentId, certificateId, courseName);
                    registrationList.add(registration);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
    }

    public ObservableList<Registration> getRegistrations(){
        return registrationList;
    }

        

        public void deleteRegistration(Registration selectedItem) throws SQLException{
            statement.executeUpdate("DELETE FROM Registration WHERE CourseId = '"+selectedItem.getCourseId()+"'");
        }



        public void insertRegistration(int studentId, String courseName, Date registrationDate) throws SQLException{
            statement.executeUpdate("INSERT INTO Registration(StudentId, CourseId, RegistrationDate, CertificateId) VALUES ('"+studentId+"', (SELECT CourseId FROM Course WHERE CourseName = '"+courseName+"'),'"+registrationDate+"', NULL)");
        }

        public int getNumberOfCertificatesPerGender(String gender) throws Exception {
            connect();

            String sql = "(SELECT COUNT(*) AS NumberOfCertificates FROM Registration INNER JOIN Student ON Registration.StudentId = Student.StudentId WHERE Student.Gender = '"+gender+"' AND Registration.CertificateId IS NOT NULL) ";
            statement = connection.createStatement();

            // Run query
            resultSet = statement.executeQuery(sql);

            // Save data
            while (resultSet.next()) {
                numberOfCertificates = resultSet.getInt("NumberOfCertificates");
            }

            return numberOfCertificates;
        }

        public int getNumberOfNoneCertificatesPerGender(String gender) throws Exception {
            connect();

            String sql = "(SELECT COUNT(*) AS NumberOfNoneCertificates FROM Registration INNER JOIN Student ON Registration.StudentId = Student.StudentId WHERE Student.Gender = '"+gender+"' AND Registration.CertificateId IS NULL) ";
            statement = connection.createStatement();

            // Run query
            resultSet = statement.executeQuery(sql);

            // Save data
            while (resultSet.next()) {
                numberOfNoneCertificates = resultSet.getInt("NumberOfNoneCertificates");
            }

            return numberOfNoneCertificates;
        }

       
}
