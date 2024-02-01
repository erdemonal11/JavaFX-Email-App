package myemailclient;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReceiveEmail {

    public List<Email> retrieveEmails() {

        List<Email> emails = new ArrayList<>();

        Properties props = new Properties();
        props.put("mail.store.protocol", CurrentUser.protocol);

        Session session = Session.getInstance(props, null);

        try {
            Store store = session.getStore(CurrentUser.protocol);
            store.connect(CurrentUser.toReceive, CurrentUser.username, CurrentUser.password);

            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);

            for (Message msg : inbox.getMessages()) {
                Address[] senders = msg.getFrom();
                String sender = senders[0].toString();

                String subject;
                if (msg.getSubject() != null) {
                    subject = msg.getSubject();
                } else {
                    subject = "No Subject";
                }

                String date = msg.getReceivedDate().toString();

                String attachmentFileName = "";
                String textContent = "";

                Object content = msg.getContent();

                if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    int partCount = multipart.getCount();

                    for (int j = 0; j < partCount; j++) {
                        BodyPart bodyPart = multipart.getBodyPart(j);

                        if (bodyPart.isMimeType("text/plain")) {
                            textContent = (String) bodyPart.getContent();
                        } else if (bodyPart instanceof MimeBodyPart && Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                            MimeBodyPart attachmentPart = (MimeBodyPart) bodyPart;
                            attachmentFileName = attachmentPart.getFileName();
                            attachmentPart.saveFile(attachmentFileName);

                        }
                    }
                }

                emails.add(new Email(sender, subject, date, attachmentFileName, textContent));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return emails;
    }
}
