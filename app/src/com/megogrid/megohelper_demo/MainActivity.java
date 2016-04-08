package com.megogrid.megohelper_demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.megogrid.megohelper.Handler.MegoHelperException;
import com.megogrid.megohelper.Handler.MegoHelperManager;
import com.megogrid.megohelper_demo.db.ImageDetail;
import com.megogrid.meuserdemo1.library.Constants;
import com.megogrid.meuserdemo1.library.Toaster;
import com.megogrid.meuserdemo1.library.UriToUrl;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private Animation animation;
    private RelativeLayout top_holder;
    private RelativeLayout bottom_holder;
    private RelativeLayout step_number;
    private TextView account, FileBackup,
            FileRestore, PrefBackup, PrefRestore, Configuration, Settings, Help, logout;
    private ImageView logout_line;
    private Uri imageUri;
    private boolean click_status = true;
    private CustomDrawerLayout drawer_layout;
    private ImageView imageView_back;
    private RelativeLayout whatYouWantInLeftDrawer;
    private static final String YOUR_BOX_ID = "KPDFC20UD";

    private static String GOOGLE_ID = " 720282980579-smn98g7o513htrhf6nnapth4t6bfgklu.apps.googleusercontent.com";
    private static final int FILE_CHOOSER = 3;
    private static final int VERSION_CHOOSER = 9;
    private static final String PREFERENCE_NAME = "AppBackup";
    private ArrayList<ImageDetail> detail;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private final int pref_backup_version = 1;
    private final int file_backup_version = 2;
    private String filetype = null;
    private String fileurl = null;
    private TextView help_view, Tutorial, ratingreview, faq, feedback, userFAQ1, UserSurvay1;
    ; MegoHelperManager megoHelperManager;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        megoHelperManager = new MegoHelperManager(MainActivity.this);
        if (getIntent().getBooleanExtra("Exit me", false)) {
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }

        whatYouWantInLeftDrawer = (RelativeLayout) findViewById(R.id.whatYouWantInLeftDrawer);
        drawer_layout = (CustomDrawerLayout) findViewById(R.id.drawer_layout1);
        top_holder = (RelativeLayout) findViewById(R.id.camera_button);
        bottom_holder = (RelativeLayout) findViewById(R.id.image_button);
        imageView_back = (ImageView) findViewById(R.id.imgViewForMenu1);
        userFAQ1 = (TextView) findViewById(R.id.UserFaq1);
        UserSurvay1 = (TextView) findViewById(R.id.survay1);


        imageView_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer_layout.isDrawerOpen(whatYouWantInLeftDrawer)) {
                    drawer_layout.closeDrawer(whatYouWantInLeftDrawer);
                } else {
                    drawer_layout.openDrawer(whatYouWantInLeftDrawer);
                }
            }
        });

        help_view = (TextView) findViewById(R.id.help_v);
        Tutorial = (TextView) findViewById(R.id.Tutorial);
        ratingreview = (TextView) findViewById(R.id.ratingreview);
        faq = (TextView) findViewById(R.id.faq);
        feedback = (TextView) findViewById(R.id.feedback);

        help_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    megoHelperManager.showHelp();
                } catch (MegoHelperException e) {
                    e.printStackTrace();
                }

            }
        });

        Tutorial.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                try {
                    megoHelperManager.showTutorial();
                } catch (MegoHelperException e) {
                    e.printStackTrace();
                }
            }
        });

        ratingreview.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    megoHelperManager.showrating();
                } catch (MegoHelperException e) {
                    e.printStackTrace();
                }

            }
        });
        faq.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    megoHelperManager.showFAQ();
                } catch (MegoHelperException e) {
                    e.printStackTrace();
                }

            }
        });
        feedback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    megoHelperManager.showFeedback();
                } catch (MegoHelperException e) {
                    e.printStackTrace();
                }

            }
        });

        UserSurvay1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                try {
                    megoHelperManager.showUserSurvey();
                } catch (MegoHelperException e) {
                    e.printStackTrace();
                }

            }
        });

        userFAQ1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                try {
                    megoHelperManager.showUserFaq();
                } catch (MegoHelperException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        flyIn();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        flyIn();
    }

    private BroadcastReceiver finishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();

        }
    };

    @Override
    protected void onStop() {
        overridePendingTransition(0, 0);
        super.onStop();
    }

    private void displayGallery() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                && !Environment.getExternalStorageState().equals(
                Environment.MEDIA_CHECKING)) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, Constants.REQUEST_GALLERY);
        } else {
            Toaster.make(getApplicationContext(), R.string.no_media);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CHOOSER) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                try {
                    fileurl = getPath(this, uri);
                    filetype = "backup";

                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else if (requestCode == Constants.REQUEST_CAMERA) {
            try {
                if (resultCode == RESULT_OK) {
                    displayPhotoActivity(1);
                } else {
                    UriToUrl.deleteUri(getApplicationContext(), imageUri);
                }
            } catch (Exception e) {
                Toaster.make(getApplicationContext(),
                        R.string.error_img_not_found);
            }
        } else if (resultCode == RESULT_OK
                && requestCode == Constants.REQUEST_GALLERY) {
            try {
                imageUri = data.getData();
                displayPhotoActivity(2);
            } catch (Exception e) {
                Toaster.make(getApplicationContext(),
                        R.string.error_img_not_found);
            }
        } else if (resultCode == RESULT_OK
                && requestCode == VERSION_CHOOSER) {
            Long version = Long.parseLong(data.getStringExtra("result"));
            try {
                if (filetype.equalsIgnoreCase("backup")) {

                } else if (filetype.equalsIgnoreCase("restore")) {

                }
            } catch (Exception e) {
                Toaster.make(getApplicationContext(),
                        R.string.error_img_not_found);
            }
        }
    }

    @SuppressWarnings("unused")
    private void displayCamera() {
        imageUri = getOutputMediaFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }

    private Uri getOutputMediaFile() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Camera Pro");
        values.put(MediaStore.Images.Media.DESCRIPTION, "www.appsroid.org");
        return getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void displayPhotoActivity(int source_id) {
        Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
        intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
        intent.setData(imageUri);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private void flyOut(final String method_name) {
        if (click_status) {
            click_status = false;

            animation = AnimationUtils.loadAnimation(this,
                    R.anim.holder_top_back);
            top_holder.startAnimation(animation);

            animation = AnimationUtils.loadAnimation(this,
                    R.anim.holder_bottom_back);
            bottom_holder.startAnimation(animation);

            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    callMethod(method_name);
                }
            });
        }
    }

    private void callMethod(String method_name) {
        if (method_name.equals("finish")) {
            overridePendingTransition(0, 0);
            finish();
        } else {
            try {
                Method method = getClass().getDeclaredMethod(method_name);
                method.invoke(this, new Object[]{});
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        flyOut("finish");
        super.onBackPressed();
    }

    public void startGallery(View view) {
        flyOut("displayGallery");
    }

    public void startCamera(View view) {
        flyOut("displayCamera");
    }

    private void flyIn() {
        click_status = true;

        animation = AnimationUtils.loadAnimation(this, R.anim.holder_top);
        top_holder.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.holder_bottom);
        bottom_holder.startAnimation(animation);
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"), FILE_CHOOSER);
    }

    //    public String getPath(Context context, Uri contentURI) throws URISyntaxException {
//        String result;
//        Cursor cursor = context.getContentResolver().query(contentURI, null,
//                null, null, null);
//        if (cursor == null) { // Source is Dropbox or other similar local file
//            // path
//            result = contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor
//                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            result = cursor.getString(idx);
//            cursor.close();
//        }
//        return result;
//    }
    public String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


}
