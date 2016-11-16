package me.weizheng.finance.data.domain;

import org.joda.time.LocalDate;

import static java.lang.String.format;

/**
 * Financial index basic data.
 */
public class Index {
    /**
     * The date on which the index is created, e.g. 13-Nov-2007
     */
    public final LocalDate inceptionDate;

    /**
     * Asset class of the index, e.g. equity or fixed income
     */
    public final String assetClass;

    /**
     * Name of the index, e.g. iShare Asia 50 ETF.
     */
    public final String name;

    /**
     * The exchange market of the index, e.g. ASX
     */
    public final String exchangeMarket;

    /**
     * The major country exposure of the index, e.g. China
     */
    public final String majorCountryExposure;

    /**
     * Class constructor.
     */
    public Index(LocalDate inceptionDate,
                 String assetClass, String name,
                 String exchangeMarket,
                 String majorCountryExposure) {
        this.inceptionDate = inceptionDate;
        this.assetClass = assetClass;
        this.name = name;
        this.exchangeMarket = exchangeMarket;
        this.majorCountryExposure = majorCountryExposure;
    }

    @Override
    public String toString() {
        return String.format("Index name: %s\n" +
                "Asset class: %s\n" +
                "Inception date: %s\n" +
                "Exchange market: %s\n" +
                "Major country exposure: %s",
                name, assetClass, inceptionDate, exchangeMarket, majorCountryExposure);
    }
}
