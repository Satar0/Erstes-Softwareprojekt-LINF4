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

public class slots extends Application {

  private Animation gameLoop;
  private Scene scene;
  private Stage stage;

  // Tick-Dauer (ms)
  // Anfang Attribute
  private final int atick = 100;

  // ints

  int playerMoney = 10000;

  public slots(int startMoney) {
    

      this.playerMoney = startMoney; // nimmt wert der übergeben wurde

   

     
    
  }        

  // Kontrollen

  boolean pressed = false;
  boolean SoundCheck = true;
  boolean cheat = false;
  boolean fast = false;
  
  boolean final1  = false;
  boolean final2  = false;
  boolean final3  = false;
  
  
  
  int ani;

  Random random = new Random();

  int durchlaufSlot1 = 0;
  int durchlaufSlot2 = 0;
  int durchlaufSlot3 = 0;

  // Spatere Kontrolle ob sieg
  int win1;
  int win2;
  int win3;

  int wahscheinlichkeit = 8; // zwischen 1 und 8 (bis zu acht symbolen)     randomizer animatrion
  
  int wahrscheinlichkeitenfinal = 100; //festlegung wahrscheinlichkeiten
  

  // Sound
  private final Media backgroundmusik = new Media(new File("sound slot/BackgrundMusik.mp3").toURI().toString());
  private final MediaPlayer BackgroundMusik = new MediaPlayer(backgroundmusik);

  private final Media Slotsound = new Media(new File("sound slot/Slot Machine Jackpot Sound Effect.mp3").toURI().toString());
  private final MediaPlayer SlotSound = new MediaPlayer(Slotsound);

  private final Media win = new Media(new File("sound slot/WinSound.mp3").toURI().toString());
  private final MediaPlayer WinSound = new MediaPlayer(win);

  private final Media klick = new Media(new File("sound slot/Klick.m4a").toURI().toString());
  private final MediaPlayer Klick = new MediaPlayer(klick);

  private final Media coinsound = new Media(new File("sound slot/CoinSound.mp3").toURI().toString());
  private final MediaPlayer CoinSound = new MediaPlayer(coinsound);
  
  private final Media buzzer = new Media(new File("sound slot/buzzer.m4a").toURI().toString());
  private final MediaPlayer Buzzer = new MediaPlayer(buzzer);

  // TICK
  private final PauseTransition smallPause = new PauseTransition(Duration.millis(atick));
  private final SequentialTransition smallStep = new SequentialTransition(smallPause);

  // Bild
  private final Image maschine = new Image("file:Images slot/Maschiene.png");
  private final ImageView Machiene = new ImageView(maschine);

  private final Image hintergrund = new Image("file:Images slot/background.png"); // alternative Background.jpg
  private final ImageView Hintergrund = new ImageView(hintergrund);

  private final Image hintergrund2 = new Image("file:Images slot/Hintergrund2.jpg");
  private final ImageView Hintergrund2 = new ImageView(hintergrund2);

  private final Image knopf = new Image("file:Images slot/Knopf.png");
  private final ImageView Knopf = new ImageView(knopf);

  private final Image Banane = new Image("file:Images slot/Banane.png");
  private final Image Erdbeere = new Image("file:Images slot/Erdbeere.png");
  private final Image Kirsche = new Image("file:Images slot/Kirsche.png");
  private final Image Orange = new Image("file:Images slot/Orange.png");
  private final Image Traube = new Image("file:Images slot/Traube.png");
  private final Image Seven = new Image("file:Images slot/Seven.png");
  private final Image Wassermelone = new Image("file:Images slot/Wassermelone.png");
  private final Image Zitrone = new Image("file:Images slot/Zitrone.png");

  private final ImageView Slot1 = new ImageView(Seven);
  private final ImageView Slot2 = new ImageView(Seven);
  private final ImageView Slot3 = new ImageView(Seven);

  private final Image jackpot = new Image("file:Images slot/Jackpot.gif");
  private final ImageView Jackpot = new ImageView(jackpot);
  
  
  private final Image pair = new Image("file:Images slot/Pair.gif");
  private final ImageView Pair = new ImageView(pair);
  

  private final Image blinken = new Image("file:Images slot/Blinken.gif");
  private final Image blinkenoben = new Image("file:Images slot/BlinkenOben.gif");

  private final ImageView Blinken1 = new ImageView(blinken);
  private final ImageView Blinken2 = new ImageView(blinken);
  private final ImageView Blinken3 = new ImageView(blinkenoben);

  private final Image coin = new Image("file:Images slot/Coin.png");
  private final ImageView Coin = new ImageView(coin);

  private final Image anSound = new Image("file:Images slot/SoundAn.png");
  private final Image ausSound = new Image("file:Images slot/SoundAus.png");
  private final ImageView Sound = new ImageView(anSound);

  private final Image exit = new Image("file:Images slot/Exit.png");
  private final ImageView Exit = new ImageView(exit);

  private Label Money = new Label();

  private ToggleButton Cheat = new ToggleButton();
  // Ende Attribute

  // Menu Bildschirm erstellung
  private Pane Menu() {
    Pane root = new Pane();

    // Buttons und so
    Button start = new Button("START");
    start.setLayoutX(700);
    start.setLayoutY(400);

    // Action der Game startet
    start.setOnAction(e -> {
      scene.setRoot(Game());
      Sound.setImage(anSound);
      SoundCheck = true;
      });

    Button exitB = new Button("EXIT");
    exitB.setLayoutX(700);
    exitB.setLayoutY(460);

    // Action das Fenster Schließt
    exitB.setOnAction(e -> {

      Stage gameStage = new Stage();

      StartBildschirm game = new StartBildschirm(); // money ubergabe
      game.start(gameStage); // start von slots

      stage.close(); // dieses fenster schließen

    });          

    // fugt knopfe hinzu
    root.getChildren().addAll(start, exitB);
    return root;
  }

  private Pane Game() {
    // Fenster Erstellen

    double Breite = 1600;
    int Hoehe = 900;
    Pane root = new Pane();

    // Musik
    BackgroundMusik.setCycleCount(Timeline.INDEFINITE);
    BackgroundMusik.setVolume(0.3);
    BackgroundMusik.play();

    SlotSound.setVolume(0.3);

    // Effekte
    Glow glow = new Glow();
    glow.setLevel(1);

    Glow glowhalb = new Glow();
    glow.setLevel(0.5);

    Glow offglow = new Glow();
    offglow.setLevel(0);

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

    // slots

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
    Jackpot.setFitWidth(Breite / 2.8);
    Jackpot.setFitHeight(Hoehe / 2.8);
    Jackpot.setPreserveRatio(true);
    
    Pair.setLayoutX(Breite / 2.5);
    Pair.setLayoutY(Hoehe / 2.75);
    Pair.setFitWidth(Breite / 2.8);
    Pair.setFitHeight(Hoehe / 2.8);
    Pair.setPreserveRatio(true);
    
    

    Blinken1.setLayoutX(Breite / 2.715);
    Blinken1.setLayoutY(Hoehe / 2.82);
    Blinken1.setFitWidth(Breite / 41);
    Blinken1.setFitHeight(Hoehe / 6.1);
    Blinken1.setPreserveRatio(false);

    Blinken2.setLayoutX(Breite / 1.652);
    Blinken2.setLayoutY(Hoehe / 2.82);
    Blinken2.setFitWidth(Breite / 41);
    Blinken2.setFitHeight(Hoehe / 6.1);
    Blinken2.setPreserveRatio(false);

    Blinken3.setLayoutX(Breite / 2.19);
    Blinken3.setLayoutY(Hoehe / 4.37);
    Blinken3.setFitWidth(Breite / 11.4);
    Blinken3.setFitHeight(Hoehe / 32);
    Blinken3.setPreserveRatio(false);

    // Design

    Coin.setLayoutX(Breite / 1.1);
    Coin.setLayoutY(Hoehe / 11);
    Coin.setFitWidth(Breite / 8);
    Coin.setFitHeight(Hoehe / 8);
    Coin.setPreserveRatio(true);

    Sound.setLayoutX(Breite / 10);
    Sound.setLayoutY(Hoehe / 9);
    Sound.setFitWidth(Breite / 15);
    Sound.setFitHeight(Hoehe / 15);
    Sound.setPreserveRatio(true);

    Exit.setLayoutX(Breite / 18);
    Exit.setLayoutY(Hoehe / 9);
    Exit.setFitWidth(Breite / 15);
    Exit.setFitHeight(Hoehe / 15);
    Exit.setPreserveRatio(true);

    Money.setLayoutX(Breite / 1.18);
    Money.setLayoutY(Hoehe / 11);
    Money.setPrefHeight(100);
    Money.setPrefWidth(110);
    Money.setText(String.valueOf(playerMoney));
    Money.setFont(new Font("Arial Black", 30));
    Money.setAlignment(Pos.CENTER_RIGHT);
    Money.setTextFill(Color.DEEPSKYBLUE);
    Money.setEffect(glowhalb);

    Cheat.setLayoutX(Breite / 1.15);
    Cheat.setLayoutY(Hoehe / 3);
    Cheat.setPrefHeight(50);
    Cheat.setPrefWidth(50);
    Cheat.setText("");
    Cheat.setOpacity(0); // unsichtbar, aber klickbar
    Cheat.setPickOnBounds(true); // Klick auf leere Fläche zählt auch

    Cheat.setOnAction(event -> Cheaten(event));

    // Fugt alles hinzu

    root.getChildren().add(Hintergrund);
    root.getChildren().add(Hintergrund2);
    root.getChildren().add(Machiene);
    root.getChildren().add(Knopf);

    root.getChildren().add(Slot1);
    root.getChildren().add(Slot2);
    root.getChildren().add(Slot3);

    root.getChildren().add(Jackpot);
    root.getChildren().add(Pair);

    root.getChildren().add(Blinken1);
    root.getChildren().add(Blinken2);
    root.getChildren().add(Blinken3);

    root.getChildren().add(Coin);
    root.getChildren().add(Money);
    root.getChildren().add(Cheat);
    root.getChildren().add(Sound);
    root.getChildren().add(Exit);

    // unsichbar machen
    Jackpot.setVisible(false);
    Pair.setVisible(false);

    Knopf.setOnMouseClicked(event -> {

      // geld kontrolle
      if (fast == true) {
        ani = 0;
        pressed = true;
        
        final1 = true;
        final2 = true;
        final3 = true;
        
        
        SlotSound.stop();
        }
      else {
       
      if (playerMoney >= 10) {
            
      
            fast = true;
            ani = 40;
            pressed = true; // start slot

        playerMoney = playerMoney - 10;
        Money.setText(String.valueOf(playerMoney));

        // Effect reset
        Slot1.setEffect(offglow);
        Slot2.setEffect(offglow);
        Slot3.setEffect(offglow);

        // Sound Start
        SlotSound.seek(Duration.ZERO);
        SlotSound.setCycleCount(1);
        SlotSound.play();

        // macht glaube nichts
        durchlaufSlot1 = 0;
        durchlaufSlot2 = 0;
        durchlaufSlot3 = 0;

           WinSound.stop();
          Jackpot.setVisible(false);
           Pair.setVisible(false);
      }     
        else {
          Buzzer.seek(Duration.ZERO);
          Buzzer.setCycleCount(1);
          Buzzer.play();  
         }
        
       }
        
      
      
        

    });

    Sound.setOnMouseClicked(event -> {

      // sound muten oder an

      if (SoundCheck == true) {
        Sound.setImage(ausSound);
        SoundCheck = false;
        BackgroundMusik.setVolume(0);
        Klick.setVolume(0);
        WinSound.setVolume(0);
        SlotSound.setVolume(0);
        CoinSound.setVolume(0);
      } else {
        Sound.setImage(anSound);
        SoundCheck = true;
        BackgroundMusik.setVolume(0.3);
        Klick.setVolume(1);
        WinSound.setVolume(1);
        SlotSound.setVolume(0.3);
        CoinSound.setVolume(1);
      }

    });

    Exit.setOnMouseClicked(event -> {
      // game pause

      /* System.exit(0);
      if (gameLoop != null) {
        gameLoop.stop();
      }
      
      
      BackgroundMusik.stop();
      SlotSound.stop();
      WinSound.stop();
      Klick.stop();
      CoinSound.stop();
      scene.setRoot(Menu());      */
      
      Stage gameStage = new Stage();

      StartBildschirm game = new StartBildschirm(); // money ubergabe
      game.start(gameStage); // start von slots

      stage.close(); // dieses fenster schließen

    });

    Machiene.setOnMouseClicked(event -> {
      // sound für machine

      CoinSound.seek(Duration.ZERO);
      CoinSound.setCycleCount(1);
      CoinSound.play();

    });

    // Tick-Callback
    smallPause.setOnFinished(
        (event) -> {
        
          if (pressed == true) {
             
            // animation random Symbole und festlegung wenn vorbei
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
          
             
            }
        
         
        if (ani == 21) {
                 

                 final1 = true;
               }
        
        
            if (final1 == true)  {
                int r = random.nextInt(wahrscheinlichkeitenfinal); // 0..99
             
                final1 = false;
          

          // Range-Verteilung:
          // 0-4   : Seven (5%)
          // 5-24  : Banane (20%)
          // 25-42 : Erdbeere (18%)
          // 43-57 : Kirsche (15%)
          // 58-72 : Orange (15%)
          // 73-84 : Traube (12%)
          // 85-94 : Wassermelone (10%)
           // 95-99 : Zitrone (5%)

          if (r < 5) {
            Slot1.setImage(Seven);
            win1 = 0;
          }
            else if (r < 25) {
              Slot1.setImage(Banane);
              win1 = 1;
            }
              else if (r < 43) {
                Slot1.setImage(Erdbeere);
                win1 = 2;
              }
                else if (r < 58) {
                  Slot1.setImage(Kirsche);
                  win1 = 3;
                }
                  else if (r < 73) {
                     Slot1.setImage(Orange);
                    win1 = 4;
                  }
                    else if (r < 85) {
                      Slot1.setImage(Traube);      
                      win1 = 5;
                    }
                      else if (r < 95) {
                        Slot1.setImage(Wassermelone);
                        win1 = 6;
                      }
                      else {
                        Slot1.setImage(Zitrone);
                        win1 = 7;
                      }
          
          
          
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
              if (ani == 11) {
            
                 final2 = true;
               }
            }
      
        if (final2 == true)  {
                int r = random.nextInt(wahrscheinlichkeitenfinal); // 0..99
        
          final2 = false;
        

          // Range-Verteilung:
          // 0-4   : Seven (5%)
          // 5-24  : Banane (20%)
          // 25-42 : Erdbeere (18%)
          // 43-57 : Kirsche (15%)
          // 58-72 : Orange (15%)
          // 73-84 : Traube (12%)
          // 85-94 : Wassermelone (10%)
           // 95-99 : Zitrone (5%)

          if (r < 5) {
            Slot2.setImage(Seven);
            win2 = 0;
          }
            else if (r < 25) {
              Slot2.setImage(Banane);
              win2 = 1;
            }
              else if (r < 43) {
                Slot2.setImage(Erdbeere);
                win2 = 2;
              }
                else if (r < 58) {
                  Slot2.setImage(Kirsche);
                  win2 = 3;
                }
                  else if (r < 73) {
                     Slot2.setImage(Orange);
                    win2 = 4;
                  }
                    else if (r < 85) {
                      Slot2.setImage(Traube);      
                      win2 = 5;
                    }
                      else if (r < 95) {
                        Slot2.setImage(Wassermelone);
                        win2 = 6;
                      }
                      else {
                        Slot2.setImage(Zitrone);
                        win2 = 7;
                      }
          
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
          
           if (ani == 1) {
            
                 final3 = true;
               }

            }
             if (final3 == true)  {
                int r = random.nextInt(wahrscheinlichkeitenfinal); // 0..99
        
                final3 = false;
        

          // Range-Verteilung:
          // 0-4   : Seven (5%)
          // 5-24  : Banane (20%)
          // 25-42 : Erdbeere (18%)
          // 43-57 : Kirsche (15%)
          // 58-72 : Orange (15%)
          // 73-84 : Traube (12%)
          // 85-94 : Wassermelone (10%)
           // 95-99 : Zitrone (5%)

          if (r < 5) {
            Slot3.setImage(Seven);
            win3 = 0;
          }
            else if (r < 25) {
              Slot3.setImage(Banane);
              win3 = 1;
            }
              else if (r < 43) {
                Slot3.setImage(Erdbeere);
                win3 = 2;
              }
                else if (r < 58) {
                  Slot3.setImage(Kirsche);
                  win3 = 3;
                }
                  else if (r < 73) {
                     Slot3.setImage(Orange);
                    win3 = 4;
                  }
                    else if (r < 85) {
                      Slot3.setImage(Traube);      
                      win3 = 5;
                    }
                      else if (r < 95) {
                        Slot3.setImage(Wassermelone);
                        win3 = 6;
                      }
                      else {
                        Slot3.setImage(Zitrone);
                        win3 = 7;
                      }
          
        }
            // sound wenn fertig

            if (ani == 21) {

              if (durchlaufSlot1 == 0) {
                Klick.seek(Duration.ZERO);
                Klick.setStopTime(Duration.seconds(1));
                Klick.play();

                durchlaufSlot1 = 1;
              }

            }
            if (ani < 11) {

              if (durchlaufSlot2 == 0) {
                Klick.seek(Duration.ZERO);
                Klick.setStopTime(Duration.seconds(1));
                Klick.play();

                durchlaufSlot2 = 1;
              }
            }
            if (ani == 2) {

              if (durchlaufSlot3 == 0) {
                Klick.seek(Duration.ZERO);
                Klick.setStopTime(Duration.seconds(1));
                Klick.play();

                durchlaufSlot3 = 1;
              }
            }

            // Effekt wenn vorbei glow

            if (ani < 20) {
              Slot1.setEffect(glow);

            }
            if (ani < 10) {
              Slot2.setEffect(glow);

            }
            if (ani == 0) {
              Slot3.setEffect(glow);

            }

            // kontrolle ob gewonnen
            if (ani <= 0) {
              pressed = false;
              fast = false;
              
            
           boolean triple = (win1 == win2 && win2 == win3);
           boolean pair   = !triple && (win1 == win2 || win1 == win3 || win2 == win3);

            int payout = 0;

    // =========================
    // 3 GLEICHE (JACKPOT)
    // =========================
           if (triple) {

            Jackpot.setVisible(true);

             switch (win1) {
              case 0: payout = 800; break; // Seven (selten)
              case 1: payout = 150; break; // Banane (häufig)
              case 2: payout = 180; break; // Erdbeere
              case 3: payout = 220; break; // Kirsche
              case 4: payout = 220; break; // Orange
              case 5: payout = 300; break; // Traube
              case 6: payout = 380; break; // Wassermelone
              case 7: payout = 800; break; // Zitrone (selten)
            }

            WinSound.seek(Duration.ZERO);
            WinSound.setCycleCount(1);
            WinSound.play();
            }

          // =========================
          // GENAU 2 GLEICHE
          // =========================
            else if (pair) {
              Pair.setVisible(true);
              int pairSymbol;

              if (win1 == win2) pairSymbol = win1;
               else if (win1 == win3) pairSymbol = win1;
                else pairSymbol = win2; // win2 == win3

              switch (pairSymbol) {
                case 0: payout = 25; break; // Seven
                case 1: payout = 10; break; // Banane
                case 2: payout = 12; break; // Erdbeere
                case 3: payout = 15; break; // Kirsche
                 case 4: payout = 15; break; // Orange
                 case 5: payout = 18; break; // Traube
                case 6: payout = 20; break; // Wassermelone
                 case 7: payout = 25; break; // Zitrone
                      }
                      }

          // =========================
          // AUSZAHLEN
          // =========================
          if (payout > 0) {
             playerMoney += payout;
          Money.setText(String.valueOf(playerMoney));
                                                      }

            

          }
         }
          ani = ani - 1; // roulet dauer
        });

    // start
    gameLoop = smallStep;
    gameLoop.setCycleCount(Timeline.INDEFINITE);
    gameLoop.play();

    return root;
           }
  // Anfang Methoden

    public void Cheaten(Event evt) {
    // wahscheinlichkeit runter stellen

    if (cheat == false) {
      wahrscheinlichkeitenfinal = 1;
      
      if (playerMoney < 10) {
        playerMoney = 10 ;
        Money.setText(String.valueOf(playerMoney));
        
        }
      
      System.out.println("Saihde Kebab Scherrif");
      cheat = true;
    }

    else {
      wahrscheinlichkeitenfinal = 100;
      
      cheat = false;
    }
  }

  public void start(Stage primaryStage) {
    // Start von Menu

    stage = primaryStage;
    scene = new Scene(Game(), 1600, 900); /// größe
    stage.setScene(scene);
    stage.setTitle("Slots"); // name
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);

  }
  // Ende Methoden
}
