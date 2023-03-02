package actions;

import entertainment.ShowType;
import entertainment.Seasonmodified;
import data.Action;
import data.Movie;
import data.Show;
import data.User;
import database.Find;

import java.util.List;
import java.util.Map;

/**
 * Singleton care executa o actiune de tip COMMAND
 */
final class CommandDoer {
    private static final CommandDoer COMMAND_DOER;

    static {
        COMMAND_DOER = new CommandDoer();
    }

    private CommandDoer() {
    }

    public static CommandDoer getInstance() {
        return COMMAND_DOER;
    }

    /**
     * Identifica tipul comenzii
     */
    String perform(final Action action) {

        User user = Find
                .getFinder()
                .getUserAfterUsername(action.getUsername());

        if (user == null) {
            return "Username " + action.getUsername() + " not found";
        }

        String commandType = action.getType();
        String mess;

        switch (commandType) {
            case "favorite":
                mess = addFav(user, action.getTitle());
                break;

            case "view":
                mess = watch(user, action.getTitle());
                break;

            case "rating":
                if (action.getSeasonNumber() == 0) {
                    mess = rateMovie(user, action.getTitle(), action.getGrade());
                } else {
                    mess = rateSeason(user, action.getTitle(),
                            action.getGrade(), action.getSeasonNumber());
                }
                break;

            default:
                return "command of type " + commandType + " not found";
        }

        return mess;
    }

    // Da un rating unui film vizionat
    private String rateMovie(final User user, final String title,
                              final Double rating) {
        if (user.getHistory().containsKey(title)) {
            Movie movie = Find
                    .getFinder()
                    .getMovieByName(title);

            if (movie == null) {
                return "movie not found";
            }

            return movie.addRating(user, rating);
        }

        return "error -> " + title + " is not seen";
    }

    // Da un rating sezonului unui serial vizionat
    private String rateSeason(final User user, final String title,
                              final Double rating, final int numOfSeason) {
        if (user.getHistory().containsKey(title)) {
            Seasonmodified season = Find
                    .getFinder()
                    .getSeasonOfSerial(title, numOfSeason);

            if (season == null) {
                return "season" + numOfSeason + " of " + title + " not found";
            }

            return season.addRating(user, rating, title);
        }

        return "error -> " + title + " is not seen";
    }

    /*
     * Adaugarea in lista de favorite
     */
    private String addFav(final User user, final String title) {
        if (user.getHistory().containsKey(title)) {
            List<String> favoriteMovies = user.getFavoriteMovies();

            if (!favoriteMovies.contains(title)) {
                favoriteMovies.add(title);

                Show show = Find
                        .getFinder()
                        .getShowAfterName(title);
                if (show != null) {
                    show.increaseFavoriteAdds();
                }

                return "success -> " + title + " was added as favourite";
            }

            return "error -> " + title + " is already in favourite list";
        }

        return "error -> " + title + " is not seen";
    }

    /*
     * Creste numarul de vizualizari
     */
    private String watch(final User user, final String title) {
        Map<String, Integer> userHistory = user.getHistory();
        int nrViews = 0;

        if (userHistory.containsKey(title)) {
            nrViews = userHistory.get(title);
        }

        nrViews++;
        userHistory.put(title, nrViews);

        Show show = Find.getFinder().getShowAfterName(title);
        if (show != null) {
            show.increaseNrViews(1);

            for (String currentGenName : show.getGenres()) {
                ShowType currentShowType = Find.getFinder().getShowTypeAfterName(currentGenName);
                if (currentShowType != null) {
                    currentShowType.increaseNrViews(1);
                }
            }
        }

        return "success -> " + title + " was viewed with total views of " + nrViews;
    }
}
