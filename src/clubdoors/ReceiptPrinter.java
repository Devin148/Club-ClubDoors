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
public class ReceiptPrinter implements Printable, Pageable{

    String type;

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

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 22));
        g.drawString("Centerfolds Boston", 10, 20);

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 12)); //Reset size
        g.drawString("-----------------------------------------", 10, 35);

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 14)); //Reset size
        g.drawString("Time: " + this.geTime(), 10, 75);
        g.drawString("Date: " + this.getDate(), 10, 115);

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 12)); //Reset size
        g.drawString("-----------------------------------------", 10, 155);

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 32)); //Reset size
        g.drawString(this.type, 10, 210);

        return PAGE_EXISTS;
    }

    /**
     * Sets the pageformat for the job and book, then tells it
     * to print
     * @param s type
     */
    public void print(String s){
        this.type = s;
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
