package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Response_DTO;
import dto.User_DTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.User;
import model.HibernateUtil;
import model.Mail;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            User_DTO user_DTO = gson.fromJson(request.getReader(), User_DTO.class);

            Response_DTO response_DTO = new Response_DTO();

            if (user_DTO.getFirstName().isEmpty()) {
                response_DTO.setContent("Please enter your First Name");
            } else if (user_DTO.getLastName().isEmpty()) {
                response_DTO.setContent("Please enter your Last Name");
            } else if (user_DTO.getEmail().isEmpty()) {
                response_DTO.setContent("Please enter your Email");
            } else if (!Validation.isEmailValid(user_DTO.getEmail())) {
                response_DTO.setContent("Email is not valid.");
            } else if (user_DTO.getPassword().isEmpty()) {
                response_DTO.setContent("Please Enter your Password");
            } else if (!Validation.isPasswordValid(user_DTO.getPassword())) {
                response_DTO.setContent("Password must include ate leat one uppercase letter, number,special charactor and be at least eight chracters long.");
            } else {

                Session session = HibernateUtil.getSessionFactory().openSession();

                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));

                if (!criteria1.list().isEmpty()) {
                    response_DTO.setContent("Email already used by another user.");

                } else {

                    int code = (int) (Math.random() * 100000);

                    User user = new User();
                    user.setEmail(user_DTO.getEmail());
                    user.setFirst_name(user_DTO.getFirstName());
                    user.setLast_name(user_DTO.getLastName());
                    user.setPassword(user_DTO.getPassword());
                    user.setVerification(String.valueOf(code));

                    String content = "<html lang=\"en\">\n"
                            + "<head>\n"
                            + "    <meta charset=\"UTF-8\">\n"
                            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "    <title>Verify Your Email</title>\n"
                            + "</head>\n"
                            + "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0;\">\n"
                            + "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\">\n"
                            + "        <tr>\n"
                            + "            <td style=\"text-align: center;\">\n"
                            + "                <h2 style=\"color: #333333;\">Verify Your Email</h2>\n"
                            + "                <p style=\"color: #666666;\">Thank you for signing up with Smart Trade!</p>\n"
                            + "                <p style=\"color: #666666;\">Please use the verification code below to complete your registration:</p>\n"
                            + "                <h3 style=\"color: #007bff; font-size: 24px; margin: 20px 0;\">" + code + "</h3>\n"
                            + "                <p style=\"color: #666666;\">If you didn’t request this, you can safely ignore this email.</p>\n"
                            + "            </td>\n"
                            + "        </tr>\n"
                            + "    </table>\n"
                            + "</body>\n"
                            + "</html>";

                    Thread sendMailThread = new Thread() {
                        @Override
                        public void run() {
                            Mail.sendMail(user_DTO.getEmail(), "Verification Smart Trade Account", content);
                        }
                    };

                    sendMailThread.start();

                    session.save(user);
                    session.beginTransaction().commit();
                    request.getSession().setAttribute("email", user_DTO.getEmail());
                    response_DTO.setSuccess(true);

                    response_DTO.setContent("Registration complete");
                }
                session.close();
            }
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(response_DTO));

    }

}
