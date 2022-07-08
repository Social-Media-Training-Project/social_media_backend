package com.smb.repo;

import com.smb.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserEntity, String> {
    

//    @Query(value="{userName: ?0}", fields="{ userName: 1, firstName : 1, lastName : 1 }")
	Optional<UserEntity> findByUserName(String userName);
    
//  Example query
//  @Query(value="{ path : ?0}", fields="{ path : 0 }")
//  List<Foo> findByPath(String path);
    
    @Query(value="{}", fields="{ _id: 1, firstName : 1, lastName : 1, gods: 1, fans: 1, userFeed: 1 }")
    List<UserEntity> findAll();
    
}
