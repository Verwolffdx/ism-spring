package com.edatwhite.smkd.controller;

import com.edatwhite.smkd.entity.*;
import com.edatwhite.smkd.entity.smkdocument.Content;
import com.edatwhite.smkd.entity.smkdocument.Parser;
import com.edatwhite.smkd.entity.smkdocument.RelationalDocument;
import com.edatwhite.smkd.message.ResponseMessage;
import com.edatwhite.smkd.payload.request.DocumentIdRequest;
import com.edatwhite.smkd.payload.request.SearchRequest;
import com.edatwhite.smkd.payload.response.MessageResponse;
import com.edatwhite.smkd.repository.DivisionRepository;

import com.edatwhite.smkd.repository.RelationalDocumentRepository;
import com.edatwhite.smkd.repository.UserRepository;
import com.edatwhite.smkd.service.document.ESQuery;
import com.edatwhite.smkd.entity.smkdocument.SMKDoc;
import com.edatwhite.smkd.service.file.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v2/smk")
public class SMKDocController {

    @Autowired
    FilesStorageService storageService;

    @Autowired
    DivisionRepository divisionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationalDocumentRepository relationalDocumentRepository;

//    @Autowired
//    SMKDocRepository smkDocRepository;

    @Autowired
    private ESQuery esQuery;


//    private final SMKDocService service;

//    @Autowired
//    public SMKDocController(SMKDocService service) {
//        this.service = service;
//    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> save(@RequestBody final SMKDoc smkDoc) {
        System.out.println(smkDoc.toString());
        try {
            SMKDoc doc = new SMKDoc(
                    smkDoc.getName(),
                    smkDoc.getCode(),
                    smkDoc.getVersion(),
                    smkDoc.getDate(),
                    smkDoc.getContent(),
                    smkDoc.getAppendix(),
                    smkDoc.getLinks(),
                    smkDoc.getApproval_sheet()

            );
//            System.out.println("ID = " + doc.getId());
//            smkDocRepository.save(doc);
//            service.save(smkDoc);
            esQuery.createOrUpdateDocument(doc);
            relationalDocumentRepository.save(new RelationalDocument(doc.getId().toString(), smkDoc.getCode(), smkDoc.getName()));

            String message = "The document has been successfully created! ";
            String document_id = doc.getId();
            System.out.println(message);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, document_id));
        } catch (Exception e) {
            String message = "Error when trying to create document " + e.getMessage();
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/favorites/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Set<RelationalDocument> getFavorites(@PathVariable String user_id ) {
        System.out.println(user_id);
        Users user = userRepository.findById(Long.parseLong(user_id)).get();

        return user.getFavorites();
    }

    @DeleteMapping("/deleteDocument")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String id) throws IOException {
        String response =  esQuery.deleteDocumentById(id);
        relationalDocumentRepository.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public Optional<SMKDoc> findById(@PathVariable String id) {
//        return service.findById(id);
//    }

    @PostMapping("/document")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DocumentDTO findByIdWithValue(@RequestBody DocumentIdRequest documentIdRequest) throws IOException {
//        SearchHit<SMKDoc> ESDocument = null;

//        if (documentIdRequest.getValue() == null || documentIdRequest.getValue().isEmpty()) {
//            Optional<SMKDoc> ESDocument = smkDocRepository.findById(documentIdRequest.getDocument_id());
//            SMKDoc doc = (SMKDoc) ESDocument.get();
            SMKDoc doc = esQuery.getDocumentById(documentIdRequest.getDocument_id());
            DocumentDTO documentDTO = new DocumentDTO(
                    doc.getId().toString(),
                    doc.getName(),
                    doc.getCode(),
                    doc.getVersion(),
                    doc.getDate(),
                    doc.getContent(),
                    doc.getAppendix(),
                    doc.getLinks(),
                    doc.getApproval_sheet()
            );

            Users user = userRepository.findById(documentIdRequest.getUser_id()).get();
            if (user.getFavorites().stream().anyMatch(fav -> fav.getDocument_id().equals(doc.getId()))) {
                documentDTO.setFavorite(true);
            }

            return documentDTO;
//        } else {
//            SearchHit<SMKDoc> ESDocument = smkDocRepository.findByIdNested(documentIdRequest.getValue(), documentIdRequest.getDocument_id()).get(0);
//
//            SMKDoc doc = (SMKDoc) ESDocument.getContent();
//            DocumentDTO documentDTO = new DocumentDTO(
//                    doc.getId().toString(),
//                    doc.getName(),
//                    doc.getCode(),
//                    doc.getVersion(),
//                    doc.getDate(),
//                    doc.getContent(),
//                    doc.getAppendix(),
//                    doc.getLinks(),
//                    doc.getApproval_sheet(),
//                    ESDocument.getHighlightFields()
//            );
//
//            Users user = userRepository.findById(documentIdRequest.getUser_id()).get();
//            if (user.getFavorites().stream().anyMatch(fav -> fav.getDocument_id().equals(doc.getId()))) {
//                documentDTO.setFavorite(true);
//            }
//
//            return documentDTO;
//        }

    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<SMKDoc> findAllDocuments() throws IOException {
        return esQuery.searchAllDocuments();
    }

    @PostMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<DocumentDTO> findDocument(@RequestBody SearchRequest searchRequest) throws IOException {
//        return service.findDocument(value);
//        List<SearchHit<SMKDoc>> ESDocuments = smkDocRepository.findDocumentNested(searchRequest.getSearch_value());
        List<DocumentDTO> documents = esQuery.searchDocument(searchRequest.getSearch_value());

        Users user = userRepository.findById(searchRequest.getUser_id()).get();

//        List<DocumentDTO> documentsDTO = new ArrayList<>();

        for (DocumentDTO s : documents) {

            if (user.getFavorites().stream().anyMatch(fav -> fav.getDocument_id().equals(s.getId()))) {
                s.setFavorite(true);
            }


        }



        return documents;
    }

    @PostMapping("/addfavorites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> addToFavorites(@RequestBody final Favorites favorites) {
        try {
            Users user = userRepository.findById(favorites.getUser_id()).get();
            RelationalDocument document = relationalDocumentRepository.findById(favorites.getDocument_id()).get();
            user.addFavorite(document);
            userRepository.save(user);
            String message = "The document has been successfully added to favorites!";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            String message = "Error when trying to add to favorites " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/deletefavorites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteFromFavorites(@RequestBody final Favorites favorites) {
        try {
            Users user = userRepository.findById(favorites.getUser_id()).get();
            RelationalDocument document = relationalDocumentRepository.findById(favorites.getDocument_id()).get();
            user.deleteFavorite(document);
            userRepository.save(user);
            String message = "Document successfully removed from favorites!";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            String message = "Error when trying to remove from favorites " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/parsefile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Content> parseDocument(@RequestParam("file") MultipartFile file) {
        List<Content> content = new ArrayList<>();
        try {
            storageService.save(file);

            System.out.println("Filename " + file.getName());
            System.out.println("Origignal Filename " + file.getOriginalFilename());
//            System.out.println("getResource().getURI() " + file.getResource().getURI());
//            System.out.println("getResource().getURL() " + file.getResource().getURL());

            Parser parser = new Parser(file.getOriginalFilename());
            content = parser.parse();

            storageService.delete(file.getOriginalFilename());

//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            System.out.println("Exception: " + e);
        }
        return content;
    }


//    @RequestMapping(value = "/parsefile",
//            method = RequestMethod.POST,
//            headers = ("content-type=multipart/*"),
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PostMapping("/parsefile")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
//        String message = "";
//        try {
//            storageService.save(file);
//
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//    }



    @GetMapping("/divisions")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<DivisionDTO> getDivisions() {
        List<DivisionDTO> divisionsDTO = new ArrayList<>();
        List<Division> divisionList = divisionRepository.findAll();

//        for (Division d : divisionList) {
//            if (d.getParent_id() == null) {
//                divisionsDTO.add(new DivisionDTO(d.getDivision_id(), d.getDivision_name()));
//            } else {
//                if (divisionsDTO.stream().anyMatch(o -> d.getParent_id().equals(o.getDivision_id()))) {
//                    divisionsDTO.get(Math.toIntExact(d.getParent_id())).addChild(new DivisionDTO(d.getDivision_id(), d.getDivision_name()));
//                } else {
//                    findChild(d, divisionsDTO);
//                }
//            }
//        }

        for (Division d : divisionList)
            System.out.println(d.getDivision_id());

        order(divisionList, divisionsDTO);

        for (DivisionDTO d : divisionsDTO) {
            System.out.println(d.getDivision_id());
            for (DivisionDTO div : d.getChildren()) {
                System.out.println(div.getDivision_id());
                for (DivisionDTO div2 : div.getChildren())
                    System.out.println(div2.getDivision_id());
            }
        }

        return divisionsDTO;
    }

    private static void order(List<Division> divisions, List<DivisionDTO> divisionsJSON) {

        for (Division d : divisions) {
            recursiveOrder(divisions, divisionsJSON, d);
        }
    }

    private static void recursiveOrder(List<Division> divisions, List<DivisionDTO> divisionsJSON, Division d) {

        System.out.println("D " + d.getDivision_id() + " " + d.getParent_id());
        if (divisionsJSON.stream().anyMatch(div -> div.getDivision_id() == d.getParent_id())) {
            DivisionDTO division = divisionsJSON.stream()
                    .filter(div -> d.getParent_id() == div.getDivision_id())
                    .findAny().orElse(null);
            division.addChild(new DivisionDTO(d.getDivision_id(), d.getDivision_name(), new ArrayList<>()));

        } else if (d.getParent_id() == null || d.getParent_id().equals(null)) {
            System.out.println(d.getDivision_id() + " is parent");
            divisionsJSON.add(new DivisionDTO(d.getDivision_id(), d.getDivision_name(), new ArrayList<>()));
        } else {
            for (DivisionDTO divisionJSON : divisionsJSON) {

                recursiveOrder(divisions.subList(divisions.indexOf(d), divisions.size()), divisionJSON.getChildren(), d);
            }
        }
    }



//    private void findChild(Division division, List<DivisionDTO> divisionsDTO) {
//        for (DivisionDTO dto : divisionsDTO) {
//            if (dto.getDivision_id().equals(division.getParent_id())) {
//                dto.addChild(new DivisionDTO(division.getDivision_id(), division.getDivision_name()));
//
//                return;
//            }
//            else {
//
//            }
//
//
//
////            if (divisionsDTO.stream().anyMatch(o -> dto.getParent_id().equals(o.getDivision_id()))) {
////                divisionsDTO.get(Math.toIntExact(d.getParent_id())).addChild(new DivisionDTO(d.getDivision_id(), d.getDivision_name()));
////            } else {
////
////            }
//        }
//    }
}
