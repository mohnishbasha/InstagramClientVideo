package android.glitterlabs.com.instagramclientvideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto>
{

    //what data do we need from activity.
    //Context Datasource.
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects)
    {
        super(context,android.R.layout.simple_list_item_1,objects);
    }

     @Override

     public  View getView(int position,View convertView,ViewGroup parent )
     {
           InstagramPhoto photo=getItem(position);

         if(convertView==null)
         {
             //create the new view for templete.
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
         }
       //set the ID for each vaiew item.
         TextView tvCaption=(TextView)convertView.findViewById(R.id.tvCaption);
         ImageView ivPhoto=(ImageView)convertView.findViewById(R.id.ivPhoto);

         //insert the model data into each of the view.
         tvCaption.setText(photo.caption);
         //clear the image view
         ivPhoto.setImageResource(0);
         // insert the image using Picasso.
         Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.ic_launcher).into(ivPhoto);

         //Return  created item as a view
         return  convertView;
     }

}
