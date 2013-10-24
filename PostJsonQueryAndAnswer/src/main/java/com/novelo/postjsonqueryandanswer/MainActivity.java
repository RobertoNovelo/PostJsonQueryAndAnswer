package com.novelo.postjsonqueryandanswer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class MainActivity extends Activity {

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onResume ()
    {
        super.onResume();

        sendJson();

    }

    protected void sendJson() {

        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();
                try {
                    json.put("Data", "data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    HttpPost post = new HttpPost("http://www2.smartplace.mx/index.php/inicio/readVersion");
                    StringEntity se = new StringEntity( json.toString());
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity

                        TextView tv = (TextView) findViewById(R.id.jsonresponse);

                        tv.setText(in.toString());

                    }
                    else
                    {


                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();
    }

}
