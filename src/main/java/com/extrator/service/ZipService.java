package com.extrator.util;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipService {

    /**
     * Cria um arquivo ZIP contendo todos os arquivos informados.
     *
     * @param arquivos   lista de caminhos absolutos dos arquivos a serem compactados
     * @param destinoZip caminho do arquivo ZIP de saída
     * @return caminho do arquivo ZIP gerado
     * @throws IOException caso ocorra erro na escrita do ZIP
     */
    public static String criarZip(List<String> arquivos, String destinoZip) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destinoZip))) {
            for (String arquivo : arquivos) {
                File file = new File(arquivo);
                if (!file.exists()) continue;

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                }
            }
        }
        return destinoZip;
    }
}