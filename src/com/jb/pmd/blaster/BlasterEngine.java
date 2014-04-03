package com.jb.pmd.blaster;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

import com.techventus.server.voice.Voice;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class BlasterEngine
{
    public static final String VERSION = "0.3";
    
    public static boolean DEBUG = false;    
    
    public static String    googleuser      = "",
                            smtpserver      = "smtp.gmail.com",
                            smtpport        = "465",
                            theme           = "";
    
    private static String   googlepass      = "";
    
    private static HashMap<String, LinkedList<Contact>> Rosters;
    
    public static void initBlaster()
    {
        DatabaseEngine.initDB(false);

        String ss, sp;
        
        googleuser = DatabaseEngine.getPreference("googleuser");
        googlepass = CryptographyEngine.decrypt(
                DatabaseEngine.getPreference("googlepass"));
        
        ss = DatabaseEngine.getPreference("smtpserver");
        sp = DatabaseEngine.getPreference("smtpport");
        
        smtpserver = ("".equals(ss))?"smtp.gmail.com":ss;
        smtpport = ("".equals(sp))?"465":sp;
        
        theme = DatabaseEngine.getPreference("theme");
        
        Rosters = DatabaseEngine.getRosters();
    }

    public static void setPass(String pass)
    {        
        DatabaseEngine.savePreference("googlepass", 
                CryptographyEngine.encrypt(googlepass = pass));
    }
    
    public static void setConfig(String usr, String pass, String server, 
            String port)
    {
        googleuser = usr; googlepass = pass; smtpserver = server; smtpport = port;
    }
    
    public static void purgeRosters(boolean confirm, boolean certain, 
            boolean positive)
    {
        if (confirm && certain && positive)
            Rosters = null;
    }
    
    public static void purgePreferences(boolean confirm, boolean certain, 
            boolean positive)
    {
        if (confirm && certain && positive)
            googleuser = smtpserver = smtpport = googlepass = "";
    }
    
    public static void addRoster(String name, LinkedList<Contact> ContactList)
    {
        if(Rosters == null)
            Rosters = new HashMap<>();
        
        else if(Rosters.containsKey(name))
            Rosters.remove(name);
        
        Rosters.put(name, ContactList);
    }
    
    public static void removeRoster(String name)
    {
        if(Rosters == null)
            Rosters = new HashMap<>();
        
        else if(Rosters.containsKey(name))
            Rosters.remove(name);
    }
    
    public static LinkedList<Contact> getRoster(String name)
    {
        if(Rosters == null)
            Rosters = new HashMap<>();
        
        return Rosters.get(name);
    }
    
    public static Set<String> getRosters()
    {
        if(Rosters == null)
            Rosters = new HashMap<>();
        
        return Rosters.keySet();
    }
    
    public static void addContact(String roster, Contact contact)
    {
        LinkedList<Contact> r = getRoster(roster);
        r.add(contact);
        sort(r);
    }
    
    public static void removeContact(String roster, Contact contact)
    {
        getRoster(roster).remove(contact);
    }
       
    public static LinkedList<Contact> sort(LinkedList<Contact> people)
    {
        Collections.sort(people, Contact.Comparator());
        return people;
    }

    public static void sendEmails(String msg, LinkedList<Contact> people)
            throws MessagingException
    {
        Properties smtpProp = new Properties();
        
        smtpProp.put("mail.smtp.host", smtpserver);
        smtpProp.put("mail.smpt.port", smtpport);
        smtpProp.put("mail.smtp.socketFactory.port", smtpport);
        
        smtpProp.put("mail.smtp.auth", "true");
        smtpProp.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        
        System.out.println("Connecting to " + smtpserver + ".");
        
        Session smtpSession = Session.getDefaultInstance(smtpProp,
                new javax.mail.Authenticator()
                {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication
                                (googleuser,googlepass);
                    }
                });
        
        System.out.println("Connected.");

        Message message = new MimeMessage(smtpSession);
        message.setFrom(new InternetAddress(googleuser));

        for(Contact c : people)
            message.addRecipients(Message.RecipientType.TO, 
                    InternetAddress.parse(c.email));

        message.setSubject("Phi Mu Delta Mu Zeta Announcement");
        message.setText(msg);

        System.out.println("Sending email...");

        if(!DEBUG)
            Transport.send(message);
    }
    
    public static void sendSMS(String msg, LinkedList<Contact> people) 
            throws IOException, JSONException, RuntimeException
    {
       int errCount = 0, sendCount, totalCount = 0;
        System.out.println("Connecting to Google Voice...");
        System.out.println("User: '" + googleuser);
        Voice voice = new Voice(googleuser, googlepass);

        for(Contact c : people)
        {
            sendCount = 0;

            System.out.println("Sending text message to " 
                        + c.first + " " + c.last + ".");

            if(!DEBUG)
            {
                while(sendCount++ < 5 && !new JSONObject(voice.sendSMS(
                        c.phone, msg)).getBoolean("ok"))
                {
                    System.out.println("[Warning] Message to " + c.first 
                            + " " + c.last + " failed.");    

                    try
                    {
                        Thread.sleep(1000 * 15);
                    } catch (InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                }

                if(sendCount >= 5)
                    errCount++;

                if(++totalCount % 5 == 0) try
                {
                    Thread.sleep(1000*15);
                } catch (InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if(errCount > 0) 
            throw new RuntimeException("Failed to send " + errCount 
                    + " text messages.");
    }
    
    public static void postFacebook(String msg)
    {
        //TODO Does not work yet.  Needs Facebook user OAUTH Token.
        AccessToken at = new DefaultFacebookClient().obtainAppAccessToken(
                "214508892052301", "8266220a2c7b7ae8a33b4e589e8280cf");
        System.out.println("My application access token: " 
                + at.getAccessToken());
        FacebookClient facebookClient = new DefaultFacebookClient(
                at.getAccessToken());
        
        FacebookType publishMessageResponse
                = facebookClient.publish("me/feed", FacebookType.class, 
                Parameter.with("message", "Test."));
        
        System.out.println("Published message ID: " 
                + publishMessageResponse.getId());
    }
    
public static LinkedList<Contact> parseCSVFile(String filename) throws IOException
    {
        LinkedList<Contact> people = new LinkedList<>();
        
        int firstIndex = -1, lastIndex = -1;
        boolean bothInOne = false;
        
        try (BufferedReader csvBr = new BufferedReader(new FileReader(filename)))
        {
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
                    people.add(new Contact(-1, first.trim(),last.trim(),
                            email.trim(),phone.trim()));
            }
            
            if(firstIndex == -1)
                System.out.println("[Warning] Could not parse first names.");
            
            if(lastIndex == -1 && !bothInOne)
                System.out.println("[Warning] Could not parse last names.");
        }
        
        return sort(people);
    }
}