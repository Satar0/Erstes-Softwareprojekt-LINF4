import org.json.JSONObject;

public class JsonManager {

    public JSONObject addDefault(JSONObject jsonObject, String username, String game, boolean verbindungBeenden){
        jsonObject.put("Username", username);
        jsonObject.put("Game", game);
        jsonObject.put("verbindungBeenden", verbindungBeenden);
        return jsonObject;
    }
}