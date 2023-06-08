package com.edatwhite.smkd.controller;

import com.edatwhite.smkd.entity.*;
import com.edatwhite.smkd.entity.smkdocument.*;
import com.edatwhite.smkd.message.ResponseMessage;
import com.edatwhite.smkd.payload.request.DocumentIdRequest;
import com.edatwhite.smkd.payload.request.SearchRequest;
import com.edatwhite.smkd.repository.*;

import com.edatwhite.smkd.service.document.ESQuery;
import com.edatwhite.smkd.service.file.FilesStorageService;
import com.ibm.icu.text.Transliterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    FamiliarizationSheetRepository familiarizationSheetRepository;

    @Autowired
    TemplatesRepository templatesRepository;

    @Autowired
    DocTypeRepository docTypeRepository;

    @Autowired
    private ESQuery esQuery;

    //Тест поиск пользователей по отделам
    @GetMapping("/getUsersByDivisions/{division_id}")
    public Set<Users> getUsersByDivisions(@RequestBody final @PathVariable String division_id) {

        return userRepository.findByDivisions(divisionRepository.findById(Long.parseLong(division_id)).get());
    }

    //Тест загрузки файлов документов
    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    public String executeSampleService(@RequestPart("string") DocumentWrapper string, @RequestPart("file") MultipartFile file, @RequestPart("templates") MultipartFile[] templates) {

        try {
            System.out.println(string.getName());
            System.out.println(file.getOriginalFilename());

            Arrays.asList(templates).stream().forEach(template -> {
                System.out.println(template.getOriginalFilename());
            });
            return "Good";
        } catch (Exception e) {
            return e.getMessage().toString();
        }
    }

    @PostMapping(value = "/create", produces = "application/json", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> save(@RequestPart("document") DocumentWithDivisionsDTO document, @RequestPart("file") MultipartFile file, @RequestPart("templates") MultipartFile[] templates) {
        try {
            SMKDoc doc = new SMKDoc(
                    document.getDocument().getName(),
                    document.getDocument().getCode(),
                    document.getDocument().getVersion(),
                    document.getDocument().getDate(),
                    document.getDocument().getContent(),
                    document.getDocument().getAppendix(),
                    document.getDocument().getLinks(),
                    document.getDocument().getApproval_sheet()
            );

            esQuery.createOrUpdateDocument(doc);


            storageService.save(file);
//            System.out.println("Filename " + file.getName());
            System.out.println("Origignal Filename " + file.getOriginalFilename());

            Long doctype;

            switch (document.getDoctype()) {
                case "standarts":
                    doctype = 1L;
                    break;
                case "regulations":
                    doctype = 2L;
                    break;
                case "methodics":
                    doctype = 3L;
                    break;
                case "methrek":
                    doctype = 4L;
                    break;
                default:
                    doctype = 5L;
            }

            relationalDocumentRepository.save(
                    new RelationalDocument(
                            doc.getId().toString(),
                            document.getDocument().getCode(),
                            document.getDocument().getName(),
                            file.getOriginalFilename(),
                            docTypeRepository.findById(doctype).get()
                    )
            );

            List<MultipartFile> templateList = Arrays.asList(templates).stream().collect(Collectors.toList());

//            for (int i = 0; i < templateList.size(); i++) {
//                storageService.save(templateList.get(i));
//                System.out.println("Template Filename " + templateList.get(i).getName());
//                System.out.println("Origignal Template Filename " + templateList.get(i).getOriginalFilename());
//
//                templatesRepository.save(new Templates(
//                        doc.getId(),
//                        doc.getAppendix().get(i),
//                        templateList.get(i).getOriginalFilename()
//                ));
//            }

            for (int i = 0; i < doc.getAppendix().size(); i++) {
                if (templateList.size() > i) {
                    storageService.save(templateList.get(i));
                    System.out.println("Template Filename " + templateList.get(i).getName());
                    System.out.println("Origignal Template Filename " + templateList.get(i).getOriginalFilename());

                    templatesRepository.save(new Templates(
                            doc.getId(),
                            doc.getAppendix().get(i),
                            templateList.get(i).getOriginalFilename()
                    ));
                } else {
                    templatesRepository.save(new Templates(
                            doc.getId(),
                            doc.getAppendix().get(i),
                            "/"
                    ));
                }
            }

//            Set<Users> usersByDivision = new HashSet<>();
            for (long division_id : document.getDivisions()) {
                Set<Users> usersByDivision = new HashSet<>();
                for (Users user : userRepository.findByDivisions(divisionRepository.findById(division_id).get()))
                    usersByDivision.add(user);
                for (Users user : usersByDivision) {
                    familiarizationSheetRepository.save(new FamiliarizationSheet(user.getUser_id(), doc.getId(), false, division_id));
                }
            }

//            for (Users user : usersByDivision) {
//                familiarizationSheetRepository.save(new FamiliarizationSheet(user.getUser_id(), doc.getId(), false));
//            }

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

    @PostMapping(value = "/update", produces = "application/json", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> update(@RequestPart("document") DocumentWithDivisionsDTO document, @RequestPart("file") MultipartFile file) {
        try {
            System.out.println(document.getDocument().getId());
            SMKDoc doc = document.getDocument();

            esQuery.createOrUpdateDocument(doc);

            RelationalDocument relationalDocument = relationalDocumentRepository.findById(doc.getId()).get();
            storageService.delete(relationalDocument.getDocument_path());

            storageService.save(file);
            System.out.println("Filename " + file.getName());
            System.out.println("Origignal Filename " + file.getOriginalFilename());

            relationalDocument.setDocument_code(doc.getCode());
            relationalDocument.setDocument_name(doc.getName());
            relationalDocument.setDocument_path(file.getOriginalFilename());
            relationalDocumentRepository.save(relationalDocument);


            Set<Users> usersByDivision = new HashSet<>();
            for (long division_id : document.getDivisions()) {
                for (Users user : userRepository.findByDivisions(divisionRepository.findById(division_id).get()))
                    usersByDivision.add(user);
            }

            for (Users user : usersByDivision) {
                familiarizationSheetRepository.save(new FamiliarizationSheet(user.getUser_id(), doc.getId(), false));
            }

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

    @GetMapping("/document/{id}/divisions")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Set<Long> getDivisionsForDocument(@PathVariable String id) {
        Set<Long> divisionsForDocument = new HashSet<>();
        for (FamiliarizationSheet fam : familiarizationSheetRepository.findFamDivisionByDocumentId(id)) {
            divisionsForDocument.add(fam.getFamDivision());
        }
        return divisionsForDocument;
    }


    @GetMapping("/file/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Resource> getFile(@PathVariable String id) {
        RelationalDocument document = relationalDocumentRepository.findById(id).get();
        Resource file = storageService.load(document.getDocument_path());
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        String filename = toLatinTrans.transliterate(file.getFilename());
        return ResponseEntity.ok().header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition").header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
    }

    @GetMapping("/template/{name}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Resource> getTemplate(@PathVariable String name) {
        Templates template = templatesRepository.findByTemplateName(name).get();
        if (template.getTemplatePath().equals("/")) {


            return ResponseEntity.noContent().build();
        } else {
            Resource file = storageService.load(template.getTemplatePath());
            Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
            String filename = toLatinTrans.transliterate(file.getFilename());
            return ResponseEntity.ok().header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition").header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
        }
    }

    @GetMapping("/familiarizationForUser/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Set<FamiliarizationForUserDTO> getFamiliarizationSheetForUser(@PathVariable String user_id) {
        Set<FamiliarizationForUserDTO> familiarizationSheetForUser = new HashSet<>();

        Set<FamiliarizationSheet> familiarizationSheets = familiarizationSheetRepository.findByUserIdAndViewedFalse(Long.parseLong(user_id));

        for (FamiliarizationSheet fam : familiarizationSheets) {
            familiarizationSheetForUser.add(new FamiliarizationForUserDTO(fam.getFam_id(), fam.getDocumentId(), relationalDocumentRepository.findById(fam.getDocumentId()).get().getDocument_code(), relationalDocumentRepository.findById(fam.getDocumentId()).get().getDocument_name()));
        }


        return familiarizationSheetForUser;
    }

    @PutMapping("/confirmFamiliarization")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> confirmFamiliarization(@RequestParam Long user_id, @RequestParam String document_id) {
        try {
            FamiliarizationSheet famSheet = familiarizationSheetRepository.findByUserIdAndDocumentId(user_id, document_id);
            famSheet.setViewed(true);
            familiarizationSheetRepository.save(famSheet);

            String message = "The user with id " + user_id + " has successfully familiarization with document " + document_id + " !";
            System.out.println(message);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            String message = "Error when trying to create document " + e.getMessage();
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/favorites/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Set<RelationalDocument> getFavorites(@PathVariable String user_id) {
        System.out.println(user_id);
        Users user = userRepository.findById(Long.parseLong(user_id)).get();

        return user.getFavorites();
    }

    @DeleteMapping("/deleteDocument")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String id) throws IOException {


        familiarizationSheetRepository.deleteDocumentIdByDocumentId(id);
        storageService.delete(relationalDocumentRepository.findById(id).get().getDocument_path());

        for (Templates t : templatesRepository.findByDocumentId(id)) {
            if (!t.getTemplatePath().equals("/"))
                storageService.delete(t.getTemplatePath());
            t.getFavoritesTemplate().clear();
            templatesRepository.save(t);
            for (Users user : t.getFavoritesTemplate()) {
                user.deleteFavoriteTemplate(t);
                userRepository.save(user);
            }

        }

        templatesRepository.deleteAll(templatesRepository.findByDocumentId(id));

        RelationalDocument document = relationalDocumentRepository.findById(id).get();


        document.getFavorites().clear();

        relationalDocumentRepository.save(document);
        for (Users user : document.getFavorites()) {
            user.deleteFavorite(document);
            userRepository.save(user);
        }

//        Set<Users> users = userRepository.findUsersByFavorites(id);
//        for (Users user : users) {
//            user.deleteFavorite(document);
//            for (Templates templates : user.getFavoritesTemplate()) {
//                if (templates.getDocumentId().equals(document.getDocument_id())) {
//                    user.deleteFavoriteTemplate(templates);
//                }
//            }
//        }

//        System.out.println("FAVS " + relationalDocumentRepository.findByFavoritesDocumentId(id));


        relationalDocumentRepository.deleteById(id);
        String response = esQuery.deleteDocumentById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/document")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DocumentDTO findByIdWithValue(@RequestBody DocumentIdRequest documentIdRequest) throws IOException {


        if (documentIdRequest.getValue() == null || documentIdRequest.getValue().isEmpty()) {

            SMKDoc doc = esQuery.getDocumentById(documentIdRequest.getDocument_id());
            DocumentDTO documentDTO = new DocumentDTO(doc.getId().toString(), doc.getName(), doc.getCode(), doc.getVersion(), doc.getDate(), doc.getContent(), doc.getAppendix(), doc.getLinks(), doc.getApproval_sheet());

            Users user = userRepository.findById(documentIdRequest.getUser_id()).get();
            if (user.getFavorites().stream().anyMatch(fav -> fav.getDocument_id().equals(doc.getId()))) {
                documentDTO.setFavorite(true);
            }

            if (familiarizationSheetRepository.existsFamiliarizationSheetByUserIdAndDocumentId(documentIdRequest.getUser_id(), documentIdRequest.getDocument_id()))
                documentDTO.setFamiliarize(familiarizationSheetRepository.findByUserIdAndDocumentId(documentIdRequest.getUser_id(), documentIdRequest.getDocument_id()).getViewed());

            return documentDTO;
        } else {

            DocumentDTO documentDTO = esQuery.searchDocumentWithId(documentIdRequest.getValue(), documentIdRequest.getDocument_id()).get(0);

            Users user = userRepository.findById(documentIdRequest.getUser_id()).get();
            if (user.getFavorites().stream().anyMatch(fav -> fav.getDocument_id().equals(documentDTO.getId()))) {
                documentDTO.setFavorite(true);
            }

            if (familiarizationSheetRepository.existsFamiliarizationSheetByUserIdAndDocumentId(documentIdRequest.getUser_id(), documentIdRequest.getDocument_id()))
                documentDTO.setFamiliarize(familiarizationSheetRepository.findByUserIdAndDocumentId(documentIdRequest.getUser_id(), documentIdRequest.getDocument_id()).getViewed());

            return documentDTO;
        }

    }

    @GetMapping("/documents/{doctype}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<RelationalDocument> getDocumentsByType(@PathVariable String doctype) {
        if (doctype.equals("all")) {
            return relationalDocumentRepository.findAll();
        } else {
            return relationalDocumentRepository.findByDoctypeSign(doctype);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<SMKDoc> findAllDocuments() throws IOException {
        return esQuery.searchAllDocuments();
    }

    @PostMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<DocumentDTO> findDocument(@RequestBody SearchRequest searchRequest) throws IOException {
        List<DocumentDTO> documents = esQuery.searchDocument(searchRequest.getSearch_value());

        Users user = userRepository.findById(searchRequest.getUser_id()).get();

        for (DocumentDTO s : documents) {
            if (user.getFavorites().stream().anyMatch(fav -> fav.getDocument_id().equals(s.getId()))) {
                s.setFavorite(true);
            }
        }

        return documents;
    }

    @PostMapping("/findtemplates")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<TemplateDTO> findTemplates(@RequestBody SearchRequest searchRequest) throws IOException {
        List<TemplateDTO> templates = esQuery.searchTemplates(searchRequest.getSearch_value());

        Users user = userRepository.findById(searchRequest.getUser_id()).get();


        for (TemplateDTO t : templates) {

            for (String s : t.getAppendix()) {
                System.out.println("TMP " + s);

                Templates tmp = templatesRepository.findByTemplateName(s).get();
                if (user.getFavoritesTemplate().stream().anyMatch(fav -> fav.getTemplateName().equals(tmp))) {
                    t.setFavorite(true);
                }
            }

        }

        return templates;
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

    @PostMapping("/addfavoritestemplate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> addTemplateToFavorites(@RequestBody final FavoritesTemplate favorites) {
        try {
            Users user = userRepository.findById(favorites.getUserId()).get();
            Templates template = templatesRepository.findById(favorites.getTemplateId()).get();
            user.addFavoriteTemplate(template);
            userRepository.save(user);
            String message = "The template has been successfully added to favorites!";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            String message = "Error when trying to add template to favorites " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/deletefavoritestemplate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteTemplateFromFavorites(@RequestBody final FavoritesTemplate favorites) {
        try {
            Users user = userRepository.findById(favorites.getUserId()).get();
            Templates template = templatesRepository.findById(favorites.getTemplateId()).get();
            user.deleteFavoriteTemplate(template);
            userRepository.save(user);
            String message = "Template successfully removed from favorites!";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            String message = "Error when trying to remove template from favorites " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/parsefile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Content> parseDocument(@RequestParam("file") MultipartFile file) {
        List<Content> content = new ArrayList<>();
        try {
            storageService.save(file);

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

    @GetMapping("/divisions")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<DivisionDTO> getDivisions() {
        List<DivisionDTO> divisionsDTO = new ArrayList<>();
        List<Division> divisionList = divisionRepository.findAll();

        order(divisionList, divisionsDTO);

//        for (DivisionDTO d : divisionsDTO) {
//            System.out.println(d.getDivision_id());
//            for (DivisionDTO div : d.getChildren()) {
//                System.out.println(div.getDivision_id());
//                for (DivisionDTO div2 : div.getChildren())
//                    System.out.println(div2.getDivision_id());
//            }
//        }

        return divisionsDTO;
    }

    private static void order(List<Division> divisions, List<DivisionDTO> divisionsJSON) {
        for (Division d : divisions) {
            recursiveOrder(divisions, divisionsJSON, d);
        }
    }

    private static void recursiveOrder(List<Division> divisions, List<DivisionDTO> divisionsJSON, Division d) {

        if (divisionsJSON.stream().anyMatch(div -> div.getDivision_id() == d.getParent_id())) {
            DivisionDTO division = divisionsJSON.stream().filter(div -> d.getParent_id() == div.getDivision_id()).findAny().orElse(null);
            division.addChild(new DivisionDTO(d.getDivision_id(), d.getDivision_name(), new ArrayList<>()));
        } else if (d.getParent_id() == null || d.getParent_id().equals(null)) {
            divisionsJSON.add(new DivisionDTO(d.getDivision_id(), d.getDivision_name(), new ArrayList<>()));
        } else {
            for (DivisionDTO divisionJSON : divisionsJSON) {
                recursiveOrder(divisions.subList(divisions.indexOf(d), divisions.size()), divisionJSON.getChildren(), d);
            }
        }
    }
}
