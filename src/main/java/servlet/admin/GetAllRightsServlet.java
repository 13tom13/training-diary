package servlet.admin;

import entity.model.Rights;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static servlet.utils.ServletUtils.writeJsonResponse;

@WebServlet(name = "GetAllRightsServlet", urlPatterns = {"/admin/rights"})
public class GetAllRightsServlet extends AdminServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Rights> allRights = userRepository.getAllRights();
        System.out.println("All rights from servlet: " + allRights);
        // Преобразуем данные в JSON и отправляем как ответ
        writeJsonResponse(response, allRights, HttpServletResponse.SC_OK);
    }
}
