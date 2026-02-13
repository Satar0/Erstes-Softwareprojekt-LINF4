/*
Software Projekt 
Poker
GUI und Features
Version 1
Zaid Sharif
20.01.2026
 */
 
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.*;
import java.io.File;

public class StartScreen extends Application {

    private Stage stage;
    private Scene menuScene, gameScene, settingsScene, infoScene;   //Unterschiedliche Szenen werden verwendet um mit Knöpfen verschiedene Ereignisse auszulösen
    private Slider slider1 = new Slider(0, 1, 0.1);

    private MediaPlayer currentMusic;

    // ALle Musikrichtungen werden importiert
    private MediaPlayer backgroundMusik = createPlayer("sounds/Musika1.mp3");
    private MediaPlayer backgroundMusik1 = createPlayer("sounds/Musika1.mp3");
    private MediaPlayer backgroundMusik2 = createPlayer("sounds/Musika2.mp3");
    private MediaPlayer backgroundMusik3 = createPlayer("sounds/Musika3.mp3");
    private MediaPlayer backgroundMusik4 = createPlayer("sounds/Musika4.mp3");
    private MediaPlayer backgroundMusik5 = createPlayer("sounds/Musika5.mp3");
    private MediaPlayer backgroundMusik6 = createPlayer("sounds/Musika6.mp3");

    private MediaView startView;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        //Alle verwendeten Szenen und die Größe auf einem Blick
        menuScene = new Scene(Menu(), 1600, 900);
        gameScene = new Scene(Game(), 1600, 900);
        settingsScene = new Scene(Settings(), 1600, 900);
        infoScene = new Scene(Info(), 1600, 900);

        stage.setTitle("Poker");
        stage.setScene(menuScene);
        stage.show();

        playMusic(backgroundMusik);  //Musik
    }

    // Erstellt einen MediaPlayer für die angegebene Datei
    private MediaPlayer createPlayer(String path) {
    
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer player = new MediaPlayer(media);           // Erstellt einen MediaPlayer für die Media-Datei
    
    player.setCycleCount(MediaPlayer.INDEFINITE);         // Setzt die Musik auf Endlosschleife

    return player;
}


    private void playMusic(MediaPlayer newMusic) {     // Spielt die übergebene Musik ab
   
    if (currentMusic != null) {                        // Falls aktuell Musik läuft, wird sie gestoppt
        currentMusic.stop();
    }
    currentMusic = newMusic;                         // Setzt die neue Musik als aktuelle Musik
    currentMusic.setVolume(slider1.getValue());     // Übernimmt die Lautstärke vom Slider
    currentMusic.play();                            // Startet die Musikwiedergabe
}

    private void styleButton(Button button) {  //Alle Designs für meine verwendeten Buttons mit Ausnahme von unsichtbaren
        button.setStyle(
            "-fx-background-color: #4a235a;" +
            "-fx-text-fill: #f5eef8;" +
            "-fx-font-family: 'Monospaced';" +
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: #6c3483;" +
            "-fx-border-width: 2;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: #5b2c6f;" +
            "-fx-text-fill: #ffffff;" +
            "-fx-font-family: 'Monospaced';" +
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: #af7ac5;" +
            "-fx-border-width: 2;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: #4a235a;" +
            "-fx-text-fill: #f5eef8;" +
            "-fx-font-family: 'Monospaced';" +
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: #6c3483;" +
            "-fx-border-width: 2;"
        ));
    }

    private void invisibleButton(Button b) {  //unsichtbarer Button Design
        b.setOpacity(0);
        b.setPickOnBounds(true);
    }


    // MENU Szene
    private Pane Menu() {
        Pane root = new Pane();

        Media video = new Media(new File("Images/S1Video2.mp4").toURI().toString());  //Hintergrundbild einfügen
        MediaPlayer videoPlayer = new MediaPlayer(video);
        startView = new MediaView(videoPlayer);
        startView.setFitWidth(1600);
        startView.setFitHeight(900);
        videoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        videoPlayer.play();
        root.getChildren().add(startView);

        // Start, Settings, Exit -> unsichtbar und Positionen
        Button start = new Button();
        start.setLayoutX(700);
        start.setLayoutY(540);
        start.setPrefSize(200, 50);
        invisibleButton(start);
        start.setOnAction(e -> stage.setScene(gameScene));  //Gameszene wird aufgerufen

        Button settings = new Button();
        settings.setLayoutX(700);
        settings.setLayoutY(620);
        settings.setPrefSize(200, 50);
        invisibleButton(settings);
        settings.setOnAction(e -> stage.setScene(settingsScene));    //Settingszene wird aufgerufen

        Button exit = new Button();
        exit.setLayoutX(700);
        exit.setLayoutY(710);
        exit.setPrefSize(200, 50);
        invisibleButton(exit);
        exit.setOnAction(e -> System.exit(0));   //Spiel wird geschlossen

        Button info = new Button("Info");
        info.setLayoutX(1450);
        info.setLayoutY(730);
        info.setPrefSize(90, 90);
        styleButton(info);
        info.setOnAction(e -> stage.setScene(infoScene));     //Infoszene wird aufgerufen

        root.getChildren().addAll(start, settings, exit, info);   //Alle Elemente werden aufgerufen in der Szene
        return root;
    }


    // GAME Menu
    private Pane Game() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");
        Image image = new Image(getClass().getResourceAsStream("/Images/Pokertisch.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(900);
        imageView.setFitWidth(1600);                              //Nochmehr Designs

    
        Button back = new Button("Zurück");
        back.setLayoutX(40);
        back.setLayoutY(40);
        back.setPrefSize(130, 60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));
    
        Image MusikStoppen = new Image(getClass().getResourceAsStream("/Images/MusikStumm1.png"));    //ist noch nicht fertig
        ImageView Mstop = new ImageView(MusikStoppen);
        Mstop.setLayoutX(1400);
        Mstop.setLayoutY(40);Mstop.setFitWidth(80);
        Mstop.setFitHeight(80);
        Mstop.setPreserveRatio(true);
    
        root.getChildren().addAll(imageView, back, Mstop);
        return root;
    }

     //Settings Menu
    private Pane Settings() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Label label = new Label("Musikeinstellungen");
        label.setLayoutX(550);
        label.setLayoutY(100);
        label.setStyle("-fx-font-size: 60px; -fx-text-fill: white;");

        slider1.setLayoutX(670);
        slider1.setLayoutY(700);
        slider1.setPrefWidth(280);
        slider1.valueProperty().addListener((obs, o, n) -> {
            if (currentMusic != null)
                currentMusic.setVolume(n.doubleValue());
        });

        Button Musik1 = musicButton("Musik 1", 450, 300, backgroundMusik1);
        Button Musik2 = musicButton("Musik 2", 450, 400, backgroundMusik2);
        Button Musik3 = musicButton("Musik 3", 450, 500, backgroundMusik3);
        Button Musik4 = musicButton("Musik 4", 900, 300, backgroundMusik4);
        Button Musik5 = musicButton("Musik 5", 900, 400, backgroundMusik5);
        Button Musik6 = musicButton("Musik 6", 900, 500, backgroundMusik6);

        Button back = new Button("Zurück");
        back.setLayoutX(40);
        back.setLayoutY(40);
        back.setPrefSize(130, 60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        root.getChildren().addAll(label, slider1, Musik1, Musik2, Musik3, Musik4, Musik5, Musik6, back);
        return root;
    }
  

    private Button musicButton(String text, int x, int y, MediaPlayer music) {
        Button b = new Button(text);
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setPrefSize(250, 60);
        styleButton(b);
        b.setOnAction(e -> playMusic(music));
        return b;
    }

    //Info Menu
    private Pane Info() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Label label = new Label("Spielregeln");
        label.setLayoutX(650);
        label.setLayoutY(50);
        label.setStyle("-fx-font-size: 60px; -fx-text-fill: white;");

        Label rules = new Label(       //Spielregeln
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
        rules.setLayoutX(100);
        rules.setLayoutY(200);
        rules.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");
        rules.setWrapText(true);
        rules.setMaxWidth(800);

        // Bild hinzufügen
        Image image = new Image(getClass().getResourceAsStream("/Images/PokerRules.png"));
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(950);
        imageView.setLayoutY(200);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        Button back = new Button("Zurück");
        back.setLayoutX(40);
        back.setLayoutY(40);
        back.setPrefSize(130, 60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        root.getChildren().addAll(label, rules, imageView, back);
        return root;
    }

    public static void main(String[] args) {  //Main Methode
        launch(args);
    }
}

