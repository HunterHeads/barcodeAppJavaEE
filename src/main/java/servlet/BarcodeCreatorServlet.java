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
//    @Inject
    private final BarcodeCreatorService barcodeCreatorService = new BarcodeCreatorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String input = request.getParameter("input");
        String barcodeType = request.getParameter("barcodeType");

        response.setContentType("application/pdf");

        InputStream inputStream = barcodeCreatorService.receiveDataFromFormAndReturnPdfFile(barcodeType, input);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
//        return "barcodeCreator";
//        test(request, response);
    }


    private void test(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        File pdfFile = new File("C:\\IntelliJ IDEA Community Edition 2018.1.5\\projekty fideltronik\\barcodeAppMavenJavaEE\\test.pdf");

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + "test.pdf");
        response.setContentLength((int) pdfFile.length());

        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        OutputStream responseOutputStream = response.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            responseOutputStream.write(bytes);
        }

    }
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
//    }

    //    @Autowired
//    public servlet.BarcodeCreatorServlet(service.BarcodeCreatorService barcodeCreatorService) {
//        this.barcodeCreatorService = barcodeCreatorService;
//    }
//
//    @GetMapping(value = {"", "/", "createBarcode"})
//    public String getBarcodeCreator(){
//        return "barcodeCreator";
//    }
//
//    @GetMapping(value="/submitBarcodeForm")
//    public void createBarcode(  @RequestParam(value="input", required=false) String input,
//                                @RequestParam(value="barcodeType", required=true) String barcodeType,
//                                HttpServletResponse response) throws IOException {
//        InputStream inputStream = barcodeCreatorService.receiveDataFromFormAndReturnPdfFile(barcodeType, input);
//        IOUtils.copy(inputStream, response.getOutputStream());
//        response.flushBuffer();
////        return "barcodeCreator";
//    }
}
