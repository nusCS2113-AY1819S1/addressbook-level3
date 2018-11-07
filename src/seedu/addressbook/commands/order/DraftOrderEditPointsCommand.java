package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Points;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * Edit the amount of points to redeem from the customer of the draft order.
 * The points to be redeemed will be keyed in and retrieved.
 */
public class DraftOrderEditPointsCommand extends Command {

    public static final String COMMAND_WORD = "draftpoints";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Add the amount of member points to be redeemed."
            + "Parameters: POINTS\n\t"
            + "Example: " + COMMAND_WORD + " 50";

    public static final String MESSAGE_SUCCESS = "Points to be redeemed has been assigned into the draft";

    private final Points toRedeem;

    public DraftOrderEditPointsCommand(int points) {
        this.toRedeem = new Points(points);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyOrder draftOrder = rms.getDraftOrder();
            final ReadOnlyMember customer = draftOrder.getCustomer();
            int points = toRedeem.getPoints();
            if (!draftOrder.hasCustomerField()) {
                throw new IllegalValueException("Member needs to be added first!");
            } else if (customer.getPointsValue() < points) {
                throw new IllegalValueException("Member does not have sufficient points to redeem!");
            } else if (points < 0) {
                throw new IllegalValueException("Points to be redeemed must not be a negative value");
            } else {
                int maxPointsRedeemable = draftOrder.getMaxPointsRedeemable();
                if (points > maxPointsRedeemable) {
                    points = maxPointsRedeemable;
                }
                rms.editDraftOrderPoints(points);
                String message = MESSAGE_SUCCESS + "\n" + getDraftOrderAsString();
                return new CommandResult(message);
            }
        } catch (IllegalValueException e) {
            String message = e.getMessage();
            return new CommandResult(message);
        }
    }

}
