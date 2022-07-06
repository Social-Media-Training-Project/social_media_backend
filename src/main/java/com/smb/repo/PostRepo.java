package com.smb.repo;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.smb.entity.PostEntity;
import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepo extends MongoRepository<PostEntity, String> {
    Optional<List<PostEntity>> findByUserId(String id);
    Optional<List<PostEntity>> findByUserIdOrderByCreatedAtDesc(String id);
    
}
