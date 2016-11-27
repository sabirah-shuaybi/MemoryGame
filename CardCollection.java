import java.awt.*;
import objectdraw.*;
/**
 * The CardCollection class.
 *
 * @author Sabirah Shuaybi
 * @version 11/15/16
 */

public class CardCollection
{
    //There are a total of 36 cards in a CardCollection object
    private static final int NUM_CARDS = 36;

    //Size of gap between each card vertically and horizontally is 10 pixels
    private static final int GAP_SIZE = 10;

    //Declare an array for cards
    private Card [] cardArray;

    /* CardCollection constructor fills cardArray with 36 cards (while also neatly
    positioning them on canvas) and assigns a new symbol to each pair */
    public CardCollection(DrawingCanvas canvas) {

        //Construct the cardArray
        cardArray = new Card[NUM_CARDS];

        //Construct and initialize an array of symbols
        char[] symbolArray = createSymbolArray();

        //Shuffle symbol array to simulate shuffling of cards
        shuffle(symbolArray);

        //Start constructing each row of cards 20 pixels from left
        int left = 20;

        //Maintain a spacing of 10 pixels between columns of cards
        int top = 10;

        //Counter keeps track of how many cards have been created at any given point in loop
        int counter = 0;

        //Fill all elements of cardArray with a card at corresponding position (left, top)
        for (int i=0; i<NUM_CARDS; i++) {

            //Retrieve corresponding indexed element of symbol array
            char symbol = symbolArray[i];

            //Initialize given index of cardArray with a card construction
            cardArray[i] = new Card(left, top, symbol, canvas);

            //Increment counter after each construction of a card
            counter++;

            //Shift left coordinate after creation of a card in each row
            //To achieve a grid-like layout and even spacing between cards
            left += (Card.CARD_SIZE + GAP_SIZE);

            //After constructing every 6 cards, start constructing again from left
            if (counter%6 == 0) {

                //When a row is complete, reset left coordinate back to original value of 20
                left = 20;

                //Shift top coordinate down after a row of cards has been created
                top += (Card.CARD_SIZE + GAP_SIZE);
            }
        }

    }

    /* Constructs and fills a char array of 36 symbols (18 unique symbols) and returns this array */
    private char[] createSymbolArray () {

        //Initialize symbol with uicode for greek letter "a"
        char symbol = '\u03B1';

        char[] symbolArray =  new char[NUM_CARDS]; //number of Cards also equals number of symbols

        for(int i=0; i<NUM_CARDS; i++) {

            symbolArray[i] = symbol;

            //Ensure symbol increments to next letter ONLY if it has been assigned to 2 spots in array
            if (i%2 == 1)
                symbol++;
        }

        return symbolArray;

    }

    /* Swaps two elements in the symbol array and repeats 100 times to simulate "shuffling" */
    private void shuffle(char[] array) {

        //Construct a RandomIntGenerator ranging from 0-35 (index values of symbol array)
        RandomIntGenerator randomInt = new RandomIntGenerator(0, array.length-1);

        //Repeat swapping algorithm 100 times to ensure proper shuffling
        for (int i=0; i<100; i++) {

            //Create 2 random numbers that will serve as two indexes
            int randomIndex = randomInt.nextValue();
            int randomIndex2 = randomInt.nextValue();

            //Swap the symbols at the two randomly selected indexes using a temp variable
            //Significance: using a temp var prevents a value from being lost during an assignment
            char tempSymbol = array[randomIndex];
            array[randomIndex] = array[randomIndex2];
            array[randomIndex2] = tempSymbol;
        }

    }

    /* Reveals the symbol of each card in the collection */
    public void revealAllSymbols() {

        //Loop through entire collection of cards and have them show their symbol
        for (int i=0; i<NUM_CARDS; i++) {

            //Null check to avoid error if a card has been removed from array
            if (cardArray[i] != null) {
                cardArray[i].showSymbol();
            }
        }

    }

    /* Physically removes card (passed as a parameter) from the canvas AND
    removes card from the collection itself (from the array) */
    public void removeCard(Card card) {

        card.removeFromCanvas();

        //Search through collection to determine which card in array matches the card passed in
        for(int i=0; i<NUM_CARDS; i++) {

            //If there is a match, then...
            if (cardArray[i] == card) {
                //Set card at given index to null,
                //Thereby eliminating the stored FilledRect(card) at that spot
                cardArray[i] = null;
            }
        }

    }

    /* Physically removes ALL cards of collection from canvas AND from array.
       This behavior is needed for the New Game feature */
    public void removeAllCards() {

        //Loop through entire collection/array of cards
        for(int i=0; i<NUM_CARDS; i++) {

            //If a card at given index exists in the first place, ...
            if (cardArray[i] != null) {
                //Then remove it from canvas
                cardArray[i].removeFromCanvas();
                //And remove it from array (by setting its reference to null)
                cardArray[i] = null;
            }
        }

    }

    /* Returns either the clicked card or null if no card is clicked */
    public Card getCardClicked(Location point) {

        //Search through card array
        for(int i=0; i<NUM_CARDS; i++) {

            //If a physical card is found to be clicked, return that card
            if(cardArray[i] != null && cardArray[i].contains(point)) {

                //Exit loop as soon as clicked card has been found
                return cardArray[i];
            }
        }

        //No card clicked
        return null;
    }
}
