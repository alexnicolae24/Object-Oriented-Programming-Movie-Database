package database;

import entertainment.ShowType;
import entertainment.Genre;
import entertainment.Seasonmodified;
import data.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton folosit pentru cautarea obiectelor dupa anumite criterii
 */
public final class Find {
    private static final Find FIND;

    /**
     * Lista de ShowType
     */
    private List<ShowType> showtypeData;
    /**
     * Lista de show-uri
     */
    private List<Show> showsData;
    /**
     * Lista de actori
     */
    private List<Actor> actorsData;
    /**
     * Lista de useri
     */
    private List<User> usersData;
    /**
     * Lista de filme
     */
    private List<Movie> moviesData;
    /**
     * Lista de seriale
     */
    private List<Serial> serialsData;

    static {
        FIND = new Find();
    }

    private Find() {
    }

    public static Find getFinder() {
        return FIND;
    }

    /**
     * Creeaza noi liste care au aceleasi referinte ca obiectele din listele originale
     * @param input contine listele originale
     */
    public void setInput(final Input input) {
        this.actorsData = new ArrayList<>(input.getActors());
        this.usersData = new ArrayList<>(input.getUsers());
        this.moviesData = new ArrayList<>(input.getMovies());
        this.serialsData = new ArrayList<>(input.getSerials());
        this.showsData = new ArrayList<>();
        this.showtypeData = new ArrayList<>();

        this.showsData.addAll(input.getMovies());
        this.showsData.addAll(input.getSerials());

        for (Genre genre : Genre.values()) {
            this.showtypeData.add(new ShowType(genre.toString()));
        }
    }

    private List<Seasonmodified> getSeasonsOfSerial(final String serialName) {
        if (this.serialsData.isEmpty()) {
            return null;
        }

        for (Serial serial : this.serialsData) {
            if (serial.getTitle().equals(serialName)) {
                return serial.getSeasons();
            }
        }

        return null;
    }

    /**
     * Returneaza obiectul de tip User care are acelasi username ca parametrul
     */
    public User getUserAfterUsername(final String userName) {
        if (this.usersData.isEmpty()) {
            return null;
        }

        for (User currentUser : this.usersData) {
            if (currentUser.getUsername().equals(userName)) {
                return currentUser;
            }
        }

        return null;
    }

    /**
     * Returneaza obiectul de tip ShowType care are acelasi name ca parametrul
     */
    public ShowType getShowTypeAfterName(final String genName) {
        for (ShowType currentShowType : this.showtypeData) {
            if (currentShowType.getName().equals(genName.toUpperCase())) {
                return currentShowType;
            }
        }
        return null;
    }

    /**
     * Returneaza filmul dupa numele sau
     */
    public Movie getMovieByName(final String movieName) {
        if (this.moviesData.isEmpty()) {
            return null;
        }

        for (Movie movie : this.moviesData) {
            if (movie.getTitle().equals(movieName)) {
                return movie;
            }
        }

        return null;
    }

    /**
     * Returneaza un sezon dupa numele serialului si al numarul sezonului
     */
    public Seasonmodified getSeasonOfSerial(final String serialName,
                                            final int nrSeason) {
        List<Seasonmodified> seasons = getSeasonsOfSerial(serialName);
        if (seasons == null || nrSeason > seasons.size()) {
            return null;
        }

        return seasons.get(nrSeason - 1);
    }

    /**
     * Returneaza show-ul cu acelasi nume ca parametrul
     */
    public Show getShowAfterName(final String showName) {
        if (this.moviesData == null) {
            return null;
        }

        for (Show currentShow : this.moviesData) {
            if (currentShow.getTitle().equals(showName)) {
                return currentShow;
            }
        }

        if (this.serialsData == null) {
            return null;
        }

        for (Show currentShow : this.serialsData) {
            if (currentShow.getTitle().equals(showName)) {
                return currentShow;
            }
        }

        return null;
    }

    /**
     * Returneaza lista sortata cu ajutorul comparatorului pentru
     * ratingul mediu aplicat pe lista de actori
     */
    public List<Actor> getActorsSortedByAverage(final String sortType) {
        if (this.moviesData.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(
                    this.actorsData,
                    Comp.getInstance().getAvgDesc());
        } else {
            Collections.sort(
                    this.actorsData,
                    Comp.getInstance().getAvgAsc());
        }

        return this.actorsData;
    }

    /**
     * Retureaza indexul primului actor care are ratingul diferit de 0
     */
    public int getFirstIndexOfNon0RatingActors(final List<Actor> actorList) {
        if (actorList.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < actorList.size(); i++) {
            if (actorList.get(i).getAverage() != 0.0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza indexul primului actor care are ratingul egal cu 0
     */
    public int getFirstIndexOf0RatingActors(final List<Actor> actorList) {
        if (actorList.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < actorList.size(); i++) {
            if (actorList.get(i).getAverage() == 0.0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza indexul primului show care are ratingul diferit de 0
     */
    public int getFirstIndexOfNon0RatingShows(final List<? extends Show> showlist) {
        if (showlist.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < showlist.size(); i++) {
            if (showlist.get(i).getAvg() != 0.0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza indexul primului show care are ratingul egal cu 0
     */
    public int getFirstIndexOf0RatingShows(final List<? extends Show> showlist) {
        if (showlist.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < showlist.size(); i++) {
            if (showlist.get(i).getAvg() == 0.0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza indexul primului show care are numarul de adugari la favorite diferit de 0
     */
    public int getFirstIndexOfNon0FavoriteShows(final List<? extends Show> showlist) {
        if (showlist.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < showlist.size(); i++) {
            if (showlist.get(i).getFavoriteAdds() != 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     *Returneaza indexul primului show care are numarul de adugari la favorite egal cu 0
     */
    public int getFirstIndexOf0FavoriteShows(final List<? extends Show> showlist) {
        if (showlist.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < showlist.size(); i++) {
            if (showlist.get(i).getFavoriteAdds() == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza indexul primului show care are numarul de vizualizari diferit de 0
     */
    public int getFirstIndexOfNon0ViewsShows(final List<? extends Show> showlist) {
        if (showlist.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < showlist.size(); i++) {
            if (showlist.get(i).getNrViews() != 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza indexul primului show care are numarul de vizualizari egal cu 0
     */
    public int getFirstIndexOf0ViewsShows(final List<? extends Show> showlist) {
        if (showlist.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < showlist.size(); i++) {
            if (showlist.get(i).getNrViews() == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza indexul primului user care a acordat un numar de ratinguri diferit de 0
     */
    public int getFirstIndexOfNon0RatingUsers(final List<User> userlist) {
        if (userlist.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getNrRatingsGiven() != 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza indexul primului user care a acordat un numar de ratinguri diferit de 0
     */
    public int getFirstIndexOf0RatingUsers(final List<User> userlist) {
        if (userlist.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getNrRatingsGiven() == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza o lista de actori sortata cu ajutorul comparatorului pentru numarul de premii
     */
    public List<Actor> getActorsSortedByNrAwards(final String sortType) {
        if (this.actorsData.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(
                    this.actorsData,
                    Comp.getInstance().getAwardsDesc());
        } else {
            Collections.sort(
                    this.actorsData,
                    Comp.getInstance().getAwardsAsc());
        }

        return this.actorsData;
    }

    /**
     * Returneaza o lista care contine toti actorii care au castigat
     * toate premiile din lista de premii primita ca parametru
     */
    public List<Actor> getActorsWithAwrds(final List<Actor> actors,
                                           final List<String> listOfAwards) {
        if (actors.isEmpty()) {
            return null;
        }

        List<Actor> res = new ArrayList<>();

        for (Actor currentActor : actors) {
            if (currentActor.hasAllAwards(listOfAwards)) {
                res.add(currentActor);
            }
        }

        return res;
    }

    /**
     * Sorteaza lista de actori alfabetic dupa nume
     */
    public List<Actor> getActorsByName(final String sortType) {
        if (this.actorsData.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(
                    this.actorsData,
                    Comp.getInstance().getActorNameDesc());
        } else {
            Collections.sort(
                    this.actorsData,
                    Comp.getInstance().getActorNameAsc());
        }

        return this.actorsData;
    }

    /**
     * Returneaza o lista noua care contine actori care au prezente toate
     * cuvintele-cheie prezente  in descrierea carierelor lor
     */
    public List<Actor> getActorsWithKeywords(final List<Actor> actorList,
                                             final List<String> keyws) {
        if (actorList.isEmpty()) {
            return null;
        }

        List<Actor> res = new ArrayList<>();

        for (Actor currentActor : actorList) {
            if (currentActor.descriptionHasKeywords(keyws)) {
                res.add(currentActor);
            }
        }

        return res;
    }

    /**
     * Sorteaza lista de show-uri dupa rating
     */
    public List<? extends Show> getShowsSortedByRat(final List<? extends Show> showlist,
                                                       final String sortType) {
        if (showlist == null) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(showlist, Comp.getInstance().getShowRatingDesc());
        } else {
            Collections.sort(showlist, Comp.getInstance().getShowRatingAsc());
        }

        return showlist;
    }

    /**
     * Sorteaza dupa rating si aparatie lista primita ca parametru
     */
    public List<? extends Show> getShowsSortedByRatingAndOccurrence(
            final List<? extends Show> showlist,
            final String sortType) {

        if (showlist.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(showlist, Comp.getInstance().getShowRatingDescOrder());
        } else {
            Collections.sort(showlist, Comp.getInstance().getShowRatingAscOrder());
        }

        return showlist;
    }

    /**
     * Sorteaza dupa adaugarile la favorite si aparatie lista primita ca parametru
     */
    public List<? extends Show> getShowsSortedByFavoriteAndOccurrence(
            final List<? extends Show> showlist,
            final String sortType) {

        if (showlist.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(showlist, Comp.getInstance().getShowFavoriteDescOrder());
        }

        return showlist;
    }

    /**
     * Returneaza indexul showului din datele show-ului primit ca parametru
     */
    public int getIdxOfShowInD(final Show show) {
        if (show == null) {
            return -1;
        }

        for (int i = 0; i < this.showsData.size(); i++) {
            if (this.showsData.get(i).getTitle().equals(show.getTitle())) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Sorteaza lista de showuri dupa nr de aparatii in favorite
     */
    public List<? extends Show> getShowsSortedByFavorite(final List<? extends Show> showlist,
                                                         final String sortType) {
        if (showlist.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(showlist, Comp.getInstance().getShowFavoriteDesc());
        } else {
            Collections.sort(showlist, Comp.getInstance().getShowFavoriteAsc());
        }

        return showlist;
    }

    /**
     * Sorteaza lista de showuri dupa durata
     */
    public List<? extends Show> getShowsSortedByDuration(final List<? extends Show> showlist,
                                                         final String sortType) {
        if (showlist.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(showlist, Comp.getInstance().getShowDurationDesc());
        } else {
            Collections.sort(showlist, Comp.getInstance().getShowDurationAsc());
        }

        return showlist;
    }

    /**
     * Sorteaza lista de showuri dupa numarul de vizualizari
     */
    public List<? extends Show> getShowsSortedByNrViews(final List<? extends Show> showlist,
                                                      final String sortType) {
        if (showlist.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(showlist, Comp.getInstance().getShowViewsDesc());
        } else {
            Collections.sort(showlist, Comp.getInstance().getShowViewsAsc());
        }

        return showlist;
    }

    /**
     * Sorteaza lista de utilizatori dupa numrul de ratinguri date de fiecare
     */
    public List<User> getUsersSortedByNrRatings(final String sortType) {
        if (this.usersData.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(this.usersData, Comp.getInstance().getUserRatingsDesc());
        } else {
            Collections.sort(this.usersData, Comp.getInstance().getUserRatingsAsc());
        }

        return this.usersData;
    }

    /**
     * Returneaza o lista cu toate show-urile lansate in anul primit ca parametru
     */
    public List<? extends Show> getShowsByYear(final List<? extends Show> showlist,
                                               final String year) {
        if (showlist.isEmpty()) {
            return null;
        }

        List<Show> res = new ArrayList<>();

        for (Show currentShow : showlist) {
            String yearAsString = Integer.toString(currentShow.getYear());
            if (yearAsString.equals(year)) {
                res.add(currentShow);
            }
        }

        return res;
    }

    /**
     * Returneaza lista de showuri care au ShowType-ul la fel cu cel primit ca parametru
     */
    public List<? extends Show> getShowsByGen(final List<? extends Show> showlist,
                                              final String gen) {
        if (showlist == null) {
            return null;
        }

        List<Show> res = new ArrayList<>();

        for (Show currentShow : showlist) {
            for (String currentGen : currentShow.getGenres()) {
                if (currentGen.equals(gen)) {
                    res.add(currentShow);
                }
            }
        }

        return res;
    }

    /**
     * Sorteaza o lista de ShowType dupa numarul de vizualizari
     */
    public List<ShowType> getShowTypeSortedNrViews(final String sortType) {
        if (this.showtypeData.isEmpty()) {
            return null;
        }

        if (sortType.equals("desc")) {
            Collections.sort(
                    this.showtypeData,
                    Comp.getInstance().getGenViewsDesc());
        }

        return this.showtypeData;
    }

    public List<Movie> getMoviesData() {
        return moviesData;
    }

    public List<Serial> getSerialsData() {
        return serialsData;
    }

    public List<Show> getShowsData() {
        return showsData;
    }
}
