import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private final ArrayList<Card> cards = new ArrayList<>();
    private final Random rand = new Random();

    public Deck() {
        buildDeck();
        shuffle();
    }

    private void buildDeck() {
        cards.clear();
        Card.Color[] colors = {Card.Color.RED, Card.Color.YELLOW, Card.Color.GREEN, Card.Color.BLUE};

        // Für jede Farbe: eine 0, zwei von 1-9 und zwei von den Aktionskarten
        for (Card.Color c : colors) {
            cards.add(new Card(c, Card.Value.ZERO));
            for (Card.Value v : new Card.Value[] {
                    Card.Value.ONE, Card.Value.TWO, Card.Value.THREE, Card.Value.FOUR,
                    Card.Value.FIVE, Card.Value.SIX, Card.Value.SEVEN, Card.Value.EIGHT, Card.Value.NINE}) {
                cards.add(new Card(c, v));
                cards.add(new Card(c, v)); // zweimal
            }
            // Aktionskarten: zwei pro Farbe
            cards.add(new Card(c, Card.Value.SKIP));
            cards.add(new Card(c, Card.Value.SKIP));
            cards.add(new Card(c, Card.Value.REVERSE));
            cards.add(new Card(c, Card.Value.REVERSE));
            cards.add(new Card(c, Card.Value.DRAW_TWO));
            cards.add(new Card(c, Card.Value.DRAW_TWO));
        }

        // Wild-Karten
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(Card.Color.WILD, Card.Value.WILD));
            cards.add(new Card(Card.Color.WILD, Card.Value.WILD_DRAW_FOUR));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards, rand);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card draw() {
        if (isEmpty()) return null;
        return cards.remove(cards.size() - 1);
    }

    public void addToBottom(Card c) {
        cards.add(0, c);
    }

    public int size() {
        return cards.size();
    }

    // Falls leer: man könnte hier die Abwurfstapelkarten zurück ins Deck mischen (nicht hier implementiert)
}