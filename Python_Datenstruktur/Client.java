import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import org.json.JSONObject;


public class Client {

    // instanzvariablen:
        private Socket mySocket;
        private OutputStream myOutStream;
        private BufferedReader myInStream;
        //private DataOutputStream myDataOut;
        //private InputStream in;

    // konstruktor für CLient.java
    public Client(String ServerIP, int ServerPort){
        try {

            //reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            //myOutStream = new DataOutputStream(mySocket.getOutputStream());
            //InputStreamReader r = new InputStreamReader(System.in);

            mySocket = new Socket(ServerIP, ServerPort);// eröffnet eine Verbindung

            myInStream = new BufferedReader(new InputStreamReader(mySocket.getInputStream(), StandardCharsets.UTF_8));//wird für das Epfangen von Daten genutzt
            myOutStream = mySocket.getOutputStream();// wird für das Senden von Daten genutzt


            JSONObject initialMessage = new JSONObject();
            initialMessage.put("IP", mySocket.getInetAddress());  
            System.out.println("enter Username");          
            initialMessage.put("Username",System.console().readLine());
            System.out.println("enter Password"); 
            initialMessage.put("Password", System.console().readLine());//the initial message to the server containing client data

            myOutStream.write(initialMessage.toString().getBytes(StandardCharsets.UTF_8));// sends initial message to server in UTF_8
            myOutStream.flush();
            System.out.println("client send: " + initialMessage.toString());

            String serverResponse = myInStream.readLine();//recieves the server response
            System.out.println("The server has send: " + serverResponse);//prints the response

        } catch (Exception e) {
            System.out.println("im Konstruktor vom client: "+e);
        }
    }

    public void sendJSONData(JSONObject JSONData) {//argument is a json object; this gets send to the server and printed to the console
        try {
            String Data = JSONData.toString();//converts to string 
            myOutStream.write((Data).getBytes("UTF-8"));// sends data
            myOutStream.flush();
            System.out.println("client has sent: " + Data);//prints sent data

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String recieveData(){
        try{
            return myInStream.readLine();//recieves the server response
        }catch(Exception e) {
            System.out.println(e);
            return "";
        }
    }

    public void endConnection(){//closes the connection with the server
        try{
            myOutStream.close();
            mySocket.close();// beenden der Verbindung
            System.out.println("client hat die verbindung beendet");
        }catch(Exception e){
            System.out.println(e);
        }   
    }
}