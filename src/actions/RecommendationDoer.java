package actions;

import entertainment.ShowType;
import data.Action;
import data.Show;
import data.User;
import database.Find;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton care executa o actiune de tip RECOMMENDATION
 */
final class RecommendationDoer {
    private static final RecommendationDoer RECOMM_DOER;

    static {
        RECOMM_DOER = new RecommendationDoer();
    }

    private RecommendationDoer() {
    }

    public static RecommendationDoer getInstance() {
        return RECOMM_DOER;
    }

    /**
     * Identifica tipul de recomandare si apeleaza metodele corespunzatoare
     */
    String perform(final Action action) {
        String type = action.getType();
        String ans;
        String mess = "";

        User user = Find
                        .getFinder()
                        .getUserAfterUsername(action.getUsername());

        switch (type) {
            case "standard":
                ans = standard(user);
                if (ans.equals("Recommendation cannot be applied")) {
                    return "StandardRecommendation cannot be applied!";
                }

                mess += "StandardRecommendation result: " + ans;
                break;

            case "best_unseen":
                ans = bestUnseen(user);
                if (ans.equals("Recommendation cannot be applied")) {
                    return "BestRatedUnseenRecommendation cannot be applied!";
                }

                mess += "BestRatedUnseenRecommendation result: " + ans;
                break;

            case "popular":
                ans = popular(user);
                if (ans.equals("Recommendation cannot be applied")) {
                    return "PopularRecommendation cannot be applied!";
                }

                mess += "PopularRecommendation result: " + ans;
                break;

            case "favorite":
                ans = favorite(user);
                if (ans.equals("Recommendation cannot be applied")) {
                    return "FavoriteRecommendation cannot be applied!";
                }

                mess += "FavoriteRecommendation result: " + ans;
                break;

            case "search":
                ans = search(user, action.getGenre());
                if (ans.equals("Recommendation cannot be applied")) {
                    return "SearchRecommendation cannot be applied!";
                }

                mess += "SearchRecommendation result: " + ans;
                break;

            default:
                return "recommendation of type " + type + " not found";
        }

        return mess;
    }

    private String standard(final User user) {
        for (Show currentShow : Find.getFinder().getShowsData()) {
            if (!currentShow.isSeenByUser(user)) {
                return currentShow.getTitle();
            }
        }

        return "Recommendation cannot be applied";
    }

    private String bestUnseen(final User user) {
        List<? extends Show> list = new ArrayList<>(Find.getFinder().getShowsData());
        list = Find
                .getFinder()
                .getShowsSortedByRatingAndOccurrence(list, "desc");

        for (Show currentShow : list) {
            if (!currentShow.isSeenByUser(user)) {
                return currentShow.getTitle();
            }
        }

        return "Recommendation cannot be applied";
    }

    private String popular(final User user) {
        if (!user.isUserPremium()) {
            return "Recommendation cannot be applied";
        }

        List<ShowType> sortedGenres = Find
                .getFinder()
                .getShowTypeSortedNrViews("desc");

        if (sortedGenres == null) {
            return "Recommendation cannot be applied";
        }

        for (ShowType currentShowType : sortedGenres) {
            for (Show currentShow : Find.getFinder().getShowsData()) {
                if (!currentShow.isSeenByUser(user)) {
                    if (currentShow.hasShowType(currentShowType.getName())) {
                        return currentShow.getTitle();
                    }
                }
            }
        }

        return "Recommendation cannot be applied";
    }

    private String favorite(final User user) {
        if (!user.isUserPremium()) {
            return "Recommendation cannot be applied";
        }

        List<? extends Show> list = new ArrayList<>(Find.getFinder().getShowsData());
        list = Find
                .getFinder()
                .getShowsSortedByFavoriteAndOccurrence(list, "desc");

        if (list.size() == 0 || list.get(0).getFavoriteAdds() == 0) {
            return "";
        }

        for (Show currentShow : list) {
            if (!currentShow.isSeenByUser(user)) {
                return currentShow.getTitle();
            }
        }

        return "Recommendation cannot be applied";
    }

    private String search(final User user, final String genre) {
        if (!user.isUserPremium()) {
            return "Recommendation cannot be applied";
        }

        List<Show> res = new ArrayList<>();

        for (Show currentShow : Find.getFinder().getShowsData()) {
            if (!currentShow.isSeenByUser(user) && currentShow.hasShowType(genre.toUpperCase())) {
                res.add(currentShow);
            }
        }

        List<? extends Show> res1 = new ArrayList<>(res);
        res1 = Find.getFinder().getShowsSortedByRat(res1, "asc");

        if (res1.size() == 0) {
            return "Recommendation cannot be applied";
        }

        return buildStrShow(res1);
    }

    private String buildStrShow(final List<? extends Show> shows) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int i = 0; i < shows.size(); i++) {
            if (i == shows.size() - 1) {
                stringBuilder.append(shows.get(i).getTitle() + "]");
                break;
            }

            stringBuilder.append(shows.get(i).getTitle() + ", ");
        }

        return stringBuilder.toString();
    }
}
