package Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileReader {
    public String getValueFromURL(String urlRequest) {
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
             
            String output = br.readLine();

            conn.disconnect();
            return output;
            
        } catch (MalformedURLException e) {
              e.printStackTrace();
        } catch (IOException e) {
              e.printStackTrace();
        }       
        
        return null;
    }
}
