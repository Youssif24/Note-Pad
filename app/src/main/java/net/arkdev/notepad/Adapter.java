package net.arkdev.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Youssif96 on 8/31/2016.
 */
public class Adapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List arrayList;

    public Adapter(Context context, List arraylist) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.arrayList=arraylist;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public SaveData getItem(int position) {
        return(SaveData) arrayList.get(position);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        ItemClass itemClass;
        Date date=new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date);
        if(contentView==null) {
            contentView = inflater.inflate(R.layout.items_layout, null);
            itemClass=new ItemClass(contentView);
            contentView.setTag(itemClass);
        }
        else
        itemClass=(ItemClass) contentView.getTag();

        SaveData saveData=getItem(position);
        itemClass.tvTitle.setText(saveData.getTitle());
        itemClass.tvNotes.setText(saveData.getNotes());
        itemClass.tvDate.setText(saveData.getDate());

        return contentView;
    }
}
