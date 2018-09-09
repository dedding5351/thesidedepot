package thesidedepot.app.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import thesidedepot.app.model.Build;
import thesidedepot.app.model.User;

public class UserManager {
    User curUser;

    @SuppressWarnings("ConstantConditions")
    public User getData() {
        return curUser;
    }

    public void setUser(User curUser) {
        this.curUser = curUser;
    }

    public boolean logInUser(String email, String pass) {
        //scripting.loginUser(email, pass);
        User newUser = new User(email, pass, true, new ArrayList<Build>());
        if (true) {
            setUser(newUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean registerUser(String email, String pass) {
        User newUser = new User(email, pass, true, new ArrayList<Build>());
        if (scripting.createUser(email, pass)) {
            setUser(newUser);
            return true;
        } else {
            return false;
        }
    }

    public Map<String, Boolean> getBadges() {
        return curUser.get_badges();
    }

    public void setBadge(String key) {
        curUser.set_badge(key);
    }

    public List<Build> getBuildList() {
        return curUser.get_buildList();
    }

    public void setBuildList(ArrayList<Build> builds) {
        curUser.set_buildList(builds);
    }
}
