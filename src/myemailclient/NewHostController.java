package myemailclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class NewHostController implements Initializable {

    @FXML
    private RadioButton rbImap;
    @FXML
    private ToggleGroup protocol;
    @FXML
    private RadioButton rbPop3;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblReceivePort;
    @FXML
    private Label lblSendPort;
    @FXML
    private Label lblHostToReceive;
    @FXML
    private Label lblHostToSend;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSave.setOnAction(e -> {
            CurrentUser.username = tfUsername.getText();
            CurrentUser.password = tfPassword.getText();
            Stage stage = new Stage();
            stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        });
    }

    @FXML
    private void changeProtocol(ActionEvent event) {
        RadioButton rb = ((RadioButton) event.getSource());
        if (rb.isSelected()) {
            if (rb.equals(rbImap)) {
                CurrentUser.protocol = "imaps";
                lblReceivePort.setText("993");
                CurrentUser.receivePort = lblReceivePort.getText();
                lblSendPort.setText("465");
                CurrentUser.sendPort = lblSendPort.getText();
                lblHostToReceive.setText("imap.gmail.com");
                CurrentUser.toReceive = lblHostToReceive.getText();
                lblHostToSend.setText("smtp.gmail.com");
                CurrentUser.toSend = lblHostToSend.getText();
            } else if (rb.equals(rbPop3)) {
                CurrentUser.protocol = "pop3";
                lblReceivePort.setText("995");
                CurrentUser.receivePort = lblReceivePort.getText();
                lblSendPort.setText("465");
                CurrentUser.sendPort = lblSendPort.getText();
                lblHostToReceive.setText("pop.gmail.com	");
                CurrentUser.toReceive = lblHostToReceive.getText();
                lblHostToSend.setText("smtp.gmail.com");
                CurrentUser.toSend = lblHostToSend.getText();
            }

        }
    }

}
