package ControllerTestsLogic;

import Helper.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationControllerTestLogic {
    private final FileReader reader;
    
    public ReservationControllerTestLogic () {
        reader = new FileReader();
    }
    
    public boolean getReservationsByRestaurantIdResult (String id) throws Exception {
        String valueAPI = reader.getValueFromURL("http://localhost:8080/reservations?id=" + id);
        String valueSQL = "[";
        
        boolean cont = true;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123")) {
                Statement st = con.createStatement();
                String sql = ("select users.name, users.telephone, reservations.date, reservations.shift, tables.tablenumber from users " +
                        "inner join reservations on users.id = reservations.user_id " +
                        "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                        "inner join tables on tables_has_reservations.table_id = tables.id " +
                        "inner join restaurants on tables.restaurant_id = restaurants.id " +
                        "WHERE restaurants.id = '" + id + "';");
                ResultSet rs = st.executeQuery(sql);
                cont = rs.first();
                
                if (cont) rs.previous();
                
                while (rs.next()) {
                    String name = rs.getString("name");
                    String telephone = rs.getString("telephone");
                    String date  = rs.getDate("date").toString();
                    String shift  = Integer.toString(rs.getInt("shift"));
                    String tablenumber = Integer.toString(rs.getInt("tablenumber"));
                    
                    valueSQL +=  "\"" + name + "\",\"" + telephone + "\",\"" + date + "\",\"" + shift + "\",\"" + tablenumber + "\",";
                }
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw e;
        }
        
        valueSQL = valueSQL.substring(0,valueSQL.length() - 1);        
        valueSQL += "]";
        valueSQL = valueSQL.replaceAll("\"null\"", "null");
        
        if (!cont) valueSQL = null;
        
        if (valueSQL == null && valueAPI == null) return true;
        else if (valueSQL == null || valueAPI == null) return false;
        else if (valueSQL.equals(valueAPI)) return true;
        return false;
    }
    
    public boolean getReservationsByRestaurantIdAndShiftResult (String id, String shift) throws Exception {
        String valueAPI = reader.getValueFromURL("http://localhost:8080/reservations?id=" + id + "&shift=" + shift);
        String valueSQL = "[";
        
        boolean cont = true;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123")) {
                Statement st = con.createStatement();
                String sql = ("select users.name, users.telephone, reservations.date, tables.tablenumber from users " +
                        "inner join reservations on users.id = reservations.user_id " +
                        "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                        "inner join tables on tables_has_reservations.table_id = tables.id " +
                        "inner join restaurants on tables.restaurant_id = restaurants.id " +
                        "WHERE restaurants.id = " + id +
                        " AND reservations.shift = " + shift + ";");
                ResultSet rs = st.executeQuery(sql);
                
                cont = rs.first();
                
                if (cont) rs.previous();
                
                while (rs.next()) {
                    String name = rs.getString("name");
                    String telephone = rs.getString("telephone");
                    String date  = rs.getDate("date").toString();
                    String tablenumber = Integer.toString(rs.getInt("tablenumber"));
                    valueSQL +=  "\"" + name + "\",\"" + telephone + "\",\"" + date + "\",\"" + tablenumber + "\",";
                }
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw e;
        }
        
        valueSQL = valueSQL.substring(0,valueSQL.length() - 1);
        valueSQL += "]";
        valueSQL = valueSQL.replaceAll("\"null\"", "null");
        
        if (!cont) valueSQL = null;
        
        if (valueSQL == null && valueAPI == null) return true;
        else if (valueSQL == null || valueAPI == null) return false;
        else if (valueSQL.equals(valueAPI)) return true;
        return false;
    }
}
