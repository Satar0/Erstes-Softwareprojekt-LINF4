import java.util.Random;

public class SlotsTestRun {
  public static void main(String args[])
    {
    int playerMoney = 0;
    
    int runsmain = 1000;
    
    int runs = runsmain;
    int tests = 1000;
    
    
    int win1;
    int win2;
    int win3;
    
    Random random = new Random();
    
    int[] results = new int[tests];
    int testIndex = 0;
    
    while (tests > 0)    {
      playerMoney = 0;
      runs = runsmain;
      while (runs>0)  {
      playerMoney = playerMoney - 10;
    
        int r = random.nextInt(100); // 0..99
     
          // Range-Verteilung:
          // 0-4   : Seven (5%)
          // 5-24  : Banane (20%)
          // 25-42 : Erdbeere (18%)
          // 43-57 : Kirsche (15%)
          // 58-72 : Orange (15%)
          // 73-84 : Traube (12%)
          // 85-94 : Wassermelone (10%)
           // 95-99 : Zitrone (5%)
    
    if (r < 5) {
            win1 = 0;
          }
            else if (r < 25) {
              win1 = 1;
            }
              else if (r < 43) {
                win1 = 2;
              }
                else if (r < 58) {
                  win1 = 3;
                }
                  else if (r < 73) {
                    win1 = 4;
                  }
                    else if (r < 85) {     
                      win1 = 5;
                    }
                      else if (r < 95) {
                        win1 = 6;
                      }
                      else {
                        win1 = 7;
                      }
      
      r = random.nextInt(100); // 0..99

 if (r < 5) {
            win2 = 0;
          }
            else if (r < 25) {
              win2 = 1;
            }
              else if (r < 43) {
                win2 = 2;
              }
                else if (r < 58) {
                  win2 = 3;
                }
                  else if (r < 73) {
                    win2 = 4;
                  }
                    else if (r < 85) {     
                      win2 = 5;
                    }
                      else if (r < 95) {
                        win2 = 6;
                      }
                      else {
                        win2 = 7;
                      }
                  
      r = random.nextInt(100); // 0..99
          
     if (r < 5) {
            win3 = 0;
          }
            else if (r < 25) {
              win3 = 1;
            }
              else if (r < 43) {
                win3 = 2;
              }
                else if (r < 58) {
                  win3 = 3;
                }
                  else if (r < 73) {
                    win3 = 4;
                  }
                    else if (r < 85) {     
                      win3 = 5;
                    }
                      else if (r < 95) {
                        win3 = 6;
                      }
                      else {
                        win3 = 7;
                      }
                
    
     boolean triple = (win1 == win2 && win2 == win3);
           boolean pair   = !triple && (win1 == win2 || win1 == win3 || win2 == win3);

            int payout = 0;

    // =========================
    // 3 GLEICHE (JACKPOT)
    // =========================
           if (triple) {

             switch (win1) {
              case 0: payout = 800; break; // Seven (selten)
              case 1: payout = 150; break; // Banane (häufig)
              case 2: payout = 180; break; // Erdbeere
              case 3: payout = 220; break; // Kirsche
              case 4: payout = 220; break; // Orange
              case 5: payout = 300; break; // Traube
              case 6: payout = 380; break; // Wassermelone
              case 7: payout = 800; break; // Zitrone (selten)
            }

            }

          // =========================
          // GENAU 2 GLEICHE
          // =========================
            else if (pair) {
        
              int pairSymbol;

              if (win1 == win2) pairSymbol = win1;
               else if (win1 == win3) pairSymbol = win1;
                else pairSymbol = win2; // win2 == win3

              switch (pairSymbol) {
                case 0: payout = 25; break; // Seven
                case 1: payout = 10; break; // Banane
                case 2: payout = 12; break; // Erdbeere
                case 3: payout = 15; break; // Kirsche
                 case 4: payout = 15; break; // Orange
                 case 5: payout = 18; break; // Traube
                case 6: payout = 20; break; // Wassermelone
                 case 7: payout = 25; break; // Zitrone
                      }
                      }

          // =========================
          // AUSZAHLEN
          // =========================
          if (payout > 0) {
             playerMoney += payout;
      }
         
      
      runs = runs - 1;
     }
      
      System.out.println("Money: " + playerMoney);
      
      results[testIndex++] = playerMoney;
      
      tests = tests - 1;
    }
    
    // =========================
    // SORTIEREN (aufsteigend)
    // =========================
      for (int i = 0; i < results.length - 1; i++) {
      for (int j = 0; j < results.length - 1 - i; j++) {
        if (results[j] > results[j + 1]) {
          int temp = results[j];
          results[j] = results[j + 1];
          results[j + 1] = temp;
        }
      }
    }


    // =========================
    // AUSGABE IM TERMINAL
    // =========================
    System.out.println("\n--- SORTIERTE ERGEBNISSE (aufsteigend) ---");
    for (int i = 0; i < results.length; i++) {
      System.out.printf("Rang %2d : %6d%n", i + 1, results[i]);
    }
    
    
    }
  
                      
    
    }

