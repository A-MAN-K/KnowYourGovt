package com.example.adsg1.knowyourgovt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.R.drawable.ic_dialog_alert;

/**
 * Created by adsg1 on 4/16/2017.
 */

public class PhotoActivity extends AppCompatActivity {

    Official officialTobeSent = new Official();
    String addresToShow= null;
    private TextView photoLocation;
    private TextView photoDesignation;
    private TextView photoName;
    private ImageView imageViewBig;
    private String TAG = "PhotoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);

        Intent intent = getIntent();

        officialTobeSent = (Official)intent.getSerializableExtra("officialTobeSent");
        addresToShow = (String)intent.getSerializableExtra("location");
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.photoConst);


        if(officialTobeSent.getParty().equals("Republican")) {
            constraintLayout.setBackgroundColor(Color.RED);
        }
        else if(officialTobeSent.getParty().equals("Democratic"))
        {
            constraintLayout.setBackgroundColor(Color.BLUE);
        }
        else
        {
            constraintLayout.setBackgroundColor(Color.BLACK);
        }


        if(officialTobeSent != null) {

            photoLocation = (TextView)findViewById(R.id.photoLocation);
            photoLocation.setBackgroundColor(getResources().getColor(R.color.back_purple));
            photoLocation.setText(addresToShow);


            photoDesignation = (TextView)findViewById(R.id.photoDesignation);
            photoDesignation.setText(officialTobeSent.getOfficialDesignation());

            photoName = (TextView) findViewById(R.id.photoName);
            photoName.setText(officialTobeSent.getOfficialName());

        }


        int result = 0;
        result = networkCheckOnAddButton(this);

        if(result == 1)
        {
            imageViewBig = (ImageView)findViewById(R.id.imageViewBig);
            imageViewBig.setImageResource(R.drawable.placeholder);

        }
        else {

            if (!officialTobeSent.getPhotoUrls().equals("NoPhoto")) {
                imageViewBig = (ImageView) findViewById(R.id.imageViewBig);
                loadImage(officialTobeSent.getPhotoUrls());
            } else {
                imageViewBig = (ImageView) findViewById(R.id.imageViewBig);
                imageViewBig.setImageResource(R.drawable.missingimage);
            }

        }




    }

    @Override
    protected void onResume(){
        super.onResume();
    }


    @Override
    protected  void onPause(){
        super.onPause();
    }



        /* Code added for ------Network Check Starts here----- Bar ends here */

    public int networkCheckOnAddButton(Context context) {

        int result = 0;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Toast.makeText(this, "You ARE Connected to the Internet!", Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            Intent data = getIntent();
            builder.setTitle("No Network Connection");
            builder.setIcon(ic_dialog_alert);
            builder.setMessage("Data cannot be accesses/loaded without an Internet connection");
            AlertDialog dialog = builder.create();
            dialog.show();
            result = 1;

        }
        return result;
    }



    private void loadImage(final String imageURL) {
        //     compile 'com.squareup.picasso:picasso:2.5.2'

        Log.d(TAG, "loadImage: " + imageURL);

        Picasso picasso = new Picasso.Builder(this)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d(TAG, "onImageLoadFailed: ");
                        picasso.load(R.drawable.brokenimage)
                                .into(imageViewBig);
                    }
                })
                .build();

        picasso.load(imageURL)
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(imageViewBig);
    }

}
