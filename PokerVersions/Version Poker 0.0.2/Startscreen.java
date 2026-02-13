/*
Software Projekt 
Poker
GUI und Features
Version 2
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
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Startscreen extends Application {

    private Stage stage;
    private Scene menuScene, gameScene, settingsScene, infoScene;
    private Slider slider1 = new Slider(0, 1, 0.5);

    private MediaPlayer currentMusic;

    private MediaPlayer backgroundMusik1 = createPlayer("sounds/Musika1.mp3");
    private MediaPlayer backgroundMusik2 = createPlayer("sounds/Musika2.mp3");
    private MediaPlayer backgroundMusik3 = createPlayer("sounds/Musika3.mp3");
    private MediaPlayer backgroundMusik4 = createPlayer("sounds/Musika4.mp3");
    private MediaPlayer backgroundMusik5 = createPlayer("sounds/Musika5.mp3");
    private MediaPlayer backgroundMusik6 = createPlayer("sounds/Musika6.mp3");


    private Button checkButton;
    private Button callButton;
    private Button raiseButton;
    private Button foldButton;

    private MediaView startView;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        menuScene = new Scene(Menu(), 1600, 900);
        gameScene = new Scene(Game(), 1600, 900);
        settingsScene = new Scene(Settings(), 1600, 900);
        infoScene = new Scene(Info(), 1600, 900);

        stage.setTitle("Poker");
        stage.setScene(menuScene);
        stage.show();

        playMusic(backgroundMusik1);
    }

    private MediaPlayer createPlayer(String path) {
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        return player;
    }

    private void playMusic(MediaPlayer newMusic) {
        if (currentMusic != null) currentMusic.stop();
        currentMusic = newMusic;
        currentMusic.setVolume(slider1.getValue());
        currentMusic.play();
    }

    private void styleButton(Button button) {
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
        button.setOnMouseExited(e -> styleButton(button));
    }

    private void invisibleButton(Button b) {
        b.setOpacity(0);
        b.setPickOnBounds(true);
    }

    // --- MENU ---
    private Pane Menu() {
        Pane root = new Pane();

        try {
            Media video = new Media(new File("Images/S1Video2.mp4").toURI().toString());
            MediaPlayer videoPlayer = new MediaPlayer(video);
            startView = new MediaView(videoPlayer);
            startView.setFitWidth(1600);
            startView.setFitHeight(900);
            videoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            videoPlayer.play();
            root.getChildren().add(startView);
        } catch (Exception e) {
            root.setStyle("-fx-background-color: darkgreen;"); // Fallback
        }

        Button start = new Button(); invisibleButton(start);
        start.setLayoutX(700); start.setLayoutY(540); start.setPrefSize(200, 50);
        start.setOnAction(e -> stage.setScene(gameScene));

        Button settings = new Button(); invisibleButton(settings);
        settings.setLayoutX(700); settings.setLayoutY(620); settings.setPrefSize(200, 50);
        settings.setOnAction(e -> stage.setScene(settingsScene));

        Button exit = new Button(); invisibleButton(exit);
        exit.setLayoutX(700); exit.setLayoutY(710); exit.setPrefSize(200, 50);
        exit.setOnAction(e -> System.exit(0));

        Button info = new Button("Info");
        info.setLayoutX(1450); info.setLayoutY(730); info.setPrefSize(90, 90);
        styleButton(info);
        info.setOnAction(e -> stage.setScene(infoScene));

        root.getChildren().addAll(start, settings, exit, info);

        return root;
    }

   private Pane Game() {
    Pane root = new Pane();
    root.setPrefSize(1600, 900);
    root.setStyle("-fx-background-color: darkgreen;");

    //Tisch Hintergrund
    Image table = new Image(getClass().getResourceAsStream("/Images/Pokertisch.jpg"));
    ImageView tableView = new ImageView(table);
    tableView.setFitWidth(1600);
    tableView.setFitHeight(900);
    root.getChildren().add(tableView);

    //Zurück Button 
    Button back = new Button("Zurück");
    back.setLayoutX(40);
    back.setLayoutY(20);
    back.setPrefSize(130, 60);
    styleButton(back);
    back.setOnAction(e -> stage.setScene(menuScene));


    //Musik Button
    Button musicToggle = new Button("Pause");
    musicToggle.setLayoutX(1400);
    musicToggle.setLayoutY(20);
    musicToggle.setPrefSize(120, 60);
    styleButton(musicToggle);
    musicToggle.setOnAction(e -> {
        if (currentMusic != null) {
            if (currentMusic.getStatus() == MediaPlayer.Status.PLAYING) {
                currentMusic.pause();
                musicToggle.setText("Play");
            } else {
                currentMusic.play();
                musicToggle.setText("Pause");
            }
        }
    });

    //Spieleranzeige
    String[] spielerNamen = {"Alice", "Bob", "Charlie", "Diana"};
    int[][] positions = {{50,100},{1450,100},{50,650},{1450,650}};
    int[] chips = {1000,1200,950,1100};
   
    List<String> deck = new ArrayList<>(Arrays.asList(     // Deck erstellen
    "H2.png","H3.png","H4.png","H5.png","H6.png",
    "H7.png","H8.png","H9.png","H10.png",
    "HB.png","HD.png","HK.png","HA.png",
    
    "K2.png","K3.png","K4.png","K5.png","K6.png",
    "K7.png","K8.png","K9.png","K10.png",
    "KB.png","KD.png","KK.png","KA.png",
    
    "P2.png","P3.png","P4.png","P5.png","P6.png",
    "P7.png","P8.png","P9.png","P10.png",
    "PB.png","PD.png","PK.png","PA.png",
    
    "R2.png","R3.png","R4.png","R5.png","R6.png",
    "R7.png","R8.png","R9.png","R10.png",
    "RB.png","RD.png","RK.png","RA.png"
    ));
    Collections.shuffle(deck); // mischen





    for (int i = 0; i < 4; i++) {
        Label player = new Label(spielerNamen[i] + "\nChips: " + chips[i]);
        player.setFont(Font.font("Arial", 20));
        player.setTextFill(Color.WHITE);
        player.setStyle(
            "-fx-background-color: rgba(0,0,0,0.6);" +
            "-fx-padding: 8px;" +
            "-fx-background-radius: 5px;"
        );
        player.setLayoutX(positions[i][0]);
        player.setLayoutY(positions[i][1]);
        root.getChildren().add(player);
    }

    // Karten auf dem Tisch
    for (int i = 0; i < 5; i++) {
        ImageView card = new ImageView(
            new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png"))
        );
        card.setFitWidth(80);
        card.setFitHeight(120);
        card.setLayoutX(570 + i * 100); //Kartenabstand
        card.setLayoutY(350);
        root.getChildren().add(card); //fügt Karten hinzu
    }

    //Action Buttons
String[] actions = {"Check", "Call", "Raise", "Fold"};


for (int i = 0; i < actions.length; i++) {

    Button action = new Button(actions[i]);
    action.setLayoutX(600 + i * 150);
    action.setLayoutY(800);
    action.setPrefSize(120, 50);
    action.setDisable(true);
    styleButton(action);

    // Check-Button
    if (actions[i].equals("Check")) {
        checkButton = action;
    }
    if (actions[i].equals("Raise")) {
        raiseButton = action;
    }
    if (actions[i].equals("Call")) {
        callButton = action;
    }
    if (actions[i].equals("Fold")) {
        foldButton = action;
    }

    int index = i;
    Button finalCheckButton = checkButton;
    Button finalCallButton = callButton;
    Button finalRaiseButton = raiseButton;

    action.setOnAction(e -> {
        System.out.println(actions[index] + " gedrückt");

        if (actions[index].equals("Fold") && finalCheckButton != null) {
            finalCheckButton.setDisable(true);
            finalRaiseButton.setDisable(true);
            finalCallButton.setDisable(true);
        }
    });

    root.getChildren().add(action);
}


    //Spielstart Button
    Button spielstarten = new Button("Spielstart");
    spielstarten.setLayoutX(200);
    spielstarten.setLayoutY(20);
    spielstarten.setPrefSize(180, 60);
    styleButton(spielstarten);
    spielstarten.setOnAction(e -> {
    back.setDisable(true);
    if (checkButton != null){

    checkButton.setDisable(false);
}
  if (callButton != null){

    callButton.setDisable(false);
}
  if (raiseButton != null){

    raiseButton.setDisable(false);
}
  if (foldButton != null){

    foldButton.setDisable(false);
}
    

});

  
    


    


root.getChildren().addAll(back, spielstarten, musicToggle);

return root;

}




    // --- SETTINGS ---
    private Pane Settings() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Label label = new Label("Musikeinstellungen");
        label.setLayoutX(550); label.setLayoutY(100);
        label.setStyle("-fx-font-size: 60px; -fx-text-fill: white;");

        slider1.setLayoutX(670); slider1.setLayoutY(700); slider1.setPrefWidth(280);
        slider1.valueProperty().addListener((obs, o, n) -> {
            if (currentMusic != null) currentMusic.setVolume(n.doubleValue());
        });

        Button Musik1 = musicButton("Musik 1", 450, 300, backgroundMusik1);
        Button Musik2 = musicButton("Musik 2", 450, 400, backgroundMusik2);
        Button Musik3 = musicButton("Musik 3", 450, 500, backgroundMusik3);
        Button Musik4 = musicButton("Musik 4", 900, 300, backgroundMusik4);
        Button Musik5 = musicButton("Musik 5", 900, 400, backgroundMusik5);
        Button Musik6 = musicButton("Musik 6", 900, 500, backgroundMusik6);

        Button back = new Button("Zurück");
        back.setLayoutX(40); back.setLayoutY(40); back.setPrefSize(130, 60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        root.getChildren().addAll(label, slider1, Musik1, Musik2, Musik3, Musik4, Musik5, Musik6, back);
        return root;
    }

    private Button musicButton(String text, int x, int y, MediaPlayer music) {
        Button b = new Button(text);
        b.setLayoutX(x); b.setLayoutY(y); b.setPrefSize(250, 60);
        styleButton(b);
        b.setOnAction(e -> playMusic(music));
        return b;
    }

    // --- INFO ---
    private Pane Info() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Label label = new Label("Spielregeln");
        label.setLayoutX(650); label.setLayoutY(50);
        label.setStyle("-fx-font-size: 60px; -fx-text-fill: white;");

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
        rules.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");
        rules.setWrapText(true); rules.setMaxWidth(800);

        try {
            Image img = new Image(getClass().getResourceAsStream("/Images/PokerRules.png"));
            ImageView imgView = new ImageView(img);
            imgView.setLayoutX(950); imgView.setLayoutY(200);
            imgView.setFitWidth(500); imgView.setPreserveRatio(true);
            root.getChildren().add(imgView);
        } catch (Exception e) {}

        Button back = new Button("Zurück");
        back.setLayoutX(40); back.setLayoutY(40); back.setPrefSize(130, 60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        root.getChildren().addAll(label, rules, back);
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
