package com.moxo.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbUtils {

	public Connection getConn() {
		InputStream in = DbUtils.class.getClassLoader().getResourceAsStream("config.properties");
		Properties properties = new Properties();
		Connection conn = null;

		try {
			properties.load(in);
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(properties.getProperty("jdbc.url"),
					properties.getProperty("jdbc.username"), properties.getProperty("jdbc.pwd"));
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("配置文件出错！");
			e.printStackTrace();
		}
		return conn;
	}

	/*
	 * 将rs结果转换成对象列表
	 * 
	 * @param rs jdbc结果集
	 * 
	 * @param clazz 对象的映射类 return 封装了对象的结果列表
	 */
	public static List populate(ResultSet rs, Class clazz)
			throws SQLException, InstantiationException, IllegalAccessException {
		// 结果集的元素对象
		ResultSetMetaData rsmd = rs.getMetaData();
		// 获取结果集的元素个数
		int colCount = rsmd.getColumnCount();
		// 返回结果的列表集合
		List list = new ArrayList();
		// 业务对象的属性数组
		Field[] fields = clazz.getDeclaredFields();
		while (rs.next()) {// 对每一条记录进行操作
			Object obj = clazz.newInstance();// 构造业务对象实体
			// 将每一个字段取出进行赋值
			for (int i = 1; i <= colCount; i++) {
				Object value = rs.getObject(i);
				// 寻找该列对应的对象属性
				for (int j = 0; j < fields.length; j++) {
					Field f = fields[j];
					// 如果匹配进行赋值
					if (f.getName().equalsIgnoreCase(rsmd.getColumnName(i))) {
						boolean flag = f.isAccessible();
						f.setAccessible(true);
						f.set(obj, value);
						f.setAccessible(flag);
					}
				}
			}
			list.add(obj);
		}
		return list;
	}
}
