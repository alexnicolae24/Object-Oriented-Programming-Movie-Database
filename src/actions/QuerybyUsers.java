package actions;

import data.Action;
import data.User;
import database.Find;

import java.util.List;

/**
 * Clasa care efectueaza un query dupa utilizatori
 */
class QuerybyUsers {
    QuerybyUsers() {
    }

    String perform(final Action action) {
        String crit = action.getCriteria();
        String mess;

        switch (crit) {
            case "num_ratings":
                mess = nrRatings(action.getNumber(), action.getSortType());
                break;

            default:
                return "query on users on criteria " + crit + " not found";
        }

        return mess;
    }

    private String nrRatings(final int nrUsers, final String sortType) {
        List<User> result = Find
                                .getFinder()
                                .getUsersSortedByNrRatings(sortType);

        int firstIdx0 = Find
                            .getFinder()
                            .getFirstIndexOf0RatingUsers(result);

        int startIdx = Find
                            .getFinder()
                            .getFirstIndexOfNon0RatingUsers(result);

        if (sortType.equals("asc")) {
            return buildAsc(result, nrUsers, startIdx);
        } else {
            return buildDesc(result, nrUsers, firstIdx0);
        }
    }

    /**
     * @param startIdx primul index din lista cu valoarea nenula
     * Returneaza o lista contine primele nrShows obiecte de tip User,
     * cu conditia ca nrUsers sa fie mai mare decat size-ul listei
     */
    private String buildAsc(final List<User> users,
                                  final int nrUsers,
                                  final int startIdx) {
        StringBuilder stringBuilder = new StringBuilder();
        int nrUserscopy = nrUsers;
        int startIdxcopy = startIdx;

        if (startIdxcopy == 0 || startIdxcopy >= users.size()) {
            return "]";
        }

        if (nrUserscopy > users.size()) {
            nrUserscopy = users.size();
        }

        for (int i = 0; i < nrUserscopy; i++) {
            if (startIdxcopy == users.size() - 1 || i == nrUserscopy - 1) {
                stringBuilder.append(users.get(startIdxcopy).getUsername() + "]");
                break;
            }

            stringBuilder.append(users.get(startIdxcopy).getUsername() + ", ");
            startIdxcopy++;
        }

        if (stringBuilder.toString().equals("")) {
            stringBuilder.append("]");
        }

        return stringBuilder.toString();
    }



    /**
     * @param firstIdx0 primul index din lista cu valoarea 0
     * Returneaza o lista care contine primele nrShows obiecte de tip User,
     * cu conditia ca nrShows - 1 sa fie mai mare decat firstIdx0
     */
    private String buildDesc(final List<User> users,
                                   final int nrUsers,
                                   final int firstIdx0) {
        StringBuilder stringBuilder = new StringBuilder();
        int nrUserscopy = nrUsers;

        if (firstIdx0 == 0) {
            return "]";
        }

        if (nrUsers > users.size()) {
            nrUserscopy = users.size();
        }

        for (int i = 0; i < nrUserscopy; i++) {
            if (i == nrUserscopy - 1 || i == firstIdx0 - 1) {
                stringBuilder.append(users.get(i).getUsername() + "]");
                break;
            }

            stringBuilder.append(users.get(i).getUsername() + ", ");
        }

        if (stringBuilder.toString().equals("")) {
            stringBuilder.append("]");
        }

        return stringBuilder.toString();
    }

}
