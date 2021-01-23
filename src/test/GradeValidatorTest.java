package src.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import src.validators.GradeValidator;

/**
     * @desc Validates if grade is valid. It should be in the form of:
     *       0 > grade <= 10
     * 
     * @subcontract negative grade {
     *   @requires grade < 0;
     *   @ensures \result = false;
     * }
     * 
     * 
     * @subcontract grade is 0 {
     *   @requires grade = 0
     *   @ensures \result = false;
     * }
     * 
     * @subcontract valid grade {
     *   @requires no other precondition
     *   @ensures \result = true;
     * }
     * 
     * @subcontract grade bigger than 10 {
     *   @requires grade > 10
     *   @ensures \result = false;
     * }
     * 
     */
public class GradeValidatorTest {
    
    @Test
    public void testIsValidRequiresGradeMinusOnePointZeroEnsuresFalse(){
         // Arrange
         double grade = -1.0;

         // Act
         Boolean result = GradeValidator.isValid(grade);
 
         // Assert
         assertEquals(false, result);
     
    }

    @Test
    public void testIsValidRequiresGradeZeroPointZeroEnsuresFalse(){
         // Arrange
         double grade = 0.0;

         // Act
         Boolean result = GradeValidator.isValid(grade);
 
         // Assert
         assertEquals(false, result);
     
    }

    @Test
    public void testIsValidRequiresGradeSixPointZeroEnsuresTrue(){
         // Arrange
         double grade = 6.0;

         // Act
         Boolean result = GradeValidator.isValid(grade);
 
         // Assert
         assertEquals(true, result);
     
    }

    @Test
    public void testIsValidRequiresGradeElevenPointZeroEnsuresFalse(){
         // Arrange
         double grade = 11.0;

         // Act
         Boolean result = GradeValidator.isValid(grade);
 
         // Assert
         assertEquals(false, result);
     
    }
}
