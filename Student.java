import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
    private int studentId;
    private StringProperty name = new SimpleStringProperty(this, "name");
    private StringProperty email = new SimpleStringProperty(this, "email");
    private StringProperty dateOfBirth = new SimpleStringProperty(this, "dateOfBirth");
    private StringProperty gender = new SimpleStringProperty(this, "gender");
    private StringProperty address = new SimpleStringProperty(this, "address");
    private StringProperty postalCode = new SimpleStringProperty(this, "postalCode");
    private StringProperty city = new SimpleStringProperty(this, "city");
    private StringProperty country = new SimpleStringProperty(this, "country");
    
   

    public int getStudentId(){
        return studentId;
    }

    public void setStudentId(int studentId){
        this.studentId = studentId;
    }

    public StringProperty studentNameProperty(){
        return name;
    }

    public String getStudentName(){
        return studentNameProperty().get();
    }

    public void setStudentName(String name){
        studentNameProperty().set(name);
    }

    public StringProperty studentEmailProperty(){
        return email;
    }

    public String getStudentEmail(){
        return studentEmailProperty().get();
    }

    public void setStudentEmail(String email){
        studentEmailProperty().set(email);
    }

    public StringProperty studentDateOfBirthProperty(){
        return dateOfBirth;
    }

    public String getStudentDateOfBirth(){
        return studentDateOfBirthProperty().get();
    }

    public void setStudentDateOfBirth(String dateOfBirth){
        studentDateOfBirthProperty().set(dateOfBirth);
    }

    public StringProperty studentGenderProperty(){
        return gender;
    }

    public String getStudentGender(){
        return studentGenderProperty().get();
    }

    public void setStudentGender(String gender){
        studentGenderProperty().set(gender);
    }

    public StringProperty studentAddressProperty(){
        return address;
    }

    public String getStudentAddress(){
        return studentAddressProperty().get();
    }

    public void setStudentAddress(String address){
        studentAddressProperty().set(address);
    }

    public StringProperty studentPostalCodeProperty(){
        return postalCode;
    }

    public String getStudentPostalCode(){
        return studentPostalCodeProperty().get();
    }

    public void setStudentPostalCode(String postalCode){
        studentPostalCodeProperty().set(postalCode);
    }

    public StringProperty studentCityProperty(){
        return city;
    }

    public String getStudentCity(){
        return studentCityProperty().get();
    }

    public void setStudentCity(String city){
        studentCityProperty().set(city);
    }

    public StringProperty studentCountryProperty(){
        return country;
    }

    public String getStudentCountry(){
        return studentCountryProperty().get();
    }

    public void setStudentCountry(String country){
        studentCountryProperty().set(country);
    }


    public Student(int studentId, String name, String email, String dateOfBirth, String gender, String address, String postalCode, String city, String country ){
        setStudentId(studentId);
        setStudentEmail(email);
        setStudentName(name);
        setStudentAddress(address);
        setStudentCity(city);
        setStudentCountry(country);
        setStudentDateOfBirth(dateOfBirth);
        setStudentGender(gender);
        setStudentPostalCode(postalCode);
    }
}
