
package com.lin.es.basic;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;


public class EsDocInsertBatch {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("127.0.0.1", 9200))
        );

        // 批量插入文档
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "lin5","age","30",
            "sex","男"));
        request.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "lin6","age","20",
            "sex","女"));
        request.add(new IndexRequest().index("user").id("1007").source(XContentType.JSON, "name", "lin7","age","10",
            "sex","女"));
        request.add(new IndexRequest().index("user").id("1008").source(XContentType.JSON, "name", "lin8","age","40",
            "sex","男"));

        final BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        System.out.println(response.getItems());

        esClient.close();
    }
}
