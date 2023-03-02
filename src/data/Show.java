package data;

import java.util.ArrayList;

/**
 * Informatii generale despre un show
 */
public abstract class Show {
    /**
     * Titlul show-ului
     */
    private final String title;
    /**
     * Anul in care show-ul a fost lansat
     */
    private final int year;
    /**
     * Distributia show-ului
     */
    private final ArrayList<String> cast;
    /**
     * Genurile show-ului
     */
    private final ArrayList<String> genres;
    /**
     * Retine de cate ori un show a fost adaugat in lista de favorite de mai multi utilizatori
     */
    private int favAdds;
    /**
     * Retine numarul de vizualizari al show-ului
     */
    private int nrviews;

    public Show(final String title, final int year,
                final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.favAdds = 0;
        this.nrviews = 0;
    }

    /**
     * Fiecare film sau serial are un rating mediu,aceasta metoda este abstracta deoarece trebuie implementata in clasele copil
     */
    public abstract Double getAvg();

    /**
     * Returneaza durata unui show
     */
    public abstract int getDuration();

    /**
     * Returneaza true daca show-ul a fost vazut de utilizatorul primit ca parametru
     */
    public final boolean isSeenByUser(final User user) {
        if (user == null) {
            return false;
        }

        for (String showName : user.getHistory().keySet()) {
            if (showName.equals(this.title)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creste atunci cand un show este adaugat la favorite
     */
    public final void increaseFavoriteAdds() {
        this.favAdds++;
    }

    /**
     * Returneaza true daca show contine tipul de show primit ca parametru
     */
    public final boolean hasShowType(final String st) {
        for (String currentShowType : this.genres) {
            if (currentShowType.toUpperCase().equals(st)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creste numarul de vizualizari,adunand numarul primit ca parametru
     */
    public final void increaseNrViews(final int num) {
        this.nrviews += num;
    }

    public final int getNrViews() {
        return nrviews;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final int getFavoriteAdds() {
        return favAdds;
    }
}
