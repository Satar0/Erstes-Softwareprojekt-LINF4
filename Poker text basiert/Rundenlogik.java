/*
Software Projekt 
Poker
Rundenlogik Klasse
Timur Druba
10.03.2026
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.DocFlavor.STRING;

public class Rundenlogik 
{
 //Input Output Klasse
 private TEST input = new TEST();

 private int einsatz = 50; // aktueller Einsatz der Runde

 private int Startgeld = 1000; // Startgeld wird muss noch festgelegt werden
 private int Spieleranzahl = 4;
 private int Rundenlimit = 8; // max anzahl an gespielten Runden bis zum automatischen Abbruch (zur sicherheit)

 private String Name; // um Spieler name festzulegen

 private boolean Stop = false; // um ein Spiel voreilig zu Stoppen

 private int Spieleraktion; // 1 = raise // 2 = check/call // 3 = fold
 private int raise;
 private int zeigeKarten; // 1=ja // andere Zahl = nein
 private int Handwert; // uzm bewerten der Hände von Spielern

 private String[] gemeineKarten = new String[5]; // 5 Karten in der Mitte

 private Spieler[] spieler = new Spieler[Spieleranzahl];
 private Handwerte Handwerte = new Handwerte();
 
private static final String[] ALLE_KARTEN = {
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
};

private ArrayList<String> karten = new ArrayList<String>(); // ArrayList für unverteilte karten

 public Rundenlogik () {}

 // Getter
 public int getEinsatz() {return einsatz;}

 public int getStartgeld(){return Startgeld;}
 public int getSpieleranzahl(){return Spieleranzahl;}
 public int getRundenlimit(){return Rundenlimit;}

 public String getName(){return Name;}
 public boolean getStop(){return Stop;}

 public int getSpieleraktion(){return Spieleraktion;}
 public int getraise(){return raise;}
 public int getZeigeKarten(){return zeigeKarten;}
 public int getHandwert(){return Handwert;}

 public String[] getGemeineKarten(){return gemeineKarten;}

 public Spieler[] getSpieler(){return spieler;}

 // Setter
 public void setEinsatz(int einsatz) {this.einsatz = einsatz;}

 public void setStartgeld(int Startgeld){this.Startgeld = Startgeld;}
 public void setSpieleranzahl(int Spieleranzahl){this.Spieleranzahl = Spieleranzahl;}
 public void setRundenlimit(int Rundenlimit){this.Rundenlimit = Rundenlimit;}

 public void setName(String Name){this.Name = Name;}
 public void setStop(boolean Stop){this.Stop = Stop;}

 public void setSpieleraktion(int Spieleraktion){this.Spieleraktion = Spieleraktion;}
 public void setRaise(int raise){this.raise = raise;}
 public void setZeigeKarten(int zeigeKarten){this.zeigeKarten = zeigeKarten;}
 public void setHandwert(int Handwert){this.Handwert = Handwert;}

 public void setGemeineKarten(String[] gemeineKarten){this.gemeineKarten = gemeineKarten;}

 public void setSpieler(Spieler[] spieler){this.spieler = spieler;}

 // Funktionen


/*
einheitliche Funktionen, um Variablen aus andere Klassen zu bekommen
*/
 private int askInt()
 {
  return input.integer();
 }
 private String askString()
 {
  return input.string();
 }
 private boolean askBoolean()
 {
  return input.bool();
 }
/*
zufällige zahl zwischen min und max (inclusive) wird ausgegeben
 */
private int random(int min, int max)
{
    return min + (int)(Math.random() * ((max - min) + 1));
}
/*
Hand in values und Suits teilen,
dann Handwert bestimmen
*/
private List<Integer> values = new ArrayList<Integer>(); // Liste von Werten aller Karten welche einem spieler zugeordnet werden können
private List<String> suits = new ArrayList<String>(); // Liste von Farben aller Karten welche einem spieler zugeordnet werden können
private String[] SpKarten = new String[2];
private String[] Karten = new String[7];
private int Handwert(int Spieler)
{
  int Handwert = 0;
  SpKarten = spieler[Spieler].getKarten();
  Karten =  spieler[Spieler].getAlleKarten();
  System.err.println();
  for(int i = 0; i < Karten.length; i++)
  { 
    // unübersichtlich und ineffizient, aber praktisch und schnell
    System.out.print(Karten[i] + ", ");
    if(Karten[i].charAt(1) == '2') {values.add(2);} else // ordne allen Karten einen Wert zu
    if(Karten[i].charAt(1) == '3') {values.add(3);} else
    if(Karten[i].charAt(1) == '4') {values.add(4);} else
    if(Karten[i].charAt(1) == '5') {values.add(5);} else
    if(Karten[i].charAt(1) == '6') {values.add(6);} else
    if(Karten[i].charAt(1) == '7') {values.add(7);} else
    if(Karten[i].charAt(1) == '8') {values.add(8);} else
    if(Karten[i].charAt(1) == '9') {values.add(9);} else 
    if(Karten[i].charAt(1) == '1') {values.add(10);} else
    if(Karten[i].charAt(1) == 'B') {values.add(11);} else
    if(Karten[i].charAt(1) == 'D') {values.add(12);} else
    if(Karten[i].charAt(1) == 'K') {values.add(13);} else
    if(Karten[i].charAt(1) == 'A') {values.add(14);} else
    {values.add(0);}

    if(Karten[i].charAt(0) == 'H') {suits.add("Hearts");} else // ordne allen Karten eine Farbe zu
    if(Karten[i].charAt(0) == 'K') {suits.add("Clubs");} else
    if(Karten[i].charAt(0) == 'P') {suits.add("Spades");} else
    {suits.add("Diamonds");}
  }
  // um Höchste Handkarte zu bestimmen
  int HöchsteKarte = 0;
  int zweithöchsteKarte = 0;
  int aktuelleKarte = 0;
  for(int i = 0; i < SpKarten.length; i++)
  {
    if(Karten[i].charAt(1) == '2') {aktuelleKarte = 2;} else // ordne Karten vom Spieler Wert zu
    if(Karten[i].charAt(1) == '3') {aktuelleKarte = 3;} else
    if(Karten[i].charAt(1) == '4') {aktuelleKarte = 4;} else
    if(Karten[i].charAt(1) == '5') {aktuelleKarte = 5;} else
    if(Karten[i].charAt(1) == '6') {aktuelleKarte = 6;} else
    if(Karten[i].charAt(1) == '7') {aktuelleKarte = 7;} else
    if(Karten[i].charAt(1) == '8') {aktuelleKarte = 8;} else
    if(Karten[i].charAt(1) == '9') {aktuelleKarte = 9;} else 
    if(Karten[i].charAt(1) == '1') {aktuelleKarte = 10;} else
    if(Karten[i].charAt(1) == 'B') {aktuelleKarte = 11;} else
    if(Karten[i].charAt(1) == 'D') {aktuelleKarte = 12;} else
    if(Karten[i].charAt(1) == 'K') {aktuelleKarte = 13;} else
    if(Karten[i].charAt(1) == 'A') {aktuelleKarte = 14;}
   
    if(HöchsteKarte < aktuelleKarte)
    {
      zweithöchsteKarte = HöchsteKarte;
      HöchsteKarte = aktuelleKarte;
    }
    else
    {
      zweithöchsteKarte = aktuelleKarte;
    }
 }
  Handwert = Handwerte.handKontrolle(values, suits); // genereller Handwert
  System.out.print(Handwert + "; ");
  Handwert = Handwert + (HöchsteKarte*100); // plus wertvollste Handkarten für Unentschieden
  System.out.print(Handwert+ "; ");
  Handwert = Handwert + zweithöchsteKarte;
  System.out.print(Handwert+ "; ");
  values.removeAll(values);
  suits.removeAll(suits);
  return Handwert;
}

/*
aktion 1,2 oder 3 raise check oder fold
*/
private int Aktionen(int spielercounter,int AkSp, int pot)
{
boolean gleicherEinsatz = false;

 //solange Spielereinsatz zwichen aktiven Spielern unterschiedlich ist:
 while (gleicherEinsatz == false || spielercounter < Spieleranzahl)  // wiederholung
 {
   // wenn Spieler Raus oder Allin überspringen
  int FoldCounter = 0;
  while(spieler[AkSp].getFold() == true || spieler[AkSp].getAllIn() == true || spieler[AkSp].getRaus() == true)
  {
    // System.out.println(AkSp + " Übersprungen");
   if(AkSp < Spieleranzahl-1)
   {
    AkSp++;
   } else { AkSp = 0; }
   spielercounter++; // zählt wie viele Spieler schon drann gewäsen währen

   FoldCounter++;
   if(FoldCounter > Spieleranzahl)
   {
    System.out.println("Alle Raus evtl.AllIn");  
    break;
   }

  }

  // bestimme Spieler aktion & beachte Blinds
  if(spieler[AkSp].getBlind() == 1) // wenn aktueller Spieler small Blind ist
  {
   Spieleraktion = 2;//check (setzt 50 weil einsatz = 50)
  }else if(spieler[AkSp].getBlind() == 2) // wenn aktueller Spieler big Blind ist
  {
   Spieleraktion = 1; // raise um 50 //sollte geändert werden
  } else
  {
  //get Spieler aktion (check/call;raise;fold)
  Spieleraktion = 0;
  while (Spieleraktion < 1 || Spieleraktion > 3) {
    System.out.println(AkSp + spieler[AkSp].getSpielerame() + " Spieleraktion(int 1=raise; 2=check/call; 3=fold) " );
    Spieleraktion = askInt(); // Spieleraktion 1 = raise / 2 = check / 3 = fold
  }
  }

  if(Spieleraktion == 1) // raise
  {
   System.out.println("raise(int): ");
   if(spieler[AkSp].getBlind() == 2) // wenn aktueller Spieler big Blind ist
   {
    raise = einsatz; // einsatz verdoppeln
   } else
   {
    raise = askInt(); // normaler Spieler
   }
   if(spieler[AkSp].getChips() > raise + einsatz - spieler[AkSp].getSpielerEinsatz()) //check, ob der spieler genug chips hat
   {
   System.out.println(AkSp  + " " + spieler[AkSp].getSpielerame() + " raise: " + einsatz+ " + " + raise);
   pot = pot + einsatz + raise - spieler[AkSp].getSpielerEinsatz(); // aktuallisiere pot
   //aktualisiere Einsatz
    einsatz = spieler[AkSp].raise(raise,einsatz,pot);
   } else { // wenn der spieler mehr setzen will als er hat (allIn)
    System.out.println(AkSp  + " " + spieler[AkSp].getSpielerame() + " allIn: " + spieler[AkSp].getSpielerEinsatz() + " + " + spieler[AkSp].getChips());
    pot = pot + spieler[AkSp].getChips();
    einsatz = spieler[AkSp].getSpielerEinsatz()  + spieler[AkSp].getChips();
    spieler[AkSp].allIn(pot);
   }
  }

  if(Spieleraktion == 2) // check
  {
   if(spieler[AkSp].getChips() > einsatz - spieler[AkSp].getSpielerEinsatz()) //check, ob der spieler genug chips hat
   {
    pot = pot + einsatz - spieler[AkSp].getSpielerEinsatz(); // aktuallisiere pot
    spieler[AkSp].check(einsatz,pot);
    System.out.println(AkSp  + " " + spieler[AkSp].getSpielerame() + " check: " + einsatz);
   } else { // allIn
    System.out.println(AkSp  + " " + spieler[AkSp].getSpielerame() + " allIn: " + spieler[AkSp].getSpielerEinsatz() + " + " + spieler[AkSp].getChips());
    pot = pot + spieler[AkSp].getChips();
    einsatz = spieler[AkSp].getSpielerEinsatz()  + spieler[AkSp].getChips();
    spieler[AkSp].allIn(pot);
   }
  }

  if(Spieleraktion == 3) // fold
  {
   spieler[AkSp].fold();
   System.out.println(AkSp  + " " + spieler[AkSp].getSpielerame() + " fold");
  }

  // setze Blind zurück
  spieler[AkSp].setBlind(0);

  // AktiverSpieler wird gewählt
  if(AkSp < Spieleranzahl-1)
  {
   AkSp++;
  } else { AkSp = 0; }
  spielercounter++; // zählt wie viele Spieler schon drann gewäsen währen


  // Schleife um zu checken, ob jeder den einsatz gesetzt hat // beachte, dass nicht jeder noch in der Runde ist,(fold etc.)
  gleicherEinsatz = false;
  for(int i = 0;  i < Spieleranzahl; i++)
  {
   while(spieler[i].getFold() == true || spieler[i].getRaus() == true || spieler[i].getAllIn() == true) // wenn Spieler Raus überspringen
   {
    i++;
    if(i > Spieleranzahl-1)
    {
      i--;
      gleicherEinsatz = true; // wenn alle genau den einsatz gesetzt haben = true
      break;
    }
   }

   if(einsatz != spieler[i].getSpielerEinsatz() )//Spieler hat nicht den gleichen einsatz
   {
    break;
   } else if(i >= Spieleranzahl-1)
   {
    gleicherEinsatz = true; // wenn alle genau den einsatz gesetzt haben = true
   }

  }

  if(gleicherEinsatz == true && spielercounter >= Spieleranzahl)
  {
   System.out.println("gleicher einsatz & alle waren drann");
  }
  //wiederhole mit neuem aktuellen Spieler
 }

 // pot von AllIn spielern richtig stellen???????????????????????????????????????
 for(int i = 0;  i < Spieleranzahl; i++)
 {
  if(spieler[i].getAllIn())
  {
    //spieler[i].getSpielerPot();
    //spieler[i].setSpielerPot();
  }
 }

 return pot; // gebe aktuellen pot zurück
}

/*
führt eine Runde aus:
5 Karten
ein Einsatz, ein Gewinner
 */
private void Runde(int StSp) // StSp = Startspieler
{

int spielercounter = 0; // counter um sicher zu gehen, dass jeder drann kommt
int pot = 0; // zusammenes in die Runde geworfenes Geld
 //bestimme Blinds / Startspieler
 spieler[StSp].setBlind(1); // small Blind

 // bestimme Spieler nach Startspieler, um Big Blind zu bestimmen
 int nextStSp = StSp;
 if (nextStSp < Spieleranzahl - 1)
 {
  nextStSp++;
 } else {
  nextStSp = 0;
 }

 int RausCounter = 0;
 while (spieler[nextStSp].getRaus() == true) // wenn Spieler Raus überspringen
 {
  if (nextStSp < Spieleranzahl - 1) {
   nextStSp++;
  } else {
   nextStSp = 0;
  }
  RausCounter++;
  if (RausCounter > Spieleranzahl)
  {
   System.out.println("Alle Raus");
   break;
  }

 }
 spieler[nextStSp].setBlind(2); // big Blind
 int AkSp = StSp; //AkSp = Aktueller Spieler

 //bestimme Einsatz
 System.out.println("start Einsatz(int): ");
 einsatz = askInt();


  // 5 öffentliche Karten werden bestimmt
 for (int i = 0; i < gemeineKarten.length; i++)
 {
  int random = random(0,karten.size()-1); // zufällige Karte aus Stapel wird "gezogen"
  gemeineKarten[i] = karten.get(random);// karte wird in Mitte gelegt
  karten.remove(random);                // karte wird vom Deck entfernt
 }

 //gib Spieler Karten aus
 for(int i = 0;  i < Spieleranzahl; i++)
 {
  String[] spielerKarten = new String[2];
  String[] spielerAlleKarten = new String[7];

  int random = random(0,karten.size()-1); // zufällige Karte aus Stapel wird "gezogen"
  spielerKarten[0] = karten.get(random);// karte wird an spieler gegeben
  karten.remove(random);                // karte wird vom Deck entfernt

  random = random(0,karten.size()-1);     // neue zufällige Karte aus Stapel wird "gezogen"
  spielerKarten[1] = karten.get(random);// karte wird an spieler gegeben
  karten.remove(random);                // karte wird vom Deck entfernt

  spieler[i].setKarten(spielerKarten); // Spieler sammelt beide Karten ein (sie werden vom Spieler gespeichert)

  // kombiniere spieler Karten und gemeine Karten zu spielerAlleKarten in ein Array
  System.arraycopy(spielerKarten, 0, spielerAlleKarten,0, spielerKarten.length); 
  System.arraycopy(gemeineKarten, 0, spielerAlleKarten,2, gemeineKarten.length);
  spieler[i].setAlleKarten(spielerAlleKarten); //private + öffentliche karten

  System.out.println(i  + " " + spieler[i].getSpielerame() +  " " + spielerKarten[0] + " & " + spielerKarten[1]);
 }
 System.out.println("Karten ausgegeben");


 //Spieler Aktionendurchführen
 pot = Aktionen(spielercounter, AkSp, pot);
 System.out.println("pot: "+pot);

 //öffne 3 Karten
System.out.println("öffne 3 Karten: ");
System.out.println(gemeineKarten[0] +" "+ gemeineKarten[1] +" "+ gemeineKarten[2]);

/* nicht mehr nötig:
 // setze gleichereinsatz und spielercounter zurück, um neue Aktionen zu erlauben
gleicherEinsatz = false;
spielercounter = 0;
*/

   //Spieler Aktionendurchführen
 pot = Aktionen(spielercounter, AkSp, pot);
 System.out.println("pot: "+pot);
   //öffne eine Karte, oder beende letzte Runde
 System.out.println("öffne die 4 Karte: ");
 System.out.println(gemeineKarten[3]);

 // vorletzte Aktionen Runde
 pot = Aktionen(spielercounter, AkSp, pot);
 System.out.println("pot: "+pot);
 // letzte Karte öffnen
 System.out.println("öffne die letzte Karte: ");
 System.out.println(gemeineKarten[4]);

 // letzte Aktionen Runde !!!
 pot = Aktionen(spielercounter, AkSp, pot);
 System.out.println("pot: "+pot);
 // alle Karte zeigen
 System.out.println("alle gemeinschaffts Karten: " + gemeineKarten[0] +" "+ gemeineKarten[1] +" "+ gemeineKarten[2] +" "+ gemeineKarten[3] +" "+ gemeineKarten[4]);
 //wenn mehr als 1 Spieler: Zeige Karten von übrigen Spielern
 int FoldCounter = 0; // zählt wie viele Spieler gefoldet haben oder Raus sind
 for (int i = 0; i < Spieleranzahl; i++)
 {
  if (spieler[i].getFold() || spieler[i].getRaus())
  {
   FoldCounter++;
  }
 }

 // wenn nur ein Spieler nicht gefoldet hat
 if(FoldCounter == Spieleranzahl-1 )
 {
  System.out.println("zeigeKarten(int 1=ja): ");
  zeigeKarten = askInt(); // Eingabewert, um auszusuchen ob man Karten zeigen möchte // 1 = Karten zeigen // 0 = Karten nicht zeigen
  for (int i = 0; i < Spieleranzahl; i++)
  {
   if(spieler[i].getFold() == false && spieler[i].getRaus() == false)
   {
    //übergebe Pot an Gewinner
    spieler[i].setChips(spieler[i].getChips() + pot); // letzter Spieler gewinnt, und bekommt den ganze pot

    System.out.println("nur noch Spieler "+ i + " " + spieler[i].getSpielerame() +" übrig. Dieser gewinnt: " + pot);
    if (zeigeKarten == 1)
    {
     // zeige gewinner Karten
     String[] spielerKarten = new String[2];
     spielerKarten = spieler[i].getKarten();
     System.out.println("gewinner Karten: "+spielerKarten[0]+" "+spielerKarten[1]);
    }
   }
  }
 }

 if (FoldCounter < Spieleranzahl-1)
 {
  Handwert = 0; // Handwert wird später zugeordnet
  int[][] ranking = new int[Spieleranzahl][2]; // index 0 = 1.Platz ... //int[Platz][0=SpielerID,1=Handwert]
  for (int i = 0; i < Spieleranzahl; i++)
  {
   if(spieler[i].getFold() == false && spieler[i].getRaus() == false)
   {
    // zeige Karten aller übrigen Spieler
    String[] spielerKarten = new String[2];
    spielerKarten = spieler[i].getKarten();
    System.out.println("Spieler "+ i + " " + spieler[i].getSpielerame() +" hat diese Karten: " + spielerKarten[0]+" "+spielerKarten[1] );

    // bestimme wert von Händen aller Spieler
    Handwert = Handwert(i); // bekomme Handwert vom Algorithmus
    System.out.println("Handwert(int): " + Handwert + "?"); //bestätige Handwert
    Handwert = askInt();
    System.out.println("Spieler "+ i + " " + spieler[i].getSpielerame() +" hat Handwert: " + Handwert );
    // erstelle gewinner Ranking
    ranking[i][0] = i; // i = SpielerID
    ranking[i][1] = Handwert;
   } else {
    ranking[i][0] = i; // i = SpielerID
    ranking[i][1] = 0; // Handwert von 0, weil Raus
   }

  }

  // sortiere ranking
  boolean getauscht; // um bubble sort zu beenden, wenn true
  for(int i = 0; i < ranking.length - 1; i++ ) // bubble sort
  {
   getauscht = false;
   for (int j = 0; j < ranking.length - i - 1; j++)
   {
    if(ranking[j][1] < ranking[j + 1][1]) // vergleiche Handwert
    {
     //tausche ranking[j] mit ranking[j+1]
     int tempHandwert = ranking[j][1];
     int tempID = ranking[j][0];
     ranking[j][1] = ranking[j+1][1]; // tausche Handwert platz
     ranking[j][0] = ranking[j+1][0]; // tausche ID platz

     ranking[j+1][1] = tempHandwert;
     ranking[j+1][0] = tempID;

     getauscht = true;
    }
   }
   // sofern keine plätze in der innneren Schleife getauscht wurden, break
   if(getauscht == false)
   {
    break;
   }
  }

  //stelle ranking dar
  System.out.println("ranking: ");
  for (int i = 0; i < Spieleranzahl; i++)
  {
   int platz = i+1;
   System.out.println("Platz: " + platz + ". Spieler " + ranking[i][0] + " " + spieler[ranking[i][0]].getSpielerame() + " Handwert " + ranking[i][1]);
  }
  // beachte allInn:
  //von 1. bis 4. wird übriger Pot vergeben
  for (int i = 0; i < Spieleranzahl; i++)
  {
   //übergebe Pot an Platz i
   int platzI = ranking[i][0];

   //für Platz i zu gewinnender pot wegen allIn
   int gewinn;
   if(spieler[platzI].getSpielerPot() > 0) {
    if(spieler[platzI].getSpielerPot() < pot)
    {
    gewinn = pot - (pot - spieler[platzI].getSpielerPot()); //sofern platz i allIn ist
    } else {
      gewinn = pot;
    }
   } else {gewinn = pot;} // wenn nicht allIn

   spieler[platzI].setChips(spieler[platzI].getChips() + gewinn);
   System.out.println("Spieler "+ platzI + " " + spieler[platzI].getSpielerame() +" gewinnt: " + gewinn);
   //aktuallisiere pot
   pot = pot - gewinn;
   //setze SpielerPot zurück
   spieler[platzI].setSpielerPot(0);
  }
 }
}

/*
fürt ein ganzes Pokerspiel durch:
erstellt alle Spieler
mehrere Runden, bis zum Ende (wird beendet oder nur 1 Spieler übrig)
 */
public void Start() {
 // In Progress
 System.out.println("Start Game");
 //Startgeld festlegen
 System.out.println("Startgeld(int): ");
 Startgeld = askInt();
 //Spieleranzahl = 4;
  System.out.println("Spieleranzahl(int) maximal 4: ");
 Spieleranzahl = askInt();
 System.out.println("Rundenlimit(int): ");
 Rundenlimit = askInt();
 // Kartendeck erstellen
 karten.addAll(Arrays.asList(ALLE_KARTEN));
 //Spieler festlegen (max. 4)    
 for (int i = 0; i < Spieleranzahl; i++)// Schleife in der Spieler initialisiert werden
 {
  //Get Name
  System.out.println("Name(String): ");
  Name = askString();

  spieler[i] = new Spieler(Name, Startgeld, i);
 }

 int Startspieler = 0;
 int RausCounter = 0; // zählt anzahl an Spielern welche Raus sind
 int Rundencounter = 0; // zählt anzahl Runden
 Stop = false; // um ein Spiel voreilig zu Stoppen

 // wiederhole Runden, bis gestoppt wird oder nur ein Spieler übrig ist
 while (RausCounter < (Spieleranzahl - 1) && Stop == false && Rundencounter < Rundenlimit)
 {
  //Spiele Runde
  Rundencounter++;
  System.out.println("Starte Runde: " + Rundencounter);
  Runde(Startspieler);

  // bestimme verlierer (Spieler mit 0 chips)
  for (int i = 0; i < Spieleranzahl; i++)
  {
   System.out.println("Spieler "+ i + " " + spieler[i].getSpielerame() +" chips: " +  spieler[i].getChips());
   if (spieler[i].getChips() <= 0) {
    // verlierer sind Raus
    spieler[i].setRaus(true);
    RausCounter++;
   }
  }
  System.out.println("Raus: " + RausCounter);

  // verschiebe Startspieler
  if (Startspieler < Spieleranzahl - 1)
  {
   Startspieler++;
  } else {
   Startspieler = 0;
  }

  int FoldCounter = 0;
  while (spieler[Startspieler].getRaus() == true) // wenn Spieler Raus überspringen
  {
   if (Startspieler < Spieleranzahl - 1) {
    Startspieler++;
   } else {
    Startspieler = 0;
   }

   FoldCounter++;
   if (FoldCounter > Spieleranzahl)
   {
    System.out.println("Alle Raus");
    break;
   }

  }

  // wenn nur noch 1 Spieler übrig ist, hat er gewonnen.
  if(RausCounter > Spieleranzahl-2 )
  {
   for (int i = 0; i < Spieleranzahl; i++)
   {
    if(spieler[i].getRaus()==false)
    {
     // verkünde letzten Spieler
     System.out.println("letzter Spieler: " + i + " " + spieler[i].getSpielerame()+ " gewinnt mit: " + spieler[i].getChips());
    }
   }
  }

  // loop durch alle Spieler
  for (int i = 0 ; i < Spieleranzahl; i++)
  {
   //setze einige SpielerVariablen zurück
   spieler[i].setSpielerEinsatz(0);
   spieler[i].setAllIn(false);
   spieler[i].setFold(false);
   spieler[i].setSpielerPot(0);
   spieler[i].setBlind(0);
  }

  // get Stopp zeichen
  System.out.println("Stop(bool) true:stop ");
  Stop = askBoolean();

  // Kartendeck neu mischen
  karten.removeAll(Arrays.asList(ALLE_KARTEN));
  karten.addAll(Arrays.asList(ALLE_KARTEN));
  // In Progress
 }
}

}
