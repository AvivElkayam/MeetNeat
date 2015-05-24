package app.meantneat.com.meetneat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class HungryMenuFragment extends Fragment {

    private ArrayList<Dish> dishesArrayList;
    private ListView dishesListView;
    private DishesRowListAdapter dishesArrayAdapter;

    public static HungryMenuFragment newInstance(String param1, String param2) {
        HungryMenuFragment fragment = new HungryMenuFragment();

        return fragment;
    }

    public HungryMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        View view = inflater.inflate(R.layout.hungry_menu_fragment,container,false);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    private void initViews()
    {

        dishesArrayList = new ArrayList<>();
        Dish dish1 = new Dish("חומוס פול","שלא נדע",22,7,true,false,null);
        Dish dish2 = new Dish("קובה סלק","שלא נדע",18,1,true,false,null);
        Dish dish3 = new Dish("מוקפץ תאילנדי","חריף אש",26,9,true,true,null);

        dishesArrayList.add(dish1);
        dishesArrayList.add(dish2);
        dishesArrayList.add(dish3);


        dishesListView =(ListView)getActivity().findViewById(R.id.hungry_menu_fragment_events_list_view);
        dishesArrayAdapter = new DishesRowListAdapter();
        dishesListView.setAdapter(dishesArrayAdapter);
    }

    public class DishesRowListAdapter extends ArrayAdapter<Dish>
    {
        public DishesRowListAdapter()
        {
            super(getActivity(),R.layout.hungry_menu_fragment_row, dishesArrayList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.hungry_menu_fragment_row,parent,false);
            }
            Dish dish = dishesArrayList.get(position);
            String name =   dish.getName();
            String description = dish.getDescriprion();
            String dishesLeft = "Dishes left: "+dish.getQuantityLeft();
            double price = dish.getPrice();
            boolean ta = dish.isTakeAway();
            boolean toSit = dish.isToSit();


            TextView titleTextView = (TextView)itemView.findViewById(R.id.hungry_menu_row_description_text_view);
            titleTextView.setText(name + description);
            TextView priceTextView = (TextView)itemView.findViewById(R.id.hungry_menu_row_price_text_view);
            priceTextView.setText(price + "$");
            TextView taTextView = (TextView)itemView.findViewById(R.id.hungry_menu_row_take_away_text_view);
            if(ta)
                taTextView.setTextColor(Color.GREEN);
            else
                taTextView.setTextColor(Color.RED);
            TextView toSitTextView = (TextView)itemView.findViewById(R.id.hungry_menu_row_to_sit_text_view);
            if(toSit)
                toSitTextView.setTextColor(Color.GREEN);
            else
                toSitTextView.setTextColor(Color.RED);
            TextView dishesLeftTextView = (TextView)itemView.findViewById(R.id.hungry_menu_row_dishes_left_text_view);
            dishesLeftTextView.setText(dishesLeft);
            return itemView;
        }
    }
}
