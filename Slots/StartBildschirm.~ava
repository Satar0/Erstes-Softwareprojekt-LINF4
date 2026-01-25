import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
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

public class StartBildschirm extends Application {

    private int Money = 100;

    private Stage stage;
    private Scene scene;
  
  private final Image startbild = new Image("file:Images Start/startbild.jpg");
  private final ImageView Startbild = new ImageView(startbild);
  
  
  private final Image background = new Image("file:Images Start/background.png");
  private final ImageView Background = new ImageView(background);
  
  private final Image Slotsicon = new Image("file:Images Start/SlotIcon1.png");
  private final ImageView SlotsIcon = new ImageView(Slotsicon);
  
  private final Image Pokericon = new Image("file:Images Start/PokerIcon.png");
  private final ImageView PokerIcon = new ImageView(Pokericon);
  
  private final Image Schachicon = new Image("file:Images Start/SchachIcon.png");
  private final ImageView SchachIcon = new ImageView(Schachicon);
  
  private final Image Unoicon = new Image("file:Images Start/UnoIcon.png");
  private final ImageView UnoIcon = new ImageView(Unoicon);
  
  
  

    private Pane StartBildschrim() {
        Pane root = new Pane();
    
        
        Startbild.setLayoutX(0);
        Startbild.setLayoutY(0);
        Startbild.setFitWidth(1600);
        Startbild.setFitHeight(900);
        Startbild.setPreserveRatio(false);
    

        Button startGame = new Button("SPIEL STARTEN");
        startGame.setLayoutX(650);
        startGame.setLayoutY(400);
        startGame.setPrefWidth(300);
        startGame.setPrefHeight(80);
        startGame.setFont(new Font("Arial Black", 24));

        Button exit = new Button("BEENDEN");
        exit.setLayoutX(650);
        exit.setLayoutY(500);
        exit.setPrefWidth(300);
        exit.setPrefHeight(60);
        exit.setFont(new Font("Arial", 20));

        startGame.setOnAction(e -> {

           scene.setRoot(Menu());
        });                                                       

        exit.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(Startbild, startGame, exit);
        return root;
    }
  
   private Pane Menu() {
        Pane root = new Pane();
    
        Glow glow = new Glow();
        glow.setLevel(0.7);

        Glow glowOff = new Glow();
        glowOff.setLevel(0);
        
    
        Background.setLayoutX(0);
        Background.setLayoutY(0);
        Background.setFitWidth(1600);
        Background.setFitHeight(900);
        Background.setPreserveRatio(false);
    
    
        SlotsIcon.setLayoutX(900);
        SlotsIcon.setLayoutY(-15);
        SlotsIcon.setFitWidth(650);
        SlotsIcon.setFitHeight(600);
        SlotsIcon.setPreserveRatio(false);
        SlotsIcon.setOnMouseEntered(e -> SlotsIcon.setEffect(glow));
        SlotsIcon.setOnMouseExited(e -> SlotsIcon.setEffect(glowOff));

        PokerIcon.setLayoutX(0);
        PokerIcon.setLayoutY(150);
        PokerIcon.setFitWidth(1600);
        PokerIcon.setFitHeight(900);
        PokerIcon.setPreserveRatio(false);
        PokerIcon.setOnMouseEntered(e -> PokerIcon.setEffect(glow));
        PokerIcon.setOnMouseExited(e -> PokerIcon.setEffect(glowOff));
    
        SchachIcon.setLayoutX(0);
        SchachIcon.setLayoutY(600);
        SchachIcon.setFitWidth(800);
        SchachIcon.setFitHeight(300);
        SchachIcon.setPreserveRatio(false);
        SchachIcon.setOnMouseEntered(e -> SchachIcon.setEffect(glow));
        SchachIcon.setOnMouseExited(e -> SchachIcon.setEffect(glowOff));
    
        UnoIcon.setLayoutX(780);
        UnoIcon.setLayoutY(580);
        UnoIcon.setFitWidth(900);
        UnoIcon.setFitHeight(420);
        UnoIcon.setPreserveRatio(false);
        UnoIcon.setOnMouseEntered(e -> UnoIcon.setEffect(glow));
        UnoIcon.setOnMouseExited(e -> UnoIcon.setEffect(glowOff));
       
        
    
        

        SlotsIcon.setOnMouseClicked(e -> {

            Stage gameStage = new Stage();

            slots game = new slots(Money);   // Money übergeben
            game.start(gameStage);           // Slots starten

            stage.close();                   // Startbildschirm schließen
        });

        PokerIcon.setOnMouseClicked(e -> {

            /*Stage gameStage = new Stage();

            poker game = new poker(Money);   // Money übergeben
            game.start(gameStage);           // Slots starten

            stage.close(); */                  // Startbildschirm schließen
        });
        root.getChildren().addAll(Background);
        root.getChildren().addAll(SlotsIcon, PokerIcon, UnoIcon,SchachIcon);
        
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        this.scene = new Scene(StartBildschrim(), 1600, 900);
        stage.setScene(scene);
        stage.setTitle("Start Bildschirm");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

