/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clubdoors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Devin
 */
public class MySQL {
    String ip;

    Connection con;
    Statement stat;

    MySQL(String ip){
        this.ip = ip;
        this.connect(); // Connect to the database
    }

    /** Returns the database url */
    public String getURL(){
        return "jdbc:mysql://"+this.ip+"/clubdoors";
    }

    /** Connect to the MySQL server using the verified IP */
    private void connect(){
        try{
            this.con = DriverManager.getConnection( // Connect
                    this.getURL(),
                    "club_user","club_password");

            this.stat = this.con.createStatement( // Make a statement
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch(Exception ex){
            new ClubException("Error Connecting to Database", ex.toString());
        }
    }

    /**
     * A function that updates the MySQL database
     * @param s string to update
     */
    public void update(String s){
        try{
            this.connect(); // Reconnect in case the program has been open
                            // too long and it closed the connection.
            this.stat.executeUpdate(s);
        } catch(Exception ex){
            new ClubException("Error Updating", ex.toString());
        }
    }
    
    /**
     * Returns the number of times a given type appears in the database
     * @param type the type to find
     * @return int amount
     */
    public int getAmountZ(String type) throws SQLException{
        int amount = 0;
        this.connect();
        
        ResultSet dataSet = this.stat.executeQuery
                ("SELECT * FROM tbl_admissions WHERE z=0 AND type='"
                + type
                + "'");
        dataSet.afterLast();
        while (dataSet.previous()) {
            amount ++;
        }

        return amount;
    }

    /**
     * Returns an array of integers which correspond to the number
     * of admissions that have yet to be Z'd
     * @return int[] admissions
     */
    public int[] getAdmissions() throws SQLException{
        int[] adm = new int[8];

        adm[0] = this.getAmountZ("$5");
        adm[1] = this.getAmountZ("$10");
        adm[2] = this.getAmountZ("$20");
        adm[3] = this.getAmountZ("G-Tix");
        adm[4] = this.getAmountZ("Mgr Comp");
        adm[5] = this.getAmountZ("Comp Pass");
        adm[6] = this.getAmountZ("Comp");
        adm[7] = this.getAmountZ("Hotel");

        return adm;
    }

    /**
     * Returns the number of times each type appears in the database
     * between the two dates given
     * @param type the type to find
     * @param from the starting date as an array of ints
     * @param to the ending date as an array of ints
     * @return int amount
     * @throws SQLException
     */
    public int getAmountR(String type, int[] f, int[] t) throws SQLException{
        int amount = 0;                 //   ^From    ^To
        this.connect();
        
        ResultSet dataSet = this.stat.executeQuery
                ("SELECT * FROM tbl_admissions WHERE type='"
                + type + "'"

                + " AND "
                + "date>="
                + "'" + f[2] + "-" + f[0] + "-" + f[1] + "'"
                //      YEAR     -   MONTH    -    DAY

                + " AND "
                + "date<="
                + "'" + t[2] + "-" + t[0] + "-" + t[1] + "'"

                + " AND "
                + "year BETWEEN "
                + f[2] + " AND " + t[2]

                );
        dataSet.afterLast();
        while (dataSet.previous()) {
            amount ++;
        }

        return amount;
    }

    /**
     * Returns an array of integers which correspond to the number
     * of admissions that have yet to be Z'd
     * @return int[] admissions
     */
    public int[] getRangeAdmissions(int[] from, int[] to) throws SQLException{
        int[] adm = new int[8];

        adm[0] = this.getAmountR("$5", from, to);
        adm[1] = this.getAmountR("$10", from, to);
        adm[2] = this.getAmountR("$20", from, to);
        adm[3] = this.getAmountR("G-Tix", from, to);
        adm[4] = this.getAmountR("Mgr Comp", from, to);
        adm[5] = this.getAmountR("Comp Pass", from, to);
        adm[6] = this.getAmountR("Comp", from, to);
        adm[7] = this.getAmountR("Hotel", from, to);

        return adm;
    }

    /**
     * Resets the Z so that all admissions have been Z'd
     */
    public void resetZ(){
        this.update("UPDATE tbl_admissions SET z=1");
        this.update("INSERT INTO last_z VALUES (NOW())");
    }

    /**
     * Gets the last Z from table last_z
     * @return String last Z
     */
    public String lastZ() throws SQLException{
        String time = "";
        this.connect();
        
        ResultSet dataSet = this.stat.executeQuery
                ("SELECT * FROM last_z ORDER BY time DESC LIMIT 1");
        dataSet.afterLast();
        while (dataSet.previous()) {
            time = dataSet.getString("time");
        }

        return time;
    }

    /**
     * Returns the current month as an integer
     * @return int month
     */
    public int getMonth(){
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
    }

    /**
     * Returns the current day as an integer
     * @return int day
     */
    public int getDay(){
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
    }

    /**
     * Returns the current year as an integer
     * @return int year
     */
    public int getYear(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
    }

}
