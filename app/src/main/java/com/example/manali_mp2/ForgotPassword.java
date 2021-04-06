package com.example.manali_mp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends Fragment {

    Button sendbtn;
    EditText emailid;
    FirebaseAuth firebaseAuth;
    NavController navController;

    public ForgotPassword() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendbtn = view.findViewById(R.id.sendbtn);
        emailid = view.findViewById(R.id.forgotpasswordemail);
        navController = Navigation.findNavController(getActivity(), R.id.hostfragment);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String resetmail = emailid.getText().toString();

                if (!checkEmptyField()) {

                    FirebaseAuth.getInstance()
                            .sendPasswordResetEmail(resetmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity().getApplicationContext(), "Password reset link send!!!", Toast.LENGTH_LONG).show();
                                        navController.navigate((R.id.loginPage));
                                    }
                                }
                            }); }
            }
        });


    }

    private boolean checkEmptyField() {
        if (TextUtils.isEmpty(emailid.getText().toString()))
        {
            emailid.setError("ADD EMAIL");
            emailid.requestFocus();
            return true;

        }
        return  false;
    }
}