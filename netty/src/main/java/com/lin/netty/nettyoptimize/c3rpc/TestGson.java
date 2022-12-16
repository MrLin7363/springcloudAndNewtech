package com.lin.netty.nettyoptimize.c3rpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.lin.netty.nettyadvance.message.Serializer;

import java.lang.reflect.Type;

public class TestGson {

    public static void main(String[] args) {
        System.out.println(new Gson().toJson("test"));
        // Attempted to serialize java.lang.Class: java.lang.String. Forgot to register a type adapter?
        // 需要写个类型转换器，将类转化为String
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec()).create();
        System.out.println(gson.toJson(String.class));
    }

}
