package me.weizheng.finance.data;

import me.weizheng.finance.data.dao.iShareUSIndexFactsDownloader;
import me.weizheng.finance.data.domain.Index;
import me.weizheng.finance.data.domain.IndexFacts;
import me.weizheng.finance.data.domain.IndexFactsSource;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

/**
 * The main function to run.
 */
public class MainRunner {
    public static void main(String [] args) {
        iShareUSIndexFactsDownloader downloader = new iShareUSIndexFactsDownloader();
        IndexFactsSource source = new IndexFactsSource("https://www.ishares.com/us/products/239620/ishares-msci-china-smallcap-etf", "China");

        Optional<Pair<Index, IndexFacts>> optionalPairIndex = downloader.download(source);
        if (optionalPairIndex.isPresent()) {
            Pair<Index, IndexFacts> pairIndex = optionalPairIndex.get();

            System.out.println();
            System.out.println(pairIndex.getLeft());
            System.out.println();
            System.out.print(pairIndex.getRight());
        } else {
            System.out.println("Unable to retrieve the index information");
        }
    }
}
