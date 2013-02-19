/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clubdoors;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Devin
 */
public class DatePrinter implements Printable, Pageable{

    int five, ten, twenty, game, mgr, comp, door, hotel;
    String fromTo;

    /**
     * Sets up and prints a receipt with the time, date, and type
     * @param g
     * @param pf
     * @param page
     * @return PAGE_EXISTS
     * @throws PrinterException
     */
    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {

        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Draw in the x boundaries of 10 and 190
        // Draw in the y boundaries of 10 and 990

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 14));
        g.drawString("Centerfolds Boston - Range", 12, 14);

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 12)); //Reset size
        g.drawString("-----------------------------------------", 10, 21);

        g.drawString("Range: " + this.fromTo, 10, 40);

        g.drawString("Current Time: " + this.geTime(), 10, 60);
        g.drawString("Current Date: " + this.getDate(), 10, 80);

        g.drawString("-----------------------------------------", 10, 100);

        g.drawString("$5 Admissions:", 10, 120);
        g.drawString(Integer.toString(this.five), 170, 120);

        g.drawString("$10 Admissions:", 10, 140);
        g.drawString(Integer.toString(this.ten), 170, 140);

        g.drawString("$20 Admissions:", 10, 160);
        g.drawString(Integer.toString(this.twenty), 170, 160);

        g.drawString("Game Admissions:", 10, 180);
        g.drawString(Integer.toString(this.game), 170, 180);

        g.drawString("Mgr Admissions:", 10, 200);
        g.drawString(Integer.toString(this.mgr), 170, 200);

        g.drawString("Comp Pass Admissions:", 10, 220);
        g.drawString(Integer.toString(this.comp), 170, 220);

        g.drawString("Door Comp Admissions:", 10, 240);
        g.drawString(Integer.toString(this.door), 170, 240);

        g.drawString("Hotel Admissions:", 10, 260);
        g.drawString(Integer.toString(this.hotel), 170, 260);

        g.drawString("-----------------------------------------", 10, 280);

        g.drawString("Total $:", 10, 300);
        g.drawString("$" + Integer.toString(this.calcTotal()), 165, 300);

        g.drawString("Total Admissions:", 10, 320);
        g.drawString(Integer.toString(this.calcAdmissions()), 170, 320);


        return PAGE_EXISTS;
    }

    /**
     * Sets the pageformat for the job and book, then tells it
     * to print
     * @param s type
     */
    public void print(int[] adm, String fromTo){
        this.fromTo = fromTo;

        this.five = adm[0];
        this.ten = adm[1];
        this.twenty = adm[2];
        this.game = adm[3];
        this.mgr = adm[4];
        this.comp = adm[5];
        this.door = adm[6];
        this.hotel = adm[7];

        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();

        Paper paper = new Paper();
        paper.setImageableArea(0, 0, 200, 1000);

        pf.setPaper(paper);

        Book book = new Book();
        book.append(this, pf);

        job.setPageable(book);

        try {
            job.print();
        } catch (Exception ex) {
            new ClubException("Error Printing", ex.toString());
        }
    }

    public int getNumberOfPages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PageFormat getPageFormat(int pageIndex)
            throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Printable getPrintable(int pageIndex)
            throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Calculates the total $ of admissions
     * @return int total $
     */
    public int calcTotal(){
        int total = 0;
        for(int i=1; i<=this.five; i++){
            total+=5;
        }
        for(int i=1; i<=this.ten; i++){
            total+=10;
        }
        for(int i=1; i<=this.twenty; i++){
            total+=20;
        }
        return total;
    }

    /**
     * Calculates the total number of admissions
     * @return into total admissions
     */
    public int calcAdmissions(){
        return this.five + this.ten + this.twenty + this.game + this.mgr
                + this.comp + this.door + this.hotel;
    }

    /**
     * Returns the current system date
     * @return system date
     */
    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Returns the current system time
     * @return system time
     */
    private String geTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
