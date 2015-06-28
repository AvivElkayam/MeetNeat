package app.meantneat.com.meetneat.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import app.meantneat.com.meetneat.Camera.CameraBasics;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;

public class SignInActivity extends ActionBarActivity {
    private EditText userNameEditText,emailEditText,passwordEditText;
    private ImageView userImage;
    private Button signInButton;
    private Bitmap userImageBitmap;
    private CameraBasics cameraBasics;
    private ProgressDialog progressBar;
    private TextView signedInTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity_layout);
        initViews();
    }

    private void initViews() {
        signInButton = (Button)findViewById(R.id.sign_in_activity_sign_in_button_id);
        userNameEditText = (EditText)findViewById(R.id.sign_in_activity_user_name_text_field_id);
        emailEditText = (EditText)findViewById(R.id.sign_in_activity_email_text_field_id);
        passwordEditText = (EditText)findViewById(R.id.sign_in_activity_password_field_id);
        signedInTextView = (TextView)findViewById(R.id.sign_in_activity_signedin_text_field_id);
        signedInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        userImage = (ImageView)findViewById(R.id.sign_in_activity_user_image);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //88888888// start camera
                dispatchTakePictureIntent();

            }
        });
        //progressBar = (ProgressBar)findViewById(R.id.sign_in_progress_bar);

        //progressBar.setProgress(0);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar=new ProgressDialog(SignInActivity.this);
                progressBar.setMessage("Signing in...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setIndeterminate(false);
                progressBar.show();

                signIn();
            }
        });
    }
    public interface SignUpCallback
    {
        public void onResult(String s);
    }
    private void signIn() {
        String name,email,password;
        name = userNameEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        if(userImageBitmap==null)
            userImageBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.chef_48_red);
        if(!name.equals("") && !password.equals("") && !email.equals("")) {
            MyModel.getInstance().getModel().signUpToMeetNeat(name, email, password, userImageBitmap, new SignUpCallback() {
                @Override
                public void onResult(String s) {
                    if (s.equals("Yes")) {
                        progressBar.hide();
                        Intent intent = new Intent(SignInActivity.this, MainTabActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(SignInActivity.this, s, Toast.LENGTH_SHORT);
                }
            });
        }
        else
        {
            Toast.makeText(SignInActivity.this, "Please fill in all details.", Toast.LENGTH_SHORT);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void dispatchTakePictureIntent() {
//        cameraBasics.setF(this);
//
//        cameraBasics.dispatchTakePictureIntent(this);
////        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//        //          startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
////        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //New image for a dish
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            bitmapArray = cameraBasics.myOnActivityResult(requestCode, resultCode, data);
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... params) {
////                    dishArrayList.get(currentPosition).setFullsizeImg(bitmapToByteArr(bitmapArray[0])); //Full size to Bytearray
////
////                    dishArrayList.get(currentPosition).setThumbnailImg(bitmapToByteArr(bitmapArray[1])); //Thumbnail to Bytearray
//                    newDish.setFullsizeImg(bitmapToByteArr(bitmapArray[0]));
//                    newDish.setThumbnailImg(bitmapToByteArr(bitmapArray[1]));
//                    return null;
//
//                }
//            }.execute();
//            addDishImageView.setImageBitmap(bitmapArray[1]);
//        }
//    }
static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //userImage.setImageBitmap(imageBitmap);
            userImageBitmap=imageBitmap;
            userImage.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            userImage.setBackground(new BitmapDrawable(imageBitmap));
        }
    }
}
