/*
Software Projekt 
Poker
GUI, Features und Kartenausteilung
Version 3
Zaid Sharif
10.03.2026
*/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.util.*;

public class PokerApp extends Application {

    // Variablen
    private Stage stage;

    private Scene menuScene;
    private Scene gameScene;
    private Scene settingsScene;
    private Scene infoScene;

    // Musik Slider
    private Slider volumeSlider = new Slider(0, 1, 0.5);
    private MediaPlayer currentMusic;

    // 6 Musikstücke
    private MediaPlayer music1 = createPlayer("sounds/Musika1.mp3");
    private MediaPlayer music2 = createPlayer("sounds/Musika2.mp3");
    private MediaPlayer music3 = createPlayer("sounds/Musika3.mp3");
    private MediaPlayer music4 = createPlayer("sounds/Musika4.mp3");
    private MediaPlayer music5 = createPlayer("sounds/Musika5.mp3");
    private MediaPlayer music6 = createPlayer("sounds/Musika6.mp3");

    // Poker Variablen
    private String[] players = {"You", "Bot1", "Bot2", "Bot3"};
    private Map<String, List<ImageView>> playerCards = new HashMap<>();
    private List<ImageView> tableCards = new ArrayList<>();
    private List<String> deck = new ArrayList<>();

    private int pot = 0;                // Aktueller Pot
    private int currentBet = 50;        // Aktueller Einsatz
    private Label potLabel;             // Anzeige für den Pot
    private int revealIndex = 0;        // Index für Tischkarten
    private Label statusLabel;          // Statusanzeige 
  

    // Alle Karten als Strings (Bilder müssen in /ALLCards/Projekt liegen)
    private static final String[] ALL_CARDS = {
            "H2.png","H3.png","H4.png","H5.png","H6.png",
            "H7.png","H8.png","H9.png","H10.png","HB.png","HD.png","HK.png","HA.png",
            "K2.png","K3.png","K4.png","K5.png","K6.png","K7.png","K8.png","K9.png","K10.png","KB.png","KD.png","KK.png","KA.png",
            "P2.png","P3.png","P4.png","P5.png","P6.png","P7.png","P8.png","P9.png","P10.png","PB.png","PD.png","PK.png","PA.png",
            "R2.png","R3.png","R4.png","R5.png","R6.png","R7.png","R8.png","R9.png","R10.png","RB.png","RD.png","RK.png","RA.png"
    };

    // ---------- START ----------
    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;

        // Szenen erstellen
        menuScene = new Scene(createMenu(), 1600, 900);
        gameScene = new Scene(createGame(), 1650, 900);
        settingsScene = new Scene(createSettings(), 1600, 900);
        infoScene = new Scene(createInfo(), 1600, 900);

        stage.setTitle("Poker");
        stage.setScene(menuScene);
        stage.show();

        // Standardmusik starten
        playMusic(music1);
    }

    // MUSIK METHODEN
    private MediaPlayer createPlayer(String path){
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        return player;
    }

    private void playMusic(MediaPlayer m){
        if(currentMusic != null)
            currentMusic.stop();

        currentMusic = m;
        currentMusic.setVolume(volumeSlider.getValue());
        currentMusic.play();
    }

    // Musikbutton
    private Button musicButton(String text, int x, int y, MediaPlayer music) {
        Button b = new Button(text);
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setPrefSize(250,60);
        styleButton(b);
        b.setOnAction(e -> playMusic(music));
        return b;
    }

    //BUTTON STYLE
    private void styleButton(Button b){
        b.setStyle(
                "-fx-background-color:#4a235a;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:18px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:10;" +
                        "-fx-border-color:#af7ac5;" +
                        "-fx-border-width:2;"
        );

        b.setOnMouseEntered(e ->
                b.setStyle(
                        "-fx-background-color:#6c3483;" +
                                "-fx-text-fill:white;" +
                                "-fx-font-size:18px;" +
                                "-fx-font-weight:bold;" +
                                "-fx-background-radius:10;" +
                                "-fx-border-color:white;" +
                                "-fx-border-width:2;"
                )
        );

        b.setOnMouseExited(e -> styleButton(b));
    }

    // Unsichtbare Buttons für Startscreen
    private void invisibleButton(Button b){
        b.setOpacity(0);
        b.setPickOnBounds(true);
    }

    // MENÜ SCENE
    private Pane createMenu(){
        Pane root = new Pane();

        // Hintergrundvideo
        try{
            Media video = new Media(new File("Images/S1Video2.mp4").toURI().toString());
            MediaPlayer videoPlayer = new MediaPlayer(video);
            MediaView view = new MediaView(videoPlayer);
            view.setFitWidth(1600);
            view.setFitHeight(900);
            videoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            videoPlayer.play();
            root.getChildren().add(view);
        }catch(Exception e){
            root.setStyle("-fx-background-color:darkgreen;");
        }

        // Startbutton unsichtbar
        Button start = new Button();
        invisibleButton(start);
        start.setLayoutX(700); start.setLayoutY(540);
        start.setPrefSize(200,50);
        start.setOnAction(e -> {
            stage.setScene(gameScene);
            startGame(); // Neue Runde starten
        });

        // Settings unsichtbar
        Button settings = new Button();
        invisibleButton(settings);
        settings.setLayoutX(700); settings.setLayoutY(620);
        settings.setPrefSize(200,50);
        settings.setOnAction(e -> stage.setScene(settingsScene));

        // Exit unsichtbar
        Button exit = new Button();
        invisibleButton(exit);
        exit.setLayoutX(700); exit.setLayoutY(710);
        exit.setPrefSize(200,50);
        exit.setOnAction(e -> System.exit(0));

        // Info sichtbar
        Button info = new Button("Info");
        info.setLayoutX(1450); info.setLayoutY(730);
        info.setPrefSize(90,90);
        styleButton(info);
        info.setOnAction(e -> stage.setScene(infoScene));

        root.getChildren().addAll(start, settings, exit, info);
        return root;
    }

    // GAME SCENE
    private Pane createGame(){
        Pane root = new Pane();

        // Tischbild
        Image table = new Image(getClass().getResourceAsStream("/Images/Pokertisch.jpg"));
        ImageView tableView = new ImageView(table);
        tableView.setFitWidth(1650);
        tableView.setFitHeight(900);
        root.getChildren().add(tableView);

        // Status Label 
        statusLabel = new Label("");
        statusLabel.setLayoutX(720); statusLabel.setLayoutY(90);
        statusLabel.setFont(Font.font(28));
        statusLabel.setTextFill(Color.WHITE);
        root.getChildren().add(statusLabel);

        // Spielerpositionen
        int[][] pos = {{750,700},{150,350},{750,120},{1350,350}};

        // Spieler Karten (verdeckt)
        for(int i=0;i<players.length;i++){
            String p = players[i];
            List<ImageView> cards = new ArrayList<>();
            for(int c=0;c<2;c++){
                ImageView card = new ImageView(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
                card.setFitWidth(80); card.setFitHeight(120);
                card.setLayoutX(pos[i][0] + c*90);
                card.setLayoutY(pos[i][1]);
                root.getChildren().add(card);
                cards.add(card);
            }
            playerCards.put(p,cards);
        }

        // Tischkarten (verdeckt)
        for(int i=0;i<5;i++){
            ImageView card = new ImageView(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
            card.setFitWidth(90); card.setFitHeight(130);
            card.setLayoutX(600 + i*110); card.setLayoutY(380);
            tableCards.add(card);
            root.getChildren().add(card);
        }

        // Pot Label
        potLabel = new Label("Pot: 0");
        potLabel.setLayoutX(780); potLabel.setLayoutY(40);
        potLabel.setFont(Font.font(28)); potLabel.setTextFill(Color.YELLOW);
        root.getChildren().add(potLabel);

        // Poker Buttons 
        Button callButton = new Button("Call");
        callButton.setLayoutX(1500); callButton.setLayoutY(780); callButton.setPrefSize(140,60);
        styleButton(callButton);
        callButton.setOnAction(e -> {
            pot += currentBet;
            potLabel.setText("Pot: " + pot);
            statusLabel.setText("Du hast gecallt");
        });

        Button checkButton = new Button("Check");
        checkButton.setLayoutX(1300); checkButton.setLayoutY(780); checkButton.setPrefSize(140,60);
        styleButton(checkButton);
        checkButton.setOnAction(e -> statusLabel.setText("Du checkst"));

        Button raiseButton = new Button("Raise");
        raiseButton.setLayoutX(1100); raiseButton.setLayoutY(780); raiseButton.setPrefSize(140,60);
        styleButton(raiseButton);
        raiseButton.setOnAction(e -> {
            currentBet += 50;
            pot += currentBet;
            potLabel.setText("Pot: " + pot);
            statusLabel.setText("Du hast erhöht auf " + currentBet);
        });

        // Next Card Button 
        Button next = new Button("Next Card");
        next.setLayoutX(450); next.setLayoutY(750); next.setPrefSize(160,60);
        styleButton(next);
        next.setOnAction(e -> revealNextCard());

        // Menu Button
        Button back = new Button("Menu");
        back.setLayoutX(40); back.setLayoutY(40); back.setPrefSize(130,60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        // Neue Runde Button
        Button restart = new Button("Neue Runde");
        restart.setLayoutX(200); restart.setLayoutY(750); restart.setPrefSize(160,60);
        styleButton(restart);
        restart.setOnAction(e -> startGame());

        // Alle Buttons zum Root hinzufügen
        root.getChildren().addAll(next, back, restart, callButton, checkButton, raiseButton);

        return root;
    }

    // ---------- SPIEL STARTEN ----------
    private void startGame(){
        // Pot + Einsatz zurücksetzen
        pot = 0;
        currentBet = 50;
        potLabel.setText("Pot: 0");
        statusLabel.setText("Neue Runde gestartet");

        // Deck mischen
        deck.clear();
        deck.addAll(Arrays.asList(ALL_CARDS));
        Collections.shuffle(deck);

        revealIndex = 0;

        // Karten austeilen
        for(String player : players){
            List<ImageView> views = playerCards.get(player);
            for(int i=0;i<2;i++){
                String card = deck.remove(0);
                if(player.equals("You")){
                    // Eigene Karten sichtbar
                    views.get(i).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/"+card)));
                } else {
                    // Bots Karten verdeckt
                    views.get(i).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
                }
            }
        }

        // Tischkarten zurücksetzen (verdeckt)
        for(ImageView card : tableCards){
            card.setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
        }
    }

    //TISCHKARTEN AUFDECKENß
    private void revealNextCard(){
        // (erste 3 Karten)
        if(revealIndex == 0){
            for(int i=0; i<3; i++){
                String card = deck.remove(0);
                tableCards.get(i).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/" + card)));
            }
            revealIndex = 3;
            statusLabel.setText("Flop");
            return;
        }

        //  (4. Karte)
        if(revealIndex == 3){
            String card = deck.remove(0);
            tableCards.get(3).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/" + card)));
            revealIndex = 4;
            statusLabel.setText("Turn");
            return;
        }

        //  (5. Karte)
        if(revealIndex == 4){
            String card = deck.remove(0);
            tableCards.get(4).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/" + card)));
            revealIndex = 5;
            statusLabel.setText("River");
        }
    }

    // ---------- SETTINGS ----------
    private Pane createSettings(){
        Pane root = new Pane();
        root.setStyle("-fx-background-color:black");

        Label label = new Label("Musik Einstellungen");
        label.setLayoutX(550); label.setLayoutY(100);
        label.setStyle("-fx-font-size:50px;");
        label.setTextFill(Color.WHITE);

        volumeSlider.setLayoutX(650);
        volumeSlider.setLayoutY(700);
        volumeSlider.setPrefWidth(300);

        volumeSlider.valueProperty().addListener((obs,o,n)->{
            if(currentMusic != null) currentMusic.setVolume(n.doubleValue());
        });

        // 6 Musikbuttons
        Button Musik1 = musicButton("Musik 1",450,300,music1);
        Button Musik2 = musicButton("Musik 2",450,400,music2);
        Button Musik3 = musicButton("Musik 3",450,500,music3);
        Button Musik4 = musicButton("Musik 4",900,300,music4);
        Button Musik5 = musicButton("Musik 5",900,400,music5);
        Button Musik6 = musicButton("Musik 6",900,500,music6);

        Button back = new Button("Zurück");
        back.setLayoutX(40); back.setLayoutY(40); back.setPrefSize(130,60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        root.getChildren().addAll(label, Musik1,Musik2,Musik3,Musik4,Musik5,Musik6, volumeSlider, back);
        return root;
    }

    // ---------- INFO ----------
    private Pane createInfo(){
        Pane root = new Pane();
        root.setStyle("-fx-background-color:black");

        Label title = new Label("Poker Regeln");
        title.setLayoutX(600); title.setLayoutY(60);
        title.setStyle("-fx-font-size:60px;");
        title.setTextFill(Color.WHITE);

        Label rules = new Label(
            "🎯 Ziel des Spiels: Die beste Hand gewinnt.\n\n" +
            "👥 Spieler & Karten\n" +
            "5 Karten liegen in der Mitte. Jeder Spieler bekommt 2 verdeckte Karten.\n\n" +
            "🔄 Spielablauf\n" +
            "Jeder Spieler erhält 2 Karten. In jeder Runde kannst du:\n" +
            "Check – nichts setzen\n" +
            "Call – mitgehen\n" +
            "Raise – erhöhen\n" +
            "Fold – aussteigen\n\n" +
            "🎉 Gewinner\n" +
            "Der Spieler mit der besten Hand oder der letzte verbleibende Spieler, wenn alle anderen gefoldet haben."
        );
        rules.setLayoutX(100); rules.setLayoutY(200);
        rules.setStyle("-fx-font-size:26px; -fx-text-fill:white;");
        rules.setWrapText(true); rules.setMaxWidth(800);

        Button back = new Button("Zurück");
        back.setLayoutX(40); back.setLayoutY(40); back.setPrefSize(130,60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        // Bild laden
        try{
            Image img = new Image(getClass().getResourceAsStream("/Images/PokerRules.png"));
            ImageView imgView = new ImageView(img);
            imgView.setLayoutX(950); imgView.setLayoutY(200);
            imgView.setFitWidth(500);
            imgView.setPreserveRatio(true);
            root.getChildren().add(imgView);
        }catch(Exception ignored){}

        root.getChildren().addAll(title,rules,back);
        return root;
    }

    // ---------- MAIN ----------
    public static void main(String[] args){
        launch(args);
    }
}

