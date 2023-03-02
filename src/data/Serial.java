package data;

import entertainment.Seasonmodified;

import java.util.ArrayList;

/**
 * Informatii despre un serial
 */
public final class Serial extends Show {
    /**
     * Numarul de sezoane
     */
    private final int numberOfSeasons;
    /**
     * Lista de sezoane
     */
    private final ArrayList<Seasonmodified> seasons;
    /**
     * Durata unui serial in minute
     */
    private int duration;
    /**
     * Ratingul mediu al unui serial
     */
    private Double avg;


    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Seasonmodified> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.avg = 0.0;
        this.duration = 0;
        calculateDuration();
    }

    private void calcAvg() {
        Double sumRatings = 0.0;
        int nrSeasons = this.seasons.size();

        if (nrSeasons == 0) {
            return;
        }

        for (Seasonmodified currentSeason : this.seasons) {
            sumRatings += currentSeason.getAverage();
        }

        this.avg = sumRatings / (double) nrSeasons;
    }

    private void calculateDuration() {
        for (Seasonmodified season : this.seasons) {
            this.duration += season.getDuration();
        }
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Seasonmodified> getSeasons() {
        return seasons;
    }

    /**
     * Returneaza media si durata actualizata a serialului
     */
    @Override
    public Double getAvg() {
        calcAvg();
        return avg;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
