package app.meantneat.com.meetneat.Controller.Chef;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import app.meantneat.com.meetneat.Entities.Dish;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 8/5/15.
 */
public class EditDishDialogBox {
    private Context context;
    private Dialog dialogBox;
    private Dish dish;

    private EditText priceEditText,quantityEditText,titleEditText;
    private ImageView dishImageView;

    private String title,price,quantity;
    public EditDishDialogBox(Context context,Dish dish) {
        this.context = context;
        this.dish = dish;
        initDialogBox();
        initViews();

    }
    private void initDialogBox()
    {
        dialogBox = new Dialog(context);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.setContentView(R.layout.chef_edit_dish_dialog_box_layout);
    }
    private void initViews()
    {
        titleEditText = (EditText)dialogBox.findViewById(R.id.chef_edit_dish_dialog_box_title_edit_text);
        titleEditText.setText(dish.getTitle());

        priceEditText = (EditText)dialogBox.findViewById(R.id.chef_edit_dish_dialog_box_price_edit_text);
        priceEditText.setText(Double.toString(dish.getPrice()));

        quantityEditText = (EditText)dialogBox.findViewById(R.id.chef_edit_dish_dialog_box_quantity_edit_text);
        quantityEditText.setText(Double.toString(dish.getQuantity()));

        dishImageView = (ImageView)dialogBox.findViewById(R.id.chef_edit_dish_dialog_box_image_view);
        dishImageView.setImageBitmap(dish.getThumbnailImage());
    }
    public void show()
    {
        this.dialogBox.show();
    }
}
