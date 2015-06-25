package app.meantneat.com.meetneat.Camera;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.ArrayList;

import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 6/24/15.
 */
public class SpecifiecChefEventsDialogBox {

    private Context context;
    private Dialog dialog;
    private String chefId;
    private String date;
    private String eventTitle;
    private LatLng coordinates;
    private ArrayList<EventDishes> eventDishesArr;



    private LinearLayout eventDishesLayout;
    private LinearLayout eventMealsLayout;



    public SpecifiecChefEventsDialogBox(Context context,String chefId,LatLng coordinates) {

        eventDishesArr = new ArrayList<>();
        this.context=context;
        this.chefId = chefId;
        this.coordinates = coordinates;
        dialog = new Dialog(context);
        initDialogBoxAndShow();

    }
    private void initDialogBoxAndShow()
    {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hungry_specifiec_chef_events_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        eventDishesLayout = (LinearLayout)dialog.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_Event_Dishes_layout);
        TextView chefNameTextView = (TextView)dialog.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_chef_name_text);
        chefNameTextView.setText("Chef: "+ "Dan Later");

        initEventDishesList(chefId,coordinates);
        initEventMealsList();
        initImagesCollection();



    }

    public interface getEventsByType<T>
    {
        public void done(ArrayList<T> arr);
    }

    private void initEventMealsList() {


    }

    private View addEventDishtoList(final EventDishes event)
    {
        View v = dialog.getLayoutInflater().inflate(R.layout.hungry_specifiec_chef_events_dialog_box_event_dishes_cell,
                eventDishesLayout,false);
        TextView dateText = (TextView)v.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_event_dishes_cell_Date);
        TextView timeText = (TextView)v.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_event_dishes_cell_Time);
        TextView descriptionText = (TextView)v.findViewById(R.id.hungry_specifiec_chef_events_dialog_box_event_dishes_cell_Description);

        dateText.setText(Integer.toString(event.getEventDay()));
        final String time = (Integer.toString(event.getStartingHour())) + ":" +
                    (Integer.toString(event.getStartingMinute())) + "-" +
                     (Integer.toString(event.getEndingHour())) + ":" +
                     (Integer.toString(event.getEndingMinute()));

        timeText.setText(time);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecificEventDishesDialogBox eventDialog = new SpecificEventDishesDialogBox(context
                        ,event.getEventId(),"TODO",time,event.getTitle(),event.getEventsDishes());
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

        //Enter Tedails From Event;
        //Date,Time,Description, Check if open now

        return v;
    }

    private void initImagesCollection() {

    }

    private void initEventDishesList(String chefId,LatLng coordinates) {


        getEventsByType<EventDishes> callback = new getEventsByType<EventDishes>() {
            @Override
            public void done(ArrayList<EventDishes> arr) {
                eventDishesArr = arr;
                int i;
                for(i=0;i<eventDishesArr.size();i++)
                {
                    eventDishesLayout.addView(addEventDishtoList(eventDishesArr.get(i)));

                }
            }
        };


        MyModel.getInstance().getModel().getSpecifiecChefsEventFromServer(chefId,coordinates,callback);



    }

    public void show()
    {
        dialog.show();
    }


    public Dialog getDialog() {
        return dialog;
    }


}