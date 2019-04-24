package sl.newpoi.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sl.newpoi.entity.Person;
import sl.newpoi.service.PersonService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 舒露
 */
@Controller
public class PersonAction extends ActionSupport {
    private static final String I_XLS_XLSX_$ = "^.+\\.(?i)((xls)|(xlsx))$";
    private final PersonService personService;
    private List<Person> personList;
    private Person person;
    private String[] selectedRow;

    private File personExcel;
    private String personExcelContentType;
    private String personExcelFileName;

    @Autowired
    public PersonAction(PersonService personService) {
        this.personService = personService;
    }


    public String inExcel() {
        //1、获取excel文件
        if (personExcel != null) {
            //是否是excel
            if (personExcelFileName.matches(I_XLS_XLSX_$)) {
                //2、导入
                personService.importExcel(personExcel, personExcelFileName);
            }
        }
        return SUCCESS;
    }

    public String showList() {
        personList = personService.findObjects();
        System.out.println(personList);
        return "list";
    }

    public void exportExcel() {
        try {
            //1、查找用户列表
            personList = personService.findObjects();
            //2、导出
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("application/x-execl");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String("用户列表.xls".getBytes(), StandardCharsets.ISO_8859_1));
            ServletOutputStream outputStream = response.getOutputStream();
            personService.exportExcel(personList, outputStream);
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String[] getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(String[] selectedRow) {
        this.selectedRow = selectedRow;
    }

    public File getPersonExcel() {
        return personExcel;
    }

    public void setPersonExcel(File personExcel) {
        this.personExcel = personExcel;
    }

    public String getPersonExcelContentType() {
        return personExcelContentType;
    }

    public void setPersonExcelContentType(String personExcelContentType) {
        this.personExcelContentType = personExcelContentType;
    }

    public String getPersonExcelFileName() {
        return personExcelFileName;
    }

    public void setPersonExcelFileName(String personExcelFileName) {
        this.personExcelFileName = personExcelFileName;
    }
}
