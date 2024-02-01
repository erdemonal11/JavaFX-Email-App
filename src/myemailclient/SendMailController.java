package myemailclient;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SendMailController implements Initializable {

    @FXML
    private TextField tfTo;
    @FXML
    private TextField tfSubject;
    @FXML
    private Button btnAddFile;
    @FXML
    private Button btnSend;
    @FXML
    private TextArea tfContent;

    @FXML
    private Label lblFileName;

    private String filePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnAddFile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose File");
            Stage stage = new Stage();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                filePath = selectedFile.getAbsolutePath();

                String[] fileNameArray = filePath.split("[\\\\/]");
                String fileName = fileNameArray[fileNameArray.length - 1];

                lblFileName.setText(fileName);
            }
        });

        btnSend.setOnAction(e -> {
            if (tfTo.getText().isEmpty()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SendMailError.fxml"));
                try {
                    Parent root = loader.load();

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception exception) {
                }
            }

            String to = tfTo.getText();
            String subject = tfSubject.getText();
            String content = tfContent.getText();

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");

            Authenticator a = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(CurrentUser.username, CurrentUser.password);
                }
            };

            Session session = Session.getDefaultInstance(props, a);

            try {
                MimeMessage msg = new MimeMessage(session);
                msg.setFrom("emailprojectjava@gmail.com");
                msg.setRecipients(Message.RecipientType.TO, to);
                msg.setSubject(subject);
                msg.setSentDate(new Date());

                Multipart mp = new MimeMultipart();
                BodyPart bp = new MimeBodyPart();
                bp.setText(content);
                mp.addBodyPart(bp);

                if (filePath != null && !filePath.isEmpty()) {
                    BodyPart partForAtt = new MimeBodyPart();
                    String filename = filePath;
                    DataSource source = new FileDataSource(filename);
                    partForAtt.setDataHandler(new DataHandler(source));
                    String fileNameArray[] = filename.split("[\\\\/]");
                    String fileName = fileNameArray[fileNameArray.length - 1];
                    partForAtt.setFileName(fileName);
                    mp.addBodyPart(partForAtt);
                }

                msg.setContent(mp);

                try {
                    Transport.send(msg);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("MailSent.fxml"));
                    try {
                        Parent root = loader.load();

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception exception) {
                    }
                    Stage stage2 = (Stage) btnSend.getScene().getWindow();
                    stage2.close();

                } catch (MessagingException mex) {
                    System.out.println("Send failed, exception: " + mex);
                }

            } catch (MessagingException mex) {
                System.out.println("Send failed, exception: " + mex);
            }

        });
    }

    public void setReplyMode(String to, String subject) {
        tfTo.setText(to);
        tfSubject.setText(subject);
    }
}
