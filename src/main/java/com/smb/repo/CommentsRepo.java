package com.smb.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.smb.entity.CommentEntity;

@Repository
public interface CommentsRepo extends MongoRepository<CommentEntity, String> {
    
}

