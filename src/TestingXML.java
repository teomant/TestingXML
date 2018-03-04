import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestingXML   {

    public static void main (String args[]) throws IOException,ParserConfigurationException,SAXException,TransformerConfigurationException,TransformerException{

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File(args[0]));

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        List<Student> students = new ArrayList<Student>();

        NodeList nList = document.getElementsByTagName("student");
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Student student = new Student();
            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                student.setAverage(Float.parseFloat(eElement.getElementsByTagName("average").item(0).getTextContent()));
                student.setFirstName(eElement.getAttribute("firstname"));
                student.setLastName(eElement.getAttribute("lastname"));
                student.setGroup(eElement.getAttribute("groupnumber"));
                NodeList subjects = eElement.getElementsByTagName("subject");
                List<Subject> parsedSubjects = new ArrayList<>();
                for (int temp1 = 0; temp1 < subjects.getLength(); temp1++) {
                    Subject subject = new Subject();
                    Node subjectnode = subjects.item(temp1);
                    if (subjectnode.getNodeType() == Node.ELEMENT_NODE) {
                        Element sElement = (Element) subjectnode;
                        subject.setTitle(sElement.getAttribute("title"));
                        subject.setMark(Integer.parseInt(sElement.getAttribute("mark")));
                    }
                    parsedSubjects.add(subject);
                }
                student.setSubjects(parsedSubjects);
            }
            students.add(student);
        }

        for (Student student:students){
            int counter = 0;
            float marksumm = 0;
            for (counter=0,marksumm=0;counter<student.getSubjects().size();counter++){
                marksumm+=student.getSubjects().get(counter).getMark();
            }
            marksumm=marksumm/counter;
            student.setAverage(marksumm);
        }

        Document outdocument = builder.newDocument();
        Element mainRootElement = outdocument.createElement("group");
        outdocument.appendChild(mainRootElement);
        for (Student student:students){
            Element s = outdocument.createElement("student");
            s.setAttribute("firstname", student.getFirstName());
            s.setAttribute("lastname", student.getLastName());
            s.setAttribute("groupnumber", student.getGroup());
            Element a= outdocument.createElement("average");
            a.setTextContent(Float.toString(student.getAverage()));
            s.appendChild(a);
            for (Subject subject:student.getSubjects()) {
                Element sub= outdocument.createElement("subject");
                sub.setAttribute("title", subject.getTitle());
                sub.setAttribute("mark",Integer.toString(subject.getMark()));
                s.appendChild(sub);
            }
            mainRootElement.appendChild(s);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(outdocument);
        StreamResult console = new StreamResult(new FileOutputStream(args[1]));
        transformer.transform(source, console);
    }
}
