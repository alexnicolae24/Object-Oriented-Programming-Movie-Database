package database;

import entertainment.ShowType;
import data.Actor;
import data.Show;
import data.User;

import java.util.Comparator;

/**
 * Singleton care retine diferite tipuri de comparatori care vor ajuta la
 * implementarea pachetului de actiuni
 */
public final class Comp {
    private static final Comp COMP;

    static {
        COMP = new Comp();
    }

    private static final Comparator<Actor> AVG_DESC = (o1, o2) -> {
        Double average1 = o1.getAverage();
        Double average2 = o2.getAverage();

        if (average1 > average2) {
            return -1;
        } else if (average1 < average2) {
            return 1;
        }

        return o2.getName().compareTo(o1.getName());
    };

    private static final Comparator<Actor> AVG_ASC = (o1, o2) -> {
        Double average1 = o1.getAverage();
        Double average2 = o2.getAverage();

        if (average1 > average2) {
            return 1;
        } else if (average1 < average2) {
            return -1;
        }

        return o1.getName().compareTo(o2.getName());
    };

    private static final Comparator<Actor> AWARDS_DESC = (o1, o2) -> {
        int awards1 = o1.getnrAwards();
        int awards2 = o2.getnrAwards();

        if (awards1 > awards2) {
            return -1;
        } else if (awards1 < awards2) {
            return 1;
        }

        return o2.getName().compareTo(o1.getName());
    };

    private static final Comparator<Actor> AWARDS_ASC = (o1, o2) -> {
        int awards1 = o1.getnrAwards();
        int awards2 = o2.getnrAwards();

        if (awards1 > awards2) {
            return 1;
        } else if (awards1 < awards2) {
            return -1;
        }

        return o1.getName().compareTo(o2.getName());
    };

    private static final Comparator<Show> SHOW_RATDESC = (o1, o2) -> {
        Double rating1 = o1.getAvg();
        Double rating2 = o2.getAvg();

        if (rating1 > rating2) {
            return -1;
        } else if (rating1 < rating2) {
            return 1;
        }

        return o2.getTitle().compareTo(o1.getTitle());
    };

    private static final Comparator<Show> SHOW_RATASC = (o1, o2) -> {
        Double rating1 = o1.getAvg();
        Double rating2 = o2.getAvg();

        if (rating1 > rating2) {
            return 1;
        } else if (rating1 < rating2) {
            return -1;
        }

        return o1.getTitle().compareTo(o2.getTitle());
    };



    private static final Comparator<Show> SHOW_RATING_DESC_ORDER = (o1, o2) -> {
        Double rating1 = o1.getAvg();
        Double rating2 = o2.getAvg();

        if (rating1 > rating2) {
            return -1;
        } else if (rating1 < rating2) {
            return 1;
        }

        int index1 = Find.getFinder().getIdxOfShowInD(o1);
        int index2 = Find.getFinder().getIdxOfShowInD(o2);

        if (index1 > index2) {
            return 1;
        } else if (index1 < index2) {
            return -1;
        }

        return 0;
    };

    private static final Comparator<Show> SHOW_RATING_ASC_ORDER = (o1, o2) -> {
        Double rating1 = o1.getAvg();
        Double rating2 = o2.getAvg();

        if (rating1 > rating2) {
            return 1;
        } else if (rating1 < rating2) {
            return -1;
        }

        int index1 = Find.getFinder().getIdxOfShowInD(o1);
        int index2 = Find.getFinder().getIdxOfShowInD(o2);

        if (index1 > index2) {
            return 1;
        } else if (index1 < index2) {
            return -1;
        }

        return 0;
    };

    private static final Comparator<Show> SHOW_FAVORITE_DESC_ORDER = (o1, o2) -> {
        int favAdds1 = o1.getFavoriteAdds();
        int favAdds2 = o2.getFavoriteAdds();

        if (favAdds1 > favAdds2) {
            return -1;
        } else if (favAdds1 < favAdds2) {
            return 1;
        }

        int index1 = Find.getFinder().getIdxOfShowInD(o1);
        int index2 = Find.getFinder().getIdxOfShowInD(o2);

        if (index1 > index2) {
            return 1;
        } else if (index1 < index2) {
            return -1;
        }

        return 0;
    };

    private static final Comparator<Show> SHOW_FAVORITE_ASC = (o1, o2) -> {
        int favAdds1 = o1.getFavoriteAdds();
        int favAdds2 = o2.getFavoriteAdds();

        if (favAdds1 > favAdds2) {
            return 1;
        } else if (favAdds1 < favAdds2) {
            return -1;
        }

        return o1.getTitle().compareTo(o2.getTitle());
    };

    private static final Comparator<Show> SHOW_FAVORITE_DESC = (o1, o2) -> {
        int favAdds1 = o1.getFavoriteAdds();
        int favAdds2 = o2.getFavoriteAdds();

        if (favAdds1 > favAdds2) {
            return -1;
        } else if (favAdds1 < favAdds2) {
            return 1;
        }

        return o2.getTitle().compareTo(o1.getTitle());
    };

    private static final Comparator<Show> SHOW_DURATION_ASC = (o1, o2) -> {
        int duration1 = o1.getDuration();
        int duration2 = o2.getDuration();

        if (duration1 > duration2) {
            return 1;
        } else if (duration1 < duration2) {
            return -1;
        }

        return o1.getTitle().compareTo(o2.getTitle());
    };

    private static final Comparator<Show> SHOW_DURATION_DESC = (o1, o2) -> {
        int duration1 = o1.getDuration();
        int duration2 = o2.getDuration();

        if (duration1 > duration2) {
            return -1;
        } else if (duration1 < duration2) {
            return 1;
        }

        return o2.getTitle().compareTo(o1.getTitle());
    };

    private static final Comparator<Show> SHOW_VIEWSASC = (o1, o2) -> {
        int views1 = o1.getNrViews();
        int views2 = o2.getNrViews();

        if (views1 > views2) {
            return 1;
        } else if (views1 < views2) {
            return -1;
        }

        return o1.getTitle().compareTo(o2.getTitle());
    };

    private static final Comparator<Show> SHOW_VIEWSDESC = (o1, o2) -> {
        int views1 = o1.getNrViews();
        int views2 = o2.getNrViews();

        if (views1 > views2) {
            return -1;
        } else if (views1 < views2) {
            return 1;
        }

        return o2.getTitle().compareTo(o1.getTitle());
    };

    private static final Comparator<User> USER_RATINGS_ASC = (o1, o2) -> {
        int user1 = o1.getNrRatingsGiven();
        int user2 = o2.getNrRatingsGiven();

        if (user1 > user2) {
            return 1;
        } else if (user1 < user2) {
            return -1;
        }

        return o1.getUsername().compareTo(o2.getUsername());
    };

    private static final Comparator<User> USER_RATINGS_DESC = (o1, o2) -> {
        int user1 = o1.getNrRatingsGiven();
        int user2 = o2.getNrRatingsGiven();

        if (user1 > user2) {
            return -1;
        } else if (user1 < user2) {
            return 1;
        }

        return o2.getUsername().compareTo(o1.getUsername());
    };

    private static final Comparator<ShowType> SHOWTYPE_VIEWS_DESC = (o1, o2) -> {
        int views1 = o1.getNumOfViews();
        int views2 = o2.getNumOfViews();

        if (views1 > views2) {
            return -1;
        } else if (views1 < views2) {
            return 1;
        }

        return 0;
    };

    private static final Comparator<Actor> ACTOR_NAME_DESC = (o1, o2) -> {
        return o2.getName().compareTo(o1.getName());
    };

    private static final Comparator<Actor> ACTOR_NAME_ASC = (o1, o2) -> {
        return o1.getName().compareTo(o2.getName());
    };

    private Comp() {
    }

    public static Comp getInstance() {
        return COMP;
    }

    public Comparator<Actor> getAvgDesc() {
        return AVG_DESC;
    }

    public Comparator<Actor> getAvgAsc() {
        return AVG_ASC;
    }

    public Comparator<Actor> getAwardsDesc() {
        return AWARDS_DESC;
    }

    public Comparator<Actor> getAwardsAsc() {
        return AWARDS_ASC;
    }

    public Comparator<Actor> getActorNameDesc() {
        return ACTOR_NAME_DESC;
    }

    public Comparator<Actor> getActorNameAsc() {
        return ACTOR_NAME_ASC;
    }

    public Comparator<Show> getShowRatingDesc() {
        return SHOW_RATDESC;
    }

    public Comparator<Show> getShowRatingAsc() {
        return SHOW_RATASC;
    }

    public Comparator<Show> getShowFavoriteAsc() {
        return SHOW_FAVORITE_ASC;
    }

    public Comparator<Show> getShowFavoriteDesc() {
        return SHOW_FAVORITE_DESC;
    }

    public Comparator<Show> getShowDurationAsc() {
        return SHOW_DURATION_ASC;
    }

    public Comparator<Show> getShowDurationDesc() {
        return SHOW_DURATION_DESC;
    }

    public Comparator<Show> getShowViewsAsc() {
        return SHOW_VIEWSASC;
    }

    public Comparator<Show> getShowViewsDesc() {
        return SHOW_VIEWSDESC;
    }

    public Comparator<User> getUserRatingsAsc() {
        return USER_RATINGS_ASC;
    }

    public Comparator<User> getUserRatingsDesc() {
        return USER_RATINGS_DESC;
    }

    public Comparator<Show> getShowRatingDescOrder() {
        return SHOW_RATING_DESC_ORDER;
    }

    public Comparator<Show> getShowRatingAscOrder() {
        return SHOW_RATING_ASC_ORDER;
    }

    public Comparator<ShowType> getGenViewsDesc() {
        return SHOWTYPE_VIEWS_DESC;
    }

    public Comparator<Show> getShowFavoriteDescOrder() {
        return SHOW_FAVORITE_DESC_ORDER;
    }
}
