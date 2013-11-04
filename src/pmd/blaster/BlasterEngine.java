package pmd.blaster;

import com.restfb.FacebookClient.AccessToken;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.FacebookType;
import com.restfb.FacebookClient;
import com.restfb.Parameter;

import com.techventus.server.voice.Voice;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import java.util.Properties;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Set;

import java.io.IOException;

import javax.mail.internet.InternetAddress;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.Message;

import javax.swing.JOptionPane;

public class BlasterEngine
{
    public static final boolean DEBUG = false;
    
    public static String    googleuser      = "",
                            smtpserver      = "smtp.gmail.com",
                            smtpport        = "465";
    
    private static String   googlepass      = "";
    
    private static HashMap<String, LinkedList<Contact>> Rosters;
    
    public static void initBlaster()
    {
        DatabaseEngine.initDB(false);

        String ss, sp;
        
        googleuser = DatabaseEngine.getPreference("googleuser");
        googlepass = DatabaseEngine.getPreference("googlepass");
        
        ss = DatabaseEngine.getPreference("smtpserver");
        sp = DatabaseEngine.getPreference("smtpport");
        
        smtpserver = ("".equals(ss))?"smtp.gmail.com":ss;
        smtpport = ("".equals(sp))?"465":sp;
        
        Rosters = DatabaseEngine.getRosters();
    }

    public static void setPass(String pass)
    {
        googlepass = pass;
        DatabaseEngine.savePreference("googlepass", googlepass);
    }
    
    public static void setConfig(String usr, String pass, String server, String port)
    {
        googleuser = usr; googlepass = pass; smtpserver = server; smtpport = port;
    }
    
    public static void addRoster(String name, LinkedList<Contact> ContactList)
    {
        if(Rosters == null)
            Rosters = new HashMap<>();
        
        else if(Rosters.containsKey(name))
            Rosters.remove(name);
        
        Rosters.put(name, ContactList);
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

    public static void sendEmails(String msg, LinkedList<Contact> people)
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
        
        try
        {
            Message message = new MimeMessage(smtpSession);
            message.setFrom(new InternetAddress(googleuser));
            
            for(Contact c : people)
                message.addRecipients(Message.RecipientType.TO, 
                        InternetAddress.parse(c.email));
                        
            message.setSubject("Phi Mu Delta Mu Zeta Announcement");
            message.setText(msg);
            
            System.out.println("Sending email...");
            
            Transport.send(message);
            
            System.out.println("[Success] Email sent.");
        } catch(MessagingException e)
        {
            throw new RuntimeException(e);
        }
    }
        
    public static void sendSMS(String msg, LinkedList<Contact> people)
    {
        try
        {
            int errCount = 0, sendCount = 0, totalCount = 0;
            System.out.println("Connecting to Google Voice...");
            Voice voice = new Voice(googleuser, googlepass);
            
            for(Contact c : people)
            {
                sendCount = 0;
     
                System.out.println("Sending text message to " 
                            + c.first + " " + c.last + ".");
                
                while(sendCount++ < 5 && !new JSONObject(voice.sendSMS(c.phone, 
                        msg)).getBoolean("ok"))
                {
                    System.out.println("[Warning] Message to " + c.first + " " 
                            + c.last + " failed.");    
                    
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
            
            if(errCount > 0) JOptionPane.showMessageDialog(null, 
                    "Failed to send messages to " + errCount + " contacts.");
            
        } catch (IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
    }
        
    public static void getInbox()
    {
        //TODO Ugly as heck.  Needs work.
        try
        {
            System.out.println("Connecting to Google Voice.");
            System.out.println(new Voice(googleuser, googlepass).getInbox());
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public static void postFacebook(String msg)
    {
        //TODO Does not work yet.  Needs Facebook user OAUTH Token.
        AccessToken at = new DefaultFacebookClient().obtainAppAccessToken(
                "214508892052301", "8266220a2c7b7ae8a33b4e589e8280cf");
        System.out.println("My application access token: " + at.getAccessToken());
        FacebookClient facebookClient = new DefaultFacebookClient(at.getAccessToken());

        
        FacebookType publishMessageResponse
                = facebookClient.publish("me/feed", FacebookType.class, 
                Parameter.with("message", "Test."));
        
        System.out.println("Published message ID: " + publishMessageResponse.getId());
    }
}