import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;

import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

import javafx.event.ActionEvent;
import javafx.event.Event;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// -------------- Zeitsteuerung
import javafx.animation.*;
import javafx.scene.Group;
import javafx.util.Duration;

import java.io.File;

import java.util.Random;


//import GUI_Logik;
import javax.swing.*;
public class GUI extends Application{
    private Scene scene;
    private Stage stage;

    double Breite = 1600;
    int Hohe = 900;  


    private JFrame frame ;
    private JButton myGameButton;
    private JButton startConnectionButton;
    private JButton endConnectionButton;
    private JTextField ServerIPTextfeld;
    private JTextField ServerPortTextfeld;
    private GUI_Logik myLogik;


private final Image startbildlohIn = new Image("file:ImagesLogIn/hintergrundLogIn.jpg");
  private final ImageView StartbildLogIn = new ImageView(startbildlohIn);
  
 private Label ServerIP = new Label();
 private Label ServerPort = new Label();
  private Label AnmdeldenameLabel = new Label();
   private Label PasswortLabel = new Label();



    //GUI(){
    private Pane Game() {
    Pane root = new Pane();
        myLogik = new GUI_Logik();

      
        
        Button gametest = new Button("SPIEL STARTEN");
        gametest.setLayoutX(Breite / 1.2);
        gametest.setLayoutY(Hohe / 12);
        gametest.setPrefWidth(Breite / 15);
        gametest.setPrefHeight(Hohe / 20);
        gametest.setFont(new Font("Arial Black", 5));

        Button startConnectionButton = new Button("Connect");
        startConnectionButton.setLayoutX(Breite / 3);
        startConnectionButton.setLayoutY(Hohe / 1.5);
        startConnectionButton.setPrefWidth(Breite / 3);
        startConnectionButton.setPrefHeight(Hohe / 12);
        startConnectionButton.setFont(new Font("Arial", 20));

        TextField ServerIPTextfeld = new TextField();
    ServerIPTextfeld.setLayoutX(Breite / 3);
    ServerIPTextfeld.setLayoutY(Hohe / 3);
    ServerIPTextfeld.setPrefWidth(Breite / 3);
    ServerIPTextfeld.setPrefHeight(Hohe / 14);
    ServerIPTextfeld.setFont(new Font("Arial Black", 20));

    TextField ServerPortTextfeld = new TextField();
    ServerPortTextfeld.setLayoutX(Breite / 3);
    ServerPortTextfeld.setLayoutY(Hohe / 2);
    ServerPortTextfeld.setPrefWidth(Breite / 3);
    ServerPortTextfeld.setPrefHeight(Hohe / 14);
    ServerPortTextfeld.setFont(new Font("Arial Black", 20));

    ServerIP.setLayoutX(Breite / 3);
    ServerIP.setLayoutY(Hohe / 3.6);
    ServerIP.setPrefHeight(Hohe / 14);
    ServerIP.setPrefWidth(Breite/ 3);
    ServerIP.setText("Server IP");
    ServerIP.setFont(new Font("Arial Black", 30));
    ServerIP.setAlignment(Pos.CENTER);
    ServerIP.setTextFill(Color.BLACK);

    ServerPort.setLayoutX(Breite / 3);
    ServerPort.setLayoutY(Hohe / 2.25);
    ServerPort.setPrefHeight(Hohe / 14);
    ServerPort.setPrefWidth(Breite/ 3);
    ServerPort.setText("Server Port");
    ServerPort.setFont(new Font("Arial Black", 30));
    ServerPort.setAlignment(Pos.CENTER);
    ServerPort.setTextFill(Color.BLACK);
    




        startConnectionButton.setOnAction(e->{
            scene.setRoot(LogIn());

            String IP = ServerIPTextfeld.getText();
            int Port = Integer.parseInt(ServerPortTextfeld.getText());
            myLogik.startConnectionButtonKlicked(IP, Port);
        });

        root.getChildren().addAll(gametest, startConnectionButton, ServerIPTextfeld, ServerPortTextfeld, ServerPort, ServerIP);

       

       return root;
    }

    private Pane LogIn() {
    Pane root = new Pane();
    
    
        StartbildLogIn.setLayoutX(0);
        StartbildLogIn.setLayoutY(0);
        StartbildLogIn.setFitWidth(Breite);
        StartbildLogIn.setFitHeight(Hohe);
        StartbildLogIn.setPreserveRatio(false);

    
    
    TextField Anmdeldename = new TextField();
    Anmdeldename.setLayoutX(Breite / 3);
    Anmdeldename.setLayoutY(Hohe / 3);
    Anmdeldename.setPrefWidth(Breite / 3);
    Anmdeldename.setPrefHeight(Hohe / 14);
    Anmdeldename.setFont(new Font("Arial Black", 20));

    PasswordField Passwort = new PasswordField();
    Passwort.setLayoutX(Breite / 3);
    Passwort.setLayoutY(Hohe / 2);
    Passwort.setPrefWidth(Breite / 3);
    Passwort.setPrefHeight(Hohe / 14);
    Passwort.setFont(new Font("Arial Black", 20));

         Button Login = new Button("LOGIN");
        Login.setLayoutX(Breite / 3);
        Login.setLayoutY(Hohe / 1.5);
        Login.setPrefWidth(Breite / 3);
        Login.setPrefHeight(Hohe / 12);
        Login.setFont(new Font("Arial", 20));



    AnmdeldenameLabel.setLayoutX(Breite / 3);
    AnmdeldenameLabel.setLayoutY(Hohe / 3.6);
    AnmdeldenameLabel.setPrefHeight(Hohe / 14);
    AnmdeldenameLabel.setPrefWidth(Breite / 3);
    AnmdeldenameLabel.setText("Anmeldename");
    AnmdeldenameLabel.setFont(new Font("Arial Black", 30));
    AnmdeldenameLabel.setAlignment(Pos.CENTER);
    AnmdeldenameLabel.setTextFill(Color.WHITE);

    PasswortLabel.setLayoutX(Breite / 3);
    PasswortLabel.setLayoutY(Hohe / 2.3);
    PasswortLabel.setPrefHeight(Hohe / 14);
    PasswortLabel.setPrefWidth(Breite / 3);
    PasswortLabel.setText("Passwort");
    PasswortLabel.setFont(new Font("Arial Black", 30));
    PasswortLabel.setAlignment(Pos.CENTER);
    PasswortLabel.setTextFill(Color.WHITE);
    

    
    root.getChildren().addAll(StartbildLogIn, Anmdeldename, Passwort, Login, AnmdeldenameLabel, PasswortLabel);

        


         int anmeldecode = 1;
          Login.setOnAction(e->{
           Stage gameStage = new Stage();

            StartBildschirm game = new StartBildschirm(anmeldecode);   // Money übergeben
            game.start(gameStage);           // Slots starten

            stage.close();                   // Startbildschirm schließen
        });


    return root;

}
    
      public void start(Stage primaryStage) {        // setzt start stage
      // Start von Menu
      
      stage = primaryStage;
      scene = new Scene(Game(), Breite, Hohe); /// größe
      // Anfang Komponenten
      // Ende Komponenten
      stage.setScene(scene);
      stage.setTitle("Serverauswahl"); // name
      stage.show();
  }

  

    public static void main(String[] args){
 launch(args);
    //GUI myGUI = new GUI();
    }
}
