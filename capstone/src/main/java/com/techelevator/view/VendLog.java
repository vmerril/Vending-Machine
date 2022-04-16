package com.techelevator.view;


import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendLog {

    public static void log(String message) throws IOException {

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        String s = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(ts);

        try {

            PrintWriter pw = new PrintWriter(new FileWriter("src/main/resources/vend.log", true));
            pw.println(s + " - " + message);

            pw.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("Log file not found.");
        }
        catch (Exception e) {
            throw new VendLogException(e.getMessage());
        }
    }



}
