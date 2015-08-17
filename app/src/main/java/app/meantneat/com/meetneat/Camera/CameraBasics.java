package app.meantneat.com.meetneat.Camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import app.meantneat.com.meetneat.Controller.Chef.EditEventDishesFragment;

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

//       takePictureIntent.putExtra("crop", "true");
//       takePictureIntent.putExtra("circleCrop", true);
//       takePictureIntent.putExtra("outputX",600);
//       takePictureIntent.putExtra("outputY", 600);
//        takePictureIntent.putExtra("aspectX", 1);
//        takePictureIntent.putExtra("aspectY", 1);
//        takePictureIntent.putExtra("scale", true);
//       takePictureIntent.putExtra("return-data", true);

        if (takePictureIntent.resolveActivity(((Activity)context).getPackageManager()) != null) {
            f.getParentFragment().startActivityForResult(takePictureIntent, 1);
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

                //imageBitmaps[0] = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(image));
                //imageBitmaps[0] = decodeSampledBitmapFromBitmap(image,400,400);



            float width  =imageBitmaps[0].getWidth();
            float height = imageBitmaps[0].getHeight();
            float ratio = width/height;
            imageBitmaps[1] = ThumbnailUtils.extractThumbnail(imageBitmaps[0],(int)(300*ratio),300);
            image.delete();

        }
        return imageBitmaps;
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
//jn
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    public static void setImageViewWithFadeAnimation(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }
}


