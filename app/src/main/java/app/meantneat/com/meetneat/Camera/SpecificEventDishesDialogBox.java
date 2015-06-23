package app.meantneat.com.meetneat.Camera;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 6/24/15.
 */
public class SpecificEventDishesDialogBox {
    private Context context;
    private Dialog dialog;
    private String chefName;
    private String date;
    private String eventTitle;
    public SpecificEventDishesDialogBox(Context context,String chefName,String eventDate,String eventTitle) {
        this.context=context;
        this.chefName=chefName;
        this.date=eventDate;
        this.eventTitle=eventTitle;
        dialog = new Dialog(context);
        initDialogBoxAndShow();

    }
    private void initDialogBoxAndShow()
    {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hungry_fragment_event_details_dialog_box);
        GridView gridview = (GridView) dialog.findViewById(R.id.hungry_fragment_dialog_box_grid_view);
        gridview.setAdapter(new ImageAdapter(context));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView chefNameTextView = (TextView)dialog.findViewById(R.id.speceific_event_dialog_box_chef_text_view);
        chefNameTextView.setText("Chef: "+chefName);

        TextView dateTextView = (TextView)dialog.findViewById(R.id.speceific_event_dialog_box_date_text_view);
        dateTextView.setText(date);

        TextView titleTextView = (TextView)dialog.findViewById(R.id.speceific_event_dialog_box_title_text_view);
        titleTextView.setText(eventTitle);


    }
    public void show()
    {
        dialog.show();
    }

    {

    }
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            //LinearLayout layout = new LinearLayout(mContext);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(200, 200);
            imageView.setLayoutParams(layoutParams);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.logo1, R.drawable.meet_n_eat_logo,
                R.drawable.logo1, R.drawable.meet_n_eat_logo,
                R.drawable.logo1, R.drawable.meet_n_eat_logo,
                R.drawable.logo1, R.drawable.meet_n_eat_logo,

        };
    }
}
