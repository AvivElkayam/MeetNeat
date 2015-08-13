package app.meantneat.com.meetneat.Camera;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import app.meantneat.com.meetneat.Entities.Dish;
import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 6/25/15.
 */
public class SpecificDishDialogBox {
private Context context;
private Dialog dialogBox;
private Dish dish;
//private String dishID,dishTitle,dishPrice,dishesLeft;
private TextView titleTextView,priceTextView,dishesLeftTextView;
private ImageView dishImageView;

    public SpecificDishDialogBox(Context context,Dish dish) {
        this.context=context;
        this.dish = dish;
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
        dialogBox.setContentView(R.layout.hungry_specific_dish_dialog_box);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        titleTextView = (TextView)dialogBox.findViewById(R.id.specific_dish_dialog_box_title_text_view);
        //titleTextView.setText(eventTitle);
        titleTextView.setText(dish.getTitle());

        priceTextView = (TextView)dialogBox.findViewById(R.id.specific_dish_dialog_box_price_text_view);
        priceTextView.setText(dish.getPrice()+"$");

        dishesLeftTextView = (TextView)dialogBox.findViewById(R.id.specific_dish_dialog_box_dishes_left_text_view);
        dishesLeftTextView.setText(Double.toString(dish.getQuantityLeft()));
        dishImageView = (ImageView)dialogBox.findViewById(R.id.specific_dish_dialog_box_image_view);

        MyModel.getInstance().getModel().getDishPicture(dish.getDishID(),new MyModel.PictureCallback() {
            @Override
            public void pictureHasBeenFetched(Bitmap bitmap) {
                dishImageView.setBackground(new BitmapDrawable(bitmap));
            }
        });



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

}
