package com.lin.mongodb;

import com.lin.mongodb.domain.Comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoTemplateTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void queryPage() {
        // 查询条件，更新条件，集合:字符串名称或类名
        Query query = Query.query(Criteria.where("_id").is("1234"));
        Update update = new Update();
        update.inc("likes");
        update.set("comment", "更新后的");
        long modifiedCount = mongoTemplate.updateFirst(query, update, Comment.class).getModifiedCount();
        System.out.println(modifiedCount);
    }
}
