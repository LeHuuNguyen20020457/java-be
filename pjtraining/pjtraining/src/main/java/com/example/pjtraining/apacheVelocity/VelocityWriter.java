package com.example.pjtraining.apacheVelocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Configuration
public class VelocityWriter {
    static String modelName = "S_Batch_Entry";
    static String packageName = "com.example.pjtraining.apacheVelocity";

    private ReadExcel readExcel;

    public VelocityWriter() {}
    @Autowired
    public VelocityWriter(ReadExcel readExcel) {
        this.readExcel = readExcel;
    }

    public static String getModelName() {
        return modelName;
    }

    public static void setModelName(String modelName) {
        VelocityWriter.modelName = modelName;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static void setPackageName(String packageName) {
        VelocityWriter.packageName = packageName;
    }

    public ReadExcel getReadExcel() {
        return readExcel;
    }

    public void setReadExcel(ReadExcel readExcel) {
        this.readExcel = readExcel;
    }



    public void velocityReadFileGenEntity() throws IOException {
//        VelocityEngine velocityEngine = new VelocityEngine();
//        velocityEngine.init();
//
//        Template t = velocityEngine.getTemplate("templates/class.vm");
//
//        VelocityContext context = new VelocityContext();
//
//        if(packageName != null) {
//            context.put("packageName", packageName);
//        }

        try {
            System.out.println("vaooooooo 1");
            List<PropertyBatchEntry> listProperties= readExcel.readFileExcel();
        } catch (IOException E) {
            System.out.println("vaooo error");
        }


//        context.put("properties", listProperties);
//
//        StringWriter writer = new StringWriter();
//
//        t.merge(context, writer);
//        System.out.println(writer.toString());

    }
}
