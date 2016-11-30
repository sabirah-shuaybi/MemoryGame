# MemoryGame

This program creates a basic memory game which the player can interact with. To accomplish this task, the program 
contains three classes: __Concentration__, __CardCollection__ and __Card__.

The __Concentration__ class is responsible for positioning the Cheat and New Game buttons
and creating a collection of cards when the program starts. The class handles mouse events
from the user who is interacting with the game. It keeps track of the state of the game,
how many cards have been clicked on (0,1,2) and proceeds accordingly.

The __CardCollection__ class creates a collection of Cards. In addition to creating an array of Cards, 
the class also layouts out the cards on the canvas in a neat 6X6 grid with even spacing in between.
It creates and fills an array of 36 characters (18 unique ones) and assigns each unique symbol to a 
pair of cards. Since it is a 'Card Collection' the class handles behaviors such as shuffling the 
cards, revealing all the cards, removing all the cards (for a new game), removing a single card 
and finding which card from the collection was clicked.

The __Card__ class is in charge of defining the blueprint for a single card. This blueprint
consists of creating a card (FilledRect), centering the symbol and initially hiding it when
program begins to simulate a facedown card. The class also defines methods for the behaviors
that a single card can perform, such as faceup, facedown, return symbol, and contains.

__@author Sabirah Shuaybi__
 
__@version 11/15/16__

