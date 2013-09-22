package equiphire.util;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMailUsage implements Runnable {
    public static void main(String from, String to, String subject, String message) {
        new Thread(new SendMailUsage(from, to, subject, message)).start();
    }
    
    private SendMailUsage(String from, String to, String subject, String message) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
    
    String from, to, subject, message;
    
    public void run() {
        sendMail(from, to, subject, message);
    }
    
    public void sendMail(String from, String to, String subject, String message) {
// SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        String host = "smtp.gmail.com";

// Create properties for the Session
        Properties props = new Properties();

// If using static Transport.send(),
// need to specify the mail server here
        props.put("mail.smtp.gmail.com", host);
// To see what is going on behind the scene
        props.put("mail.debug", "true");
        props.put("mail.smtp.user", "senders mail address");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
//props.put("mail.smtp.debug", "true"); // if the user wants
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
// Get a session
        Session session = Session.getInstance(props);

        try {
// Get a Transport object to send e-mail
            Transport bus = session.getTransport("smtp");

// Connect only once here
// Transport.send() disconnects after each send
// Usually, no username and password is required for SMTP
//bus.connect();
            bus.connect("smtp.gmail.com", "mylocaltestserver@gmail.com", "ucscthebest");

// Instantiate a message
            Message msg = new MimeMessage(session);

// Set message attributes
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            
            /*
// Parse a comma-separated list of email addresses. Be strict.
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(cc, true));
// Parse comma/space-separated list. Cut some slack.
            msg.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(to, false));
*/
            msg.setSubject(subject);
            msg.setSentDate(new Date());

// Set message content and send
            setTextContent(msg, message);
            msg.saveChanges();
            bus.sendMessage(msg, address);
/* 
            setMultipartContent(msg);
            msg.saveChanges();
            bus.sendMessage(msg, address);

            setFileAsAttachment(msg, "c:/SendMailUsage.JAVA");
            msg.saveChanges();
            bus.sendMessage(msg, address);

            setHTMLContent(msg);
            msg.saveChanges();
            bus.sendMessage(msg, address);
*/
            bus.close();

        } catch (MessagingException mex) {
// Prints all nested (chained) exceptions as well
            mex.printStackTrace();
// How to access nested exceptions
            while (mex.getNextException() != null) {
// Get next exception in chain
                Exception ex = mex.getNextException();
                ex.printStackTrace();
                if (!(ex instanceof MessagingException)) {
                    break;
                } else {
                    mex = (MessagingException) ex;
                }
            }
        }
    }
// A simple, single-part text/plain e-mail.
    public void setTextContent(Message msg, String mytxt) throws MessagingException {
// Set message content
//String mytxt = "This is a test of sending a " +
// "plain text e-mail through Java.\n" +
// "Here is line 2.";
        msg.setText(mytxt);

// Alternate form
        msg.setContent(mytxt, "text/plain");

    }
// A simple multipart/mixed e-mail. Both body parts are text/plain.
    public void setMultipartContent(Message msg) throws MessagingException {
// Create and fill first part
        MimeBodyPart p1 = new MimeBodyPart();
        p1.setText("This is part one of a test multipart e-mail.");

// Create and fill second part
        MimeBodyPart p2 = new MimeBodyPart();
// Here is how to set a charset on textual content
        p2.setText("This is the second part", "us-ascii");

// Create the Multipart. Add BodyParts to it.
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(p1);
        mp.addBodyPart(p2);

// Set Multipart as the message's content
        msg.setContent(mp);
    }
// Set a file as an attachment. Uses JAF FileDataSource.
    public void setFileAsAttachment(Message msg, String filename)
            throws MessagingException {

// Create and fill first part
        MimeBodyPart p1 = new MimeBodyPart();
        p1.setText("This is part one of a test multipart e-mail." +
                "The second part is file as an attachment");

// Create second part
        MimeBodyPart p2 = new MimeBodyPart();

// Put a file in the second part
        FileDataSource fds = new FileDataSource(filename);
        p2.setDataHandler(new DataHandler(fds));
        p2.setFileName(fds.getName());

// Create the Multipart. Add BodyParts to it.
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(p1);
        mp.addBodyPart(p2);

// Set Multipart as the message's content
        msg.setContent(mp);
    }
// Set a single part html content.
// Sending data of any type is similar.
    public void setHTMLContent(Message msg) throws MessagingException {

        String html = "<html><head><title>" +
                msg.getSubject() +
                "</title></head><body><h1>" +
                msg.getSubject() +
                "</h1><p>This is a test of sending an HTML e-mail" +
                " through Java.</body></html>";

// HTMLDataSource is an inner class
        msg.setDataHandler(new DataHandler(new HTMLDataSource(html)));
    }

    /*
     * Inner class to act as a JAF datasource to send HTML e-mail content
     */
    class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }
// Return html string in an InputStream.
// A new stream must be returned each time.
        public InputStream getInputStream() throws IOException {
            if (html == null) {
                throw new IOException("Null HTML");
            }
            return new ByteArrayInputStream(html.getBytes());
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        public String getContentType() {
            return "text/html";
        }

        public String getName() {
            return "JAF text/html dataSource to send e-mail only";
        }
    }
    public static void main(String args[]){
    SendMailUsage se=new SendMailUsage("kanishka","kkgweerasekara@gmail.com", "from java", "hello world");
    se.sendMail("kkgweerasekara", "kkgweerasekara@gmail.com","hi","hello world2");
    }


} //End of class
