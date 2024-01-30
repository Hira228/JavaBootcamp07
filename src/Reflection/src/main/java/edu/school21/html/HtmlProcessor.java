package edu.school21.html;

import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;
import edu.school21.employee.User;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes({"edu.school21.annotations.HtmlInput", "edu.school21.annotations.HtmlForm"})
@AutoService(Processor.class) public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            try (FileWriter fileWriter = new FileWriter(htmlForm.fileName(), true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                bufferedWriter.write("<form action = " + '"' +htmlForm.action() + '"' + " method = " + '"' + htmlForm.method() + '"' + ">\n");
                List<? extends Element> enclosedElements = element.getEnclosedElements();
                for( Element element1: enclosedElements) {
                    HtmlInput annotation = element1.getAnnotation(HtmlInput.class);
                    if(annotation != null)
                        bufferedWriter.write("\t<input type = " + '"' + annotation.type() + '"' + " name = " +  '"' +
                                annotation.name() + '"' + " placeholder = " + '"' + annotation.placeholder() + '"' + ">\n");
                }


                bufferedWriter.write("</form>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
