/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clubdoors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Devin
 */
public class Algorithms{

    Algorithms(){}

    /** Checks if the given string is in an IP address format */
    public boolean isIP(String s){
        // Make an ArrayList splitting the string up based off of . and :
        ArrayList<String> alist =
                new ArrayList<String>(Arrays.asList(s.split("[.:]+")));

        if(alist.size() != 5){ // If the string wasn't a long enough IP
            return false;
        }

        try{
            for(int i=0; i<alist.size(); i++){
                // Throws and error if it wasn't a number
                int n = Integer.parseInt(alist.get(i));
                // If the IP range is <0 or >255
                if((n < 0 || n > 255) && i != alist.size()-1){
                    return false;
                }
                // If the section is the last in the alist (the port)
                //   and is out of bounds
                if(i == alist.size()-1 && (n < 0 || n > 65535)){
                    return false;
                }
            }
        } catch(Exception ex){
            // Means the user put in letters not numbers
            return false;
        }

        return true;
    }

    /** Gets the IP address from the startup url txt file */
    public String getIP(String s){
        String ip = "";
        try{
            File file = new File(s);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ip = reader.readLine(); // Only one line
        } catch(Exception ex){
            new ClubException("Error Reading IP", ex.toString());
        }
        return ip;
    }
}