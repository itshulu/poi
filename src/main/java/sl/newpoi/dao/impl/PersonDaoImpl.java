package sl.newpoi.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sl.newpoi.dao.PersonDao;
import sl.newpoi.dao.core.impl.BaseDaoImpl;
import sl.newpoi.entity.Person;

/**
 * @author 舒露
 */
@Repository
public class PersonDaoImpl extends BaseDaoImpl<Person> implements PersonDao {
    @Override
    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
