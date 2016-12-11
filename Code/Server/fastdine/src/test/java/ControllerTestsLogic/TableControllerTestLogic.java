package ControllerTestsLogic;

import Helper.FileReader;
import dataEntities.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class TableControllerTestLogic {    
    public boolean getTablesByRestaurantIdAndShiftAndDateResult(int shift, int id, String date) throws Exception {
        List<Table> tablesAPI = new ArrayList<>();
        List<Table> tablesSQL = new ArrayList<>();
      
        FileReader reader = new FileReader();
        
        String value = reader.getValueFromURL("http://localhost:8080/tables?id=" + id + "&shift=" + shift + "&date=" + date);

        JSONArray mJsonArray = new JSONArray(value);
        JSONObject mJsonObject = new JSONObject();

        for (int i = 0; i < mJsonArray.length(); i++) {
            mJsonObject = mJsonArray.getJSONObject(i);
            
            int tableId = mJsonObject.getInt("id");
            int seats = mJsonObject.getInt("seats");     

            tablesAPI.add(new Table(tableId, seats));
        }
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123")) {
                Statement st = con.createStatement();
                String sql = "select tables.id as tablesID, tables.seats as tablesSeats from tables " +
                        "where tables.restaurant_id = " + id + " " +
                        "AND tables.id NOT IN (select tables.id from restaurants " +
                        "inner join tables on restaurants.id = tables.restaurant_id " +
                        "inner join tables_has_reservations on tables.id = tables_has_reservations.table_id " +
                        "inner join reservations on tables_has_reservations.reservation_id = reservations.id " +
                        "where restaurants.id = " + id +
                        " AND reservations.date = '" + date + "'" +
                        " AND reservations.shift = " + shift + ");";
                ResultSet rs = st.executeQuery(sql);   
                

                while (rs.next()) {
                    int tableId = rs.getInt("tablesID");
                    int seats = rs.getInt("tablesSeats");
                    
                    tablesSQL.add(new Table(tableId, seats));
                }
            }
                      
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw e;
        }
        
        boolean result = false;
        
        if (tablesSQL.size() == tablesAPI.size()) {    
            int x = 0;
            for (Table i : tablesSQL)
            {
                result = (i.toString().equals(tablesAPI.get(x++).toString()));
                if (!result) break;
            }
        }
        
        return result;
    }
}
