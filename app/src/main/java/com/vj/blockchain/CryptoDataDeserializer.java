package com.vj.blockchain;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Vijay on 12/17/17.
 */

public class CryptoDataDeserializer implements JsonDeserializer<CryptoData> {
    @Override
    public CryptoData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null)
            return null;
        else {
            CryptoData cryptoData=null;
            JsonElement active=json.getAsJsonObject().get("active_currencies");
            if(active!=null){
                cryptoData=context.deserialize(json,new TypeToken<MarketData>(){}.getType());
            }
            else {
                cryptoData=context.deserialize(json,new TypeToken<CoinData>(){}.getType());
            }
            return cryptoData;
        }
    }
}
