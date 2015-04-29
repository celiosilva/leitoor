package br.com.delogic.leitoor.repository;

import java.io.InputStream;
import java.util.Map;

public interface DocumentosRepository {

    Map<Integer, String> dividirArquivo(String nomeArquivo);

    Map<Integer, String> dividirPdf(InputStream inputStream);

}
