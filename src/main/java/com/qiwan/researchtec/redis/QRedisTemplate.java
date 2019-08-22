//package com.qiwan.researchtec.redis;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.qiwan.researchtec.utils.ArrayUtils;
//
//import redis.clients.jedis.JedisCluster;
//
///**
// * <br>类 名: QRedisTemplate
// * <br>描 述: redis集群模版类
// * <br>作 者: wangkefengname@163.com
// * <br>创 建: 2018年3月15日 下午1:52:47
// * <br>版 本: v1.0.0
// */
//@Component
//public class QRedisTemplate {
//	
//	public static final Logger LOG = LoggerFactory.getLogger(QRedisTemplate.class);;
//
//	@Autowired
//	private JedisCluster cluster;
//
//	@Value("${spring.redis.lifecycle:-1}")
//	private int lifecycle;
//
//	public final static String CACHE_NAMESPACE = "_cache_defualt_";
//	public final static String KEY_LIST_NAMESPACE = "_list_namespace_";
//
//	/**
//	 * @Description:在指定缓存空间内加入或更新缓存信息
//	 * @param namespace
//	 * @param key
//	 * @param value
//	 * @param seconds 有效时长
//	 */
//	public void put(String namespace, String key, Object value, int seconds) {
//		LOG.info("SET缓存数据", "空间名称：" + namespace + "，KEY：" + key + "，value：" + value);
//		if (StringUtils.isEmpty(namespace)) {
//			namespace = CACHE_NAMESPACE;
//		}
//		List<String> namespaceList = getNamespaces();
//		if (ArrayUtils.isEmpty(namespaceList) || !namespaceList.contains(namespace)) {
//			createCache(namespace);
//		}
//		if (value == null) {
//			remove(namespace, key);
//			return;
//		}
//		String className = value.getClass().getName();
//		JSONObject json = new JSONObject();
//		json.put("class", className);
//		json.put("data", JSON.toJSONString(value));
//		cluster.hset(namespace, key, json.toJSONString());
//		if (-1 == seconds) {
//			cluster.persist(namespace);
//		} else {
//			cluster.expire(namespace, seconds);
//		}
//	}
//
//	/**
//	 * @Description:在指定缓存空间内加入或更新缓存信息
//	 * @param namespace
//	 * @param key
//	 * @param value
//	 */
//	public void put(String namespace, String key, Object value) {
//		put(namespace, key, value, lifecycle);
//	}
//
//	/**
//	 * @Description:在默认缓存空间内加入和更新缓存信息
//	 * @param key
//	 * @param value
//	 */
//	public void put(String key, Object value) {
//		put(CACHE_NAMESPACE, key, value);
//	}
//
//	/**
//	 * @Description:获取缓存信息
//	 * @param namespace
//	 * @param key
//	 */
//	@SuppressWarnings("unchecked")
//	public <T> T get(String namespace, String key) {
//		LOG.info("GET缓存数据，空间名称：{}，key:{}", namespace, key);
//		if (StringUtils.isEmpty(namespace)) {
//			namespace = CACHE_NAMESPACE;
//		}
//		if (StringUtils.isEmpty(key)) {
//			return null;
//		}
//		String value = cluster.hget(namespace, key);
//		if (StringUtils.isEmpty(value)) {
//			return null;
//		}
//		JSONObject json = JSON.parseObject(value);
//		String className = json.getString("class");
//		if (StringUtils.isEmpty(className)) {
//			return null;
//		}
//		Class<T> clazz;
//		try {
//			clazz = (Class<T>) Class.forName(className);
//			T rs = (T) JSON.parseObject(json.getString("data"), clazz);
//			return rs;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	/**
//	 * @Description:在默认缓存空间内获取缓存信息
//	 * 	如果信息不存在这遍历其他缓存空间
//	 * 	如果其他缓存空间存在多个相同key的缓存信息，将返回默认第一个找到的信息，这种情况建议使用 {@link QRedisTemplate#get(String, String)}
//	 * @param key
//	 * @return
//	 */
//	public <T> T get(String key) {
//		return get(CACHE_NAMESPACE, key);
//	}
//
//	/**
//	 * @Description:在指定空间内删除缓存信息
//	 * @param namespace
//	 * @param key
//	 */
//	public void remove(String namespace, String key) {
//		LOG.info("REMOVE缓存数据，空间名称：{},key:{}", namespace, key);
//		if (StringUtils.isEmpty(namespace)) {
//			namespace = CACHE_NAMESPACE;
//		}
//		cluster.hdel(namespace, key);
//	}
//
//	/**
//	 * @Description:在所有缓存空间删除缓存信息
//	 * @deprecated {@link QRedisTemplate#remove(String, String)}
//	 * @param key
//	 */
//	public void remove(String key) {
//		remove(CACHE_NAMESPACE, key);
//	}
//
//	/**
//	 * @Description:获取全部缓存空间
//	 * @return
//	 */
//	public List<String> getNamespaces() {
//		LOG.info("GET缓存空间LIST");
//		long count = cluster.llen(KEY_LIST_NAMESPACE);
//		if (count <= 0) {
//			return null;
//		}
//		return cluster.lrange(KEY_LIST_NAMESPACE, 0, count);
//	}
//
//	/**
//	 * @Description:获取空间内缓存键值
//	 * @param namespace
//	 * @return
//	 */
//	public List<String> getKeys(String namespace) {
//		LOG.info("GET缓存空间KEYS，空间名称:{}", namespace);
//		Set<String> set = cluster.hkeys(namespace);
//		if (!ArrayUtils.isEmpty(set)) {
//			return new ArrayList<>(set);
//		}
//		return null;
//	}
//
//	/**
//	 * @Description:创建缓存空间
//	 * @param namespace
//	 */
//	public void createCache(String namespace) {
//		LOG.info("CREATE空间，创建空间名称：{}", namespace);
//		cluster.lpush(KEY_LIST_NAMESPACE, namespace);
//	}
//
//	/**
//	 * @Description:删除缓存空间内的数据
//	 * @param namespace
//	 */
//	public void removeCache(String namespace) {
//		LOG.info("REMOVE空间数据，空间名称：{}", namespace);
//		cluster.del(namespace);
//	}
//
//	/**
//	 * @Description:清空缓存空间以及空间内的缓存信息
//	 * @param namespace
//	 */
//	public void clearNamespace(String namespace) {
//		LOG.info("REMOVE空间，空间名称：{}", namespace);
//		removeCache(namespace);// 删除缓存
//		cluster.lrem(KEY_LIST_NAMESPACE, 0, namespace);// 删除空间
//	}
//
//	/**
//	 * @Description:清空所有缓存空间内全部信息
//	 */
//	public void clear() {
//		LOG.info("清空缓存数据库");
//		List<String> namespaces = getNamespaces();
//		for (String key : namespaces) {
//			clearNamespace(key);
//		}
//	}
//}
