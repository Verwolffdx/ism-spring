package com.edatwhite.smkd.service.document;

import com.edatwhite.smkd.repository.SMKDocumentRepository;
import com.edatwhite.smkd.entity.smkdocument.SMKDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SMKDocumentService {

    @Autowired
    SMKDocumentRepository repository;

    private final String indexName = "nir_index";

    @Autowired
    public SMKDocumentService(SMKDocumentRepository repository) {
        this.repository = repository;
    }

    public void save(final SMKDocument smkDocument) {
        repository.save(smkDocument);
    }

//    public SMKDocument findById(final String id) {
//        return repository.findById(id).orElse(null);
//    }

    public Optional<SMKDocument> findById(final String id) {
        return repository.findById(id);
    }

    public List<SMKDocument> findAllDocuments() {
        return repository.findAllDocuments();
    }

    //New
    public List<SearchHit<SMKDocument>> findDocument(String value) {
        return repository.findDocument(value);
    }

    //Old
//    public List<SearchHit<SMKDocument>> findDocument(String field, String value) {
//        return repository.findDocument(field, value);
//    }

//    public List<SMKDocument> searchAllDocuments() throws IOException {
//
//        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
//
//        System.out.println("!!! searchRequest.q()" + searchRequest.q());
//        System.out.println("!!! searchRequest.query()" + searchRequest.query());
//
//
//        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, SMKDocument.class);
//
//
//
//        List<Hit> hits = searchResponse.hits().hits();
//        System.out.println("METADATA " + searchResponse.hits().toString());
//        System.out.println("COUNT OF HITS " + searchResponse.hits().total());
//        List<SMKDocument> documents = new ArrayList<>();
//        for (Hit object : hits) {
//
//            System.out.print(((SMKDocument) object.source()));
//            System.out.println("object.index()" + object.index());
//
//
//            documents.add((SMKDocument) object.source());
//
//        }
//        return documents;
//    }
}
