package android.glitterlabs.com.instagramclientvideo;

import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity
{

    public static final String CLIENT_ID="19e2582bf10f42728ce0d939b30a7a4c";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
         //send out popular API request to popular photos.

       photos= new ArrayList<>();
        //1.create the adapter it to linking to sources
        aPhotos=new InstagramPhotosAdapter(this,photos);
        // 2.find the listview from the layout
        ListView listView=(ListView)findViewById(R.id.lvPhotos);
        //3.set the adapter binding it to listview
        listView.setAdapter(aPhotos);
         //fetch the popular photos
        fetchPopularPhotos();
    }

     public void fetchPopularPhotos()
     {
         String url="https://api.instagram.com/v1/media/popular?client_id=" +CLIENT_ID;
         //create network client
         AsyncHttpClient client=new AsyncHttpClient();
         //Triggre the GET Request
        client.get(url, null, new JsonHttpResponseHandler() {

             //onSuccess(worked, 200)
             @Override
             public void onSuccess(int statusCode, Header[] header, JSONObject response) {
                 Log.i("DEBUGG", response.toString());

                 JSONArray photosJSON = null;
                 try
                 {
                   photosJSON=response.getJSONArray("data"); //gives array of photos item.
                     //iterate array of posts.
                     for(int i=0;i<photosJSON.length();i++)
                     {
                         //get the josn object at that position
                         JSONObject photoJSON=photosJSON.getJSONObject(i);
                         //decode the attribute of jason into data model;
                         InstagramPhoto photo=new InstagramPhoto();
                         photo.username=photoJSON.getJSONObject("user").getString("username");
                         photo.caption=photoJSON.getJSONObject("caption").getString("text");
                         photo.imageUrl=photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                         photo.imageHeight=photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                         photo.likesCounts=photoJSON.getJSONObject("likes").getInt("count");
                         photos.add(photo);
                     }

                 }
                 catch (JSONException e)
                 {
                    e.printStackTrace();
                 }
                 aPhotos.notifyDataSetChanged();
             }
             //onFailure(fail)

             public void onFailure(int statusCode, Header[] header, Throwable throwable) {
                 //Do Something.
             }


         });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
