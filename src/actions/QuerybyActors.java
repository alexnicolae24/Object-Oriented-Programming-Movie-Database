package actions;

import data.Action;
import data.Actor;
import database.Find;
import utils.Utils;

import java.util.List;

/**
 * Clasa care efectueaza un query dupa actori
 */
class QuerybyActors {
    QuerybyActors() {
    }

    String perform(final Action action) {
        int size = action.getFilters().size();
        String crit = action.getCriteria();
        String mess;

        switch (crit) {
            case "average":
                mess = average(action.getNumber(), action.getSortType());
                break;

            case "awards":
                mess = awards(
                        action.getFilters().get(size - 1),
                        action.getSortType());
                break;

            case "filter_description":
                mess = filterDescription(
                        action.getFilters().get(size - 2),
                        action.getSortType());
                break;

            default:
                return "query on actors on criteria " + crit + " not found";
        }

        return mess;
    }

    private String average(final int nrActors, final String sortType) {
        StringBuilder stringBuilder = new StringBuilder();
        int nrActorsCalc = nrActors;

        List<Actor> sortActors = Find
                                    .getFinder()
                                    .getActorsSortedByAverage(sortType);


        if (sortType.equals("desc")) {
            int firstIdxRat = Find
                            .getFinder()
                            .getFirstIndexOf0RatingActors(sortActors);

            if (firstIdxRat == 0) {
                return "]";
            }

            if (nrActors > sortActors.size()) {
                nrActorsCalc = sortActors.size();
            }

            for (int i = 0; i < nrActorsCalc; i++) {
                if (i == nrActorsCalc - 1 || i == firstIdxRat - 1) {
                    stringBuilder.append(sortActors.get(i).getName() + "]");
                    break;
                }

                stringBuilder.append(sortActors.get(i).getName() + ", ");
            }

        } else {
            int startIdx = Find
                            .getFinder()
                            .getFirstIndexOfNon0RatingActors(sortActors);

            if (startIdx == 0 || startIdx >= sortActors.size()) {
                return "]";
            }

            if (nrActorsCalc > sortActors.size()) {
                nrActorsCalc = sortActors.size();
            }

            for (int i = 0; i < nrActorsCalc; i++) {
                if (startIdx == sortActors.size() - 1 || i == nrActorsCalc - 1) {
                    stringBuilder.append(sortActors.get(startIdx).getName() + "]");
                    break;
                }

                stringBuilder.append(sortActors.get(startIdx).getName() + ", ");
                startIdx++;
            }
        }

        return stringBuilder.toString();
    }

    private String awards(final List<String> listOfAwards, final String sortType) {
        List<Actor> sortedActors = Find
                                    .getFinder()
                                    .getActorsSortedByNrAwards(sortType);

        List<Actor> actorsWithAwards = Find
                                        .getFinder()
                                        .getActorsWithAwrds(sortedActors, listOfAwards);

        if (actorsWithAwards.size() == 0) {
            return "]";
        }

        return Utils.buildStringActors(actorsWithAwards, actorsWithAwards.size());
    }

    private String filterDescription(final List<String> listOfWords,
                                     final String sortType) {
        List<Actor> sortActors = Find
                                    .getFinder()
                                    .getActorsByName(sortType);

        List<Actor> actorsKeywords = Find
                                        .getFinder()
                                        .getActorsWithKeywords(sortActors, listOfWords);

        if (actorsKeywords.size() == 0) {
            return "]";
        }

        return Utils.buildStringActors(actorsKeywords, actorsKeywords.size());
    }
}
