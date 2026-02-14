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



public class SlotsGUI extends Application {

  private Animation gameLoop;
  private Scene scene;
  private Stage stage;
  
  double Breite = 1600;
  int Hoehe = 900;                                                 // größe und erstellung der stage für später
  

  // Tick-Dauer (ms)
  // Anfang Attribute
  private final int atick = 100;

  // ints

  int playerMoney = 1;

  
    
    
       
     
  public SlotsGUI(int startMoney)  {
  
   playerMoney = startMoney;
  
    }
         

     
    
   Random random = new Random();       

  // Kontrollen

  boolean pressed = false;
  boolean SoundCheck = true;
  boolean cheat = false;
  boolean fast = false;
  
  boolean final1  = false;
  boolean final2  = false;             //festlegung der zeichen am ende
  boolean final3  = false;
  
  
  
  int ani;      //tick dauer der animation

  
 

  //  Kontrolle ob sieg
  int win1;
  int win2;
  int win3;
  
  int WIN = 0;        // gewinn

  int wahscheinlichkeit = 8; // zwischen 1 und 8 (bis zu acht symbolen)     randomizer animatrion
  
  int wahrscheinlichkeitenfinal = 100; //festlegung wahrscheinlichkeiten      1-100%
  
  
   // TICK
  private final PauseTransition smallPause = new PauseTransition(Duration.millis(atick));
  private final SequentialTransition smallStep = new SequentialTransition(smallPause);

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
  
  private final Image[] SYMBOLS = {
  Seven, // 0
  Banane, // 1
  Erdbeere, // 2                                                                          //liste der Zeichen im slots brereich
  Kirsche, // 3
  Orange, // 4
  Traube, // 5
  Wassermelone, // 6
  Zitrone // 7
  };
  
  

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

  
  // Ende Attribute
  
  //knopf und labels
  private Label Money = new Label();
  private ToggleButton Cheat = new ToggleButton();

  
  
  //verbindung SlotsGUI und SlotsLogik
  private final SlotsLogik logik = new SlotsLogik();
  

  //  Bildschirm erstellung
  

  private Pane Game() {
    // Fenster Erstellen
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
    glowhalb.setLevel(0.5);

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
    
    
    //gewinn zeichen
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
    
    
    //lampen an maschine
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

    // Design und EXIT geldangabe

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
    Money.setPrefWidth(100);
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

      // fast ist an wenn der noch spinnt und man kann dann schnell stoppen
      if (fast == true) {
        ani = 0;
        pressed = true;
        
        final1 = true;
        final2 = true;
        final3 = true;
        
        
        SlotSound.stop();
        }
      else {
        // geld kontrolle
      if (playerMoney >= 10) {
            
      
            fast = true;       //schnell spins
            ani = 40;            // dauer animation
            pressed = true; // start slot

        playerMoney = playerMoney - 10;
        Money.setText(String.valueOf(playerMoney));         // geld update

        // Effect reset
        Slot1.setEffect(offglow);
        Slot2.setEffect(offglow);
        Slot3.setEffect(offglow);

        // Sound Start
        SlotSound.seek(Duration.ZERO);
        SlotSound.setCycleCount(1);
        SlotSound.play();


           //sound reset
           WinSound.stop();
          Jackpot.setVisible(false);
           Pair.setVisible(false);
      }     
        else {
          //sound wenn zu wenig geld
          
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
        Buzzer.setVolume(0);
        }
       else {
            Sound.setImage(anSound);
        SoundCheck = true;
        BackgroundMusik.setVolume(0.3);
        Klick.setVolume(1);
        WinSound.setVolume(1);
        SlotSound.setVolume(0.3);
        CoinSound.setVolume(1); 
        Buzzer.setVolume(1);
        
         }
        
      
     

    });

    Exit.setOnMouseClicked(event -> {
      BackgroundMusik.stop();
      SlotSound.stop();
      WinSound.stop();
      Klick.stop();
      CoinSound.stop();                               //alle sound stoppen beim gehen
      Buzzer.stop();
      
      
      //zurück startbildschirm
      
      Stage gameStage = new Stage();

      StartBildschirm game = new StartBildschirm(); // money ubergabe
      game.start(gameStage); // start von slots

      stage.close(); // dieses fenster schließen

    });

    Machiene.setOnMouseClicked(event -> {
      // sound für machine wenn drauf geklickt

      CoinSound.seek(Duration.ZERO);
      CoinSound.setCycleCount(1);
      CoinSound.play();

    });

    // Tick-Callback
    smallPause.setOnFinished(
        (event) -> {
        
          if (pressed == true) {
             
            // animation random Symbole 
        if (ani > 20) {
             Slot1.setImage(randomSymbolImage());
            }
        if (ani > 10) {
             Slot2.setImage(randomSymbolImage());
              }
        if (ani > 0) {
             Slot3.setImage(randomSymbolImage());
           }
        
        //und festlegung wenn vorbei             
        if (ani == 21 || final1 == true) {
           final1 = false;      
           win1 = logik.final1();
           Slot1.setImage(SYMBOLS[win1]);
               }
        if (ani == 11 || final2 == true) {
                final2 = false;
                win2 = logik.final2();
                Slot2.setImage(SYMBOLS[win2]);
               }
        if (ani == 1 || final3 == true) {
                final3 = false;
                win3 = logik.final3();
                Slot3.setImage(SYMBOLS[win3]);
               }

        //klick sound wenn symbol final ist    
            if (ani == 21) {
                Klick.seek(Duration.ZERO);
                Klick.setStopTime(Duration.seconds(1));
                Klick.play();
            }
            if (ani < 11) {
             Klick.seek(Duration.ZERO);
             Klick.setStopTime(Duration.seconds(1));
             Klick.play();     
            }
            if (ani == 2) {
                Klick.seek(Duration.ZERO);
                Klick.setStopTime(Duration.seconds(1));
                Klick.play(); 
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
        
        //konrtolle am ende ob man gewonnen hat 
        if (ani == 0) {
          WIN = logik.Ausgabe(win1, win2, win3);           // check
          
          if (WIN < 30 && WIN > 0) {                            //gewinn ? wenn pair ist weniger als tripple deswegen        WIN < 30 && WIN > 0
            WinSound.seek(Duration.ZERO);
            WinSound.setCycleCount(1);
            WinSound.play();
          
            playerMoney += WIN;
            Money.setText(String.valueOf(playerMoney));
            WIN = 0;
          
          
            Pair.setVisible(true);
          
          
          }
        
         if (WIN > 30) {
            WinSound.seek(Duration.ZERO);
            WinSound.setCycleCount(1);
            WinSound.play();
          
            playerMoney += WIN;
            Money.setText(String.valueOf(playerMoney));
            WIN = 0;
          
           
            Jackpot.setVisible(true);
          }
            }

        
        }
          ani = ani - 1; // roulet dauer kürzer machen
      
      if (ani < 0) {               // wenn fertig auf anfang zurück
        pressed = false;
        fast = false;
        }
    
        });

    // start
      gameLoop = smallStep;
      gameLoop.setCycleCount(Timeline.INDEFINITE);
      gameLoop.play();

      return root;
           }
  // Anfang Methoden

    public void Cheaten(Event evt) {
      logik.Cheaten(cheat);                                    //macht wahscheinlichkeit anders 1 || 100
    if (playerMoney < 10) {
        playerMoney = 10 ;
        Money.setText(String.valueOf(playerMoney));            // anzeige geld wenn zu wenig
        
        }
    if (cheat == true) {                                                       //zum ausstellen
      cheat = false;
      }
    else {
      cheat = true;
      }
    
  }
    
   private Image randomSymbolImage() {                           // macht die bilder random
    int r = random.nextInt(wahscheinlichkeit); // 0..wahrscheinlichkeit-1


    switch (r) {
      case 0: return Seven;
      case 1: return Banane;
      case 2: return Erdbeere;
      case 3: return Kirsche;
      case 4: return Orange;
      case 5: return Traube;
      case 6: return Wassermelone;
      case 7: return Zitrone;
      default: return Seven; // Fallback
    }
  }
    public void start(Stage primaryStage) {        // setzt start stage
      // Start von Menu
      
      stage = primaryStage;
      scene = new Scene(Game(), Breite, Hoehe); /// größe
      // Anfang Komponenten
      // Ende Komponenten
      stage.setScene(scene);
      stage.setTitle("Slots"); // name
      stage.show();
  }

    public static void main(String[] args) {
      launch(args);

  }
  // Ende Methoden
}
