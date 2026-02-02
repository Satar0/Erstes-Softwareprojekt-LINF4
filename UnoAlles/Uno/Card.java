public class Card {
    public enum Color { RED, YELLOW, GREEN, BLUE, WILD }
    public enum Value {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
        SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR
    }

    private final Color color;
    private final Value value;

    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() { return color; }
    public Value getValue() { return value; }

    // Prüft, ob diese Karte auf 'top' gelegt werden kann
    public boolean isPlayableOn(Card top) {
        if (this.value == Value.WILD || this.value == Value.WILD_DRAW_FOUR) return true;
        return this.color == top.color || this.value == top.value;
    }

    @Override
    public String toString() {
        if (value == Value.WILD || value == Value.WILD_DRAW_FOUR) {
            return value.name();
        }
        return color.name() + " " + value.name();
    }
}