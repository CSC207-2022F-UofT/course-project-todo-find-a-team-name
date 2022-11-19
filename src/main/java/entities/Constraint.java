package entities;

public abstract class Constraint {
    /**
     * A blacklist constraint bans the input instructor,
     * if the boolean flag is true, then blacklist constraint. else whitelist constraint
     **/
    private boolean isBlackList;

    /**
     * default constructor.
     */

    public Constraint() {
    }

    /**
     * Create a Entity.Constraint Entity with the given constraint and blacklist/whitelist
     *
     * @param isBlackList a boolean indicating whether the constraint is blacklist or whitelist.
     */
    public Constraint(boolean isBlackList) {
        this.isBlackList = isBlackList;
    }

    /**
     * return whether the constraint is blacklist or not.
     *
     * @return a boolean value indicating whether the constraint is blacklist or whitelist.
     */
    public boolean isBlackList() {
        return isBlackList;
    }

    /**
     * filter and modify the sections instance variable of a Course Object based on the
     * type of constraint.
     *
     * @param course the course to be modified with filtered sections.
     */
    public abstract void filter(CalendarCourse course);


    /**
     * @return a String representation of the Constraint with listType.
     */
    @Override
    public String toString() {
        String listType = "BlackList";
        if (!this.isBlackList()) {
            listType = "WhiteList";
        }
        return listType + " Constraint";
    }
}






