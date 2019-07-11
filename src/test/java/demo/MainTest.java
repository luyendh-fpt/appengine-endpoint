package demo;

import com.google.gson.Gson;
import hello.entity.Product;
import hello.entity.Student;

import java.util.Calendar;

public class MainTest {
    public static void main(String[] args) {
//        Student student = new Student();
//        student.setRollNumber("A001");
//        student.setName("Hung");
//        student.setEmail("hung@gmail.com");
//        student.setStatus(1);
//
//        Product product = new Product();
//        product.setId(10);
//        product.setName("San pham 1");
//        product.setPrice(5000);
//        product.setStatus(1);
//        product.setCreatedAt(Calendar.getInstance().getTimeInMillis());
//        product.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
////
//        JsonApiObject obj = new JsonApiObject();
//        obj.setObject(product);
//        System.out.println(new Gson().toJson(obj));
        String content = "{\"type\":\"student\",\"id\":\"A001\",\"attributes\":{\"name\":\"Hung\",\"email\":\"hung@gmail.com\",\"status\":1}}\n";
//        String contentProduct = "{\"type\":\"product\",\"id\":10,\"attributes\":{\"createdAt\":1562821406448,\"price\":5000,\"name\":\"San pham 1\",\"updatedAt\":1562821406466,\"status\":1}}\n";

        JsonApiObject<Student> jsonApiObject = JsonApiObject.getInstance(Student.class);
        Student student = jsonApiObject.getContentFromJson(content);

        System.out.println(student.getRollNumber());
        System.out.println(student.getStatus());

    }
}
