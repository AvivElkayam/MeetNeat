package app.meantneat.com.meetneat.Camera;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import org.w3c.dom.Text;

import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 6/24/15.
 */
public class SpecificEventDishesDialogBox {
    private Context context;
    private Dialog dialogBox;
    private String chefName;
    private String date;
    private String eventTitle;
    private String eventID;
    private TextView chefNameTextView,dateTextView,titleTextView;
    private ImageView chefImageView;
    public SpecificEventDishesDialogBox(Context context,String eventID,String chefName,String eventDate,String eventTitle) {
        this.context=context;
        this.eventID=eventID;
        this.chefName=chefName;
        this.date=eventDate;
        this.eventTitle=eventTitle;
        dialogBox = new Dialog(context);
        initDialogBoxAndShow();

    }
    public interface DishEventCallback
    {
        public void eventHasBeenFetched(EventDishes event);
    }
    private void initDialogBoxAndShow()
    {
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.setContentView(R.layout.hungry_fragment_event_details_dialog_box);
        GridView gridview = (GridView) dialogBox.findViewById(R.id.hungry_fragment_dialog_box_grid_view);
        gridview.setAdapter(new ImageAdapter(context));
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

         chefNameTextView = (TextView)dialogBox.findViewById(R.id.speceific_event_dialog_box_chef_text_view);
        chefNameTextView.setText("Chef: "+chefName);

        chefImageView = (ImageView)dialogBox.findViewById(R.id.events_dialog_box_chef_image_view);
        dateTextView = (TextView)dialogBox.findViewById(R.id.speceific_event_dialog_box_date_text_view);
        //dateTextView.setText(date);

        titleTextView = (TextView)dialogBox.findViewById(R.id.speceific_event_dialog_box_title_text_view);
        //titleTextView.setText(eventTitle);
        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EventsDialogBox eventsDialogBox = new EventsDialogBox(context);
                dialogBox.hide();
                //dialogBox.show();
                eventsDialogBox.getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialogBox.show();

                    }
                });
                eventsDialogBox.show();
            }
        });
        getEventFromserver();


    }
    public void show()
    {
        dialogBox.show();
    }
    {

    }

    public Dialog getDialog() {
        return dialogBox;
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
                R.drawable.logo1, R.drawable.jachnun,
                R.drawable.logo1, R.drawable.dish2,
                R.drawable.logo1, R.drawable.pasta1,


        };
    }
    private void getEventFromserver()
    {
        MyModel.getInstance().getModel().getDishEventDetailsByID(eventID,new DishEventCallback() {
            @Override
            public void eventHasBeenFetched(EventDishes event) {
                titleTextView.setText(event.getTitle());
                dateTextView.setText(event.getEventYear()
                        +"."
                        +event.getEventMonth()
                        + "."
                        +event.getEventDay()
                        +" | "
                        +event.getStartingHour()
                        +":"
                        +event.getStartingMinute()
                        +"-"
                        +event.getEndingHour()
                        +":"
                        +event.getEndingMinute()
                );
                MyModel.getInstance().getModel().getChefPicture(event.getChefID(),new MyModel.PictureCallback() {
                    @Override
                    public void pictureHasBeenFetched(Bitmap bitmap) {
                        chefImageView.setBackground(new BitmapDrawable(bitmap));
                    }
                });
            }
        });
    }
}
