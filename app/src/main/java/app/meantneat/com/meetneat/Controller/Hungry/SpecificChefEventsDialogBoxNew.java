package app.meantneat.com.meetneat.Controller.Hungry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import app.meantneat.com.meetneat.Camera.CameraBasics;
import app.meantneat.com.meetneat.Controller.Chef.EditDishDialogBox;
import app.meantneat.com.meetneat.Entities.Dish;
import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.MeetnEatDates;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mac on 6/24/15.
 */
public class SpecificChefEventsDialogBoxNew {

    private Context context;
    private Dialog dialog;
    private String chefId,chefName;
    private LatLng coordinates;
    private ListView eventsListView;
    private EventDishesRowListAdapter adapter;
    private ArrayList<EventDishes> eventDishesArr;



    public SpecificChefEventsDialogBoxNew(Context context, String chefId, String chefName, LatLng coordinates) {

        this.context=context;
        this.chefId = chefId;
        this.chefName=chefName;
        this.coordinates = coordinates;
        dialog = new Dialog(context);
        initDialogBoxAndShow();

    }
    private class EventDishesRowListAdapter extends ArrayAdapter<EventDishes>
    {
        public EventDishesRowListAdapter()
        {
            super(context, R.layout.hungry_specifiec_chef_events_dialog_box_new_list_view_row, eventDishesArr);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = dialog.getLayoutInflater().inflate(R.layout.hungry_specifiec_chef_events_dialog_box_new_list_view_row,parent,false);
            }
            final EventDishes event = eventDishesArr.get(position);
            TextView titleTextView = (TextView)itemView.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_new_list_view_row_title);
            titleTextView.setText(event.getTitle());

            TextView startingDateTextView = (TextView)itemView.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_new_list_view_row_starting_date);
            startingDateTextView.setText(MeetnEatDates.getDateString(event.getStartingYear(), event.getStartingMonth(), event.getStartingDay()));

            TextView endingDateTextView = (TextView)itemView.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_new_list_view_row_ending_date);
            endingDateTextView.setText(MeetnEatDates.getDateString(event.getEndingYear(), event.getEndingMonth(), event.getEndingDay()));

            final LinearLayout dishesImagesContainer = (LinearLayout)itemView.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_Images_layout);
            MyModel.getInstance().getModel().getEventsDishes(event.getEventId(), new MyModel.DishesCallback() {
                @Override
                public void dishesAhBeenFetched(ArrayList<Dish> dishes) {
                    event.setEventsDishes(dishes);
                    for(final Dish dish : dishes)
                    {
                        MyModel.getInstance().getModel().getDishPicture(dish.getDishID(), new MyModel.PictureCallback() {
                            @Override
                            public void pictureHasBeenFetched(Bitmap bitmap) {
                                dish.setThumbnailImage(bitmap);
                                RoundedImageView imageView = new RoundedImageView(context);
                                imageView.setBorderWidth(context.getResources().getDimension(R.dimen.rounded_image_view_border_width));
                                imageView.setBorderColor(context.getResources().getColor(android.R.color.transparent));
                                imageView.setCornerRadius(0);
                                float px = CameraBasics.dpToPX(context,96);
                                //float dp = somePxValue / density;
                                imageView.setLayoutParams(new LinearLayout.LayoutParams((int) px, (int) px));
                                imageView.setImageBitmap(bitmap);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                dishesImagesContainer.addView(imageView);
                            }
                        });

                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            SpecificEventDishesDialogBox eventDialog = new SpecificEventDishesDialogBox(context
                                    ,event);
                            eventDialog.getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    show();
                                }
                            });
                            dialog.hide();
                            eventDialog.show();

                }
            });
            return itemView;
        }
    }
    private void initDialogBoxAndShow()
    {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hungry_specifiec_chef_events_dialog_box_new);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView chefNameTextView = (TextView)dialog.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_chef_name_text);
        chefNameTextView.setText("Chef: " + chefName);

        final RoundedImageView chefImageView = (RoundedImageView)dialog.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_chef_image);
        chefImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        MyModel.getInstance().getModel().getChefPicture(chefId, new MyModel.PictureCallback() {
            @Override
            public void pictureHasBeenFetched(Bitmap bitmap) {

                chefImageView.setImageBitmap(bitmap);
            }
        });
        initEventDishesList(chefId, coordinates);




    }

    public interface GetEventsCallback<T>
    {
        public void done(ArrayList<T> arr);
    }

    private void initEventDishesList(String chefId,LatLng coordinates) {

        eventDishesArr = new ArrayList<>();
        adapter = new EventDishesRowListAdapter();
        eventsListView = (ListView)dialog.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_List_view);
        eventsListView.setAdapter(adapter);
        GetEventsCallback<EventDishes> callback = new GetEventsCallback<EventDishes>() {
            @Override
            public void done(ArrayList<EventDishes> arr) {
                //eventDishesArr = arr;
                eventDishesArr.clear();
                eventDishesArr.addAll(arr);
                adapter.notifyDataSetChanged();


            }
        };


        MyModel.getInstance().getModel().getSpecifiecChefsEventFromServer(chefId, coordinates, callback);



    }

    public void show()
    {
        dialog.show();
    }


    public Dialog getDialog() {
        return dialog;
    }


}