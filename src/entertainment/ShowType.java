package entertainment;

public final class ShowType {
    private String name;
    private int nrViews;

    public ShowType(final String name) {
        this.name = name;
        this.nrViews = 0;
    }

    /**
     * Creste numarul de ori de care un show de tipul ShowType a fost vizionat
     */
    public void increaseNrViews(final int num) {
        this.nrViews += num;
    }

    public String getName() {
        return name;
    }

    public int getNumOfViews() {
        return nrViews;
    }
}
