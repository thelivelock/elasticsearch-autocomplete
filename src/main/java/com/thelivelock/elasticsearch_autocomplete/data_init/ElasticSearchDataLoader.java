package com.thelivelock.elasticsearch_autocomplete.data_init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.thelivelock.elasticsearch_autocomplete.model.User;
import com.thelivelock.elasticsearch_autocomplete.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ElasticSearchDataLoader implements CommandLineRunner {

    @Value("classpath:data/users.json")
    private Resource usersJsonFile;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (this.isInitialized()) {
            return;
        }

        List<User> users = this.loadUsersFromFile();
        users.forEach(userService::save);
    }

    private List<User> loadUsersFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(List.class, UserJsonData.class);
        List<UserJsonData> allFakeUsers = objectMapper.readValue(this.usersJsonFile.getFile(), collectionType);
        return allFakeUsers.stream().map(this::from).map(this::generateId).collect(Collectors.toList());
    }

    private User generateId(User user) {
        user.setId(UUID.randomUUID().toString());
        return user;
    }

    private User from(UserJsonData userJson) {
        User user = new User();
        user.setFirstName(userJson.getName());
        user.setLastName(userJson.getSurname());
        user.setCountry(userJson.getRegion());
        return user;
    }

    private boolean isInitialized() {
        return this.userService.count() > 0;
    }
}
