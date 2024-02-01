package myemailclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myemailclient.Email;

public class MainController implements Initializable {

    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnComposeNew;
    @FXML
    private Button btnReply;
    @FXML
    private Button btnNewHost;
    @FXML
    private Button btnInbox;
    @FXML
    private Button btnSent;
    @FXML
    private Label lblHost;
    @FXML
    private TableView<Email> tblMail; 
    @FXML
    private TableColumn<Email, String> tcSender;
    @FXML
    private TableColumn<Email, String> tcSubject;
    @FXML
    private TableColumn<Email, String> tcDate;
    @FXML
    private TableColumn<Email, String> tcAttachment; 

    @FXML
    private TextArea taMailContent;

    private ReceiveEmail emailReceiver;

    private List<Email> emails;

    private Email selectedEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        taMailContent.setEditable(false);
        lblHost.setText("Current Host: " + CurrentUser.username);
        emailReceiver = new ReceiveEmail();

        tcSender.setCellValueFactory(cellData -> cellData.getValue().senderProperty());
        tcSubject.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        tcDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        tcAttachment.setCellValueFactory(cellData -> cellData.getValue().attachmentFileNameProperty());

        emails = emailReceiver.retrieveEmails(); 
        tblMail.getItems().addAll(emails);
        
        tblMail.getSelectionModel().selectedItemProperty().addListener(
                (observable, mail, selectedMail) -> showEmailDetails(selectedMail));

        btnComposeNew.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SendMail.fxml"));
            try {
                Parent root = loader.load();
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (Exception exception) {
            }

        });

        btnReply.setOnAction(e -> {
            if (selectedEmail != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SendMail.fxml"));
                try {
                    Parent root = loader.load();
                    SendMailController sendMailController = loader.getController();

                    sendMailController.setReplyMode(selectedEmail.getSender(), "Re: " + selectedEmail.getSubject());

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        btnRefresh.setOnAction(e -> {
            lblHost.setText("Current Host: " + CurrentUser.username);
            emails.clear();
            tblMail.getItems().clear();
            tcSender.setCellValueFactory(cellData -> cellData.getValue().senderProperty());
            tcSubject.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
            tcDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            tcAttachment.setCellValueFactory(cellData -> cellData.getValue().attachmentFileNameProperty());

            emails = emailReceiver.retrieveEmails(); 
            tblMail.getItems().addAll(emails);

            tblMail.getSelectionModel().selectedItemProperty().addListener(
                    (observable, mail, selectedMail) -> showEmailDetails(selectedMail));
        });

        btnNewHost.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NewHost.fxml"));
            try {
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (Exception exception) {
            }
        });

    }

    private void showEmailDetails(Email email) {
        selectedEmail = email;
        taMailContent.setText(email.getContent());
    }
}
