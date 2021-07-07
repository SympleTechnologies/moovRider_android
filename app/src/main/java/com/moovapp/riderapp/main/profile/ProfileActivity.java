package com.moovapp.riderapp.main.profile;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.GetFilePath;
import com.moovapp.riderapp.utils.LMTBaseActivity;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.SelectCollegeResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdateProfilePicResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdateProfileResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewProfileResponseModel;
import com.moovapp.riderapp.utils.spinnerAdapter.WhiteSpinnerLeftAdapter;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Lijo Mathew Theckanal on 18-Jul-18.
 */

public class ProfileActivity extends LMTBaseActivity implements Validator.ValidationListener {

    private final int VIEW_PROFILE_API = 1;
    private final int UPDATE_PROFILE_PIC_API = 2;
    private final int LIST_COLLEGES_API = 3;
    private final int EDIT_PROFILE_API = 10;
    private static final int SELECT_PICTURE = 4;
    private static final int CAMERA_REQUEST = 5;
    private static final int PIC_CROP = 6;
    private static final int ASK_WRITE_PERMISSION = 7;
    private static final int ASK_CAMERA_PERMISSION = 8;
    private static final int ASK_CAMERA_WRITE_PERMISSION = 9;
    private static final int PROFILE_EDIT_SUCCESS_DIALOG = 11;

//    @BindView(R.id.cardViewEdit)
//    CardView cardViewEdit;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @NotEmpty(trim = true)
    @BindView(R.id.edFirstName)
    EditText edFirstName;
    @NotEmpty(trim = true)
    @BindView(R.id.edLastName)
    EditText edLastName;
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.spinnerInstitution)
    Spinner spinnerInstitution;
    @BindView(R.id.edUniversity)
    EditText edUniversity;
    @BindView(R.id.rlSpinnerUniversity)
    RelativeLayout rlSpinnerUniversity;
    @BindView(R.id.llUniversity)
    LinearLayout llUniversity;
    @BindView(R.id.edPhoneNumber)
    EditText edPhoneNumber;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.profileImage)
    CircleImageView profileImage;
    private File imageFile;
    private File compressedImageFile;
    private String selectedImagePath = "";
    private GetFilePath getFilePath;
    private SelectCollegeResponseModel.DataEntity selectCollegeDataEntity;
    private Validator validator;

    private boolean isInEditMode = false;
    private List<String> collegeList;
    private List<String> collegeIdList;
    private String selectedCollegeId = "";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);
        getFilePath = new GetFilePath(getApplicationContext());
        validator = new Validator(this);
        validator.setValidationListener(this);
        callViewProfileApi();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.profileImage)
    public void profileImageClick() {
        showImageUploadDialog();
    }

    @OnClick(R.id.cardViewEditImage)
    public void cardViewEditImageClick() {
        showImageUploadDialog();
    }

    @OnClick(R.id.navBackButton)
    public void navBackButtonClick() {
        if (isInEditMode) {
            disableEditing();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (isInEditMode) {
            disableEditing();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.tvSubmit)
    public void tvSubmitClick() {
        validator.validate();
    }

    private void disableEditing() {
        tvTitle.setText("Profile");
        isInEditMode = false;
//        cardViewEdit.setVisibility(View.VISIBLE);
        tvSubmit.setVisibility(View.GONE);
        edFirstName.setClickable(false);
        edFirstName.setFocusable(false);
        edLastName.setClickable(false);
        edLastName.setFocusable(false);
        edEmail.setClickable(false);
        edEmail.setFocusable(false);
        edUniversity.setClickable(false);
        edUniversity.setFocusable(false);
        llUniversity.setVisibility(View.VISIBLE);
        rlSpinnerUniversity.setVisibility(View.GONE);
        edPhoneNumber.setClickable(false);
        edPhoneNumber.setFocusable(false);
    }

//    @OnClick(R.id.cardViewEdit)
//    public void cardViewEditClick() {
//        tvTitle.setText("Edit Profile");
//        isInEditMode = true;
//        cardViewEdit.setVisibility(View.GONE);
//        tvSubmit.setVisibility(View.VISIBLE);
//        edFirstName.setClickable(true);
//        edFirstName.setFocusable(true);
//        edFirstName.setFocusableInTouchMode(true);
//        edLastName.setClickable(true);
//        edLastName.setFocusable(true);
//        edLastName.setFocusableInTouchMode(true);
//        llUniversity.setVisibility(View.GONE);
//        rlSpinnerUniversity.setVisibility(View.VISIBLE);
////        edEmail.setClickable(true);
////        edEmail.setFocusable(true);
////        edEmail.setFocusableInTouchMode(true);
////        edUniversity.setClickable(true);
////        edUniversity.setFocusable(true);
////        edUniversity.setFocusableInTouchMode(true);
////        edPhoneNumber.setClickable(true);
////        edPhoneNumber.setFocusable(true);
////        edPhoneNumber.setFocusableInTouchMode(true);
//    }

    private void setCollegeSpinner() {
        collegeList = new ArrayList<>();
        collegeIdList = new ArrayList<>();
        int currentlySelectedItemPos = 0;
        for (int i = 0; i < selectCollegeDataEntity.getDetails().size(); i++) {
            collegeList.add(selectCollegeDataEntity.getDetails().get(i).getName());
            collegeIdList.add(selectCollegeDataEntity.getDetails().get(i).getId() + "");
            if (selectCollegeDataEntity.getDetails().get(i).getName().equals(edUniversity.getText().toString())) {
                currentlySelectedItemPos = i;
            }
        }
        WhiteSpinnerLeftAdapter classAdapter = new WhiteSpinnerLeftAdapter(this, R.layout.white_spinner_left_list_item, R.id.title, collegeList);
        spinnerInstitution.setAdapter(classAdapter);
        spinnerInstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCollegeId = collegeIdList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerInstitution.setSelection(currentlySelectedItemPos);
    }

    private void setValues(ViewProfileResponseModel.DataEntity data) {
        if (data.getUser_pic_url().trim().length() > 3) {
            Picasso.get().load(data.getUser_pic_url()).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(profileImage);
        }
        edFirstName.setText(data.getUser_details().getFirst_name()+ " " + data.getUser_details().getLast_name() );
//        edLastName.setText();
        edEmail.setText(data.getUser_details().getEmail());
        edUniversity.setText(data.getUser_details().getInstitution_name());
        edPhoneNumber.setText(data.getUser_details().getPhone_country() + data.getUser_details().getPhone());
    }

    private void showImageUploadDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_select_image_from);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout upload = (LinearLayout) dialog.findViewById(R.id.upload);
        final LinearLayout takeNewPicture = (LinearLayout) dialog.findViewById(R.id.takeNewPicture);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shouldAskPermission()) {
                    askPermissionForWrite();
                } else {
                    Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                    intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                    startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);
                }
                dialog.dismiss();
            }
        });
        takeNewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shouldAskPermission()) {
                    askPermissionCamera();
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img1.jpg");
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    private void askPermissionForWrite() {
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        requestPermissions(perms, ASK_WRITE_PERMISSION);
    }

    @TargetApi(23)
    private void askPermissionForWriteCamera() {
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        requestPermissions(perms, ASK_CAMERA_WRITE_PERMISSION);
    }

    @TargetApi(23)
    private void askPermissionCamera() {
        String[] perms = {"android.permission.CAMERA"};
        int permsRequestCode = ASK_CAMERA_PERMISSION;
        requestPermissions(perms, permsRequestCode);
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case ASK_WRITE_PERMISSION:
                boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (writeAccepted) {
                    Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                    intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                    startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);
                } else {
                    Toast.makeText(getBaseContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case ASK_CAMERA_PERMISSION:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    askPermissionForWriteCamera();
                } else {
                    Toast.makeText(getBaseContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case ASK_CAMERA_WRITE_PERMISSION:
                boolean cameraWriteAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraWriteAccepted) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img1.jpg");
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    Toast.makeText(getBaseContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == ProfileActivity.this.RESULT_OK) {
            try {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img1.jpg");
                imageFile = file;
                selectedImagePath = "test";
                System.out.println("===   path ====" + imageFile);
//                performCrop(Uri.fromFile(file));
                UCrop.Options options = new UCrop.Options();
                options.setLogoColor(getResources().getColor(R.color.colorPrimary));
                options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                options.setCropFrameColor(getResources().getColor(R.color.colorPrimary));
                options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                UCrop.of(Uri.fromFile(file), Uri.fromFile(file))
                        .withOptions(options)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(600, 600)
                        .start(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);
            try {
                System.gc();
                Uri selectedImageUri = Uri.fromFile(new File(images.get(0).path));
                selectedImagePath = getFilePath.getPath(selectedImageUri);
                imageFile = new File(selectedImagePath);
                System.out.println("Image Path : " + imageFile);
                UCrop.Options options = new UCrop.Options();
                options.setLogoColor(getResources().getColor(R.color.colorPrimary));
                options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                options.setCropFrameColor(getResources().getColor(R.color.colorPrimary));
                options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "imgCrop.jpg");
                UCrop.of(selectedImageUri, Uri.fromFile(file))
                        .withOptions(options)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(600, 600)
                        .start(this);
//                performCrop(selectedImageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap photo = decodeUriAsBitmap(resultUri);
            profileImage.setImageBitmap(photo);
            try {
                System.gc();
                selectedImagePath = getFilePath.getPath(resultUri);
                imageFile = new File(selectedImagePath);
                System.out.println("Image Path : " + imageFile);
                callUpdateProfilePicApi();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getApplicationContext().getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    public void onValidationSucceeded() {
        callEditProfileApi();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void callViewProfileApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ViewProfileResponseModel> call = apiService.viewProfile("view/details/user/" + appPrefes.getData(Constants.USER_ID));
                call.enqueue(new retrofit2.Callback<ViewProfileResponseModel>() {
                    @Override
                    public void onResponse(Call<ViewProfileResponseModel> call, Response<ViewProfileResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                appPrefes.SaveData(Constants.USER_PROFILE_PIC, response.body().getData().getUser_pic_url());
                                appPrefes.SaveData(Constants.USER_UNIVERSITY, response.body().getData().getUser_details().getInstitution_name());
                                appPrefes.SaveData(Constants.USER_UNIVERSITY_ID, response.body().getData().getUser_details().getInstitution_id()+"");
                                setValues(response.body().getData());
                                Log.d("profile", "onResponse: "+response.body().getData().getUser_pic_url());
                                callListCollegesApi();
                            } else {
                                showServerErrorAlert(VIEW_PROFILE_API);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(VIEW_PROFILE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewProfileResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VIEW_PROFILE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(VIEW_PROFILE_API);
            }
        } else {
            showNoInternetAlert(VIEW_PROFILE_API);
        }
    }

    private void callUpdateProfilePicApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                Random r = new Random();
                int i1 = r.nextInt(999999999);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Map<String, RequestBody> map = new HashMap<>();
                if (imageFile != null) {
                    RequestBody file = RequestBody.create(MediaType.parse("image/*"), imageFile);
                    map.put("image\"; filename=\"profile_image" + i1 + ".jpg\" ", file);
                } else {
                    RequestBody jobseekerImage = RequestBody.create(MediaType.parse("text/plain"), "");
                    map.put("image", jobseekerImage);
                }
//                RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), appPrefes.getData(Constants.USER_ID));
//                map.put("userid", userid);

                Call<UpdateProfilePicResponseModel> call = apiService.updateProfilePic(appPrefes.getData(Constants.USER_ID) , map);

                Log.d("profile", "callUpdateProfilePicApi: "+ map.toString());
                call.enqueue(new Callback<UpdateProfilePicResponseModel>() {
                    @Override
                    public void onResponse(Call<UpdateProfilePicResponseModel> call, Response<UpdateProfilePicResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getBaseContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                if (response.body().getData().getUser_pic_url().trim().length() > 3) {
                                    Picasso.get().load(response.body().getData().getUser_pic_url()).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(profileImage);
                                }
                                appPrefes.SaveData(Constants.USER_PROFILE_PIC, response.body().getData().getUser_pic_url());
                                HomeActivity.homeActivityActions.onProfileUpdate();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(UPDATE_PROFILE_PIC_API);
                            Log.d("profilerespr", "callUpdateProfilePicApi: "+ response.raw());
                            Log.d("profilerepe", "callUpdateProfilePicApi: "+ e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProfilePicResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(UPDATE_PROFILE_PIC_API);
                        Log.d("profilef", "callUpdateProfilePicApi: "+ t.toString());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(UPDATE_PROFILE_PIC_API);
            }
        } else {
            showNoInternetAlert(UPDATE_PROFILE_PIC_API);
        }
    }

    private void callListCollegesApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<SelectCollegeResponseModel> call = apiService.selectCollege("");
                call.enqueue(new retrofit2.Callback<SelectCollegeResponseModel>() {
                    @Override
                    public void onResponse(Call<SelectCollegeResponseModel> call, Response<SelectCollegeResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                selectCollegeDataEntity = response.body().getData();
                                setCollegeSpinner();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(LIST_COLLEGES_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<SelectCollegeResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(LIST_COLLEGES_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(LIST_COLLEGES_API);
            }
        } else {
            showNoInternetAlert(LIST_COLLEGES_API);
        }
    }

    private void callEditProfileApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateProfileResponseModel> call = apiService.updateProfile(appPrefes.getData(Constants.USER_ID), edFirstName.getText().toString(), edLastName.getText().toString(), selectedCollegeId, "male");
                call.enqueue(new retrofit2.Callback<UpdateProfileResponseModel>() {
                    @Override
                    public void onResponse(Call<UpdateProfileResponseModel> call, Response<UpdateProfileResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                showRequestSuccessDialog("Success", "Profile details updated successfully!", "Ok", PROFILE_EDIT_SUCCESS_DIALOG);
                                edUniversity.setText(spinnerInstitution.getSelectedItem().toString());
                                appPrefes.SaveData(Constants.USER_FIRST_NAME, edFirstName.getText().toString());
                                disableEditing();
                                HomeActivity.homeActivityActions.onProfileUpdate();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(EDIT_PROFILE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProfileResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(EDIT_PROFILE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(EDIT_PROFILE_API);
            }
        } else {
            showNoInternetAlert(EDIT_PROFILE_API);
        }
    }

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case VIEW_PROFILE_API:
                callViewProfileApi();
                break;
            case UPDATE_PROFILE_PIC_API:
                callUpdateProfilePicApi();
                break;
            case LIST_COLLEGES_API:
                callListCollegesApi();
                break;
            case EDIT_PROFILE_API:
                callEditProfileApi();
                break;
        }
    }
}
