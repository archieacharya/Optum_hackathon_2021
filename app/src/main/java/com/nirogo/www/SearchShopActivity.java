package com.nirogo.www;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchShopActivity extends AppCompatActivity  implements SearchShopAdapter.OnItemClickListener {

    public static final String MMMMM = "servicename";
    public static final String MMMMMN = "image";
    SharedPrefManager mInstance;
    String lati, longi;
    int check_delete = 10;
    SearchView searchQuer;
    AlertDialog.Builder builder;

    public static final String SHOP_LICENSE_NO_LOCAL = "jsjdaajnjd";//SHOP_AREA
    public static final String SHOP_AREA_LOCAL = "jsjdajbjja";
    public static final String SHOP_NAME_LOCAL = "jsjdaaa";
    public static final String SHOP_TYPENEW_LOCAL = "jsjndd";
    public static final String SHOP_ID_LOCAL = "jsjnddwserde";
    public static final String MERCHANT_USERS_ID_LOCAL = "jsjnddwserdddfe";

    public static final String MER_LAT = "jsjnddmerlat";
    public static final String MER_LON = "jsjnddwserdemerlon";
    public static final String MER_DISTANCE = "jsjnddwserdddfemerdistance";
    private int backpressvariable;
    private RecyclerView recyclerView;
    private List<SearchShopList> contacts;
    private SearchShopAdapter adapter;
    //ProgressBar progressBar;
    // public static ProgressDialog progressDialog;
    TextView search;
    String[] item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop);

        //progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recycler_inventory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchQuer = findViewById(R.id.inventorysearch);

        // progressDialog = new ProgressDialog(this);

        mInstance = new SharedPrefManager(this);
        lati = String.valueOf(mInstance.getlatitude());
        longi = String.valueOf(mInstance.getLongitude());
        // fetchContact(lati, longi, "");
        searchQuer.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchContact(lati, longi, query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                fetchContact(lati, longi, newText);
                return false;
            }
        });
    }


    public void fetchContact(String lat, String lon, String key){

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<SearchShopList>> call = apiInterface.getShopList(lat, lon, key);
        call.enqueue(new Callback<List<SearchShopList>>() {
            @Override
            public void onResponse(@NonNull Call<List<SearchShopList>> call, @NonNull Response<List<SearchShopList>> response) {
                //progressBar.setVisibility(View.GONE);
                contacts = response.body();
                adapter = new SearchShopAdapter(SearchShopActivity.this, contacts);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(SearchShopActivity.this);
            }

            @Override
            public void onFailure(@NonNull Call<List<SearchShopList>> call, @NonNull Throwable t) {
                // progressBar.setVisibility(View.GONE);
                Toast.makeText(SearchShopActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

//   @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        assert searchManager != null;
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                fetchContact(userid, query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                fetchContact(userid, newText);
//                return false;
//            }
//        });
//        return true;
//    }
//
//


    @Override
    public void onItemClick(int position) {

        SearchShopList clickedItem = contacts.get(position);
        // Toast.makeText(getApplicationContext(), "Under development"+clickedItem.getMerchant_users_id(), Toast.LENGTH_LONG).show();
        SharedPrefManager.getInstance(getApplicationContext())
                .merchantUsersId(
                        clickedItem.getMerchant_users_id()
                );
        Intent detailIntent = new Intent(getApplicationContext(), DocterDetailActivity.class);
        detailIntent.putExtra("DOCTER_ID", String.valueOf(clickedItem.getMerchant_users_id()));
        detailIntent.putExtra("DOCTER_NAME", String.valueOf(clickedItem.getShop_name()));
        detailIntent.putExtra("DOCTER_IMAGE", String.valueOf(clickedItem.getImg()));
        startActivity(detailIntent);
    }
}




