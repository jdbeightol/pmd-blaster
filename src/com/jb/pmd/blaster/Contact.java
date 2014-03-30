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
       return "<html><b><font size=\"5\">" + first + " " 
                + last + "</font></b><br><font size=\"2\" color=\"gray\">" 
                + email + "&nbsp;&nbsp;&nbsp;&nbsp;" + phone 
                + "</font></html>";
    }
    
    public Contact(Contact c)
    {
        id = c.id;
        first = c.first; last = c.last;
        email = c.email; phone = c.phone;
    }
    
    public Contact(long id, String first, String last, String email, String phone)
    {
        id = -1;
        first = first; last = last;
        email = email; phone = phone;
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