package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Created by Paktalin on 14.08.2017.
 */
public class SparkFormHandling {
    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(SparkFormHandling.class, "/");

        Spark.get("/", new Route() {
            StringWriter writer = new StringWriter();
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                Template fruitTemplate = configuration.getTemplate("fruit_picker.ftl");
                Map<String, Object> fruitMap = new HashMap<String, Object>();
                fruitMap.put("fruits", Arrays.asList("apple", "orange", "banana", "peach"));
                fruitTemplate.process(fruitMap, writer);
                } catch (Exception e) {
                    halt(500);
                    return null;
                }
                return writer;
            }
        });

        Spark.post("/favorite_fruit", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                final String fruit = request.queryParams("fruit");
                if (fruit == null) {
                    return "Why didn't you pick one?";
                }
                else {
                    return "Your favorite fruit is " + fruit;
                }
            }
        });
    }
}
