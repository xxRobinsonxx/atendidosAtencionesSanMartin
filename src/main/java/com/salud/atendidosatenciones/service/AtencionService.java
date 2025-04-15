package com.salud.atendidosatenciones.service;

import com.salud.atendidosatenciones.exception.ImportExcelException;
import com.salud.atendidosatenciones.model.Atencion;
import com.salud.atendidosatenciones.repository.AtencionRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AtencionService {

    private final AtencionRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(AtencionService.class);

    public AtencionService(AtencionRepository repository) {
        this.repository = repository;
    }

    // 1) Registrar (save)
    public Atencion registrarAtencion(Atencion atencion) {
        return repository.save(atencion);
    }

    // 2) Listar (findAll)
    public List<Atencion> listarAtenciones() {
        return repository.findAll();
    }

    // 3) Importar Excel
    public List<Atencion> importarExcel(MultipartFile file) {
        List<Atencion> listaAtenciones = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            // Asumimos la primera fila (0) como cabecera, los datos inician en la fila 1
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Atencion atencion = new Atencion();
                try {
                // Orden de columnas asumiendo tu dataset:
                // 0: anio, 1: mes, 2: dia, 3: categoria, 4: red, 5: microred,
                // 6: codigoUnico, 7: nombreEstablecimiento, 8: provincia,
                // 9: distrito, 10: edaReg, 11: tipoEda, 12: sexo,
                // 13: etnia, 14: tipoSegur, 15: ups, 16: atendidos,
                // 17: consulta, 18: fechaCierre


                    logger.debug("Procesando fila " + i + ": celda 0 = " + row.getCell(0).toString());
                    atencion.setAnio(getIntegerCellValue(row.getCell(0)));
                    logger.debug("Procesando fila " + i + ": celda 1 = " + row.getCell(1).toString());
                    atencion.setMes(getIntegerCellValue(row.getCell(1)));
                    logger.debug("Procesando fila " + i + ": celda 2 = " + row.getCell(1).toString());
                atencion.setDia(getIntegerCellValue(row.getCell(2)));
                    logger.debug("Procesando fila " + i + ": celda 3 = " + row.getCell(2).toString());
                atencion.setCategoria(getStringCellValue(row.getCell(3)));
                    logger.debug("Procesando fila " + i + ": celda 4 = " + row.getCell(3).toString());
                atencion.setRed(getStringCellValue(row.getCell(4)));
                    logger.debug("Procesando fila " + i + ": celda 5 = " + row.getCell(4).toString());
                atencion.setMicrored(getStringCellValue(row.getCell(5)));
                    logger.debug("Procesando fila " + i + ": celda 6 = " + row.getCell(5).toString());
                atencion.setCodigoUnico(getLongCellValue(row.getCell(6)));
                    logger.debug("Procesando fila " + i + ": celda 7 = " + row.getCell(7).toString());
                atencion.setNombreEstablecimiento(getStringCellValue(row.getCell(7)));
                    logger.debug("Procesando fila " + i + ": celda 8 = " + row.getCell(8).toString());
                atencion.setProvincia(getStringCellValue(row.getCell(8)));
                    logger.debug("Procesando fila " + i + ": celda 9 = " + row.getCell(9).toString());
                atencion.setDistrito(getStringCellValue(row.getCell(9)));
                    logger.debug("Procesando fila " + i + ": celda 10 = " + row.getCell(10).toString());
                atencion.setEdaReg(getIntegerCellValue(row.getCell(10)));
                    logger.debug("Procesando fila " + i + ": celda 11 = " + row.getCell(11).toString());
                atencion.setTipoEda(getStringCellValue(row.getCell(11)));
                    logger.debug("Procesando fila " + i + ": celda 12 = " + row.getCell(12).toString());
                atencion.setSexo(getStringCellValue(row.getCell(12)));
                    logger.debug("Procesando fila " + i + ": celda 13 = " + row.getCell(13).toString());
                atencion.setEtnia(getStringCellValue(row.getCell(13)));
                    logger.debug("Procesando fila " + i + ": celda 14 = " + row.getCell(14).toString());
                atencion.setTipoSegur(getStringCellValue(row.getCell(14)));
                    logger.debug("Procesando fila " + i + ": celda 15 = " + row.getCell(15).toString());
                atencion.setUps(getStringCellValue(row.getCell(15)));
                    logger.debug("Procesando fila " + i + ": celda 16 = " + row.getCell(16).toString());
                atencion.setAtendidos(getIntegerCellValue(row.getCell(16)));
                    logger.debug("Procesando fila " + i + ": celda 17 = " + row.getCell(17).toString());
                atencion.setConsulta(getIntegerCellValue(row.getCell(17)));
                    logger.debug("Procesando fila " + i + ": celda 18 = " + row.getCell(18).toString());
                atencion.setFechaCierre(getStringCellValue(row.getCell(18)));

                listaAtenciones.add(atencion);

                } catch (Exception e) {
                    // Si algo explota al parsear ESTE row, lanza excepción con info de la fila
                    String mensajeError = "Error en la fila " + (i + 1) + ": " + e.getMessage();
                    logger.error(mensajeError, e);
//                    throw new ImportExcelException(
//                            "Error en la fila " + (i + 1) +
//                                    ". Revisa la columna que corresponde. Mensaje: " + e.getMessage(), e
//                    );
                    throw new ImportExcelException(mensajeError, e);
                }
            }
        } catch (Exception e) {
            // Lanza excepción personalizada para tener un mensaje amigable
            throw new ImportExcelException("Error al procesar el archivo Excel. "
                    + "Verifique que el formato y los datos sean correctos.", e);
        }

        // Guarda en lote y retorna la lista importada
        return repository.saveAll(listaAtenciones);
    }

    // Métodos auxiliares para leer celdas:

    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // Convertimos a String (por si es un número que debe interpretarse como texto)
            return String.valueOf((long) cell.getNumericCellValue());
        }
        return null;
    }

    private Integer getIntegerCellValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

//    private Long getLongCellValue(Cell cell) {
//        if (cell == null) return null;
//        if (cell.getCellType() == CellType.NUMERIC) {
//            return (long) cell.getNumericCellValue();
//        } else if (cell.getCellType() == CellType.STRING) {
//            try {
//                return Long.parseLong(cell.getStringCellValue().trim());
//            } catch (NumberFormatException e) {
//                return null;
//            }
//        }
//        return null;
//    }

    private Long getLongCellValue(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                // Si es numérico y no tiene decimales, se puede convertir
                return (long) cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String value = cell.getStringCellValue().trim();
                return value.isEmpty() ? null : Long.valueOf(value);
            }
        } catch (NumberFormatException e) {
            // Si ocurre un error de conversión, retorna null para que la validación falle
            return null;
        }
        return null;
    }


}
