
import fastdine.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import sun.net.www.http.HttpClient;

//WERKT NIET... ALLES IS VOOR NIKS... TIME WASTE... SCRUM PROJECT FAILED... UITSCHRIJVEN GRAAG... VOLGENDE KEER BETER...

public class ControllerTests {
    private List<String> getValueFromURL(String urlRequest)
    {
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
             
            List<String> outputValues = new ArrayList<String>();
            String output;
            while ((output = br.readLine()) != null) {
                    outputValues.add(output);
            }

            conn.disconnect();
            return outputValues;
            
        } catch (MalformedURLException e) {
              e.printStackTrace();
        } catch (IOException e) {
              e.printStackTrace();
        }       
       
        return null;
    }
    
    @Test
    public void test1 ()
    {
        List<String> values = getValueFromURL("http://localhost:8080/restaurants");
        for (String value : values) {
            value = value.substring(1,value.length() - 1);
            System.out.println(value);
            String jsonString = value;
            JSONObject jsonObject = new JSONObject(jsonString);
            //TODO verder kijken vanaf hier
            JSONObject newJSON = jsonObject.getJSONObject("stat");
            System.out.println(newJSON.toString());
            jsonObject = new JSONObject(newJSON.toString());
            System.out.println(jsonObject.getString("rcv"));
            System.out.println(jsonObject.getJSONArray("argv"));
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
