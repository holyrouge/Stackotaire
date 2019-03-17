/**
 * This is an explanation of the algorithm used to make
 * the next best move in Stackotaire.
 *
 * @author Prangon Ghose
 */


 First, the best move to make would be to add to the foundation and thus, push toward the win condition. So, the
 algorithm uses two for loops to go through each foundation and each tableau to find if any cards can be moved to
 a foundation. A card can be moved if the foundation is empty and the card to be added is an ace of the corresponding
 suit. If the foundation is not empty, the best card to add would be next highest card of the corresponding suit. If
 neither of these work, continue to the next best move.

 The next best move would be to move a card from the waste stack to the foundation. First, check if the top of the
 waste stack is an ace. If so, place the ace in its corresponding foundation. If there is no ace and the foundation
 is not empty, check if the top card of the waste stack is one higher than the top card of its suit's corresponding
 foundation. If so, move the top card from the waste stack top the foundation.

 The next best move would be to move the top card of the waste stack to the tableau stacks. If the tableau is empty and
 the waste stack's top card is a king, move the card into the tableau. If the tableau is not empty, then go through
 each tableau and compare the top card with the top card of the waste stack. If they are of alternating colored suits
 and the top card of the waste stack is less in value than the top card of the tableau stack, then move from the waste
 stack to the tableau stack.

 The next best move would be to move cards into the tableau stacks. The algorithm uses two for loops to compare the top
 cards of each tableau stack to each other. If they are of alternating colors and one is less than the other, move
 the card with the lower value to the stack with the card of the higher value.

 The last best move would be to draw a card. This works in the same way that the draw command does in the user-inputted
 version.

 If no possible move can be made, then the algorithm outputs no possible move can be made.