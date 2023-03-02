package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Informatii despre un film
 */
public final class Movie extends Show {
    /**
     * Durata in minute a filmului
     */
    private final int duration;
    /**
     * Mapa care retine userii care au dat un rating la film si ratingul acestora
     */
    private final Map<String, Double> usersRat;
    /**
     * Ratingul mediu al unui film
     */
    private Double avg;

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.usersRat = new HashMap<>();
        this.avg = 0.0;
    }

    private void calcAvg() {
        int size = this.usersRat.size();
        Double S = 0.0;

        if (size == 0) {
            return;
        }

        for (String currentUser : this.usersRat.keySet()) {
            S += this.usersRat.get(currentUser);
        }

        this.avg = S / (double) size;
    }

    /**
     * Aceasta metoda va adauga ratingul unui user,daca acesta nu a mai rate-uit filmul si afiseaza un mesaj corespunzator
     */
    public String addRating(final User user, final Double rating) {
        if (this.usersRat.containsKey(user.getUsername())) {
            return "error -> " + this.getTitle() + " has been already rated";
        }

        this.usersRat.put(user.getUsername(), rating);
        calcAvg();

        user.increaseNumOfRatingsGiven();

        return "success -> " + this.getTitle() + " was rated with "
                + rating + " by " + user.getUsername();
    }

    /**
     * Returneaza media si durata actualizata a filmului
     */
    @Override
    public Double getAvg() {
        return avg;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
