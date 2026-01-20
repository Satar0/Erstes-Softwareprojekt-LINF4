import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final ArrayList<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void drawFrom(Deck deck, int n) {
        for (int i = 0; i < n; i++) {
            Card c = deck.draw();
            if (c != null) hand.add(c);
        }
    }

    public void drawOne(Deck deck) {
        drawFrom(deck, 1);
    }

    public List<Card> getHand() { return hand; }

    public void removeCard(Card c) {
        hand.remove(c);
    }

    public List<Card> getPlayableCards(Card top) {
        ArrayList<Card> playable = new ArrayList<>();
        for (Card c : hand) {
            if (c.isPlayableOn(top)) playable.add(c);
        }
        return playable;
    }

    public int handSize() { return hand.size(); }

    @Override
    public String toString() {
        return name + " (" + hand.size() + " Karten)";
    }
}