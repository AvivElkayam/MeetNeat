package app.meantneat.com.meetneat.Camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import app.meantneat.com.meetneat.Controller.EditEventDishesFragment;

/**
 * Created by DanltR on 09/06/2015.
 */
public class CameraBasics {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final int PIC_CROP = 2;
    static final int GALLERY = 3;
    Uri picUri;
    File image;
    EditEventDishesFragment f;
    Context context;


    public void setF(EditEventDishesFragment f) {
        this.f = f;
    }


    public CameraBasics() {
    }

    public void dispatchTakePictureIntent(Context context)
    {
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile("temp",".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.context = context;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(image));
        if (takePictureIntent.resolveActivity(((Activity)context).getPackageManager()) != null) {
            f.startActivityForResult(takePictureIntent, 1);
        }
    }

    public Bitmap[] myOnActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap[] imageBitmaps = new Bitmap[2];

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode==((Activity)context).RESULT_OK) {


            try {
                imageBitmaps[0] = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(image));
            } catch (IOException e) {
                e.printStackTrace();
            }

            float width  =imageBitmaps[0].getWidth();
            float height = imageBitmaps[0].getHeight();
            float ratio = width/height;
            imageBitmaps[1] = ThumbnailUtils.extractThumbnail(imageBitmaps[0],(int)(300*ratio),300);
            image.delete();

        }
        return imageBitmaps;
    }
}
