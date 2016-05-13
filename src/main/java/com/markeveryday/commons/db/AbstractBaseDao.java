package com.markeveryday.commons.db;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateOperations;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 * DAO层基础实现, 主要采用HibernateTemplate实现, 推荐使用sessionFactory.
 * 各个具体的DAO通过继承此类来获得基本的CRUD功能
 *
 * @author liming
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractBaseDao<E> implements IBaseDao<E> {

    /**
     * 当参数集合大于500时，将自动转换为join
     */
    private static final int MAX_PARAM_COLLECTION_SIZE = 500;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    /**
     * 实体类型信息
     */
    protected Class<E> entityClass;
    /**
     * 实体类名
     */
    protected String entityClassName;

    public AbstractBaseDao() {
        // 通过范型反射，获取在子类中定义的entityClass.
        this.entityClass =
                (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityClassName = entityClass.getSimpleName();
    }

    /**
     * 保存实体
     *
     * @param entity 待保存的实体
     */
    public void save(E entity) {
        hibernateTemplate.save(entity);
    }

    /**
     * merge实体
     *
     * @param entity 待merge的实体
     *
     * @return merge后的实体
     */
    public E merge(E entity) {
        return getHibernateTemplate().merge(entity);
    }

    /**
     * 删除实体
     *
     * @param entity 待删除的实体
     */
    public void delete(E entity) {
        getHibernateTemplate().delete(entity);
        getHibernateTemplate().flush();
    }

    /**
     * 更新实体
     */
    public void update(E entity) {
        getHibernateTemplate().merge(entity);
    }

    /**
     * 根据ID查找对应的实体
     *
     * @param id 实体id
     *
     * @return 对应的实体或者null
     */

    public E findById(Serializable id) {
        return getHibernateTemplate().get(entityClass, id);
    }

    /**
     * 保存或更新(如果实体已存在)
     *
     * @param entity 待保存或者更新的实体
     */
    public void saveOrUpdate(E entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    /**
     * 批量删除
     *
     * @param entities 待删除的实体
     */
    public void deleteBatch(Collection<E> entities) {
        getHibernateTemplate().deleteAll(entities);
    }

    /**
     * 批量保存或更新
     *
     * @param entities 待保存或者更新的实体
     */
    public void saveOrUpdateAll(Collection<E> entities) {
        getHibernateTemplate().saveOrUpdate(entities);
    }

    /**
     * 查找所有实体
     *
     * @param pageParam Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return 所有实体
     */
    public List<E> findAll(Page... pageParam) {

        return findAll(null, pageParam);

    }

    /**
     * 查找所有实体
     *
     * @param orderByProperties String[] 需要排序的属性名，按数组中先后顺序order by
     * @param pageParam         Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return 所有实体
     */
    public List<E> findAll(final List<OrderCondition> orderByProperties, final Page... pageParam) {

        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {
            Criteria criteria = session.createCriteria(entityClass);
            fillOrderByAndPageParam(criteria, orderByProperties, pageParam);
            Object list = criteria.list();
            return list;
        });

    }

    /**
     * 查找和所给实体相似的所有实体，相似表示所给实体中非空的属性值相同的实体
     *
     * @param pageParam Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */

    public List<E> findByExample(final E example, final Page... pageParam) {
        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {
            Criteria criteria = session.createCriteria(entityClass);
            Example exp = Example.create(example);
            criteria.add(exp);
            fillOrderByAndPageParam(criteria, null, pageParam);
            return criteria.list();
        });
    }

    /**
     * 根据属性查找，属性对应的值可以支持含有like条件的value
     *
     * @param properties Map
     * @param pageParam  Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    public List<E> findByProperties(final ConditionSet properties, final Page... pageParam) {
        return findByProperties(properties, new ArrayList<>(), pageParam);
    }

    /**
     * 根据属性查找，属性对应的值可以支持含有like条件的value 。
     *
     * @param properties Map
     * @param pageParam  Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    public E findUniqueByProperties(final ConditionSet properties, final Page... pageParam) {
        return findUniqueByProperties(properties, null, pageParam);

    }

    /**
     * 根据属性查找，属性对应的值可以支持含有like条件
     *
     * @param pageParam Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    public List<E> findByProperty(String propertyName, Object value, Page... pageParam) {
        ConditionSet pv = ConditionAndSet.newInstance(propertyName, value);
        return findByProperties(pv, pageParam);
    }

    /**
     * 根据单个属性查找,并指定排序列，属性对应的值可以支持含有like条件
     *
     * @param orderByProperties OrderBy[] 需要排序的属性名，
     * @param pageParam         Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    public List<E> findByProperty(String propertyName, Object value, List<OrderCondition> orderByProperties,
                                  Page... pageParam) {
        ConditionSet pv = ConditionAndSet.newInstance(propertyName, value);
        return findByProperties(pv, orderByProperties, pageParam);
    }

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件，
     *
     * @param properties      Map
     * @param orderByProperty String
     * @param pageParam       Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    public List<E> findByProperties(final ConditionSet properties, final OrderCondition orderByProperty,
                                    final Page... pageParam) {
        List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
        if (orderByProperty != null) {
            orderConditions.add(orderByProperty);
        }
        return findByProperties(properties, orderConditions, pageParam);

    }

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件
     *
     * @param properties        条件属性
     * @param orderByProperties OrderBy[] 需要排序的属性名，
     * @param pageParam         Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    public List<E> findByProperties(final ConditionSet properties, final List<OrderCondition> orderByProperties,
                                    final Page... pageParam) {
        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {
            Criteria criteria = convertProperties2Criteria(properties, session);
            fillOrderByAndPageParam(criteria, orderByProperties, pageParam);
            return criteria.list();

        });
    }

    /**
     * 根据属性查找,并指定排序列，属性对应的值可以支持含有like条件
     *
     * @param properties        条件属性
     * @param orderByProperties OrderBy[] 需要排序的属性名，
     * @param pageParam         Integer[] 分页查找参数，开始页，与最大记录条数
     *
     * @return Collection
     */
    public E findUniqueByProperties(final ConditionSet properties, final List<OrderCondition> orderByProperties,
                                    final Page... pageParam) {

        return (E) getHibernateTemplate().execute((HibernateCallback) session -> {
            Criteria criteria = convertProperties2Criteria(properties, session);
            fillOrderByAndPageParam(criteria, orderByProperties, pageParam);
            return criteria.uniqueResult();

        });

    }

    /**
     * 查询数据库指定的条目
     *
     * @param properties        查找参数
     * @param orderByProperties 排序参数
     * @param offset            开始index
     * @param limit             最大数目
     *
     * @return list or null
     */
    @Override
    public List<E> findHeadByProperties(final ConditionSet properties, final List<OrderCondition> orderByProperties,
                                        final int offset, final int limit) {

        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {
            if (limit <= 0) {
                return new ArrayList<E>();
            }
            Criteria criteria = convertProperties2Criteria(properties, session);
            criteria.setFirstResult(offset > 0 ? offset : 0).setMaxResults(limit);
            processOrderProps(criteria, orderByProperties);
            return criteria.list();
        });
    }

    /**
     * 查询数据库指定的条目
     *
     * @param hql    hibernate查询语句
     * @param params 查询参数
     * @param offset 开始index
     * @param limit  最大数目
     *
     * @return
     */
    @Override
    public List<?> queryHeadByHql(final String hql, final Map params, final int offset, final int limit) {

        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {
            if (limit <= 0) {
                return new ArrayList<E>();
            }

            Query query = session.createQuery(hql);
            if (params != null) {
                query = setParams(query, params);
            }
            query.setFirstResult(offset > 0 ? offset : 0).setMaxResults(limit);
            return query.list();
        });
    }

    /**
     * 执行hql语句查询，如果上面的那些方法都不可用的话，如果不带totalCountHql，本方法不计算总记录数
     *
     * @param hql       查询sql
     * @param pageParam 分页参数
     *
     * @return Collection
     */
    public List queryByHQL(final String hql, final String totalCountHql, final Page... pageParam) {

        return this.queryByHQL(hql, null, totalCountHql, pageParam);

    }

    /**
     * 执行hql语句查询，如果上面的那些方法都不可用的话
     *
     * @param hql 查询sql
     *
     * @return Collection
     */
    public List queryByHQL(final String hql, final Map params) {
        return this.queryByHQL(hql, params, null);

    }

    /**
     * 执行hql语句查询;hql语句中变量为?;根据动态参数顺序传入查询参数
     */
    public List queryByHqlWithParams(final String hql, final Object... values) {
        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery(hql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
            }
            return query.list();
        });
    }

    /**
     * 跟与hql和参数查询,如果不带totalCountHql，本方法不计算总记录数
     *
     * @param hql        查询hql
     * @param queryParam 查询参数
     * @param pageParam  分页参数
     *
     * @return
     */
    public List queryByHQL(final String hql, final Map<String, Object> queryParam, final String totalCountHql,
                           final Page... pageParam) {
        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery(hql);
            if (queryParam != null) {
                query = setParams(query, queryParam);
            }
            if (pageParam.length == 1 && pageParam[0] != null) {
                if (totalCountHql != null)// 如果带了需要查询总数的HQL，查询总数设置到pagebean中
                {
                    Query countQuery = session.createQuery(totalCountHql);
                    if (queryParam != null) {
                        countQuery = setParams(countQuery, queryParam);
                    }
                    Integer count = Integer.parseInt(countQuery.uniqueResult() + "");
                    pageParam[0].setRecordsCount(count);
                }
                int start = pageParam[0].getCurrentPage() - 1;
                start = start >= 0 ? start : 0;
                query.setFirstResult(start * pageParam[0].getMaxRecords()).setMaxResults(
                        pageParam[0].getMaxRecords());
            }
            return query.list();
        });
    }

    /**
     * 提供sql和参数查询,如果不带totalCountSql，本方法不计算总记录数
     *
     * @param sql            查询sql
     * @param queryParam     查询参数
     * @param totalCountHql  计数sql
     * @param entities(实体映射) 实体
     * @param pageParam      分页参数
     *
     * @return
     */
    public List queryBySQL(final String sql, final Map<String, Object> queryParam, final String totalCountHql,
                           final Map<String, Class<?>> entities, final Page... pageParam) {
        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {

            Query query = session.createSQLQuery(sql);
            if (entities != null) {
                for (String alias : entities.keySet()) {
                    ((SQLQuery) query).addEntity(alias, entities.get(alias));
                }
            }
            if (queryParam != null) {
                query = setParams(query, queryParam);
            }
            processPageParam(session, query, pageParam, totalCountHql, queryParam);
            return query.list();
        });
    }

    private void processPageParam(Session session, Query query, Page[] pageParam, String totalCountHql,
                                  Map<String, Object> queryParam) {
        if (pageParam.length == 1 && pageParam[0] != null) {
            if (totalCountHql != null)// 如果带了需要查询总数的sql，查询总数设置到pagebean中
            {
                Query countQuery = session.createSQLQuery(totalCountHql);
                if (queryParam != null) {
                    countQuery = setParams(countQuery, queryParam);
                }
                Integer count = Integer.parseInt(countQuery.uniqueResult() + "");
                pageParam[0].setRecordsCount(count);
            }
            int start = pageParam[0].getCurrentPage() - 1;
            start = start >= 0 ? start : 0;
            query.setFirstResult(start * pageParam[0].getMaxRecords()).setMaxResults(
                    pageParam[0].getMaxRecords());
        }
    }

    /**
     * 提供sql和参数查询,如果不带totalCountSql，本方法不计算总记录数
     *
     * @param sql           查询sql
     * @param queryParam    查询参数
     * @param totalCountHql 计数sql
     * @param pageParam     分页参数
     *
     * @return
     */
    public List queryBySQL(final String sql, final Map<String, Object> queryParam, final String totalCountHql,
                           final Page... pageParam) {
        return (List) getHibernateTemplate().execute((HibernateCallback) session -> {

            Query query = session.createSQLQuery(sql);

            if (queryParam != null) {
                query = setParams(query, queryParam);
            }
            processPageParam(session, query, pageParam, totalCountHql, queryParam);
            return query.list();
        });
    }

    /**
     * 执行更新hql
     *
     * @param hql String
     */
    public Integer executeUpdateHql(final String hql) {
        return (Integer) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery(hql);
            return query.executeUpdate();
        });

    }

    public Integer updateByHqlWithParams(final String hql, final Object... values) {
        return (Integer) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery(hql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
            }
            return query.executeUpdate();
        });
    }

    /**
     * 执行更新的sql
     *
     * @param sql    待执行的sql
     * @param params 参数信息
     *
     * @return 更新的条目数
     */
    public Integer executeUpdateSql(final String sql, final Map params) {
        return (Integer) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createSQLQuery(sql);
            if (params != null) {
                query = setParams(query, params);
            }
            return query.executeUpdate();
        });

    }

    /**
     * 执行更新的hql
     *
     * @param hql    待执行的hql
     * @param params 查询参数
     *
     * @return 更新的条目树
     */
    public Integer executeUpdateHql(final String hql, final Map params) {
        return (Integer) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery(hql);
            query = setParams(query, params);
            return query.executeUpdate();
        });

    }

    /**
     * 本类使用，属性排序和分页设置
     *
     * @param criteria          Criteria
     * @param orderByProperties OrderBy[]
     * @param pageParam         Integer[]
     */
    private void fillOrderByAndPageParam(Criteria criteria, List<OrderCondition> orderByProperties, Page... pageParam) {

        if (pageParam != null && pageParam.length == 1 && pageParam[0] != null) {
            // 首先得到总记录数，付给pagebean实体的totalCount值
            criteria.setProjection(Projections.rowCount());
            Long totalCount = (Long) criteria.uniqueResult();
            pageParam[0].setRecordsCount(totalCount.intValue());
            // 将crit投影值空，继续查结果集合
            criteria.setProjection(null);
            int start = pageParam[0].getCurrentPage() - 1;
            start = start >= 0 ? start : 0;
            criteria.setFirstResult(start * pageParam[0].getMaxRecords()).setMaxResults(pageParam[0].getMaxRecords());
        }
        processOrderProps(criteria, orderByProperties);

    }

    private void processOrderProps(Criteria criteria, List<OrderCondition> orderByProperties) {
        if (orderByProperties != null) {
            for (OrderCondition orderBy : orderByProperties) {
                Order order = null;
                if (orderBy instanceof DescOrder) {
                    order = Order.desc(orderBy.getPropertyName());
                } else {
                    order = Order.asc(orderBy.getPropertyName());
                }
                criteria.addOrder(order);
            }
        }
    }

    /**
     * 根据属性查询记录个数
     *
     * @param properties 条件属性
     *
     * @return 符合条件属性的条目个数
     */
    public Long findCountByProperties(final ConditionSet properties) {

        return (Long) getHibernateTemplate().execute((HibernateCallback) session -> {
            Criteria criteria = convertProperties2Criteria(properties, session);
            criteria.setProjection(Projections.rowCount());
            List<String> groupBys = properties.getGroups();
            if (groupBys != null && !groupBys.isEmpty()) {
                for (String propGroupBy : groupBys) {
                    criteria.setProjection(Projections.groupProperty(propGroupBy));
                }
            }
            Long totalCount = (Long) criteria.uniqueResult();
            return totalCount;
        });

    }

    /**
     * 构造查询所有条件
     *
     * @param properties 条件参数
     * @param session    当前session
     *
     * @return
     */
    private Criteria convertProperties2Criteria(ConditionSet properties, Session session) {
        Criteria criteria = session.createCriteria(entityClass);
        if (properties != null) {
            criteria.add(constructCriteriaValue(null, properties));

            List<String> groupBys = properties.getGroups();
            if (groupBys != null && !groupBys.isEmpty()) {
                for (String propGroupBy : groupBys) {
                    criteria.setProjection(Projections.groupProperty(propGroupBy));
                }
            }
        }

        return criteria;
    }

    /**
     * 构造查询条件
     *
     * @param propertyName 属性名
     * @param propValue    属性值
     *
     * @return Criterion
     */

    private Criterion constructCriteriaValue(String propertyName, Object propValue) {
        Criterion criteria = null;
        if (propValue == null) {
            criteria = Restrictions.isNull(propertyName);
        } else if (propValue instanceof String && ((String) propValue).indexOf("%") != -1) {
            // 属性值是字符串类型的并且含有%,用like匹配
            criteria = Restrictions.like(propertyName, propValue);
        } else if (propValue instanceof Collection) {
            Collection valueCollection = (Collection) propValue;
            if (valueCollection.isEmpty()) {
                criteria = Restrictions.isNull(propertyName);
            } else {
                criteria = Restrictions.in(propertyName, valueCollection);
            }

        } else if (propValue instanceof Object[]) {
            Object[] values = (Object[]) propValue;
            if (values.length == 0) {
                criteria = Restrictions.isNull(propertyName);
            } else {
                criteria = Restrictions.in(propertyName, values);
            }
            criteria = Restrictions.in(propertyName, (Object[]) propValue);
        } else if (propValue instanceof CompareCondition) {
            CompareCondition condition = (CompareCondition) propValue;
            if (propertyName == null) {
                propertyName = condition.getPropertyName();
            }
            if (propValue instanceof EqualCondition) {
                criteria = constructCriteriaValue(propertyName, condition.getValue());
            } else if (propValue instanceof NotEqualCondition) {
                Object notValue = condition.getValue();
                if (notValue == null) {
                    criteria = Restrictions.isNotNull(propertyName);
                } else {
                    criteria = Restrictions.ne(propertyName, notValue);
                }
            } else if (propValue instanceof NotInCondition) {
                criteria = Restrictions.not(Restrictions.in(propertyName, (Collection) condition.getValue()));
            } else if (propValue instanceof GreaterCondition) {
                criteria = Restrictions.gt(propertyName, condition.getValue());
            } else if (propValue instanceof LessThanCondition) {
                criteria = Restrictions.lt(propertyName, condition.getValue());
            } else if (propValue instanceof BetweenCondition) {
                criteria = Restrictions.between(propertyName, condition.getMinValue(), condition.getMaxValue());
            } else if (propValue instanceof LessOrEqualCondition) {
                criteria = Restrictions.le(propertyName, condition.getValue());
            } else if (propValue instanceof GreaterOrEqualCondition) {
                criteria = Restrictions.ge(propertyName, condition.getValue());
            }

        } else if (propValue instanceof ConditionSet) {
            ConditionSet propMap = (ConditionSet) propValue;
            List<Criterion> ccIt = new ArrayList<Criterion>();
            for (Iterator<Object> it = propMap.getValues().iterator(); it.hasNext(); ) {
                Object condition = it.next();

                ccIt.add(constructCriteriaValue(null, condition));
            }

            if (ccIt.size() > 1) {
                if (propValue instanceof ConditionAndSet) {
                    criteria = Restrictions.and(ccIt.get(0), ccIt.get(1));
                    for (int i = 2; i < ccIt.size(); i++) {
                        criteria = Restrictions.and(criteria, ccIt.get(i));
                    }
                } else {
                    criteria = Restrictions.or(ccIt.get(0), ccIt.get(1));
                    for (int i = 2; i < ccIt.size(); i++) {
                        criteria = Restrictions.or(criteria, ccIt.get(i));

                    }
                }
            } else if (ccIt.size() == 1) {
                criteria = ccIt.get(0);
            }
        } else {
            criteria = Restrictions.eq(propertyName, propValue);
        }
        return criteria;
    }

    private Query setParams(Query resultQuery, Map queryParam) {
        // 设置查询参数
        for (Map.Entry<String, Object> entry : (Set<Map.Entry<String, Object>>) queryParam.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Collection) {
                Collection collection = (Collection) value;
                if (collection.size() < MAX_PARAM_COLLECTION_SIZE) {
                    resultQuery.setParameterList(key, (Collection) value);
                } else {
                    String collectionStr = "'" + StringUtils.join(collection, "','") + "'";
                    resultQuery.setParameter(key, collectionStr);
                }
            } else if (value instanceof Object[]) {
                resultQuery.setParameterList(key, (Object[]) value);
            } else {
                resultQuery.setParameter(key, value);
            }

        }

        return resultQuery;
    }

    /**
     * 根据属性值，批量删除
     *
     * @param propertyName 属性名
     * @param value        属性值
     *
     * @return 删除的个数
     */
    public Integer deleteByProperty(String propertyName, Object value) {
        Map pv = new HashMap(1);
        pv.put(propertyName, value);
        return deleteByProperties(pv);
    }

    /**
     * 根据属性的值批量删除
     *
     * @param props 属性参数
     *
     * @return 删除的条目数
     */
    public Integer deleteByProperties(Map<String, Object> props) {
        // 构建HQL删除
        String className = entityClass.getSimpleName();
        String alisname = className.toLowerCase();
        StringBuilder hqlbuffer = new StringBuilder("delete from " + entityClass.getName() + " as " + alisname
                + " where ");

        Iterator<Map.Entry<String, Object>> i = props.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, Object> entry = i.next();
            if (entry.getValue() == null) {
                hqlbuffer.append(" ").append(alisname).append(".").append(entry.getKey()).append(" is null and");
                i.remove();
            } else if (entry.getValue() instanceof Collection) {
                if (((Collection) entry.getValue()).isEmpty()) {
                    hqlbuffer.append(" 1<>1 and");
                } else {
                    hqlbuffer.append(" ").append(alisname).append(".").append(entry.getKey()).append(" in(:")
                            .append(entry.getKey()).append(") and");
                }

            } else {
                hqlbuffer.append(" ").append(alisname).append(".").append(entry.getKey()).append("=:")
                        .append(entry.getKey()).append(" and");
            }
        }

        hqlbuffer.replace(hqlbuffer.length() - 3, hqlbuffer.length(), "");// 去最后一个and

        return executeUpdateHql(hqlbuffer.toString(), props);
    }

    /**
     * 根据特殊的条件查询
     *
     * @param properties 属性名称数组，属性名称支持段式："属性名OPERATOR属性值",OPERATOR可以为"=,>,<",字符串类型可以带%查询，表示匹配任意
     * @param orderBy    按属性排序， 属性名：0|1，0降序，1升序
     * @param pageBean
     */
    public List<E> findByCommonCondition(String[] properties, String[] orderBy, Page... pageBean) {
        String[] hqls = convertProps2Hql(properties, orderBy);
        return this.queryByHQL(hqls[0], pageBean.length > 0 ? hqls[1] : null, pageBean);
    }

    /**
     * 查询数量
     *
     * @param HSql
     * @param values
     *
     * @return
     */
    public int findCount(String HSql, Object[] values) {
        return ((Long) getHibernateTemplate().iterate(HSql, values).next()).intValue();
    }

    /**
     * 从session中移除
     *
     * @param entity
     *
     * @see HibernateOperations#evict(Object)
     */
    public void evict(E entity) {
        getHibernateTemplate().evict(entity);
    }

    private void handleFieldCondition(StringBuilder sbuffer, String alias, String fieldCondition) {
        if (fieldCondition.startsWith("(")) {
            int count = 1;
            boolean inQuote = false;
            for (int i = 1; i < fieldCondition.length(); i++) {
                if (fieldCondition.charAt(i) == '\'') {
                    inQuote = !inQuote;
                } else if (!inQuote) {
                    if (fieldCondition.charAt(i) == '(') {
                        count++;
                    } else if (fieldCondition.charAt(i) == ')') {
                        count--;
                    }
                }

                if (count == 0) {
                    String first = fieldCondition.substring(1, i).trim();
                    String second = fieldCondition.substring(i + 1).trim();

                    if (second.startsWith("OR")) {
                        sbuffer.append("(");
                        handleFieldCondition(sbuffer, alias, first);
                        sbuffer.append(" OR ");
                        handleFieldCondition(sbuffer, alias, second.substring(2).trim());
                        sbuffer.append(")");
                    } else if (second.startsWith("AND")) {
                        sbuffer.append("(");
                        handleFieldCondition(sbuffer, alias, first);
                        sbuffer.append(" AND ");
                        handleFieldCondition(sbuffer, alias, second.substring(3).trim());
                        sbuffer.append(")");
                    }

                    break;
                }
            }
        } else {
            int pos = fieldCondition.indexOf(" OR ");
            if (pos >= 0) {
                sbuffer.append("(");
                handleFieldCondition(sbuffer, alias, fieldCondition.substring(0, pos).trim());
                sbuffer.append(" OR ");
                handleFieldCondition(sbuffer, alias, fieldCondition.substring(pos + 4).trim());
                sbuffer.append(")");
            } else {
                pos = fieldCondition.indexOf(" AND ");
                if (pos >= 0) {
                    sbuffer.append("(");
                    handleFieldCondition(sbuffer, alias, fieldCondition.substring(0, pos).trim());
                    sbuffer.append(" AND ");
                    handleFieldCondition(sbuffer, alias, fieldCondition.substring(pos + 4).trim());
                    sbuffer.append(")");
                } else {
                    sbuffer.append(alias + "." + fieldCondition.trim());
                }
            }
        }
    }

    private String[] convertProps2Hql(String[] properties, String[] orderBy) {
        String[] results = new String[2];
        String className = this.entityClassName;
        String alias = "_" + className;
        StringBuilder sbuffer = new StringBuilder(" from " + className + " " + alias);
        if (properties != null && properties.length > 0) {
            sbuffer.append(" where ");
        }
        for (int i = 0; properties != null && i < properties.length; i++) {
            String fieldCondition = properties[i];
            handleFieldCondition(sbuffer, alias, fieldCondition.trim());
            if (i < properties.length - 1) {
                sbuffer.append(" and ");
            }
        }
        results[1] = "select count(*) " + sbuffer;// 第二给只有属性的查询总数查询
        if (orderBy != null && orderBy.length > 0) {
            sbuffer.append(" order by ");

            for (String odby : orderBy) {
                String[] obs = odby.split(":");
                sbuffer.append(" " + alias + "." + obs[0] + " " + ("1".equals(obs[1]) ? "asc" : "desc ") + ",");
            }
            sbuffer.delete(sbuffer.length() - 1, sbuffer.length());
        }
        results[0] = sbuffer.toString();
        return results;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

}
