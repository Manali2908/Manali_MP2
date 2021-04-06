package com.example.manali_mp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginPage extends Fragment {




    EditText email,psd_login;
    TextView register,forgotpsd;
    Button loginbtn;
    public  NavController navController;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;




    public LoginPage() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_page, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        register= view.findViewById(R.id.login_register);
        loginbtn=view.findViewById(R.id.Loginbtn);
        forgotpsd = view.findViewById(R.id.FP);
        email = view.findViewById(R.id.Email);
        psd_login = view.findViewById(R.id.password);

        navController = Navigation.findNavController(getActivity(),R.id.hostfragment);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.registerPage);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckEmptyField())
                {
                    String user_email = email.getText().toString();
                    String user_psd = psd_login.getText().toString();

                    Login_detais(user_email,user_psd);
                }
            }
        });

        forgotpsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.forgotPassword);
            }
        }); }

    private void Login_detais(String user_email, String user_psd) {

        auth.signInWithEmailAndPassword(user_email,user_psd)
                .addOnCompleteListener(getActivity(),task -> {
                    if(task.isSuccessful())
                    {
                        firebaseUser = auth.getCurrentUser();
                        Toast.makeText(getActivity().getApplicationContext(), "Log In Succesful!!", Toast.LENGTH_SHORT).show();
                        updateUI(firebaseUser);
                    }else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Log in failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LoginFragment","on start called!");

        firebaseUser = auth.getCurrentUser();
        if(firebaseUser!=null)
        {
            updateUI(firebaseUser);
            Toast.makeText(getActivity().getApplicationContext(), "User already signing", Toast.LENGTH_LONG).show();
        }


    }

    private void updateUI(FirebaseUser firebaseUser)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",firebaseUser);
        Intent intent = new Intent(getContext(),SecondActivity.class);
        startActivity(intent);
    }

    private boolean CheckEmptyField()
    {
       if(TextUtils.isEmpty(email.getText().toString()))
       {
           email.setError("EmailId cannot be empty!!");
           email.requestFocus();
           return true;
       }else if(TextUtils.isEmpty(psd_login.getText().toString()))
        {
            psd_login.setError("Enter the valid password");
            psd_login.requestFocus();
            return true;
        }
        return  false;
    }

}