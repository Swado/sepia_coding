package com.example.sepia_coding;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Object> listRecyclerItem;

    public RecyclerAdapter(Context context, List<Object> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView petName;
        private ImageView petImage;
        private CardView petCard;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = (TextView) itemView.findViewById(R.id.card_text);
            petImage = (ImageView) itemView.findViewById(R.id.card_image);
            petCard = (CardView) itemView.findViewById(R.id.card);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new ItemViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        petObject petObject = (petObject) listRecyclerItem.get(position);
        String str = petObject.getImage_url();

        // Used Picasso Library to traverse image from web.
        Picasso.get().load(str).fit()
                .centerInside().into(itemViewHolder.petImage, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(Constants.IMAGE_LOADED_TAG, Constants.IMAGE_LOADED_ON_SUCCESS);
            }

            @Override
            public void onError(Exception e) {
                Log.e(Constants.IMAGE_FAILED_TO_LOAD_TAG,Constants.IMAGE_LOADED_ON_ERROR+ e);
            }
        });

        // Set Pet name.
        itemViewHolder.petName.setText(petObject.getTitle().toString());

        // On click for the card.
        itemViewHolder.petCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(((ItemViewHolder) holder).petCard.getContext(), PetInfo.class);
                intent.putExtra(Constants.CONTENT_URL_FIELD, petObject.getContent_url());
                ((ItemViewHolder) holder).petCard.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
