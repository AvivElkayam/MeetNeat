package app.meantneat.com.meetneat.Controller.Hungry;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import app.meantneat.com.meetneat.Entities.Dish;
import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.MeetnEatDates;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;
import de.hdodenhof.circleimageview.CircleImageView;

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
    private RoundedImageView chefImageView;
    private ArrayList<Dish> dishArrayList;
    private DishAdapter dishAdapter;
    private EventDishes eventDishes;
    public SpecificEventDishesDialogBox(Context context,EventDishes eventDishes) {
        this.context=context;
        this.eventID=eventID;
        this.chefName=chefName;
        //this.date=eventDate;
        this.eventTitle=eventTitle;
        dialogBox = new Dialog(context);
        this.dishArrayList = eventDishes.getEventsDishes();
        this.eventDishes=eventDishes;
        initDialogBoxAndShow();

    }
    public interface DishEventCallback
    {
        public void eventHasBeenFetched(EventDishes event);
    }
    private void initDialogBoxAndShow()
    {
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.setContentView(R.layout.hungry_specifiec_event_dishes_details_dialog_box);
        StaggeredGridView gridview = (StaggeredGridView) dialogBox.findViewById(R.id.hungry_fragment_dialog_box_grid_view);
        dishAdapter = new DishAdapter(context);
        gridview.setAdapter(dishAdapter);
        dishAdapter.notifyDataSetChanged();
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

         chefNameTextView = (TextView)dialogBox.findViewById(R.id.speceific_event_dialog_box_chef_text_view);
        chefNameTextView.setText("Chef: "+eventDishes.getChefName());

        chefImageView = (RoundedImageView)dialogBox.findViewById(R.id.events_dialog_box_chef_image_view);
        chefImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        MyModel.getInstance().getModel().getChefPicture(eventDishes.getChefID(), new MyModel.PictureCallback() {
            @Override
            public void pictureHasBeenFetched(Bitmap bitmap) {
                if (bitmap != null
                        )
                    chefImageView.setBackground(new BitmapDrawable(bitmap));
            }
        });
        TextView titleTextView = (TextView)dialogBox.findViewById(R.id.speceific_event_dialog_box_title_text_view);
        titleTextView.setText(eventDishes.getTitle());
        TextView startingDateTextView = (TextView)dialogBox.findViewById(R.id.speceific_event_dialog_box_starting_date_text_view);
        startingDateTextView.setText(MeetnEatDates.getDateString(eventDishes.getStartingYear(),
                        eventDishes.getStartingMonth()
                        , eventDishes.getStartingDay()) + ", " + MeetnEatDates.getTimeString(eventDishes.getStartingHour(),
                        eventDishes.getStartingMinute())

        );
        TextView endingDateTextView = (TextView)dialogBox.findViewById(R.id.speceific_event_dialog_box_ending_date_text_view);
        endingDateTextView.setText(MeetnEatDates.getDateString(eventDishes.getEndingYear(),
                        eventDishes.getEndingMonth()
                        , eventDishes.getEndingDay()) + ", " + MeetnEatDates.getTimeString(eventDishes.getEndingHour(),
                        eventDishes.getEndingMinute())

        );
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

    public class DishAdapter extends BaseAdapter {
        private Context mContext;

        public DishAdapter(Context c) {
            mContext = c;
        }

        public int getCount()
        {
            return dishArrayList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
//
            if(itemView==null)
            {
//                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = dialogBox.getLayoutInflater().inflate(R.layout.hungry_specifiec_event_dishes_details_dialog_box_cell,parent,false);
            }
            final Dish dish = dishArrayList.get(position);
            TextView dishNameTextView = (TextView)itemView.findViewById(R.id.hungry_fragment_event_dishes_dialog_box_cell_dish_name_text_view);
            dishNameTextView.setText(dish.getTitle());

            TextView dishPriceTextView = (TextView)itemView.findViewById(R.id.hungry_fragment_event_dishes_dialog_box_cell_price_texr_view);
            dishPriceTextView.setText(dish.getPrice()+"$");

            final ImageView dishImageView = (ImageView)itemView.findViewById(R.id.hungry_fragment_event_dishes_dialog_box_cell_image_view);

            MyModel.getInstance().getModel().getDishPicture(dish.getDishID(),new MyModel.PictureCallback() {
                @Override
                public void pictureHasBeenFetched(Bitmap bitmap) {
                    dishImageView.setImageBitmap(bitmap);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpecificDishDialogBox specificDishDialogBox = new SpecificDishDialogBox(context,dish);
                    specificDishDialogBox.show();
                }
            });
            return itemView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                 R.drawable.jachnun,
                R.drawable.dish2,
                R.drawable.pasta1,


        };
    }
    private void getEventFromserver()
    {




    }

        public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }

}
