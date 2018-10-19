package sl.newpoi.dao.core;

import java.io.Serializable;
import java.util.List;

/**
 * @author 舒露
 */
public interface BaseDao<T> {
    /**
     * 新增
     */
     void save(T entity);

    /**
     * 更新
     */
     void update(T entity);

    /**
     * 根据id删除
     */
     void delete(Serializable id);

    /**
     * 根据id查找
     */
     T findObjectById(Serializable id);

    /**
     * 查找列表
     */
    List<T> findObjects();
}
