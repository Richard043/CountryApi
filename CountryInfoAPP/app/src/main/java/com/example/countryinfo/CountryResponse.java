package com.example.countryinfo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CountryResponse {
    @SerializedName("country")
    private String country;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("continent")
    private String continent;

    @SerializedName("capital")
    private String capital;

    @SerializedName("population")
    private int population;

    @SerializedName("timezones")
    private List<String> timezones;

    @SerializedName("currency")
    private Currency currency;

    @SerializedName("flag_url")
    private String flagUrl;

    // Public getters
    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getContinent() {
        return continent;
    }

    public String getCapital() {
        return capital;
    }

    public int getPopulation() {
        return population;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    // Nested Currency class with getters
    public static class Currency {
        @SerializedName("code")
        private String code;
        @SerializedName("name")
        private String name;
        @SerializedName("symbol")
        private String symbol;

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
