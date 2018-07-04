package com.vj.blockchain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Vijay on 12/16/17.
 */

public class CoinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private static final DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");
    private static final DecimalFormat moneyFormat = new DecimalFormat("#.##");
    private List<CryptoData> coins;
    private Context context;
    private static final int MARKET_HOLDER=0;
    private static final int COIN_HOLDER=1;
    private static final String TAG="CoinAdapter";

    public CoinAdapter(Context context){
        this.context=context;

    }
    public void addCoins(List<CryptoData> coins){
        if(this.coins!=null){
            this.coins.clear();
        }
        this.coins=coins;
        notifyDataSetChanged();
    }
    public class MarketHolder extends RecyclerView.ViewHolder{
        public TextView marketCap;
        public TextView dayVolume;
        public TextView bitcoinPercentage;
        public TextView activeCurrencies;
        public TextView lastUpdated;

        public MarketHolder(View view){
            super(view);
            marketCap=view.findViewById(R.id.marketcap);
            dayVolume=view.findViewById(R.id.dayvolume);
            bitcoinPercentage=view.findViewById(R.id.bitcoinpercentage);
            activeCurrencies=view.findViewById(R.id.activecurrencies);
            lastUpdated=view.findViewById(R.id.lastupdated);
        }
    }

    public class CoinHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView price;
        public TextView hourlyPercentage;
        public TextView dayPercentage;
        public TextView weekPercentage;
        public TextView dayVolume;
        public TextView marketCap;
        public TextView availableSupply;
        public TextView maxSupply;
        public TextView lastUpdated;
        public CoinHolder(View view){
            super(view);
            title=view.findViewById(R.id.title);
            price=view.findViewById(R.id.price);
            hourlyPercentage=view.findViewById(R.id.hourlypercentage);
            dayPercentage=view.findViewById(R.id.daypercentage);
            weekPercentage=view.findViewById(R.id.weekpercentage);
            dayVolume=view.findViewById(R.id.dayvolume);
            marketCap=view.findViewById(R.id.marketcap);
            availableSupply=view.findViewById(R.id.availablesupply);
            maxSupply=view.findViewById(R.id.maxsupply);
            lastUpdated=view.findViewById(R.id.lastupdated);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if(viewType==MARKET_HOLDER){
            View view= LayoutInflater.from(context).inflate(R.layout.markettotal,parent,false);
            viewHolder=new MarketHolder(view);
        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.coindata,parent,false);
            viewHolder=new CoinHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MarketHolder){
            MarketHolder marketHolder= (MarketHolder) holder;
            MarketData marketData= (MarketData) coins.get(position);

            marketHolder.marketCap.setText("Market Cap : "+dollarFormat.format(marketData.getTotalMarketCapUsd()));
            marketHolder.dayVolume.setText("24 hr Volume : "+dollarFormat.format(marketData.getTotal24hVolumeUsd()));
            marketHolder.bitcoinPercentage.setText("Bitcoin % "+marketData.getBitcoinPercentageOfMarketCap());
            marketHolder.activeCurrencies.setText("Active Currencies : "+marketData.getActiveCurrencies());
            marketHolder.lastUpdated.setText("Last Updated : "+getTimeString(marketData.getLastUpdated()));

        }
        else{

            CoinHolder coinHolder= (CoinHolder) holder;
            CoinData coinData= (CoinData) coins.get(position);
            double price=coinData.getPriceUsd();
            coinHolder.title.setText("#"+coinData.getRank()+" "+coinData.getName()+" ("+coinData.getSymbol()+")");
            coinHolder.price.setText("$"+coinData.getPriceUsd());
            String temp="";
            if(price*coinData.getPercentChange1h()>0){
                temp="+$"+moneyFormat.format(price*coinData.getPercentChange1h()/100)+" [ +"+coinData.getPercentChange1h()+"% ] "+" 1 hr";
            }
            else{
                temp="-$"+moneyFormat.format(Math.abs(price*coinData.getPercentChange1h())/100)+" [ "+coinData.getPercentChange1h()+"% ] "+" 1 hr";
            }
            coinHolder.hourlyPercentage.setText(temp);
            temp="";
            if(price*coinData.getPercentChange24h()>0){
                temp="+$"+moneyFormat.format(price*coinData.getPercentChange24h()/100)+" [ +"+coinData.getPercentChange24h()+"% ] "+" 24 hrs";
            }
            else{
                temp="-$"+moneyFormat.format(Math.abs(price*coinData.getPercentChange24h())/100)+" [ "+coinData.getPercentChange24h()+"% ] "+" 24 hrs";
            }
            coinHolder.dayPercentage.setText(temp);
            temp="";
            if(price*coinData.getPercentChange7d()>0){
                temp="+$"+moneyFormat.format(price*coinData.getPercentChange7d()/100)+" [ +"+coinData.getPercentChange7d()+"% ] "+" week";
            }
            else{
                temp="-$"+moneyFormat.format(Math.abs(price*coinData.getPercentChange7d())/100)+" [ "+coinData.getPercentChange7d()+"% ] "+" week";
            }
            coinHolder.weekPercentage.setText(temp);
            coinHolder.dayVolume.setText("24 hr Volume : "+dollarFormat.format(coinData.get_24hVolumeUsd()));
            temp="Market Cap : ";
            coinHolder.marketCap.setText(temp+dollarFormat.format(coinData.getMarketCapUsd()));
            temp="Available Supply : ";
            coinHolder.availableSupply.setText(temp+coinData.getAvailableSupply());
            temp="Max Supply : ";
            if(coinData.getMaxSupply()!=0){
                coinHolder.maxSupply.setText(temp+coinData.getMaxSupply());
            }
            else{
                coinHolder.maxSupply.setText(temp+"N/A");
            }
            coinHolder.lastUpdated.setText("Last Updated : "+getTimeString(coinData.getLastUpdated()));
            Log.d(TAG," Postition ===== "+position);

        }

    }
    @Override
    public int getItemViewType(int position) {
        //Implement your logic here
        CryptoData cryptoData=coins.get(position);
        if(cryptoData instanceof MarketData){
            return MARKET_HOLDER;
        }
        else{
            return COIN_HOLDER;
        }

    }


    @Override
    public int getItemCount() {
        if(coins!=null){
            return coins.size();
        }
        return 0;
    }

    public static String getTimeString(long then) {

        //Log.d("Time","in loong "+then);

        SimpleDateFormat lollygagFormat = new SimpleDateFormat("MMM dd 'at' hh:mm a");
        lollygagFormat.setTimeZone(TimeZone.getDefault());

        StringBuffer dateStr = new StringBuffer();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(then*1000);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar now = Calendar.getInstance();

        int days = daysBetween(calendar.getTime(), now.getTime());
        int minutes = hoursBetween(calendar.getTime(), now.getTime());
        int hours = minutes / 60;
        if (days == 0) {

            int second = minuteBetween(calendar.getTime(), now.getTime());
            if (minutes > 60) {

                if (hours >= 1 && hours <= 24) {
                    dateStr.append(hours).append("h ago");
                }

            } else {

                if (second <= 10) {
                    dateStr.append("Now");
                } else if (second > 10 && second <= 30) {
                    dateStr.append("few seconds ago");
                } else if (second > 30 && second <= 60) {
                    dateStr.append(second).append("s ago");
                } else if (second >= 60 && minutes <= 60) {
                    dateStr.append(minutes).append("m ago");
                }
            }
        } else if (hours > 24 && days <= 7) {
            dateStr.append(days).append("d ago");
        } else {
            dateStr.append(lollygagFormat.format(calendar.getTime()));
        }
        //Log.d("Time","in string "+dateStr.toString());
        return dateStr.toString();
    }

    public static int minuteBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static int hoursBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.MINUTE_IN_MILLIS);
    }

    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.DAY_IN_MILLIS);
    }
}
