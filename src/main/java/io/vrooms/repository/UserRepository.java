package io.vrooms.repository;

import io.vrooms.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);

	Optional<User> findById(String id);
}
