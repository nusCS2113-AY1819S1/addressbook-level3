package seedu.addressbook.data.member;

/**
 * Represents the number of membership tier of a Member in the member list.
 */

public class MemberTier {

    public static final int GOLD_TIER = 400;
    public static final int SILVER_TIER = 200;
    public static final int BRONZE_TIER = 0;

    private String tier;

    public MemberTier() {
        this.tier = "Bronze";
    }

    public MemberTier(String tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return tier;
    }

    /**
     * Checks the points and updates the existing tier.
     * @param points the number of Member points used for tier checking
     */
    public void updateTier(Points points) {
        int value = points.getCurrentPoints();
        if (value > GOLD_TIER) {
            this.tier = "Gold";
        } else if (value > SILVER_TIER) {
            this.tier = "Silver";
        } else if (value >= BRONZE_TIER) {
            this.tier = "Bronze";
        }
    }
}
