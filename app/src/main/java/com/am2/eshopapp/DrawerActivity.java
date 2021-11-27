package com.am2.eshopapp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.am2.eshopapp.Entities.SharedPreferenceEntities;
import com.am2.eshopapp.ui.gallery.GalleryFragment;
import com.am2.eshopapp.ui.home.HomeFragment;
import com.am2.eshopapp.ui.home.UpdateProductFragment;
import com.am2.eshopapp.ui.login.LoginFragment;
import com.am2.eshopapp.ui.register.RegisterFragment;
import com.am2.eshopapp.ui.slideshow.SlideshowFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.am2.eshopapp.databinding.ActivityDrawerBinding;

public class DrawerActivity extends AppCompatActivity {

    public FragmentManager getSupportFragmentManager;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferenceEntities.setContext(this);

        setSupportActionBar(binding.appBarDrawer.toolbar);

        binding.appBarDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_login, R.id.nav_register)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this , R.id.nav_host_fragment_content_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void onClick(View view) {
    }


}