package me.weizheng.finance.data.dao;

import me.weizheng.finance.data.domain.Index;
import me.weizheng.finance.data.domain.IndexFacts;
import me.weizheng.finance.data.domain.IndexFactsSource;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

/**
 * Interface for the tasks that download index facts from the Internet.
 */
public interface IndexFactsDownloader {
    /**
     *
     * @param factsSource The source of the index facts's information
     * @return ImmutablePair whose left is the index and right is the index facts downloaded
     */
    Optional<Pair<Index, IndexFacts>> download(IndexFactsSource factsSource);
}
