package com.hpugs.commons.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 类型转换工具类
 * @author 唐鹏鹏
 * @version 1.0
 * @date 创建时间：2017年6月21日 上午11:32:16
 */
public class ClassCastUtil {

	/**
	 * 
	 * @Description 实体类转换Map
	 * @param obj
	 * @return
	 * @throws IOException
	 *
	 * @author 唐鹏鹏
	 * @version 1.0
	 * @date 创建时间：2017年6月21日 上午11:32:45
	 */
	public static Map bean2Map(Object obj) throws IOException {
		Map map = new HashMap();  
	    Class c;  
	    try  
	    {  
	      c = Class.forName(obj.getClass().getName());  
	      Method[] m = c.getMethods();  
	      for (int i = 0; i < m.length; i++)  
	      {  
	        String method = m[i].getName();  
	        if (method.startsWith("get"))  
	        {  
	          try{  
	          Object value = m[i].invoke(obj);  
	          if (value != null)  
	          {  
	            String key=method.substring(3);  
	            key=key.substring(0,1).toLowerCase()+key.substring(1);
	            map.put(key, value);  
	          }  
	          }catch (Exception e) {  
	            System.out.println("error:"+method);  
	          }  
	        }  
	      }  
	    }  
	    catch (Exception e)  
	    {  
	      e.printStackTrace();  
	    }  
	    return map;
	}
}
