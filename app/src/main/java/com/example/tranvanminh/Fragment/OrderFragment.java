package com.example.tranvanminh.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tranvanminh.Adapter.OrderFinishedAdapter;
import com.example.tranvanminh.OrderFinished;
import com.example.tranvanminh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    RecyclerView rvOrder;
    OrderFinishedAdapter orderAdapter;
    ArrayList<OrderFinished> orderFinisheds;
    FirebaseAuth fAuth;
    FirebaseDatabase fDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        orderFinisheds = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        orderFinisheds = new ArrayList();
        rvOrder =  view.findViewById(R.id.rvOrder);
        orderAdapter = new OrderFinishedAdapter(orderFinisheds);
        rvOrder.setAdapter(orderAdapter);
        rvOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvOrder.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        FirebaseAuth instance = FirebaseAuth.getInstance();
        fAuth = instance;
        String userUID = instance.getCurrentUser().getUid();
        FirebaseDatabase instance2 = FirebaseDatabase.getInstance();
        fDatabase = instance2;
        instance2.getReference().child("orders").child(userUID).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    orderFinisheds.add((OrderFinished) dataSnapshot.getValue(OrderFinished.class));
                }
                orderAdapter.notifyDataSetChanged();
            }

            public void onCancelled(DatabaseError error) {
//                Toast.makeText(OrderFragment.this.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}