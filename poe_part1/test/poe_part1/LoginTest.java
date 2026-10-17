
package poe_part1;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {
    
      // Test 1: Username is correctly formatted
    @Test
    public void testCheckUserNameCorrect() {

        Login user = new Login("Naomi", "Phiri");

        user.setUsername("kyl_1");

        assertEquals(true, user.checkUserName());
    }

    // Test 2: Username is incorrectly formatted
    @Test
    public void testCheckUserNameIncorrect() {

        Login user = new Login("Naomi", "Phiri");

        user.setUsername("kyle!!!!!!!");

        assertEquals(false, user.checkUserName());
    }
    
     // Test 3: Password meets the complexity requirements
    @Test
    public void testCheckPasswordComplexityCorrect() {

        Login user = new Login("Naomi", "Phiri");

        user.setPassword("Ch&&sec@ke99");

        assertEquals(true, user.checkPasswordComplexity());
    }

    // Test 4: Password does not meet the complexity requirements
    @Test
    public void testCheckPasswordComplexityIncorrect() {

        Login user = new Login("Naomi", "Phiri");

        user.setPassword("password");

        assertEquals(false, user.checkPasswordComplexity());
    }
     // Test 5: Cell phone number is correctly formatted
    @Test
    public void testCheckCellPhoneNumberCorrect() {

        Login user = new Login("Naomi", "Phiri");

        user.setCellPhoneNumber("+27821234567");

        assertEquals(true, user.checkCellPhoneNumber());
    }

    // Test 6: Cell phone number is incorrectly formatted
    @Test
    public void testCheckCellPhoneNumberIncorrect() {

        Login user = new Login("Naomi", "Phiri");

        user.setCellPhoneNumber("0821234567");

        assertEquals(false, user.checkCellPhoneNumber());
    }
     // Test 7: Login details are correct
    @Test
    public void testLoginUserCorrect() {

        Login user = new Login("Naomi", "Phiri");

        user.setUsername("im_1");
        user.setPassword("Password1!");

        user.setLoginDetails("im_1", "Password1!");

        assertEquals(true, user.loginUser());
    }

    // Test 8: Login details are incorrect
    @Test
    public void testLoginUserIncorrect() {

        Login user = new Login("Naomi", "Phiri");

        user.setUsername("im_1");
        user.setPassword("Password1!");

        user.setLoginDetails("wrong", "wrong123!");

        assertEquals(false, user.loginUser());
    }
    
   
}
