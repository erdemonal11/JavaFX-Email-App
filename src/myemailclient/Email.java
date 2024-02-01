package myemailclient;

import javafx.beans.property.*;

public class Email {

    private final StringProperty sender;
    private final StringProperty subject;
    private final StringProperty date;
    private final StringProperty attachmentFileName;
    private final StringProperty content;

    public Email(String sender, String subject, String date, String attachmentFileName, String content) {
        this.sender = new SimpleStringProperty(sender);
        this.subject = new SimpleStringProperty(subject);
        this.date = new SimpleStringProperty(date);
        this.attachmentFileName = new SimpleStringProperty(attachmentFileName);
        this.content = new SimpleStringProperty(content);
    }

    public String getSender() {
        return sender.get();
    }

    public String getSubject() {
        return subject.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getAttachmentFileName() {
        return attachmentFileName.get();
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty senderProperty() {
        return sender;
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty attachmentFileNameProperty() {
        return attachmentFileName;
    }

    public StringProperty contentProperty() {
        return content;
    }

}
