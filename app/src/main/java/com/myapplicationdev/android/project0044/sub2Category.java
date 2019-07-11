package com.myapplicationdev.android.project0044;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.os.Build.VERSION_CODES.O;
import static android.widget.AdapterView.*;

public class sub2Category extends AppCompatActivity {

    ListView lv;
    List<String> cat;

    //String category[] = {};
    ArrayAdapter<String> catAdapt;
    String temp_appender;
    //    String appender = "";
    String[] splitup;
    public String items;
    Toolbar tbr;
    String delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2_category);

        tbr = (Toolbar) findViewById(R.id.my_toolbar2Sub);
        lv = (ListView) findViewById(R.id.lvSub2);

        Intent intent = getIntent();
        tbr.setTitle(intent.getStringExtra("cat"));

        delete = intent.getStringExtra("delete");

        loadData();
        lv.setAdapter(catAdapt);
        loadData();

        catAdapt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        setSupportActionBar(tbr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


//        lv.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
//
//


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(sub2Category.this);
                builder1.setTitle("Do you want to enter or delete ?");

                // Set up the buttons
                builder1.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent inte = new Intent(getApplicationContext(), sub3Category.class);
                        inte.putExtra("subCat", cat.get(arg2));
                        inte.putExtra("subCategory", cat.get(arg2));
                        startActivity(inte);
                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(arg1);
                    }
                });

                builder1.show();

            }
        });
    }

    protected void deleteItem(final View arg1) {

        final Intent in = getIntent();

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        dialogBuilder.setTitle("Delete");
        dialogBuilder.setMessage
                ("Do you want to delete \"" + ((TextView) arg1).getText() + "\"?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String splitit = loadData();

                List<String> listit = Arrays.asList(splitit.split(","));
                ArrayList<String> itemsarraylist = new ArrayList<String>();
                for (int i = 0; i < listit.size(); i++) {
                    itemsarraylist.add(i, listit.get(i));
                }
                try {
                    catAdapt.remove(((TextView) arg1).getText().toString());
                    catAdapt.notifyDataSetChanged();
                    itemsarraylist.remove(((TextView) arg1).getText().toString());

                    try {
                        saveData(in.getStringExtra("cat"), null, itemsarraylist);
                    } catch (Exception e) {

                    }
                } catch (Exception e) {
                    Log.e("remove", "failed");
                }
            }
        });

        dialogBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        android.app.AlertDialog deleteDialog = dialogBuilder.create();
        deleteDialog.show();
    }

    protected void saveData(String key, String value, ArrayList<String> opt) {

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = data.edit();

        if (opt != null) {
            editor.remove(key);
//            for (int i = 0; i <= opt.size(); i++)
//                value += opt.get(i) + ",";
        } else {
            editor.putString(key, value);
        }

        editor.commit();

    }

    protected String loadData() {

        Intent in = getIntent();

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        String dataSet = data.getString(in.getStringExtra("cat"), "");
        cat = Arrays.asList(dataSet.split(","));
        splitup = dataSet.split(",");

        List<String> items = Arrays.asList(dataSet.split(","));
        ArrayList<String> itemsarraylist = new ArrayList<String>();

        if (items.size() != 1) {
            for (int i = 0; i < items.size(); i++) {
                itemsarraylist.add(i, items.get(i));
            }
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
                // Toast.makeText(sub2Category.this, "Menu clicked", Toast.LENGTH_LONG).show();
                return true;
//            case R.id.action_delete:

//                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//                builder1.setTitle("Are you sure you want to delete "+delete.toString() +" ?");
//
//                // Set up the buttons
//                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        deleteItem(delete.toStrin);
//                    }
//                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder1.show();
//
//                return true;


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

                        String appender = loadData();

                        if (input.getText().toString() != null) {
                            temp_appender = input.getText().toString();
                            String string_to_split = appender + "," + temp_appender;
                            ;
                            if (appender.equals("")) {
                                List<String> items = Arrays.asList(string_to_split.split(","));
                                catAdapt = new ArrayAdapter<String>
                                        (getApplicationContext(), android.R.layout.simple_list_item_1, items);
                            } else {
                                List<String> items = Arrays.asList(string_to_split.split(","));
                                catAdapt = new ArrayAdapter<String>
                                        (getApplicationContext(), android.R.layout.simple_list_item_1, items);
                            }
//                            Toast.makeText(sub2Category.this, string_to_split, Toast.LENGTH_LONG).show();


                            lv.setAdapter(catAdapt);
                            Intent i = getIntent();
                            catAdapt.notifyDataSetChanged();
                            saveData(i.getStringExtra("cat"), string_to_split, null);

                            loadData();
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
