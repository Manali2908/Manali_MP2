package com.example.manali_mp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfilePage extends Fragment {

    TextView email,name,birthdate,city,gender;
    //FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;



    public ProfilePage() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
       // firebaseUser = getArguments().getParcelable("User");
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.EI);
        name = view.findViewById(R.id.N);
        birthdate = view.findViewById(R.id.BD);
        city = view.findViewById(R.id.C);
        gender = view.findViewById(R.id.G);

        readFirestore();

    }

    private void readFirestore()
    {
        DocumentReference documentReference = firebaseFirestore.collection("User")
                .document(firebaseAuth.getUid());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists())
                    {
                        Person obj = documentSnapshot.toObject(Person.class);
                        email.setText(obj.getEmail());
                        name.setText(obj.getName());
                        gender.setText(obj.getGender());
                        city.setText(obj.getCity());
                        birthdate.setText(obj.getBirthdate());
                    }
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_LONG).show();
                    }
                });

    }
}