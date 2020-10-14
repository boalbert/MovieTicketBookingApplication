package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class LogInPage extends Application {

    public static TextField tfEmail = new TextField();

    @Override
    public void start(Stage stage2) {

        Text txtLogin = new Text("Sign In");
        txtLogin.setStyle("-fx-font-weight: bold");
        txtLogin.setStyle("-fx-font: 20 arial;");

        Text notACustomer = new Text("Not a customer yet? Register now!");

        tfEmail.setPromptText("E-Mail");

        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");

        Button bntLogIn = new Button("Sign In");
        Button bntCreateAccount = new Button("Create Account");

        Label lblErrorSignIn = new Label("Incorrect details, please try again");
        lblErrorSignIn.setVisible(false);

        Image imageSF = new Image("file:src/sample/logoCompany.jpg");
        ImageView imageViewSF = new ImageView(imageSF);

        imageViewSF.setPreserveRatio(true);
        imageViewSF.setFitHeight(120);
        imageViewSF.setFitWidth(120);


        GridPane gridPane2 = new GridPane();
        gridPane2.setVgap(5);
        gridPane2.setHgap(5);
        gridPane2.setAlignment(Pos.TOP_CENTER);

        gridPane2.add(txtLogin, 1, 3);
        gridPane2.add(imageViewSF, 3, 1);
        gridPane2.add(tfEmail, 1, 4);
        gridPane2.add(pfPassword, 2, 4);
        gridPane2.add(bntLogIn, 1, 6);
        gridPane2.add(lblErrorSignIn, 2, 6);
        gridPane2.add(notACustomer, 1, 12, 2, 1);
        gridPane2.add(bntCreateAccount, 1, 13);


        stage2.setTitle("Log-in");
        Scene scene2 = new Scene(gridPane2, 400, 400);
        stage2.setScene(scene2);
        stage2.show();

        // Button -> Create Account
        // Goes to Create Account
        bntCreateAccount.setOnAction(actionEvent -> {
            stage2.close();
            CreateAccount stage = new CreateAccount();
            stage.start(stage2);
        });

        // Button -> Log in
        // Tests if account exists in database
        bntLogIn.setOnAction(actionEvent -> {

            try (Connection conn = DriverManager.getConnection(Datasource.CONNECTION_STRING); Statement statement = conn.createStatement()) {

                ResultSet resultEmail = statement.executeQuery("SELECT * FROM " + Datasource.TABLE_CUSTOMER + " WHERE " + Datasource.COLUMN_EMAIL + "='" + tfEmail.getText() + "'");
                ResultSet resultPassword = statement.executeQuery("SELECT * FROM " + Datasource.TABLE_CUSTOMER + " WHERE " + Datasource.COLUMN_PASSWORD + "='" + pfPassword.getText() + "'");

                String email = resultEmail.getString(Datasource.COLUMN_EMAIL);
                System.out.println("Email in database: " + email);

                String password = resultPassword.getString(Datasource.COLUMN_PASSWORD);
                System.out.println("Password in database : " + password);

                if (tfEmail.getText().equalsIgnoreCase(email) && pfPassword.getText().equalsIgnoreCase(password)) {

                    System.out.println("Welcome!");

                    Payment payment = new Payment();
                    payment.start(stage2);

                } else {
                    lblErrorSignIn.setVisible(true);
                }


            } catch (
                    SQLException e) {
                System.out.println("Something went wrong: " + e.getMessage());
                e.printStackTrace();
            }


        });

    }
//    private void checkUserCredentials(String userEmail) {
//
//
//    }
}
