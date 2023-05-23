package com.edatwhite.smkd.service.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.edatwhite.smkd.entity.DocumentDTO;
import com.edatwhite.smkd.entity.smkdocument.SMKDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Repository
public class ESQuery {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String indexName = "smk_index_test_1";

    public SMKDoc getDocumentById(String id) throws IOException {

        SMKDoc document = null;
        GetResponse<SMKDoc> response = elasticsearchClient.get(g -> g
                        .index(indexName)
                        .id(id),
                SMKDoc.class
        );

        if (response.found()) {
            document = response.source();
            System.out.println("Product name " + document.getName());
        } else {
            System.out.println("Product not found");
        }

        return document;

    }

    public String deleteDocumentById(String id) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(id));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Document with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        System.out.println("Document not found");
        return new StringBuilder("Document with id " + deleteResponse.id() + " does not exist.").toString();

    }

//    public String createOrUpdateDocument(Product product) throws IOException {
//
//        IndexResponse response = elasticsearchClient.index(i -> i
//                .index(indexName)
//                .id(product.getId())
//                .document(product)
//        );
//        if (response.result().name().equals("Created")) {
//            return new StringBuilder("Document has been successfully created.").toString();
//        } else if (response.result().name().equals("Updated")) {
//            return new StringBuilder("Document has been successfully updated.").toString();
//        }
//        return new StringBuilder("Error while performing the operation.").toString();
//    }

    public String createOrUpdateDocument(SMKDoc document) throws IOException {
        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(document.getId())
                .document(document)
        );
        if (response.result().name().equals("Created")) {
            return new StringBuilder("Document has been successfully created.").toString();
        } else if (response.result().name().equals("Updated")) {
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }


    public List<SMKDoc> searchAllDocuments() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, SMKDoc.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<SMKDoc> documents = new ArrayList<>();
        for (Hit object : hits) {
            System.out.println((SMKDoc) object.source());
            documents.add((SMKDoc) object.source());
        }

        return documents;
    }

    public List<DocumentDTO> searchDocument(String value) throws IOException {
        System.out.println("Code " + value);

        MultiMatchQuery multiMatchQuery = QueryBuilders.multiMatch()
                .query(value)
                .fields("content.chapter_title", "content.chapter")
                .build();
        BoolQuery boolQuery = QueryBuilders.bool()
                .must(multiMatchQuery._toQuery())
                .build();

        NestedQuery nestedQuery = QueryBuilders.nested()
                .path("content")
                .query(boolQuery._toQuery())
                .build();

        MultiMatchQuery multiMatchQuery1 = QueryBuilders.multiMatch()
                .query(value)
                .fields("code", "name", "appendix")
                .build();

        BoolQuery boolQuery1 = QueryBuilders.bool()
                .must(nestedQuery._toQuery(), multiMatchQuery1._toQuery())
                .build();

//        System.out.println(boolQuery1.toString());

        Map<String, HighlightField> field = new HashMap();
        field.put("code", new HighlightField.Builder().build());
        field.put("name", new HighlightField.Builder().build());
        field.put("content.chapter_title", new HighlightField.Builder().build());
        field.put("content.chapter", new HighlightField.Builder().build());
        field.put("appendix", new HighlightField.Builder().build());

        Highlight.Builder highlight = new Highlight.Builder().fields(field);

        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName).query(boolQuery1._toQuery()).highlight(highlight.build()));
        System.out.println(searchRequest.query().toString());

        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, SMKDoc.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<DocumentDTO> documents = new ArrayList<>();
        for (Hit object : hits) {

            System.out.println((SMKDoc) object.source());
//            SMKDoc doc = new SMKDoc(
//                    ((SMKDoc) object.source()).getId(),
//                    ((SMKDoc) object.source()).getName(),
//                    ((SMKDoc) object.source()).getCode(),
//                    ((SMKDoc) object.source()).getVersion(),
//                    ((SMKDoc) object.source()).getDate(),
//                    ((SMKDoc) object.source()).getContent(),
//                    ((SMKDoc) object.source()).getAppendix(),
//                    ((SMKDoc) object.source()).getLinks(),
//                    ((SMKDoc) object.source()).getApproval_sheet()
//            );
            documents.add(new DocumentDTO(
                    ((SMKDoc) object.source()).getId(),
                    ((SMKDoc) object.source()).getName(),
                    ((SMKDoc) object.source()).getCode(),
                    ((SMKDoc) object.source()).getVersion(),
                    ((SMKDoc) object.source()).getDate(),
                    ((SMKDoc) object.source()).getContent(),
                    ((SMKDoc) object.source()).getAppendix(),
                    ((SMKDoc) object.source()).getLinks(),
                    ((SMKDoc) object.source()).getApproval_sheet(),
                    object.highlight()));
            System.out.println(object.highlight());
        }

        return documents;
    }

}
