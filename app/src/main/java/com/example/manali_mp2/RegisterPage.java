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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterPage extends Fragment {


    EditText edt_email,edt_password,edt_cpassword,city,gender,dob,name;
    Button btn_register;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private NavController navController;



    public RegisterPage() {
        // Required empty   public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_email = view.findViewById(R.id.EID);
        edt_password = view.findViewById(R.id.password);
        edt_cpassword = view.findViewById(R.id.cpassword);
        name = view.findViewById(R.id.Name);
        dob = view.findViewById(R.id.DOB);
        city = view.findViewById(R.id.city);
        gender = view.findViewById(R.id.Gender);
        btn_register = view.findViewById(R.id.Registerbtn);

        navController = Navigation.findNavController(getActivity(),R.id.hostfragment);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkEmptyField())
                {
                    if(edt_password.getText().length()<6)
                    {
                        edt_password.setError("Invalid password,password should atleast 7 characters");
                        edt_password.requestFocus();
                    }
                    else
                    {

                        if(!edt_password.getText().toString().equals(edt_cpassword.getText().toString()))
                        {
                            edt_cpassword.setError("password not match");
                            edt_cpassword.requestFocus();
                        }else
                        {
                            String email  = edt_email.getText().toString();
                            String psd  = edt_password.getText().toString();
                            String Name = name.getText().toString();
                            String City = city.getText().toString();
                            String Gender  = gender.getText().toString();
                            String bod = dob.getText().toString();

                            Person person = new Person(email,psd,Name,City,Gender,bod);
                            createUser(person);
                        }
                    }
                }
            }
        });
    }

    public boolean checkEmptyField()
    {
        if(TextUtils.isEmpty(edt_email.getText().toString()))
        {
            edt_email.setError("Email cannot be empty!");
            edt_email.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(edt_password.getText().toString()))
        {
            edt_password.setError("passwoed cannot be empty!!");
            edt_password.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(edt_cpassword.getText().toString()))
        {
            edt_cpassword.setError(" confirm passwoed cannot be empty!!");
            edt_cpassword.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(name.getText().toString()))
        {
            name.setError("Name cannot be empty!!");
            name.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(dob.getText().toString()))
        {
            dob.setError("Birtdate cannot be empty!!");
            dob.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(city.getText().toString()))
        {
            city.setError("city cannot be empty!!");
            city.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(gender.getText().toString()))
        {
            gender.setError("Gender cannot be empty!!");
            gender.requestFocus();
            return true;
        }
         return  false;
    }

    public void createUser(Person person)
    {

        auth.createUserWithEmailAndPassword(person.getEmail(),person.getPassword())
                .addOnCompleteListener(getActivity(),task -> {

                    if(task.isSuccessful())
                    {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        writefirestore(person,firebaseUser);
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Registraion Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void writefirestore(Person person, FirebaseUser firebaseUser)
    {
        Map<String,Object> usermap = new HashMap<>();
        usermap.put( "name",person.getName());
        usermap.put("city",person.getCity());
        usermap.put("email",person.getEmail());
        usermap.put("gender",person.getGender());
        usermap.put("birthdate",person.getBirthdate());

        firestore.collection("User").document(firebaseUser.getUid())
                .set(usermap)
                .addOnCompleteListener(getActivity(),task ->
                {
                    if(task.isSuccessful()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        navController.navigate((R.id.loginPage));
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Registraion Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}