package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Points;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * Edit the amount of points to redeem from the customer of the draft order.
 * The points to be redeemed will be keyed in and retrieved.
 */
public class DraftOrderEditPointsCommand extends Command {

    public static final String COMMAND_WORD = "draftpoints";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Add the amount of member points to be redeemed.\n\t"
            + "Parameters: POINTS\n\t"
            + "Example: " + COMMAND_WORD + " 50";

    public static final String MESSAGE_SUCCESS = "Points to be redeemed has been assigned into the draft";

    public static final String MESSAGE_EMPTY_CUSTOMER_FIELD = "Member needs to be added first!";

    public static final String MESSAGE_EMPTY_DISH_FIELD = "At least one dish needs to be added first!";

    public static final String MESSAGE_NO_REDEEMABLE_POINTS = "Member does not have any points to redeem!";

    public static final String MESSAGE_NEGATIVE_POINTS = "Points to be redeemed must not be a negative value!";

    private final Points toRedeem;

    public DraftOrderEditPointsCommand(int points) {
        this.toRedeem = new Points(points);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyOrder draftOrder = rms.getDraftOrder();
            int points = toRedeem.getCurrentPoints();
            if (!draftOrder.hasCustomerField()) {
                throw new IllegalValueException(MESSAGE_EMPTY_CUSTOMER_FIELD);
            } else if (!draftOrder.hasDishItems()) {
                throw new IllegalValueException(MESSAGE_EMPTY_DISH_FIELD);
            } else if (!draftOrder.hasPoints()) {
                throw new IllegalValueException(MESSAGE_NO_REDEEMABLE_POINTS);
            } else if (points < 0) {
                throw new IllegalValueException(MESSAGE_NEGATIVE_POINTS);
            } else {
                final int maxPointsRedeemable = draftOrder.getMaxPointsRedeemable();
                if (points > maxPointsRedeemable) {
                    points = maxPointsRedeemable;
                }
                rms.editDraftOrderPoints(points);
                String message = MESSAGE_SUCCESS + "\n" + getDraftOrderAsString();
                return new CommandResult(message);
            }
        } catch (IllegalValueException e) {
            String message = e.getMessage() + "\n" + getDraftOrderAsString();
            return new CommandResult(message);
        }
    }

}
