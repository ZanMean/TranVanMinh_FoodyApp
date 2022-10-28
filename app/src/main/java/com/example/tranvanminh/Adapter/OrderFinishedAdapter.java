package com.example.tranvanminh.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.tranvanminh.OrderFinished;
import com.example.tranvanminh.R;
import com.example.tranvanminh.Restaurant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderFinishedAdapter extends Adapter<OrderFinishedAdapter.ViewHolderOrderFinished> {
    FirebaseDatabase fDatabase;
    FirebaseStorage fStorage;
    ArrayList<OrderFinished> orders;

    public class ViewHolderOrderFinished extends ViewHolder {
        ImageView ivImage;
        TextView tvAddress, tvName, tvOrderDate, tvOrderID, tvOrderSum, tvStatus;

        public ViewHolderOrderFinished(View itemView) {
            super(itemView);
            tvOrderSum = itemView.findViewById(R.id.tvOrderSum);
            tvOrderID = itemView.findViewById(R.id.tvOrderID);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }

    public OrderFinishedAdapter(ArrayList<OrderFinished> orders) {
        this.orders = orders;
    }

    public ViewHolderOrderFinished onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderOrderFinished(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false));
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolderOrderFinished holder, int position) {
//
//    }

    public void onBindViewHolder(final ViewHolderOrderFinished holder, int position) {
        fDatabase = FirebaseDatabase.getInstance();
        fStorage = FirebaseStorage.getInstance();
        OrderFinished orderFinished = orders.get(position);
        fDatabase.getReference().child("restaurants").child((orderFinished.getFoodBaskets().get(0)).getResKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                holder.tvName.setText(restaurant.getName());
                holder.tvAddress.setText(restaurant.getAddress());
                OrderFinishedAdapter.this.fStorage.getReference().child("restaurants/" + restaurant.getLogo()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(holder.ivImage);
                    }
                });
            }

            public void onCancelled(DatabaseError error) {
            }
        });
        holder.tvOrderID.setText(orderFinished.getOrderID());
        holder.tvOrderDate.setText(orderFinished.getOrderDate());
        holder.tvOrderSum.setText(orderFinished.getOrderSum());
        if (orderFinished.getOrderStatus() == 1) {
            holder.tvStatus.setText("Dang Van chuyen");
        }
    }


    public int getItemCount() {
        return orders.size();
    }
}
