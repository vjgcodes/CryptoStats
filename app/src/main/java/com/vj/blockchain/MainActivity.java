package com.vj.blockchain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vj.blockchain.services.ApiService;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL ="https://blockchain.info/q/";
    private static final String ENCODING ="UTF-8";
    private static final long SATOSHI=100000000;
    private static final long MILLION=1000000000;
    private static final String TAG="MainActivity";
    SwipeRefreshLayout refreshLayout=null;
    private static final NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private static final String[] urls=new String[]{"24hrprice","marketcap","24hrtransactioncount","24hrbtcsent","getblockcount","bcperblock"};
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(CryptoData.class,new CryptoDataDeserializer())
            .create();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.coinmarketcap.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    private static SwipeRefreshLayout.OnRefreshListener refreshListener=null;

    protected RecyclerView mRecyclerView;
    private CoinAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);

        /*
        refreshLayout=findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");
                        new GetData().execute();
                    }
                }
        );
        */


        mRecyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CoinAdapter(getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);
        initDataset();
        final SwipeRefreshLayout swipeRefreshLayout= findViewById(R.id.swiperefresh);

        refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG,"Refreshing ...");
                swipeRefreshLayout.setRefreshing(true);
                initDataset();
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(refreshListener);


    }
    public void initDataset(){
        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("com.vj.blockchain",Context.MODE_PRIVATE);
        final String objectName="CryptoData";
        final String lastUpdatedStr="LastUpdated";
        String json=sharedPref.getString(objectName,null);
        Type listType = new TypeToken<ArrayList<CryptoData>>(){}.getType();
        final List<CryptoData> sharedCryptoData = gson.fromJson(json, listType);
        long lastUpdated=sharedPref.getLong(lastUpdatedStr,Calendar.getInstance().getTimeInMillis());
        if(sharedCryptoData!=null&&(Calendar.getInstance().getTimeInMillis()-lastUpdated)<300000){
            mAdapter.addCoins(sharedCryptoData);
            return;
        }
        final List<CryptoData> cryptoData=new ArrayList<>();
        Call<MarketData> marketDataCall=retrofit.create(ApiService.class).getMarketData();
        marketDataCall.enqueue(new Callback<MarketData>() {
            @Override
            public void onResponse(Call<MarketData> call, Response<MarketData> response) {
                if(response.code()!=200){
                    Log.d(TAG," Response code "+response.code());
                    return;
                }
                MarketData marketData=response.body();
                Log.d(TAG," Data "+marketData);
                cryptoData.add(marketData);

            }

            @Override
            public void onFailure(Call<MarketData> call, Throwable t) {
                Log.d(TAG," marketDataCall Exception  "+t);
            }
        });
        Call<List<CoinData>> coinDataCall=retrofit.create(ApiService.class).getCoinData();
        coinDataCall.enqueue(new Callback<List<CoinData>>() {
            @Override
            public void onResponse(Call<List<CoinData>> call, Response<List<CoinData>> response) {
                if(response.code()!=200){
                    Log.d(TAG," Response code "+response.code());
                    return;
                }
                List<CoinData> coinData=response.body();
                Log.d(TAG," Data "+coinData);
                cryptoData.addAll(coinData);
                mAdapter.addCoins(cryptoData);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(objectName, gson.toJson(cryptoData));
                editor.putLong(lastUpdatedStr, Calendar.getInstance().getTimeInMillis());
                editor.commit();

            }

            @Override
            public void onFailure(Call<List<CoinData>> call, Throwable t) {
                Log.d(TAG," coinDataCall Exception "+t);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                if(refreshListener!=null){
                    refreshListener.onRefresh();
                }
                else {
                    initDataset();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
