/**
 * The <code>Stackotaire</code> class implements the
 * Solitaire game using CardStack objects.
 *
 * Note: Please see attached README.txt for algorithm for
 * 'make next move' command.
 *
 * @author Prangon Ghose
 */
import java.util.Scanner; // for taking user input
import java.util.Collections; // for shuffling CardStack

public class Stackotaire {
    private static CardStack[] tableauStacks = new CardStack[7]; // CardStack array to hold tableau stacks
    private static CardStack[] foundationStacks = new CardStack[4]; // CardStack array to hold foundation stacks
    private static CardStack stockStack = new CardStack('s'); // CardStack to hold stock pile
    private static CardStack wasteStack = new CardStack('w'); // CardStack to hold waste pile
    private static CardStack mainStack = new CardStack(); // CardStack to main deck of cards
    private static int numFaceUp; // int to store number of face up cards

    /**
     * Runs a menu driven program to play Stackotaire using CardStacks. The method takes user input using
     * the Scanner class to play the game.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // declare and instantiate Scanner
        String userInput; // String to hold user input of operations
        String[] inputs = new String[3]; // create an array of 3 strings to hold user inputted values
        int numOfStack0, numOfStack1; // ints to hold the number of each stack in user input

        System.out.println("Welcome to Stackotaire! Let's Play!");
        initializeGame(); // initialize game

        while (true) { // while loop for menu to continue running unless quit
            if (isWinningBoard()) { // check for winning board
                System.out.println("\nCongratulations! You have won!");
                System.out.print("Would you like to play again? (Y/N): "); // ask user to play again
                userInput = scanner.next().toUpperCase();
                if (userInput.charAt(0) == 'Y') { // if yes, reinitialize game
                    System.out.println("\n\nStarting New Game...");
                    System.out.println("\n\nWelcome to Stackotaire! Let's Play!");
                    initializeGame();
                }
                else { // if no, quit
                    System.out.println("Exiting the Game...");
                    System.exit(0);
                }
            }

            // main menu
            System.out.println("\n\nMain Menu: \n" +
                    "1) Draw A Card (draw) \n" +
                    "2) Move from Waste Stack (move W1 --)\n" +
                    "3) Move from Foundation Stack to Tableau Stack (move F- T-) \n" +
                    "4) Move from Tableau Stack to Foundation Stack (move T- F-) \n" +
                    "5) Move N Cards from one Tableau Stack to Another Tableau Stack (move T- T-)\n" +
                    "6) Make Next Move (make) \n" +
                    "7) Restart the Game (restart) \n" +
                    "8) Quit the Game (quit) \n\n");

            renderBoard(); // print board
            while (true) { // while loop and try/catch to handle exceptions to scanner input
                try {
                    System.out.print("\n\nEnter a command: ");
                    userInput = scanner.next().toLowerCase(); // take first word of user input
                    break;
                }
                catch (Exception exception) { // exception to catch exceptions with scanner
                    System.out.println("Invalid user input. Please try again!");
                }
            }

            switch(userInput) { // switch case to handle choices
                case "move":
                    try { // try catch to take 2 more arguments and format them
                        inputs[0] = scanner.next().toUpperCase();
                        inputs[1] = scanner.next().toUpperCase();
                        numOfStack0 = inputs[0].charAt(1) - '0' - 1;
                        numOfStack1 = inputs[1].charAt(1) - '0' - 1;
                    }
                    catch (Exception exception) {
                        System.out.println("Invalid user input. Please try again!");
                        break;
                    }

                    if (inputs[0].equals("W1")) { // if moving from waste stack
                        if (wasteStack.size() > 0) { // check waste stack size, otherwise call an invalid move
                            Card tempCard = wasteStack.peek(); // get top card of waste stacck
                            tempCard.setFaceUp(true);
                            if (inputs[1].charAt(0) == 'F') { // if moving to foundation
                                if (numOfStack1 > 3 || numOfStack1 < 0) { // make sure foundation number is valid
                                    System.out.println("Invalid Stack Number.");
                                    break;
                                }
                                // make sure correct corresponding suit
                                if (numOfStack1 + 1 == tempCard.getCardSuit()) {
                                    // check if foundation stack is empty, and if empty, check if the card to be
                                    // inputted is an ace, if not. if not empty, check if the card to be inputted
                                    // is one more than the current top card.
                                    if (((foundationStacks[numOfStack1].size() != 0) &&
                                            (foundationStacks[numOfStack1].peek().getCardValue() + 1
                                                    == tempCard.getCardValue())) || (tempCard.getCardValue() == 1)) {

                                        wasteStack.pop(); // remove from waste stack
                                        foundationStacks[numOfStack1].push(tempCard); // push to foundation stack
                                        // set new number of face up cards
                                        wasteStack.setNumOfFaceUpCards(wasteStack.getNumOfFaceUpCards() - 1);
                                        foundationStacks[numOfStack1].setNumOfFaceUpCards(
                                                foundationStacks[numOfStack1].getNumOfFaceUpCards() + 1);
                                        System.out.println("Moved Card from Waste Stack to Foundation Stack #" +
                                                (numOfStack1 + 1));
                                    }
                                    else { // if invalid move
                                        System.out.println("Invalid Move. The first Card in the foundation" +
                                                "should be an Ace with the same suit as the foundation.");
                                        break;
                                    }
                                }
                                else { // if invalid move
                                    System.out.println("Invalid Move. You can only move a Card to the foundation " +
                                            "with the same suit as the Card. Please try again.");
                                    break;
                                }
                            }
                            else if (inputs[1].charAt(0) == 'T') { // if moving to tableau stack
                                if (numOfStack1 > 6 || numOfStack1 < 0) { // check valid tableau stack number
                                    System.out.println("Invalid Stack Number.");
                                    break;
                                }
                                // check if alternating colors for tableau stack top card and card to be inputted, and
                                // card to be inputted is less than tableau stack top card
                                if ((tableauStacks[numOfStack1].isEmpty() && tempCard.getCardValue() == 13) ||
                                        ((tableauStacks[numOfStack1].peek().isRed() != tempCard.isRed()) &&
                                                (tableauStacks[numOfStack1].peek().getCardValue() > tempCard.getCardValue()))) {

                                    tableauStacks[numOfStack1].push(tempCard);
                                    wasteStack.pop();
                                    wasteStack.setNumOfFaceUpCards(wasteStack.getNumOfFaceUpCards() - 1);
                                    tableauStacks[numOfStack1].setNumOfFaceUpCards(
                                            tableauStacks[numOfStack1].getNumOfFaceUpCards() + 1);
                                    System.out.println("Moved Card from Waste Stack to Tableau Stack #" +
                                            (numOfStack1 + 1));
                                }
                                else {
                                    System.out.println("Invalid Move. You can only move a Card to a tableau stack" +
                                            "with its top card being an opposite colored suit and of a higher value. " +
                                            "If the Tableau Stack is empty, then the first Card must be a King. ");
                                    break;
                                }
                            }
                            else {
                                System.out.println("Invalid second argument for MOVE command. Please try again!");
                                break;
                            }
                        }
                        else {
                            System.out.println("Invalid Move. The Waste Stack is empty! Please try again.");
                            break;
                        }
                    }
                    // if moving from in between foundation and tableau
                    else if (inputs[0].charAt(0) == 'F' || inputs[0].charAt(0) == 'T') {
                        // if moving from foundation to tableau
                        if (inputs[0].charAt(0) == 'F' && inputs[1].charAt(0) == 'T') {
                            if (numOfStack0 > 3 || numOfStack0 < 0 || numOfStack1 < 0 ||  numOfStack1 > 6) {
                                System.out.println("Invalid Stack Number.");
                                break;
                            }
                            // if foundation stack is not empty
                            if (foundationStacks[numOfStack0].size() > 0) {
                                Card tempCard = foundationStacks[numOfStack0].peek();
                                // check if alternating colors for tableau stack and lower value for card to inputted
                                if ((tableauStacks[numOfStack1].peek().isRed() != tempCard.isRed()) &&
                                        (tableauStacks[numOfStack1].peek().getCardValue() > tempCard.getCardValue())) {

                                    foundationStacks[numOfStack0].pop();
                                    tableauStacks[numOfStack1].push(tempCard);
                                    foundationStacks[numOfStack0].setNumOfFaceUpCards(
                                            foundationStacks[numOfStack0].getNumOfFaceUpCards() - 1);
                                    tableauStacks[numOfStack1].setNumOfFaceUpCards(
                                            tableauStacks[numOfStack1].getNumOfFaceUpCards() + 1);
                                    System.out.println("Moved Card from Foundation Stack #" +
                                            (numOfStack0 + 1) + " to Tableau Stack #" + (numOfStack1 + 1));
                                }
                                else {
                                    System.out.println("Invalid Move. You can only move a Card to a tableau stack" +
                                            "with its top card being an opposite colored suit and of a higher value.");
                                    break;
                                }
                            }
                            else {
                                System.out.println("Invalid Move. The Foundation Stack is empty! Please try again.");
                                break;
                            }
                        }
                        // if moving from tableau to foundation
                        else if (inputs[0].charAt(0) == 'T' && inputs[1].charAt(0) == 'F') {
                            if (numOfStack0 > 6 || numOfStack0 < 0 || numOfStack1 > 3 || numOfStack1 < 0) {
                                System.out.println("Invalid Stack Number.");
                                break;
                            }
                            // check tableau stack size
                            if (tableauStacks[numOfStack0].size() > 0) {
                                Card tempCard = tableauStacks[numOfStack0].peek();
                                // if foundation already has cards
                                if (foundationStacks[numOfStack1].size() > 0) {
                                    // check suit of card and card value
                                    if ((numOfStack1 + 1 == tempCard.getCardSuit()) &&
                                            (foundationStacks[numOfStack1].peek().getCardValue() + 1
                                                    == tempCard.getCardValue())) {

                                        tableauStacks[numOfStack0].pop();
                                        foundationStacks[numOfStack1].push(tempCard);

                                        foundationStacks[numOfStack1].setNumOfFaceUpCards(
                                                foundationStacks[numOfStack1].getNumOfFaceUpCards() + 1);
                                        tableauStacks[numOfStack0].setNumOfFaceUpCards(
                                                tableauStacks[numOfStack0].getNumOfFaceUpCards() - 1);

                                        System.out.println("Moved Card from Tableau Stack #" +
                                                (numOfStack0 + 1) + " to Foundation Stack #" + (numOfStack1 + 1));

                                        // set new top card of tableau stack as face up
                                        if (tableauStacks[numOfStack0].size() > 0 &&
                                                !tableauStacks[numOfStack0].peek().isFaceUp()) {
                                            tableauStacks[numOfStack0].peek().setFaceUp(true);
                                            tableauStacks[numOfStack0].setNumOfFaceUpCards(
                                                    tableauStacks[numOfStack0].getNumOfFaceUpCards() + 1);
                                            numFaceUp++; // increment number of face up cards
                                        }
                                    }
                                    else {
                                        System.out.println("Invalid Move. You can only move a Card to the foundation " +
                                                "with the same suit as the Card. Please try again.");
                                        break;
                                    }
                                }
                                else if (foundationStacks[numOfStack1].size() == 0
                                        && tempCard.getCardValue() == 1) { // if foundation has no cards and
                                    // new card is ace
                                    if (numOfStack1 + 1 == tempCard.getCardSuit()) {
                                        tableauStacks[numOfStack0].pop();
                                        foundationStacks[numOfStack1].push(tempCard);
                                        foundationStacks[numOfStack1].setNumOfFaceUpCards(
                                                foundationStacks[numOfStack1].getNumOfFaceUpCards() + 1);
                                        tableauStacks[numOfStack0].setNumOfFaceUpCards(
                                                tableauStacks[numOfStack0].getNumOfFaceUpCards() - 1);

                                        System.out.println("Moved Card from Tableau Stack #" +
                                                (numOfStack0 + 1) + " to Foundation Stack #" + (numOfStack1 + 1));

                                        // set new top card of tableau stack as face up
                                        if (tableauStacks[numOfStack0].size() > 0 &&
                                                !tableauStacks[numOfStack0].peek().isFaceUp()) {
                                            tableauStacks[numOfStack0].peek().setFaceUp(true);
                                            tableauStacks[numOfStack0].setNumOfFaceUpCards(
                                                    tableauStacks[numOfStack0].getNumOfFaceUpCards() + 1);
                                            numFaceUp++; // increment number of face up cards
                                        }
                                    }
                                    else {
                                        System.out.println("Invalid Move. You can only move a Card to the foundation " +
                                                "with the same suit as the Card. Please try again.");
                                        break;
                                    }
                                }
                                else {
                                    System.out.println("Invalid Move. The first Card in the foundation " +
                                            "should be an Ace with the same suit as the foundation.");
                                    break;
                                }
                            }
                            else {
                                System.out.println("Invalid Move. The Tableau Stack is empty! Please try again.");
                                break;
                            }
                        }
                        else {
                            System.out.println("Invalid second argument for MOVE command. Please try again!");
                            break;
                        }
                    }
                    else {
                        System.out.println("Invalid first argument for MOVE command. Please try again!");
                        break;
                    }
                    break;
                case "moven": // move n cards from one tableau stack to another
                    int numOfCards; // variable to hold the number of cards to be moved
                    try { // take inputs and format them
                        inputs[0] = scanner.next().toUpperCase();
                        inputs[1] = scanner.next().toUpperCase();
                        inputs[2] = scanner.next();
                        numOfCards = Integer.parseInt(inputs[2]);
                        numOfStack0 = inputs[0].charAt(1) - '0' - 1;
                        numOfStack1 = inputs[1].charAt(1) - '0' - 1;
                    }
                    catch (Exception exception) {
                        System.out.println("Invalid user input. Please try again!");
                        break;
                    }

                    // check if valid tableau stack numbers
                    if (numOfStack0 > 6 || numOfStack0 < 0|| numOfStack1 > 6 || numOfStack1 < 0) {
                        System.out.println("Invalid Stack Number.");
                        break;
                    }
                    else if (numOfStack0 == numOfStack1) { // check if tableau stack numbers are the same
                        System.out.println("Moved " +
                                numOfCards + " Cards from the Tableau Stack back into the same Tableau Stack.");
                        break;
                    }

                    // check if there are enough face up cards in the stack
                    if (tableauStacks[numOfStack0].getNumOfFaceUpCards() >= numOfCards) {
                        // check if tableau stack is empty
                        if (tableauStacks[numOfStack1].isEmpty()) {
                            CardStack tempCardStack = new CardStack(); // create tempCardStack for cards to be moved
                            for (int i = 0; i < numOfCards; i++) { // move the cards that are to be moved to temp
                                tempCardStack.push(tableauStacks[numOfStack0].pop());
                            }
                            CardStack reversedTempCardStack = tempCardStack.reverseStack(); // create reverse of stack
                            // if tableau stack is empty, first card must be a king
                            if (reversedTempCardStack.peek().getCardValue() == 13) {
                                while (!tempCardStack.isEmpty()) {  // push temp stack of cards into tableau stack
                                    tableauStacks[numOfStack1].push(tempCardStack.pop());
                                }
                                tableauStacks[numOfStack1].setNumOfFaceUpCards(
                                        tableauStacks[numOfStack1].getNumOfFaceUpCards() + numOfCards);
                                tableauStacks[numOfStack0].setNumOfFaceUpCards(
                                        tableauStacks[numOfStack0].getNumOfFaceUpCards() - numOfCards);

                                // set new top card of origin tableau stack to be face up
                                if (tableauStacks[numOfStack0].size() > 0 &&
                                        !tableauStacks[numOfStack0].peek().isFaceUp()) {
                                    tableauStacks[numOfStack0].peek().setFaceUp(true);
                                    tableauStacks[numOfStack0].setNumOfFaceUpCards(
                                            tableauStacks[numOfStack0].getNumOfFaceUpCards() + 1);
                                    numFaceUp++;
                                }
                                System.out.println("Moved " +
                                        numOfCards + " Cards from Tableau Stack #" +
                                        (numOfStack0 + 1) + " to Tableau Stack #" + (numOfStack1 + 1));
                                break;
                            }
                            else { // if first card is not king, reverse previous procedure
                                // put cards back as they were
                                while (!tableauStacks[numOfStack0].isEmpty()) {
                                    tempCardStack.push(tableauStacks[numOfStack0].pop());
                                }
                                while (!tempCardStack.isEmpty()) {
                                    tableauStacks[numOfStack0].push(tempCardStack.pop());
                                }
                                System.out.println("Invalid Move. The first card in an empty Tableau Stack needs" +
                                        " to be a King.");
                                break;
                            }
                        }
                        else { // if destination tableau stack is not empty
                            CardStack tempCardStack = new CardStack();
                            for (int i = 0; i < numOfCards; i++) { // move to be moved cards into temp
                                tempCardStack.push(tableauStacks[numOfStack0].pop());
                            }
                            CardStack reversedTempCardStack = tempCardStack.reverseStack(); // create reverse of stack
                            // check for card value and for alternating colors
                            if ((reversedTempCardStack.peek().getCardValue() <
                                    tableauStacks[numOfStack1].peek().getCardValue()) &&
                                    (tempCardStack.peek().isRed() != tableauStacks[numOfStack1].peek().isRed())) {

                                while (!tableauStacks[numOfStack1].isEmpty()) { // empty destination stack into temp
                                    tempCardStack.push(tableauStacks[numOfStack1].pop());
                                }
                                while (!tempCardStack.isEmpty()) { // empty temp into destination
                                    tableauStacks[numOfStack1].push(tempCardStack.pop());
                                }

                                tableauStacks[numOfStack1].setNumOfFaceUpCards(
                                        tableauStacks[numOfStack1].getNumOfFaceUpCards() + numOfCards);
                                tableauStacks[numOfStack0].setNumOfFaceUpCards(
                                        tableauStacks[numOfStack0].getNumOfFaceUpCards() - numOfCards);

                                if (tableauStacks[numOfStack0].size() > 0 &&
                                        !tableauStacks[numOfStack0].peek().isFaceUp()) {
                                    tableauStacks[numOfStack0].peek().setFaceUp(true);
                                    tableauStacks[numOfStack0].setNumOfFaceUpCards(
                                            tableauStacks[numOfStack0].getNumOfFaceUpCards() + 1);
                                    numFaceUp++;
                                }
                                System.out.println("Moved " +
                                        numOfCards + " Card from Tableau Stack #" +
                                        (numOfStack0 + 1) + " to Tableau Stack #" + (numOfStack1 + 1));
                                break;
                            }
                            else {
                                // put cards back as they were
                                while (!tableauStacks[numOfStack0].isEmpty()) {
                                    tempCardStack.push(tableauStacks[numOfStack0].pop());
                                }
                                while (!tempCardStack.isEmpty()) {
                                    tableauStacks[numOfStack0].push(tempCardStack.pop());
                                }
                                System.out.println("Invalid move. Cards in a Tableau Stack need to be in order" +
                                        " from highest to least in terms of their value and need to be of" +
                                        " alternating colors.");
                                break;
                            }
                        }
                    }
                    else {
                        System.out.println("Invalid Move. There are not enough face up cards in the stack. " +
                                "Please try again.");
                    }
                    break;
                case "draw": // draw a card from stock and place in waste
                    if (stockStack.size() > 0) { // if stock stack is not empty
                        Card tempCard = stockStack.pop();
                        tempCard.setFaceUp(true);
                        numFaceUp++; // increment number of face up cards
                        wasteStack.push(tempCard);
                        wasteStack.setNumOfFaceUpCards(wasteStack.getNumOfFaceUpCards() + 1);
                        System.out.println("Drew a card from the Stock Stack and placed face up in Waste Stack.");
                    }
                    else if (wasteStack.size() > 0) { // if stock stack is empty and there are unused cards in waste,
                        // recycle them back into stock stack
                        while (!wasteStack.isEmpty()) {
                            wasteStack.peek().setFaceUp(false);
                            numFaceUp--;
                            stockStack.push(wasteStack.pop());
                        }
                        Card tempCard = stockStack.pop();
                        tempCard.setFaceUp(true);
                        numFaceUp++;
                        wasteStack.push(tempCard);
                        wasteStack.setNumOfFaceUpCards(1);
                        System.out.println("Drew a Card from the Stock Stack and placed face up in Waste Stack.");
                    }
                    else {
                        System.out.println("The Stock Stack is empty! Please try again.");
                        break;
                    }
                    break;
                case "make": // make the next "best" move
                    makeNextMove();
                    break;
                case "restart": // restart game
                    while (true) { // take user input about starting a new game
                        try {
                            System.out.print("Do you want to start a new game? (Y/N): ");
                            inputs[0] = scanner.next().toUpperCase();
                            break;
                        }
                        catch (Exception ex) {
                            System.out.println("Invalid input. Please try again.");
                        }
                    }
                    if (inputs[0].charAt(0) == 'Y') { // reinitialize game if yes to start
                        System.out.println("Starting New Game...");
                        System.out.println("\nWelcome to Stackotaire! Let's Play!");
                        initializeGame();
                    }
                    else { // otherwise, do nothing
                        System.out.println("\nContinuing Current Game...");
                    }
                    break;
                case "quit": // exit game
                    while (true) {
                        try {
                            System.out.print("Do you want to quit? (Y/N): ");
                            inputs[0] = scanner.next().toUpperCase();
                            break;
                        }
                        catch (Exception ex) {
                            System.out.println("Invalid input. Please try again!");
                        }
                    }
                    if (inputs[0].charAt(0) == 'Y') { // if yes, exit game
                        System.out.println("Exiting the Game...");
                        System.exit(0);
                    }
                    else {
                        System.out.println("Continuing Current Game...");
                    }
                    break;
                default: // if userInput is none of the above
                    System.out.println("\nInvalid Command. Please try again!");
                    break;
            } // end switch
        } // end while
    } // end main method

    /**
     * Initializes the game by distributing all cards into the proper piles.
     * First, the method stores all 52 cards in mainStack. Then, it shuffles them using
     * the Collections.shuffle() method. Lastly, it empties all tableau, stock, waste,
     * and foundation stacks and distributes the 52 cards in the deck into each stack.
     */
    private static void initializeGame() {
        mainStack.emptyCardStack(); // empty mainStack
        for (int i = 1; i <= 4; i++) { // adds 52 cards to the deck
            for (int j = 1; j <= 13; j++) {
                Card newCard = new Card(i, j, false); // creates new Card with i as suit, j as value
                mainStack.push(newCard); // pushes card to the Stack
            }
        }
        Collections.shuffle(mainStack); // shuffles the order of the deck

        for (int i = 0; i < tableauStacks.length; i++) { // distribute cards to tableau stacks
            tableauStacks[i] = new CardStack('t'); // instantiate stack
            tableauStacks[i].emptyCardStack(); // empties tableau stacks
            for (int j = 1; j <= i + 1; j++) { // moves cards from the deck to the tableau stack
                tableauStacks[i].push(mainStack.pop());
            }
            tableauStacks[i].peek().setFaceUp(true); // set top card as face up
            tableauStacks[i].setNumOfFaceUpCards(1);
            numFaceUp++; // increment faceUpCard for each card that is turned face up
        }

        stockStack.emptyCardStack(); // empty stockStack
        while (!mainStack.isEmpty()) { // place the rest of the cards in mainStack in stockStack
            stockStack.push(mainStack.pop());
        }

        wasteStack.emptyCardStack(); // empty wasteStack

        for (int i = 0; i < foundationStacks.length; i++) { // empty foundationStacks and set their type
            foundationStacks[i] = new CardStack('f'); // instantiate stack
            foundationStacks[i].emptyCardStack();
        }
    }

    /**
     * Prints the current board by rendering all stacks to produce an image of the game board.
     *
     * <dt><b>Precondition:</b><db>
     *      The <code>initializeGame</code> method has been run once before to initialize the game.
     */
    private static void renderBoard() {
        // print Top Line with Foundation Stacks, Waste Stack and Stock Stack
        for (int i = 0; i < foundationStacks.length; i++) {
            if (foundationStacks[i].isEmpty()) { // if foundationStack is empty, output [F-]
                System.out.print("[F" + (i + 1) + "]");
            }
            else { // otherwise, output the top card of the foundation stack
                foundationStacks[i].printStack(foundationStacks[i].getType());
            }
        }

        // print the waste stack symbol and its top card
        System.out.print("\tW1 ");
        wasteStack.printStack(wasteStack.getType());

        // print the stock stack symbol and its size
        System.out.println("\t[XX] " + stockStack.size());

        // print the contents of each tableau stack in its own line
        for (int i = tableauStacks.length - 1; i >= 0; i--) {
            System.out.print("\nT" + (i + 1));
            tableauStacks[i].printStack(tableauStacks[i].getType());
        }
    }

    /**
     * Returns true if the current board is a winning board, false otherwise.
     *
     * <dt><b>Precondition:</b><db>
     *      The <code>initializeGame</code> method has been run once before to initialize the game.
     *
     * @return
     *      Returns a boolean value of true if the current board is a winning board, returns false otherwise.
     */
    private static boolean isWinningBoard() {
        // throughout the program, the number of face up cards is incremented and decremented accordingly
        // by the end, there should be 52 face up cards to win
        return (numFaceUp == 52); // return boolean if numFaceUp == 52
    }

    /**
     * Makes the next best Card move in the current game.
     *
     * <dt><b>Precondition:</b><db>
     *      The <code>initializeGame</code> method has been run once before to initialize the game.
     *
     * <dt><b>Postcondition:</b><db>
     *      A Card object has been moved from one stack to another according to the algorithm.
     *
     * For explanation of algorithm, please look at README.txt file.
     */
    private static void makeNextMove() {
        boolean moveMade = false; // variable to say if a move has been made

        // first option, move from tableau to foundation
        for (int i = 0; i < foundationStacks.length; i++) { // goes through each foundation stack
            for (int j = 0; j < tableauStacks.length; j++) { // goes through tableau stack
                if (foundationStacks[i].isEmpty() && !tableauStacks[j].isEmpty()) { // if foundation is empty and
                    // tableau isn't
                    if (tableauStacks[j].peek().getCardValue() == 1 &&
                            tableauStacks[j].peek().getCardSuit() == (i + 1)) { // if alternating and descending

                        foundationStacks[i].push(tableauStacks[j].pop());

                        foundationStacks[i].setNumOfFaceUpCards(
                                foundationStacks[i].getNumOfFaceUpCards() + 1);
                        tableauStacks[j].setNumOfFaceUpCards(
                                tableauStacks[j].getNumOfFaceUpCards() - 1);

                        System.out.println("Moved Card from Tableau Stack #" +
                                (i + 1) + " to Foundation Stack #" + (j + 1));

                        // set new top card of tableau stack as face up
                        if (tableauStacks[j].size() > 0 &&
                                !tableauStacks[j].peek().isFaceUp()) {
                            tableauStacks[j].peek().setFaceUp(true);
                            tableauStacks[j].setNumOfFaceUpCards(
                                    tableauStacks[j].getNumOfFaceUpCards() + 1);
                            numFaceUp++; // increment number of face up cards
                        }
                        moveMade = true;
                        break;
                    }
                }
                else if (!tableauStacks[j].isEmpty()){ // if tableau is not empty
                    if (tableauStacks[j].peek().getCardValue() == foundationStacks[i].peek().getCardValue() + 1 &&
                            tableauStacks[j].peek().getCardSuit() == (i + 1)) {
                        foundationStacks[i].push(tableauStacks[j].pop());

                        foundationStacks[i].setNumOfFaceUpCards(
                                foundationStacks[i].getNumOfFaceUpCards() + 1);
                        tableauStacks[j].setNumOfFaceUpCards(
                                tableauStacks[j].getNumOfFaceUpCards() - 1);

                        System.out.println("Moved Card from Tableau Stack #" +
                                (i + 1) + " to Foundation Stack #" + (j + 1));

                        // set new top card of tableau stack as face up
                        if (tableauStacks[j].size() > 0 &&
                                !tableauStacks[j].peek().isFaceUp()) {
                            tableauStacks[j].peek().setFaceUp(true);
                            tableauStacks[j].setNumOfFaceUpCards(
                                    tableauStacks[j].getNumOfFaceUpCards() + 1);
                            numFaceUp++; // increment number of face up cards
                        }
                        moveMade = true;
                        break;
                    }

                }
            }
            if (moveMade) {
                break;
            }
        }

        if (!moveMade && !wasteStack.isEmpty()) { // move from waste to foundation
            int suit = wasteStack.peek().getCardSuit();
            if (wasteStack.peek().getCardValue() == 1) { // if top of waste is ace
                foundationStacks[suit - 1].push(wasteStack.pop());
                System.out.println("Moved Card from Waste Stack to Foundation Stack #" + suit);
                moveMade = true;
            }
            else if (!foundationStacks[suit- 1].isEmpty()) { // if foundation is not empty
                if (wasteStack.peek().getCardValue() == foundationStacks[suit - 1].peek().getCardValue() + 1) {
                    foundationStacks[suit - 1].push(wasteStack.pop());
                    System.out.println("Moved Card from Waste Stack to Foundation Stack #" + suit);
                    moveMade = true;
                }
            }
        }

        if (!moveMade && !wasteStack.isEmpty()) { // move from waste to tableau
            for (int i = 0; i < tableauStacks.length; i++) {
                if (tableauStacks[i].isEmpty() && wasteStack.peek().getCardValue() == 13) {
                    tableauStacks[i].push(wasteStack.pop());
                    tableauStacks[i].setNumOfFaceUpCards(tableauStacks[i].getNumOfFaceUpCards() + 1);
                    System.out.println("Moved Card from Waste Stack to Tableau Stack #" + (i + 1));
                    moveMade = true;
                    break;
                }
                else if (!tableauStacks[i].isEmpty()) {
                    if (wasteStack.peek().getCardValue() < tableauStacks[i].peek().getCardValue() &&
                            wasteStack.peek().isRed() != tableauStacks[i].peek().isRed()) {

                        tableauStacks[i].push(wasteStack.pop());
                        tableauStacks[i].setNumOfFaceUpCards(tableauStacks[i].getNumOfFaceUpCards() + 1);
                        System.out.println("Moved Card from Waste Stack to Tableau Stack #" + (i + 1));
                        moveMade = true;
                        break;
                    }
                }
            }
        }

        if (!moveMade) { // move from tableau to tableau
            for (int i = 0; i < tableauStacks.length - 1; i++) {
                for (int j = i + 1; j < tableauStacks.length; j++) {
                    if (!tableauStacks[i].isEmpty() && !tableauStacks[j].isEmpty() &&
                            tableauStacks[i].peek().getCardValue() < tableauStacks[j].peek().getCardValue() &&
                            tableauStacks[i].peek().isRed() != tableauStacks[j].peek().isRed()) {

                        tableauStacks[j].push(tableauStacks[i].pop());

                        tableauStacks[j].setNumOfFaceUpCards(
                                tableauStacks[j].getNumOfFaceUpCards() + 1);
                        tableauStacks[i].setNumOfFaceUpCards(
                                tableauStacks[i].getNumOfFaceUpCards() - 1);

                        // set new top card of tableau stack as face up
                        if (tableauStacks[i].size() > 0 &&
                                !tableauStacks[i].peek().isFaceUp()) {
                            tableauStacks[i].peek().setFaceUp(true);
                            tableauStacks[i].setNumOfFaceUpCards(
                                    tableauStacks[i].getNumOfFaceUpCards() + 1);
                            numFaceUp++; // increment number of face up cards
                        }

                        System.out.println("Moved a Card from Tableau Stack #" +
                                (i + 1) + " to Tableau Stack #" + (j + 1));

                        moveMade = true;
                        break;
                    }
                }
                if (moveMade) {
                    break;
                }
            }
        }

        if (!moveMade) { // last choice, draw a card
            if (stockStack.size() > 0) { // if stock stack is not empty
                Card tempCard = stockStack.pop();
                tempCard.setFaceUp(true);
                numFaceUp++; // increment number of face up cards
                wasteStack.push(tempCard);
                wasteStack.setNumOfFaceUpCards(wasteStack.getNumOfFaceUpCards() + 1);
                System.out.println("Drew a card from the Stock Stack and placed face up in Waste Stack.");
            }
            else if (wasteStack.size() > 0) { // if stock stack is empty and there are unused cards in waste,
                // recycle them back into stock stack
                while (!wasteStack.isEmpty()) {
                    wasteStack.peek().setFaceUp(false);
                    numFaceUp--;
                    stockStack.push(wasteStack.pop());
                }
                Card tempCard = stockStack.pop();
                tempCard.setFaceUp(true);
                numFaceUp++;
                wasteStack.push(tempCard);
                wasteStack.setNumOfFaceUpCards(1);
                System.out.println("Drew a Card from the Stock Stack and placed face up in Waste Stack.");
            }
            moveMade = true;
        }

        if (!moveMade) { // if no possible moves
            System.out.println("No possible moves at this time. Maybe play a move yourself?");
        }
    } // end makeNextMove() method
} // end class
