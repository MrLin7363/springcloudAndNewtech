
package com.lin.es.basic;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;


public class EsDocSearchContition {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("127.0.0.1", 9200))
        );

        // 1.条件查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//
//        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age","30")));
//
//        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println("count=" + hits.getTotalHits());
//        System.out.println("times=" + response.getTook());
//        for (SearchHit hit : hits) {
//            System.out.println("=" + hit.getSourceAsString());
//        }

        // 2.分页查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
//        builder.from(4); // （当前页码-1）* 每页的数据条数
//        builder.size(2);
//        request.source(builder);
//
//        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println("count=" + hits.getTotalHits());
//        System.out.println("times=" + response.getTook());
//        for (SearchHit hit : hits) {
//            System.out.println("=" + hit.getSourceAsString());
//        }

        // 3.查询排序
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
//        builder.sort("age", SortOrder.ASC);
//        request.source(builder);
//
//        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println("count=" + hits.getTotalHits());
//        System.out.println("times=" + response.getTook());
//        for (SearchHit hit : hits) {
//            System.out.println("=" + hit.getSourceAsString());
//        }

        // 4.过滤字段
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
//
//        String[] excludes={"name"};
//        String[] includes={};
//        builder.fetchSource(includes,excludes);
//        request.source(builder);
//
//        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println("count=" + hits.getTotalHits());
//        System.out.println("times=" + response.getTook());
//        for (SearchHit hit : hits) {
//            System.out.println("=" + hit.getSourceAsString());
//        }

//        // 5.组合查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.must().add(QueryBuilders.matchQuery("age",30));
//        boolQuery.must().add(QueryBuilders.matchQuery("set","男"));
//        boolQuery.mustNot().add(QueryBuilders.matchQuery("set","女"));
////        boolQueryBuilder.should().add(QueryBuilders.matchQuery("age",40)); // 或 ||
//
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(boolQuery);
//        request.source(builder);
//
//        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println("count=" + hits.getTotalHits());
//        System.out.println("times=" + response.getTook());
//        for (SearchHit hit : hits) {
//            System.out.println("=" + hit.getSourceAsString());
//        }

        // 6.模糊查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        // Fuzziness.ONE 差一个字符可以查询来，偏差距离
//        builder.query(QueryBuilders.fuzzyQuery("name","lin").fuzziness(Fuzziness.ONE));
//        request.source(builder);
//
//        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println("count=" + hits.getTotalHits());
//        System.out.println("times=" + response.getTook());
//        for (SearchHit hit : hits) {
//            System.out.println("=" + hit.getSourceAsString());
//        }

        // 7.高亮查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//
//        HighlightBuilder highlightBuilder=new HighlightBuilder();
//        highlightBuilder.preTags("<font color='red'>");
//        highlightBuilder.postTags("</font>");
//        highlightBuilder.field("name");
//        builder.highlighter(highlightBuilder);
//        // Fuzziness.ONE 差一个字符可以查询来，偏差距离
//        builder.query(QueryBuilders.fuzzyQuery("name","lin").fuzziness(Fuzziness.ONE));
//        request.source(builder);
//
//        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
//
//        SearchHits hits = response.getHits();
//
//        System.out.println("count=" + hits.getTotalHits());
//        System.out.println("times=" + response.getTook());
//        for (SearchHit hit : hits) {
//            System.out.println("=" + hit.getSourceAsString());
//        }

        // 8.聚合查询
        SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder= AggregationBuilders.max("maxAge").field("age");
        builder.aggregation(aggregationBuilder);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();

        System.out.println("count=" + hits.getTotalHits());
        System.out.println("times=" + response.getTook());
        for (SearchHit hit : hits) {
            System.out.println("=" + hit.getSourceAsString());
        }

        esClient.close();
    }
}
