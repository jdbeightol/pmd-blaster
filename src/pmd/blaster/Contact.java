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
    
    public Contact(String first, String last, String email, String phone)
    {
        this.first = first; this.last = last;
        this.email = email; this.phone = phone;
    }
    
    public static LinkedList<Contact> parseCSVFile(String filename)
    {
        LinkedList<Contact> people = new LinkedList<>();
        
        try
        {
            int firstIndex = -1, lastIndex = -1;
            boolean bothInOne = false;
            BufferedReader csvBr = new BufferedReader(new FileReader(filename));
            String line;
            
            while((line = csvBr.readLine()) != null)
            {
                String first = "", last = "", email = "", phone = "";
                String[] row = line.split(",");

                for(int i = 0; i < row.length; i++)
                {
                    if(row[i].matches("1?-?\\d{3}-?\\d{3}-?\\d{4}"))
                        phone = row[i];
                    
                    else if(row[i].matches("\\w+@\\w+(?:\\.\\w+)+"))
                        email = row[i];
                    
                    else if(row[i].toLowerCase().matches(
                            "(!?.*(first|last))*name.*") 
                            && firstIndex == -1 && lastIndex == -1)
                    {   firstIndex = i; bothInOne = true;   }
                    
                    else if(row[i].toLowerCase().matches(".*first.*") 
                            && firstIndex == -1)
                        firstIndex = i;
                    
                    else if(row[i].toLowerCase().matches(".*last.*") 
                            && lastIndex == -1)
                        lastIndex = i;
                }
                
                if(firstIndex != -1 && row.length > firstIndex) 
                    first = row[firstIndex];
                
                if(lastIndex != -1 && row.length > lastIndex)
                    last = row[lastIndex];
                
                if(!(email.equals("") && phone.equals("")))
                    people.add(new Contact(first.trim(),last.trim(),
                            email.trim(),phone.trim()));
            }
            
            if(firstIndex == -1)
                System.out.println("[Warning] Could not parse first names.");
            
            if(lastIndex == -1 && !bothInOne)
                System.out.println("[Warning] Could not parse last names."); 
            
            try
            {
                csvBr.close();
            } catch(Exception e) 
            {
                throw new RuntimeException(e);
            }
        } catch(IOException | RuntimeException e)
        {
            throw new RuntimeException(e);
        }
        
        Collections.sort(people, new Comparator<Contact>()
        {
            @Override
            public int compare(Contact t, Contact t1)
            {
                return t.first.compareTo(t1.first);
            }
        });
        
        return people;
    }
}