package sl.newpoi.service;

import org.springframework.stereotype.Service;
import sl.newpoi.entity.Person;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.List;


/**
 * @author 舒露
 */
public interface PersonService {
    /**
     * 新增
     */
    void save(Person person);

    /**
     * 更新
     */
    void update(Person person);

    /**
     * 根据id删除O
     */
    void delete(Serializable id);

    /**
     * 根据id查找
     *
     * @param id 用户Id
     * @return 返回person
     */
    Person findObjectById(Serializable id);

    /**
     * 查找列表
     *
     * @return 返回所有用户
     */
    List<Person> findObjects();

    /**
     * 导出用户列表
     *
     * @param personList   返回所有用户
     * @param outputStream 传入一个输出流
     */
    void exportExcel(List<Person> personList, ServletOutputStream outputStream);

    /**
     * 导入用户列表
     *
     * @param personExcel    导入的用户表格
     * @param personFileName 用户表格名称
     */
    void importExcel(File personExcel, String personFileName);
}
