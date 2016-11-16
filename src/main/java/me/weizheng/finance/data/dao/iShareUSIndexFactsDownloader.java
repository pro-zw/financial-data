package me.weizheng.finance.data.dao;

import me.weizheng.finance.data.domain.Index;
import me.weizheng.finance.data.domain.IndexFacts;
import me.weizheng.finance.data.domain.IndexFactsSource;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.money.Money;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.NumberFormat;
import java.util.Optional;

/**
 * The iShare US website index facts downloader
 */
public class iShareUSIndexFactsDownloader implements IndexFactsDownloader {
    final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MMM-yyyy");
    final NumberFormat numberFormat = NumberFormat.getNumberInstance(java.util.Locale.US);
    final MoneyFormatter moneyFormatter = new MoneyFormatterBuilder()
            .appendCurrencyCode()
            .appendLiteral(" ")
            .appendAmountLocalized()
            .toFormatter();

    @Override
    public Optional<Pair<Index, IndexFacts>> download(IndexFactsSource factsSource) {
        if (!factsSource.factsUrl.startsWith("https://www.ishares.com/us")) {
            throw new IllegalArgumentException("Invalid index facts url for iShareUSIndexFactsDownloader");
        }

        try {
            final Document indexDoc = Jsoup.connect(factsSource.factsUrl).get();
            final Element keyFactsElem = indexDoc.select("#keyFundFacts").first();
            final Element riskElem = indexDoc.select("#fundamentalsAndRisk").first();

            // Retrieve the basic index information from the url
            String name = indexDoc.select("h1.product-title").first().text();
            LocalDate inceptionDate = dateTimeFormatter.parseLocalDate(
                    keyFactsElem.select("div.col-inceptionDate span.data").first().text());
            String assetClass = keyFactsElem.select("div.col-assetClass span.data").first().text();
            String exchangeMarket = keyFactsElem.select("div.col-exchange span.data").first().text();

            Index index = new Index(inceptionDate, assetClass, name, exchangeMarket, factsSource.majorCountryExposure);

            // Retrieve the index facts from the url
            Money netAssets = moneyFormatter.parseMoney(
                    keyFactsElem.select("div.col-totalNetAssets span.data")
                            .first().text().replace("$", "USD "));
            LocalDate netAssetsDate = dateTimeFormatter.parseLocalDate(
                    keyFactsElem.select("div.col-totalNetAssets span.as-of-date")
                            .first().text().replace("as of ", ""));

            long sharesOutstanding = numberFormat.parse(
                    keyFactsElem.select("div.col-sharesOutstanding span.data").first().text()).longValue();
            LocalDate sharesOutstandingDate = dateTimeFormatter.parseLocalDate(
                    keyFactsElem.select("div.col-sharesOutstanding span.as-of-date")
                            .first().text().replace("as of ", ""));

            int numberOfHoldings = numberFormat.parse(
                    keyFactsElem.select("div.col-numHoldings span.data").first().text()).intValue();
            LocalDate numberOfHoldingsDate = dateTimeFormatter.parseLocalDate(
                    keyFactsElem.select("div.col-numHoldings span.as-of-date")
                            .first().text().replace("as of ", ""));

            float closingPrice = numberFormat.parse(
                    keyFactsElem.select("div.col-closingPrice span.data").first().text()).floatValue();
            LocalDate closingPriceDate = dateTimeFormatter.parseLocalDate(
                    keyFactsElem.select("div.col-closingPrice span.as-of-date")
                            .first().text().replace("as of ", ""));

            Elements peRatioElem = riskElem.select("div.col-priceEarnings span.data");
            Optional<Float> peRatio;
            Optional<LocalDate> peRatioDate;
            if (peRatioElem.isEmpty()) {
                peRatio = Optional.empty();
                peRatioDate = Optional.empty();
            } else {
                peRatio = Optional.of(numberFormat.parse(peRatioElem.first().text()).floatValue());
                peRatioDate = Optional.of(dateTimeFormatter.parseLocalDate(
                        riskElem.select("div.col-priceEarnings span.as-of-date").first().text().replace("as of ", "")));
            }

            Elements pbRatioElem = riskElem.select("div.col-priceBook span.data");
            Optional<Float> pbRatio;
            Optional<LocalDate> pbRatioDate;
            if (pbRatioElem.isEmpty()) {
                pbRatio = Optional.empty();
                pbRatioDate = Optional.empty();
            } else {
                pbRatio = Optional.of(numberFormat.parse(pbRatioElem.first().text()).floatValue());
                pbRatioDate = Optional.of(dateTimeFormatter.parseLocalDate(
                        riskElem.select("div.col-priceBook span.as-of-date").first().text().replace("as of ", "")));
            }

            float distributionYield = numberFormat.parse(
                    riskElem.select("div.col-distYield span.data")
                            .first().text().replace("%", "")).floatValue();
            LocalDate distributionYieldDate = dateTimeFormatter.parseLocalDate(
                    riskElem.select("div.col-distYield span.as-of-date")
                            .first().text().replace("as of ", ""));

            IndexFacts indexFacts = new IndexFacts(netAssets, netAssetsDate,
                    sharesOutstanding, sharesOutstandingDate,
                    numberOfHoldings, numberOfHoldingsDate,
                    closingPrice, closingPriceDate,
                    peRatio, peRatioDate,
                    pbRatio, pbRatioDate,
                    distributionYield, distributionYieldDate);

            return Optional.of(new ImmutablePair<>(index, indexFacts));
        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }
}
