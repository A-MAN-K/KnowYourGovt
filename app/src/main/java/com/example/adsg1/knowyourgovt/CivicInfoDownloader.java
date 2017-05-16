package com.example.adsg1.knowyourgovt;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adsg1 on 4/14/2017.
 */

public class CivicInfoDownloader  extends AsyncTask<Official, Void, String> {

    MainActivity mainActivity;

    Official official;
    private int count;


    private String dataURLLess = "https://www.googleapis.com/civicinfo/v2/representatives?key=";
    private String intermediateString = "&address=";
    private String apiKey = "AIzaSyCEakdj2kvmMQYEdLX20QT4sJQrlAyvH4o";
    private String dataUrlMore = dataURLLess+apiKey+intermediateString;

    private static final String TAG="CivicInfoDownloader";

    String addressLine1 = null, addressLine2 = null;
    String city11 = null, state11 = null, zip11 = null, type1 = null;
    String  id1 = null, photo = null, channeltype = null, channelid = null;
    String urlsForObject = null;
    String address1 = null, address2 = null, address3 = null, address4 = null, address5 = null;
    String posname= null;
    String officerName = null, address = null, phone = null, email = null, officerParty = null;
    String locationName;
    String channelForObject;
    JSONArray jb3 = null;
    JSONObject jb4 = null;
    ArrayList<Official> offialDataList =  new ArrayList<Official>();


    HashMap<String, ArrayList<Official>> objectList = new HashMap<String, ArrayList<Official>>();

    public CivicInfoDownloader(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected String doInBackground(Official... params) {

        String completeURL = dataUrlMore+params[0].getZipCode();

        Uri dataUri = Uri.parse(completeURL);
        String urlToUse = dataUri.toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {


            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, "doInBackground: " + sb.toString());

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            //return null;

            sb.append("[{\"company_name\": \"NULL\", \"company_symbol\": \"123\", \"listing_exchange\": \"NULL\"}]");
            return sb.toString();
        }

        Log.d(TAG, "doInBackground: " + sb.toString());


        return sb.toString();


    }

    @Override
    protected void onPostExecute(String s) {

         parseJSON(s);
         mainActivity.setOfficialList(objectList);

    }


    private HashMap<String, ArrayList<Official>> parseJSON(String s) {

        ArrayList<Official> officialList = new ArrayList<>();
        ArrayList<Official> dataArrayList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(s);
            JSONObject response = jsonObject.getJSONObject("normalizedInput");
            count = response.length();
            String city = jsonObject.getJSONObject("normalizedInput").getString("city");
            String state = jsonObject.getJSONObject("normalizedInput").getString("state");
            String zipCode = jsonObject.getJSONObject("normalizedInput").getString("zip");

            if(city!=null || state!=null || zipCode!=null)
            {
                locationName = city +", "+ state +" "+ zipCode;
            }
            JSONObject jsonObject1 = new JSONObject(s);
            JSONObject jsonObject2 = new JSONObject(s);

            JSONArray jsonArrayOffices = jsonObject1.getJSONArray("offices");
            Log.d(TAG, "21: " +jsonArrayOffices.length());

//Location
            for(int i =0; i < jsonArrayOffices.length(); i++)
            {
                JSONObject jb1 = jsonArrayOffices.getJSONObject(i);
                String positionType = jb1.getString("name");
           //     dataArrayList.add(new Official(positionType));
                //Log.i("positionType:",positionType);
            }

            for(int i1 =0; i1 < jsonArrayOffices.length(); i1++)
            {


                Log.d("length", String.valueOf(jsonArrayOffices.length()));
                posname = jsonArrayOffices.getJSONObject(i1).getString("name");
                JSONArray offices = (jsonArrayOffices.getJSONObject(i1).getJSONArray("officialIndices"));
                Log.d("officiesIndicies1" + ": ", offices.toString());

                for (int j = 0; j < jsonArrayOffices.getJSONObject(i1).getJSONArray("officialIndices").length(); j++)
                {
                    Official officialDataFetcher = new Official();
                    int id11 = offices.getInt(j);

                    officialDataFetcher.setOfficialDesignation(posname);

                    officerName = jsonObject1.getJSONArray("officials").getJSONObject(id11).getString("name");
                    officialDataFetcher.setOfficialName(officerName);
                    try
                    {
                        if(jsonObject1.getJSONArray("officials").getJSONObject(id11).has("party"))
                        {
                            officerParty = jsonObject1.getJSONArray("officials").getJSONObject(id11).getString("party");
                        }
                        else {
                            officerParty = "Unknown";
                        }
                        officialDataFetcher.setParty(officerParty);
                    }

                    catch (JSONException e)
                    {
                        Log.e(TAG, "doInBackground3: ", e);
                    }
                    if(jsonObject1.getJSONArray("officials").getJSONObject(id11).has("address"))
                    {
                        try
                        {
                            {
                                JSONObject line1 = jsonObject2.getJSONArray("officials").getJSONObject(id11).getJSONArray("address").getJSONObject(0);
                                if (line1.has("line1")) {
                                    addressLine1 = line1.getString("line1");
                                }
                                if (line1.has("line2")) {
                                    addressLine2 = line1.getString("line2");
                                }
                                if (line1.has("city")) {
                                    city11 = line1.getString("city");
                                }
                                if (line1.has("state")) {
                                    state11 = line1.getString("state");
                                }
                                if (line1.has("line2")) {
                                    zip11 = line1.getString("zip");
                                }
                                officialDataFetcher.setAddLineOne(addressLine1);
                                officialDataFetcher.setAddLineTwo(addressLine2);
                                officialDataFetcher.setCity(city11);
                                officialDataFetcher.setState(state11);
                                officialDataFetcher.setZipCode(zip11);



                                Log.i("Address", "[" + j + "]" + ":" + addressLine1 + " " + addressLine2 + " " + city11 + " " + state11 + " " + zip11);

                            }
                        }
                        catch (JSONException e)
                        {
                            Log.e(TAG, "JSONEXCEPTION: ", e);
                        }
                    }
                    if(jsonObject1.getJSONArray("officials").getJSONObject(id11).has("phones"))
                    {

                            phone = (jsonObject1.getJSONArray("officials").getJSONObject(id11).getJSONArray("phones").get(0).toString());
                         //   Log.i("Phone", "[" + j + "]" + ":" + phones.toString().replaceAll("[^\\d.]", ""));
                           // phone = phones.toString().replaceAll("[^\\d.]", "");
                           // phone = phones.toString();
                            officialDataFetcher.setPhone(phone);

                    }
                    /* Code added for urls*/
                    if(jsonObject1.getJSONArray("officials").getJSONObject(id11).has("urls"))
                    {

                        urlsForObject =  jsonObject1.getJSONArray("officials").getJSONObject(id11).getJSONArray("urls").get(0).toString();

                        officialDataFetcher.setUrls(urlsForObject);
                        officialDataFetcher.setWebSite(urlsForObject);


                    }

                    if(jsonObject1.getJSONArray("officials").getJSONObject(id11).has("photoUrl"))
                    {

                        photo = jsonObject1.getJSONArray("officials").getJSONObject(id11).getString("photoUrl");
                        officialDataFetcher.setPhotoUrls(photo);

                    }
                    else
                    {
                        photo = "NoPhoto";
                        officialDataFetcher.setPhotoUrls(photo);
                    }

                    if(jsonObject1.getJSONArray("officials").getJSONObject(id11).has("emails"))
                    {

                        email = jsonObject1.getJSONArray("officials").getJSONObject(id11).getJSONArray("emails").get(0).toString();
                        officialDataFetcher.seteMail(email);

                    }

                    if(jsonObject1.getJSONArray("officials").getJSONObject(id11).has("channels"))
                    {
                        HashMap<String, String> hashMap = new HashMap<String, String>();

                        hashMap.put("GooglePlus","99999");
                        hashMap.put("Facebook","99999");
                        hashMap.put("Twitter","99999");
                        hashMap.put("YouTube","99999");

                        JSONArray jsonChannels = jsonObject1.getJSONArray("officials").getJSONObject(id11).getJSONArray("channels");

                        for ( int pntr = 0; pntr < jsonChannels.length(); pntr++)
                        {

                                if (jsonChannels.getJSONObject(pntr).getString("type").equals("GooglePlus")) {
                                    hashMap.put("GooglePlus", jsonChannels.getJSONObject(pntr).getString("id"));
                                }

                                if (jsonChannels.getJSONObject(pntr).getString("type").equals("Facebook")) {
                                    hashMap.put("Facebook", jsonChannels.getJSONObject(pntr).getString("id"));
                                }
                                if (jsonChannels.getJSONObject(pntr).getString("type").equals("Twitter")) {
                                    hashMap.put("Twitter", jsonChannels.getJSONObject(pntr).getString("id"));
                            }

                                if (jsonChannels.getJSONObject(pntr).getString("type").equals("YouTube")) {
                                    hashMap.put("YouTube", jsonChannels.getJSONObject(pntr).getString("id"));
                            }


                        }

                        officialDataFetcher.setChannels(hashMap);


                    }





                    dataArrayList.add(officialDataFetcher);

                    objectList.put(locationName, dataArrayList);
                }
            }

            return objectList;

        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



}
