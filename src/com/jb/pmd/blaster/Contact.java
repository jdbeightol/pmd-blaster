package com.jb.pmd.blaster;

import java.util.Comparator;

public class Contact
{
    public long id;
    public String first, last, email, phone;

    public Contact()
    {   id = -1; first = ""; last = ""; email = ""; phone = "";  }
    
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
        this.id = c.id;
        this.first = c.first; this.last = c.last;
        this.email = c.email; this.phone = c.phone;
    }
    
    public Contact(long id, String first, String last, String email, String phone)
    {
        this.id = -1;
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
                return (t.first + t.last + t.id).compareTo(t1.first + t1.last + t.id);
            }
        };
    }
}