package com.lin.mongodb.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "comment") // 执行文档对应的集合
//@CompoundIndex(def = "{'userid':1,'nickname':-1}") // 复合索引,推荐命令行建
public class Comment {
    // 代表_id
    @Id
    private String id;

    private String content;

    @Indexed
    private String userId;

    private String nickname;

    private Integer likes;
}
