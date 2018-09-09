package thesidedepot.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Build implements Serializable {
    private String _name;
    private String _desc;
    private int hours;
    private ArrayList<String> steps;
    private ArrayList<String> materials;
    private boolean _done;

    public Build(String _name, String _desc, int hours, ArrayList<String> steps, ArrayList<String> materials, boolean _done) {
        this._name = _name;
        this._desc = _desc;
        this.hours = hours;
        this.steps = steps;
        this._done = _done;
        this.materials = materials;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public boolean is_done() {
        return _done;
    }

    public void set_done(boolean _done) {
        this._done = _done;
    }

    public ArrayList<String> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<String> materials) {
        this.materials = materials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Build build = (Build) o;
        return ((Build) o).get_name().equals(this._name);
    }

    @Override
    public String toString() {
        return "Build{" +
                "_name='" + _name + '\'' +
                ", _desc='" + _desc + '\'' +
                ", hours=" + hours +
                ", steps=" + steps +
                ", _done=" + _done +
                ", materials=" + materials +
                '}';
    }
}
