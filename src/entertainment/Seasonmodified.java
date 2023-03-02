package entertainment;

import data.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Informatii despre un sezon al unui serial
 */
public final class Seasonmodified {
    /**
     * Numarul sezonului curent
     */
    private final int currentSeason;
    /**
     * Durata in minute a unui sezon
     */
    private int duration;
    /**
     * Lista cu ratingurile fiecarui sezon
     */
    private List<Double> ratings;
    /**
     * Mapa care retine numele utilizatorilor care au dat rating si ratingurile
     * oferite de acestia
     */
    private final Map<String, Double> usersRatings;
    /**
     * Ratingul mediu al unui sezon
     */
    private Double average;

    public Seasonmodified(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.usersRatings = new HashMap<>();
        this.average = 0.0;
    }

    /**
     * Calculul ratingului mediu de la fiecare user al unui sezon
     */
    private void makeAverage() {
        int sizeOfMap = this.usersRatings.size();
        Double sumRat = 0.0;

        if (sizeOfMap == 0) {
            return;
        }

        for (String currentUser : this.usersRatings.keySet()) {
            sumRat += this.usersRatings.get(currentUser);
        }

        this.average = sumRat / (double) sizeOfMap;
    }

    /**
     * Metoda adauga rating-ul unui user pentru un sezon daca
     * user-ul nu a mai acordat recenzie acestuia si returneaza mesajul corespunzator
     */
    public String addRating(final User user, final Double rating,
                            final String serialTitle) {
        if (this.usersRatings.containsKey(user.getUsername())) {
            return "error -> " + serialTitle + " has been already rated";
        }

        this.usersRatings.put(user.getUsername(), rating);
        makeAverage();

        user.increaseNumOfRatingsGiven();

        return "success -> " + serialTitle + " was rated with "
                + rating + " by " + user.getUsername();
    }

    public Double getAverage() {
        return average;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

