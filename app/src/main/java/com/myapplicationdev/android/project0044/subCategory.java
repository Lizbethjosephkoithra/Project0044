package com.myapplicationdev.android.project0044;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class subCategory extends AppCompatActivity {

    ListView lv;
    List<String> cat;
    String category[] = {};
    ArrayAdapter<String> catAdapt;
    String temp_appender;
    String appender = "";
    String[] splitup;
    public String items;
    Toolbar tbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        tbr = (Toolbar) findViewById(R.id.my_toolbarSub);
        lv = (ListView) findViewById(R.id.lvSub);

        loadData();
        lv.setAdapter(catAdapt);
        loadData();
        Intent intent = getIntent();

        //cat = new ArrayList<String>();
        catAdapt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);


        //Intent intent = getIntent();

        if (intent.getStringExtra("cat").equals("ent")) {
            tbr.setTitle("Entertainment");
//            cat.add("Music");
//            intent.putExtra("mus", "music");
//            cat.add("Videos");
//            cat.add("Songs");
//            cat.add("Games");
//            cat.add("Toys");
            //cat.add(set.toString());
        } else if (intent.getStringExtra("cat").equals("fash")) {
            tbr.setTitle("Fashion");
//            cat.add("Clothes");
//            cat.add("Wearables");
//            cat.add("Cosmetics");
            //cat.add(set.toString());
        } else if (intent.getStringExtra("cat").equals("hse")) {
            tbr.setTitle("Household");
//            cat.add("Utensils");
//            cat.add("Toiletries");
//            cat.add("Cleaners");
            //cat.add(set.toString());
        } else if (intent.getStringExtra("cat").equals("elct")) {
            tbr.setTitle("Electronics");
//            cat.add("PC");
//            cat.add("Sports");
            //cat.add(set.toString());
        } else if (intent.getStringExtra("cat").equals("book")){
            tbr.setTitle("Books");
//            cat.add("Mystery");
//            cat.add("Romance");
//            cat.add("Action");
            //cat.add(set.toString());
            //cat.add(set.toString());
        }
        //category = cat.toArray(category);


        setSupportActionBar(tbr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        catAdapt = new ArrayAdapter<>(getApplicationContext(),  android.R.layout.simple_list_item_1);
        lv.setAdapter(catAdapt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent inte = new Intent(getApplicationContext(),sub2Category.class);
                inte.putExtra("subCat", items );
                startActivity(inte);

            }
        });
    }

    protected void saveData (String key, String value, ArrayList < String > opt){
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);

        if (opt != null) {
            for (int i = 0; i <= opt.size(); i++)
                value += opt.get(i) + ",";
        }

        SharedPreferences.Editor editor = data.edit();
        editor.putString(key, value);
        editor.commit();

        //Toast.makeText(subCategory.this, "saved = " + cat.size(), Toast.LENGTH_LONG).show();

    }

    protected String loadData() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        String dataSet = data.getString("LISTS", "");
        cat = Arrays.asList(dataSet.split(","));
        splitup = dataSet.split(",");


        List<String> items = Arrays.asList(dataSet.split(","));
        ArrayList<String> itemsarraylist = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            itemsarraylist.add(i, items.get(i));
        }
        catAdapt = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, itemsarraylist);

        lv.setAdapter(catAdapt);
        catAdapt.notifyDataSetChanged();

        return dataSet;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sub_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_menu:
               // Toast.makeText(subCategory.this, "Menu clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add New Category");

                // Set up the input
                final EditText input = new EditText(this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //appender = loadData();
                        Intent intent = getIntent();
                        if (input.getText().toString() != null) {

                            temp_appender = input.getText().toString();
                            String string_to_split = appender + "," + temp_appender;
                            List<String> items = Arrays.asList(string_to_split.split(","));
                            catAdapt = new ArrayAdapter<String>
                                    (getApplicationContext(), android.R.layout.simple_list_item_1, items);

                            lv.setAdapter(catAdapt);
                            catAdapt.notifyDataSetChanged();
                            saveData(intent.getStringExtra("cat"), string_to_split, null);
                        }
                        input.setText("");

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
        }
        return super.

                onOptionsItemSelected(item);
    }
}
