package guru.qa.homework;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadZipTest {

    private String PDF = "junit-user-guide-5.8.2.pdf";
    private String CSV = "research-and-development-survey-2016-2019-csv-notes.csv";
    private String XLS = "Vova.xlsx";

    @Test
    void readZipTest() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/files/files.zip");
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                if (entry.getName().contains("csv")) {
                    assertThat(entry.getName()).isEqualTo(CSV);
                    parseCsvTest(zipFile.getInputStream(entry));
                } else if (entry.getName().contains("xlsx")) {
                    assertThat(entry.getName()).isEqualTo(XLS);
                    parseXlsTest(zipFile.getInputStream(entry));
                } else if (entry.getName().contains("pdf")) {
                    assertThat(entry.getName()).isEqualTo(PDF);
                    parsePdfTest(zipFile.getInputStream(entry));
                }
            }
        }
    }

    void parseCsvTest(InputStream file) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file))) {
            List<String[]> content = reader.readAll();
            assertThat(content.get(0)).contains("Footnotes");
        }
    }

    void parseXlsTest(InputStream file) throws Exception {
        com.codeborne.xlstest.XLS xls = new XLS(file);
        assertThat(xls.excel.getSheetAt(0)
                .getRow(2)
                .getCell(2)
                .getStringCellValue()
                .contains("рюкзак походный"));
    }

    void parsePdfTest(InputStream file) throws Exception {
        com.codeborne.pdftest.PDF pdf = new PDF(file);
        assertThat(pdf.author).contains("Marc Philipp");
    }
}