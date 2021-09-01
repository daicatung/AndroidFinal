package com.example.androidfinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfinal.model.User;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class EditUser extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_SHARE_SAVE_DATA = "MyObjectUser";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button mBtnCancel, mBtnDone;
    private EditText mTvNameUser, mTvDateUser, mTvSexUser, mTvEmailUser;
    private Calendar mCalendar;
    private ImageView mImgUser;
    private String mCurrentPhotoPath;
    private Bitmap mImageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        init();

        mBtnCancel.setOnClickListener((View.OnClickListener) this);
        mBtnDone.setOnClickListener((View.OnClickListener) this);
        mTvDateUser.setOnClickListener((View.OnClickListener) this);
        mImgUser.setOnClickListener(this);

//        Gson gson = new Gson();
//        String json = mSharedPreferencesUser.getString(KEY_SHARE_SAVE_DATA, "");
//        User user = gson.fromJson(json, User.class);


    }
    private void init(){
        mBtnCancel = findViewById(R.id.activity_edit_user_btn_cancel);
        mBtnDone = findViewById(R.id.activity_edit_user_btn_done);
        mTvNameUser = findViewById(R.id.activity_edit_user_tv_name_user);
        mTvDateUser = findViewById(R.id.activity_edit_user_tv_date_user);
        mTvSexUser = findViewById(R.id.activity_edit_user_tv_sex_user);
        mTvEmailUser = findViewById(R.id.activity_edit_user_tv_email_user);
        mImgUser = findViewById(R.id.activity_edit_user_img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_edit_user_btn_cancel:
                finish();
            case R.id.activity_edit_user_btn_done:
                clickBtnDone();
                break;
            case R.id.activity_edit_user_img:
                clickImgAvatar();
                break;
            case R.id.activity_edit_user_tv_date_user:
                clickTvDate();
                break;
            default:
                break;
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "picture";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                mImgUser.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void clickBtnDone(){
        if(!mTvNameUser.equals("") && !mTvDateUser.equals("") && !mTvEmailUser.equals("") && !mTvSexUser.equals("")){
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    byte[] b = baos.toByteArray();
//                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


            SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
            editor.putString("name_user", mTvNameUser.getText().toString());
            editor.putString("sex_user", mTvSexUser.getText().toString());
            editor.putString("email_user", mTvEmailUser.getText().toString());
            editor.putString("date_user", mTvDateUser.getText().toString());
            editor.commit();

            Toast.makeText(this, "Edit user completed !!!", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Please fill in all the information!!!", Toast.LENGTH_SHORT).show();
        }
    }
    private void clickTvDate(){
        mCalendar = Calendar.getInstance();
        int mYear = mCalendar.get(Calendar.YEAR);
        int mMonth = mCalendar.get(Calendar.MONTH);
        int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditUser.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        mTvDateUser.setText(dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void clickImgAvatar(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.i("TAG", "IOException");
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(EditUser.this,getApplicationContext().getPackageName() + ".provider", photoFile));
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }
}