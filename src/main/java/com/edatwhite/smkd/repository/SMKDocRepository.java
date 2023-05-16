package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.smkdocument.SMKDoc;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SMKDocRepository extends ElasticsearchRepository<SMKDoc, String> {
    List<SMKDoc> findAll();

    @Query("{\n" +
                    "\"match_all\": {}\n" +
                "}\n"
            )
    List<SMKDoc> findAllDocuments();

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
    List<SearchHit<SMKDoc>> findDocument(String value);

    @Query("{" +
            "\"bool\": {" +
            "\"must\": [" +
            "{" +
            " \"nested\": {" +
            "\"path\": \"content\"," +
            "\"query\": {" +
            " \"bool\": {" +
            "\"must\": [" +
            "{" +
            "\"multi_match\": {" +
            " \"query\": \"?0\"," +
            "\"fields\": [" +
            "\"content.chapter_title\"," +
            "\"content.chapter\"" +
            "]" +
            "}" +
            "}" +
            "]" +
            "}" +
            "}" +
            "}" +
            "}," +
            "{" +
            "\"multi_match\": {" +
            "\"query\": \"?0\"," +
            "\"fields\": [" +
            "\"code\"," +
            "\"name\"," +
            "\"appendix\"" +
            "]" +
            "}" +
            "}" +
            "]" +
            "}" +
            "}")
    @Highlight(
            fields = {
                    @HighlightField(name = "code"),
                    @HighlightField(name = "name"),
                    @HighlightField(name = "content.chapter_title"),
                    @HighlightField(name = "content.chapter"),
                    @HighlightField(name = "appendix"),
            },
            parameters = @HighlightParameters(
                    preTags = "<strong>",
                    postTags = "</strong>",
                    fragmentSize = 500,
                    numberOfFragments = 3
            )
    )
    List<SearchHit<SMKDoc>> findDocumentNested(String value);

    @Query("{" +
            "\"bool\": {" +
            "\"must\": [" +
            "{" +
            " \"nested\": {" +
            "\"path\": \"content\"," +
            "\"query\": {" +
            " \"bool\": {" +
            "            \"filter\": [" +
            "                {" +
            "                    \"term\": {" +
            "                        \"_id\": \"?1\"" +
            "                    }" +
            "                }" +
            "            ]," +
            "\"must\": [" +
            "{" +
            "\"multi_match\": {" +
            " \"query\": \"?0\"," +
            "\"fields\": [" +
            "\"content.chapter_title\"," +
            "\"content.chapter\"" +
            "]" +
            "}" +
            "}" +
            "]" +
            "}" +
            "}" +
            "}" +
            "}," +
            "{" +
            "\"multi_match\": {" +
            "\"query\": \"?0\"," +
            "\"fields\": [" +
            "\"code\"," +
            "\"name\"," +
            "\"appendix\"" +
            "]" +
            "}" +
            "}" +
            "]" +
            "}" +
            "}")
    @Highlight(
            fields = {
                    @HighlightField(name = "code"),
                    @HighlightField(name = "name"),
                    @HighlightField(name = "content.chapter_title"),
                    @HighlightField(name = "content.chapter"),
                    @HighlightField(name = "appendix"),
            },
            parameters = @HighlightParameters(
                    preTags = "<strong>",
                    postTags = "</strong>",
                    fragmentSize = 500,
                    numberOfFragments = 3
            )
    )
    List<SearchHit<SMKDoc>> findByIdNested(String value, String id);
}
