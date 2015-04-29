package br.com.delogic.leitoor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;

import br.com.delogic.csa.manager.ContentManager;
import br.com.delogic.leitoor.repository.DocumentosRepository;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class TestePdfSplit extends SpringTeste {

    @Inject
    private DocumentosRepository documentoRepository;

    @Inject
    private ContentManager       contentManager;

    @Test
    public void testeSplitPdf() {

        Document document = new Document();

        try {

            InputStream inputStream = ctx.getResource("file:src/main/webapp/resources-teste/primeirofausto.pdf").getInputStream();
            assertNotNull(inputStream);

            PdfReader inputPDF = new PdfReader(inputStream);

            int totalPages = inputPDF.getNumberOfPages();
            assertTrue("Pages must be bigger than 0", totalPages > 0);

            String tempDir = System.getProperty("java.io.tmpdir") + File.separator + "paginas";
            System.out.println("Printing files to " + tempDir);
            new File(tempDir).mkdirs();

            for (int i = 1; i <= totalPages; i++) {
                FileOutputStream outputStream = new FileOutputStream(tempDir + File.separator + "primeirofausto" + i + ".pdf");

                PdfWriter writer = PdfWriter.getInstance(document, outputStream);

                document.open();
                PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
                                                               // data
                PdfImportedPage page;

                document.newPage();
                page = writer.getImportedPage(inputPDF, i);
                cb.addTemplate(page, 0, 0);

                outputStream.flush();
                document.close();
                outputStream.close();
            }

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    @Test
    public void testeDividirPdf() throws Exception {
        InputStream inputStream = ctx.getResource("file:src/main/webapp/resources-teste/primeirofausto.pdf").getInputStream();
        assertNotNull(inputStream);

        Map<Integer, String> paginas = documentoRepository.dividirPdf(inputStream);

        assertNotNull(paginas);
        assertEquals(34, paginas.size());
        for (Entry<Integer, String> entry : paginas.entrySet()) {
            // verificar que os arquivos existem
            assertNotNull(contentManager.getInpuStream(entry.getValue()));
        }

    }

    @Test
    @Ignore
    public void testeDividirPdfProcessoInterno() throws Exception {
        InputStream inputStream = ctx.getResource("file:src/main/webapp/resources-teste/primeirofausto.pdf").getInputStream();
        assertNotNull(inputStream);

        // Map<Integer, String> paginas =
        // documentoRepository.dividirPdf(inputStream);

        Document document = new Document();
        Map<Integer, String> paginasDoArquivo = new HashMap<Integer, String>();
        try {

            PdfReader inputPDF = new PdfReader(inputStream);

            int totalPaginas = inputPDF.getNumberOfPages();

            String tempDir = System.getProperty("java.io.tmpdir") + File.separator + "paginas";
            System.out.println("Printing files to " + tempDir);
            new File(tempDir).mkdirs();

            for (int nrPagina = 1; nrPagina <= totalPaginas; nrPagina++) {

                ByteArrayOutputStream outpuPagina = new ByteArrayOutputStream();
                // FileOutputStream outpuPagina = new FileOutputStream(tempDir +
                // File.separator + "primeirofausto" + nrPagina + ".pdf");

                PdfWriter writer = PdfWriter.getInstance(document, outpuPagina);

                document.open();
                PdfContentByte cb = writer.getDirectContent();

                document.newPage();
                PdfImportedPage page = writer.getImportedPage(inputPDF, nrPagina);
                cb.addTemplate(page, 0, 0);

                outpuPagina.flush();

                document.close();
                outpuPagina.close();

                ByteArrayInputStream inputPagina = new ByteArrayInputStream(outpuPagina.toByteArray());
                // apenas para obter a extensão pdf
                // String nomeArquivoPagina = contentManager.create(new
                // FileInputStream(tempDir + File.separator + "primeirofausto" +
                // nrPagina + ".pdf"), "arquivo.pdf");
                String nomeArquivoPagina = contentManager.create(inputPagina, "arquivo.pdf");

                paginasDoArquivo.put(nrPagina, nomeArquivoPagina);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
