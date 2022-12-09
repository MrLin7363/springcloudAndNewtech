package com.lin.netty.nettyadvance.c3ChatRoom.server.session;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class Group {

    private String groupName;

    private Set<String> mermbers;
}
