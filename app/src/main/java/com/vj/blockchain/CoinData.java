package com.vj.blockchain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vijay on 12/13/17.
 */

public class CoinData implements CryptoData{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("price_usd")
    @Expose
    private double priceUsd;
    @SerializedName("price_btc")
    @Expose
    private double priceBtc;
    @SerializedName("24h_volume_usd")
    @Expose
    private double _24hVolumeUsd;
    @SerializedName("market_cap_usd")
    @Expose
    private double marketCapUsd;
    @SerializedName("available_supply")
    @Expose
    private long availableSupply;
    @SerializedName("total_supply")
    @Expose
    private double totalSupply;
    @SerializedName("max_supply")
    @Expose
    private long maxSupply;
    @SerializedName("percent_change_1h")
    @Expose
    private double percentChange1h;
    @SerializedName("percent_change_24h")
    @Expose
    private double percentChange24h;
    @SerializedName("percent_change_7d")
    @Expose
    private double percentChange7d;
    @SerializedName("last_updated")
    @Expose
    private long lastUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public double getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public double get_24hVolumeUsd() {
        return _24hVolumeUsd;
    }

    public void set_24hVolumeUsd(double _24hVolumeUsd) {
        this._24hVolumeUsd = _24hVolumeUsd;
    }

    public double getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(double marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public long getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(long availableSupply) {
        this.availableSupply = availableSupply;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(long maxSupply) {
        this.maxSupply = maxSupply;
    }

    public double getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public double getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
