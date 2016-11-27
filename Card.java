import java.awt.*;
import objectdraw.*;
/**
 * The Card class is in charge of defining the blueprint for a single card. This blueprint
 * consists of creating a card (FilledRect), centering the symbol and initially hiding it when
 * program begins to simulate a facedown card. The class also defines methods for the behaviors
 * that a single card can perform, such as faceup, facedown, return symbol, and contains
 *
 * @author Sabirah Shuaybi
 * @version 11/15/16
 */
public class Card
{

    private FilledRect card;

    public static final int CARD_SIZE = 50;

    private char symbol;

    private Text text;

    /* Card constructor ensures card will be presented facedown at start of program */
    public Card (double left, double top, char symbol, DrawingCanvas canvas) {

        //Construct a filled rectangle that is the card
        card = new FilledRect(left, top, CARD_SIZE, CARD_SIZE, canvas);

        //Set card color to a razzmatazz (a pink/red hue)
        card.setColor(new Color(232, 0, 108));

        //Create and format a text object consisting of symbol
        text = new Text("" + symbol, left, top, canvas);
        text.setFontSize(30);
        boolean bold = true;
        text.setBold(bold);

        //Dynamically center symbol within card
        double textCenterX = card.getX() + (card.getWidth()/2) - (text.getWidth()/2);
        double textCenterY = card.getY() + (card.getWidth()/2) - (text.getHeight()/2);
        text.moveTo(textCenterX, textCenterY);

        //Hide symbol to simulate facedown effect
        text.hide();

        //Save symbol reference to use in getSymbol method
        this.symbol = symbol;

    }

    /* Reveals the symbol on card, essentially "flips" card over */
    public void showSymbol() {

        text.show();
    }

    /* Hides the symbol on card, essentially "flips" card facedown */
    public void hideSymbol() {

        text.hide();
    }

    /* Returns the symbol that has been assigned to card */
    public char getSymbol() {

        return symbol;
    }

    /* Determines if user clicked on card */
    public boolean contains(Location point) {

        //Returns true or false based on contains statement below
        return card.contains(point);
    }

    /* Removes entire card from the window canvas */
    public void removeFromCanvas() {

        card.removeFromCanvas();
        text.removeFromCanvas();
    }
}
