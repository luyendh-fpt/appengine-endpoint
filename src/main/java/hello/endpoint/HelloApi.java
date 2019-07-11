package hello.endpoint;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import hello.entity.Student;
import hello.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class HelloApi extends HttpServlet {

    static {
        ObjectifyService.register(Student.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(new Gson().toJson(ofy().load().type(Student.class).filter("status", 1).list()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = StringUtil.convertInputStreamToString(req.getInputStream());
        Student student = new Gson().fromJson(content, Student.class);
        Key<Student> studentKey = ofy().save().entity(student).now();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print(new Gson().toJson(studentKey));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = StringUtil.convertInputStreamToString(req.getInputStream());
        Student updateStudent = new Gson().fromJson(content, Student.class);
        updateStudent.getRollNumber();
        Student existStudent = ofy().load().type(Student.class).id(updateStudent.getRollNumber()).now();
        if (existStudent == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sinh viên.");
            return;
        }
        existStudent.setName(updateStudent.getName());
        existStudent.setEmail(updateStudent.getEmail());
        ofy().save().entity(existStudent).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(new Gson().toJson(existStudent));
    } // Patch


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String rollNumber = req.getParameter("rollNumber");
        Student existStudent = ofy().load().type(Student.class).id(rollNumber).now();
        if (existStudent == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sinh viên.");
            return;
        }
        existStudent.setStatus(-1);
        Key<Student> key = ofy().save().entity(existStudent).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(new Gson().toJson(key));
    }
}
