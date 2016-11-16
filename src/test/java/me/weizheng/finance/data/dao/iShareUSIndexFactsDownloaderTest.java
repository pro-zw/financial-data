package me.weizheng.finance.data.dao;


import me.weizheng.finance.data.domain.Index;
import me.weizheng.finance.data.domain.IndexFacts;
import me.weizheng.finance.data.domain.IndexFactsSource;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Optional;

public class iShareUSIndexFactsDownloaderTest {
    @Test
    public void downloadIndexFacts() {
        iShareUSIndexFactsDownloader downloader = new iShareUSIndexFactsDownloader();
        IndexFactsSource source = new IndexFactsSource("https://www.ishares.com/us/products/239620/ishares-msci-china-smallcap-etf", "China");

        Optional<Pair<Index, IndexFacts>> optionalPairIndex = downloader.download(source);
        assertTrue(optionalPairIndex.isPresent());

        Index index = optionalPairIndex.get().getLeft();
        assertEquals(index.name, "iShares MSCI China Small-Cap ETF");
        assertEquals(index.assetClass, "Equity");
        assertEquals(index.majorCountryExposure, "China");
    }
}
