package com.edatwhite.smkd.controller;

import com.edatwhite.smkd.service.document.SMKDocumentService;
import com.edatwhite.smkd.entity.smkdocument.SMKDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/smk")
public class SMKDocumentController {

    private final SMKDocumentService service;

    @Autowired
    public SMKDocumentController(SMKDocumentService service) {
        this.service = service;
    }

    @PostMapping
    public void save(@RequestBody final SMKDocument smkDocument) {
        service.save(smkDocument);
    }

    @GetMapping("/{id}")
    public Optional<SMKDocument> findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<SMKDocument> findAllDocuments() {
        return service.findAllDocuments();
    }

    @GetMapping("/find")
    public List<SearchHit<SMKDocument>> findDocument(@RequestParam String value) {
        return service.findDocument(value);
    }
}
