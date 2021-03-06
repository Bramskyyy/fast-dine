package UnitTests;

import ControllerTestsLogic.UserControllerTestLogic;
import Helper.FileReader;
import controllers.UserController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserControllerTests {
    private UserController userController;
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setController() {
        userController = new UserController();
    }
    
    @Test
    public void testNewUser() {
        String customerName = "test";
        String customerEmail = "test@test.be";
        String customerPhone = "012334556789";
        String customerType = "klant";
        String customerPswd = "123";
              
        assertFalse(userController.newUser(customerName, "", customerPhone, customerType, customerPswd));
        assertFalse(userController.newUser(customerName, customerEmail, customerPhone, "", customerPswd));
        assertFalse(userController.newUser(customerName, customerEmail, customerPhone, customerType, ""));
        
        assertFalse(userController.newUser(customerName, "", customerPhone, customerType, ""));
        assertFalse(userController.newUser(customerName, customerEmail, customerPhone, "", ""));
        assertFalse(userController.newUser(customerName, "", customerPhone, "", customerPswd));
        
        assertFalse(userController.newUser(customerName, "", customerPhone, "", ""));
        
        exception.expect(NullPointerException.class);
        userController.newUser(customerName, customerEmail, customerPhone, customerType, customerPswd);
    }
    
    @Test
    public void testGetUserByEmail() {
        String inexistentEmail = "test@test.be";
        
        assertEquals(userController.getUserByEmail(""), null);
        assertEquals(userController.getUserByEmail("0"), null);
        
        exception.expect(NullPointerException.class);
        userController.getUserByEmail(inexistentEmail);
    }
    
    @Test
    public void getUserByEmailTest() throws Exception {
        UserControllerTestLogic testLogic = new UserControllerTestLogic();
           
        assertTrue(testLogic.getUserByEmailResult("gebruiker1@odisee.be"));       
        exception.expect(RuntimeException.class);
        testLogic.getUserByEmailResult("deze user bestaat niet");
    }
    
    @Test
    public void newUserTest() throws Exception {
        String email = "testgebruiker@student.odisee.be";
        String type = "customer";
        String password = "123";
        
        FileReader reader = new FileReader();

        assertTrue(Boolean.parseBoolean(reader.getValueFromURL("http://localhost:8090/newUser?email=" + email + "&type=" + type + "&password=" + password)));
        assertFalse(Boolean.parseBoolean(reader.getValueFromURL("http://localhost:8090/newUser?email=" + email + "&type=" + type + "&password=" + password)));
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123")) {
                Statement st = con.createStatement();
                String sql = ("DELETE FROM users WHERE email = '" + email + "' AND type = '" + type + "' AND password = '" + password + "';");
                st.executeUpdate(sql);
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw e;
        }
    }
}
