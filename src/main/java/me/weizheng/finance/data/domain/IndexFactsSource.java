package me.weizheng.finance.data.domain;

/**
 * The source of the index facts's information
 */
public class IndexFactsSource {
    /**
     * The url used to download the facts of the index
     */
    public final String factsUrl;

    /**
     * The major country exposure of the index, e.g. China
     * The information cannot retrieved easily from every index facts source, so
     * we set it manually here for now
     */
    public final String majorCountryExposure;

    public IndexFactsSource(String factsUrl,
                            String majorCountryExposure) {
        this.factsUrl = factsUrl;
        this.majorCountryExposure = majorCountryExposure;
    }
}
