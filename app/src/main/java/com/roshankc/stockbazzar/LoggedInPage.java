package com.roshankc.stockbazzar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class LoggedInPage extends AppCompatActivity {

    private TextView tvName;
    private User user;
    private SearchView svSearch;
    private StockDetails stockDetails;

    public static final String PREFIXURL="https://api.iextrading.com/1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_page);

        setUpViews();
        user= (User) getIntent().getSerializableExtra("user");

        tvName.setText(Character.toUpperCase(user.getFirstName().charAt(0))+user.getFirstName().substring(1,user.getFirstName().length())+" "+Character.toUpperCase(user.getLastName().charAt(0))+user.getLastName().substring(1,user.getLastName().length()));

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menus=bottomNavigationView.getMenu();
        MenuItem menuItem=menus.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.menu_home:{
                        break;
                    }

                    case R.id.menu_notes:{
                        Intent intent= new Intent(LoggedInPage.this,NotesActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.menu_profile:{
                        Intent intent= new Intent(LoggedInPage.this,ProfileActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.menu_stock_portfolio:{
                        Intent intent= new Intent(LoggedInPage.this,PortfolioActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.menu_watchlist:{
                        Intent intent= new Intent(LoggedInPage.this,WatchListActivity.class);
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
                String URL=PREFIXURL+"/stock/"+s.trim()+"/batch?types=quote";
                JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, URL,null,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject q=response.getJSONObject("quote");
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

                            Intent intent= new Intent(LoggedInPage.this,SearchActivity.class);
                            intent.putExtra("user",user);
                            intent.putExtra("stockDetails",stockDetails);
                            finish();
                            startActivity(intent);
                        }catch (JSONException e){
                            Log.d("roshan", "result: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent intent= new Intent(LoggedInPage.this,SearchActivity.class);
                        intent.putExtra("user",user);
                        stockDetails= null;
                        intent.putExtra("stockDetails",stockDetails);
                        finish();
                        startActivity(intent);
                    }
                });


                RequestQueue queue= Volley.newRequestQueue(LoggedInPage.this);
                queue.add(request);
               return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    private void setUpViews(){
        tvName=findViewById(R.id.tvName);
        svSearch=findViewById(R.id.sv_search);
        stockDetails=new StockDetails();
    }

}
