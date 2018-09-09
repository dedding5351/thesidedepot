package thesidedepot.app.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {
    private String title;
    private String description;
    private String difficulty;
    private String category;
    private ArrayList<String> toolsAndMaterials;
    private String time;
    private String image;
    private double priceEstimate;
    private ArrayList<String> parsedSteps;
    private ArrayList<String> parsedHeaders;
    private ArrayList<String> webCollection;


    public Project(String title, String description, String difficulty, String category, ArrayList<String> toolsAndMaterials, String time, String image, double priceEstimate, ArrayList<String> parsedSteps, ArrayList<String> parsedHeaders, ArrayList<String> webCollection) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;
        this.toolsAndMaterials = toolsAndMaterials;
        this.time = time;
        this.image = image;
        this.priceEstimate = priceEstimate;
        this.parsedSteps = parsedSteps;
        this.parsedHeaders = parsedHeaders;
        this.webCollection = webCollection;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", category='" + category + '\'' +
                ", toolsAndMaterials=" + toolsAndMaterials +
                ", time='" + time + '\'' +
                ", image='" + image + '\'' +
                ", priceEstimate=" + priceEstimate +
                ", parsedSteps=" + parsedSteps +
                ", parsedHeaders=" + parsedHeaders +
                ", webCollection=" + webCollection +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getToolsAndMaterials() {
        return toolsAndMaterials;
    }

    public void setToolsAndMaterials(ArrayList<String> toolsAndMaterials) {
        this.toolsAndMaterials = toolsAndMaterials;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPriceEstimate() {
        return priceEstimate;
    }

    public void setPriceEstimate(double priceEstimate) {
        this.priceEstimate = priceEstimate;
    }

    public ArrayList<String> getParsedSteps() {
        return parsedSteps;
    }

    public void setParsedSteps(ArrayList<String> parsedSteps) {
        this.parsedSteps = parsedSteps;
    }

    public ArrayList<String> getParsedHeaders() {
        return parsedHeaders;
    }

    public void setParsedHeaders(ArrayList<String> parsedHeaders) {
        this.parsedHeaders = parsedHeaders;
    }

    public ArrayList<String> getWebCollection() {
        return webCollection;
    }

    public void setWebCollection(ArrayList<String> webCollection) {
        this.webCollection = webCollection;
    }
}
