package actions;

import data.Action;
import data.Input;
import data.User;
import data.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Aceasta clasa executa actiuni pentru un input dat si rezultatul este
 * retinut ca JSONObject in arrayResult.
 */
public final class ActionsDoer {
    private final Input input;
    private final Writer fileWriter;
    private final JSONArray arrayResult;

    public ActionsDoer(final Input input, final Writer fileWriter,
                       final JSONArray arrayResult) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;
    }

    /**
     * Rezultatul fiecarei actiuni va fi stocat in arrayResult
     */
    public void perform() throws IOException {
        updateUserdata();

        CommandDoer commandDoer = CommandDoer.getInstance();
        QueryDoer queryDoer = QueryDoer.getInstance();
        RecommendationDoer recommendationDoer = RecommendationDoer.getInstance();

        for (Action currAction : this.input.getCommands()) {
            int actionID = currAction.getActionId();
            String mess = null;

            if (currAction.getActionType().equals("command")) {
                mess = commandDoer.perform(currAction);
            } else {
                if (currAction.getActionType().equals("query")) {
                    mess = queryDoer.perform(currAction);
                } else {
                    if (currAction.getActionType().equals("recommendation")) {
                        mess = recommendationDoer.perform(currAction);
                    }
                }
            }

            JSONObject resJSON = this.fileWriter.writeFile(actionID, " ", mess);

            this.arrayResult.add(resJSON);
        }
    }

    /**
     * Actualizeaza metodele userilor
     */
    private void updateUserdata() {
        List<User> users = this.input.getUsers();
        for (User currUser : users) {
            currUser.updateFav();
            currUser.updateViews();
            currUser.updateShowTypeViews();
        }
    }
}
