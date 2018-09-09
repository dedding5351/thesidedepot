package thesidedepot.app.controller;
//calendar
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import thesidedepot.app.R;
import thesidedepot.app.model.Project;
import thesidedepot.app.model.Build;
import thesidedepot.app.model.Model;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "";
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private TextView month;
    private ImageButton monthForward, monthBack;

    public static String currentUser;
    public static HashMap<String, Project> projectList = new HashMap<>();
    public static ArrayList<Project> myProjectList = new ArrayList<>();
    public static ArrayList<String> badges = new ArrayList<>();
    public static int totalDone = 0;
    public static int DIYDone = 0;
    public static int RenDone = 0;
    public static int MainDone = 0;
    public static int OutDone = 0;
    public static int projIndex = 0;
    public static boolean firstLoad = true;

    private Button currentProj;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = Model.getInstance();
//        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 10); // see this max value coming back here, we animate towards that value
//        //animation.setDuration(5000); // in milliseconds
//        //animation.setInterpolator(new DecelerateInterpolator());
//        //animation.start();



        if (firstLoad) {
            currentUser = getIntent().getStringExtra("currUser");

            new loadAllProjectList().execute("https://sidedepot.herokuapp.com/project");
            new loadProjectList().execute("https://sidedepot.herokuapp.com/users/" + currentUser);

            firstLoad = false;
        }

        System.out.println(projectList.size());

        ProgressBar diy = (ProgressBar) findViewById(R.id.progressBar2);
        ProgressBar ren = (ProgressBar) findViewById(R.id.progressBar6);
        ProgressBar main = (ProgressBar) findViewById(R.id.progressBar);
        ProgressBar out = (ProgressBar) findViewById(R.id.progressBar5);
        ProgressBar totalProgress = (ProgressBar) findViewById(R.id.progressBar7);

        diy.setProgress(DIYDone);
        ren.setProgress(RenDone);
        main.setProgress(MainDone);
        out.setProgress(OutDone);
        totalProgress.setProgress(totalDone);

        month = (TextView) findViewById(R.id.month);
        month.setText(dateFormatMonth.format(Calendar.getInstance().getTime()));

        monthBack = (ImageButton) findViewById(R.id.monthBack);
        monthForward = (ImageButton) findViewById(R.id.monthForward);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view); // get the reference of CalendarView
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setLocale(Calendar.getInstance().getTimeZone(), Locale.getDefault());
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        //Event ev1 = new Event(Color.RED, 1540123200000L, "Teachers' Professional Day");
        System.out.print("HENLO" + " " + myProjectList.size());
        if (myProjectList.size() > 0) {
            Event ev2 = new Event(Color.GREEN ,1536651337000L, myProjectList.get(0).getTitle());
            Event ev3 = new Event(Color.GREEN, 1540539337000L, myProjectList.get(1).getTitle());
            Event ev4 = new Event(Color.GREEN, 1541835337000L, myProjectList.get(2).getTitle());
            Event ev5 = new Event(Color.YELLOW, 1546155337000L, myProjectList.get(3).getTitle());
            Event ev6 = new Event(Color.GREEN, 1547105737000L, myProjectList.get(4).getTitle());
            Event ev7 = new Event(Color.YELLOW, 1549697737000L, myProjectList.get(5).getTitle());
            Event ev8 = new Event(Color.RED, 1555140937000L, myProjectList.get(6).getTitle());
            Event ev9 = new Event(Color.YELLOW, 1560065737000L, myProjectList.get(7).getTitle());
            Event ev10 = new Event(Color.RED, 1564040137000L, myProjectList.get(8).getTitle());
            Event ev11 = new Event(Color.RED, 1565336137000L, myProjectList.get(9).getTitle());
            compactCalendarView.addEvent(ev2);
            compactCalendarView.addEvent(ev3);
            compactCalendarView.addEvent(ev4);
            compactCalendarView.addEvent(ev5);
            compactCalendarView.addEvent(ev6);
            compactCalendarView.addEvent(ev7);
            compactCalendarView.addEvent(ev8);
            compactCalendarView.addEvent(ev9);
            compactCalendarView.addEvent(ev10);
            compactCalendarView.addEvent(ev11);
        }


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                if (dateClicked.toString().compareTo("Tue Sep 11 00:00:00 EDT 2018") == 0) {
                    Toast.makeText(context, myProjectList.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                } else if (dateClicked.toString().compareTo("Fri Oct 26 00:00:00 EDT 2018") == 0) {
                    Toast.makeText(context, myProjectList.get(1).getTitle(), Toast.LENGTH_SHORT).show();
                } else if (dateClicked.toString().compareTo("Sat Nov 10 00:00:00 EDT 2018") == 0) {
                    Toast.makeText(context, myProjectList.get(2).getTitle(), Toast.LENGTH_SHORT).show();

                } else if (dateClicked.toString().compareTo("Sun Dec 30 00:00:00 EST 2018") == 0) {
                    Toast.makeText(context, myProjectList.get(3).getTitle(), Toast.LENGTH_SHORT).show();

                } else if (dateClicked.toString().compareTo("Thu Jan 10 00:00:00 EST 2019") == 0) {
                    Toast.makeText(context, myProjectList.get(4).getTitle(), Toast.LENGTH_SHORT).show();

                } else if (dateClicked.toString().compareTo("Sat Feb 09 00:00:00 EST 2019") == 0) {
                    Toast.makeText(context, myProjectList.get(5).getTitle(), Toast.LENGTH_SHORT).show();

                } else if (dateClicked.toString().compareTo("Sat Apr 13 00:00:00 EDT 2019") == 0) {
                    Toast.makeText(context, myProjectList.get(6).getTitle(), Toast.LENGTH_SHORT).show();

                } else if (dateClicked.toString().compareTo("Sun Jun 09 00:00:00 EDT 2019") == 0) {
                    Toast.makeText(context, myProjectList.get(7).getTitle(), Toast.LENGTH_SHORT).show();

                } else if (dateClicked.toString().compareTo("Thu Jul 25 00:00:00 EDT 2019") == 0) {
                    Toast.makeText(context, myProjectList.get(8).getTitle(), Toast.LENGTH_SHORT).show();

                } else if (dateClicked.toString().compareTo("Fri Aug 09 00:00:00 EDT 2019") == 0) {
                    Toast.makeText(context, myProjectList.get(9).getTitle(), Toast.LENGTH_SHORT).show();

                }
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                month.setText(dateFormatMonth.format(firstDayOfNewMonth));
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });

        monthForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollRight();
            }
        });

        monthBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollLeft();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentProj = (Button) findViewById(R.id.projectButton);
        currentProj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HowToActivity.class);
                startActivity(i);
            }
        });

        Button fab = (Button) findViewById(R.id.nav_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ProgressBar diy = (ProgressBar) findViewById(R.id.progressBar2);
        ProgressBar ren = (ProgressBar) findViewById(R.id.progressBar6);
        ProgressBar main = (ProgressBar) findViewById(R.id.progressBar);
        ProgressBar out = (ProgressBar) findViewById(R.id.progressBar5);
        ProgressBar totalProgress = (ProgressBar) findViewById(R.id.progressBar7);

        diy.setProgress(DIYDone);
        ren.setProgress(RenDone);
        main.setProgress(MainDone);
        out.setProgress(OutDone);
        totalProgress.setProgress(totalDone);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) { //Profile
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) { //Calendar
//            new updateUser().execute("https://sidedepot.herokuapp.com/users/" + currentUser);
//            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        } else if (id == R.id.nav_slideshow) { //Project Areas
            new updateUser().execute("https://sidedepot.herokuapp.com/users/" + currentUser);
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("myBuilds", myProjectList);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    public static class loadProjectList extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                URL obj = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                //con.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = con.getResponseCode();
                //System.out.println("\nSending 'GET' request to URL : " + url);


                //USE THIS CODE TO CHECK RESPONSE CODE FOR INVALID TOKEN

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //print in String
                //System.out.println(response.toString());
                //Read JSON response and print
                JSONObject myResponse = new JSONObject(response.toString());
                Log.d("Total Length", "val = " + myResponse.getJSONArray("message").length());

                JSONArray currProjects = myResponse.getJSONArray("message").getJSONObject(0).getJSONArray("projects");
                JSONArray currBadges = myResponse.getJSONArray("message").getJSONObject(0).getJSONArray("badges");

                for (int count = 0; count < currProjects.length(); count++) {

                    myProjectList.add(projectList.get(currProjects.getString(count)));


                }
                for (int count = 0; count < currBadges.length(); count++) {
                    badges.add(currBadges.getString(count));
                }


//                JSONArray parsedSteps;
//                JSONArray parsedHeaders;
//                JSONArray toolsAndMaterials;
//                JSONArray webCollection;
//                ArrayList<String> finalSteps = new ArrayList<>();
//                ArrayList<String> finalHeaders = new ArrayList<>();
//                ArrayList<String> finalTools = new ArrayList<>();
//                ArrayList<String> finalWeb = new ArrayList<>();


//                for (int count = 0; count < myResponse.getJSONArray("message").length(); count ++) {
//
//
//                    parsedSteps = myResponse.getJSONArray("message").getJSONObject(count).getJSONArray("parsedSteps");
//                    parsedHeaders = myResponse.getJSONArray("message").getJSONObject(count).getJSONArray("parsedHeaders");
//                    toolsAndMaterials = myResponse.getJSONArray("message").getJSONObject(count).getJSONArray("toolsAndMaterials");
//                    webCollection = myResponse.getJSONArray("message").getJSONObject(count).getJSONArray("webCollection");
//
//                    for (int i=0; i<parsedSteps.length(); i++) {
//                        finalSteps.add( parsedSteps.getString(i) );
//                    }
//                    for (int i=0; i<parsedHeaders.length(); i++) {
//                        finalHeaders.add( parsedHeaders.getString(i) );
//                    }
//
//                    for (int i=0; i<toolsAndMaterials.length(); i++) {
//                        finalTools.add( toolsAndMaterials.getString(i) );
//                    }
//
//                    for (int i=0; i<webCollection.length(); i++) {
//                        finalWeb.add( webCollection.getString(i) );
//                    }
//
//
//                    projectList.add(
//
//
//                            new Project(myResponse.getJSONArray("message").getJSONObject(count).getString("title"),
//                                    myResponse.getJSONArray("message").getJSONObject(count).getString("description"),
//                                    myResponse.getJSONArray("message").getJSONObject(count).getString("difficulty"),
//                                    myResponse.getJSONArray("message").getJSONObject(count).getString("category"),
//                                    finalTools,
//                                    myResponse.getJSONArray("message").getJSONObject(count).getString("time"),
//                                    myResponse.getJSONArray("message").getJSONObject(count).getString("image"),
//                                    myResponse.getJSONArray("message").getJSONObject(count).getDouble("priceEstimate"),
//                                    finalSteps, finalHeaders, finalWeb));
//                }





                //Log.d("hi", "Loading intent");
                //Collectionsons.shuffle(recipeList);
                //Intent i  = new Intent(LoaderActivity.this, MainRecipeActivity.class);
                //i.putExtra("recipeList", recipeList);
                //startActivity(i);
                return null;

            } catch (Exception exception) {
                Log.d("hi", exception.toString());
            }
            return null;
        }
    }


    public class loadAllProjectList extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                URL obj = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                //con.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = con.getResponseCode();
                //System.out.println("\nSending 'GET' request to URL : " + url);


                //USE THIS CODE TO CHECK RESPONSE CODE FOR INVALID TOKEN

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //print in String
                //System.out.println(response.toString());
                //Read JSON response and print
                JSONObject myResponse = new JSONObject(response.toString());
                Log.d("Total Length", "val = " + myResponse.getJSONArray("message").length());



                JSONArray parsedSteps;
                JSONArray parsedHeaders;
                JSONArray toolsAndMaterials;
                JSONArray webCollection;
                ArrayList<String> finalSteps = new ArrayList<>();
                ArrayList<String> finalHeaders = new ArrayList<>();
                ArrayList<String> finalTools = new ArrayList<>();
                ArrayList<String> finalWeb = new ArrayList<>();


                for (int count = 0; count < myResponse.getJSONArray("message").length(); count ++) {


                    parsedSteps = myResponse.getJSONArray("message").getJSONObject(count).getJSONArray("parsedSteps");
                    parsedHeaders = myResponse.getJSONArray("message").getJSONObject(count).getJSONArray("parsedHeaders");
                    toolsAndMaterials = myResponse.getJSONArray("message").getJSONObject(count).getJSONArray("toolsAndMaterials");
                    webCollection = myResponse.getJSONArray("message").getJSONObject(count).getJSONArray("weblinks");

                    System.out.println(webCollection.length());


                    for (int i=0; i<parsedSteps.length(); i++) {
                        finalSteps.add( parsedSteps.getString(i) );
                    }
                    for (int i=0; i<parsedHeaders.length(); i++) {
                        finalHeaders.add( parsedHeaders.getString(i) );
                    }

                    for (int i=0; i<toolsAndMaterials.length(); i++) {
                        finalTools.add( toolsAndMaterials.getString(i) );
                    }

                    for (int i=0; i<webCollection.length(); i++) {
                        finalWeb.add( webCollection.getString(i) );

                    }


                    projectList.put(myResponse.getJSONArray("message").getJSONObject(count).getString("title"),


                            new Project(myResponse.getJSONArray("message").getJSONObject(count).getString("title"),
                                    myResponse.getJSONArray("message").getJSONObject(count).getString("description"),
                                    myResponse.getJSONArray("message").getJSONObject(count).getString("difficulty"),
                                    myResponse.getJSONArray("message").getJSONObject(count).getString("category"),
                                    finalTools,
                                    myResponse.getJSONArray("message").getJSONObject(count).getString("time"),
                                    myResponse.getJSONArray("message").getJSONObject(count).getString("image"),
                                    myResponse.getJSONArray("message").getJSONObject(count).getDouble("priceEstimate"),
                                    finalSteps, finalHeaders, finalWeb));
                    finalWeb = new ArrayList<>();
                    finalSteps = new ArrayList<>();
                    finalHeaders = new ArrayList<>();
                    finalTools = new ArrayList<>();
                }





                        Log.d("hi", "Loading intent");
                //Collectionsons.shuffle(recipeList);
                //Intent i  = new Intent(LoaderActivity.this, MainRecipeActivity.class);
                //i.putExtra("recipeList", recipeList);
                //startActivity(i);
                return null;

            } catch (Exception exception) {
                Log.d("hi", exception.toString());
            }
            return null;
        }
    }



    public static class updateUser extends AsyncTask<String, String, String> {


        //New json object (email field, password)
        @Override
        protected String doInBackground(String... params) {
            try {
                URL obj = new URL(params[0]);
                ArrayList<String> projects = new ArrayList<>();
                for (int i = 0; i < myProjectList.size(); i++) {
                    projects.add(myProjectList.get(i).getTitle());
                }
                myProjectList.clear();
                ArrayList<String> badgesPass = new ArrayList<>();
                for (int i = 0; i < badges.size(); i++) {
                    badgesPass.add(badges.get(i));
                }
                badges.clear();

                String data = "{\"username\" : " + "'" + currentUser + "'" + ", \"badges\": " + new JSONArray(badgesPass) + ", \"projects\": " + new JSONArray(projects) + "}";

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
                    new loadProjectList().execute("https://sidedepot.herokuapp.com/users/" + currentUser);
                    return null;


                } else {


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

