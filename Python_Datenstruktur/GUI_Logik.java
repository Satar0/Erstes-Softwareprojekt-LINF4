import game.*;
import org.json.JSONObject;

public class GUI_Logik implements GameListener{


    private Client myClient;
    private firstGame myfirstGame;
    //private JsonManager

    @Override // überschreibt die abstrakte methode des interfaces Gamelistener
    public void mousePosition(int x, int y){//wird jedes mal ausgeführt wenn die mausposition sich ändert
        JSONObject firstGameData = new JSONObject();
        firstGameData.put("game", "firstGame");
        firstGameData.put("mouseX", x);
        firstGameData.put("mouseY",y);
        firstGameData.put("end", false);
        myClient.sendJSONData(firstGameData); // sendet die informationen an den server
        JSONObject serverResponse = new JSONObject(myClient.recieveData());//wartet dann auf eine antwort
        //myfirstGame.drawPixel([,serverResponse["mouseY"]]);
        int mouseX = serverResponse.getInt("mouseX");
        int mouseY = serverResponse.getInt("mouseY");
        myfirstGame.drawPixel(mouseX,mouseY);//malt an der stelle die der server geschickt hat
    }
    //konstruktor der gui
    //public GUI_Logik(){
        /*
        -zeigt die gui an
        -ruft den client auf, gibt ihm daten welche er an den server schickt
        -sagt dem client wann er wenn er empfangen soll
         -startet die spiele
        */
    //}

    public void GameButtonKlicked(){
        myfirstGame = new firstGame();//startet das spiel
        myfirstGame.addGameListener(this);//notwendug um mausevents zu tracken
        System.out.println("first game has been started");
    }

    public void endConnectionButtonKlicked(){//aktiviert wenn verbindung beenden knopf geklickt wird
        JSONObject gameInfo = new JSONObject();
        gameInfo.put("end",true);
        myClient.sendJSONData(gameInfo);//sendet jsonObject mit beenden an den server
    }

    public void startConnectionButtonKlicked(String IP,int Port){//versucht sich mit dem server zu verbinden
        System.out.println("trying to connect to: " +IP+" ont the port: "+ Port);
        myClient = new Client(IP,Port);}

    public void sendToServer(String game, Boolean endConnection, String username, JSONObject gameData){
        JSONObject GameData = new JSONObject();
        GameData.put("game", "game");
        GameData.put("endConnection",endConnection);
        GameData.put("username", username);
        GameData.put("gameData",gameData);
        myClient.sendJSONData(GameData);
    }

    public JSONObject waitForServerResponse(){
        JSONObject serverResponse = new JSONObject(myClient.recieveData());
        return serverResponse;
    }

}
//ideen:
//start connection nicht klickbar wenn es verbindung gibt
//pw unf uName textfeld
//gui anzeige und logik trennen
//mehrere Verbindungen von server eingehen können
//wie fuktioniert mvc-paradigma