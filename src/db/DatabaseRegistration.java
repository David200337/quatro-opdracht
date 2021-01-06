package src.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.domain.Registration;

public class DatabaseRegistration extends Database {
    //Student, Course, Registration & Certificate
    private ObservableList<Registration> registrationList;
    
    public DatabaseRegistration(String connectionUrl) {
        super(connectionUrl);
        this.registrationList = FXCollections.observableArrayList();
	}
    
    public void loadCourseModules() {
        try {
            connect();

            /*SELECT Registration.CourseId, Registration.CertificateId, Registration.StudentId, RegistrationDate
                FROM Registration
                INNER JOIN Student ON Registration.StudentId = Student.StudentId
                INNER JOIN Course ON Registration.CourseId = Course.CourseId
                INNER JOIN Certificate ON Registration.CertificateId = Certificate.CertificateId*/
            String sql = "";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                

            }
}
