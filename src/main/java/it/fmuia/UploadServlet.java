package it.fmuia;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = this.getServletContext().getRealPath("/images");
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            System.out.println("ITEMS SIZE " + items.size());
            String url = "";
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    url = item.getName();
                    File file = new File(path, url);
                    System.out.println("FILE = " + file);
                    item.write(file);
                }
            }
            RequestDispatcher disp = request.getRequestDispatcher("/viewImage.jsp");
            request.setAttribute("IMAGE", url);
            disp.forward(request, response);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
