package servlet.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.repository.user.UserRepository;
import jakarta.servlet.http.HttpServlet;


import static servlet.utils.ServletUtils.getObjectMapper;

public abstract class AdminServlet extends HttpServlet {

    protected final UserRepository userRepository;
    protected final ObjectMapper objectMapper;

    public AdminServlet() {

        try {
            Class.forName("org.postgresql.Driver");
            this.userRepository = null;
            this.objectMapper = getObjectMapper();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
