package srees.com.flickr;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import srees.com.flickr.Service.ImageService;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mPhotos;
    private Context mContext;
    private ItemListener mListener;

    public RecyclerViewAdapter(Context context, ArrayList<String> photos, ItemListener itemListener) {
        mPhotos = photos;
        mContext = context;
        mListener = itemListener;
    }

    public void setList(ArrayList<String> list) {
        mPhotos = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        String item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            imageView = v.findViewById(R.id.imageView);
        }

        public void setData(String item) {
            this.item = item;
            new ImageService(item, imageView).execute();
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.flickr_image_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        if(mPhotos != null) {
            return mPhotos.size();
        } else {
            return 0;
        }
    }

    public interface ItemListener {
        void onItemClick(String item);
    }
}