package com.example.adsg1.knowyourgovt;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import static android.R.drawable.ic_dialog_alert;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {


    private ArrayList<Official> officialList = new ArrayList<Official>();
    private RecyclerView recyclerView;
    private OfficialAdapter officialAdapter;
    private Official official;
    String zipCodeTobeSent ="";

    private static final String TAG = "MainActivity";
    private static final int NEW = 1;
    private static final int EDIT = 2;
    private Locator locator;
    private String location=null;
    Official officialTobeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        officialAdapter = new OfficialAdapter(officialList, this);
        recyclerView.setAdapter(officialAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);


 /*       for ( int i = 0; i<20; i++ )
        {
            officialList.add(new Official());
        }
*/
        int result = 0;
        result = networkCheckOnAddButton(this);

        if(result != 1) {
            locator = new Locator(this);
        }
    }




 /*   @Override
    protected void onResume() {
        super.onResume();

        int result = 0;
        result = networkCheckOnAddButton(this);

        if(result != 1) {
            locator = new Locator(this);
        }

    }


    @Override
    protected void onPause(){
        super.onPause();

    }
*/


    /* Code added for ------MENU----- Bar starts here */

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_official, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuHelp:
            {

                Intent intent = new Intent(MainActivity.this, About.class);
                startActivityForResult(intent, NEW);
                return true;
            }

            // Here Also

            case R.id.menuSearch:

            {

                loadCivicDownloader(this);

            }



            default:
                return super.onOptionsItemSelected(item);
        }
    }

/* Code added for ------MENU----- Bar ends here */


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

/* Code added for ------ON CLICK EVENT----- starts here */



    @Override
    public void onClick(View v) {


        Log.d(TAG, "Running Good !!!");
        int pos = recyclerView.getChildLayoutPosition(v);
        officialTobeSent = officialList.get(pos);
        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
        intent.putExtra("officialTobeSent", officialTobeSent);
        intent.putExtra("officialPosition",pos);
        intent.putExtra("location",location);
        startActivityForResult(intent, NEW);


        Toast.makeText(this, "View has been clicked", Toast.LENGTH_LONG).show();


    }

/* Code added for ------ ON CLICK ----- ends here */





/* Code added for ------ ON LONG CLICK ----- starts here */



    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG, "Working!!!!!");
/*
        final int pos = recyclerView.getChildLayoutPosition(v);
        stock = stockList.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Intent data = getIntent();
        builder.setTitle("Delete Stock!");
        builder.setIcon(ic_menu_delete);
        builder.setMessage("Delete Stock"+"  "+ stock.getSymbol()+" ?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                stockList.remove(stock);
                stocksAdapter = new StocksAdapter(stockList, MainActivity.this );
                recyclerView.setAdapter(stocksAdapter);


                DatabaseHandler.getInstance(mainActivity).deleteStock(stockList.get(pos).getSymbol());
                stockList.remove(pos);
                Collections.sort(stockList, new SorterOnTheSymbol());
                stocksAdapter.notifyDataSetChanged();


            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
*/
        Toast.makeText(this, " Long View has been clicked", Toast.LENGTH_LONG).show();
        return false;


    }





/* Code added for ------ ON LONG CLICK ----- ends here */







/* Code added for ------ ON LOCATOR ----- starts here */



    public void setData(double lat, double lon) {


        Log.d(TAG, "setData: Lat: " + lat + ", Lon: " + lon);
        String address = doAddress(lat, lon);

        StringTokenizer stringTokenizer = new StringTokenizer(address," ");

        String zipCodeToBeSentAuto = null;
        while(stringTokenizer.hasMoreTokens())
        {
            zipCodeToBeSentAuto = stringTokenizer.nextToken();
        }
        ((TextView) findViewById(R.id.postalAddress)).setText(address);
        official = new Official();
        official.setZipCode(zipCodeToBeSentAuto);
        new CivicInfoDownloader(MainActivity.this).execute(official);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(TAG, "onRequestPermissionsResult: CALL: " + permissions.length);
        Log.d(TAG, "onRequestPermissionsResult: PERM RESULT RECEIVED");

        if (requestCode == 5) {
            Log.d(TAG, "onRequestPermissionsResult: permissions.length: " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: HAS PERM");
                        locator.setUpLocationManager();
                        locator.determineLocation();
                    } else {
                        Toast.makeText(this, "Location permission was denied - cannot determine address", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onRequestPermissionsResult: NO PERM");
                    }
                }
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }


    private String doAddress(double latitude, double longitude) {

        Log.d(TAG, "doAddress: Lat: " + latitude + ", Lon: " + longitude);



        List<Address> addresses = null;
        for (int times = 0; times < 3; times++) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                Log.d(TAG, "doAddress: Getting address now");


                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder sb = new StringBuilder();

                for (Address ad : addresses) {
                    Log.d(TAG, "doLocation: " + ad);

                 //   sb.append("\nAddress\n\n");
                    for (int i = 0; i < 1; i++)
                    //    sb.append("\t" + ad.getAddressLine(i) + "\n");

                 //   sb.append(ad.getLocality() + ", "+ad.getAdminArea() +" "+ ad.getPostalCode() );
                     sb.append(ad.getAddressLine(1));

                }

                return sb.toString();
            } catch (IOException e) {
                Log.d(TAG, "doAddress: " + e.getMessage());

            }
            Toast.makeText(this, "GeoCoder service is slow - please wait", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "GeoCoder service timed out - please try again", Toast.LENGTH_LONG).show();
        return null;
    }

    public void noLocationAvailable() {
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        locator.shutdown();
        super.onDestroy();
    }
    /* Code added for ------ ON LOCATOR ----- ends here */








    /* Code added for ------ CIVIC DOWNLOADER ----- ends here */

    public void loadCivicDownloader(final Context context)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Intent data = getIntent();
        builder.setTitle("Search Location");
        builder.setMessage("Enter a City, State or a Zip Code:");
        final EditText zipCode = new EditText(this);
        zipCode.setInputType(InputType.TYPE_CLASS_TEXT);
        zipCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        zipCode.setGravity(Gravity.CENTER);
        builder.setView(zipCode);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(MainActivity.this, " Alert Builder has been clicked", Toast.LENGTH_LONG).show();

                official = new Official();
                zipCodeTobeSent = zipCode.getText().toString();
                official.setZipCode(zipCodeTobeSent);
                new CivicInfoDownloader(MainActivity.this).execute(official);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();






    }



    /* Code added for ------ CIVIC DOWNLOADER ----- ends here */



    /* Code added for ------ Setting the Official List in the main Activty ----- starts here */



    public void setOfficialList( HashMap<String, ArrayList<Official>> hmap)
    {

        HashMap<String, ArrayList<Official>> objectList = new HashMap<String, ArrayList<Official>>();

        objectList = hmap;



        officialList.clear();
        for(Map.Entry<String,ArrayList<Official>> map : objectList.entrySet())
        {
            location = map.getKey();
            ((TextView) findViewById(R.id.postalAddress)).setText(map.getKey());
            officialList.addAll(map.getValue());

        }

        officialAdapter.notifyDataSetChanged();



        }




    /* Code added for ------ Setting the Official List in the main Activty ----- ends here */







}
