package thesidedepot.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class User implements Serializable {
    private String _username;
    private String _pWord;
    private boolean _loggedIn;
    private ArrayList<Build> _buildList;
    private Map<String, Boolean> _badges;

    public Map<String, Boolean> get_badges() {
        return _badges;
    }

    public void set_badges(Map<String, Boolean> _badges) {
        this._badges = _badges;
    }

    public void set_badge(String key) {
        this._badges.put(key, true);
    }

    public ArrayList<Build> get_buildList() {
        return _buildList;
    }

    public void set_buildList(ArrayList<Build> _buildList) {
        this._buildList = _buildList;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_pWord() {
        return _pWord;
    }

    public void set_pWord(String _pWord) {
        this._pWord = _pWord;
    }

    public boolean is_loggedIn() {
        return _loggedIn;
    }

    public void set_loggedIn(boolean _loggedIn) {
        this._loggedIn = _loggedIn;
    }

    public User(String _username, String _pWord, boolean _loggedIn, ArrayList<Build> _buildList) {
        this._username = _username;
        this._pWord = _pWord;
        this._loggedIn = _loggedIn;
        this._buildList = _buildList;
        this._badges = new HashMap<String, Boolean>();
        _badges.put("HoReB", false);
        _badges.put("HoReS", false);
        _badges.put("HoReG", false);
        _badges.put("DIYB", false);
        _badges.put("DIYS", false);
        _badges.put("DIYG", false);
        _badges.put("LGOB", false);
        _badges.put("LGOS", false);
        _badges.put("LGOG", false);
        _badges.put("HoMaB", false);
        _badges.put("HoMaS", false);
        _badges.put("HoMaG", false);
    }

    @SuppressWarnings("unused")
    public User() {}


    @Override
    public String toString() {
        return "User{" +
                ", _username='" + _username + '\'' +
                ", _pWord='" + _pWord + '\'' +
                ", _loggedIn=" + _loggedIn +
                ", _buildList=" + _buildList +
                ", _badges=" + _badges +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        boolean ret = false;
        if (o instanceof User) {
            User ptr = (User) o;
            ret = ptr.get_username().equals(this._username);
        }
        return ret;
    }
}
