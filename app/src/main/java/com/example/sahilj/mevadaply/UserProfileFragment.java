package com.example.sahilj.mevadaply;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Profile";
    private final Activity activity;
    private final Bundle bundle;
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

    public UserProfileFragment(Activity activity, Bundle bundle) {
        this.activity = activity;
        this.bundle=bundle;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        initialization(view);

        //Open Profile Update Activity for new User

        if(bundle ==null || bundle.getInt("time", 1) != 1) {
            setUserDetails();
        }

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
                Glide.with(activity.getApplicationContext()).load(url).into(userImage);

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
                else if(etEmail.getText().toString().isEmpty())
                    etEmail.setError("Fill This!");
                else if(!pattern.matcher(etEmail.getText().toString()).matches())
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
                    Toast.makeText(activity, "You can't choose Image.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= MyUtilities.checkPermission(activity);

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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                image = data.getData();
                userImage.setImageURI(image);

                destination = new File(MyUtilities.getPath(image,activity.getApplicationContext()));

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

        Glide.with(activity.getApplicationContext()).load(destination).into(userImage);
        Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show();
        userImage.setImageBitmap(thumbnail);
    }

    public void updateData(View view) {

        MultipartBody.Part fileToUpload = null;
        RequestBody fname = RequestBody.create(MediaType.parse("text/plain"), etUserFirstName.getText().toString());
        RequestBody lname = RequestBody.create(MediaType.parse("text/plain"), etUserLastName.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString());
        RequestBody area = RequestBody.create(MediaType.parse("text/plain"), etArea.getText().toString());
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), etCity.getText().toString());
        RequestBody number = RequestBody.create(MediaType.parse("text/plain"), MyUtilities.getPhoneNumber());


        if(destination!=null){
            Log.v(TAG,"File Created + " + destination.getName());
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),destination);
            fileToUpload = MultipartBody.Part.createFormData("file",destination.getName(),requestBody);
        }

        Call<UpdateResult> call = apiInterface.updateUserData(fileToUpload,fname,lname,email,number,area,city);

        call.enqueue(new Callback<UpdateResult>() {
            @Override
            public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
                if(response.body().isSuccess()){
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateResult> call, Throwable t) {
                Log.v(TAG,"Fail "+t.getMessage(),t);
            }
        });
    }
}
