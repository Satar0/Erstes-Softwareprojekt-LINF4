/*
Software Projekt 
Poker
Spieler Klasse Prototyp
Timur Druba
06.03.2026
 */

/*
Klasse mit funktionen fuer jeden Spieler im Poker Spiel
 */
public class Spieler
{
// konstruktor Variablen
private String Spielername;
 private int chips; // speichert aktuelle Chips im besitz vom Spieler, zaehlt nicht den Einsatz
 private final int SpielerID; // einzigartige nummer für den Spieler

// veraenderbare start Variablen
private int SpielerEinsatz = 0; // aktueller einsatz des Spielers in der Runde, 0 wenn Spieler gefoldet hat oder noch nicht am zug war.
 private int Blind; // 0 = kein Blind // 1 = small Blind // 2 = big Blind
 private boolean fold; // true wenn spieler gefoldet hat
 private boolean allIn; // true wenn spieler allIn ist
 private boolean Raus; // true wenn Spieler keine weiteren Runden mehr Spielen kann
 private String[] karten = new String[2];// irgendwas mit 2 Karten. (als png String)
 private String[] alleKarten = new String[7];// irgendwas mit 2 Karten. (als png String)
 private int spielerPot = 0; // potgröße, als der Spieler allIn ging

 public Spieler(String Spielername, int startChips, int SpielerID) // Spieler name wird vom Spieler festgelegt, und chips von der Start chipzahl
 {
  this.Spielername = Spielername;
  this.chips = startChips;
  this.SpielerID = SpielerID;
 }

// Getter
public String getSpielerame() { return Spielername; }

 public int getChips() { return chips; }

 public int getSpielerEinsatz() { return SpielerEinsatz; }

 public int getSpielerID() { return SpielerID; }

 public int getBlind() { return Blind ; }

 public boolean getFold() { return fold ; }
 public boolean getAllIn() { return allIn ; }
 public boolean getRaus() { return Raus; }

 public String[] getKarten() { return karten; }
 public String[] getAlleKarten() { return alleKarten; }

 public int getSpielerPot() { return spielerPot; }

// Setter
public void setSpielername(String Spielername) { this.Spielername = Spielername; }

 public void setSpielerEinsatz(int einsatz) { this.SpielerEinsatz = einsatz;}

 public void setChips(int chips) { this.chips = chips; }

 public void setBlind(int Blind ) { this.Blind = Blind; }

 public void setFold(boolean fold) { this.fold = fold; }
 public void setAllIn(boolean allIn ) { this.allIn = allIn; }
 public void setRaus(boolean Raus) { this.Raus = Raus; }

 public void setKarten(String[] karten) {this.karten = karten ;}
 public void setAlleKarten(String[] alleKarten) {this.alleKarten = alleKarten ;}

 public void setSpielerPot(int spielerPot) { this.spielerPot = spielerPot; }


// Funktionen

/*
raise:
erhöht den einstaz, um die bestimmte menge, und subtrahiert den Einsatz von den chips.
 */
public int raise(int raise, int einsatz, int pot) //einsatz ist die aktuelle menge, um die gespielt wird //raise ist die menge, um die der einsatz erhöht wird (vom aktuellen Spieler)
{
 if(chips > einsatz + raise) // es wird gescheckt, ob der Spieler genug Chips hat.
 {
  einsatz = einsatz + raise; // einsatz wir erhoeht
  this.chips = chips - (einsatz - SpielerEinsatz); // es wird von den Chips das entfernt, was der Spieler noch einzusetzen hat. Einen teil des einsatzes hat er ja in vorherigen Runden gesetzt.
  this.SpielerEinsatz = einsatz; // Spieler-einsatz wird auf den aktuellen Einsatz aktualisiert
 } else // Der Spieler hat nicht genug chips, heißt ALL IN
 {
  allIn(pot); //Spieler funktion
 }
 return einsatz; // gibt erhoehten Einsatz zurück
}

/*
check:
subtrahiert aktuellen einsatz von den chips des Spielers. Kann 0 sein
 */
public void check(int einsatz, int pot) //einsatz ist die aktuelle menge, um die gespielt wird
{
 if(chips > einsatz) // es wird gescheckt, ob der Spieler genug Chips hat.
 {
  this.chips = chips - (einsatz - SpielerEinsatz); // es wird von den Chips das entfernt, was der Spieler noch einzusetzen hat. Einen teil des einsatzes hat er ja in vorherigen Runden gesetzt.
  this.SpielerEinsatz = einsatz; // Spieler-einsatz wird auf den aktuellen Einsatz aktualisiert
 } else // Der Spieler hat nicht genug chips, heißt ALL IN
 {
  allIn(pot); //Spieler funktion
 }
}

/*
fold:
gehe aus Runde. Heißt kein checken, raisen oder Gewinnen.
Karten werden nicht gezeigt.
SpielerEinsatz wird 0 gesetzt, denn er ist für die Runde raus.
*/
public void fold()
{
 SpielerEinsatz = 0;
 fold = true;
}

/*
allIn:
Spieler Einsatz wird um die Restliche anzahl an chips vom Spieler erhöht.
 */
public int allIn(int pot)
{
 spielerPot = pot; // speichere aktuelle pot größe
 SpielerEinsatz = SpielerEinsatz + chips; //Spieler einsatz wird erhöht
 chips = 0;// Spieler hat keine chips mehr
 allIn = true;
 return SpielerEinsatz; // gib SpielerEinsatz zurück. Wahrscheinlich kleiner oder größer als der allgemine Einsatz
}
}