/*
Software Projekt 
Poker
GUI, Features, Kartenausteilung, Logik 
Version 4
Zaid Sharif,(teils Jonas bei Logik)
16.03.2026
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

    // Fenster und Szenen
    private Stage stage;
    private Scene menuScene, gameScene, settingsScene, infoScene;

    // Musik
    private Slider volumeSlider = new Slider(0, 1, 0.5);
    private MediaPlayer currentMusic;
    private MediaPlayer music1 = createPlayer("sounds/Musika1.mp3");
    private MediaPlayer music2 = createPlayer("sounds/Musika2.mp3");
    private MediaPlayer music3 = createPlayer("sounds/Musika3.mp3");
    private MediaPlayer music4 = createPlayer("sounds/Musika4.mp3");
    private MediaPlayer music5 = createPlayer("sounds/Musika5.mp3");
    private MediaPlayer music6 = createPlayer("sounds/Musika6.mp3");

    // Spieler und Karten
    private Button restartButton = new Button("Neue Runde");
    private static final String PLAYER_NAME = "Spieler";
    private final String[] players = {PLAYER_NAME, "Bot1", "Bot2", "Bot3"};
    private final Map<String, List<ImageView>> playerCardViews = new HashMap<>();
    private final Map<String, List<String>> playerCardNames = new HashMap<>();
    private final Map<String, Label> chipLabels = new HashMap<>();
    private final Map<String, Integer> playerChips = new LinkedHashMap<>();
    private final Set<String> foldedPlayers = new HashSet<>();
    private final Set<String> eliminatedPlayers = new HashSet<>();
    private final Map<String, Integer> currentRoundBets = new HashMap<>();

    // Tischkarten
    private final List<ImageView> tableCardViews = new ArrayList<>();
    private final List<String> tableCardNames = new ArrayList<>();
    private List<String> deck = new ArrayList<>();

    // Alle Karten im Deck
    private final String[] ALL_CARDS = {
        "H2.png","H3.png","H4.png","H5.png","H6.png","H7.png","H8.png","H9.png","H10.png","HB.png","HD.png","HK.png","HA.png",
        "K2.png","K3.png","K4.png","K5.png","K6.png","K7.png","K8.png","K9.png","K10.png","KB.png","KD.png","KK.png","KA.png",
        "P2.png","P3.png","P4.png","P5.png","P6.png","P7.png","P8.png","P9.png","P10.png","PB.png","PD.png","PK.png","PA.png",
        "R2.png","R3.png","R4.png","R5.png","R6.png","R7.png","R8.png","R9.png","R10.png","RB.png","RD.png","RK.png","RA.png"
    };

    // Spielzustand
    private Label potLabel;
    private Label statusLabel;
    private Label betInfoLabel;
    private int pot = 0;
    private int currentBet = 0;
    private int revealIndex = 0;
    private boolean gameOver = false;
    private boolean waitingForPlayerAction = false;
    private int dealerIndex = 0;
    private Random random = new Random();

    // Konstanten
    private static final int SMALL_BLIND = 25;
    private static final int BIG_BLIND = 50;
    private static final int MIN_RAISE = 50;
    private static final int STARTING_CHIPS = 1000;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        initializePlayers();

        menuScene = new Scene(createMenu(), 1600, 900);
        gameScene = new Scene(createGame(), 1650, 900);
        settingsScene = new Scene(createSettings(), 1600, 900);
        infoScene = new Scene(createInfo(), 1600, 900);

        stage.setTitle("Poker");
        stage.setScene(menuScene);
        stage.show();

        playMusic(music1);
    }

    // Spieler mit Startchips initialisieren
    private void initializePlayers() {
        playerChips.clear();
        eliminatedPlayers.clear();
        
        for (String player : players) {
            playerChips.put(player, STARTING_CHIPS);
            playerCardNames.put(player, new ArrayList<>());
        }
    }

    // Musik laden
    private MediaPlayer createPlayer(String path) {
        try {
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer player = new MediaPlayer(media);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            return player;
        } catch (Exception e) {
            return null;
        }
    }

    // Musik abspielen
    private void playMusic(MediaPlayer m) {
        if (m == null) return;
        if (currentMusic != null) currentMusic.stop();
        currentMusic = m;
        currentMusic.setVolume(volumeSlider.getValue());
        currentMusic.play();
    }

    // Button für Musikauswahl erstellen
    private Button musicButton(String text, int x, int y, MediaPlayer music) {
        Button b = new Button(text);
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setPrefSize(250, 60);
        styleButton(b);
        b.setOnAction(e -> playMusic(music));
        return b;
    }

    // Button-Style setzen
    private void styleButton(Button b) {
        b.setStyle(
            "-fx-background-color:#4a235a;" +
            "-fx-text-fill:white;" +
            "-fx-font-size:18px;" +
            "-fx-font-weight:bold;" +
            "-fx-background-radius:10;" +
            "-fx-border-color:#af7ac5;" +
            "-fx-border-width:2;"
        );
        
        b.setOnMouseEntered(e -> b.setStyle(
            "-fx-background-color:#6c3483;" +
            "-fx-text-fill:white;" +
            "-fx-font-size:18px;" +
            "-fx-font-weight:bold;" +
            "-fx-background-radius:10;" +
            "-fx-border-color:white;" +
            "-fx-border-width:2;"
        ));
        
        b.setOnMouseExited(e -> styleButton(b));
    }

    // Button unsichtbar machen für Menü
    private void invisibleButton(Button b) {
        b.setOpacity(0);
        b.setPickOnBounds(true);
    }

    // Hauptmenü erstellen
    private Pane createMenu() {
        Pane root = new Pane();

        // Video als Hintergrund
        try {
            Media video = new Media(new File("Images/S1Video2.mp4").toURI().toString());
            MediaPlayer videoPlayer = new MediaPlayer(video);
            MediaView view = new MediaView(videoPlayer);
            view.setFitWidth(1600);
            view.setFitHeight(900);
            videoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            videoPlayer.play();
            root.getChildren().add(view);
        } catch (Exception e) {
            root.setStyle("-fx-background-color:darkgreen;");
        }

        // Unsichtbare Buttons über dem Video
        Button start = new Button();
        invisibleButton(start);
        start.setLayoutX(700);
        start.setLayoutY(540);
        start.setPrefSize(200, 50);
        start.setOnAction(e -> {
            stage.setScene(gameScene);
            resetFullGame();
            startNewGame();
        });

        Button settings = new Button();
        invisibleButton(settings);
        settings.setLayoutX(700);
        settings.setLayoutY(620);
        settings.setPrefSize(200, 50);
        settings.setOnAction(e -> stage.setScene(settingsScene));

        Button exit = new Button();
        invisibleButton(exit);
        exit.setLayoutX(700);
        exit.setLayoutY(710);
        exit.setPrefSize(200, 50);
        exit.setOnAction(e -> System.exit(0));

        Button info = new Button("Info");
        info.setLayoutX(1450);
        info.setLayoutY(730);
        info.setPrefSize(90, 90);
        styleButton(info);
        info.setOnAction(e -> stage.setScene(infoScene));

        root.getChildren().addAll(start, settings, exit, info);
        return root;
    }

    // Spielbildschirm erstellen
    private Pane createGame() {
        Pane root = new Pane();

        // Pokertisch Hintergrund
        try {
            ImageView table = new ImageView(new Image(getClass().getResourceAsStream("/Images/Pokertisch.jpg")));
            table.setFitWidth(1650);
            table.setFitHeight(900);
            root.getChildren().add(table);
        } catch (Exception e) {
            root.setStyle("-fx-background-color:darkgreen;");
        }

        // Pot Anzeige
        potLabel = new Label("Pot: 0");
        potLabel.setFont(Font.font(28));
        potLabel.setTextFill(Color.YELLOW);
        potLabel.setLayoutX(780);
        potLabel.setLayoutY(40);
        root.getChildren().add(potLabel);

        // Einsatz Info
        betInfoLabel = new Label("");
        betInfoLabel.setFont(Font.font(22));
        betInfoLabel.setTextFill(Color.LIGHTGREEN);
        betInfoLabel.setLayoutX(250);
        betInfoLabel.setLayoutY(80);
        root.getChildren().add(betInfoLabel);

        // Status Nachrichten
        statusLabel = new Label("");
        statusLabel.setFont(Font.font(18));
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setLayoutX(1350);
        statusLabel.setLayoutY(20);
        statusLabel.setPrefWidth(280);
        statusLabel.setWrapText(true);
        root.getChildren().add(statusLabel);

        // Spielerpositionen: unten, links, oben, rechts
        int[][] positions = {{750, 700}, {150, 350}, {750, 120}, {1350, 350}};

        // Karten und Chips für jeden Spieler
        for (int i = 0; i < players.length; i++) {
            String player = players[i];
            List<ImageView> cards = new ArrayList<>();

            for (int c = 0; c < 2; c++) {
                ImageView card = new ImageView(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
                card.setFitWidth(80);
                card.setFitHeight(120);
                card.setLayoutX(positions[i][0] + c * 90);
                card.setLayoutY(positions[i][1]);
                root.getChildren().add(card);
                cards.add(card);
            }
            playerCardViews.put(player, cards);

            Label chips = new Label("Chips: " + playerChips.get(player));
            chips.setFont(Font.font(20));
            chips.setTextFill(Color.YELLOW);
            chips.setLayoutX(positions[i][0]);
            chips.setLayoutY(positions[i][1] - 30);
            chipLabels.put(player, chips);
            root.getChildren().add(chips);
        }

        // 5 Gemeinschaftskarten
        for (int i = 0; i < 5; i++) {
            ImageView card = new ImageView(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
            card.setFitWidth(90);
            card.setFitHeight(130);
            card.setLayoutX(600 + i * 110);
            card.setLayoutY(380);
            tableCardViews.add(card);
            root.getChildren().add(card);
        }

        // Aktions-Buttons
        Button callBtn = new Button("Call");
        callBtn.setLayoutX(1400);
        callBtn.setLayoutY(780);
        callBtn.setPrefSize(140, 60);
        styleButton(callBtn);
        callBtn.setOnAction(e -> playerAction("Call"));

        Button checkBtn = new Button("Check");
        checkBtn.setLayoutX(1250);
        checkBtn.setLayoutY(780);
        checkBtn.setPrefSize(140, 60);
        styleButton(checkBtn);
        checkBtn.setOnAction(e -> playerAction("Check"));

        Button raiseBtn = new Button("Raise");
        raiseBtn.setLayoutX(1100);
        raiseBtn.setLayoutY(780);
        raiseBtn.setPrefSize(140, 60);
        styleButton(raiseBtn);
        raiseBtn.setOnAction(e -> playerAction("Raise"));

        Button foldBtn = new Button("Fold");
        foldBtn.setLayoutX(950);
        foldBtn.setLayoutY(780);
        foldBtn.setPrefSize(140, 60);
        styleButton(foldBtn);
        foldBtn.setOnAction(e -> playerAction("Fold"));

        restartButton.setLayoutX(200);
        restartButton.setLayoutY(750);
        restartButton.setDisable(true);
        restartButton.setPrefSize(160, 60);
        styleButton(restartButton);
        restartButton.setOnAction(e -> {
            if (isPlayerEliminated()) {
                showEliminationMessage();
            } else {
                startNewGame();
                restartButton.setDisable(true);
            }
        });

        Button backBtn = new Button("Menu");
        backBtn.setLayoutX(40);
        backBtn.setLayoutY(40);
        backBtn.setPrefSize(130, 60);
        styleButton(backBtn);
        backBtn.setOnAction(e -> stage.setScene(menuScene));

        root.getChildren().addAll(callBtn, checkBtn, raiseBtn, foldBtn, restartButton, backBtn);
        return root;
    }

    // Komplettes Spiel zurücksetzen
    private void resetFullGame() {
        initializePlayers();
        dealerIndex = 0;
        updateChipLabels();
        resetCardVisuals();
    }

    // Alle Karten auf Rückseite
    private void resetCardVisuals() {
        for (String player : players) {
            List<ImageView> views = playerCardViews.get(player);
            for (ImageView view : views) {
                view.setOpacity(1.0);
                view.setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
            }
        }
    }

    private boolean isPlayerEliminated() {
        return eliminatedPlayers.contains(PLAYER_NAME) || playerChips.get(PLAYER_NAME) <= 0;
    }

    private void showEliminationMessage() {
        statusLabel.setText("💀 GAME OVER!\n\nDu hast keine Chips mehr!\n\nGehe zurück zum Menü\num neu zu starten.");
        betInfoLabel.setText("ELIMINIERT - Keine Chips mehr!");
        betInfoLabel.setTextFill(Color.RED);
        restartButton.setDisable(true);
    }

    // Pleite Spieler entfernen
    private void checkAndEliminateBrokePlayers() {
        for (String player : players) {
            if (playerChips.get(player) <= 0 && !eliminatedPlayers.contains(player)) {
                eliminatedPlayers.add(player);
                appendStatus("💀 " + player + " ist pleite und ausgeschieden!");
                
                List<ImageView> views = playerCardViews.get(player);
                for (ImageView view : views) {
                    view.setOpacity(0.3);
                }
                
                chipLabels.get(player).setText("ELIMINIERT");
                chipLabels.get(player).setTextFill(Color.RED);
            }
        }
    }

    // Liste der aktiven Spieler
    private List<String> getActivePlayers() {
        List<String> active = new ArrayList<>();
        for (String player : players) {
            if (!eliminatedPlayers.contains(player)) {
                active.add(player);
            }
        }
        return active;
    }

    // Prüfen ob nur noch einer übrig ist
    private boolean checkGameEnd() {
        List<String> active = getActivePlayers();
        
        if (active.size() == 1) {
            String winner = active.get(0);
            String winnerDisplay = winner.equals(PLAYER_NAME) ? "DU HAST GEWONNEN" : winner + " hat gewonnen";
            statusLabel.setText("🏆🏆🏆\n\n" + winnerDisplay + "!\n\nAlle anderen sind pleite!\n\nGehe zum Menü für ein neues Spiel.");
            betInfoLabel.setText("SPIEL BEENDET!");
            betInfoLabel.setTextFill(Color.GOLD);
            gameOver = true;
            restartButton.setDisable(true);
            return true;
        }
        
        if (eliminatedPlayers.contains(PLAYER_NAME)) {
            showEliminationMessage();
            return true;
        }
        
        return false;
    }

    // Neue Runde starten
    private void startNewGame() {
        checkAndEliminateBrokePlayers();
        
        if (checkGameEnd()) return;

        // Alles zurücksetzen
        pot = 0;
        currentBet = 0;
        revealIndex = 0;
        gameOver = false;
        waitingForPlayerAction = false;
        foldedPlayers.clear();
        currentRoundBets.clear();
        tableCardNames.clear();

        for (String player : players) {
            currentRoundBets.put(player, 0);
            playerCardNames.get(player).clear();
        }

        // Deck mischen
        deck = new ArrayList<>(Arrays.asList(ALL_CARDS));
        Collections.shuffle(deck);

        potLabel.setText("Pot: 0");
        statusLabel.setText("Neue Runde startet...");
        betInfoLabel.setTextFill(Color.LIGHTGREEN);

        // Tischkarten ziehen
        for (int i = 0; i < 5; i++) {
            tableCardNames.add(deck.remove(0));
            tableCardViews.get(i).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
        }

        // Karten austeilen
        for (String player : players) {
            List<ImageView> views = playerCardViews.get(player);
            List<String> names = playerCardNames.get(player);

            if (eliminatedPlayers.contains(player)) {
                for (ImageView view : views) {
                    view.setOpacity(0.3);
                    view.setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
                }
                continue;
            }

            for (ImageView view : views) {
                view.setOpacity(1.0);
            }

            for (int i = 0; i < 2; i++) {
                String card = deck.remove(0);
                names.add(card);

                // Nur eigene Karten aufdecken
                if (player.equals(PLAYER_NAME)) {
                    views.get(i).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/" + card)));
                } else {
                    views.get(i).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/BlueBack.png")));
                }
            }
        }

        updateChipLabels();
        postBlinds();
        startBettingRound();
    }

    // Blinds setzen
    private void postBlinds() {
        List<String> active = getActivePlayers();
        
        if (active.size() < 2) {
            checkGameEnd();
            return;
        }

        // Dealer muss aktiv sein
        while (eliminatedPlayers.contains(players[dealerIndex])) {
            dealerIndex = (dealerIndex + 1) % players.length;
        }

        int smallBlindIndex = findNextActivePlayer(dealerIndex);
        int bigBlindIndex = findNextActivePlayer(smallBlindIndex);

        String smallBlindPlayer = players[smallBlindIndex];
        String bigBlindPlayer = players[bigBlindIndex];

        // Small Blind
        int sbAmount = Math.min(SMALL_BLIND, playerChips.get(smallBlindPlayer));
        playerChips.put(smallBlindPlayer, playerChips.get(smallBlindPlayer) - sbAmount);
        currentRoundBets.put(smallBlindPlayer, sbAmount);
        pot += sbAmount;

        // Big Blind
        int bbAmount = Math.min(BIG_BLIND, playerChips.get(bigBlindPlayer));
        playerChips.put(bigBlindPlayer, playerChips.get(bigBlindPlayer) - bbAmount);
        currentRoundBets.put(bigBlindPlayer, bbAmount);
        pot += bbAmount;

        currentBet = BIG_BLIND;

        updateChipLabels();
        potLabel.setText("Pot: " + pot);
        statusLabel.setText("Blinds: " + smallBlindPlayer + " (SB: " + sbAmount + "), " + bigBlindPlayer + " (BB: " + bbAmount + ")");
    }

    // Nächsten aktiven Spieler finden
    private int findNextActivePlayer(int currentIndex) {
        int nextIndex = (currentIndex + 1) % players.length;
        while (eliminatedPlayers.contains(players[nextIndex])) {
            nextIndex = (nextIndex + 1) % players.length;
        }
        return nextIndex;
    }

    // Chip-Labels aktualisieren
    private void updateChipLabels() {
        for (String player : players) {
            if (eliminatedPlayers.contains(player)) {
                chipLabels.get(player).setText("ELIMINIERT");
                chipLabels.get(player).setTextFill(Color.RED);
            } else {
                chipLabels.get(player).setText("Chips: " + playerChips.get(player));
                chipLabels.get(player).setTextFill(Color.YELLOW);
            }
        }
    }

    // Einsatz-Info für Spieler anzeigen
    private void updateBetInfo() {
        if (eliminatedPlayers.contains(PLAYER_NAME)) {
            betInfoLabel.setText("Du bist eliminiert!");
            betInfoLabel.setTextFill(Color.RED);
            return;
        }

        int myBet = currentRoundBets.getOrDefault(PLAYER_NAME, 0);
        int toCall = currentBet - myBet;

        if (foldedPlayers.contains(PLAYER_NAME)) {
            betInfoLabel.setText("Du hast gefoldet");
        } else if (toCall > 0) {
            betInfoLabel.setText("Aktueller Einsatz: " + currentBet + " | Du musst " + toCall + " zahlen");
        } else if (currentBet > 0) {
            betInfoLabel.setText("Aktueller Einsatz: " + currentBet + " | Du bist dabei");
        } else {
            betInfoLabel.setText("Noch kein Einsatz");
        }
    }

    // Setzrunde starten
    private void startBettingRound() {
        // Nach Flop/Turn/River Einsätze resetten
        if (revealIndex > 0) {
            for (String player : players) {
                currentRoundBets.put(player, 0);
            }
            currentBet = 0;
        }

        if (eliminatedPlayers.contains(PLAYER_NAME) || foldedPlayers.contains(PLAYER_NAME)) {
            processBotOnlyRound();
        } else {
            waitingForPlayerAction = true;
            statusLabel.setText("Dein Zug!\nCheck, Call, Raise oder Fold");
            updateBetInfo();
        }
    }

    // Spieleraktion verarbeiten
    private void playerAction(String action) {
        if (gameOver || eliminatedPlayers.contains(PLAYER_NAME) || foldedPlayers.contains(PLAYER_NAME) || !waitingForPlayerAction) {
            return;
        }

        int myBet = currentRoundBets.getOrDefault(PLAYER_NAME, 0);
        int toCall = currentBet - myBet;
        int myChips = playerChips.get(PLAYER_NAME);

        switch (action) {
            case "Call":
                if (toCall <= 0) {
                    statusLabel.setText("Nichts zu callen.\nCheck oder Raise.");
                    return;
                }
                if (myChips <= 0) {
                    statusLabel.setText("Du hast keine Chips mehr!");
                    return;
                }
                int callAmt = Math.min(toCall, myChips);
                playerChips.put(PLAYER_NAME, myChips - callAmt);
                pot += callAmt;
                currentRoundBets.put(PLAYER_NAME, myBet + callAmt);
                statusLabel.setText("Du hast gecallt (" + callAmt + ")");
                break;

            case "Check":
                if (toCall > 0) {
                    statusLabel.setText("Check nicht möglich!\nDu musst " + toCall + " callen oder folden.");
                    return;
                }
                statusLabel.setText("Du checkst");
                break;

            case "Raise":
                if (myChips <= 0) {
                    statusLabel.setText("Du hast keine Chips zum Erhöhen!");
                    return;
                }
                int raiseTotal = toCall + MIN_RAISE;
                int actualRaise = Math.min(raiseTotal, myChips);
                playerChips.put(PLAYER_NAME, myChips - actualRaise);
                pot += actualRaise;
                currentBet = myBet + actualRaise;
                currentRoundBets.put(PLAYER_NAME, currentBet);
                statusLabel.setText("Du erhöhst auf " + currentBet);
                break;

            case "Fold":
                foldedPlayers.add(PLAYER_NAME);
                statusLabel.setText("Du hast gefoldet");
                
                List<ImageView> playerViews = playerCardViews.get(PLAYER_NAME);
                for (ImageView view : playerViews) {
                    view.setOpacity(0.5);
                }
                
                updateChipLabels();
                waitingForPlayerAction = false;
                updateBetInfo();

                if (checkForWinnerByFold()) return;

                continueGameAfterPlayerFold();
                return;
        }

        updateChipLabels();
        potLabel.setText("Pot: " + pot);
        waitingForPlayerAction = false;
        updateBetInfo();

        processBotBetting();
    }

    // Wenn Spieler gefoldet hat, Bots spielen alleine weiter
    private void continueGameAfterPlayerFold() {
        while (!gameOver && revealIndex < 5) {
            processBotOnlyRound();
            
            if (!gameOver && checkForWinnerByFold()) return;
            
            if (!gameOver) {
                revealNextCard();
            }
        }

        if (!gameOver) {
            for (String bot : players) {
                if (!bot.equals(PLAYER_NAME) && !foldedPlayers.contains(bot) && !eliminatedPlayers.contains(bot)) {
                    revealBotCards(bot);
                }
            }
            determineWinner();
        }
    }

    // Setzrunde nur mit Bots
    private void processBotOnlyRound() {
        for (String player : players) {
            if (!foldedPlayers.contains(player) && !eliminatedPlayers.contains(player)) {
                currentRoundBets.put(player, 0);
            }
        }
        currentBet = 0;

        boolean someoneRaised = true;
        int maxIterations = 10;
        int iterations = 0;

        while (someoneRaised && iterations < maxIterations && !gameOver) {
            someoneRaised = false;
            iterations++;

            for (String bot : players) {
                if (bot.equals(PLAYER_NAME) || foldedPlayers.contains(bot) || eliminatedPlayers.contains(bot) || gameOver) {
                    continue;
                }

                int botBet = currentRoundBets.getOrDefault(bot, 0);
                int toCall = currentBet - botBet;

                String decision;
                if (toCall > 0) {
                    decision = getBotDecision(bot, toCall);
                } else {
                    int handScore = evaluateHandStrength(bot);
                    if (handScore > 12 && random.nextDouble() < 0.2 && playerChips.get(bot) >= MIN_RAISE) {
                        decision = "RAISE";
                    } else {
                        decision = "CHECK";
                    }
                }

                switch (decision) {
                    case "FOLD":
                        foldedPlayers.add(bot);
                        appendStatus(bot + " foldet!");
                        revealBotCards(bot);
                        if (checkForWinnerByFold()) return;
                        break;

                    case "CALL":
                        int amt = Math.min(toCall, playerChips.get(bot));
                        playerChips.put(bot, playerChips.get(bot) - amt);
                        pot += amt;
                        currentRoundBets.put(bot, botBet + amt);
                        appendStatus(bot + " callt (" + amt + ")");
                        break;

                    case "RAISE":
                        int raiseAmt = MIN_RAISE;
                        int totalAmt = toCall + raiseAmt;
                        int actual = Math.min(totalAmt, playerChips.get(bot));
                        playerChips.put(bot, playerChips.get(bot) - actual);
                        pot += actual;
                        currentBet = botBet + actual;
                        currentRoundBets.put(bot, currentBet);
                        appendStatus(bot + " ERHÖHT auf " + currentBet + "!");
                        someoneRaised = true;
                        break;

                    case "CHECK":
                        appendStatus(bot + " checkt");
                        break;
                }
                
                chipLabels.get(bot).setText("Chips: " + playerChips.get(bot));
            }

            potLabel.setText("Pot: " + pot);
        }
    }

    // Bot-Setzrunde nach Spieleraktion
    private void processBotBetting() {
        boolean someoneRaised = true;
        int maxIterations = 10;
        int iterations = 0;

        while (someoneRaised && iterations < maxIterations && !gameOver) {
            someoneRaised = false;
            iterations++;

            for (String bot : players) {
                if (bot.equals(PLAYER_NAME) || foldedPlayers.contains(bot) || eliminatedPlayers.contains(bot) || gameOver) {
                    continue;
                }

                int botBet = currentRoundBets.getOrDefault(bot, 0);
                int toCall = currentBet - botBet;

                if (toCall > 0 || (toCall == 0 && evaluateHandStrength(bot) > 12 && random.nextDouble() < 0.15)) {
                    String decision = toCall > 0 ? getBotDecision(bot, toCall) : 
                        (playerChips.get(bot) >= MIN_RAISE && random.nextDouble() < 0.2 ? "RAISE" : "CHECK");

                    switch (decision) {
                        case "FOLD":
                            foldedPlayers.add(bot);
                            appendStatus(bot + " foldet!");
                            revealBotCards(bot);
                            break;

                        case "CALL":
                            int amt = Math.min(toCall, playerChips.get(bot));
                            playerChips.put(bot, playerChips.get(bot) - amt);
                            pot += amt;
                            currentRoundBets.put(bot, botBet + amt);
                            appendStatus(bot + " callt (" + amt + ")");
                            break;

                        case "RAISE":
                            int raiseAmt = MIN_RAISE;
                            int totalAmt = toCall + raiseAmt;
                            int actual = Math.min(totalAmt, playerChips.get(bot));
                            playerChips.put(bot, playerChips.get(bot) - actual);
                            pot += actual;
                            currentBet = botBet + actual;
                            currentRoundBets.put(bot, currentBet);
                            appendStatus(bot + " ERHÖHT auf " + currentBet + "!");
                            someoneRaised = true;
                            break;

                        case "CHECK":
                            break;
                    }
                    chipLabels.get(bot).setText("Chips: " + playerChips.get(bot));
                }
            }

            potLabel.setText("Pot: " + pot);
            if (checkForWinnerByFold()) return;

            // Wenn erhöht wurde, Spieler muss reagieren
            if (someoneRaised && !foldedPlayers.contains(PLAYER_NAME) && !eliminatedPlayers.contains(PLAYER_NAME) && !gameOver) {
                int playerBet = currentRoundBets.getOrDefault(PLAYER_NAME, 0);
                int playerToCall = currentBet - playerBet;

                if (playerToCall > 0) {
                    waitingForPlayerAction = true;
                    appendStatus("\nDu musst " + playerToCall + " callen!");
                    updateBetInfo();
                    return;
                }
            }
        }

        if (!gameOver) {
            advanceRound();
        }
    }

    // Bot-Entscheidung basierend auf Handstärke
    private String getBotDecision(String bot, int toCall) {
        int handStrength = evaluateHandStrength(bot);
        int chips = playerChips.get(bot);

        if (chips <= 0) return "FOLD";

        if (chips < toCall) {
            return handStrength > 10 ? "CALL" : "FOLD";
        }

        double foldChance = 0.05;

        if (handStrength < 8) foldChance = 0.4;
        else if (handStrength < 12) foldChance = 0.15;

        if (toCall >= 100) foldChance += 0.1;
        if (toCall >= 200) foldChance += 0.15;
        if (chips < 300) foldChance += 0.1;

        double raiseChance = 0.0;
        if (handStrength >= 12) raiseChance = 0.25;
        if (handStrength >= 16) raiseChance = 0.5;

        double roll = random.nextDouble();

        if (roll < foldChance) return "FOLD";
        if (roll < foldChance + raiseChance && chips >= toCall + MIN_RAISE) return "RAISE";
        return "CALL";
    }

    // Handstärke berechnen
    private int evaluateHandStrength(String player) {
        List<String> cards = new ArrayList<>(playerCardNames.get(player));

        for (int i = 0; i < revealIndex && i < tableCardNames.size(); i++) {
            cards.add(tableCardNames.get(i));
        }

        return calculateHandValue(cards);
    }

    // Handwert berechnen (Paare, Drillinge usw.)
    private int calculateHandValue(List<String> cards) {
        if (cards.isEmpty()) return 0;

        int[] values = new int[cards.size()];
        char[] suits = new char[cards.size()];

        for (int i = 0; i < cards.size(); i++) {
            String card = cards.get(i).replace(".png", "");
            suits[i] = card.charAt(0);
            values[i] = parseCardValue(card.substring(1));
        }

        Map<Integer, Integer> valueCounts = new HashMap<>();
        for (int v : values) {
            valueCounts.put(v, valueCounts.getOrDefault(v, 0) + 1);
        }

        int pairs = 0;
        int threeOfKind = 0;
        int fourOfKind = 0;
        int highCard = 0;

        for (Map.Entry<Integer, Integer> entry : valueCounts.entrySet()) {
            int count = entry.getValue();
            int value = entry.getKey();

            if (count == 4) fourOfKind = value;
            else if (count == 3) threeOfKind = value;
            else if (count == 2) pairs++;

            highCard = Math.max(highCard, value);
        }

        // Flush prüfen
        Map<Character, Integer> suitCounts = new HashMap<>();
        for (char s : suits) {
            suitCounts.put(s, suitCounts.getOrDefault(s, 0) + 1);
        }
        boolean hasFlush = suitCounts.values().stream().anyMatch(c -> c >= 5);

        // Wert zurückgeben
        if (fourOfKind > 0) return 200 + fourOfKind;
        if (threeOfKind > 0 && pairs > 0) return 180 + threeOfKind;
        if (hasFlush) return 160 + highCard;
        if (threeOfKind > 0) return 100 + threeOfKind;
        if (pairs >= 2) return 50 + highCard;
        if (pairs == 1) return 30 + highCard;

        return highCard;
    }

    // Kartenwert parsen
    private int parseCardValue(String valueStr) {
        switch (valueStr) {
            case "A": return 14;
            case "K": return 13;
            case "D": return 12;
            case "B": return 11;
            case "10": return 10;
            default:
                try {
                    return Integer.parseInt(valueStr);
                } catch (Exception e) {
                    return 0;
                }
        }
    }

    // Bot-Karten aufdecken
    private void revealBotCards(String bot) {
        List<ImageView> views = playerCardViews.get(bot);
        List<String> names = playerCardNames.get(bot);
        for (int i = 0; i < 2 && i < names.size(); i++) {
            views.get(i).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/" + names.get(i))));
        }
    }

    // Status-Text erweitern
    private void appendStatus(String text) {
        String current = statusLabel.getText();
        if (current.length() > 200) {
            current = current.substring(current.indexOf("\n") + 1);
        }
        statusLabel.setText(current + "\n" + text);
    }

    // Prüfen ob alle bis auf einen gefoldet haben
    private boolean checkForWinnerByFold() {
        List<String> active = new ArrayList<>();
        for (String p : players) {
            if (!foldedPlayers.contains(p) && !eliminatedPlayers.contains(p)) {
                active.add(p);
            }
        }

        if (active.size() == 1) {
            String winner = active.get(0);
            endGame(winner, "Alle anderen haben gefoldet!");
            return true;
        }
        return false;
    }

    // Zur nächsten Phase
    private void advanceRound() {
        if (gameOver) return;

        revealNextCard();

        if (revealIndex >= 5) {
            // Showdown
            for (String bot : players) {
                if (!bot.equals(PLAYER_NAME) && !foldedPlayers.contains(bot) && !eliminatedPlayers.contains(bot)) {
                    revealBotCards(bot);
                }
            }
            determineWinner();
        } else {
            startBettingRound();
        }
    }

    // Nächste Karte(n) aufdecken
    private void revealNextCard() {
        if (revealIndex == 0) {
            // Flop
            for (int i = 0; i < 3; i++) {
                tableCardViews.get(i).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/" + tableCardNames.get(i))));
            }
            revealIndex = 3;
            appendStatus("--- FLOP ---");
        } else if (revealIndex == 3) {
            // Turn
            tableCardViews.get(3).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/" + tableCardNames.get(3))));
            revealIndex = 4;
            appendStatus("--- TURN ---");
        } else if (revealIndex == 4) {
            // River
            tableCardViews.get(4).setImage(new Image(getClass().getResourceAsStream("/ALLCards/Projekt/" + tableCardNames.get(4))));
            revealIndex = 5;
            appendStatus("--- RIVER ---");
        }
    }

    // Gewinner ermitteln
    private void determineWinner() {
        gameOver = true;
        waitingForPlayerAction = false;
        restartButton.setDisable(false);

        Map<String, Integer> scores = new HashMap<>();
        for (String p : players) {
            if (!foldedPlayers.contains(p) && !eliminatedPlayers.contains(p)) {
                List<String> allCards = new ArrayList<>(playerCardNames.get(p));
                allCards.addAll(tableCardNames);
                scores.put(p, calculateHandValue(allCards));
            }
        }

        if (scores.isEmpty()) {
            statusLabel.setText("Alle haben gefoldet!");
            return;
        }

        String winner = Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
        endGame(winner, "Beste Hand!");
    }

    // Spiel beenden und Pot auszahlen
    private void endGame(String winner, String reason) {
        gameOver = true;
        waitingForPlayerAction = false;

        playerChips.put(winner, playerChips.get(winner) + pot);
        updateChipLabels();

        String winnerDisplay = winner.equals(PLAYER_NAME) ? "Du gewinnst" : winner + " gewinnt";
        statusLabel.setText("🏆 " + winnerDisplay + "!\n" + reason + "\nGewinn: " + pot);
        
        pot = 0;
        potLabel.setText("Pot: 0");
        betInfoLabel.setText("");

        List<ImageView> playerViews = playerCardViews.get(PLAYER_NAME);
        for (ImageView view : playerViews) {
            view.setOpacity(1.0);
        }

        checkAndEliminateBrokePlayers();

        if (!checkGameEnd()) {
            restartButton.setDisable(false);
        }

        // Dealer rotieren
        do {
            dealerIndex = (dealerIndex + 1) % players.length;
        } while (eliminatedPlayers.contains(players[dealerIndex]) && getActivePlayers().size() > 1);
    }

    // Einstellungen-Screen
    private Pane createSettings() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color:black");

        Label label = new Label("Musik Einstellungen");
        label.setLayoutX(550);
        label.setLayoutY(100);
        label.setStyle("-fx-font-size:50px;");
        label.setTextFill(Color.WHITE);

        volumeSlider.setLayoutX(650);
        volumeSlider.setLayoutY(700);
        volumeSlider.setPrefWidth(300);
        volumeSlider.valueProperty().addListener((obs, o, n) -> {
            if (currentMusic != null) currentMusic.setVolume(n.doubleValue());
        });

        Button musik1 = musicButton("Musik 1", 450, 300, music1);
        Button musik2 = musicButton("Musik 2", 450, 400, music2);
        Button musik3 = musicButton("Musik 3", 450, 500, music3);
        Button musik4 = musicButton("Musik 4", 900, 300, music4);
        Button musik5 = musicButton("Musik 5", 900, 400, music5);
        Button musik6 = musicButton("Musik 6", 900, 500, music6);

        Button back = new Button("Zurück");
        back.setLayoutX(40);
        back.setLayoutY(40);
        back.setPrefSize(130, 60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        root.getChildren().addAll(label, musik1, musik2, musik3, musik4, musik5, musik6, volumeSlider, back);
        return root;
    }

    // Info-Screen mit Regeln
    private Pane createInfo() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color:black");

        Label title = new Label("Poker Regeln");
        title.setLayoutX(600);
        title.setLayoutY(60);
        title.setStyle("-fx-font-size:60px;");
        title.setTextFill(Color.WHITE);

        Label rules = new Label(
            "🎯 Ziel: Die beste 5-Karten-Hand gewinnt den Pot.\n\n" +
            "💰 Blinds\n" +
            "Small Blind: 25 | Big Blind: 50\n" +
            "Blinds rotieren jede Runde.\n\n" +
            "🔄 Spielablauf\n" +
            "1. Jeder erhält 2 verdeckte Karten\n" +
            "2. Setzrunde (Pre-Flop)\n" +
            "3. Flop: 3 Gemeinschaftskarten\n" +
            "4. Setzrunde\n" +
            "5. Turn: 4. Karte\n" +
            "6. Setzrunde\n" +
            "7. River: 5. Karte\n" +
            "8. Finale Setzrunde + Showdown\n\n" +
            "🎮 Aktionen\n" +
            "Check - Kein Einsatz (nur wenn möglich)\n" +
            "Call - Aktuellen Einsatz mitgehen\n" +
            "Raise - Erhöhen (min. 50)\n" +
            "Fold - Aussteigen (kann nicht mehr gewinnen!)\n\n" +
            "💀 Eliminierung\n" +
            "Spieler ohne Chips sind ELIMINIERT!\n" +
            "Das Spiel endet, wenn nur noch ein Spieler übrig ist.\n\n" +
            "💡 Alle müssen gleich viel zahlen um weiterzuspielen!"
        );
        rules.setLayoutX(100);
        rules.setLayoutY(180);
        rules.setStyle("-fx-font-size:20px; -fx-text-fill:white;");
        rules.setWrapText(true);
        rules.setMaxWidth(750);

        Button back = new Button("Zurück");
        back.setLayoutX(40);
        back.setLayoutY(40);
        back.setPrefSize(130, 60);
        styleButton(back);
        back.setOnAction(e -> stage.setScene(menuScene));

        try {
            Image img = new Image(getClass().getResourceAsStream("/Images/PokerRules.png"));
            ImageView imgView = new ImageView(img);
            imgView.setLayoutX(900);
            imgView.setLayoutY(180);
            imgView.setFitWidth(550);
            imgView.setPreserveRatio(true);
            root.getChildren().add(imgView);
        } catch (Exception e) {
            // Bild nicht da
        }

        root.getChildren().addAll(title, rules, back);
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
