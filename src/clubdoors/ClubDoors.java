package clubdoors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;

/**
 *
 * @author Devin
 */
public class ClubDoors extends JFrame implements ActionListener{

    MainPanel mainPanel = new MainPanel();
    ReportsPanel reportsPanel = new ReportsPanel();

    PassFrame passFrame = new PassFrame();
    DateFrame dateFrame = new DateFrame();

    Constants cons = new Constants();

    ReceiptPrinter receiptPrinter = new ReceiptPrinter();
    ZPrinter zPrinter = new ZPrinter();
    DatePrinter datePrinter = new DatePrinter();

    ClubDoors(){
        super("Club Doors"); //Title name
        
        this.setup(); // Setup the GUI

        // Make a startup class to quickly veryfy the database IP
        new Startup();
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new ClubDoors();
    }

    /*
     * Sets up the initial variables and buttons
     */
    public final void setup(){
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640,480);
        setVisible(true);
        setResizable(true);
        getContentPane().add(this.mainPanel);

        //MainPanel
        this.mainPanel.errorLabel.setVisible(false);

        this.mainPanel.fiveButton.addActionListener(this);
        this.mainPanel.tenButton.addActionListener(this);
        this.mainPanel.twentyButton.addActionListener(this);
        this.mainPanel.gameButton.addActionListener(this);
        this.mainPanel.managerButton.addActionListener(this);
        this.mainPanel.compButton.addActionListener(this);
        this.mainPanel.doorButton.addActionListener(this);
        this.mainPanel.hotelButton.addActionListener(this);
        this.mainPanel.reportsButton.addActionListener(this);

        this.mainPanel.one.addActionListener(this);
        this.mainPanel.two.addActionListener(this);
        this.mainPanel.three.addActionListener(this);
        this.mainPanel.four.addActionListener(this);
        this.mainPanel.five.addActionListener(this);
        this.mainPanel.six.addActionListener(this);
        this.mainPanel.seven.addActionListener(this);
        this.mainPanel.eight.addActionListener(this);
        this.mainPanel.nine.addActionListener(this);
        this.mainPanel.zero.addActionListener(this);

        this.mainPanel.clearButton.addActionListener(this);

        //ReportsPanel
        this.reportsPanel.backButton.addActionListener(this);

        this.reportsPanel.zButton.addActionListener(this);
        this.reportsPanel.dateButton.addActionListener(this);

        //PassFrame
        this.passFrame.one.addActionListener(this);
        this.passFrame.two.addActionListener(this);
        this.passFrame.three.addActionListener(this);
        this.passFrame.four.addActionListener(this);
        this.passFrame.five.addActionListener(this);
        this.passFrame.six.addActionListener(this);
        this.passFrame.seven.addActionListener(this);
        this.passFrame.eight.addActionListener(this);
        this.passFrame.nine.addActionListener(this);
        this.passFrame.zero.addActionListener(this);

        this.passFrame.clearButton.addActionListener(this);
        this.passFrame.submitButton.addActionListener(this);
        this.passFrame.cancelButton.addActionListener(this);

        //DateFrame
        this.dateFrame.cancelButton.addActionListener(this);
        this.dateFrame.submitButton.addActionListener(this);
        this.setupDates();
        this.dateFrame.reset();
    }

    /**
     * Initializes the dates for the JComboBox in the DateFrame
     */
    public final void setupDates(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        int cYear = Integer.parseInt(dateFormat.format(date));

        ArrayList years = new ArrayList();
        for(int i=cYear-5; i<=cYear+5; i++){
            this.dateFrame.yearBox.addItem(i);
            this.dateFrame.yearBoxTo.addItem(i);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Whenever anything happens clear the error
        this.mainPanel.errorLabel.setVisible(false);

        //MainPanel
        if(e.getSource() == mainPanel.fiveButton){
            this.printAddClear("$5");
        }
        if(e.getSource() == mainPanel.tenButton){
            this.printAddClear("$10");
        }
        if(e.getSource() == mainPanel.twentyButton){
            this.printAddClear("$20");
        }
        if(e.getSource() == mainPanel.gameButton){
            this.printAddClear("G-Tix");
        }
        if(e.getSource() == mainPanel.managerButton){
            this.printAddClear("Mgr Comp");
        }
        if(e.getSource() == mainPanel.compButton){
            this.printAddClear("Comp Pass");
        }
        if(e.getSource() == mainPanel.doorButton){
            this.printAddClear("Comp");
        }
        if(e.getSource() == mainPanel.hotelButton){
            this.printAddClear("Hotel");
        }
        if(e.getSource() == mainPanel.clearButton){
            this.mainPanel.clear();
        }
        if(e.getSource() == mainPanel.reportsButton){
            this.passFrame.setVisible(true);
        }

        //ReportsPanel
        if(e.getSource() == reportsPanel.backButton){
            getContentPane().remove(reportsPanel);
            getContentPane().add(mainPanel);
        }
        if(e.getSource() == reportsPanel.zButton){
            this.printZ();
        }
        if(e.getSource() == reportsPanel.dateButton){
            this.dateFrame.setVisible(true);
        }

        //PassFrame
        if(e.getSource() == passFrame.cancelButton){
            this.passFrame.clear();
            this.passFrame.setVisible(false);
        }
        if(e.getSource() == passFrame.clearButton){
            this.passFrame.clear();
        }
        if(e.getSource() == passFrame.submitButton){
            if(this.passFrame.checkPass()){
                this.passFrame.clear();
                this.passFrame.setVisible(false);
                getContentPane().remove(mainPanel);
                getContentPane().add(reportsPanel);
            }else{
                new ClubException("Invalid Password",
                        "The password you entered is incorrect.");
                this.passFrame.clear();
            }
        }

        //DateFrame
        if(e.getSource() == dateFrame.cancelButton){
            this.dateFrame.reset();
            this.dateFrame.setVisible(false);
        }
        if(e.getSource() == dateFrame.submitButton){
            this.printDate();
        }

        //Numbers
        if(e.getSource() == mainPanel.one){this.mainPanel.add(1);}
        if(e.getSource() == mainPanel.two){this.mainPanel.add(2);}
        if(e.getSource() == mainPanel.three){this.mainPanel.add(3);}
        if(e.getSource() == mainPanel.four){this.mainPanel.add(4);}
        if(e.getSource() == mainPanel.five){this.mainPanel.add(5);}
        if(e.getSource() == mainPanel.six){this.mainPanel.add(6);}
        if(e.getSource() == mainPanel.seven){this.mainPanel.add(7);}
        if(e.getSource() == mainPanel.eight){this.mainPanel.add(8);}
        if(e.getSource() == mainPanel.nine){this.mainPanel.add(9);}
        if(e.getSource() == mainPanel.zero){this.mainPanel.add(0);}

        if(e.getSource() == passFrame.one){this.passFrame.add(1);}
        if(e.getSource() == passFrame.two){this.passFrame.add(2);}
        if(e.getSource() == passFrame.three){this.passFrame.add(3);}
        if(e.getSource() == passFrame.four){this.passFrame.add(4);}
        if(e.getSource() == passFrame.five){this.passFrame.add(5);}
        if(e.getSource() == passFrame.six){this.passFrame.add(6);}
        if(e.getSource() == passFrame.seven){this.passFrame.add(7);}
        if(e.getSource() == passFrame.eight){this.passFrame.add(8);}
        if(e.getSource() == passFrame.nine){this.passFrame.add(9);}
        if(e.getSource() == passFrame.zero){this.passFrame.add(0);}

        //Repaint and validate after everything, just to be safe
        getContentPane().repaint();
        getContentPane().validate();
    }

    /**
     * Calls the print function in receiptPrinter based off the number
     * given in numField
     * @param s String type
     */
    public void printReceipt(String s){
        try{
            for(int i=1; i<=this.mainPanel.getNum(); i++){
                this.receiptPrinter.print(s);
            }
        } catch(Exception ex){
            this.mainPanel.errorLabel.setVisible(true);
        }
    }

    /**
     * Calls the print function in receiptPrinter based off the number
     * given in numField
     * @param s String type
     */
    public void printZ(){
        try{
            this.zPrinter.print(
                    this.cons.mysql.getAdmissions(),
                    this.cons.mysql.lastZ());
            this.cons.mysql.resetZ();
        } catch(Exception ex){
            new ClubException("Error Selecting from Database",
                    ex.toString());
        }
    }

    /**
     * Calls the print function in receiptPrinter based off the number
     * given in numField
     * @param s String type
     */
    public void printDate(){
        try{
            this.datePrinter.print(
                    this.cons.mysql.getRangeAdmissions(
                        this.dateFrame.getFrom(),
                        this.dateFrame.getTo()),
                    this.dateFrame.getFromTo());
        } catch(Exception ex){
            new ClubException("Error Selecting from Database",
                    ex.toString());
        }
    }

    /**
     * Adds a listing of the
     * @param s String type
     */
    public void addToDatabase(String s){
        try{
            for(int i=1; i<=this.mainPanel.getNum(); i++){
                this.cons.mysql.update("INSERT INTO tbl_admissions "
                        + "(type, month, day, year, z, date) VALUES "
                        + "('" + s // the given type
                        + "', "+ this.cons.mysql.getMonth() // month
                        + ", " + this.cons.mysql.getDay() // day
                        + ", " + this.cons.mysql.getYear() // year
                        + ", 0" // has it been z'd yet (0=no)
                        + ", CURDATE())"); //The current date
            }
        } catch(Exception ex){
            this.mainPanel.errorLabel.setVisible(true);
        }
    }

    /**
     * Prints a receipt, adds to the database, and clears the numField.
     * Used to simplify ActionListener
     */
    public void printAddClear(String s){
        this.printReceipt(s);
        this.addToDatabase(s);
        this.mainPanel.clear();
    }

    

}
