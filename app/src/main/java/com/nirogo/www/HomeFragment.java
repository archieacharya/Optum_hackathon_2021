package com.nirogo.www;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;
import static com.nirogo.www.MainActivity.mCartItemCount;
import static com.nirogo.www.MainActivity.textCartItemCount;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ShopCategoryAdapter.OnItemClickListener  {

    AlertDialog.Builder builder;
    public static final String SHOP_TYPE = "servicename";
    Button searchItem;
    public static final String CAT = "cat";
    //public static String sho_typeee;
    List<RecyclerViewPoster> imageList;
    List<RecyclerViewPoster2> imageList2;
    List<ShopCategoryList> productList;

    List<PostList> postlistssss;
    SharedPrefManager mInstance;
    private ProgressDialog progressDialog;
    // CardView grocery, fruits, meat, milk, paan, sweets;


    public HomeFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView, recyclerView2, catrecycler, postrecycler;
    private ArrayList<Integer> mImageUrls = new ArrayList<Integer>();
    private ArrayList<Integer> mImageUrls2 = new ArrayList<Integer>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_home, container, false);


        builder = new AlertDialog.Builder(getContext());
        mInstance = new SharedPrefManager(getContext());
        progressDialog = new ProgressDialog(getContext());

        try {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            mInstance = new SharedPrefManager(getContext());
         //   Toast.makeText(getContext(), "id: "+mInstance.getIddd(), Toast.LENGTH_LONG).show();
           // Toast.makeText(getContext(), "docter id: "+mInstance.getMerchantUsersId(), Toast.LENGTH_LONG).show();


            // locationEnabled();
            //loadPoster();
            //loadPoster2();
            catList();
            progressDialog.dismiss();
        }catch (Exception e){

            Toast.makeText(getContext(), "Please check your internet and try again ", Toast.LENGTH_LONG).show();

        }
        //SharedPrefManager.product_id_cnt_two.clear();
        // if(mInstance.getOrdList(getContext()) != null){
       // mInstance.clearordList();
       /// mInstance.getOrdListData();
        //mInstance.ttlprize();

       // mInstance.clearordList();


        //}

      //  alertBpxxx();


        imageList = new ArrayList<>();
        imageList2 = new ArrayList<>();
        productList = new ArrayList<>();
        postlistssss = new ArrayList<>();

       /* mImageUrls.add(R.drawable.poster2);
        mImageUrls.add(R.drawable.poster3);
        mImageUrls.add(R.drawable.pn2);

        mImageUrls2.add(R.drawable.pnew);
        mImageUrls2.add(R.drawable.newpostersecond);



        */




       searchItem = view.findViewById(R.id.searc_item);
        recyclerView = view.findViewById(R.id.recyclerposter);
        recyclerView2 = view.findViewById(R.id.recyclerposter2);
        catrecycler = view.findViewById(R.id.recyclerhome);
        postrecycler = view.findViewById(R.id.recycler_post);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        catrecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        postrecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        searchItem.setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        if(mInstance.getlatitude().equals("not")){

                            alertBpxxx();
                        }else {
                            Intent i = new Intent(v.getContext(), SearchShopActivity.class);
                            startActivity(i);
                        }

                      // Toast.makeText(getActivity(), "Under development", Toast.LENGTH_LONG).show();



                    }
                }
        );
        //  RecyclerViewPosterAdapter adapter = new RecyclerViewPosterAdapter(mImageUrls, getContext());
        // RecyclerViewPosterAdapter2 adapter2 = new RecyclerViewPosterAdapter2(mImageUrls2, getContext());

        //recyclerView.setAdapter(adapter);
        //recyclerView2.setAdapter(adapter2);


        postListtttt();

        return view;
    }

    private void loadPoster() {

        //  if (ide != null) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.FETCH_IMAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject product = jsonArray.getJSONObject(i);

                                //adding the product to product list
                                imageList.add(new RecyclerViewPoster(
                                        product.getInt("id"),
                                        product.getString("image")

                                ));

                            }

                            RecyclerViewPosterAdapter adapter = new RecyclerViewPosterAdapter(HomeFragment.this, imageList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cat_id", "RecyclerViewPoster");
                return params;
            }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(stringRequest);
        // }

    }

    private void loadPoster2() {

        //  if (ide != null) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.FETCH_IMAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject product = jsonArray.getJSONObject(i);

                                //adding the product to product list
                                imageList2.add(new RecyclerViewPoster2(
                                        product.getInt("id"),
                                        product.getString("image")

                                ));

                            }

                            RecyclerViewPosterAdapter2 adapter = new RecyclerViewPosterAdapter2(HomeFragment.this, imageList2);
                            recyclerView2.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cat_id", "RecyclerViewPoster2");
                return params;
            }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(stringRequest);
        // }

    }

    private void postListtttt() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.FETCH_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject product = jsonArray.getJSONObject(i);

                                //Toast.makeText(getActivity(), "CAT"+product.getString("SHP_CAT"), Toast.LENGTH_LONG).show();
                                //Toast.makeText(getActivity(), "DESC"+product.getString("SHP_DESC"), Toast.LENGTH_LONG).show();
                                //adding the product to product list
                                postlistssss.add(new PostList(
                                        product.getString("PST_HEAD"),
                                        product.getString("PST_DESC"),
                                        product.getString("PST_IMG")

                                ));

                            }

                            PostAdapter adapter = new PostAdapter(HomeFragment.this, postlistssss);
                            postrecycler.setAdapter(adapter);
                            //adapter.setOnItemClickListener(HomeFragment.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("TOKEN", "12345");
                return params;
            }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(stringRequest);
        // }

    }


    private void catList() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiUrl.CAT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting product object from json array
                                JSONObject product = jsonArray.getJSONObject(i);

                                //Toast.makeText(getActivity(), "CAT"+product.getString("SHP_CAT"), Toast.LENGTH_LONG).show();
                                //Toast.makeText(getActivity(), "DESC"+product.getString("SHP_DESC"), Toast.LENGTH_LONG).show();
                                //adding the product to product list
                                productList.add(new ShopCategoryList(
                                        product.getInt("SHP_ID"),
                                        product.getString("SHP_CAT"),
                                        product.getString("SHP_IMG"),
                                        product.getString("SHP_DESC")

                                ));

                            }

                            ShopCategoryAdapter adapter = new ShopCategoryAdapter(HomeFragment.this, productList);
                            catrecycler.setAdapter(adapter);
                            adapter.setOnItemClickListener(HomeFragment.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("TOKEN", "SUCCESS");
                return params;
            }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(stringRequest);
        // }

    }






    @Override
    public void onItemClick(int position) {

        ShopCategoryList clickedItem = productList.get(position);


        if(mInstance.getlatitude().equals("not")){

            alertBpxxx();
        }else {

            SharedPrefManager.getInstance(getActivity())
                    .catName(
                            clickedItem.getSHP_CAT()

                    );
            Intent detailIntent = new Intent(getActivity(), SubCategoryActivity.class);
            detailIntent.putExtra(CAT, String.valueOf(clickedItem.getSHP_CAT()));
            startActivity(detailIntent);
        }
//
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
//                    Intent i = new Intent(getContext(), CartActivity.class);
//                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(i);
//                    //finish();
//                }else {
//                    Toast.makeText(getContext(), "Minimum cart value is Rs.50 ", Toast.LENGTH_LONG).show();
//                }
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);

       // Toast.makeText(getActivity(), "Under development", Toast.LENGTH_LONG).show();

    }

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




    public void alertBpxxx(){
        builder.setMessage("This is location based service")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
//                                        Toast.LENGTH_SHORT).show();




                        LocationManager lm = (LocationManager) getActivity().getSystemService(Context. LOCATION_SERVICE ) ;
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
                            new AlertDialog.Builder(getContext() )
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
                                            Toast.makeText(getContext(),"You must enable the gps to set location",
                                                    Toast.LENGTH_SHORT).show();
                                            //  android.os.Process.killProcess(android.os.Process.myPid());


                                        }
                                    })
                                    .show() ;
                        }else {
                            Intent i = new Intent(getContext(), PermissionActivity.class);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }

                    }
                });
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //  Action for 'NO' Button
//                                dialog.cancel();
//                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Please on gps and select your location");
        alert.show();
    }





}
