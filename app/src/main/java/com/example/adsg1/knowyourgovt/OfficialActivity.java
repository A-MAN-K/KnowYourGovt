package com.example.adsg1.knowyourgovt;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.R.drawable.ic_dialog_alert;

/**
 * Created by adsg1 on 4/14/2017.
 */

public class OfficialActivity extends AppCompatActivity {


    Official officialTobeSent = new Official();
    private TextView location;
    private TextView designation;
    private TextView nameOfficial;
    private TextView party;
    private TextView address1;
    private TextView address2;
    private TextView address3;
    private TextView phone;
    private TextView eMail;
    private TextView webSite;

    private ImageButton googlePlusImageButton;
    private ImageButton faceBookImageButton;
    private ImageButton youTubeImageButton;
    private ImageButton twitterImageButton;
    private ImageView imageViewSmall;
    private String addressToShow=null;

    private static final int NEW = 1;

    HashMap<String, String> hashMapChannels = new HashMap<String, String>();

    private static final String TAG = "OfficialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.official_activity);





        Intent intent = getIntent();

        officialTobeSent = (Official)intent.getSerializableExtra("officialTobeSent");
        addressToShow = (String)intent.getSerializableExtra("location");
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintId);


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

            location = (TextView) findViewById(R.id.location);
            location.setBackgroundColor(getResources().getColor(R.color.back_purple));
           // location.setText(officialTobeSent.getCity()+","+officialTobeSent.getState()+" "+officialTobeSent.getZipCode());
            location.setText(addressToShow);

            designation = (TextView) findViewById(R.id.designation);
            designation.setText(officialTobeSent.getOfficialDesignation());

            nameOfficial = (TextView) findViewById(R.id.nameOfficial);
            nameOfficial.setText(officialTobeSent.getOfficialName());

            party = (TextView) findViewById(R.id.party);
            party.setText("("+officialTobeSent.getParty()+")");

            address1 = (TextView) findViewById(R.id.address1);
            address1.setText(officialTobeSent.getAddLineOne()+ " "+officialTobeSent.getAddLineTwo() +" "+ officialTobeSent.getCity() +
            ", "+officialTobeSent.getState()+" "+officialTobeSent.getZipCode());
            address1.setLinkTextColor(Color.GREEN);

            Linkify.addLinks(((TextView) findViewById(R.id.address1)), Linkify.MAP_ADDRESSES);

    /*        address2 = (TextView) findViewById(R.id.address2);
            address2.setText(officialTobeSent.getAddLineTwo());
            address2.setLinkTextColor(Color.GREEN);

            Linkify.addLinks(((TextView) findViewById(R.id.address2)), Linkify.MAP_ADDRESSES);

            address3 = (TextView) findViewById(R.id.address3);
            //address3.setText(officialTobeSent.getCity()+", "+officialTobeSent.getState()+" "+officialTobeSent.getZipCode());
            address3.setText("10 West 35th Street Chicago, IL 60616");
            address3.setLinkTextColor(Color.GREEN);

            Linkify.addLinks(((TextView) findViewById(R.id.address3)), Linkify.MAP_ADDRESSES);
*/
            phone = (TextView) findViewById(R.id.phone);
            phone.setText(officialTobeSent.getPhone());
            phone.setLinkTextColor(Color.GREEN);
            Linkify.addLinks(((TextView) findViewById(R.id.phone)), Linkify.PHONE_NUMBERS);


            eMail = (TextView) findViewById(R.id.email);
            if(officialTobeSent.geteMail() == null) {
                eMail.setText("Email not provided");
            }
            else
            {
                eMail.setText(officialTobeSent.geteMail());
                eMail.setLinkTextColor(Color.GREEN);
                Linkify.addLinks(((TextView) findViewById(R.id.email)), Linkify.EMAIL_ADDRESSES);
            }
            webSite = (TextView) findViewById(R.id.website);
            if(officialTobeSent.getWebSite() == null) {
                webSite.setText("Website data not available");
            }
            else{
                webSite.setText(officialTobeSent.getWebSite());
                webSite.setLinkTextColor(Color.GREEN);
                Linkify.addLinks(((TextView) findViewById(R.id.website)), Linkify.WEB_URLS);
            }


            googlePlusImageButton = (ImageButton)findViewById(R.id.googleplus);
            faceBookImageButton =  (ImageButton)findViewById(R.id.facebook);
            youTubeImageButton = (ImageButton)findViewById(R.id.youtube);
            twitterImageButton = (ImageButton)findViewById(R.id.twitter);

            hashMapChannels = officialTobeSent.getChannels();

            if(hashMapChannels.get("GooglePlus").equals("99999"))
            {
                googlePlusImageButton.setVisibility(View.INVISIBLE);
            }

            if(hashMapChannels.get("Facebook").equals("99999"))
            {
                faceBookImageButton.setVisibility(View.INVISIBLE);
            }

            if(hashMapChannels.get("Twitter").equals("99999"))
            {
                twitterImageButton.setVisibility(View.INVISIBLE);
            }

            if(hashMapChannels.get("YouTube").equals("99999"))
            {
                youTubeImageButton.setVisibility(View.INVISIBLE);
            }


            int result = 0;
            result = networkCheckOnAddButton(this);
            if(result == 1)
            {
                imageViewSmall = (ImageView)findViewById(R.id.imageSmall);
                imageViewSmall.setImageResource(R.drawable.placeholder);
            }
            else {
                if (!officialTobeSent.getPhotoUrls().equals("NoPhoto")) {
                    imageViewSmall = (ImageView) findViewById(R.id.imageSmall);
                    loadImage(officialTobeSent.getPhotoUrls());
                } else {
                    imageViewSmall = (ImageView) findViewById(R.id.imageSmall);
                    imageViewSmall.setImageResource(R.drawable.missingimage);
                }
            }
            imageViewSmall.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    openPhotoActivity(view);

                }

            });


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

    public void openPhotoActivity(View view)
    {
        Intent intent = new Intent(OfficialActivity.this, PhotoActivity.class);
        intent.putExtra("officialTobeSent", officialTobeSent);
        intent.putExtra("location",addressToShow);
        startActivityForResult(intent, NEW);

        Toast.makeText(OfficialActivity.this, "Image View has been clicked", Toast.LENGTH_LONG).show();

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



/* Code added for ------Network Check ends here----- Bar ends here */


    public void facebookClicked(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" + officialTobeSent.getChannels().get("Facebook");
        String urlToUse;

        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;

            }
            else
            {
                urlToUse = "fb://page/" + officialTobeSent.getChannels().get("Facebook");
            }
        }
        catch (PackageManager.NameNotFoundException e) {

            urlToUse = FACEBOOK_URL;
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);

    }

    public void twitterClicked(View v)
    {
        Intent intent = null;
        String name = officialTobeSent.getChannels().get("Twitter");
        try
        {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }

    public void googlePlusClicked(View v)
    {
        String name = officialTobeSent.getChannels().get("GooglePlus");
        Intent intent = null; try { intent = new Intent(Intent.ACTION_VIEW);
        intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
        intent.putExtra("customAppUri", name);
        startActivity(intent);
    }
    catch (ActivityNotFoundException e)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + name)));
        }
    }

    public void youTubeClicked(View v)
    {
        String name = officialTobeSent.getChannels().get("YouTube") ;
        Intent intent = null;
        try
        {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        }

        catch (ActivityNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name)));
        }
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
                                .into(imageViewSmall);
                    }
                })
                .build();

        picasso.load(imageURL)
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(imageViewSmall);
    }






}
