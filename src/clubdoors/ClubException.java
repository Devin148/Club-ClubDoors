package clubdoors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** An exception class that will cover all general exceptions
 * and explain them appropriately
 *
 * @author Devin
 */
public final class ClubException extends Exception {

    String type, msg;


    /** By giving no information, a general error is given */
    public ClubException(){
        this.type = "";
        this.msg = "";
        this.checkResponse(this.giveMessage());
    }

    /** Creates an error message and then asks to open the guide */
    public ClubException(String type, String msg){
        this.type = type;
        this.msg = msg;

        this.checkResponse(this.giveMessage());
    }

    /** Gives an error code to the user using a JOptionPane
     * and gives the user and option to open the guide
     * which explains what each error code means
     */
    public int giveMessage(){
        JFrame frame = new JFrame();
        Object[] options = {"Ok"};
        return JOptionPane.showOptionDialog(null, "Error Message:\n"+this.msg,
                type,
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);
    }

    /** Checks which option the user chose in the alert
     and responds accordingly */
    public void checkResponse(int i){
        switch(i){
            case 0:
                break; // Moves on...
            case -1:
                // If the close button is pressed
                System.exit(0);
                break;

            default:
                // Should never really happen - defensive programming
                new ClubException("Error during startup file setup",
                        "This should never happen");
                break;
        }
    }
}
