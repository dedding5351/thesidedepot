package thesidedepot.app.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import thesidedepot.app.model.Project;


public  class scripting {








    public void databasePull() {
        ArrayList<Project> holder = new ArrayList<>();
        //MongoClientURI uri = new MongoClientURI("mongodb+srv://admin:siderift@cluster0-1jnpy.mongodb.net/test?retryWrites=true");

        MongoClientURI connectionString = new MongoClientURI("mongodb+srv://admin:siderift@cluster0-1jnpy.mongodb.net/test?retryWrites=true");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("database");
        MongoCollection<Document> collection = database.getCollection("projectsFinalA");




        for (Document cursor : collection.find()) {
            Project proj = new Project((String) cursor.get("title"),(String) cursor.get("description"), (String)cursor.get("difficulty"),
                    (String)cursor.get("category"),(ArrayList<String>) cursor.get("toolsAndMaterials"), (String)cursor.get("time"),(String) cursor.get("image"),
                    (Double) cursor.get("priceEstimate"),(ArrayList<String>) cursor.get("parsedSteps"),(ArrayList<String>) cursor.get("parsedHeaders"), (ArrayList<String>) cursor.get("weblinks"));
            holder.add(proj);
        }

        for (Project item : holder) {
            System.out.println(item.toString());
        }

        System.out.println(holder.size());
    }

    public static boolean createUser(String username, String password) {
        MongoClientURI connectionString = new MongoClientURI("mongodb+srv://admin:siderift@cluster0-1jnpy.mongodb.net/test?retryWrites=true");
        MongoClient mongoClient = new MongoClient(connectionString);
        DB database = mongoClient.getDB("database");
        DBCollection collection = database.getCollection("userNewA");
        BasicDBObject queryObj = new BasicDBObject();
        queryObj.put("username", username);
        DBCursor results = collection.find(queryObj);


        if (results.size() == 0) {
            BasicDBObject document = new BasicDBObject();
            document.put("username", username);
            document.put("password", password);
            document.put("badges", new ArrayList<String>());
            document.put("projects", new ArrayList<String>());
            document.put("firstLogin", true);
            collection.insert(document);
            System.out.println("user made");
            return true;
        } else {
            System.out.println("user exists");
            while(results.hasNext()) {
                System.out.println(results.next());
            }
            return false;
        }
    }

    public static boolean loginUser(String username, String password) {
        MongoClientURI connectionString = new MongoClientURI("mongodb+srv://admin:siderift@cluster0-1jnpy.mongodb.net/test?retryWrites=true");
        MongoClient mongoClient = new MongoClient(connectionString);
        DB database = mongoClient.getDB("database");
        DBCollection collection = database.getCollection("userNewA");
        BasicDBObject queryObj = new BasicDBObject();
        queryObj.put("username", username);
        DBCursor results = collection.find(queryObj);

        if (results.size() == 0) {
            System.out.println("User does not exist");
            return false;
        } else {
            while(results.hasNext()) {

                BasicDBObject currDoc = (BasicDBObject) results.next();

                if (password.equals(currDoc.get("password"))) {
                    System.out.println("valid login");

                    //Set first login to FALSE
                    if ((boolean) (currDoc.get("firstLogin"))) {
                        System.out.println("firstLogin is true");

                        BasicDBObject newDocument = new BasicDBObject();
                        newDocument.put("firstLogin", false);

                        BasicDBObject updateObject = new BasicDBObject();
                        updateObject.put("$set", newDocument);

                        collection.update(queryObj, updateObject);

                        return true;

                    } else {
                        System.out.println("firstLogin is false");
                        return true;
                    }
                } else {
                    System.out.println("bad password");
                    return false;
                }
            }

            return false;
        }
    }

    public static void generateProjects(String[] categories) {

        MongoClientURI connectionString = new MongoClientURI("mongodb+srv://admin:siderift@cluster0-1jnpy.mongodb.net/test?retryWrites=true");
        MongoClient mongoClient = new MongoClient(connectionString);
        DB database = mongoClient.getDB("database");
        DBCollection collection = database.getCollection("projectsFinalA");
        BasicDBObject queryObj = new BasicDBObject();

        ArrayList<Project> possibleProjects = new ArrayList<>();
//        ArrayList<Project> possibleProjectsIntermediate = new ArrayList<>();
//        ArrayList<Project> possibleProjectsAdvanced = new ArrayList<>();

        for (String category: categories) {
            queryObj.put("category", category);
            DBCursor results = collection.find(queryObj);
            //System.out.println(results.size());
            while (results.hasNext()) {
                BasicDBObject currDoc = (BasicDBObject) results.next();
                Project proj = new Project((String) currDoc.get("title"),(String) currDoc.get("description"), (String)currDoc.get("difficulty"),
                        (String)currDoc.get("category"),(ArrayList<String>) currDoc.get("toolsAndMaterials"), (String)currDoc.get("time"),(String) currDoc.get("image"),
                        (Double) currDoc.get("priceEstimate"),(ArrayList<String>) currDoc.get("parsedSteps"),(ArrayList<String>) currDoc.get("parsedHeaders"), (ArrayList<String>) currDoc.get("weblinks"));
                possibleProjects.add(proj);
            }
            //System.out.println(possibleProjects.size());
        }

        ArrayList<Project> easy = new ArrayList<>();
        ArrayList<Project> intermediate = new ArrayList<>();
        ArrayList<Project> advanced = new ArrayList<>();

        for (Project proj : possibleProjects) {
            if (proj.getDifficulty().equals("Easy")) {
                easy.add(proj);
            } else if (proj.getDifficulty().equals("Intermediate")) {
                intermediate.add(proj);
            } else {
                advanced.add(proj);
            }
        }

        Collections.shuffle(easy);
        Collections.shuffle(intermediate);
        Collections.shuffle(advanced);

        ArrayList<Project> suggestList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            suggestList.add(easy.get(i));
        }
        for (int i = 0; i < 3; i++) {
            suggestList.add(intermediate.get(i));
        }
        for (int i = 0; i < 3; i++) {
            suggestList.add(advanced.get(i));
        }

        for (Project proj : suggestList) {
            System.out.println(proj.getDifficulty() + " " + proj.getTitle() + " " + proj.getCategory());
        }




    }

    public static void main(String[] args) {

        //createUser("admin", "password");
        //loginUser("admin", "password");
        //generateProjects(new String[]{"Home Renovation", "DIY, Decor & Fun", "Lawn, Garden, & Outdoor"});



    }




}
