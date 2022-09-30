package com.lin.mongodb.dao;

import com.lin.mongodb.domain.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 第二个 ID 的类型
 */
public interface CommentRepo extends MongoRepository<Comment, String> {

    /*
    类似jpa的形式
     */
    Page<Comment> findCommentByUserId(String userId, Pageable pageable);
}
