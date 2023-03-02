package data;

import entertainment.ShowType;
import database.Find;

import java.util.ArrayList;
import java.util.Map;

/**
 * Informatii despre un user
 */
public final class User {
    /**
     * Username-ul user-ului
     */
    private final String username;
    /**
     * Tipul de abonament
     */
    private final String subscriptionType;
    /**
     * Istoricul
     */
    private final Map<String, Integer> history;
    /**
     * Filmele adaugate in lista de favorite
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * Verifica daca updateFav a fost apelata
     */
    private boolean isFavUpdated;
    /**
     * Verifica daca updateViews a fost apelata
     */
    private boolean isViewsUpdated;
    /**
     * De cate ori un user a dat o recenzie unui show
     */
    private int nrRatingsGiven;
    /**
     * Verifica daca updateShowTypeViews a fost apelata
     */
    private boolean isShowTypeViewsUpdated;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.isFavUpdated = false;
        this.isViewsUpdated = false;
        this.nrRatingsGiven = 0;
        this.isShowTypeViewsUpdated = false;
    }

    /**
     * Creste numarul de adaugari la favorite pentru un show daca show-ul
     * este prezent in lista de favorite a utilizatorului
     */
    public void updateFav() {
        if (isFavUpdated) {
            return;
        }

        for (String currentShowName : this.favoriteMovies) {
            Show show = Find.getFinder().getShowAfterName(currentShowName);
            if (show != null) {
                show.increaseFavoriteAdds();
            }
        }

        this.isFavUpdated = true;
    }

    /**
     * Creste numarul de vizualizari pentru un show daca
     * acesta este prezent in istoricul utilizatorului
     */
    public void updateViews() {
        if (isViewsUpdated) {
            return;
        }

        for (String currentShowName : this.history.keySet()) {
            Show show = Find.getFinder().getShowAfterName(currentShowName);
            if (show != null) {
                show.increaseNrViews(this.history.get(currentShowName));
            }
        }

        this.isViewsUpdated = true;
    }

    /**
     * Creste numarul de vizualizari al unui tip de show pentru un show
     * daca acesta este prezent in istoricul utilizatorului
     */
    public void updateShowTypeViews() {
        if (isShowTypeViewsUpdated) {
            return;
        }

        for (String currentShowName : this.history.keySet()) {
            Show currentShow = Find.getFinder().getShowAfterName(currentShowName);
            if (currentShow == null) {
                return;
            }

            for (String currentGenName : currentShow.getGenres()) {
                ShowType currentShowType = Find
                                            .getFinder()
                                            .getShowTypeAfterName(currentGenName);
                if (currentShowType != null) {
                    currentShowType.increaseNrViews(this.history.get(currentShowName));
                }
            }
        }

        this.isShowTypeViewsUpdated = true;
    }

    /**
     * Creste numarul de ratinguri date de un user
     */
    public void increaseNumOfRatingsGiven() {
        this.nrRatingsGiven++;
    }

    /**
     * Returneaza true daca abonamentul user-ului este premium
     */
    public boolean isUserPremium() {
        if (this.subscriptionType.equals("BASIC")) {
            return false;
        }

        return true;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public int getNrRatingsGiven() {
        return nrRatingsGiven;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
