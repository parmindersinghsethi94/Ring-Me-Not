package app.com.ringmenot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import app.com.ringmenot.util.Missed;
import app.com.ringmenot.util.UsersAdapter;

public class MissedFragment extends Fragment {
    TextView name, missedTitle, credits;
    ProgressDialog pDialog;
    View rootView;
    ListView listView;
    String url = "http://madhousefurniture.co.uk/?page=parminder3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_missed, container, false);

        credits = (TextView) rootView.findViewById(R.id.credits);
        name = (TextView) rootView.findViewById(R.id.name);
        missedTitle = (TextView) rootView.findViewById(R.id.missedTitle);
        listView = (ListView) rootView.findViewById(R.id.listView);

        Typeface helvetica = Typeface.createFromAsset(getActivity().getAssets(), "CaviarDreams.ttf");

        credits.setTypeface(helvetica);
        name.setTypeface(helvetica);
        missedTitle.setTypeface(helvetica);

        new GetRecords().execute();
        return rootView;
    }
    private class GetRecords extends AsyncTask<Void, Void, Void> {

        private Boolean isChanged = false;
        private String queryUrl = url;
        HttpResponse response;
        JSONArray jsonArray;
        ArrayList<Missed> data = new ArrayList<Missed>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Fetching Call Logs...");
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
                            isChanged=true;
                            jsonArray = new JSONArray(entity);
                        } catch (Exception j) {
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
                try {
                     for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonChildNode = jsonArray.getJSONObject(i);

                        String operator = jsonChildNode.optString("operator");
                        String number = jsonChildNode.optString("number");
                        String circle = jsonChildNode.optString("circle");

                        data.add(new Missed(number, circle, operator));
                    }
            }
            catch(Exception e){
                e.printStackTrace();
            }
                UsersAdapter adapter = new UsersAdapter(getActivity().getApplicationContext(), data);

                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
