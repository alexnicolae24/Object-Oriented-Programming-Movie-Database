package data;

import java.util.List;

/**
 * Informatii despre input
 */
public final class Input {
    /**
     * Lista de actori
     */
    private final List<Actor> actorsData;
    /**
     * Lista de utilizatori
     */
    private final List<User> usersData;
    /**
     * Lista de comenzi
     */
    private final List<Action> commandsData;
    /**
     * Lista de filme
     */
    private final List<Movie> moviesData;
    /**
     * Lista de seriale
     */
    private final List<Serial> serialsData;

    public Input() {
        this.actorsData = null;
        this.usersData = null;
        this.commandsData = null;
        this.moviesData = null;
        this.serialsData = null;
    }

    public Input(final List<Actor> actors, final List<User> users,
                 final List<Action> commands,
                 final List<Movie> movies,
                 final List<Serial> serials) {
        this.actorsData = actors;
        this.usersData = users;
        this.commandsData = commands;
        this.moviesData = movies;
        this.serialsData = serials;
    }

    public List<Actor> getActors() {
        return actorsData;
    }

    public List<User> getUsers() {
        return usersData;
    }

    public List<Action> getCommands() {
        return commandsData;
    }

    public List<Movie> getMovies() {
        return moviesData;
    }

    public List<Serial> getSerials() {
        return serialsData;
    }
}
