package thesidedepot.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import thesidedepot.app.data.UserManager;

public class Model {
    private static Model _instance;
    final private UserManager um;

    private Model() {
        um = new UserManager();
    }

    public static synchronized Model getInstance() {
        if (_instance == null) {
            _instance = new Model();
        }
        return _instance;
    }

//    public User getUser() {
//        return um.getData();
//    }
//
//    public void setUser(User user) {
//        um.setUser(user);
//    }

    public boolean logIn(String email, String pass) {
        return um.logInUser(email, pass);
    }

    public boolean signUp(String email, String pass) {
        return um.registerUser(email, pass);
    }

    public Map<String, Boolean> getBadges() {
        return um.getBadges();
    }

    public void setBadge(String key) {
        um.setBadge(key);
    }

    public List<Build> getBuildList() {
        return um.getBuildList();
    }

    public void setBuildList(ArrayList<Build> builds) {
        um.setBuildList(builds);
    }
}
