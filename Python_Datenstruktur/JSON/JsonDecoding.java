import org.json.JSONObject;

public class JsonDecoding {
    public static void main(String[] args) {
        String jsonString ="{\"Full Name\":\"Ritu Sharma\",\"Tuition Fees\":65400.0,\"Roll No.\":1704310046}";

        JSONObject jsonObject = new JSONObject(jsonString);


        String name = (String) jsonObject.get("Full Name");
        int fees = jsonObject.getInt("Tuition Fees");
        //double fees = (Double) jsonObject.get("Tuition Fees");
        //long rollNo = (Long) jsonObject.get("Roll No.");

        System.out.println(name + " " + fees + " ");
    }
}