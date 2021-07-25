package com.balsa.teletraderentryapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Symbol implements Parcelable {

    private String id;
    private String symbolName;
    private String tickerSymbol;
    private String last;
    private String high;
    private String low;
    private String bid;
    private String ask;
    private String volume;
    private Date dateTime;
    private String change;
    private String changePrecent;

    public Symbol(String id, String symbolName, String tickerSymbol, String last, String high, String low, String bid, String ask, String volume, Date dateTime, String change, String changePrecent) {
        this.id = id;
        this.symbolName = symbolName;
        this.tickerSymbol = tickerSymbol;
        this.last = last;
        this.high = high;
        this.low = low;
        this.bid = bid;
        this.ask = ask;
        this.volume = volume;
        this.dateTime = dateTime;
        this.change = change;
        this.changePrecent = changePrecent;
    }

    protected Symbol(Parcel in) {
        id = in.readString();
        symbolName = in.readString();
        tickerSymbol = in.readString();
        last = in.readString();
        high = in.readString();
        low = in.readString();
        bid = in.readString();
        ask = in.readString();
        volume = in.readString();
        change = in.readString();
        changePrecent = in.readString();
    }

    public static final Creator<Symbol> CREATOR = new Creator<Symbol>() {
        @Override
        public Symbol createFromParcel(Parcel in) {
            return new Symbol(in);
        }

        @Override
        public Symbol[] newArray(int size) {
            return new Symbol[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChangePrecent() {
        return changePrecent;
    }

    public void setChangePrecent(String changePrecent) {
        this.changePrecent = changePrecent;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "id='" + id + '\'' +
                ", symbolName='" + symbolName + '\'' +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", last=" + last +
                ", high=" + high +
                ", low=" + low +
                ", bid=" + bid +
                ", ask=" + ask +
                ", volume=" + volume +
                ", dateTime=" + dateTime +
                ", change=" + change +
                ", changePrecent=" + changePrecent +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(symbolName);
        dest.writeString(tickerSymbol);
        dest.writeString(last);
        dest.writeString(high);
        dest.writeString(low);
        dest.writeString(bid);
        dest.writeString(ask);
        dest.writeString(volume);
        dest.writeString(change);
        dest.writeString(changePrecent);
    }
}
