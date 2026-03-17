/*
verändert von Timur
verbunden mit Rundenlogik
11.03.26
 */
import java.util.*;

public class Handwerte {


    public static final int Nichts = 0;
    public static final int High_Card = 10000; 
    public static final int One_Pair = 20000; 
    public static final int Two_Pair = 30000; 
    public static final int Tripple = 40000; 
    public static final int Straight = 50000; 
    public static final int Flush = 60000;
    public static final int Full_House = 70000; 
    public static final int Quads = 80000; 
    public static final int Straight_Flush = 90000; 
    public static final int Royal_Flush = 100000;

    public Handwerte(){};

public int handKontrolle(List<Integer> values, List<String> suits) {
    if (values == null || suits == null || values.size() != suits.size() || values.size() < 5) {
        return Nichts;
    }

    // --- Counts für Werte (2..14) ---
    int[] cnt = new int[15]; // index 0..14, wir nutzen 2..14
    for (int v : values) {
        if (v >= 2 && v <= 14) cnt[v]++;
    }

    // --- Flush prüfen: gibt es einen Suit mit >=5 Karten? ---
    Map<String, List<Integer>> suitToVals = new HashMap<>();
    for (int i = 0; i < values.size(); i++) {
        suitToVals.computeIfAbsent(suits.get(i), k -> new ArrayList<>()).add(values.get(i));
    }

    List<Integer> flushVals = null; // Werte (Ranks) der Flush-Farbe (falls vorhanden)
    for (List<Integer> sv : suitToVals.values()) {
        if (sv.size() >= 5) {
            flushVals = sv;
            break;
        }
    }

    // --- Straight prüfen (normal, und innerhalb Flush für Straight Flush) ---
    boolean hasStraight = hasStraight(values);

    boolean hasStraightFlush = false;
    boolean hasRoyalFlush = false;
    if (flushVals != null) {
        int top = straightTop(flushVals); // 0 wenn keine Straight
        if (top > 0) {
            hasStraightFlush = true;
            // Royal: 10-J-Q-K-A => Top = 14 und enthält 10
            if (top == 14 && containsRank(flushVals, 10)) {
                hasRoyalFlush = true;
            }
        }
    }

    // --- Mehrlinge zählen ---
    int pairs = 0;
    boolean hasTrips = false;
    boolean hasQuads = false;

    // extra: mehrere Trips möglich (z.B. 7 Karten)
    int tripsCount = 0;

    for (int r = 2; r <= 14; r++) {
        if (cnt[r] == 4) hasQuads = true;
        if (cnt[r] == 3) { hasTrips = true; tripsCount++; }
        if (cnt[r] == 2) pairs++;
    }

    // Full House: (Trips + Pair) oder (2 Trips => eins als Trips, eins als Pair)
    boolean hasFullHouse = false;
    if (tripsCount >= 2) {
        hasFullHouse = true; // z.B. 777 + 999
    } else if (tripsCount == 1 && pairs >= 1) {
        hasFullHouse = true;
    }

    // --- Ranking nach Poker-Hierarchie ---
    if (hasRoyalFlush) return Royal_Flush;
    if (hasStraightFlush) return Straight_Flush;
    if (hasQuads) return Quads;
    if (hasFullHouse) return Full_House;
    if (flushVals != null) return Flush;
    if (hasStraight) return Straight;
    if (hasTrips) return Tripple;
    if (pairs >= 2) return Two_Pair;
    if (pairs == 1) return One_Pair;

    return High_Card;
}

// ===== Helper =====

// Prüft Straight (A kann low sein: A-2-3-4-5)
private static boolean hasStraight(List<Integer> values) {
    return straightTop(values) > 0;
}

// Gibt den höchsten Rank einer Straight zurück (z.B. A2345 => 5, TJQKA => 14), sonst 0
private static int straightTop(List<Integer> values) {
    boolean[] present = new boolean[15]; // 0..14
    for (int v : values) {
        if (v >= 2 && v <= 14) present[v] = true;
    }
    // Ass low ermöglichen: wenn Ass da ist, zählt es auch als 1
    if (present[14]) present[1] = true;

    int run = 0;
    int bestTop = 0;
    for (int r = 1; r <= 14; r++) {
        if (present[r]) {
            run++;
            if (run >= 5) bestTop = r;
        } else {
            run = 0;
        }
    }
    // Wenn bestTop==1..4 ist das keine echte 5er Straight; bestTop>=5 ist ok
    return bestTop >= 5 ? bestTop : 0;
}

private static boolean containsRank(List<Integer> vals, int rank) {
    for (int v : vals) if (v == rank) return true;
    return false;
}

}