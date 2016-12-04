
import fastdine.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import sun.net.www.http.HttpClient;

//WERKT NIET... ALLES IS VOOR NIKS... TIME WASTE... SCRUM PROJECT FAILED... UITSCHRIJVEN GRAAG... VOLGENDE KEER BETER...

public class ControllerTests {
   @Test
   public void getterTest()
   {
       try {

            URL url = new URL("http://localhost:8080/RESTfulExample/json/product/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                    System.out.println(output);
            }

            conn.disconnect();

      } catch (MalformedURLException e) {
            e.printStackTrace();
      } catch (IOException e) {
            e.printStackTrace();
      }
   }
    
//    private UserController uc;
//    
//    @Before
//    public void setUp ()
//    {
//        uc = new UserController();
//    }    
//    
//    @Test
//    public void testGetUserPasswordByEmailEmptyString ()
//    {
//        String emptyVal = uc.getUserPasswordByEmail("");
//        assertEquals(null, emptyVal);
//    }
//    
//    @Test
//    public void testGetUserPasswordByEmailRandomString ()
//    {
//        String randomVal = uc.getUserPasswordByEmail("azertyuiopqsdfghjklmwxcvbn");
//        assertEquals(null, randomVal);
//    }
//    
//    @Test
//    public void testGetUserPasswordByEmailExistingString () {
//        String existingVal = uc.getUserPasswordByEmail("gebruiker1@odisee.be");
//        assertEquals("Azerty1", existingVal);
//    }
}
