package com.example.tranvanminh.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.tranvanminh.R;
import com.example.tranvanminh.UserInfor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    TextView tv_name_profile, tvmailProfile, tvSdtProfile, tvAddressProfile;
    AppCompatButton btnUpdateProfile, btnChangePassWord;
    FirebaseDatabase fDatabase;
    FirebaseAuth fAuth;
    String userID;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_name_profile = view.findViewById(R.id.tv_name_profile);
        tvmailProfile = view.findViewById(R.id.tvmailProfile);
        tvAddressProfile = view.findViewById(R.id.tvAddressProfile);
        tvSdtProfile = view.findViewById(R.id.tvSdtProfile);
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);
        btnChangePassWord = view.findViewById(R.id.btnChangePassWord);

        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();


        String userID = fAuth.getCurrentUser().getUid();
//        fDatabase.getReference().child("users").child(userID).get()
////                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
////                    @Override
////                    public void onSuccess(DataSnapshot dataSnapshot) {
////                        UserInfor userInfor = dataSnapshot.getValue(UserInfor.class);
////                        userInfor.setUserID(userID);
////                        tv_name_profile.setText(userInfor.getFirstname() + " " + userInfor.getLastname());
////                        tvmailProfile.setText(userInfor.getEmail());
////                        tvAddressProfile.setText(userInfor.getAddress());
////                        tvSdtProfile.setText(userInfor.getMobile());
////
////                    }
////                })
////                .addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Toast.makeText(getContext(), e.getMessage(),
////                                Toast.LENGTH_SHORT).show();
////                    }
////                });

        this.fDatabase.getReference().child("users").child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                UserInfor user = snapshot.getValue(UserInfor.class);
                tv_name_profile.setText(user.getFirstname() + " " + user.getLastname());
                tvAddressProfile.setText(user.getAddress());
                tvSdtProfile.setText(user.getMobile());
                tvmailProfile.setText(user.getEmail());
            }

            public void onCancelled(DatabaseError error) {
            }
        });


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext()).setTitle("Áp dụng thay đổi ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String[] words = tv_name_profile.getText().toString().split("\\s");
                                Update(words[0], words[1], tvSdtProfile.getText().toString());
                            }
                        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "Đã hủy",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();

            }
        });
    }

    private void Update(String firstname, String lastname, String mobile) {
        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = fDatabase.getReference();

        Map<String, Object> user = new HashMap<>();
        user.put("firstname", firstname);
        user.put("lastname", lastname);
        user.put("mobile", mobile);

        reference.child("users").child(fAuth.getUid()).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Cập nhật thành công",
                                Toast.LENGTH_SHORT).show();
                        NavigationView nav = getActivity().findViewById(R.id.navView);
                        View view = nav.getHeaderView(0);
                        TextView name = view.findViewById(R.id.tvFullNameProfile);
                        name.setText(firstname + " " + lastname);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
