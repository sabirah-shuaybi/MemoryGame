import java.awt.*;
import objectdraw.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * The Concentration class is responsible for positioning the Cheat and New Game buttons
 * and creating a collection of cards when the program starts. The class handles mouse events
 * from the user who is interacting with the game. It keeps track of the state of the game,
 * how many cards have been clicked on (0,1,2) and proceeds accordingly.
 *
 * @author Sabirah Shuaybi
 * @version 11/15/16
 */
public class Concentration extends WindowController implements ActionListener
{
    //To store the JButtons for source evaluation
    private JButton newGame;
    private JButton cheat;

    private CardCollection cardCollection;

    //Cards are null at start of program
    private Card firstCard = null;
    private Card secondCard = null;

    private boolean cheatMode = false;

    /* Lays out the buttons and creates a collection of concentration cards */
    public void begin() {

        //Add panel in grid layout for the two buttons
        JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new GridLayout(1,2));
        //Add panel to top of window
        add(componentPanel, BorderLayout.NORTH);

        //Create cheat and new game buttons
        newGame = new JButton("New Game");
        cheat = new JButton("Cheat");

        //Add action listener to buttons to "listen" for an action aka a mouse click
        //Pass running instance of the Concentration class
        newGame.addActionListener(this);
        cheat.addActionListener(this);

        //Add buttons to panel
        componentPanel.add(newGame);
        componentPanel.add(cheat);

        //Create a collection of cards on the canvas
        cardCollection = new CardCollection(canvas);

    }

    /* Handles mouse clicks according to what mode game is in - cheat or regular */
    public void onMouseClick(Location point) {

        //Request for the card that was clicked on
        Card card = cardCollection.getCardClicked(point);

        //If no card has been clicked, exit the method
        if (card == null)
            return;

        //If game is in cheat mode, process card(s) accordingly
        if (cheatMode)
            processCheatMode(card);

        //Else, just process card(s) regularly
        else
            processRegularMode(card);
    }

    /* Carries out regular processing of cards, where two cards either vanish or turn facedown
    (depending on if they match or not) on THIRD click */
    private void processRegularMode(Card card) {

        //If user clicks on same card twice, ignore second click
        if (card == firstCard)
            return;

        //If no card has been clicked yet and player clicks on a card,
            //assign clicked card to firstCard, reveal symbol, and exit method
        if (firstCard == null) {
            firstCard = card;
            firstCard.showSymbol();
            return;
        }

        //If a second card is clicked, firstCard will not be null so code executionw will begin here
            //assign clicked card to secondCard, reveal symbol, and exit method
        if (secondCard == null) {
            secondCard = card;
            secondCard.showSymbol();
            return;
        }

        //At this point both first and second card are not null (have been clicked)
        //If the symbols of the two cards selected are a match, remove cards (on third click)
        if (firstCard.getSymbol() == secondCard.getSymbol()) {

            //Invoking removeCard() will remove card from canvas as well as from array
            cardCollection.removeCard(firstCard);
            cardCollection.removeCard(secondCard);
        }
        //If symbols are not a match, turn the two card facedown again
        else {
            firstCard.hideSymbol();
            secondCard.hideSymbol();
        }

        //Reset these variables back to null to keep the game going
        firstCard = null;
        secondCard = null;

    }
    /* Processes cards (which are already faceup) so that matched cards, when clicked,
    vanish on second click (rather than the third click like in regular mode).
    Similarly, unmatched cards, when clicked, turn facedown on second click. */
    public void processCheatMode (Card card) {

        //If user clicks on same card twice, ignore second click (by exiting method)
        //OR if user clicks on a facedown card in cheat mode, ignore that click
            //Significance: if user clicks on two unmatched cards and they turn facedown,
                //clicking on a hidden card should not interfere with state of game in cheat mode
        if (card == firstCard || card.isHidden()) {
            return;
        }

        //If no card has been clicked yet and player clicks on a card,
            //assign clicked card to first card and exit method
        if (firstCard == null) {
            firstCard = card;
            return;
        }

        //If we reach this point it means a second card has been clicked
        //No need for a secondCard variable since we want cards to vanish at second click
        if (firstCard.getSymbol() == card.getSymbol()) {
            //Remove the matching pair
            cardCollection.removeCard(firstCard);
            cardCollection.removeCard(card);
        }
        //At this point, the player has clicked on two unequal cards
        //Turn cards facedown again
        else {
            firstCard.hideSymbol();
            card.hideSymbol();
        }

        //Set the first card back to null
        firstCard = null;
    }


    /* Required method by interface, evaluates the source of the event and
    performs the action associated with that button */
    public void actionPerformed (ActionEvent e) {

        //If Cheat button is clicked...
        if (e.getSource() == cheat) {
            //Reveal all symbols to the player
            cardCollection.revealAllSymbols();
            //Game is in cheat mode
            cheatMode = true;
            //Reset state of game to 0 cards clicked by setting firstCard back to null
            firstCard = null;
        }
        //If New Game button is clicked...
        else if(e.getSource() == newGame) {
            //Reset state of game (aka how many card clicks) to 0 card clicks
            //by setting both first and second cards to null again
            firstCard = null;
            secondCard = null;
            //Remove all the cards from current card collection (from canvas and array)
            cardCollection.removeAllCards();
            //Construct a brand new card collection, with new order of symbols
            cardCollection = new CardCollection(canvas);
            //Ensure that game is no longer in cheat mode (if it was before)
            cheatMode = false;
        }

    }
}

