package seedu.addressbook.data.member;

/**
 * Represents the number of membership tier of a Member in the member list.
 */

public class MemberTier {
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
     * @param points
     * @return MemberTier object with the updated tier value
     */
    public MemberTier updateTier(Points points) {
        int value = points.getCurrentPoints();
        if (value > 100) {
            this.tier = "Gold";
            return this;
        } else if (value > 50) {
            this.tier = "Silver";
            return this;
        } else {
            this.tier = "Bronze";
            return this;
        }
    }
}
