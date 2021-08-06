package com.nirogo.www;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   // public static final String SHOP_TYPE = "servicename";
   // CardView grocery, fruits, meat, milk, paan, sweets;

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    TextView locationtext;
    public static TextView textCartItemCount;
    public static int mCartItemCount = 1;
    SharedPrefManager mInstance;
    String add, longitudeaa, latitudeaaa, latlongbothaaa;
   // CardView grocery, fruits, meat, milk, paan, sweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }



        //grocery = findViewById(R.id.grocerycard);
        //fruits = findViewById(R.id.fruits);
       // meat = findViewById(R.id.meat);
       // milk = findViewById(R.id.milk);
       // paan = findViewById(R.id.paan);
       // sweets = findViewById(R.id.sweets);



        locationtext = findViewById(R.id.location);
        mInstance = new SharedPrefManager(this);
        add = mInstance.getShortaddress();
        longitudeaa = mInstance.getLongitude();
        latitudeaaa = mInstance.getlatitude();
        latlongbothaaa = mInstance.getLatlongboth();

        if (add == null){
           locationtext.setText("Select Location");
        }else {
            locationtext.setText(add);
        }

        mInstance = new SharedPrefManager(this);
        //SharedPrefManager.product_id_cnt_two.clear();
        // if(mInstance.getOrdList(getContext()) != null){
        // mInstance.clearordList();

        // friends for toolbar we use v7 package
        //Toast.makeText(getApplicationContext(), "lati "+latitudeaaa, Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "hello ", Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "longi "+longitudeaa, Toast.LENGTH_LONG).show();
       // Toast.makeText(getApplicationContext(), "ttl_item "+Integer.parseInt(SharedPrefManager.ttlitem), Toast.LENGTH_LONG).show();


       /* grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(view.getContext(), Grocery.class);
                i.putExtra(SHOP_TYPE, "Grocery & Staples");
                startActivity(i);
            }
        });

        */


        locationtext.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        //locationEnabled ();
                        LocationManager lm = (LocationManager) getSystemService(Context. LOCATION_SERVICE ) ;
                        boolean gps_enabled = false;
                        boolean network_enabled = false;
                        try {
                            gps_enabled = lm.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
                        } catch (Exception e) {
                            e.printStackTrace() ;
                        }
                        try {
                            network_enabled = lm.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;
                        } catch (Exception e) {
                            e.printStackTrace() ;
                        }
                        if (!gps_enabled && !network_enabled) {
                            new AlertDialog.Builder(MainActivity.this )
                                    .setMessage( "GPS Enable" )
                                    .setPositiveButton( "Settings" , new
                                            DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick (DialogInterface paramDialogInterface , int paramInt) {
                                                    startActivity( new Intent(Settings. ACTION_LOCATION_SOURCE_SETTINGS )) ;
                                                }
                                            })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"You must enable the gps to set location",
                                   Toast.LENGTH_SHORT).show();
                                          //  android.os.Process.killProcess(android.os.Process.myPid());


                                        }
                                    })
                                    .show() ;
                        }else {
                            Intent i = new Intent(v.getContext(), PermissionActivity.class);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            //finish();
                        }


                    }
                }
        );

        final Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.menu, null);
                toolbar.setNavigationIcon(d);
            }
        });


        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
        fragmentTransaction.commit();



    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//
//        final MenuItem menuItem = menu.findItem(R.id.action_cart);
//
//        View actionView = menuItem.getActionView();
//        textCartItemCount = actionView.findViewById(R.id.cart_badge);
//
//        setupBadge();
//
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuItem);
//            }
//        });
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.action_cart: {
//
//                if (Integer.parseInt(mInstance.getTTLPRIZE()) > 49) {
//
//                    Intent i = new Intent(getApplicationContext(), CartActivity.class);
//                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(i);
//                    //finish();
//                }else {
//                    Toast.makeText(getApplicationContext(), "Minimum cart value is Rs.50 ", Toast.LENGTH_LONG).show();
//                }
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void setupBadge() {
//
//        if (textCartItemCount != null) {
//            if (mCartItemCount == 0) {
//                if (textCartItemCount.getVisibility() != View.GONE) {
//                    textCartItemCount.setVisibility(View.GONE);
//                }
//            } else {
//                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
//                if (textCartItemCount.getVisibility() != View.VISIBLE) {
//                    textCartItemCount.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//    }
//

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();


        if (id == R.id.homemenu) {
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
            fragmentTransaction.commit();
        }
//        else if (id == R.id.profilemenu) {
//            startActivity(new Intent(MainActivity.this, ProfileCustomer.class));
//        }
//        else if (id == R.id.ordermenu) {
//            startActivity(new Intent(MainActivity.this, OrderHistory.class));
//        }
        else if (id == R.id.refermenu) {
            startActivity(new Intent(MainActivity.this, ReferActivity.class));
        }
        else if (id == R.id.supportmenu) {
            startActivity(new Intent(MainActivity.this, SupportActivity.class));
        }
        else if (id == R.id.aboutmenu) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));

        }

        else if (id == R.id.Legal) {
            startActivity(new Intent(MainActivity.this, legal.class));
        }

        else if (id == R.id.ordermenu) {
            startActivity(new Intent(MainActivity.this, AppointmentHistory.class));
        }

        else if (id == R.id.profilemenu) {
            startActivity(new Intent(MainActivity.this, ProfileCustomer.class));
        }

        else if (id == R.id.signOutmenu) {
            SharedPrefManager.getInstance(this).logout();
            Intent i = new Intent(getApplicationContext(), SignIn.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }

//        else if (id == R.id.returnHistory) {
//            startActivity(new Intent(MainActivity.this, OrdReturnActivity.class));
//
//        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
