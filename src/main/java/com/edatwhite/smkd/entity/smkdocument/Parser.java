package com.edatwhite.smkd.entity.smkdocument;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Range bodyRange;
    private WordExtractor wordExtractor;

    private String filename;

    public Parser(String filename) {
        this.filename = filename;
    }

    public List<Content> parse() throws IOException {
        /*
        TODO Чтение файла
         */

        HWPFDocument document = readDocument("uploads/" + this.filename);

        this.bodyRange = document.getRange();
        this.wordExtractor = new WordExtractor(document);


        /*
        TODO Проверка на отсутствие титульного листа и оглавления
         */

        /*
        TODO Парсинг глав (название и содержание) + Удаление пустых строк и лишних символов
         */

        List<Content> content = readChapters();

        //Вывод спарсенных глав
//        System.out.println("\n-------------------------------");
//        for(Chapter ch : chapters) {
//            System.out.println(ch.getTitle());
//            for (String s : ch.getContent())
//                System.out.println(s);
//            System.out.println("-------------------------------");
//        }

        /*
        TODO Парсинг приложений
         */

//        //Запись в файл
//        try (PrintWriter out = new PrintWriter("parsed.txt")) {
//            for(Content c : content) {
//                out.println(c.getChapter_title());
//                for (String s : c.getChapter())
//                    out.println(s);
//                out.println("-------------------------------");
//            }
////            out.println(docText);
//            System.out.println("Writing complete");
//        }

        return content;
    }

    private HWPFDocument readDocument(String filename) throws IOException {
        File f = new File(filename);
        HWPFDocument document = new HWPFDocument(new FileInputStream(f));

        return document;
    }

    private List<Content> readChapters() {
        List<Content> content = new ArrayList<>();
        String[] getParagraphText = this.wordExtractor.getParagraphText();

        int i = 0;
        Paragraph paragraph = null;
        String title;
        Content ch = null;

        while (i < getParagraphText.length) {
            paragraph = this.bodyRange.getParagraph(i);

            Pattern pattern = Pattern.compile("(^(Приложение [А-Я]))|(ПРИЛОЖЕНИЕ [А-Я])");
            Matcher matcher = pattern.matcher(paragraph.text());
            if (matcher.find()){
                break;
            }

            String s = "";
            if (!paragraph.text().equals("\r")
                    && !paragraph.text().equals("\u0007")
                    && !paragraph.text().equals("\t")
                    && !paragraph.text().equals("\f") ) {
                s = paragraph.text();
                if (paragraph.text().contains("\r"))
                    s = paragraph.text().replace("\r", "");
                if (paragraph.text().contains("\u0007"))
                    s = paragraph.text().replace("\u0007", "");
                if (paragraph.text().contains("\f"))
                    s = paragraph.text().replace("\f", "");
            }
            if (isChapterTitle(paragraph)) {
                title = s;
                ch = new Content(title, new ArrayList<>());
                content.add(ch);
            } else {
                if (!isChapterTitle(paragraph) && !paragraph.text().equals("\r")) {
                    if (ch != null)
                        ch.addChapter(s);
                    else {
                        System.out.println(s);
                        ch = new Content("Название главы отсутсвует", new ArrayList<>());
                        ch.addChapter(s);
                        content.add(ch);
                    }
                }
            }
            i++;
        }

//        for (int i = 0; i < getParagraphText.length; i++) {
//            Paragraph paragraph = bodyRange.getParagraph(i);
//
//            String s = "";
//            if (!paragraph.text().equals("\r")
//                    && !paragraph.text().equals("\u0007")
//                    && !paragraph.text().equals("\t")
//                    && !paragraph.text().equals("\f") ) {
//                s = paragraph.text();
//                if (paragraph.text().contains("\r"))
//                    s = paragraph.text().replace("\r", "");
//                if (paragraph.text().contains("\u0007"))
//                    s = paragraph.text().replace("\u0007", "");
//                if (paragraph.text().contains("\f"))
//                    s = paragraph.text().replace("\f", "");
//
//                System.out.println(s);
//            }
//        }

        return content;
    }

    private static boolean isChapterTitle(Paragraph paragraph) {

//        if (paragraph.isInList()) {
//            System.out.println("inList " + paragraph.text());
//            return true;
//        }

        String tmpTitle = "";
        Pattern tmpTitlePattern = Pattern.compile("(([0-9]\\.){0,1}[ ]{0,1}([А-Яa-я]+)+)+");
        Matcher tmpTitleMatcher;
        int j = 0;
        while (true) {
            CharacterRun run = paragraph.getCharacterRun(j++);

             if(run.isBold()) {
                 tmpTitle += run.text();
                 tmpTitleMatcher = tmpTitlePattern.matcher(tmpTitle);
                 if (tmpTitleMatcher.find() && paragraph.text().startsWith(tmpTitle)) {
                     return true;
                 }
             } else {
                 tmpTitle = "";
             }

            if (run.getEndOffset() == paragraph.getEndOffset()) {
                break;
            }
        }

        return false;
    }
}
