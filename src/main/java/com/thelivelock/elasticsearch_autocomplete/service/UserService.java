package com.thelivelock.elasticsearch_autocomplete.service;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhrasePrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.thelivelock.elasticsearch_autocomplete.model.User;
import com.thelivelock.elasticsearch_autocomplete.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public long count() {
        return this.userRepository.count();
    }

    public List<User> search(String keywords) {
        MatchPhrasePrefixQuery query = QueryBuilders.matchPhrasePrefix().field("country").query(keywords).build();
        NativeQuery nativeQuery = NativeQuery.builder().withQuery(query._toQuery()).build();
        SearchHits<User> result = this.elasticsearchOperations.search(nativeQuery, User.class);
        return result.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
