package com.vj.blockchain.services;

import com.vj.blockchain.CoinData;
import com.vj.blockchain.MarketData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Vijay on 12/13/17.
 */

public interface ApiService {
    @GET("global")
    Call<MarketData> getMarketData();

    @GET("ticker")
    Call<List<CoinData>> getCoinData();
}
