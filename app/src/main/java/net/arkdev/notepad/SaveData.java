package net.arkdev.notepad;

import java.io.Serializable;

/**
 * Created by Youssif96 on 8/30/2016.
 */
public class SaveData implements Serializable {
    private String title;
    private String notes;
    private String Date;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


}
