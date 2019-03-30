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
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roshankc.myclasses.BottomNavigationViewHelper;
import com.roshankc.myclasses.StockDetails;
import com.roshankc.myclasses.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    private TextView tvResponse;
    private SearchView svSearch;
    private User user;
    private StockDetails stockDetails;
    private BottomNavigationView bottomNavigationView;
    private Button btnSell;
    private Button btnBuy;
    private Button btnAddToWatchlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setUpViews();

        user=(User) getIntent().getSerializableExtra("user");
        stockDetails=(StockDetails) getIntent().getSerializableExtra("stockDetails");
        if(stockDetails==null){
            tvResponse.setText("Company Not found with that ticker symbol");
        }else{
            tvResponse.setText("("+stockDetails.getSymbol()+") "+stockDetails.getCompanyName()+" "+stockDetails.getLatestPrice()+" "+stockDetails.getChange());
        }



        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.menu_home:{
                        Intent intent= new Intent(SearchActivity.this,LoggedInPage.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }

                    case R.id.menu_notes:{
                        Intent intent= new Intent(SearchActivity.this,NotesActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.menu_profile:{
                        Intent intent= new Intent(SearchActivity.this,ProfileActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.menu_stock_portfolio:{
                        Intent intent= new Intent(SearchActivity.this,PortfolioActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.menu_watchlist:{
                        Intent intent= new Intent(SearchActivity.this,WatchListActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String URL=LoggedInPage.PREFIXURL+"/stock/"+s.trim()+"/batch?types=quote";
                JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, URL,null,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject q=response.getJSONObject("quote");
                            stockDetails= new StockDetails();
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
                            tvResponse.setText("("+stockDetails.getSymbol()+") "+stockDetails.getCompanyName()+" "+stockDetails.getLatestPrice()+" "+stockDetails.getChange());
                        }catch (JSONException e){

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvResponse.setText("No company found with that Ticker Symbol");
                        stockDetails=null;
                    }
                });

                RequestQueue queue= Volley.newRequestQueue(SearchActivity.this);
                queue.add(request);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stockDetails!=null){
                    Intent intent= new Intent(SearchActivity.this,BuyTransactionActivity.class);
                    intent.putExtra("user",user);
                    intent.putExtra("stockDetails",stockDetails);
                    finish();
                    startActivity(intent);
                }
            }
        });

        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stockDetails!=null){
                    Intent intent= new Intent(SearchActivity.this,SellTransactionActivity.class);
                    intent.putExtra("user",user);
                    intent.putExtra("stockDetails",stockDetails);
                    finish();
                    startActivity(intent);
                }
            }
        });

        btnAddToWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stockDetails!=null){

                }
            }
        });


    }

    private void setUpViews(){
        tvResponse=(TextView)findViewById(R.id.tv_response);
        svSearch=findViewById(R.id.sv_searchSA);
        bottomNavigationView=findViewById(R.id.bottomNav);
        btnBuy=findViewById(R.id.btnBuy);
        btnSell=findViewById(R.id.btnSell);
        btnAddToWatchlist=findViewById(R.id.btnAddtoWatchlist);
    }


}
