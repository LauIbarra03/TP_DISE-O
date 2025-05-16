package model.entities.GeneradorInformes;

import java.io.IOException;
import model.entities.GeneradorReportes.Reporte;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GeneradorPDF {

  public void generarReportePDF(Reporte reporte, String rutaArchivo) throws IOException {
    PDDocument document = new PDDocument();
    PDPage page = new PDPage(PDRectangle.A4);
    document.addPage(page);

    PDPageContentStream contentStream = new PDPageContentStream(document, page);
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

    float margin = 50; // Margen izquierdo
    float yPosition = page.getMediaBox().getHeight() - 50; // Posición inicial del texto

    String contenido = reporte.generarContenido();
    String[] lineas = contenido.split("\n");

    for (String linea : lineas) {
      if (yPosition < 50) {
        // Cambio de página si la posición actual excede el margen inferior
        contentStream.beginText();
        contentStream.endText();
        contentStream.close();
        document.addPage(new PDPage(PDRectangle.A4));
        contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        yPosition = page.getMediaBox().getHeight() - 50;
      }
      contentStream.beginText();
      contentStream.newLineAtOffset(margin, yPosition);
      contentStream.showText(linea);
      contentStream.endText();
      yPosition -= 15; // Ajustar este valor para controlar el espaciado entre líneas
    }

    contentStream.close();
    document.save(rutaArchivo);
    document.close();
  }
}
