package app.meantneat.com.meetneat.Controller.Chef;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import app.meantneat.com.meetneat.Camera.CameraBasics;
import app.meantneat.com.meetneat.Entities.Dish;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 8/5/15.
 */
public class AddDishDialogBox {
    private Context context;
    private Dialog dialogBox;
    private Dish dish;
    private CameraBasics cameraBasics = new CameraBasics();

    public CameraBasics getCameraBasics() {
        return cameraBasics;
    }

    public void setCameraBasics(CameraBasics cameraBasics) {
        this.cameraBasics = cameraBasics;
    }



    private EditText priceEditText,quantityEditText,titleEditText,descriptionEditText;

    public RoundedImageView getDishImageView() {
        return dishImageView;
    }

    public void setDishImageView(RoundedImageView dishImageView) {
        this.dishImageView = dishImageView;
    }

    private RoundedImageView dishImageView;
    private Button editDishButton;
    private String title,price,quantity,description;
    public AddDishDialogBox(Context context) {
        this.context = context;
        this.dish = new Dish();
        initDialogBox();
        initViews();

    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Dialog getDialogBox() {
        return dialogBox;
    }

    private void initDialogBox()
    {

        dialogBox = new Dialog(context);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.setContentView(R.layout.chef_add_dish_dialog_box_layout);
    }
    private void initViews()
    {
        titleEditText = (EditText)dialogBox.findViewById(R.id.chef_add_dish_dialog_box_title_edit_text);

        descriptionEditText = (EditText)dialogBox.findViewById(R.id.chef_add_dish_dialog_box_description_edit_text);

        priceEditText = (EditText)dialogBox.findViewById(R.id.chef_add_dish_dialog_box_price_edit_text);

        quantityEditText = (EditText)dialogBox.findViewById(R.id.chef_add_dish_dialog_box_quantity_edit_text);

        dishImageView = (RoundedImageView)dialogBox.findViewById(R.id.chef_add_dish_dialog_box_image_view);
        dishImageView.setImageBitmap(dish.getThumbnailImage());
        dishImageView.setScaleType(ImageView.ScaleType.CENTER);
        dishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dispatchTakePictureIntent();
                //cameraBasics.dispatchTakePictureIntent(context);

            }
        });

        editDishButton = (Button)dialogBox.findViewById(R.id.chef_add_dish_dialog_box_add_dish_button);
        editDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()==true) {
                    dish.setTitle(titleEditText.getText().toString());
                    dish.setDescriprion(descriptionEditText.getText().toString());
                    dish.setPrice(Double.parseDouble(priceEditText.getText().toString()));
                    dish.setQuantityLeft(Double.parseDouble(quantityEditText.getText().toString()));

                    dialogBox.dismiss();
                }
            }
        });
    }

    public void show()
    {
        this.dialogBox.show();
    }


    private void dispatchTakePictureIntent() {
        //cameraBasics.setFragment(EditEventDishesFragment.this); - sett on dialog initialize
        cameraBasics.dispatchTakePictureIntent(context);

    }
    private boolean validateInputs()
    {//true for good inputs, false for bad inputs
        if(titleEditText.getText().toString().isEmpty()==true)
        {
            Toast.makeText(this.context,"Please fill in a title",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(descriptionEditText.getText().toString().isEmpty()==true)
        {
            Toast.makeText(this.context,"Please fill in a description",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(priceEditText.getText().toString().isEmpty()==true)
        {
            Toast.makeText(this.context,"Please fill in a Price",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(quantityEditText.getText().toString().isEmpty()==true)
        {
            Toast.makeText(this.context,"Please fill in a quantity",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
