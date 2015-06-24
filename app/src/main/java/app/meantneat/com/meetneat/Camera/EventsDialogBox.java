package app.meantneat.com.meetneat.Camera;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 6/24/15.
 */
public class EventsDialogBox {

    private Context context;
    private Dialog dialog;
    private String chefName;
    private String date;
    private String eventTitle;
    public EventsDialogBox(Context context) {
        this.context=context;
        dialog = new Dialog(context);
        initDialogBoxAndShow();

    }
    private void initDialogBoxAndShow()
    {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hungry_fragment_events_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView chefNameTextView = (TextView)dialog.findViewById(R.id.events_dialog_box_chef_text_view);
        chefNameTextView.setText("Chef: "+chefName);



    }
    public void show()
    {
        dialog.show();
    }


    public Dialog getDialog() {
        return dialog;
    }


}