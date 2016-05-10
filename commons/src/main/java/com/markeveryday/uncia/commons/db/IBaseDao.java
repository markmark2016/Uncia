package com.markeveryday.uncia.commons.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate4.HibernateOperations;

/**
 * 基础DAO层接口
 *
 * @param <E> entity
 *
 * @author liming
 */
@SuppressWarnings("rawtypes")
public interface IBaseDao<E> {

    /**
     * 保存实体
     *
     * @param entity 待保存的实体(实体)
     */
    void save(E entity);

    /**
     * merge实体
     *
     * @param entity 待merge的实体(实体)
     *
     * @see HibernateOperations#merge(Object)
     */
    E merge(E entity);

    /**
     * 删除实体
     *
     * @param entity 待删除的实体
     *
     * @see HibernateOperations#delete(Object)
     */
    void delete(E entity);

    /**
     * 更新实体
     *
     * @param entity 待更新的实体
     *
     * @see HibernateOperations#update(Object)
     */
    void update(E entity);

    /**
     * 从session中移除
     *
     * @param entity
     *
     * @see HibernateOperations#evict(Object)
     */
    void evict(E entity);

    /**
     * 根据ID查找
     *
     * @param id 实体id
     *
     * @return 实体实体或者null
     *
     * @see HibernateOperations#get(Class, Serializable)
     */
    E findById(Serializable id);

    /**
     * 保存或更新(如果实体已存在)
     *
     * @param entity 待保存或者更新的实体
     */
    void saveOrUpdate(E entity);

    /**
     * 批量删除
     *
     * @param entitys Collection
     */
    void deleteBatch(Collection<E> entitys);

    /**
     * 批量保存或更新
     *
     * @param entitys Collection
     */
    void saveOrUpdateAll(Collection<E> entitys);

    /**
     * 查找所有实体
     *
     * @param pages Page 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    List<E> findAll(Page... pages);

    /**
     * 查找所有实体
     *
     * @param orderByProperties OrderBy[] 需要排序的属性名，
     * @param pages             Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */

    List<E> findAll(List<OrderCondition> orderByProperties, Page... pages);

    /**
     * 查找和所给实体相似的所有实体，相似表示所给实体中非空的属性值相同的实体
     *
     * @param pages Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    List<E> findByExample(E entity, Page... pages);

    /**
     * 根据属性查找，属性对应的值可以支持含有like条件
     *
     * @param pages Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    List<E> findByProperties(ConditionSet conditionSet, Page... pages);

    /**
     * 根据属性查找，属性对应的值可以支持含有like条件
     *
     * @param pages Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    E findUniqueByProperties(ConditionSet conditionSet, Page... pages);

    /**
     * 根据属性查找，属性对应的值可以支持含有like条件
     *
     * @param pages Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    List<E> findByProperty(String propertyName, Object value, Page... pages);

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件
     *
     * @param properties        Map
     * @param orderByProperties OrderBy[] 需要排序的属性名，
     * @param pages             Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    List<E> findByProperties(ConditionSet properties, List<OrderCondition> orderByProperties, Page... pages);

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件
     *
     * @param properties      Map
     * @param orderByProperty OrderBy 需要排序的属性名
     * @param pages           Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    List<E> findByProperties(ConditionSet properties, OrderCondition orderByProperty, Page... pages);

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件
     *
     * @param properties        Map
     * @param orderByProperties OrderBy[] 需要排序的属性名，
     * @param pages             Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    E findUniqueByProperties(ConditionSet properties, List<OrderCondition> orderByProperties, Page... pages);

    /**
     * 根据单个属性查找,并指定排序列，属性对应的值可以支持含有like
     *
     * @param orderByProperties OrderBy[] 需要排序的属性名，
     * @param pages             Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    List<E> findByProperty(String propertyName, Object value, List<OrderCondition> orderByProperties,
                           Page... pages);

    /**
     * 根据属性查询记录个数
     *
     * @param properties
     *
     * @return
     */
    Long findCountByProperties(ConditionSet properties);

    /**
     * 执行hql语句查询，如果上面的那些方法都不可用的话，如果不带totalCountHql，本方法不计算总记录数
     *
     * @param hql   String
     * @param pages Integer[]
     *
     * @return Collection
     */
    List queryByHQL(String hql, String totalCountHql, Page... pages);

    /**
     * 执行hql语句查询，如果上面的那些方法都不可用的话
     *
     * @param hql String
     *
     * @return Collection
     */
    List queryByHQL(String hql, Map<String, Object> queryParam);

    /**
     * 跟与hql和参数查询,如果不带totalCountHql，本方法不计算总记录数
     *
     * @param hql
     * @param queryParam
     * @param pages
     *
     * @return
     *
     * @author zhaolei
     * <p>
     * Jan 7, 2009
     */
    List queryByHQL(final String hql, final Map<String, Object> queryParam, String totalCountHql,
                    final Page... pages);

    /**
     * 执行hql语句查询;hql语句中变量为?;根据动态参数顺序传入查询参数
     */
    List queryByHqlWithParams(final String hql, final Object... values);

    /**
     * 提供sql和参数查询,如果不带totalCountSql，本方法不计算总记录数
     *
     * @param sql
     * @param queryParam
     * @param totalCountHql
     * @param entities(实体映射)
     * @param pages
     *
     * @return
     */
    List queryBySQL(final String sql,
                    final Map<String, Object> queryParam, final String totalCountHql,
                    final Map<String, Class<?>> entities,
                    final Page... pages);

    /**
     * 提供sql和参数查询,如果不带totalCountSql，本方法不计算总记录数
     *
     * @param sql
     * @param queryParam
     * @param totalCountSql
     * @param pages
     *
     * @return
     *
     * @author lichang
     */
    List queryBySQL(final String sql, final Map<String, Object> queryParam, String totalCountSql,
                    final Page... pages);

    /**
     * 执行更新的hql
     */
    Integer executeUpdateHql(String hql);

    Integer executeUpdateHql(String hql, Map<String, Object> queryParam);

    Integer executeUpdateSql(final String sql, final Map params);

    /**
     * 根据属性值，批量删除
     *
     * @param propertyName
     * @param value
     *
     * @return
     */
    Integer deleteByProperty(String propertyName, Object value);

    /**
     * 根据属性的值批量删除
     *
     * @param props
     *
     * @return
     */
    Integer deleteByProperties(Map<String, Object> props);

    /**
     * 根据特殊的条件查询
     *
     * @param properties 属性名称数组，属性名称支持段式："属性名OPERATOR属性值",OPERATOR可以为"=,>,<",字符串类型可以带%查询，表示匹配任意
     * @param orderBy    按属性排序， 属性名：0|1，0降序，1升序
     * @param pages
     */

    List<E> findByCommonCondition(String[] properties, String[] orderBy,
                                  Page... pages);

    /**
     * 查询数据库指定的条目
     *
     * @param properties
     * @param orderByProperties
     * @param offset            开始index
     * @param limit             最大数目
     *
     * @return
     */
    List<E> findHeadByProperties(final ConditionSet properties, final List<OrderCondition> orderByProperties,
                                 final int offset, final int limit);

    /**
     * 查询数据库指定的条目
     *
     * @param hql
     * @param params
     * @param offset 开始index
     * @param limit  最大数目
     *
     * @return
     */
    List<?> queryHeadByHql(String hql, Map params, int offset, int limit);
}
