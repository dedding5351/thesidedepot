package thesidedepot.app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import thesidedepot.app.R;
import thesidedepot.app.model.Build;
import thesidedepot.app.model.Model;
import thesidedepot.app.model.Project;

import static thesidedepot.app.controller.MainActivity.currentUser;

public class ProjectActivity extends AppCompatActivity {
    Model model;
    private ListView lv;
    ArrayList<Project> builds;
    ArrayList<Project> generated;
    Button refresh;
    List<String> buildnames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        model = Model.getInstance();

        lv = (ListView) findViewById(R.id.projectList);

        System.out.print("categories: " + CategoryActivity.categories.size());

        buildnames = new ArrayList<String>();
        generated = generateBuilds();

        for (Project b : generated) {
            buildnames.add(b.getTitle());
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                buildnames);

        lv.setAdapter(arrayAdapter);

        Button back_button = (Button) findViewById(R.id.back_button);

        refresh = (Button) findViewById(R.id.button_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generated = generateBuilds();
                buildnames.clear();
                for (Project b : generated) {
                    buildnames.add(b.getTitle());
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

        back_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button confirm_button = (Button) findViewById(R.id.button_confirm);

        confirm_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainActivity.updateUser().execute("https://sidedepot.herokuapp.com/users/" + currentUser);
                MainActivity.myProjectList = generated;
                Intent intent = new Intent(ProjectActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Project> generateBuilds() {
        ArrayList<Project> easy = new ArrayList<>();
        ArrayList<Project> med = new ArrayList<>();
        ArrayList<Project> hard = new ArrayList<>();

        ArrayList<Project> total = new ArrayList<>();

        Iterator it = MainActivity.projectList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String name = (String) pair.getKey();
            Project project = (Project) pair.getValue();

            if (name != null && !name.isEmpty() && CategoryActivity.categories.contains(project.getCategory())) {
                System.out.print(CategoryActivity.categories.size());
                if (project.getDifficulty().equals("Easy")) {
                    easy.add(project);
                } else if (project.getDifficulty().equals("Intermediate")) {
                    med.add(project);
                } else if (project.getDifficulty().equals("Advanced")) {
                    hard.add(project);
                }
            }
        }

        Collections.shuffle(easy);
        Collections.shuffle(med);
        Collections.shuffle(hard);

        total.add(easy.get(0));
        total.add(easy.get(1));
        total.add(easy.get(2));
        total.add(med.get(0));
        total.add(easy.get(3));
        total.add(med.get(1));
        total.add(hard.get(0));
        total.add(med.get(2));
        total.add(hard.get(1));
        total.add(hard.get(2));

        return total;
    }
}
