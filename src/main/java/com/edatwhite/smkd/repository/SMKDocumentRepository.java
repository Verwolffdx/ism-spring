package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.smkdocument.SMKDocument;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SMKDocumentRepository extends ElasticsearchRepository<SMKDocument, String> {

//    @Override
//    @Highlight(
//            fields = {
//                    @HighlightField(name = "code"),
//                    @HighlightField(name = "name"),
//                    @HighlightField(name = "content"),
//                    @HighlightField(name = "appendix"),
//            },
//            parameters = @HighlightParameters(
//                    preTags = "<strong>",
//                    postTags = "</strong>",
//                    fragmentSize = 500,
//                    numberOfFragments = 3
//            )
//    )
//    Hit<SMKDocument> findById1(String id);

    List<SMKDocument> findAll();

    @Query("{\n" +
                "\"match_all\": {}\n" +
            "}\n"
    )
    List<SMKDocument> findAllDocuments();

    @Query("{" +
                "\"multi_match\": {" +
                    "\"query\": \"?0\"," +
                    "\"fields\": [ \"code\", \"name\", \"content\",  \"appendix\"]" +
                "}" +
            "}")
    @Highlight(
            fields = {
                    @HighlightField(name = "code"),
                    @HighlightField(name = "name"),
                    @HighlightField(name = "content"),
                    @HighlightField(name = "appendix"),
            },
            parameters = @HighlightParameters(
            preTags = "<strong>",
            postTags = "</strong>",
            fragmentSize = 500,
            numberOfFragments = 3
        )
    )
    List<SearchHit<SMKDocument>> findDocument(String value);

}
