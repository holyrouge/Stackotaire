/**
 * The <code>CardStack</code> class implements a CardStack
 * object which contains a number of Card objects and
 * manipulates them using the Stack object from the Java API.
 *
 * @author Prangon Ghose
 */
import java.util.Stack; // to extend Stack from Java API

public class CardStack extends Stack<Object> { // extends the Java Stack class
    private char type; // char variable to hold the type of the CardStack
    private int numOfFaceUpCards; // char variable to hold the number of face up cards in a CardStack

    /**
     * Returns an instance of CardStack with template values.
     *
     * <dt><b>Postcondition:</b><db>
     *     This CardStack has been initialized with template values.
     */
    public CardStack() {
        super();
        this.type = ' ';
        this.numOfFaceUpCards = 0;
    }

    /**
     * Returns an instance of CardStack with given values.
     *
     * @param type
     *      The type of the CardStack
     *
     * <dt><b>Precondition:</b><db>
     *      <code>type</code> is 's', 'w', 'f' or 't'.
     *
     * <dt><b>Postcondition:</b><db>
     *     This CardStack has been initialized with template values.
     *
     * @exception IllegalArgumentException
     *      Indicates that the <code>type</code> input is not 's', 'w', 'f' or 't'.
     */
    public CardStack(char type) {
        super();
        // if the type input is not 's', 'w', 'f' or 't', throw an exception
        if ((type != 's') && (type != 'w') && (type != 'f') && (type != 't'))
            throw new IllegalArgumentException("Invalid value for CardStack type.");
        this.type = type;
        this.numOfFaceUpCards = 0;
    }

    /**
     * Sets the type of the CardStack.
     *
     * @param type
     *      The type of the CardStack
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * @exception IllegalArgumentException
     *      Indicates that the <type>type</type> input is not 's', 'w', 'f' or 't'.
     */
    public void setType(char type) {
        // if the type input is not 's', 'w', 'f' or 't', throw an exception
        if ((type != 's') && (type != 'w') && (type != 'f') && (type != 't'))
            throw new IllegalArgumentException("Invalid value for CardStack type.");
        this.type = type;
    }

    /**
     * Returns the type of the CardStack.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * @return
     *      Returns the char value of <code>type</code>.
     */
    public char getType() {
        return type;
    }

    /**
     * Pushes the newCard to the CardStack.
     *
     * @param newCard
     *      <code>newCard</code> to be added to <code>CardStack</code>.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * <dt><b>Postcondition:</b><db>
     *      The <code>newCard</code> is stored at the top of the <code>CardStack</code>.
     *
     */
    public void push(Card newCard) {
        super.push(newCard);
    }

    /**
     * Removes the top Card from the CardStack and returns it.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * <dt><b>Postcondition:</b><db>
     *      The top card from the <code>CardStack</code> has been removed and
     *      there is a new top card.
     *
     * @return
     *      Returns removed <code>Card</code>.
     */
    public Card pop() {
        return (Card)super.pop();
    }

    /**
     * Returns the top card from the stack, without removing it from the stack
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     * @return
     *      Returns the top <code>Card</code> from this <code>CardStack</code>.
     */
    public Card peek() {
        return (Card)super.peek();
    }

    /**
     * Returns the boolean value for if the CardStack is empty.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * @return
     *      Returns the boolean value for if the <code>CardStack</code> is empty.
     */
    public boolean isEmpty() {
        return super.empty();
    }

    /**
     * Returns the size of the CardStack.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * @return
     *      Returns the number of <code>Card</code> objects in this <code>CardStack</code>.
     */
    public int size() {
        return super.size();
    }


    /**
     * Returns the number of face up cards in the CardStack.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * @return
     *      Returns the number of <code>Card</code> objects with <code>isFaceUp</code> equal to true.
     */
    public int getNumOfFaceUpCards() {
        return numOfFaceUpCards;
    }

    /**
     * Sets the number of face up cards in the Stack based on input.
     *
     * @param numOfFaceUpCards
     *      Number of face up cards in the <code>CardStack</code>.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated and <code>numOfFaceUpCards</code> is positive.
     *
     * @exception IllegalArgumentException
     *      Indicates that the <code>numOfFaceUpCards</code> is negative.
     *
     */
    public void setNumOfFaceUpCards(int numOfFaceUpCards) throws IllegalArgumentException {
        if (numOfFaceUpCards < 0)
            throw new IllegalArgumentException("Invalid input for number of face up cards.");
        this.numOfFaceUpCards = numOfFaceUpCards;
    }

    /**
     * Empties the CardStack.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * <dt><b>Postcondition:</b><db>
     *      This CardStack has been emptied and does not have any more Cards.
     */
    public void emptyCardStack() {
        if (size() > 0) {
            while (!this.isEmpty()) {
                this.pop();
            }
        }
    }

    /**
     * Returns the reverse of the current CardStack.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated and has size greater than 0.
     *
     * @return
     *      Returns a reversed version of the this <code>CardStack</code>.
     */
    public CardStack reverseStack() {
        // initialize 2 CardStacks
        CardStack reversedStack1 = new CardStack(); // to return
        CardStack reversedStack2 = new CardStack(); // to reset this CardStack
        Card tempCard; // temp Card
        while (!this.isEmpty()) { // empty this CardStack into both CardStacks
            tempCard = this.pop();
            reversedStack1.push(tempCard);
            reversedStack2.push(tempCard);
        }
        while (!reversedStack2.isEmpty()) { // set this CardStack back
            this.push(reversedStack2.pop());
        }
        return reversedStack1; // return one reversed CardStack
    }

    /**
     * Prints the CardStack based on its type.
     *
     * <dt><b>Precondition:</b><db>
     *      This CardStack has been instantiated.
     *
     * @param type
     *      The type of the CardStack.
     */
    public void printStack(char type) {
        // switch case to print different things based on type
        switch (type) {
            case 's': // if stock type, print the top card face down
                System.out.print("[XX]");
                break;
            case 'w': // if waste type, print the top card face up
                if (size() == 0) {
                    System.out.print("[  ]"); // if waste stack is empty, print [  ]
                }
                else {
                    System.out.print(super.peek().toString());
                }
                break;
            case 'f': // if foundation type, print the top card face up
                System.out.print(super.peek().toString());
                break;
            case 't': // if tableau type, print the whole stack on a line
                CardStack tempCardStack = new CardStack(this.type); // create tempCardStack
                while (!this.isEmpty()) { // remove the cards and move them to tempCardStack
                    tempCardStack.push(this.pop());
                }
                // print the top card and pushes the removed Cards back into this CardStack
                while (!tempCardStack.isEmpty()) {
                    System.out.print(tempCardStack.peek());
                    this.push(tempCardStack.pop());
                }
                break;
            default:
                break;
        }
    } // end of printStack method
} // end class
