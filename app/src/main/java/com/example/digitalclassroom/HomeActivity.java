package com.example.digitalclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    String n, em, ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        Intent home = getIntent();
        n = home.getStringExtra("name");
        em = home.getStringExtra("email");
        ph = home.getStringExtra("phone");


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState==null){
          //  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeActivity()).commit();

            //navigationView.setCheckedItem(R.id.profile);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(this, Profile.class);
                intent.putExtra("name", n);
                intent.putExtra("email", em);
                intent.putExtra("phone", ph);
                startActivity(intent);
                break;
            case R.id.course:
                Intent course = new Intent(this, Course.class);
                course.putExtra("name", n);
                course.putExtra("email", em);
                startActivity(course);

                break;
            case R.id.pdf:
                Intent pdf = new Intent(this, Pdf.class);
                startActivity(pdf);
                break;
            case R.id.signout:
                SaveSharedPreference saveSharedPreference = new SaveSharedPreference();
                saveSharedPreference.clearUserName(getApplicationContext());
                startActivity(new Intent(HomeActivity.this, LoginPage.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
