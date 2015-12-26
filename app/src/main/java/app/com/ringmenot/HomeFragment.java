package app.com.ringmenot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HomeFragment extends Fragment {
    private Switch statusSwitch;
    TextView number, name,credits;
    ProgressDialog pDialog;
    int status;
    Boolean changed;
    String url = "http://madhousefurniture.co.uk/?page=parminder2";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Loading Helvetica Font



        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Typeface helvetica = Typeface.createFromAsset(getActivity().getAssets(), "CaviarDreams.ttf");


        number = (TextView) rootView.findViewById(R.id.number);
        name = (TextView) rootView.findViewById(R.id.name);
        credits = (TextView) rootView.findViewById(R.id.credits);
        number.setTypeface(helvetica);
        name.setTypeface(helvetica);
        credits.setTypeface(helvetica);
        statusSwitch = (Switch) rootView.findViewById(R.id.statusSwitch1);
        statusSwitch.setTypeface(helvetica);

        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("Checked", isChecked + "");
                if(isChecked){
                    status =0;
                    new ChangeStatus().execute();
                }
                else{
                    status =1;
                    new ChangeStatus().execute();
                }
            }
        });
        return rootView;
    }
    private class ChangeStatus extends AsyncTask<Void, Void, Void> {
        private Boolean isChanged=false;
        private String queryUrl = url + "&status=" + status;
        HttpResponse response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Changing Status...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpClient httpclient = new DefaultHttpClient(); // create new httpClient
            HttpGet httpGet = new HttpGet(queryUrl); // create new httpGet object
            try {
                response = httpclient.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    // System.out.println(statusLine);
                    HttpEntity e = response.getEntity();
                    String entity = EntityUtils.toString(e);
                    Log.e("QUERY", queryUrl);
                    Log.e("RESPONSE", entity);
                    if (entity != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(entity);

                            if (jsonObj.getString("success").equals("true")) {
                                isChanged = true;
                            } else {
                                isChanged = false;
                            }
                        } catch (JSONException j) {
                            j.printStackTrace();
                        }
                    } else {
                        Log.e("StatusChange", "No Response");
                    }
                } else {
                    Log.e("StatusChange", "Error in Request");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            super.onPostExecute(result);
            if (isChanged) {
                if(status==0) {
                   statusSwitch.setBackgroundColor(Color.parseColor("#2ECC71"));
                    //statusSwitch.setChecked(Boolean.TRUE);
                    Toast.makeText(getActivity().getApplicationContext(), "You will now receive Work Calls", Toast.LENGTH_SHORT).show();
                }
                else{
                    statusSwitch.setBackgroundColor(Color.parseColor("#ea6153"));
                            // statusSwitch.setChecked(Boolean.FALSE);
                            Toast.makeText(getActivity().getApplicationContext(), "You will not receive any Work Calls", Toast.LENGTH_SHORT).show();

                }
           } else {
                if(status==0) {
                  //  statusSwitch.setBackgroundColor(Color.RED);
                  //  statusSwitch.setChecked(Boolean.FALSE);
                    Toast.makeText(getActivity().getApplicationContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //statusSwitch.setBackgroundColor(Color.GREEN);
                    //statusSwitch.setChecked(Boolean.TRUE);
                    Toast.makeText(getActivity().getApplicationContext(), "Connection Error!", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
