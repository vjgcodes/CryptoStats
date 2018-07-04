package com.vj.blockchain;

/**
 * Created by Vijay on 12/8/17.
 */

public class BlockData {
    private double btcValue;
    private double marketCap;
    private long transactionCount;
    private double btcSent;
    private int blockCount;
    private double blockReward;
    private long timeUpdated;

    public long getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(long timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public double getBtcValue() {
        return btcValue;
    }

    public void setBtcValue(double btcValue) {
        this.btcValue = btcValue;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(long transactionCount) {
        this.transactionCount = transactionCount;
    }

    public double getBtcSent() {
        return btcSent;
    }

    public void setBtcSent(double btcSent) {
        this.btcSent = btcSent;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }

    public double getBlockReward() {
        return blockReward;
    }

    public void setBlockReward(double blockReward) {
        this.blockReward = blockReward;
    }

    @Override
    public String toString() {
        return "BlockData{" +
                "btcValue=" + btcValue +
                ", marketCap=" + marketCap +
                ", transactionCount=" + transactionCount +
                ", btcSent=" + btcSent +
                ", blockCount=" + blockCount +
                ", blockReward=" + blockReward +
                '}';
    }
}
