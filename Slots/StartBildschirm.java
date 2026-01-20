import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class StartBildschirm extends Application {
  
   private int Money = 100;
  

    @Override
    public void start(Stage primaryStage) {
    
          

        Pane root = new Pane();
    
    
        //buttons... 
    
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
           
                Stage gameStage = new Stage();

               
                slots game = new slots(Money);             // money ubergabe
                game.start(gameStage);                    // start von slots

                primaryStage.close();                   //dieses fenster schließen
            
        });

        exit.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(startGame, exit);

        Scene scene = new Scene(root, 1600, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Startbildschirm");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

