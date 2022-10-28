package com.example.tranvanminh.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tranvanminh.R;

public class OrderActivity extends AppCompatActivity {
    private TextView tvTotal, tvName, tvAddress,tvMobile;
    private RecyclerView rvFoods;
//    private Basket basket;
//    private FoodBasketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        tvAddress = findViewById(R.id.tvAddress);
        tvName = findViewById(R.id.tvName);
        tvMobile= findViewById(R.id.tvMobile);

    }
}