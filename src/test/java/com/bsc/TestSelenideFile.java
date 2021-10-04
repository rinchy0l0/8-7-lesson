package com.bsc;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.model.FileHeader;
import org.junit.jupiter.api.Test;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import net.lingala.zip4j.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSelenideFile {

    @Test
    void assertionTxt() throws Exception {
        String result;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("FileTxt.txt")) {
            result = new String(is.readAllBytes(), "UTF-8");
            assertThat(result).contains("He won a lottery ticket");
        }
    }

    @Test
    void assertionPdf() throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("Baranovskaya.pdf")) {
            PDF parsed = new PDF(stream);
            assertThat(parsed.numberOfPages > 310);
            assertThat(parsed.text).contains("They They");
        }
    }

    @Test
    void assertionXls() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("2_kurs.xls")) {
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue())
                    .isEqualTo("Hello!");
        }
    }

    @Test
    void assertionZip() throws Exception {
        ZipFile zipFile = new ZipFile("./src/test/resources/Course.zip");

        if (zipFile.isEncrypted())
            zipFile.setPassword("123".toCharArray());

        zipFile.extractAll("./src/test/resources/");

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("Text1.txt")) {
            String result = new String(in.readAllBytes(), "UTF-8");
            assertThat(result).contains("He");
        }
    }
}