package src.validators;

public class GradeValidator {
    //Check if the given grade is valid
    public static boolean isValid(double grade){
        if(grade > 0 && grade <= 10){
            return true;
        } else{
            return false;
        }
    }
}
