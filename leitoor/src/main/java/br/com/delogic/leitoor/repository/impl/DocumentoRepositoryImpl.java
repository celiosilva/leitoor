package br.com.delogic.leitoor.repository.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.delogic.csa.manager.ContentManager;
import br.com.delogic.leitoor.repository.DocumentosRepository;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

@Named("documentoRepository")
public class DocumentoRepositoryImpl implements DocumentosRepository {

    @Inject
    private ContentManager contentManager;

    @Override
    public Map<Integer, String> dividirArquivo(String nomeArquivo) {
        try {
            return dividirPdf(contentManager.getInpuStream(nomeArquivo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, String> dividirPdf(InputStream inputStream) {
        Document document = new Document();
        Map<Integer, String> paginasDoArquivo = new HashMap<Integer, String>();
        try {

            PdfReader inputPDF = new PdfReader(inputStream);

            int totalPaginas = inputPDF.getNumberOfPages();

            for (int nrPagina = 1; nrPagina <= totalPaginas; nrPagina++) {

                ByteArrayOutputStream outpuPagina = new ByteArrayOutputStream();

                PdfWriter writer = PdfWriter.getInstance(document, outpuPagina);
                Rectangle pageSize = inputPDF.getPageSize(nrPagina);
                document.setPageSize(pageSize);

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

        return paginasDoArquivo;
    }

}
