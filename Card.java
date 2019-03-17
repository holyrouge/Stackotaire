/**
 * The <code>Card</code> class implements a Card
 * object with information about its value and suit.
 *
 * @author Prangon Ghose
 */

public class Card {
    // static variables to hold all possible values and suits
    private static String values[] = {" ","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    private static char suits[]    = {' ', '\u2666', '\u2663','\u2665', '\u2660'};   // {' ', '♦', '♣','♥', '♠'}
    private int cardSuit; // variable to hold index of the suit in values[]
    private int cardValue; // variable to hold index of the suit in suits[]
    private boolean isFaceUp; // boolean variable for the card being face up

    /**
     * Returns an instance of Card with template values.
     *
     * <dt><b>Postcondition:</b><db>
     *     This Card has been initialized with template values.
     */
    public Card() {
        this.cardSuit = 0;
        this.cardValue = 0;
        this.isFaceUp = false;
    }

    /**
     * Returns an instance of Card with given values.
     *
     * @param cardSuit
     *      The index of the suit in suits[]
     * @param cardValue
     *      The index of the value in values[]
     * @param isFaceUp
     *      The boolean value for the card being face up
     *
     * <dt><b>Precondition:</b><db>
     *      <code>cardSuit</code> and <code>cardValue</code> must be greater
     *      than of equal to 0.
     *
     * <dt><b>Postcondition:</b><db>
     *      This Card has been initialized with given values.
     *
     * @exception IllegalArgumentException
     *      Indicates that <code>cardSuit</code> or <code>cardValue</code> is less than 0.
     */
    public Card(int cardSuit, int cardValue, boolean isFaceUp) throws IllegalArgumentException {
        if (cardSuit < 0)
            throw new IllegalArgumentException("Invalid value for cardSuit.");
        this.cardSuit = cardSuit;
        if (cardValue < 0)
            throw new IllegalArgumentException("Invalid value for cardValue.");
        this.cardValue = cardValue;
        this.isFaceUp = isFaceUp;
    }

    /**
     * Sets the index of the suit in suits[].
     *
     * @param cardSuit
     *      The index of the suit in suits[]
     *
     * <dt><b>Precondition:</b><db>
     *     This Card has been instantiated. <code>cardSuit</code> is greater than or equal to 0.
     *
     * @exception IllegalArgumentException
     *      Indicates that <code>cardSuit</code> is less than 0.
     */
    public void setCardSuit(int cardSuit) throws IllegalArgumentException {
        if (cardSuit < 0)
            throw new IllegalArgumentException("Invalid value for cardSuit.");
        this.cardSuit = cardSuit;
    }

    /**
     * Returns the index of the suit in suits[].
     *
     * <dt><b>Precondition:</b><db>
     *      This Card has been instantiated.
     *
     * @return
     *      Returns the int value of <code>cardSuit</code>.
     */
    public int getCardSuit() {
        return cardSuit;
    }

    /**
     * Sets the index of the value in values[].
     *
     * @param cardValue
     *      The index of the value in values[]
     *
     * <dt><b>Precondition:</b><db>
     *     This Card has been instantiated. <code>cardValue</code> is greater than or equal to 0.
     *
     * @exception IllegalArgumentException
     *      Indicates that <code>cardValue</code> is less than 0.
     */
    public void setCardValue(int cardValue) throws IllegalArgumentException {
        if (cardValue < 0)
            throw new IllegalArgumentException("Invalid value for cardValue.");
        this.cardValue = cardValue;
    }

    /**
     * Returns the index of the value in values[].
     *
     * <dt><b>Precondition:</b><db>
     *      This Card has been instantiated.
     *
     * @return
     *      Returns the int value of <code>cardValue</code>.
     */
    public int getCardValue() {
        return cardValue;
    }

    /**
     * Set the boolean value of card being face up.
     *
     * @param faceUp
     *      The boolean value of the card being face up
     *
     * <dt><b>Precondition:</b><db>
     *      This Card has been instantiated.
     */
    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    /**
     * Returns the boolean value of the card being face up.
     *
     * <dt><b>Precondition:</b><db>
     *      This Card has been instantiated.
     *
     * @return
     *      Returns the boolean value of <code>isFaceUp</code>.
     */
    public boolean isFaceUp() {
        return isFaceUp;
    }

    /**
     * Returns the boolean value of the card being red.
     *
     * <dt><b>Precondition:</b><db>
     *      This Card has been instantiated.
     *
     * @return
     *      Returns a true if the <code>cardSuit</code> is odd, false otherwise.
     */
    public boolean isRed() {
        if (cardSuit % 2 > 0)
            return true;
        return false;
    }

    /**
     * Returns the attributes of the Card for printing.
     *
     * <dt><b>Precondition:</b><db>
     *      This Card has been instantiated.
     *
     * @return
     *      Returns a String containing the attributes of this Card if
     *      <code>isFaceUp</code> is true, otherwise returns "[XX]".
     */
    public String toString() {
        if (isFaceUp)
            return "[" + values[cardValue] + suits[cardSuit] + "]";
        return "[XX]";
    } // end toString method
} // end class
