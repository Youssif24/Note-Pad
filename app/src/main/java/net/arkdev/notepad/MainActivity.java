package net.arkdev.notepad;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Adapter myAdapter;
    String fileName = "NotesFile";
    SharedPreferences notesFile;
    ListView listView;
    Intent intent;
    SaveData saveData;

    List<SaveData> arrayList;
    String objectAsString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_id);
        notesFile = getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = notesFile.edit();
        registerForContextMenu(listView);


    }


    @Override
    public void onResume() {
        super.onResume();
        showData(notesFile);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               saveData= (SaveData) listView.getItemAtPosition(i);
               Intent noteTextIntent=new Intent(MainActivity.this,SaveNoteActivity.class);
               noteTextIntent.putExtra("noteObject",saveData);
              // noteTextIntent.putExtra("noteTitle",saveData.getTitle().toString());
               //noteTextIntent.putExtra("noteData",saveData.getNotes().toString());
               startActivity(noteTextIntent);
           }
       });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.list_id) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            //menu.setHeaderTitle());
            String[] menuItems = getResources().getStringArray(R.array.context_menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        //arrayList.remove(menuItemIndex);
        SharedPreferences.Editor editor = notesFile.edit();
        editor.remove(String.valueOf(arrayList.remove(menuItemIndex))).commit();
        //String[] menuItems = getResources().getStringArray(R.array.menu);
       // String menuItemName = menuItems[menuItemIndex];
        //String listItemName = Countries[info.position];

        //TextView text = (TextView)findViewById(R.id.footer);
        //text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.add_new_note) {
            intent = new Intent(this, SaveNoteActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.delete) {

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {

                    SharedPreferences.Editor editor = notesFile.edit();
                    SaveData noteToDelete = arrayList.get(index);
                    editor.remove(noteToDelete.getDate());
//                    editor.remove("title"+String.valueOf(index));
//                    editor.remove("notes"+String.valueOf(index));
                    editor.commit();
                    arrayList.remove(index);
                    myAdapter.notifyDataSetChanged();
                    return false;
                }
            });

            if(item.getItemId()==R.id.edit)
            {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
                    {
                        SaveData noteToEdit=arrayList.get(position);


                    }
                });
            }
        }

        if (item.getItemId() == R.id.Exit) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showData(SharedPreferences sharedPref) {
        Map<String, ?> allEntries = sharedPref.getAll();
        arrayList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            SaveData saveData = new SaveData();
            try {
                String jsonKey = sharedPref.getString(entry.getKey(), null);

                JSONObject jsonRoot = new JSONObject(jsonKey);
                saveData.setNotes(jsonRoot.getString("notes").toString());
                saveData.setTitle(jsonRoot.getString("title").toString());
                saveData.setDate(jsonRoot.getString("Date").toString());
                arrayList.add(saveData);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        myAdapter = new Adapter(this, arrayList);
        listView.setAdapter(myAdapter);


    }
}
