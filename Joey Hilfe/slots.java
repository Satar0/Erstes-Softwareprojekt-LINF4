import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// -------------- Zeitsteuerung
import javafx.animation.*;
import javafx.scene.Group;
import javafx.util.Duration;


import java.io.File;

import java.util.Random;

public class slots extends Application {

    // Tick-Dauer (ms)
    private final int atick = 100;  

    // ints
    boolean pressed = false;

    int ani = atick * 2; // länge der animation

    Random random = new Random();
  
    // Spatere Kontrolle ob sieg
    int win1 = 10;
    int win2 = 10;
    int win3 = 10;   
  
    int wahscheinlichkeit = 2;

    // Sound
    // private final Media tngTheme =
    // new Media(new File("sound/Kirbydreamlandthemesong.mp3").toURI().toString());
    // private final MediaPlayer themeSong = new MediaPlayer(tngTheme);

    // TICK
    private final PauseTransition smallPause = new PauseTransition(Duration.millis(atick));
    private final SequentialTransition smallStep = new SequentialTransition(smallPause);

    // Bild
    private final Image maschine = new Image("file:Images/Maschiene.png");
    private final ImageView Machiene = new ImageView(maschine);

    private final Image hintergrund = new Image("file:Images/Background.jpg");
    private final ImageView Hintergrund = new ImageView(hintergrund);

    private final Image hintergrund2 = new Image("file:Images/Hintergrund2.jpg");
    private final ImageView Hintergrund2 = new ImageView(hintergrund2);

    private final Image knopf = new Image("file:Images/Knopf.png");
    private final ImageView Knopf = new ImageView(knopf);

    private final Image Banane = new Image("file:Images/Banane.png");
    private final Image Erdbeere = new Image("file:Images/Erdbeere.png");
    private final Image Kirsche = new Image("file:Images/Kirsche.png");
    private final Image Orange = new Image("file:Images/Orange.png");
    private final Image Traube = new Image("file:Images/Traube.png");
    private final Image Seven = new Image("file:Images/Seven.png");
    private final Image Wassermelone = new Image("file:Images/Wassermelone.png");
    private final Image Zitrone = new Image("file:Images/Zitrone.png");

    private final ImageView Slot1 = new ImageView(Seven);
    private final ImageView Slot2 = new ImageView(Seven);
    private final ImageView Slot3 = new ImageView(Seven);
  
  
    private final Image jackpot = new Image("file:Images/Jackpot.png");
    private final ImageView Jackpot = new ImageView(jackpot);

    @Override
    public void start(Stage primaryStage) {
        double Breite = 1600;
        int Hoehe = 900;
        Pane root = new Pane();
        Scene scene = new Scene(root, Breite, Hoehe);

        // Musik
        // themeSong.setCycleCount(Timeline.INDEFINITE);
        // themeSong.play();

        // Bild platzieren / skalieren
        Machiene.setLayoutX(Breite / 3.3);
        Machiene.setLayoutY(0);
        Machiene.setFitWidth(Breite);
        Machiene.setFitHeight(Hoehe);
        Machiene.setPreserveRatio(true);

        Hintergrund.setLayoutX(0);
        Hintergrund.setLayoutY(0);
        Hintergrund.setFitWidth(Breite);
        Hintergrund.setFitHeight(Hoehe);
        Hintergrund.setPreserveRatio(false);

        Hintergrund2.setLayoutX(Breite / 2.6);
        Hintergrund2.setLayoutY(Hoehe / 3.3);
        Hintergrund2.setFitWidth(Breite / 4);
        Hintergrund2.setFitHeight(Hoehe / 4);
        Hintergrund2.setPreserveRatio(true);

        Knopf.setLayoutX(Breite / 1.5);
        Knopf.setLayoutY(Hoehe / 1.3);
        Knopf.setFitWidth(Breite / 6);
        Knopf.setFitHeight(Hoehe / 6);
        Knopf.setPreserveRatio(true);

        // slots position

        Slot1.setLayoutX(Breite / 2.53);
        Slot1.setLayoutY(Hoehe / 2.75);
        Slot1.setFitWidth(Breite / 7);
        Slot1.setFitHeight(Hoehe / 7);
        Slot1.setPreserveRatio(true);

        Slot2.setLayoutX(Breite / 2.15);
        Slot2.setLayoutY(Hoehe / 2.75);
        Slot2.setFitWidth(Breite / 7);
        Slot2.setFitHeight(Hoehe / 7);
        Slot2.setPreserveRatio(true);

        Slot3.setLayoutX(Breite / 1.87);
        Slot3.setLayoutY(Hoehe / 2.75);
        Slot3.setFitWidth(Breite / 7);
        Slot3.setFitHeight(Hoehe / 7);
        Slot3.setPreserveRatio(true);
    
        Jackpot.setLayoutX(Breite / 2.5);
        Jackpot.setLayoutY(Hoehe / 2.75);
        Jackpot.setFitWidth(Breite / 5);
        Jackpot.setFitHeight(Hoehe / 5);
        Jackpot.setPreserveRatio(true);


        Knopf.setOnMouseClicked(event -> {
        ani = 40;
        pressed = true; // start slot
            
        Jackpot.setVisible(false);
        });

        // F�gt das Bild hinzu

        root.getChildren().add(Hintergrund);
        root.getChildren().add(Hintergrund2);
        root.getChildren().add(Machiene);
        root.getChildren().add(Knopf);
        
    
       

        root.getChildren().add(Slot1);
        root.getChildren().add(Slot2);
        root.getChildren().add(Slot3);
    
        root.getChildren().add(Jackpot);
    
        Jackpot.setVisible(false);
    
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setTitle("Slots");
        primaryStage.setScene(scene);
        primaryStage.show();
    
    

        // Tick-Callback
        smallPause.setOnFinished(
        (event) -> {

            if (pressed == true) {


                // animation random Symbole
                if (ani > 20) {
                    int SymbolRandom = random.nextInt(wahscheinlichkeit);
          
          
                    
          
          
                    switch (SymbolRandom) {
                        case 0:
                            Slot1.setImage(Seven);
                            win1 = SymbolRandom;
                            break;
                        case 1:
                            Slot1.setImage(Banane);
                            win1 = SymbolRandom;
                            break;
                        case 2:
                            Slot1.setImage(Erdbeere);
                            win1 = SymbolRandom;
                            break;
                        case 3:
                            Slot1.setImage(Kirsche);
                            win1 = SymbolRandom;
                            break;
                        case 4:
                            Slot1.setImage(Orange);
                            win1 = SymbolRandom;
                            break;
                        case 5:
                            Slot1.setImage(Traube);
                            win1 = SymbolRandom;
                            break;
                        case 6:
                            Slot1.setImage(Wassermelone);
                            win1 = SymbolRandom;
                            break;
                        case 7:
                            Slot1.setImage(Zitrone);
                            win1 = SymbolRandom;
                            break;
                          
                    }
                    
                    ani = ani - 1;
                }
        
         if (ani > 10) {
                    int SymbolRandom = random.nextInt(wahscheinlichkeit);
          
          
                    
          
          
                    switch (SymbolRandom) {
                        case 0:
                            Slot2.setImage(Seven);
                            win2 = SymbolRandom;
                            break;
                        case 1:
                            Slot2.setImage(Banane);
                            win2 = SymbolRandom;
                            break;
                        case 2:
                            Slot2.setImage(Erdbeere);
                            win2 = SymbolRandom;
                            break;
                        case 3:
                            Slot2.setImage(Kirsche);
                            win2 = SymbolRandom;
                            break;
                        case 4:
                            Slot2.setImage(Orange);
                            win2 = SymbolRandom;
                            break;
                        case 5:
                            Slot2.setImage(Traube);
                            win2 = SymbolRandom;
                            break;
                        case 6:
                            Slot2.setImage(Wassermelone);
                            win2 = SymbolRandom;
                            break;
                        case 7:
                            Slot2.setImage(Zitrone);
                            win2 = SymbolRandom;
                            break;
                          
                    }
                    
                    ani = ani - 1;
                }
        
        
         if (ani > 0) {
                    int SymbolRandom = random.nextInt(wahscheinlichkeit);
          
          
                    
          
          
                    switch (SymbolRandom) {
                        case 0:
                            Slot3.setImage(Seven);
                            win3 = SymbolRandom;
                            break;
                        case 1:
                            Slot3.setImage(Banane);
                            win3 = SymbolRandom;
                            break;
                        case 2:
                            Slot3.setImage(Erdbeere);
                            win3 = SymbolRandom;
                            break;
                        case 3:
                            Slot3.setImage(Kirsche);
                            win3 = SymbolRandom;
                            break;
                        case 4:
                            Slot3.setImage(Orange);
                            win3 = SymbolRandom;
                            break;
                        case 5:
                            Slot3.setImage(Traube);
                            win3 = SymbolRandom;
                            break;
                        case 6:
                            Slot3.setImage(Wassermelone);
                            win3 = SymbolRandom;
                            break;
                        case 7:
                            Slot3.setImage(Zitrone);
                            win3 = SymbolRandom;
                            break;
                          
                    }
                    
                    ani = ani - 1;
                }
        
               if (ani == 0) {
                     pressed = false;   
                     System.out.println("Gedrückt");
                     
                     if (win1 == win2 && win2 == win3) {
                         System.out.println("WIN");
                         Jackpot.setVisible(true);
                       }
                 }
        
        
            }
      
      
        });

        smallStep.setCycleCount(Timeline.INDEFINITE);
        smallStep.play();

        

    }

    public static void main(String[] args) {
        launch(args);

    }
}
