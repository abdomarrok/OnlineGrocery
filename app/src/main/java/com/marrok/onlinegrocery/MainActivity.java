package com.marrok.onlinegrocery;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FrameLayout fragmentLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();



        setSupportActionBar(toolbar);
        ActionBarDrawerToggle Toggle= new ActionBarDrawerToggle(
                                     this,drawer,toolbar,
                                            R.string.drawerOpen,
                                            R.string.drawerClose);
        drawer.addDrawerListener(Toggle);
        
        Toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        FragmentTransaction trans= getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment_container,new FragmentMain());
        trans.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.category:
                ShowAllCategoriesDialog showAllCategoriesDialog=new ShowAllCategoriesDialog();
                showAllCategoriesDialog.show(getSupportFragmentManager(),"All categories");
                break;
            case R.id.about:
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("About")
                        .setMessage("check us in telgram")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO:url
                            }
                        });
                builder.create().show();

                break;
            case R.id.terms:
                AlertDialog.Builder termbuilder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Terms of use")
                        .setMessage("enjoy")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO:url
                            }
                        });
                termbuilder.create().show();
                break;
            case R.id.licence:
                LicensesDialog licensesDialog=new LicensesDialog();
                licensesDialog.show(getSupportFragmentManager(),"licences dialog");
                break;
            case R.id.card:
                Intent intent =new Intent(MainActivity.this,CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }

    private void initWidget() {
        Log.d(TAG, "initWidget: started");
        drawer= (DrawerLayout) findViewById(R.id.drawer);
        navigationView =(NavigationView) findViewById(R.id.navigationDrawer);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
    }
}