package actions;

import data.Action;

/**
 * Singleton care executa o actiune de tip QUERY
 */
final class QueryDoer {
    private static final QueryDoer QUERY_DOER;

    private final QuerybyActors actorsDoer;
    private final QuerybyShows showsDoer;
    private final QuerybyUsers usersDoer;

    static {
        QUERY_DOER = new QueryDoer();
    }

    private QueryDoer() {
        this.actorsDoer = new QuerybyActors();
        this.showsDoer = new QuerybyShows();
        this.usersDoer = new QuerybyUsers();
    }

    public static QueryDoer getInstance() {
        return QUERY_DOER;
    }

    /**
     * Identifica tipul de Query si executa metoda corespunzatoare
     */
    String perform(final Action action) {
        String queryObj = action.getObjectType();
        String mess = "Query result: [";

        switch (queryObj) {
            case "actors":
                mess += this.actorsDoer.perform(action);
                break;

            case "movies": case "shows":
                mess += this.showsDoer.perform(action);
                break;

            case "users":
                mess += this.usersDoer.perform(action);
                break;

            default:
                return "query on object of type " + queryObj + " not found";
        }

        return mess;
    }
}
