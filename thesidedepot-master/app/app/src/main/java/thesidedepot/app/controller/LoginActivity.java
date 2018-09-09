package thesidedepot.app.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.TextView;
import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import thesidedepot.app.R;
import thesidedepot.app.model.Model;
import thesidedepot.app.model.User;

import static thesidedepot.app.controller.MainActivity.currentUser;

public class LoginActivity extends AppCompatActivity {
    Model model = Model.getInstance();
    EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CardView login;
        TextView signup;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final LoginActivity here = this;

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snack = Snackbar.make(findViewById(R.id.loginScreen), "logging you in...", Snackbar.LENGTH_LONG);
                System.out.println("logged in");
                snack.show();
                if (isEmailValid(email.getText().toString()) && model.logIn(email.getText().toString(), pass.getText().toString())) {
                    //new loginUser(here).execute("https://godhelpusall.com");
                    new loginUser(here).execute("https://sidedepot.herokuapp.com/users/login");
                } else {
                    snack = Snackbar.make(findViewById(R.id.loginScreen), "Something went wrong!", Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snack = Snackbar.make(findViewById(R.id.loginScreen), "signing you up...", Snackbar.LENGTH_LONG);
                System.out.println("signed up");
                snack.show();
                if (isEmailValid(email.getText().toString())) {
                    //new registerUser(here).execute("https://time4cook.herokuapp.com/users/signup");
                    new createUser(here).execute("https://sidedepot.herokuapp.com/users");
                } else {
                    snack = Snackbar.make(findViewById(R.id.loginScreen), "Something went wrong!", Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }
        });

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public class createUser extends AsyncTask<String, String, String> {

        private LoginActivity parent;

        public createUser(LoginActivity parent) {
            this.parent = parent;
        }
        //New json object (email field, password)
        @Override
        protected String doInBackground(String... params) {
            try {
                URL obj = new URL(params[0]);
                String data = "{username : " + email.getText().toString() +", password: " + pass.getText().toString() + ", badges: " + new ArrayList<String>() + ", projects: " + new ArrayList<String>() + "}";

                JSONObject jsonData = new JSONObject(data);
                //Log.d("JSONTest", jsonData.toString());
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setDoOutput(true);
                con.setInstanceFollowRedirects(false);
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
                Log.d("JSONTest", jsonData.toString());
                outputStreamWriter.write(jsonData.toString());
                outputStreamWriter.flush();
                con.setRequestMethod("POST");
                int responseCode = con.getResponseCode();

                if (responseCode == 200) {
                    //new MainActivity.updateUser().execute("https://sidedepot.herokuapp.com/users/" + email.getText().toString());

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //JSONObject myResponse = new JSONObject(response.toString());
                    //System.out.println(myResponse.getString("result"));

//                    SharedPreferences preferences = LoginActivity.this.getSharedPreferences("CookingToken",MODE_PRIVATE);
//                    SharedPreferences.Editor editor;
//                    editor = preferences.edit();
//                    editor.putString("JWT Token", myResponse.getString("token"));
//                    editor.commit();

                    //TO RETRIEVE STORED TOKEN: preferences.getString("CookingToken", "No Token");

                    Intent i  = new Intent(LoginActivity.this, MainActivity.class);
                    //i.putExtra("recipeList", recipeList);
                    startActivity(i);
                    finish();
                } else {

                    parent.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(parent.getBaseContext(), "Account already exists", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //System.out.println("\nSending 'GET' request to URL : " + url);

                //Log.d("JSONTest", jsonData.toString());

            } catch (Exception exception) {
                Log.d("hi", exception.toString());
            }
            return null;
        }
    }

    public class loginUser extends AsyncTask<String, String, String> {

        private LoginActivity parent;

        public loginUser(LoginActivity parent) {
            this.parent = parent;
        }

        //New json object (email field, password)
        @Override
        protected String doInBackground(String... params) {
            try {
                URL obj = new URL(params[0]);
                String data = "{username : " + email.getText().toString() + ", password: " + pass.getText().toString() + "}";

                JSONObject jsonData = new JSONObject(data);
                //Log.d("JSONTest", jsonData.toString());
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setDoOutput(true);
                con.setInstanceFollowRedirects(false);
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
                Log.d("JSONTest", jsonData.toString());
                outputStreamWriter.write(jsonData.toString());
                outputStreamWriter.flush();
                con.setRequestMethod("POST");
                int responseCode = con.getResponseCode();

                if (responseCode == 200) {
                    //new MainActivity.updateUser().execute("https://sidedepot.herokuapp.com/users/" + email.getText().toString());

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //JSONObject myResponse = new JSONObject(response.toString());
                    //System.out.println(myResponse.getString("token"));

//                    SharedPreferences preferences = LoginActivity.this.getSharedPreferences("CookingToken",MODE_PRIVATE);
//                    SharedPreferences.Editor editor;
//                    editor = preferences.edit();
//                    editor.putString("JWT Token", myResponse.getString("token"));
//                    editor.commit();

                    //TO RETRIEVE STORED TOKEN: preferences.getString("CookingToken", "No Token");

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("currUser", email.getText().toString());
                    startActivity(i);
                    finish();
                } else {

                    parent.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(parent.getBaseContext(), "Login Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //System.out.println("\nSending 'GET' request to URL : " + url);

                //Log.d("JSONTest", jsonData.toString());

            } catch (Exception exception) {
                Log.d("hi", exception.toString());
            }
            return null;
        }
    }
}
