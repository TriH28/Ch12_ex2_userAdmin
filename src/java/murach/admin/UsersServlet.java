package murach.admin;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import murach.business.User;
import murach.data.UserDB;

public class UsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String url = "/index.jsp";

        // Lấy action từ request
        String action = request.getParameter("action");
        if (action == null) {
            action = "display_users";  // hành động mặc định
        }

        try {
            // =============================
            // 1️⃣ HIỂN THỊ DANH SÁCH NGƯỜI DÙNG
            // =============================
            if (action.equals("display_users")) {
                ArrayList<User> users = UserDB.selectUsers();
                request.setAttribute("users", users);
                url = "/index.jsp";
            }

            // =============================
            // 2️⃣ HIỂN THỊ FORM SỬA THÔNG TIN (display_user)
            // =============================
            else if (action.equals("display_user")) {
                String email = request.getParameter("email");
                User user = UserDB.selectUser(email);
                request.setAttribute("user", user);
                url = "/user.jsp";
            }

            // =============================
            // 3️⃣ CẬP NHẬT NGƯỜI DÙNG (update_user)
            // =============================
            else if (action.equals("update_user")) {
                String email = request.getParameter("email");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");

                User user = new User();
                user.setEmail(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);

                int result = UserDB.update(user);

                if (result > 0) {
                    System.out.println("✅ Cập nhật thành công cho user: " + email);
                } else {
                    System.out.println("⚠️ Cập nhật thất bại cho user: " + email);
                }

                ArrayList<User> users = UserDB.selectUsers();
                request.setAttribute("users", users);
                url = "/index.jsp";
            }

            // =============================
            // 4️⃣ XÓA NGƯỜI DÙNG (delete_user)
            // =============================
            else if (action.equals("delete_user")) {
                String email = request.getParameter("email");
                User user = UserDB.selectUser(email);

                if (user != null) {
                    UserDB.delete(user);
                    System.out.println("🗑️ Đã xóa user: " + email);
                }

                ArrayList<User> users = UserDB.selectUsers();
                request.setAttribute("users", users);
                url = "/index.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "❌ Lỗi xử lý yêu cầu: " + e.getMessage());
            url = "/index.jsp";
        }

        // =============================
        // Forward kết quả về trang JSP
        // =============================
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
