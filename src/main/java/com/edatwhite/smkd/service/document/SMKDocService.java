package com.edatwhite.smkd.service.document;

import com.edatwhite.smkd.repository.SMKDocRepository;
import com.edatwhite.smkd.entity.smkdocument.SMKDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SMKDocService {
    @Autowired
    SMKDocRepository repository;

    private final String indexName = "nir_index";

    @Autowired
    public SMKDocService(SMKDocRepository repository) {
        this.repository = repository;
    }

    public void save(final SMKDoc smkDoc) {
        repository.save(smkDoc);
    }

//    public SMKDocument findById(final String id) {
//        return repository.findById(id).orElse(null);
//    }

    public Optional<SMKDoc> findById(final String id) {
        return repository.findById(id);
    }

    public List<SMKDoc> findAllDocuments() {
        return repository.findAllDocuments();
    }

    //New
    public List<SearchHit<SMKDoc>> findDocument(String value) {
        return repository.findDocumentNested(value);
    }

    public List<SearchHit<SMKDoc>> findDocumentQuery(String value) {
        return repository.findDocumentNested(value);
    }
}
