package seedu.addressbook.data.member;

public class MemberTier {
    public static String tier;

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


    public MemberTier updateTier(Points points){
        int value = points.getPoints();
        if(value > 100) {
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
