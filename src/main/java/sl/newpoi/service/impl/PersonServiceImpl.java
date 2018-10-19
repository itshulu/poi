package sl.newpoi.service.impl;

import org.springframework.stereotype.Service;
import sl.newpoi.dao.PersonDao;
import sl.newpoi.entity.Person;
import sl.newpoi.service.PersonService;
import sl.newpoi.util.ExcelUtil;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * @author 舒露
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Resource
    private PersonDao personDao;

    @Override
    public void save(Person person) {
        personDao.save(person);
    }

    @Override
    public void update(Person person) {
        personDao.update(person);
    }

    @Override
    public void delete(Serializable id) {
        personDao.delete(id);
    }

    @Override
    public Person findObjectById(Serializable id) {
        return personDao.findObjectById(id);
    }

    @Override
    public List<Person> findObjects() {
        return personDao.findObjects();
    }

    @Override
    public void exportExcel(List<Person> personList, ServletOutputStream outputStream) {
        ExcelUtil.exportUserExcel(personList, outputStream);
    }
    @Override
    public void importExcel(File personExcel, String personFileName) {
        List<Person> personList = ExcelUtil.importExcel(personExcel, personFileName);
        assert personList != null;
        personList.forEach(x->{personDao.save(x);});
    }
}
