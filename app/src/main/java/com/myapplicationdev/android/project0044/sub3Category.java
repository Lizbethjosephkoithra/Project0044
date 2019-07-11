package com.myapplicationdev.android.project0044;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sub3Category extends AppCompatActivity {

    Toolbar tbr;
    ImageView imageView;
    EditText imageName;
    Button btnSave;
    Uri image_uri;
    boolean cameraFlag;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

//    private static final int CAPTURE_IMAGE = 0;
//    private static final int SELECT_PICTURE = 1;

    Bitmap thumbnail;

    String imageArr[] = {};
    String nameArr[] = {};
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub3_category);

        Intent intnt = getIntent();

        tbr = (Toolbar) findViewById(R.id.my_toolbar3Sub);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageName = (EditText) findViewById(R.id.imageEdit);
        btnSave = (Button) findViewById(R.id.imageSave);

        tbr.setTitle(intnt.getStringExtra("subCat"));

        loadName();
        loadImage();

        setSupportActionBar(tbr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(sub3Category.this);
                builder.setTitle("Enter New Name");

                // Set up the input
                final EditText input = new EditText(sub3Category.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
//                input.setGravity(Gravity.CENTER);
                input.setText(imageName.getText().toString());

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (input.getText().toString() != null) {
                            imageName.setText(input.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getDrawable() != null && imageName.getText().toString() != "") {

//                    btnSave.setClickable(true);
//                    btnDelete.setClickable(true);

//                    Intent i = getIntent();
//
//                    imageView.invalidate();
//                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//                    Bitmap bitmap = drawable.getBitmap();
//
//                    SharedPreferences myPrefrence = getPreferences(MODE_PRIVATE);
//                    SharedPreferences.Editor editor = myPrefrence.edit();
//                    editor.putString(i.getStringExtra("subCat"), imageName.getText().toString());
//                    editor.putString(i.getStringExtra("subCategory"), encodeToBase64(bitmap));
//                    editor.commit();
//                } else {
//                    btnSave.setClickable(false);
//                    btnDelete.setClickable(false);
//                }
                    storeImage(thumbnail);
                    storeName(imageName.getText().toString());

                    Intent intnt = getIntent();

                    SharedPreferences myPrefrence = PreferenceManager.getDefaultSharedPreferences(sub3Category.this);
                    SharedPreferences.Editor editor = myPrefrence.edit();
                    editor.putString("delete", intnt.getStringExtra("subCategory"));
//                    Toast.makeText(this,name,Toast.LENGTH_LONG).show();
                    editor.commit();

                    onBackPressed();
                }
            }
        });


    }

    public void storeName(String name) {
        Intent intnt = getIntent();

        SharedPreferences myPrefrence = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = myPrefrence.edit();
        editor.putString(intnt.getStringExtra("subCategory"), name);
        Toast.makeText(this,name,Toast.LENGTH_LONG).show();
        editor.commit();
    }


//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAPTURE_IMAGE) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            storeImage(thumbnail);
//        } else if (requestCode == SELECT_PICTURE) {
//            Uri mImageUri = data.getData();
//            Bitmap thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
//            storeImage(thumbnail);
//        }
//    }


    private void storeImage(Bitmap thumbnail) {
        Intent intnt = getIntent();

        // Removing image saved earlier in shared prefernces
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = shre.edit();
        edit.remove(intnt.getStringExtra("subCat"));
        edit.commit();

        //        if (thumbnail.equals(null)) {
////            Toast.makeText(this, "empty", Toast.LENGTH_LONG).show();
//            Log.d("Editable", "empty");
//        } else {
////            Toast.makeText(this,"not empty",Toast.LENGTH_LONG).show();
//            Log.d("Editable", "not empty");
//
//        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            Toast.makeText(this, "Stored Image", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(thumbnail);
        byte[] b = bytes.toByteArray();
        byte[] encodedImage = Base64.encode(b, Base64.DEFAULT);
        String encoded = Base64.encodeToString(encodedImage,Base64.DEFAULT);
        //Toast.makeText(this, encoded, Toast.LENGTH_LONG).show();

        //saving image to shared preferences
//        edit.putString(intnt.getStringExtra("subCatt"), encodedImage);
        edit.putString(intnt.getStringExtra("subCat"), encoded);

        edit.apply();
    }


    protected void loadImage() {
        Intent intnt = getIntent();

//        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
//        String bitmap = data.getString(in.getStringExtra("subCategory"), "");
//        return bitmap;

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
//        String previouslyEncodedImage = shre.getString(intnt.getStringExtra("subCatt"), "");
        String previouslyEncodedImage = shre.getString(intnt.getStringExtra("subCat"), "");

//        Toast.makeText(this, previouslyEncodedImage, Toast.LENGTH_SHORT).show();

//        if (!previouslyEncodedImage.equalsIgnoreCase("")) {
//            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
////            Toast.makeText(this, bitmap+"", Toast.LENGTH_LONG).show();
//            imageView.setImageBitmap(bitmap);
//        }
    }

    protected void loadName() {
        Intent intnt = getIntent();

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        String name = data.getString(intnt.getStringExtra("subCategory"), "");
        imageName.setText(name);
    }



//    public static Bitmap decodeBase64(String input) {
//
//        byte[] decodedByte = Base64.decode(input, 0);
//        return BitmapFactory
//                .decodeByteArray(decodedByte, 0, decodedByte.length);
//    }
//
//    public String encodeToBase64(Bitmap image) {
//        Bitmap immage = image;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] b = baos.toByteArray();
//        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
//
//        Toast.makeText(this, "Image Log: " + imageEncoded, Toast.LENGTH_SHORT).show();
//        return imageEncoded;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_camera:
                showImageImportDialog();
                return true;
        }
        return super.

                onOptionsItemSelected(item);
    }

    private void showImageImportDialog() {
        {
            //items to display in dialog
            String items[] = {"Take a new Photo", "Import from Gallery"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            //set title
            dialog.setTitle("Select Image");
            dialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    if (i == 0) {
                        pickCamera();

                    }
                    if (i == 1) {
                        pickGallery();
                    }
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.create().show();  //show dialog
        }
    }

    private void pickGallery() {

        cameraFlag = false;

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {

        cameraFlag = true;

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Pic");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intnt = getIntent();
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }

            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
//                if (data.getData() == null) {
//                    image_uri = data.getData();
//                }
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                image_uri = result.getUri();
                if (cameraFlag == true) {
                    try {
                        thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
                        Toast.makeText(this, "IF", Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    thumbnail = (Bitmap) data.getExtras().get(intnt.getStringExtra("subCat"));
                    Toast.makeText(this, "else", Toast.LENGTH_LONG).show();
                }
                imageView.setImageURI(image_uri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    final StringBuilder sb = new StringBuilder();


                    for (int i = 0; i < items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    //askChoice();

                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }
    }
}
