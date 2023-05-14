package com.edatwhite.smkd.controller;

import com.edatwhite.smkd.entity.Division;
import com.edatwhite.smkd.entity.DivisionDTO;
import com.edatwhite.smkd.entity.smkdocument.Content;
import com.edatwhite.smkd.entity.smkdocument.Parser;
import com.edatwhite.smkd.message.ResponseMessage;
import com.edatwhite.smkd.repository.DivisionRepository;
import com.edatwhite.smkd.service.document.SMKDocService;
import com.edatwhite.smkd.entity.smkdocument.SMKDoc;
import com.edatwhite.smkd.service.file.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/v2/smk")
public class SMKDocController {

    @Autowired
    FilesStorageService storageService;

    @Autowired
    DivisionRepository divisionRepository;

    private final SMKDocService service;

    @Autowired
    public SMKDocController(SMKDocService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void save(@RequestBody final SMKDoc smkDoc) {
        System.out.println(smkDoc.toString());
        try {
            service.save(smkDoc);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Optional<SMKDoc> findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public List<SMKDoc> findAllDocuments() {
        return service.findAllDocuments();
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER')")
    public List<SearchHit<SMKDoc>> findDocument(@RequestParam String value) {
        return service.findDocument(value);
    }

    @PostMapping("/parsefile")
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
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
