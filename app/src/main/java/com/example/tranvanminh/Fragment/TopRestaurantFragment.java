package com.example.tranvanminh.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tranvanminh.Adapter.RestaurantAdapter;
import com.example.tranvanminh.R;
import com.example.tranvanminh.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRestaurantFragment extends Fragment implements RestaurantAdapter.OnRestaurantItemClickListener {
    RecyclerView rvTopRestaurant;
    RestaurantAdapter restaurantAdapter;
    ArrayList<Restaurant> topRestaurant;
    FirebaseDatabase fDatabase;
    DatabaseReference dRestaurant;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopRestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopRestaurantFragment newInstance(String param1, String param2) {
        TopRestaurantFragment fragment = new TopRestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        topRestaurant = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTopRestaurant = view.findViewById(R.id.rv_top_restaurant);
        restaurantAdapter = new RestaurantAdapter(topRestaurant,null,2);
        rvTopRestaurant.setAdapter(restaurantAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        rvTopRestaurant.setLayoutManager(layoutManager);
//        rvRestaurants.addItemDecoration(new DividerItemDecoration(getContext(),GridLayoutManager.VERTICAL));
        fDatabase = FirebaseDatabase.getInstance();
        dRestaurant = fDatabase.getReference();
        Query qRestaurant = dRestaurant.child("restaurants").orderByChild("rate").limitToLast(3);
        qRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topRestaurant.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                    topRestaurant.add(restaurant);
                }
                restaurantAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void onRestaurantItemClick(Restaurant restaurant) {

    }
}