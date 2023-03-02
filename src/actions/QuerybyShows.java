package actions;

import data.Action;
import data.Show;
import database.Find;

import java.util.List;

/**
 * Clasa cara efectueaza un query dupa flme sau seriale
 */
class QuerybyShows {
    QuerybyShows() {
    }

    String perform(final Action action) {
        List<? extends Show> shows;
        if (action.getObjectType().equals("movies")) {
            shows = Find.getFinder().getMoviesData();
        } else {
            shows = Find.getFinder().getSerialsData();
        }

        String crit = action.getCriteria();
        String year = action.getFilters().get(0).get(0);
        String type = action.getFilters().get(1).get(0);

        String mess;

        switch (crit) {
            case "ratings":
                mess = ratings(shows, action.getNumber(),
                        year, type, action.getSortType());
                break;

            case "favorite":
                mess = favorite(shows, action.getNumber(),
                        year, type, action.getSortType());
                break;

            case "longest":
                mess = longest(shows, action.getNumber(),
                        year, type, action.getSortType());
                break;

            case "most_viewed":
                mess = mostViewed(shows, action.getNumber(),
                        year, type, action.getSortType());
                break;

            default:
                return "query on shows on criteria " + crit + " not found";
        }

        return mess;
    }

    private String ratings(final List<? extends Show> shows,
                           final int nrShows, final String year,
                           final String type, final String sortType) {
        List<? extends Show> res;
        res = Find.getFinder().getShowsSortedByRat(shows, sortType);
        res = filter(res, year, type);

        int startIdx = Find
                .getFinder()
                .getFirstIndexOfNon0RatingShows(res);

        int firstIdx0 = Find
                .getFinder()
                .getFirstIndexOf0RatingShows(res);

        if (sortType.equals("asc")) {
            return buildAsc(res, nrShows, startIdx);
        } else {
            return buildDesc(res, nrShows, firstIdx0);
        }
    }

    private String favorite(final List<? extends Show> shows,
                            final int nrShows, final String year,
                            final String type, final String sortType) {
        List<? extends Show> res;
        res = Find.getFinder().getShowsSortedByFavorite(shows, sortType);
        res = filter(res, year, type);

        int firstIdx0 = Find
                            .getFinder()
                            .getFirstIndexOf0FavoriteShows(res);

        int startIndex = Find
                            .getFinder()
                            .getFirstIndexOfNon0FavoriteShows(res);

        if (sortType.equals("asc")) {
            return buildAsc(res, nrShows, startIndex);
        } else {
            return buildDesc(res, nrShows, firstIdx0);
        }
    }

    private String longest(final List<? extends Show> shows,
                           final int nrShows, final String year,
                           final String type, final String sortType) {
        List<? extends Show> res;
        res = Find.getFinder().getShowsSortedByDuration(shows, sortType);
        res = filter(res, year, type);

        if (sortType.equals("asc")) {
            return buildAsc(res, nrShows, 0);
        } else {
            return buildDesc(res, nrShows, res.size());
        }
    }

    private String mostViewed(final List<? extends Show> shows,
                              final int nrShows, final String year,
                              final String type, final String sortType) {
        List<? extends Show> res;
        res = Find.getFinder().getShowsSortedByNrViews(shows, sortType);
        res = filter(res, year, type);

        int firstIdx0 = Find
                .getFinder()
                .getFirstIndexOf0ViewsShows(res);

        int startIndex = Find
                .getFinder()
                .getFirstIndexOfNon0ViewsShows(res);


        if (sortType.equals("asc")) {
            return buildAsc(res, nrShows, startIndex);
        } else {
            return buildDesc(res, nrShows, firstIdx0);
        }
    }

    /**
     * @param startIdx primul index din lista cu valoarea nenula
     * Returneaza o lista contine primele nrShows obiecte de tip Show,
     * cu conditia ca nrShows sa fie mai mare decat size-ul listei
     */
    private String buildAsc(final List<? extends Show> shows,
                            final int nrShows,
                            final int startIdx) {
        StringBuilder stringBuilder = new StringBuilder();
        int nrShowscopy = nrShows;
        int startIdxcopy = startIdx;

        if (startIdxcopy == -1 || startIdxcopy >= shows.size()) {
            return "]";
        }

        if (nrShowscopy > shows.size()) {
            nrShowscopy = shows.size();
        }

        for (int i = 0; i < nrShowscopy; i++) {
            if (startIdxcopy == shows.size() - 1 || i == nrShowscopy - 1) {
                stringBuilder.append(shows.get(startIdxcopy).getTitle() + "]");
                break;
            }

            stringBuilder.append(shows.get(startIdxcopy).getTitle() + ", ");
            startIdxcopy++;
        }

        if (stringBuilder.toString().equals("")) {
            stringBuilder.append("]");
        }

        return stringBuilder.toString();
    }

    /**
     * @param firstIdx0 primul index din lista cu valoarea 0
     * Returneaza o lista care contine primele nrShows obiecte de tip Show,
     * cu conditia ca nrShows - 1 sa fie mai mare decat firstIdx0
     */
    private String buildDesc(final List<? extends Show> shows,
                                   final int nrShows,
                                   final int firstIdx0) {
        StringBuilder stringBuilder = new StringBuilder();
        int nrShowscopy = nrShows;

        if (firstIdx0 == 0) {
            return "]";
        }

        if (nrShows >= shows.size()) {
            nrShowscopy = shows.size();
        }

        for (int i = 0; i < nrShowscopy; i++) {
            if (i == nrShowscopy - 1 || i == firstIdx0 - 1) {
                stringBuilder.append(shows.get(i).getTitle() + "]");
                break;
            }

            stringBuilder.append(shows.get(i).getTitle() + ", ");
        }

        if (stringBuilder.toString().equals("")) {
            stringBuilder.append("]");
        }

        return stringBuilder.toString();
    }



    private List<? extends Show> filter(final List<? extends Show> shows,
                                        final String year, final String gen) {
        List<? extends Show> res = shows;

        if (year != null) {
            res = Find.getFinder().getShowsByYear(res, year);
        }

        if (gen != null) {
            res = Find.getFinder().getShowsByGen(res, gen);
        }

        return res;
    }
}
