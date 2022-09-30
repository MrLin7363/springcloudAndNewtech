package com.lin.mongodb;

import com.lin.mongodb.dao.CommentRepo;
import com.lin.mongodb.domain.Comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoRepoTest {
    @Autowired
    private CommentRepo commentRepo;

    @Test
    public void insert() {
        Comment comment = new Comment();
        comment.setContent("spring data insert");
        comment.setLikes(10);
        comment.setUserId("12345");
        comment.setNickname("nick name");
        comment.setId("1234");
        commentRepo.insert(comment);
        Comment comment2 = new Comment();
        comment2.setContent("spring data insert");
        comment2.setLikes(199);
        comment2.setUserId("12345");
        comment2.setNickname("nick name22");
        // 不指定ID，mongoDB会自动生成ObjectID
        commentRepo.insert(comment2);
    }

    @Test
    public void queryList() {
        List<Comment> comments = commentRepo.findAll();
        comments.forEach(comment -> System.out.println(comment.toString()));
    }

    @Test
    public void querySingle() {
        Comment comment = commentRepo.findById("6332a266b2aaef383206d6f6").orElseGet(null);
        System.out.println(comment);
    }

    @Test
    public void queryPage() {
        Page<Comment> page = commentRepo.findCommentByUserId("12345", PageRequest.of(0, 10));
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent());
    }
}
