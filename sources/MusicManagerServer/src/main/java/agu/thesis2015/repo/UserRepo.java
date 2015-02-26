package agu.thesis2015.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import agu.thesis2015.domain.User;

/**
 * 
 * @author ltduoc
 *
 */
@Repository
public interface UserRepo extends MongoRepository<User, String> {

}
