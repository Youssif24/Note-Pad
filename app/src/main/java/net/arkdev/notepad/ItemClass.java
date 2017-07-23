package net.arkdev.notepad;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Youssif96 on 8/31/2016.
 */
public class ItemClass {
    TextView tvTitle,tvNotes,tvDate;
    public ItemClass(View view)
    {
        tvTitle=(TextView)view.findViewById(R.id.tvTitle);
        tvNotes=(TextView)view.findViewById(R.id.tvNotes);
        tvDate=(TextView)view.findViewById(R.id.tvTime);
    }
}
