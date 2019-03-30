package com.roshankc.stockbazzar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.roshankc.myclasses.BottomNavigationViewHelper;
import com.roshankc.myclasses.StockDetails;
import com.roshankc.myclasses.User;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileActivity extends AppCompatActivity {

    private User user;
    private SearchView searchView;
    private ImageView btnLogout;
    private TextView tvProfileDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUpViews();
        user= (User) getIntent().getSerializableExtra("user");

        tvProfileDetail.setText("First Name: "+user.getFirstName()+"\nLast Name: "+user.getLastName()+"\nEmail Address: "+user.getEmailAddress());

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menus=bottomNavigationView.getMenu();
        MenuItem menuItem=menus.getItem(4);
        menuItem.setChecked(true);




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.menu_home:{
                        Intent intent= new Intent(ProfileActivity.this,LoggedInPage.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }

                    case R.id.menu_notes:{
                        Intent intent= new Intent(ProfileActivity.this,NotesActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.menu_profile:{
                        break;
                    }
                    case R.id.menu_stock_portfolio:{
                        Intent intent= new Intent(ProfileActivity.this,PortfolioActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.menu_watchlist:{
                        Intent intent= new Intent(ProfileActivity.this,WatchListActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }

                }


                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String URL=LoggedInPage.PREFIXURL+"/stock/"+s.trim()+"/batch?types=quote";
                JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, URL,null,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject q=response.getJSONObject("quote");
                            StockDetails stockDetails=new StockDetails();
                            stockDetails.setSymbol(q.getString("symbol"));
                            stockDetails.setCompanyName(q.getString("companyName"));
                            stockDetails.setSymbol(q.getString("symbol"));
                            stockDetails.setPrimaryExchange(q.getString("primaryExchange"));
                            stockDetails.setLatestPrice(q.getDouble("latestPrice"));
                            stockDetails.setLatestVolume(q.getInt("latestVolume"));
                            stockDetails.setPreviousClose(q.getDouble("previousClose"));
                            stockDetails.setChange(q.getDouble("change"));
                            stockDetails.setChangePercent(q.getDouble("changePercent"));
                            stockDetails.setWeek52High(q.getDouble("week52High"));
                            stockDetails.setWeek52High(q.getDouble("week52High"));
                            stockDetails.setMarketCap(q.getInt("week52Low"));
                            stockDetails.setLatestTime(q.getString("latestTime"));
                            stockDetails.setLatestSource(q.getString("latestSource"));

                            Intent intent= new Intent(ProfileActivity.this,SearchActivity.class);
                            intent.putExtra("user",user);
                            intent.putExtra("stockDetails",stockDetails);
                            startActivity(intent);
                            finish();
                        }catch (JSONException e){
                            Log.d("roshan", "result: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent intent= new Intent(ProfileActivity.this,SearchActivity.class);
                        intent.putExtra("user",user);
                        StockDetails stockDetails= null;
                        Log.d("roshan", "onErrorResponse: not found");
                        intent.putExtra("stockDetails",stockDetails);
                        startActivity(intent);
                    }
                });


                RequestQueue queue= Volley.newRequestQueue(ProfileActivity.this);
                queue.add(request);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProfileActivity.this,MainActivity.class);
                user=null;
                startActivity(intent);
                finish();
            }
        });


    }


    private void setUpViews(){
        searchView= findViewById(R.id.sv_searchPrA);
        btnLogout=findViewById(R.id.btnLogout);
        tvProfileDetail=findViewById(R.id.tvProfileDetail);
    }


}
