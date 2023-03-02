package data;

import actor.ActorsAwards;
import database.Find;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Informatii despre un actor
 */
public final class Actor {
    /**
     * Numele actorului
     */
    private String name;
    /**
     * Descrierea carierei unui actor
     */
    private String careerDescription;
    /**
     * Show-urile in care joaca actorul
     */
    private ArrayList<String> filmography;
    /**
     * premiile castigate de un actor
     */
    private Map<ActorsAwards, Integer> awards;
    /**
     * Rating-ul mediu al tuturor filmelor in care a jucat actorul
     */
    private Double avgMovieRat;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.avgMovieRat = 0.0;
    }

    private void calcavg() {
        Double sumRat = 0.0;
        int nr = 0;

        if (this.filmography.size() == 0) {
            return;
        }

        for (String currShowName : this.filmography) {
            Show show = Find.getFinder().getShowAfterName(currShowName);
            if (show == null) {
                continue;
            }

            Double showAvg = show.getAvg();
            if (showAvg == 0.0) {
                continue;
            }

            sumRat += showAvg;
            nr++;
        }

        if (nr == 0) {
            return;
        }

        this.avgMovieRat = sumRat / (double) nr;
    }

    /**
     * Returneaza true daca textul contine subsirul "inFrontwordbehind"
     */
    private boolean checkOcc(final String inFront, final String word,
                                   final String txt, final String behind) {
        StringBuilder s = new StringBuilder();
        s.append(inFront);
        s.append(word);
        s.append(behind);

        if (txt.contains(s.toString())) {
            return true;
        }

        return false;
    }

    /**
     * Returneaza media actualizata a tutror filmelor si serialelor in care actorul a jucat
     */
    public Double getAverage() {
        calcavg();
        return avgMovieRat;
    }

    /**
     * Returneaza numarul de premii castigate de actor
     */
    public int getnrAwards() {
        int nrAwards = 0;

        for (ActorsAwards award : this.awards.keySet()) {
            nrAwards += this.awards.get(award);
        }

        return nrAwards;
    }

    /**
     * Returneaza true daca actorul a castigat toate premiile primite ca parametru
     */
    public boolean hasAllAwards(final List<String> awardList) {
        if (awardList == null) {
            return false;
        }

        for (String currAward : awardList) {
            int found = 0;

            for (ActorsAwards actorsAward : this.awards.keySet()) {
                if (actorsAward.toString().equals(currAward)) {
                    found = 1;
                }
            }

            if (found == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     *  Returneaza true daca actorul are toate cuvintele primite ca parametru in careerDescription
     */
    public boolean descriptionHasKeywords(final List<String> descr) {
        String txt = this.careerDescription.toLowerCase();
        boolean hasWrd = true;

        for (String wrd : descr) {
            wrd = wrd.toLowerCase();

            if (checkOcc(" ", wrd, txt, " ")
                || checkOcc("-", wrd, txt, " ")
                || checkOcc(",", wrd, txt, " ")
                || checkOcc(",", wrd, txt, " ")
                || checkOcc(" ", wrd, txt, ".")
                || checkOcc(" ", wrd, txt, ",")) {

                continue;
            }

            hasWrd = false;
        }

        if (!hasWrd) {
            return false;
        }

        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) { this.name = name; }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
