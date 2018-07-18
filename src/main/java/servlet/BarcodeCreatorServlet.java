package servlet;

import org.apache.commons.io.IOUtils;
import service.BarcodeCreatorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = "/submitBarcodeForm")
public class BarcodeCreatorServlet extends HttpServlet {
    private final BarcodeCreatorService barcodeCreatorService = new BarcodeCreatorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String input = request.getParameter("input");
        String barcodeType = request.getParameter("barcodeType");

        InputStream inputStream;
        try {
            inputStream = barcodeCreatorService.receiveDataFromFormAndReturnPdfFile(barcodeType, input);
            response.setContentType("application/pdf");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/").forward(request, response);
        }
    }


//    private void testSendPdfFromFile(HttpServletRequest request, HttpServletResponse response) throws ServletException,
//            IOException {
//
//        File pdfFile = new File("C:\\IntelliJ IDEA Community Edition 2018.1.5\\projekty fideltronik\\barcodeAppMavenJavaEE\\test.pdf");
//
//        response.setContentType("application/pdf");
//        response.addHeader("Content-Disposition", "attachment; filename=" + "test.pdf");
//        response.setContentLength((int) pdfFile.length());
//
//        FileInputStream fileInputStream = new FileInputStream(pdfFile);
//        OutputStream responseOutputStream = response.getOutputStream();
//        int bytes;
//        while ((bytes = fileInputStream.read()) != -1) {
//            responseOutputStream.write(bytes);
//        }
//
//    }
}
