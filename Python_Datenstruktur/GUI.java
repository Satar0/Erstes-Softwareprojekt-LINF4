import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;

//import GUI_Logik;
//import javax.swing.*;
public class GUI {
    private JFrame frame ;
    private JButton myGameButton;
    private JButton startConnectionButton;
    private JButton endConnectionButton;
    private JTextField ServerIPTextfeld;
    private JTextField ServerPortTextfeld;
    private GUI_Logik myLogik;

    GUI(){
        frame = new JFrame("Client");
        myGameButton = new JButton("game1");
        startConnectionButton = new JButton("connect to Server");
        endConnectionButton = new JButton("end connection");
        ServerIPTextfeld = new JTextField("127.0.0.1",10);
        ServerPortTextfeld = new JTextField("65432",6);
        myLogik = new GUI_Logik();


    }
    public void main(String[] args){

        myGameButton.addActionListener(e-> {
            myLogik.GameButtonKlicked();
        });

        //clickButton.setFocusable(false);
        endConnectionButton.addActionListener(e-> {
            myLogik.endConnectionButtonKlicked();
        });

        startConnectionButton.addActionListener(e->{
            String IP = ServerIPTextfeld.getText();
            int Port = Integer.parseInt(ServerPortTextfeld.getText());
            myLogik.startConnectionButtonKlicked(IP, Port);
        });

        //ab hier werden gui elemente wie knöpfe und textfelder konfiguriert
        ServerIPTextfeld.setSize(new Dimension(1000,20));
        ServerPortTextfeld.setPreferredSize(new Dimension(1000,20));
        
        frame.setLayout(new FlowLayout(10,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(myGameButton);
        frame.add(startConnectionButton);
        frame.add(endConnectionButton);
        frame.add(ServerIPTextfeld);
        frame.add(ServerPortTextfeld);
        
        frame.setSize(180, 300);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
