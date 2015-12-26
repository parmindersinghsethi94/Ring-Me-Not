package app.com.ringmenot;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.io.IOException;

public class MessageFragment extends Fragment {
    ProgressDialog pDialog;
    Button button;
    EditText message;
    String msg="";
    TextView busyTitle, busyDesc,name,credits;
    String url = "http://madhousefurniture.co.uk/?page=parminder2&";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_message, container, false);

        busyDesc = (TextView) rootView.findViewById(R.id.busyDesc);
        busyTitle = (TextView) rootView.findViewById(R.id.busyTitle);
        credits = (TextView) rootView.findViewById(R.id.credits);
        name = (TextView) rootView.findViewById(R.id.name);
        message = (EditText) rootView.findViewById(R.id.message);
        button = (Button) rootView.findViewById(R.id.button);

        Typeface helvetica = Typeface.createFromAsset(getActivity().getAssets(), "CaviarDreams.ttf");

        busyTitle.setTypeface(helvetica);
        message.setTypeface(helvetica);
        button.setTypeface(helvetica);
        busyDesc.setTypeface(helvetica);
        name.setTypeface(helvetica);
        credits.setTypeface(helvetica);

        //WORKING
        button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                msg = message.getText().toString().replaceAll(" ","%20");
                if(msg.equals("%20")||msg.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter a Message First", Toast.LENGTH_SHORT).show();
                }
                else {
                    new ChangeValue().execute();
                }
            }
        });


        return rootView;
    }
    private class ChangeValue extends AsyncTask<Void, Void, Void> {

        private Boolean isChanged = false;
        private String queryUrl = url + "value=" + msg;
        HttpResponse response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Changing Message...");
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
                Toast.makeText(getActivity().getApplicationContext(), "New Busy Message Updated.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Connection Error.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
