package com.example.manali_mp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavController navController;
    public NavigationView navigationView;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setupNavigationView();
    }

    public  void setupNavigationView()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout =findViewById(R.id.Drawer_layout);
        navigationView =findViewById(R.id.NavigationView);

        navController=Navigation.findNavController(SecondActivity.this,R.id.host_fragment2);
        NavigationUI.setupActionBarWithNavController(SecondActivity.this,navController,drawerLayout);
        NavigationUI.setupWithNavController(navigationView,navController);
        navigationView.setNavigationItemSelectedListener(SecondActivity.this);
    }



    @Override

    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.host_fragment2),drawerLayout);
    }

    @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setCheckable(true);
        drawerLayout.closeDrawers();
        int id  = item.getItemId();


         switch (id)
         {
             case R.id.profile:
                 //Toast.makeText(getApplicationContext(), "profile page", Toast.LENGTH_SHORT).show();
                 navController.navigate(R.id.ProfilePage);
                 break;

                 case R.id.logout:

                  FirebaseAuth.getInstance().signOut();
                 Toast.makeText(getApplicationContext(), "Login page", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent (getApplicationContext(),Firstactivity.class);
                 startActivity(intent);

         }
         return true;

    }
}
