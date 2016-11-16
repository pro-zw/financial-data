package me.weizheng.finance.data.domain;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import java.util.Optional;

/**
 * Financial index facts.
 */
public class IndexFacts {
    /**
     * The net assets of the index
     */
    public final Money netAssets;
    public final LocalDate netAssetsDate;

    /**
     * The share outstanding of the index
     */
    public final long sharesOutstanding;
    public final LocalDate sharesOutstandingDate;

    /**
     * The number of holdings in the fund
     */
    public final int numberOfHoldings;
    public final LocalDate numberOfHoldingsDate;

    /**
     * The closing price of the index
     */
    public final float closingPrice;
    public final LocalDate closingPriceDate;

    /**
     * The price to earning ratio (only for equity asset class)
     */
    public final Optional<Float> peRatio;
    public final Optional<LocalDate> peRatioDate;

    /**
     * The price to book value ratio (only for equity asset class)
     */
    public final Optional<Float> pbRatio;
    public final Optional<LocalDate> pbRatioDate;

    /**
     * The annual yield an investor would receive if the most recent fund distribution and
     * current fund price stayed the same going forward.
     */
    public final float distributionYield;
    public final LocalDate distributionYieldDate;

    public IndexFacts(Money netAssets, LocalDate netAssetsDate,
                      long sharesOutstanding, LocalDate sharesOutstandingDate,
                      int numberOfHoldings, LocalDate numberOfHoldingsDate,
                      float closingPrice, LocalDate closingPriceDate,
                      Optional<Float> peRatio, Optional<LocalDate> peRatioDate,
                      Optional<Float> pbRatio, Optional<LocalDate> pbRatioDate,
                      float distributionYield, LocalDate distributionYieldDate
                      ) {
        this.netAssets = netAssets;
        this.netAssetsDate = netAssetsDate;
        this.sharesOutstanding = sharesOutstanding;
        this.sharesOutstandingDate = sharesOutstandingDate;
        this.numberOfHoldings = numberOfHoldings;
        this.numberOfHoldingsDate = numberOfHoldingsDate;
        this.closingPrice = closingPrice;
        this.closingPriceDate = closingPriceDate;
        this.peRatio = peRatio;
        this.peRatioDate = peRatioDate;
        this.pbRatio = pbRatio;
        this.pbRatioDate = pbRatioDate;
        this.distributionYield = distributionYield;
        this.distributionYieldDate = distributionYieldDate;
    }

    @Override
    public String toString() {
        return String.format("Index net assets: %s\nAsset date: %s\n" +
                "Share outstanding: %s\nShare outstanding date: %s\n" +
                "Number of holdings: %s\nNumber of holdings date: %s\n" +
                "Closing price: %s\nClosing price date: %s\n" +
                "PE ratio: %s\nPe ratio date: %s\n" +
                "PB ratio: %s\nPB ratio date: %s\n" +
                "Distribution yield: %s\nDistribution yield date: %s\n",
                netAssets, netAssetsDate,
                sharesOutstanding, sharesOutstandingDate,
                numberOfHoldings, numberOfHoldingsDate,
                closingPrice, closingPriceDate,
                peRatio.isPresent() ? peRatio.get() : "NA",
                peRatioDate.isPresent() ? peRatioDate.get() : "NA",
                pbRatio.isPresent() ? pbRatio.get() : "NA",
                pbRatioDate.isPresent() ? pbRatioDate.get() : "NA",
                distributionYield, distributionYieldDate);
    }
}
