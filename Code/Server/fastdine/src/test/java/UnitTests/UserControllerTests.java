package UnitTests;

import ControllerTestsLogic.UserControllerTestLogic;
import fastdine.UserController;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserControllerTests {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testNewUser() {
        UserController uc = new UserController();
        
        int customerId = 1;
        String customerName = "test";
        String customerEmail = "test@test.be";
        String customerPhone = "012334556789";
        String customerType = "klant";
        String customerPswd = "123";
              
        assertFalse(uc.newUser(customerName, "", customerPhone, customerType, customerPswd));
        assertFalse(uc.newUser(customerName, customerEmail, customerPhone, "", customerPswd));
        assertFalse(uc.newUser(customerName, customerEmail, customerPhone, customerType, ""));
        
        assertFalse(uc.newUser(customerName, "", customerPhone, customerType, ""));
        assertFalse(uc.newUser(customerName, customerEmail, customerPhone, "", ""));
        assertFalse(uc.newUser(customerName, "", customerPhone, "", customerPswd));
        
        assertFalse(uc.newUser(customerName, "", customerPhone, "", ""));
        
        assertFalse(uc.newUser(customerName, customerEmail, customerPhone, customerType, customerPswd));
    }
    
    @Test
    public void testGetUserPassword() {
        UserController uc = new UserController();
        String customerEmail = "test@test.be";
        String expectedException = "Exception: null";
        
        assertEquals(uc.getUserPasswordByEmail(""), null);
        assertEquals(uc.getUserPasswordByEmail(customerEmail), expectedException);
        
    }
    
    @Test
    public void getUserPasswordByEmailTest() throws Exception {
        UserControllerTestLogic testLogic = new UserControllerTestLogic();
        
        assertTrue(testLogic.getUserPasswordByEmailResult("1"));
        assertTrue(testLogic.getUserPasswordByEmailResult("gebruiker1@odisee.be"));
        assertTrue(testLogic.getUserPasswordByEmailResult(null));
        exception.expect(NullPointerException.class);
        testLogic.getUserPasswordByEmailResult("");
    }
    
    //TODO - complete test
    @Test
    public void newUserTest() throws Exception {
        String email = "testgebruiker@student.odisee.be";
        String type = "customer";
        String password = "123";

        //assertTrue(Boolean.parseBoolean(getValueFromURL("http://localhost:8080/newUser?email=testgebruiker1&type=customer&password=123")));
        //assertFalse(Boolean.parseBoolean(getValueFromURL("http://localhost:8080/newUser?email=testgebruiker1&type=customer&password=123")));
        
        // Vraag gebruiker op via sql
        // Id != null bool = true else return false;
        // (bool) remove user where id = Id
    }
}
