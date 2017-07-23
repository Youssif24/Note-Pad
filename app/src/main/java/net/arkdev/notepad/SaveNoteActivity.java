package net.arkdev.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Date;

public class SaveNoteActivity extends AppCompatActivity {

    private final static String EXTRA_MESSAGE="Message";
    EditText edTitle,edNotes;
    String fileName="NotesFile";
    Intent intent;
    SaveData saveData;


    private SharedPreferences notesFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_note);
        edTitle=(EditText)findViewById(R.id.etTitle);
        edNotes=(EditText)findViewById(R.id.etNotes);
        notesFile=getSharedPreferences(fileName,MODE_PRIVATE);


        Intent myintent =getIntent();
        saveData = (SaveData) myintent.getSerializableExtra("noteObject");
            if(saveData!=null) {
                edTitle.setText(saveData.getTitle().toString());
                edNotes.setText(saveData.getNotes().toString());
            }


    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//
//if(keyCode==KeyEvent.KEYCODE_BACK)
//    if(edTitle.getText().toString()!=saveData.getTitle().toString()||edNotes.getText().toString()!=saveData.getNotes().toString())
//    {
//        SharedPreferences.Editor editor = notesFile.edit();
//        editor.remove(saveData.getDate()).commit();
//
//    }
//
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.saveactivity, menu);
        return true;
    }

//    public void editNote(SaveData saveData)
//    {
//        edTitle.setText(saveData.getTitle().toString());
//        edNotes.setText(saveData.getNotes().toString());
//        String Date=saveData.getDate().toString();
//
//
//    }


    public boolean isUpdated(SaveData saveObject)
    {

        if(edTitle.getText().toString().equals(edTitle.getEditableText()) ||edNotes.getText().toString().equals(edNotes.getEditableText()))
            return false;
        else
            return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Save_id)
        {
          saveDataInFile(edTitle,edNotes,saveData);
            return true;
        }
        return false;
    }
    private void saveDataInFile(EditText title,EditText notes,SaveData saveObj)
    {
       Date date=new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = notesFile.edit();
        if(saveObj!=null){
            if(isUpdated(saveObj)==true)
            {
                editor.remove(saveObj.getDate());
                saveObj.setTitle(title.getText().toString());
                saveObj.setNotes(notes.getText().toString());
                saveObj.setDate(stringDate);
                String objectAsString = gson.toJson(saveObj);
                editor.putString(stringDate,objectAsString).commit();
                intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                SaveNoteActivity.this.finish();
            }
            else{
                intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                SaveNoteActivity.this.finish();
            }

        }
        else
        {
            saveObj=new SaveData();
            saveObj.setTitle(title.getText().toString());
            saveObj.setNotes(notes.getText().toString());
            saveObj.setDate(stringDate);
            String objectAsString = gson.toJson(saveObj);
            editor.putString(stringDate,objectAsString).commit();
            intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            SaveNoteActivity.this.finish();
        }




    }
}
