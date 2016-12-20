package UnitTests;

import ControllerTestsLogic.TableControllerTestLogic;
import controllers.TableController;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TableControllerTests {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testGetTablesByRestaurantIdAndShiftAndDate() {
        TableController tableController = new TableController();
        
        exception.expect(RuntimeException.class);
        tableController.getTablesByRestaurantIdAndShiftAndDate(1, "1", "2016, 11, 27");
    }
    
    @Test
    public void getTablesByRestaurantIdAndShiftAndDateTest() throws Exception {
        TableControllerTestLogic testLogic = new TableControllerTestLogic();
        
        assertTrue(testLogic.getTablesByRestaurantIdAndShiftAndDateResult(0, 1, "0"));
        assertTrue(testLogic.getTablesByRestaurantIdAndShiftAndDateResult(0, 1, "2017-01-01"));
        assertTrue(testLogic.getTablesByRestaurantIdAndShiftAndDateResult(1, 1, "0"));
        assertTrue(testLogic.getTablesByRestaurantIdAndShiftAndDateResult(1, 1, "2017-01-01"));
        exception.expect(NullPointerException.class);        
        testLogic.getTablesByRestaurantIdAndShiftAndDateResult(0, 0, "0");
    }
}
