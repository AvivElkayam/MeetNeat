package app.meantneat.com.meetneat.Camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import app.meantneat.com.meetneat.Controller.Chef.EditEventDishesFragment;
import app.meantneat.com.meetneat.R;

/**
 * Created by DanltR on 09/06/2015.
 */
public class CameraBasics {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final int REQUEST_LOAD_IMAGE = 3;

    static final int CROP = 3;
    Uri picUri;
    File image;
    EditEventDishesFragment fragment;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    Context context;
    String mCurrentPhotoPath;


    public void setFragment(EditEventDishesFragment fragment) {
        this.fragment = fragment;
    }


    public CameraBasics() {
    }

    public void dispatchTakePictureIntent(Context context)
    {
        this.context = context;
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile("temp",".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        picUri = Uri.fromFile(image);


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(image));



        if (takePictureIntent.resolveActivity(((Activity)context).getPackageManager()) != null) {
            fragment.getParentFragment().startActivityForResult(takePictureIntent, 1);
        }
    }

    public Bitmap[] myOnActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap[] imageBitmaps = new Bitmap[2];

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode==((Activity)context).RESULT_OK) {


                //imageBitmaps[0] = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(image));
                imageBitmaps[0] = decodeSampledBitmapFromBitmap(image,400,400);



            float width  =imageBitmaps[0].getWidth();
            float height = imageBitmaps[0].getHeight();
            float ratio = width/height;
            imageBitmaps[1] = ThumbnailUtils.extractThumbnail(imageBitmaps[0],(int)(300*ratio),300);
            image.delete();

        }




        if (requestCode == REQUEST_LOAD_IMAGE && resultCode==((Activity)context).RESULT_OK) {
            Uri selectedImage = data.getData();
            picUri = selectedImage;
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = context.getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Log.w("path of image from gallery......******************.........", picturePath + "");


            imageBitmaps[0] = decodeSampledBitmapFromBitmap(new File(picturePath),400,400);

            float width  =imageBitmaps[0].getWidth();
            float height = imageBitmaps[0].getHeight();
            float ratio = width/height;
            imageBitmaps[1] = ThumbnailUtils.extractThumbnail(imageBitmaps[0], (int) (300 * ratio), 300);
            //viewImage.setImageBitmap(thumbnail);
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

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static byte[] bitmapToByteArr(Bitmap b)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        b.compress(Bitmap.CompressFormat.JPEG,100,baos);
        return baos.toByteArray();




    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromBitmap(File imageFile,
                                                int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getPath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bit = BitmapFactory.decodeFile(imageFile.getPath(), options);


        //Rotation for old devices

        try {
            bit = rotateImageIfRequired(bit, picUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bit;
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

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public static int dpToPX(Context context,int dp)
    {


        float density = context.getResources().getDisplayMetrics().density;
        float px = 96 * density;
        return (int)px;
    }

}


