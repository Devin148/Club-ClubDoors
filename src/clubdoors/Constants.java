/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clubdoors;

/** Class to hold the program's constants such as:
 * the address to the guide and the startup file
 *
 * @author Devin
 */
public class Constants {

    // The location of the program:
    private java.io.File fake = new java.io.File(""); // Make a fake file
    String path = fake.getAbsolutePath(); // Get the path of the fake
    String loc = path.substring(0, path.length()-5); //Remove the dist folder

    // Address to the startup file
    String startupURL = loc + "\\resources\\startup.txt";

    // Algorithms class to do all potential algorithms
    Algorithms algos = new Algorithms();

    // MySQL class to hold all MySQL querys
    MySQL mysql = new MySQL(this.getIP());

    public Constants(){}

    /** Gets the ip address from the startup url txt file */
    public String getIP(){
        return this.algos.getIP(this.startupURL);
    }

}
