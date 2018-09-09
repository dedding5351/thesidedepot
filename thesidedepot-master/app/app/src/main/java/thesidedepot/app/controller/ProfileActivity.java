package thesidedepot.app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

import thesidedepot.app.R;
import thesidedepot.app.model.Model;

import static thesidedepot.app.controller.MainActivity.currentUser;

public class ProfileActivity extends AppCompatActivity {
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(MainActivity.projectList.size());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("John Doe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView yearProjNum = (TextView) findViewById(R.id.yearProjNum);
        TextView totProjNum = (TextView) findViewById(R.id.totProjNum);

        ImageView HoReB = (ImageView) findViewById(R.id.hoReBro);
        ImageView HoReS = (ImageView) findViewById(R.id.hoReSil);
        ImageView HoReG = (ImageView) findViewById(R.id.hoReGol);
        ImageView DIYB = (ImageView) findViewById(R.id.DIYBro);
        ImageView DIYS = (ImageView) findViewById(R.id.DIYSil);
        ImageView DIYG = (ImageView) findViewById(R.id.DIYGol);
        ImageView LGOB = (ImageView) findViewById(R.id.LawnBro);
        ImageView LGOS = (ImageView) findViewById(R.id.LawnSil);
        ImageView LGOG = (ImageView) findViewById(R.id.LawnGol);
        final ImageView HoMaB = (ImageView) findViewById(R.id.HoMaBro);
        ImageView HoMaS = (ImageView) findViewById(R.id.HoMaSil);
        ImageView HoMaG = (ImageView) findViewById(R.id.HoMaGol);

        Button logout = (Button) findViewById(R.id.logout);

        model = Model.getInstance();
        Map<String, Boolean> badges = model.getBadges();

        model.setBadge("HoReB");

        if (badges.get("HoReB")) {
            HoReB.setImageResource(R.drawable.bronze);
        } else if (badges.get("HoReS")) {
            HoReS.setImageResource(R.drawable.silver);
        } else if (badges.get("HoReG")) {
            HoReG.setImageResource(R.drawable.gold);
        } else if (badges.get("DIYB")) {
            DIYB.setImageResource(R.drawable.bronze);
        } else if (badges.get("DIYS")) {
            DIYS.setImageResource(R.drawable.silver);
        } else if (badges.get("DIYG")) {
            DIYG.setImageResource(R.drawable.gold);
        } else if (badges.get("LGOB")) {
            LGOB.setImageResource(R.drawable.bronze);
        } else if (badges.get("LGOS")) {
            LGOS.setImageResource(R.drawable.silver);
        } else if (badges.get("LGOG")) {
            LGOG.setImageResource(R.drawable.gold);
        } else if (badges.get("HoMaB")) {
            HoMaB.setImageResource(R.drawable.bronze);
        } else if (badges.get("HoMaS")) {
            HoMaS.setImageResource(R.drawable.silver);
        } else if (badges.get("HoMaG")) {
            HoMaG.setImageResource(R.drawable.gold);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new MainActivity.updateUser().execute("https://sidedepot.herokuapp.com/users/" + currentUser);
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }
}
