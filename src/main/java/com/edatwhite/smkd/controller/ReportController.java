package com.edatwhite.smkd.controller;

import com.edatwhite.smkd.entity.Division;
import com.edatwhite.smkd.entity.DivisionDTO;
import com.edatwhite.smkd.entity.FamiliarizationSheet;
import com.edatwhite.smkd.entity.reports.*;
import com.edatwhite.smkd.entity.smkdocument.RelationalDocument;
import com.edatwhite.smkd.repository.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/smk")
public class ReportController {

    @Autowired
    FamiliarizationSheetRepository familiarizationSheetRepository;

    @Autowired
    DocTypeRepository docTypeRepository;

    @Autowired
    DivisionRepository divisionRepository;

    @Autowired
    RelationalDocumentRepository relationalDocumentRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/familiarizationreport/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FamReportForDocument getFamiliarizationReportForDocuments(@PathVariable String id) {
        //TODO Доделать лист ознакомления по документу

        FamReportForDocument report = new FamReportForDocument(relationalDocumentRepository.findById(id).get().getDocument_code());
        Set<FamiliarizationSheet> familiarizationSheet = familiarizationSheetRepository.findByDocumentId(id);

        List<ReportItem> reportItems = new ArrayList<>();
        for (FamiliarizationSheet f : familiarizationSheet) {
            reportItems.add(new ReportItem(
                    divisionRepository.findById(f.getFamDivision()).get().getDivision_name(),
                    userRepository.findById(f.getUserId()).get().getFio(),
                    f.getViewed()
            ));
        }

        report.setReport(reportItems);

        return report;
    }

    @GetMapping("/familiarizationreport")
    @PreAuthorize("hasRole('ADMIN')")
    public FamiliarizationReport getFamiliarizationReport() {
        FamiliarizationReport report = new FamiliarizationReport();

        DocTypeForReport regulations = new DocTypeForReport("Положения общеуниверситетские");
        DocTypeForReport methodics = new DocTypeForReport("Инструкции");
        DocTypeForReport methrek = new DocTypeForReport("Методические рекомендации");
        DocTypeForReport standarts = new DocTypeForReport("Стандарты организации");

        //Получаем уникальные id всех документов в листе ознакомления
        Set<String> famDocTypeDocuments = familiarizationSheetRepository.getDistinctDocumentId();

        //Проходимся по массиву всех id документов
        for (String f : famDocTypeDocuments) {
            //Получением документ по id
            RelationalDocument doc = relationalDocumentRepository.findById(f).get();
            //Получаем список всех отделов
            List<Division> divisionList = divisionRepository.findAll();
            //Группируем документы по типам
            switch (doc.getDoctype().getSign()) {
                case "regulations":
                    //Иерархическая структура отделов с количество ознакомившихся/не ознакомившихся
                    List<DivisionForReport> divisionsDTO = new ArrayList<>();

                    Set<Division> divisionsToFam = new HashSet<>();
                    //Получаем и проходимся по массиву отделов (отсортированном по возрастанию),
                    // которым надо ознакомится с документом (id отдела)
                    for (int i : familiarizationSheetRepository.getDistinctFamDivisionByDocumentIdOrderByFamDivision(f)) {
                        //Получаем список отделов, которым надо ознакомиться
                        Division division = divisionRepository.findById((long) i).get();
                        divisionsToFam.add(division);
                        if (division.getParent_id() != null) {
                            Division parent = divisionRepository.findById((division.getParent_id())).get();
                            do {
                                divisionsToFam.add(parent);
                                parent = divisionRepository.findById((division.getParent_id())).get();
                            }
                            while (parent.getParent_id() != null);
                        }

                    }

                    List<Division> sortedDivisionRegulations = divisionsToFam.stream().sorted((Division d1, Division d2) -> d2.getDivision_name().compareTo(d1.getDivision_name())).collect(Collectors.toList());
                    //Переводим список в иерархическую структуру
                    order(f, sortedDivisionRegulations , divisionsDTO);

                    regulations.addDocumentForReport(new DocumentForReport(
                            doc.getDocument_code(),
                            familiarizationSheetRepository.countViewedByDocumentIdAndViewedTrue(doc.getDocument_id()),
                            familiarizationSheetRepository.countViewedByDocumentIdAndViewedFalse(doc.getDocument_id()),
                            divisionsDTO
                    ));
                    break;
                case "methodics":
                    //Иерархическая структура отделов с количество ознакомившихся/не ознакомившихся
                    List<DivisionForReport> divisionsDTOmethodics = new ArrayList<>();

                    Set<Division> divisionsToFamMethodics = new HashSet<>();
                    //Получаем и проходимся по массиву отделов (отсортированном по возрастанию),
                    // которым надо ознакомится с документом (id отдела)
                    for (int i : familiarizationSheetRepository.getDistinctFamDivisionByDocumentIdOrderByFamDivision(f)) {
                        //Получаем список отделов, которым надо ознакомиться
                        Division division = divisionRepository.findById((long) i).get();
                        divisionsToFamMethodics.add(division);
                        if (division.getParent_id() != null) {
                            Division parent = divisionRepository.findById((division.getParent_id())).get();
                            do {
                                divisionsToFamMethodics.add(parent);
                                parent = divisionRepository.findById((division.getParent_id())).get();
                            }
                            while (parent.getParent_id() != null);
                        }

                    }

                    List<Division> sortedDivisionMethodics = divisionsToFamMethodics.stream().sorted((Division d1, Division d2) -> d2.getDivision_name().compareTo(d1.getDivision_name())).collect(Collectors.toList());
                    //Переводим список в иерархическую структуру
                    order(f, sortedDivisionMethodics , divisionsDTOmethodics);

                    methodics.addDocumentForReport(new DocumentForReport(
                            doc.getDocument_code(),
                            familiarizationSheetRepository.countViewedByDocumentIdAndViewedTrue(doc.getDocument_id()),
                            familiarizationSheetRepository.countViewedByDocumentIdAndViewedFalse(doc.getDocument_id()),
                            divisionsDTOmethodics
                    ));
                    break;
                case "methrek":
                    //Иерархическая структура отделов с количество ознакомившихся/не ознакомившихся
                    List<DivisionForReport> divisionsDTOmethrek = new ArrayList<>();

                    Set<Division> divisionsToFamMethrek = new HashSet<>();
                    //Получаем и проходимся по массиву отделов (отсортированном по возрастанию),
                    // которым надо ознакомится с документом (id отдела)
                    for (int i : familiarizationSheetRepository.getDistinctFamDivisionByDocumentIdOrderByFamDivision(f)) {
                        //Получаем список отделов, которым надо ознакомиться
                        Division division = divisionRepository.findById((long) i).get();
                        divisionsToFamMethrek.add(division);
                        if (division.getParent_id() != null) {
                            Division parent = divisionRepository.findById((division.getParent_id())).get();
                            do {
                                divisionsToFamMethrek.add(parent);
                                parent = divisionRepository.findById((division.getParent_id())).get();
                            }
                            while (parent.getParent_id() != null);
                        }

                    }

                    List<Division> sortedDivisionMethrek = divisionsToFamMethrek.stream().sorted((Division d1, Division d2) -> d2.getDivision_name().compareTo(d1.getDivision_name())).collect(Collectors.toList());
                    //Переводим список в иерархическую структуру
                    order(f, sortedDivisionMethrek , divisionsDTOmethrek);

                    methrek.addDocumentForReport(new DocumentForReport(
                            doc.getDocument_code(),
                            familiarizationSheetRepository.countViewedByDocumentIdAndViewedTrue(doc.getDocument_id()),
                            familiarizationSheetRepository.countViewedByDocumentIdAndViewedFalse(doc.getDocument_id()),
                            divisionsDTOmethrek
                    ));
                    break;
                case "standarts":
                    //Иерархическая структура отделов с количество ознакомившихся/не ознакомившихся
                    List<DivisionForReport> divisionsDTOstandarts = new ArrayList<>();

                    Set<Division> divisionsToFamStandarts = new HashSet<>();
                    //Получаем и проходимся по массиву отделов (отсортированном по возрастанию),
                    // которым надо ознакомится с документом (id отдела)
                    for (int i : familiarizationSheetRepository.getDistinctFamDivisionByDocumentIdOrderByFamDivision(f)) {
                        //Получаем список отделов, которым надо ознакомиться
                        Division division = divisionRepository.findById((long) i).get();
                        divisionsToFamStandarts.add(division);
                        if (division.getParent_id() != null) {
                            Division parent = divisionRepository.findById((division.getParent_id())).get();
                            do {
                                divisionsToFamStandarts.add(parent);
                                parent = divisionRepository.findById((division.getParent_id())).get();
                            }
                            while (parent.getParent_id() != null);
                        }

                    }

                    List<Division> sortedDivisionStandarts = divisionsToFamStandarts.stream().sorted((Division d1, Division d2) -> d2.getDivision_name().compareTo(d1.getDivision_name())).collect(Collectors.toList());

                    //Переводим список в иерархическую структуру
                    order(f, sortedDivisionStandarts , divisionsDTOstandarts);

                    standarts.addDocumentForReport(new DocumentForReport(
                            doc.getDocument_code(),
                            familiarizationSheetRepository.countViewedByDocumentIdAndViewedTrue(doc.getDocument_id()),
                            familiarizationSheetRepository.countViewedByDocumentIdAndViewedFalse(doc.getDocument_id()),
                            divisionsDTOstandarts
                    ));
                    break;
            }
        }


        report.addDocTypeForReport(regulations);
        report.addDocTypeForReport(methodics);
        report.addDocTypeForReport(methrek);
        report.addDocTypeForReport(standarts);


        return report;
    }

    private void remove(List<DivisionForReport> divisionsJSON, List<Division> divisionsToRemove) {
        for (DivisionForReport d : divisionsJSON) {
            recursiveRemove(d, divisionsToRemove, divisionsJSON);
        }

    }

    private void recursiveRemove(DivisionForReport division, List<Division> divisionsToRemove, List<DivisionForReport> divisionsJSON) {
        for (Division divToRemove : divisionsToRemove) {
            if (division.getChildDivisions().size() > 0) {
                recursiveRemove(division, divisionsToRemove, division.getChildDivisions());
                if (division.getChildDivisions().stream().anyMatch(div -> div.getDivisionId() == divToRemove.getDivision_id())) {
                    divisionsJSON.remove(division);
                }
            }
            else if (division.getChildDivisions().size() == 0 && division.getChildDivisions().stream().anyMatch(div -> div.getDivisionId().equals(division.getDivisionId()))) {
                divisionsJSON.remove(division);
            }
        }
    }

    private void order(String documentId, List<Division> divisions, List<DivisionForReport> divisionsJSON) {
        for (Division d : divisions) {
            recursiveOrder(documentId, divisions, divisionsJSON, d);
        }
    }

    private void recursiveOrder(String documentId, List<Division> divisions, List<DivisionForReport> divisionsJSON, Division d) {
        if (divisionsJSON.stream().anyMatch(div -> div.getDivisionId() == d.getParent_id())) {
            DivisionForReport division = divisionsJSON.stream().filter(div -> d.getParent_id() == div.getDivisionId()).findAny().orElse(null);
            division.addChildDivisions(new DivisionForReport(
                    d.getDivision_id(),
                    d.getDivision_name(),
                    familiarizationSheetRepository.countViewedByDocumentIdAndFamDivisionAndViewedTrue(documentId,d.getDivision_id()),
                    familiarizationSheetRepository.countViewedByDocumentIdAndFamDivisionAndViewedFalse(documentId,d.getDivision_id()),
                    new ArrayList<>())
            );
        } else if (d.getParent_id() == null || d.getParent_id().equals(null)) {
            divisionsJSON.add(new DivisionForReport(
                    d.getDivision_id(),
                    d.getDivision_name(),
                    familiarizationSheetRepository.countViewedByDocumentIdAndFamDivisionAndViewedTrue(documentId,d.getDivision_id()),
                    familiarizationSheetRepository.countViewedByDocumentIdAndFamDivisionAndViewedFalse(documentId,d.getDivision_id()),
                    new ArrayList<>())
            );
        } else {
            for (DivisionForReport divisionJSON : divisionsJSON) {
                recursiveOrder(documentId, divisions.subList(divisions.indexOf(d), divisions.size()), divisionJSON.getChildDivisions(), d);
            }
        }
    }
}
