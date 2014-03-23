package pmd.blaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;

public class Contact
{
    public String first, last, email, phone;

    public Contact()
    {   first = ""; last = ""; email = ""; phone = "";  }
    
    @Override
    public String toString()
    {
       return "<html><b><font size=\"5\">" + this.first + " " 
                + this.last + "</font></b><br><font size=\"2\" color=\"gray\">" 
                + this.email + "&nbsp;&nbsp;&nbsp;&nbsp;" + this.phone 
                + "</font></html>";
    }
    
    public Contact(Contact c)
    {
        this.first = c.first; this.last = c.last;
        this.email = c.email; this.phone = c.phone;
    }
    
    public Contact(String first, String last, String email, String phone)
    {
        this.first = first; this.last = last;
        this.email = email; this.phone = phone;
    }
    
    public static Comparator Comparator()
    {
        return new Comparator<Contact>()
        {
            @Override
            public int compare(Contact t, Contact t1)
            {
                return t.first.compareTo(t1.first);
            }
        };
    }
}