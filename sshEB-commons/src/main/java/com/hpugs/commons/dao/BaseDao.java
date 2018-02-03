package com.hpugs.commons.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import com.hpugs.commons.entity.BaseEntity;
import com.hpugs.commons.util.EncapsulationObject;
import com.hpugs.commons.util.ReflectUtils;

/**
 * 
 * @author fux
 * @Description: basedao 专门用于继承
 * @date 2017年6月2日 下午8:45:06
 * @param <T>
 */
public class BaseDao<T extends BaseEntity> implements IBaseDao<T> {
	protected Log log = LogFactory.getLog(this.getClass());
	protected Class<T> entityClass;

	/**
	 * 在构造函数中利用反射机制获得参数T的具体类
	 */
	public BaseDao() {
		entityClass = ReflectUtils.getClassGenricType(getClass());
		log.info(entityClass.getName());
	}

	/**
	 * 数据操作工厂SessionFactory
	 */
	// private static SessionFactory sessionFactory;
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 获得Session
	 */
	public Session getCurrentSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (Exception e) {
			log.error("获取session工厂出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 添加
	 */
	@SuppressWarnings("unchecked")
	public T saveObject(Object obj) {
		try {
			this.getCurrentSession().save(obj);
			return (T) obj;
		} catch (Exception e) {
			log.error("保存出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * saveOrUpdate,成功返回对象,否则返回null
	 */
	@SuppressWarnings("unchecked")
	public T saveOrUpdateObject(Object obj) {
		try {
			if (null == obj) {
				System.out.println("saveOrUpdateObject---obj is null");
			}
			this.getCurrentSession().saveOrUpdate(obj);
			return (T) obj;
		} catch (Exception e) {
			log.error("保存或更新出现异常:" + e);
			System.out.println("保存或更新出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改
	 */
	@SuppressWarnings("unchecked")
	public T updateObject(Object obj) {
		try {
			this.getCurrentSession().update(obj);
			return (T) obj;
		} catch (Exception e) {
			log.error("指定对象更新出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过指定 的Id删除一条记录
	 */
	public boolean deleteObjectById(String id) {
		Session session = this.getCurrentSession();
		try {
			Object obj = session.get(entityClass, Integer.parseInt(id));
			if (obj != null) {
				session.delete(obj);
			}
		} catch (Exception e) {
			log.error("通过Id删除对象出现异常:" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 通过指定 的pojo删除一条记录
	 */
	public boolean deleteObject(Object obj) {
		if (obj != null) {
			try {
				this.getCurrentSession().delete(obj);
			} catch (Exception e) {
				log.error("通过制定对象删除对象出现异常:" + e);
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 通过指定 的条件删除记录
	 */
	public boolean deleteByParams(String whereCondition) {
		String hql = "select o from " + entityClass.getSimpleName() + " o ";
		if (whereCondition != null)
			hql += whereCondition;
		List<?> list = this.getCurrentSession().createQuery(hql).list();
		try {
			for (int i = 0; i < list.size(); i++) {
				this.getCurrentSession().delete(list.get(i));
			}
		} catch (Exception e) {
			log.error("通过条件删除对象出现异常:" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 通过指定 的Id查找一个对象
	 */
	@SuppressWarnings("unchecked")
	public T getObjectById(String id) {
		try {
			Object obj = this.getCurrentSession().get(entityClass.getName(), Integer.parseInt(id));
			return (T) obj;
		} catch (Exception e) {
			log.error("通过Id查询对象出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过指定的条件查找
	 */
	@SuppressWarnings("unchecked")
	public T get(String whereCondition) {
		String hql = "select o from " + entityClass.getSimpleName() + " o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			Query query = this.getCurrentSession().createQuery(hql);
			query.setMaxResults(1);
			Object obj = query.uniqueResult();
			return (T) obj;
		} catch (Exception e) {
			log.error("通过条件查询对象出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取一些数据 firstIndex 开始索引 maxResult 需要获取的最大记录数
	 */
	@SuppressWarnings("unchecked")
	public T get(int firstIndex, String whereCondition) {
		String hql = "select o from " + entityClass.getSimpleName() + " o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			Query query = this.getCurrentSession().createQuery(hql);
			query.setMaxResults(1);
			query.setFirstResult(firstIndex);
			Object obj = query.uniqueResult();
			return (T) obj;
		} catch (Exception e) {
			log.error("通过记录开始位置以及条件查询对象出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取随机记录
	 */
	public T getRandom(String whereCondition) {
		int count = this.getCount(whereCondition);
		if (count == 0)
			return null;
		int recordIndex = (int) (Math.random() * count);
		return this.get(recordIndex, whereCondition);
	}

	/**
	 * 查询所有的记录的分页数目
	 */
	@SuppressWarnings("rawtypes")
	public int getCount(String whereCondition) {
		String hql = "select count(o) from " + entityClass.getSimpleName() + " o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			Query query = this.getCurrentSession().createQuery(hql);
			// 执行查询，并返回结果
			Iterator iterate = query.iterate();
			if (!iterate.hasNext())
				return 0;
			Object object = iterate.next();
			if (object == null)
				return 0;
			if (object instanceof Long) {
				return ((Long) object).intValue();
			} else if (object instanceof Integer) {
				return ((Integer) object).intValue();
			} else {
				try {
					throw new Exception("统计HQL存在错误。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 查询所有的记录的分页数目自定义SQL
	 */
	public int getCountByHQL(String hql) {
		try {
			Query query = this.getCurrentSession().createQuery(hql);
			Iterator<T> it = query.iterate();
			// 执行查询，并返回结果
			if (!it.hasNext())
				return 0;
			Object object = it.next();
			if (object == null)
				return 0;
			if (object instanceof Long) {
				int count = ((Long) object).intValue();
				return count;
			} else if (object instanceof Integer) {
				return ((Integer) object).intValue();
			} else {
				try {
					throw new Exception("统计HQL存在错误。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通过HQL语句以及字段投影查询
	 */
	public List<?> findByHQL(String[] selectColumns, int firstIndex, int maxResult, String hql) {
		try {
			Query query = (Query) this.getCurrentSession().createQuery(hql);
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
			List<Object[]> querylist = query.list();
			List list = null;
			if (querylist != null) {
				list = new ArrayList();
				for (Object[] dataObjs : querylist) {
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add(obj);
				}
			}
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过HQL语句
	 */
	public List<?> findByHQL(String[] selectColumns, String hql, int limit) {
		try {
			Query query = (Query) this.getCurrentSession().createQuery(hql);
			query.setFirstResult(0).setMaxResults(limit);// 查询前limit条，否则查询所有
			List<Object[]> querylist = query.list();
			List list = null;
			if (querylist != null) {
				list = new ArrayList();
				for (Object[] dataObjs : querylist) {
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add(obj);
				}
			}
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过HQL语句
	 */
	public List<?> findByHQL(String[] selectColumns, String hql) {
		try {
			Query query = (Query) this.getCurrentSession().createQuery(hql);
			List<Object[]> querylist = query.list();
			List list = null;
			if (querylist != null) {
				list = new ArrayList();
				for (Object[] dataObjs : querylist) {
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add(obj);
				}
			}
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取一些数据 (多个表)wjy firstIndex 开始索引 maxResult 需要获取的最大记录数 params where 条件参数
	 */
	@SuppressWarnings("rawtypes")
	public List findSomeManyTable(int firstIndex, int maxResult, String whereCondition) {
		String hql = " ";
		if (whereCondition != null)
			hql += whereCondition;
		Session session = null;
		try {
			session = this.getCurrentSession();
			Query query = (Query) session.createQuery(hql);
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
			List list = query.list();
			return list;
		} catch (Exception e) {
			log.error("分页查询出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询所有的记录的分页数目(多个表 )wjy
	 */
	public int getCountManyTable(String params) {
		String hql = "";
		if (params != null)
			hql += params;
		// 得到Hibernate Query对象
		try {
			Query query = this.getCurrentSession().createQuery(hql);
			// 执行查询，并返回结果
			if (!query.iterate().hasNext())
				return 0;
			Object object = query.iterate().next();
			if (object == null)
				return 0;
			if (object instanceof Long) {
				return ((Long) object).intValue();
			} else if (object instanceof Integer) {
				return ((Integer) object).intValue();
			} else {
				try {
					throw new Exception("统计HQL存在错误。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通过HQL语句
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> findByHQL(String hql) {
		try {
			// Query query = (Query) this.getCurrentSession().createQuery(hql);
			Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
			List<Object[]> querylist = query.list();
			return querylist;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取一些数据 column：排重的列 params：where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findUnSame(String column, String whereCondition) {
		String hql = "select distinct o." + column + " from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			List<T> list = this.getCurrentSession().createQuery(hql).list();
			return list;
		} catch (Exception e) {
			log.error("通过条件查询不重复的集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取一些数据 firstIndex 开始索引 maxResult 需要获取的最大记录数 params where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findSome(int firstIndex, int maxResult, String whereCondition) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
		if (whereCondition != null)
			sb.append(whereCondition);
		try {
			Query query = (Query) this.getCurrentSession().createQuery(sb.toString());
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
			List<T> list = query.list();
			return list;
		} catch (Exception e) {
			log.error("分页查询出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取一些数据 selectMap:投影的字段map firstIndex 开始索引 maxResult 需要获取的最大记录数 params
	 * where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findSome(String[] selectColumns, int firstIndex, int maxResult, String whereCondition) {
		StringBuffer sb = new StringBuffer();
		Session session = null;
		try {
			List<T> list = new ArrayList<T>();
			session = this.getCurrentSession();
			if (selectColumns != null) {
				sb.append("select ");
				for (int i = 0; i < selectColumns.length; i++) {
					if (i == selectColumns.length - 1) {
						sb.append(" o.").append(selectColumns[i]);
					} else {
						sb.append(" o.").append(selectColumns[i]).append(" ,");
					}
				}
				sb.append(" from ").append(entityClass.getSimpleName()).append(" o ");
				if (whereCondition != null)
					sb.append(whereCondition);
				Query query = (Query) session.createQuery(sb.toString());
				query.setFirstResult(firstIndex).setMaxResults(maxResult);
				List<Object[]> querylist = query.list();
				for (Object[] dataObjs : querylist) {
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add((T) obj);
				}
			} else {
				sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
				if (whereCondition != null)
					sb.append(whereCondition);
				Query query = (Query) session.createQuery(sb.toString());
				query.setFirstResult(firstIndex).setMaxResults(maxResult);
				list = query.list();
			}
			return list;
		} catch (Exception e) {
			log.error("分页查询出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 求和 field 开始索引 params where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public T getSum(String field, String whereCondition) {
		String hql = "select sum(o." + field + ") from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			Object obj = this.getCurrentSession().createQuery(hql).list().get(0);
			return (T) obj;
		} catch (Exception e) {
			log.error("统计求和出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询所有的记录
	 */
	@SuppressWarnings("unchecked")
	public List<T> findObjects(String whereCondition) {
		String hql = " from " + entityClass.getSimpleName() + "  o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			List<T> list = this.getCurrentSession().createQuery(hql).list();
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findObjectsByCollections(String whereCondition, Collection collections) {
		String hql = " from " + entityClass.getSimpleName() + "  o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			Query query = this.getCurrentSession().createQuery(hql);
			query.setParameterList("alist", collections);
			List<T> list = query.list();
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询所有的记录
	 */
	public List<T> findObjects(String[] selectColumns, String whereCondition) {
		StringBuffer sb = new StringBuffer();
		Session session = null;
		try {
			List<T> list = new ArrayList<T>();
			session = this.getCurrentSession();
			if (selectColumns != null) {
				sb.append("select ");
				for (int i = 0; i < selectColumns.length; i++) {
					if (i == selectColumns.length - 1) {
						sb.append(" o.").append(selectColumns[i]);
					} else {
						sb.append(" o.").append(selectColumns[i]).append(" ,");
					}
				}
				sb.append(" from ").append(entityClass.getSimpleName()).append(" o ");
				if (whereCondition != null)
					sb.append(whereCondition);
				Query query = (Query) session.createQuery(sb.toString());
				List<T[]> querylist = query.list();
				for (Object[] dataObjs : querylist) {
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add((T) obj);
				}
			} else {
				sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
				if (whereCondition != null)
					sb.append(whereCondition);
				Query query = (Query) session.createQuery(sb.toString());
				list = query.list();
			}
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 创建nativeQuery 查询所有的记录
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByNative(String whereCondition) {
		String hql = "select o from " + entityClass.getSimpleName() + "  o ";
		if (whereCondition != null)
			hql += whereCondition;
		return this.getCurrentSession().createQuery(hql).list();
	}

	/**
	 * 根据一个列的所有数据
	 * 
	 * @author 刘青松
	 */
	@SuppressWarnings("unchecked")
	public List<T> findUnList(String column, String whereCondition) {
		String hql = "select o.'" + column + "' from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			return this.getCurrentSession().createQuery(hql).list();
		} catch (Exception e) {
			log.error("制定列集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据一个列的所有数据，去重复
	 * 
	 * @author 刘青松
	 */
	@SuppressWarnings("unchecked")
	public List<T> findUnDistinctList(String column, String params) {
		String hql = "select distinct o." + column + " from " + entityClass.getSimpleName() + " as o ";
		if (params != null)
			hql += params;
		try {
			return this.getCurrentSession().createQuery(hql).list();
		} catch (Exception e) {
			log.error("去重查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据一个列查询该列最大数据
	 * 
	 * @author 付雄
	 */
	@SuppressWarnings("unchecked")
	public T getMaxData(String column, String whereCondition) {
		String hql = "FROM " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		int pos = hql.toUpperCase().indexOf("ORDER BY");
		String appended = "";
		if (pos != -1) {
			appended = hql.substring(pos + 8, hql.length());
			hql = hql.substring(0, pos);
		}
		if ("".equals(appended)) {
			hql += " ORDER BY (o." + column + ") DESC ";
		} else {
			hql += " ORDER BY (o." + column + ") DESC, " + appended;
		}
		try {
			List<Object> list = this.getCurrentSession().createQuery(hql).list();
			if(list != null && list.size() > 0){
				return (T) list.get(0);
			}
			//Object obj = this.getCurrentSession().createQuery(hql).list().get(0);
			return null;
		} catch (Exception e) {
			log.error("指定列求出最大值出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据一个列查询最大数据，执行SQL查询
	 * 
	 * @author 崔云松
	 */
	public Object getMaxDataSQL(String sql) {
		try {
			Object obj = this.getCurrentSession().createSQLQuery(sql).list().get(0);
			return obj;
		} catch (Exception e) {
			log.error("指定列求出最大值出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取一些数据 column：排重的列 params：where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findProdProgressSame(String column, String whereCondition) {
		String hql = "select distinct o." + column + " ,o.pattern from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			return this.getCurrentSession().createQuery(hql).list();
		} catch (Exception e) {
			log.error("指定列求出最大值出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list<map>集合 自定义Hql 语句，返回结果为list<map>
	 * 
	 * @param Hql
	 * @return
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findListMapByHql(String hql) {
		try {
			List<Map> list = this.getCurrentSession().createQuery(hql)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		} catch (Exception e) {
			log.error("获取list<map>集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> findListMapByHql2(String hql) {
		try {
			List<Map<String, Object>> list = this.getCurrentSession().createQuery(hql)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		} catch (Exception e) {
			log.error("获取list<map>集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> findSomeListMapByHql2(String hql, int firstIndex, int maxResult) {
		try {
			List<Map<String, Object>> list = this.getCurrentSession().createQuery(hql).setFirstResult(firstIndex)
					.setMaxResults(maxResult).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		} catch (Exception e) {
			log.error("获取list<map>集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list<map>集合 分页自定义Hql 语句，返回结果为list<map>
	 * 
	 * @param Hql
	 * @return
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findListMapPage(String hql, int firstIndex, int maxResult) {
		try {
			List<Map> list = this.getCurrentSession().createQuery(hql).setFirstResult(firstIndex)
					.setMaxResults(maxResult).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		} catch (Exception e) {
			log.error("获取list<map>集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list<map>集合 分页自定义SQL 语句，返回结果为list<map>
	 * 
	 * @param sql
	 * @return
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findListMapPageBySql(String sql, int firstIndex, int maxResult) {
		try {
			List<Map<String, Object>> list = this.getCurrentSession().createSQLQuery(sql).setFirstResult(firstIndex)
					.setMaxResults(maxResult).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		} catch (Exception e) {
			log.error("获取list<map>集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list<map>集合 自定义SQL 语句，返回结果为list<map>
	 * 
	 * @param Hql
	 * @return
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findListMapBySql(String sql) {
		try {
			List<Map<String, Object>> list = this.getCurrentSession().createSQLQuery(sql)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		} catch (Exception e) {
			log.error("获取list<map>集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取多表联合查询的数据总数 自定义Hql 语句，返回结果为int总数
	 * 
	 * @param Hql
	 * @return
	 * @author
	 */
	public int getMultilistCount(String hql) {
		try {
			Query query = this.getCurrentSession().createQuery(hql);
			// 执行查询，并返回结果
			if (!query.iterate().hasNext())
				return 0;
			Object object = query.iterate().next();
			if (object == null)
				return 0;
			if (object instanceof Long) {
				return ((Long) object).intValue();
			} else if (object instanceof Integer) {
				return ((Integer) object).intValue();
			} else {
				try {
					throw new Exception("统计HQL存在错误。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取查询的数据总数(多表，单表都可以) 自定义Hql 语句，返回结果为int总数
	 * 
	 * @param Hql
	 * @return
	 * @author
	 */
	public int getMoreTableCount(String hql) {
		try {
			Long a = (Long) this.getCurrentSession().createQuery(hql).uniqueResult();
			return a.intValue();
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通过占位符的方式进行查询一个分页集合
	 * 
	 * @param whereHQL
	 *            条件语句
	 * @param paramsMap
	 *            参数Map
	 * @param sortParam
	 *            排序字段
	 * @return List
	 */
	public List<T> findListByParamsMap(String selectColumns[], int firstIndex, int maxResult, String whereHQL,
			Map<Object, Object> paramsMap, String sortParam) {
		Query query = null;
		Session session = null;
		Object obj = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = this.getCurrentSession();
			List<T> list = new ArrayList<T>();
			if (selectColumns != null) {
				// 1、组装select 指定字段语句片段
				sb.append("select ");
				for (int i = 0; i < selectColumns.length; i++) {
					if (i == selectColumns.length - 1) {
						sb.append(" o.").append(selectColumns[i]);
					} else {
						sb.append(" o.").append(selectColumns[i]).append(" ,");
					}
				}
				// 2、组装from语句片段
				sb.append(" from ").append(entityClass.getSimpleName()).append(" o ");
				// 3、组装where语句片段
				if (whereHQL != null && paramsMap != null) {
					// 4、设置占位符的参数
					Set<Entry<Object, Object>> set = paramsMap.entrySet();
					if (set != null) {
						sb.append(whereHQL);
						if (sortParam != null)
							sb.append(sortParam);
						query = session.createQuery(sb.toString());
						Iterator<Entry<Object, Object>> it = set.iterator();
						while (it.hasNext()) {
							Map.Entry mapentry = it.next();
							query.setParameter(mapentry.getKey().toString(), mapentry.getValue().toString());
						}
					} else {
						if (sortParam != null)
							sb.append(sortParam);
						query = session.createQuery(sb.toString());
					}
				} else {
					if (sortParam != null)
						sb.append(sortParam);
					query = session.createQuery(sb.toString());
				}
				List<T[]> querylist = query.list();
				for (Object[] dataObjs : querylist) {
					Object query_obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add((T) query_obj);
				}
			} else {
				// 1、组装select 字段语句和from语句片段
				sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
				// 2、组装where语句片段
				// 3、设置占位符的参数
				if (whereHQL != null && paramsMap != null) {
					// 4、设置占位符的参数
					Set<Entry<Object, Object>> set = paramsMap.entrySet();
					if (set != null) {
						sb.append(whereHQL);
						if (sortParam != null)
							sb.append(sortParam);
						query = session.createQuery(sb.toString());
						Iterator<Entry<Object, Object>> it = set.iterator();
						while (it.hasNext()) {
							Map.Entry mapentry = it.next();
							query.setParameter(mapentry.getKey().toString(), mapentry.getValue().toString());
						}
					} else {
						if (sortParam != null)
							sb.append(sortParam);
						query = session.createQuery(sb.toString());
					}
				} else {
					if (sortParam != null)
						sb.append(sortParam);
					query = session.createQuery(sb.toString());
				}
				query.setFirstResult(firstIndex).setMaxResults(maxResult);
				list = query.list();
			}
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过占位符的方式进行查询一个对象
	 * 
	 * @param whereHQL
	 *            条件语句
	 * @param paramsMap
	 *            参数Map
	 * @param sortParam
	 *            排序字段
	 * @return T
	 */
	public T findObjectByParamsMap(String selectColumns[], String whereHQL, Map<Object, Object> paramsMap) {
		Query query = null;
		Session session = null;
		Object obj = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = this.getCurrentSession();
			List<T> list = new ArrayList<T>();
			if (selectColumns != null) {
				// 1、组装select 指定字段语句片段
				sb.append("select ");
				for (int i = 0; i < selectColumns.length; i++) {
					if (i == selectColumns.length - 1) {
						sb.append(" o.").append(selectColumns[i]);
					} else {
						sb.append(" o.").append(selectColumns[i]).append(" ,");
					}
				}
				// 2、组装from语句片段
				sb.append(" from ").append(entityClass.getSimpleName()).append(" o ");
				// 3、组装where语句片段
				if (whereHQL != null && paramsMap != null) {
					// 4、设置占位符的参数
					Set<Entry<Object, Object>> set = paramsMap.entrySet();
					if (set != null) {
						sb.append(whereHQL);
						query = session.createQuery(sb.toString());
						Iterator<Entry<Object, Object>> it = set.iterator();
						while (it.hasNext()) {
							Map.Entry mapentry = it.next();
							query.setParameter(mapentry.getKey().toString(), mapentry.getValue().toString());
						}
					} else {
						query = session.createQuery(sb.toString());
					}
				} else {
					query = session.createQuery(sb.toString());
				}
				List<T[]> querylist = query.list();
				for (Object[] dataObjs : querylist) {
					Object query_obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add((T) query_obj);
				}
			} else {
				// 1、组装select 字段语句和from语句片段
				sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
				// 2、组装where语句片段
				// 3、设置占位符的参数
				if (whereHQL != null && paramsMap != null) {
					// 4、设置占位符的参数
					Set<Entry<Object, Object>> set = paramsMap.entrySet();
					if (set != null) {
						sb.append(whereHQL);
						query = session.createQuery(sb.toString());
						Iterator<Entry<Object, Object>> it = set.iterator();
						while (it.hasNext()) {
							Map.Entry mapentry = it.next();
							query.setParameter(mapentry.getKey().toString(), mapentry.getValue().toString());
						}
					} else {
						query = session.createQuery(sb.toString());
					}
				} else {
					query = session.createQuery(sb.toString());
				}
				query.setMaxResults(1);
				list = query.list();
				if (list != null && list.size() > 0) {
					obj = list.get(0);
				}
			}
			return (T) obj;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	  *  @Description  通过主键批量更新field值
	  *  @param fieldKey 待更新field域
	  *  @param fieldValue 待更新field值
	  *  @param pkName 主键名字
	  *  @param values 值的集合,以','分隔
	  *  @return
	  *
	  *  @author  付雄
	  *  @version  1.0
	  *  @date  创建时间：2017年7月5日  下午4:18:34
	 */
	public boolean updateObjectsFieldByPK(String fieldKey,String fieldValue,String pkName,String values){
		String hql = "UPDATE " + entityClass.getSimpleName() + " o SET o." + fieldKey
				+ " = " + fieldValue + " WHERE " + pkName + " IN " + "(" + values + ")";
		try {
			Query query = this.getCurrentSession().createQuery(hql);
			int size = query.executeUpdate();
			if(size > 0){
				return true;
			}
		} catch (Exception e) {
			log.error("通过主键批量更新field值出现异常:" + e);
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 
	  *  @Description  通过主键更新某行数据的指定列的值
	  *  @param input Map类型,key需要修改的字段,value要修改的值
	  *  @param pkName 主键的名字,例如id
	  *  @param pkValue 主键的具体值
	  *  @return
	  *
	  *  @author  付雄
	  *  @version  1.0
	  *  @date  创建时间：2017年7月20日  上午10:20:12
	 */
	public boolean updateFieldsByPK(Map<String, String> input, String pkName, String pkValue){
		if (input.containsKey(null) || input.size() == 0) {
			log.error("传入的map更新field域的域名有空值或者大小为0,请检查!");
			return false;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE " + entityClass.getSimpleName() + " o SET ");
		for (Map.Entry<String, String> entity : input.entrySet()) {
			sb.append("o." + entity.getKey() + " = " + entity.getValue() + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" WHERE " + pkName + "=" + pkValue);
		try {
			Query query = this.getCurrentSession().createQuery(sb.toString());
			int size = query.executeUpdate();
			if(size > 0){
				return true;
			}
		} catch (Exception e) {
			log.error("通过主键更新field值出现异常:" + e);
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public boolean updateFieldsByPK(Map<String, String> input, String whereHql) {
		if (input.containsKey(null) || input.size() == 0) {
			log.error("传入的map更新field域的域名有空值或者大小为0,请检查!");
			return false;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE " + entityClass.getSimpleName() + " o SET ");
		for (Map.Entry<String, String> entity : input.entrySet()) {
			sb.append("o." + entity.getKey() + " = " + entity.getValue() + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		if(null != whereHql && 0 < whereHql.length()){
			sb.append(" "+whereHql);
		}
		try {
			Query query = this.getCurrentSession().createQuery(sb.toString());
			int size = query.executeUpdate();
			if(size > 0){
				return true;
			}
		} catch (Exception e) {
			log.error("通过主键更新field值出现异常:" + e);
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
