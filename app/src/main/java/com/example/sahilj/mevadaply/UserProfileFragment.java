package com.example.sahilj.mevadaply;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.Responses.Result;
import com.example.sahilj.mevadaply.Responses.UpdateResult;
import com.example.sahilj.mevadaply.Responses.UserDetails;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.firebase.ui.auth.data.model.User;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Profile";
    private CircularImageView userImage;
    private EditText etCity;
    private EditText etUserFirstName;
    private EditText etUserLastName;
    private EditText etEmail;
    private EditText etArea;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private File destination;
    private Uri image;
    private Button btnUpdate;
    private UserDetails details;
    private Pattern pattern;
    private String imgPath;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        initialization(view);

        Bundle bundle = getArguments();

        if (bundle != null) {
            if(bundle.getInt(MyConstants.TIME, 1) != 1) {
                setUserDetails();
            }
        }


        //Open Profile Update Activity for new User

        userImage.setOnClickListener(this);

        btnUpdate.setOnClickListener(this);
        pattern = Patterns.EMAIL_ADDRESS;

        etUserFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etUserFirstName.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etUserLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etUserLastName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void setUserDetails() {
        if(MyConstants.USER_DETAILS!=null) {
            details = MyConstants.USER_DETAILS;
            String url = details.getUser_pic_url();
            String fname = details.getUser_fname();
            String lname = details.getUser_lname();
            String email = details.getUser_email();
            String area = details.getUser_area();
            String city = details.getUser_city();

            if (destination == null)
                Glide.with(UserProfileFragment.this).load(url).into(userImage);

            etUserFirstName.setText(fname);
            etUserLastName.setText(lname);
            etEmail.setText(email);
            etArea.setText(area);
            etCity.setText(city);
        }
    }

    private void initialization(View view) {
        userImage= view.findViewById(R.id.imgUserProfile);
        etUserFirstName = view.findViewById(R.id.etFirstName);
        etUserLastName  = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etArea = view.findViewById(R.id.etArea);
        etCity = view.findViewById(R.id.etCity);
        btnUpdate = view.findViewById(R.id.btnUpdate);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgUserProfile:
                selectImage();
                break;
            case R.id.btnUpdate:
                if(etUserFirstName.getText().toString().isEmpty())
                    etUserFirstName.setError("Fill This!");
                else if(etUserLastName.getText().toString().isEmpty())
                    etUserLastName.setError("Fill This!");
                else if(!etEmail.getText().toString().isEmpty() && !pattern.matcher(etEmail.getText().toString()).matches())
                    etEmail.setError("Invalid Email!");
                else
                    updateData(view);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MyUtilities.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                    Toast.makeText(getActivity(), "You can't choose Image.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= MyUtilities.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        }

    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE){

                if (data != null && data.getData() != null) {
                    boolean isImageFromGoogleDrive = false;
                    Uri uri = data.getData();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
                            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                                String docId = DocumentsContract.getDocumentId(uri);
                                String[] split = docId.split(":");
                                String type = split[0];

                                if ("primary".equalsIgnoreCase(type)) {
                                    imgPath = Environment.getExternalStorageDirectory() + "/" + split[1];
                                }
                                else {
                                    Pattern DIR_SEPORATOR = Pattern.compile("/");
                                    Set<String> rv = new HashSet<>();
                                    String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
                                    String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
                                    String rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET");
                                    if(TextUtils.isEmpty(rawEmulatedStorageTarget))
                                    {
                                        if(TextUtils.isEmpty(rawExternalStorage))
                                        {
                                            rv.add("/storage/sdcard0");
                                        }
                                        else
                                        {
                                            rv.add(rawExternalStorage);
                                        }
                                    }
                                    else
                                    {
                                        String rawUserId;
                                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
                                        {
                                            rawUserId = "";
                                        }
                                        else
                                        {
                                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                                            String[] folders = DIR_SEPORATOR.split(path);
                                            String lastFolder = folders[folders.length - 1];
                                            boolean isDigit = false;
                                            try
                                            {
                                                Integer.valueOf(lastFolder);
                                                isDigit = true;
                                            }
                                            catch(NumberFormatException ignored)
                                            {
                                            }
                                            rawUserId = isDigit ? lastFolder : "";
                                        }
                                        if(TextUtils.isEmpty(rawUserId))
                                        {
                                            rv.add(rawEmulatedStorageTarget);
                                        }
                                        else
                                        {
                                            rv.add(rawEmulatedStorageTarget + File.separator + rawUserId);
                                        }
                                    }
                                    if(!TextUtils.isEmpty(rawSecondaryStoragesStr))
                                    {
                                        String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
                                        Collections.addAll(rv, rawSecondaryStorages);
                                    }
                                    String[] temp = rv.toArray(new String[rv.size()]);
                                    for (String aTemp : temp) {
                                        File tempf = new File(aTemp + "/" + split[1]);
                                        if (tempf.exists()) {
                                            imgPath = aTemp + "/" + split[1];
                                        }
                                    }
                                }
                            }
                            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                                String id = DocumentsContract.getDocumentId(uri);
                                Uri contentUri = ContentUris.withAppendedId( Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                                Cursor cursor = null;
                                String column = "_data";
                                String[] projection = { column };
                                try {
                                    cursor = getActivity().getContentResolver().query(contentUri, projection, null, null,
                                            null);
                                    if (cursor != null && cursor.moveToFirst()) {
                                        int column_index = cursor.getColumnIndexOrThrow(column);
                                        imgPath = cursor.getString(column_index);
                                    }
                                } finally {
                                    if (cursor != null)
                                        cursor.close();
                                }
                            }
                            else if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                                String docId = DocumentsContract.getDocumentId(uri);
                                String[] split = docId.split(":");
                                String type = split[0];

                                Uri contentUri = null;
                                if ("image".equals(type)) {
                                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                                } else if ("video".equals(type)) {
                                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                                } else if ("audio".equals(type)) {
                                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                                }

                                String selection = "_id=?";
                                String[] selectionArgs = new String[]{ split[1] };

                                Cursor cursor = null;
                                String column = "_data";
                                String[] projection = { column };

                                try {
                                    cursor = getActivity().getContentResolver().query(contentUri, projection, selection, selectionArgs, null);
                                    if (cursor != null && cursor.moveToFirst()) {
                                        int column_index = cursor.getColumnIndexOrThrow(column);
                                        imgPath = cursor.getString(column_index);
                                    }
                                } finally {
                                    if (cursor != null)
                                        cursor.close();
                                }
                            }
                            else if("com.google.android.apps.docs.storage".equals(uri.getAuthority()))   {
                                isImageFromGoogleDrive = true;
                            }
                        }
                        else if ("content".equalsIgnoreCase(uri.getScheme())) {
                            Cursor cursor = null;
                            String column = "_data";
                            String[] projection = { column };

                            try {
                                cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    int column_index = cursor.getColumnIndexOrThrow(column);
                                    imgPath = cursor.getString(column_index);
                                }
                            }
                            finally {
                                if (cursor != null)
                                    cursor.close();
                            }
                        }
                        else if ("file".equalsIgnoreCase(uri.getScheme())) {
                            imgPath = uri.getPath();
                        }
                    }

                    if(isImageFromGoogleDrive)  {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                            btnUpdate.setEnabled(false);
                            new fileFromBitmap(bitmap, getActivity()).execute();
                            userImage.setImageURI(uri);
                            //userImage.setImageBitmap(bitmap);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else    {
                        destination = new File(imgPath);
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(destination.getAbsolutePath(),bmOptions);
                        //userImage.setImageBitmap(bitmap);
                        Glide.with(getActivity().getBaseContext()).load(bitmap).into(userImage);
                    }

                }

                /*image = data.getData();
                userImage.setImageURI(image);

                destination = new File(MyUtilities.getPath(image,getContext()));
*/
                //onSelectFromGalleryResult(data);
            }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert thumbnail != null;
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            if(destination.createNewFile()) {
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Glide.with(UserProfileFragment.this).load(destination).into(userImage);
        Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
        userImage.setImageBitmap(thumbnail);
    }

    public void updateData(View view) {
        btnUpdate.setEnabled(false);
        MultipartBody.Part fileToUpload = null;
        RequestBody fname = RequestBody.create(MediaType.parse("text/plain"), etUserFirstName.getText().toString());
        RequestBody lname = RequestBody.create(MediaType.parse("text/plain"), etUserLastName.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString());
        RequestBody area = RequestBody.create(MediaType.parse("text/plain"), etArea.getText().toString());
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), etCity.getText().toString());
        RequestBody number = RequestBody.create(MediaType.parse("text/plain"), MyUtilities.getPhoneNumber());


        if(destination!=null){
            Log.v(TAG,"File Created + " + destination.getName());
            try {
                destination = new Compressor(getContext()).compressToFile(destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),destination);
            fileToUpload = MultipartBody.Part.createFormData("file",destination.getName(),requestBody);
        }

        Call<UpdateResult> call = apiInterface.updateUserData(fileToUpload,fname,lname,email,number,area,city);

        call.enqueue(new Callback<UpdateResult>() {
            @Override
            public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
                btnUpdate.setEnabled(true);
                if(response.body().isSuccess()){
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateResult> call, Throwable t) {
                btnUpdate.setEnabled(true);
                Log.v(TAG,"Fail "+t.getMessage(),t);
            }
        });
    }

    File file;

    public class fileFromBitmap extends AsyncTask<Void, Integer, String> {

        Context context;
        Bitmap bitmap;
        String path_external = Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg";

        public fileFromBitmap(Bitmap bitmap, Context context) {
            this.bitmap = bitmap;
            this.context= context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before executing doInBackground
            // update your UI
            // exp; make progressbar visible
        }

        @Override
        protected String doInBackground(Void... params) {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            file  = new File(context.getCacheDir(), "temporary_file.jpg");
            try {
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.flush();
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // back to main thread after finishing doInBackground
            // update your UI or take action after
            // exp; make progressbar gone

            sendFile(file);

        }
    }

    private void sendFile(File file) {
        btnUpdate.setEnabled(true);
        destination=file;
    }
}

