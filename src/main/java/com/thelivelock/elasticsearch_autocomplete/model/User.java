package com.thelivelock.elasticsearch_autocomplete.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "users")
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String country;
}
