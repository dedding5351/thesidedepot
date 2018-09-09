package thesidedepot.app.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import android.view.View;
import android.widget.Button;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import thesidedepot.app.R;
import thesidedepot.app.model.Build;
import thesidedepot.app.model.Model;
import thesidedepot.app.model.Project;

import static thesidedepot.app.controller.MainActivity.currentUser;

public class HowToActivity extends AppCompatActivity {
    GridView materials;
    Model model;
    TextView desc, time, diff, link;
    ListView lv;
    ImageView image;
    String weblink;

    Project cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        //Project cur = MainActivity.myProjectList.get(0);

        cur = MainActivity.myProjectList.get(MainActivity.projIndex);
        cur = MainActivity.projectList.get(cur.getTitle());


        Toolbar toolbar = (Toolbar) findViewById(R.id.howtoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(MainActivity.myProjectList.get(0).getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        image = (ImageView) findViewById(R.id.howto_image);
        Picasso.get().load(MainActivity.myProjectList.get(0).getImage()).into(image);

        desc = (TextView) findViewById(R.id.desc);
        desc.setText(MainActivity.myProjectList.get(0).getDescription() + "and the estimated price of this project is $" + MainActivity.myProjectList.get(0).getPriceEstimate() + ".");
        
        time = (TextView) findViewById(R.id.totProjTime);
        time.setText(MainActivity.myProjectList.get(0).getTime());

        diff = (TextView) findViewById(R.id.diff);
        diff.setText(MainActivity.myProjectList.get(0).getDifficulty());

        link = (TextView) findViewById(R.id.link);
        link.setText(MainActivity.myProjectList.get(0).getWebCollection().get(MainActivity.myProjectList.get(0).getWebCollection().size() - 2));


        Button completionButton = (Button) findViewById(R.id.completionButton);

        completionButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(HowToActivity.this, android.R.style.Theme_Material_Dialog_Alert);

                builder.setTitle("Project Complete")
                        .setMessage("Mark project as complete?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if (cur.getCategory().equals("DIY, Decor & Fun")) {
                                    MainActivity.DIYDone++;
                                } else if (cur.getCategory().equals("Lawn, Garden, & Outdoor")) {
                                    MainActivity.OutDone++;
                                } else if (cur.getCategory().equals("Home Maintenance")) {
                                    MainActivity.MainDone++;
                                } else if (cur.getCategory().equals("Home Renovation")) {
                                    MainActivity.RenDone++;
                                }
                                MainActivity.totalDone++;

                                MainActivity.projIndex++;

                                new MainActivity.updateUser().execute("https://sidedepot.herokuapp.com/users/" + currentUser);

                                Intent intent = new Intent(HowToActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
