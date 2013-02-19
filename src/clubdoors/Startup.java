/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clubdoors;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/** Class called at startup to verify the location of the startup
 * file which contains the database IP and other vital information
 *
 * @author Devin
 */
public class Startup {

    // Program constants
    Constants cons = new Constants();

    File file = new File(this.cons.startupURL);

    Desktop desktop;

    Startup(){
        if (Desktop.isDesktopSupported()) {
            this.desktop = Desktop.getDesktop();
        }

        this.verifyFile();
        // If the file is there, start connecting to the server
        this.connect();
    }

    /** Checks if the startup file with the given path exists
     */
    private void verifyFile(){
        if(this.file.exists()){
            // Move forwards...
        }else{
            // Tell the error and ask to setup the file
            this.setup();
        }
    }

    /** If the file doesn't exist, as the user for help
     */
    public void setup(){

        Object[] options = {"Browse", "Build"};
        int i = JOptionPane.showOptionDialog(null,
                "There has been an error locating the startup file."
                + "\nPlease select below either 'Browse' to navigate to"
                + " the correct file"
                + "\nor 'Build' to provide the program with information to"
                + " rebuild the file.",
                "Error locating startup file",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);

        switch(i){
            case 0:
                this.browse(); // Find the file
                break;

            case 1:
                this.rebuild(); // Rebuild the file
                break;

            case -1:
                // If the close button is pressed
                System.exit(0);
                break;

            default:
                // Should never really happen - defensive programming
                new ClubException("Error setting up the startup file",
                        "This should never happen");
                break;
        }

        this.verifyFile(); // Double check it's actually there
    }

    /** Browse for the startup file and then copy it to the correct
     * location
     */
    public void browse(){
        JFileChooser fc = new JFileChooser();

        fc.showOpenDialog(null);
        File browse = fc.getSelectedFile();

        try{
            FileReader in = new FileReader(browse);
            FileWriter out = new FileWriter(this.file);
            int c;

            while((c=in.read()) != -1){
                out.write(c);
            }

            in.close();
            out.close();

        } catch(Exception ex){
            new ClubException("Error Reading File", ex.toString());
        }
    }

    /** Asks the user for the server address to rebuild the startup
     * file
     */
    public void rebuild(){
        try{
            String s = JOptionPane.showInputDialog(null,
                    "What is the MySQL database IP address?"
                    + "\nDefault: 127.0.0.1:3306",
                    "IP Address",
                    JOptionPane.QUESTION_MESSAGE);
            if(this.cons.algos.isIP(s)){
                this.makeFile(s);
            }else{
                JOptionPane.showMessageDialog(null,
                        "This is not a proper IP address."
                        + "\nPlease try the format: ###.###.###.###:####");
                this.rebuild();
            }
        } catch(Exception ex){
            // If they click cancel, or there is another major error
            this.setup();
        }
    }

    /** Makes the startup file using the given string as the IP
     * address
     */
    public void makeFile(String s){
        Writer output;
        File outputFile = new File(this.cons.startupURL);
        try{
            output = new BufferedWriter(new FileWriter(outputFile));
            output.write(s);
            output.close();
            JOptionPane.showMessageDialog(null,
                    "The file has been sucessfully made.");
        } catch(Exception ex){
            new ClubException("Error Writing File", ex.toString());
        }
    }

    /** Connect to the MySQL server using the verified IP */
    private void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://"+this.cons.getIP()+"/clubdoors",
                    "club_user","club_password");
        } catch(Exception ex){
            new ClubException("Error Connecting to Database", ex.toString());
        }
    }
}
