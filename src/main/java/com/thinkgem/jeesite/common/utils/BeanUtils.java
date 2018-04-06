package com.thinkgem.jeesite.common.utils;

import java.beans.PropertyDescriptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.UnexpectedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;


/**
 * <p>
 * Bean操作类
 * </p>
 */
public final class BeanUtils {
	
	private static Map cache = new HashMap();

    private BeanUtils() {
    }
    
    private static Map beanCopierMap = new HashMap();
    
	
	static BeanCopier getCopyerByClassName(Class source,Class target,boolean iscovert){
		String clazzName = source.getName()+target.getName();
		BeanCopier temp = (BeanCopier)beanCopierMap.get(clazzName);
		if(temp==null){
			temp = BeanCopier.create(source, target, iscovert);
			beanCopierMap.put(clazzName, temp);
			return temp;
		}
		return temp;
	}
    //update by zhuzf at 2004-9-29
//    static {
        //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空//
//        ConvertUtils.register(new SqlDateConverter(), java.sql.Date.class);
//        ConvertUtils.register(new SqlTimestampConverter(),java.sql.Timestamp.class);
//    }

    /**
     * 对象属性拷贝     * @param source   源对象     * @param target   要拷贝的目标对象
     * 注：如为标准javabean，拷贝性能要优于非标准javabean。 
     */
    public static void copyProperties(Object source, Object target) {
        //update bu zhuzf at 2004-9-29
        try {
          	if(source!=null){
          		//update by 
          		boolean flag = false;
                if(!(source instanceof Map)){
                	flag = true;
                }
                BeanCopier beanCopier = getCopyerByClassName(source.getClass(), target.getClass(),true);
		        if(flag){
		        	beanCopier.copy(source, target, new Converter() {
			            public Object convert(Object value, Class target, Object context) {
			            	if(value == null)
			            		return value;
			            	if(value.getClass().equals(target)){
			            		return value;
			            	}else {
			            		if(target.equals(String.class)){
			            			return String.valueOf(value);
			            		}
			            		if(target.equals(Integer.TYPE)){
			            			return Integer.valueOf(value.toString());
			            		}
			            		if(target.equals(Boolean.TYPE)){
			            			return Boolean.valueOf(value.toString());
			            		}
			            	}
			            	return value;
			            }
			        });
		        }else{
		        	org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
		        }
           	}
        } catch (Exception e) {
        	try {
				org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
    }
    
    /**
     * 将bean对象转化成map
     * @param obj
     * @return
     */
    public static Map describe(Object obj) {
    	
    	Map map = null;
    	try {
			map = org.apache.commons.beanutils.BeanUtils.describe(obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return map;
    }
    
    
    static Map describeConvert(Object obj) {
    	
    	Map map = null;
    	try {
			map = org.apache.commons.beanutils.BeanUtils.describe(obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		getIntegerProperty(obj, map);
		
		return map;
    }
    
    
    private static void getIntegerProperty(Object pValue, Map hashMap){

		Class cls = pValue.getClass();
		Field[] fields = cls.getDeclaredFields();
		Object result = null;
		
		if(fields==null) return ;
		
		for(int i=0; i<fields.length; i++)
		{
			Field field = fields[i];
			String fieldName = field.getName();
			
			String methodName = "get"
				+ (String.valueOf(fieldName.charAt(0))).toUpperCase()
				+ fieldName.substring(1);
			
			Method Meth = null;
			try {
				Meth = cls.getMethod(methodName, null);
				result = Meth.invoke(pValue, null);
				
			} catch (Exception e) {
				continue;
			}
			
			
			if (result instanceof Integer) {
				hashMap.put(fieldName, result);
			}
		}
	}
    
    /**
     * Bean列表拷贝（批量bean拷贝）
     * @param sourceList 被拷贝对象
     * @param sourceClass 被拷贝对象类
     * @param targetList 拷贝目标
     * @param targetClass 拷贝目标类
     * 注：如为标准javabean，拷贝性能要优于非标准javabean。 
     */
    public static void copyProperties(List sourceList, Class sourceClass,
			List targetList, Class targetClass) {

		// 支持对列表的copy

		if (sourceList == null || sourceList.isEmpty())
			return;
		if (targetList == null)
			targetList = new ArrayList();
		// update by 
		boolean flag = false;
		if (!(sourceList.get(0) instanceof Map)) {
			flag = true;
		}
		for (Iterator iter = sourceList.iterator(); iter.hasNext();) {
			Object source = iter.next();
			if (targetClass != null) {
				Object target=null;
				try {
					target = targetClass.newInstance();
				} catch (Exception e1) {
					e1.printStackTrace();
					return;
				} 
				try {
					
					BeanCopier beanCopier = getCopyerByClassName(
							sourceClass == null ? source.getClass(): sourceClass, targetClass, true);
					if (flag) {
						beanCopier.copy(source, target, new Converter() {
							public Object convert(Object value, Class target,Object context) {
								if (value == null)
									return value;
								if (value.getClass().equals(target)) {
									return value;
								} else {
									if (target.equals(String.class)) {
										return String.valueOf(value);
									}
									if (target.equals(Integer.TYPE)) {
										return Integer.valueOf(value.toString());
									}
									if (target.equals(Boolean.TYPE)) {
										return Boolean.valueOf(value.toString());
									}
								}
								return value;
							}
						});
					} else {
						org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
					}
					targetList.add(target);
				} catch (Exception e) {
					try {
						org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
			} else {
				targetList.add(source);
			}
		}

	}
    /**
     * 复制对象控制是否过滤null值
     * @param source
     * @param target
     * @param filterNull
     */
    public static void copyProperties(Object source, Object target, boolean filterNull) {
    	//确认是否过滤null值
    	if(filterNull){
			PropertyDescriptor[] propertyDescriptors=org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(source);
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor=propertyDescriptors[i];
				try{
					Object aValue=org.apache.commons.beanutils.PropertyUtils.getProperty(source, descriptor.getName());
					//值不为null进行copy
					if(aValue!=null){
		    			if(org.apache.commons.beanutils.PropertyUtils.isWriteable(target, descriptor.getName())){
		    				org.apache.commons.beanutils.PropertyUtils.setProperty(target, descriptor.getName(), aValue);
		    			}    			
		    		}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
    	}else{
    		copyProperties(source, target);
    	}
    }
   
//    public static void copyProperties(Object source, Object target, boolean alignBySource) {
//        if ( source == null ){
//        logger.error(BeanUtil.class + ":copyProperties方法出错!" +
//                        "source为空null");
//      }
//
//      if ( target == null ){
//        logger.error(BeanUtil.class + ":copyProperties方法出错!" +
//                        "target为空null");
//      }
//
//      Class alignClass = alignBySource ? source.getClass() : target.getClass();
//      Field[] field = alignClass.getDeclaredFields();
//      for (int i = 0; i < field.length; i++) {
//        try {
//          invokeSetter(target, field[i], invokeGetter(source, field[i]));
//        }
//        catch (Exception e) {
//        }
//      }
//
//    }

    /**
     * 对象浅度克隆
     * @param source 克隆目标
     */
    public static Object clone(Object source) {
        Object target = newInstance(source.getClass());
        copyProperties(source, target);
        return target;
    }

//    /**
//     * 转换request参数为clazz Bean
//     * @param clazz
//     * @param request
//     * @return
//     */
//    public static Object createBean(Class clazz, HttpServletRequest request) {
//        return createBean(clazz, request, false);
//    }

//    /**
//     * 转换request参数为clazz Bean
//     * @param clazz
//     * @param request
//     * @param usePropertyFile 属性对应关系文件
//     * @return
//     */
//    public static Object createBean(Class clazz, HttpServletRequest request, boolean usePropertyFile) {
//        Properties prop = getMappingProperties(clazz, usePropertyFile);
//        Object bean = newInstance(clazz);
//
//        Field[] field = clazz.getDeclaredFields();
//        for (int i = 0; i < field.length; i++) {
//            String parameterName = prop.getProperty(field[i].getName());
//            String parameterValue = request.getParameter(parameterName);
//
//            if (parameterName == null || parameterName.length() == 0 ||
//                    parameterValue == null || parameterValue.length() == 0) {
//                continue;
//            }
//
//            try {
//                Object parameter = createSetterParameter(field[i], request, parameterName);
//                invokeSetter(bean, field[i], parameter);
//            } catch (Exception e) {
//            	e.printStackTrace();
//            }
//        }
//        return bean;
//    }
//
//    private static Object createSetterParameter(Field field, HttpServletRequest request,
//                                                String parameterName) throws Exception {
//        String parameterValue = request.getParameter(parameterName);
//        Object result = null;
//        if (field.getType() == String[].class) {
//            result = request.getParameterValues(parameterName);
//        } else if (field.getType() == String.class) {
//            result = request.getParameter(parameterName);
//        } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
//            boolean booleanValue = (parameterValue != null) ?
//                    parameterValue.equalsIgnoreCase("true") : false;
//            result = new Boolean(booleanValue);
//        } else if (field.getType() == short.class || field.getType() == Short.class) {
//            result = new Short(parameterValue);
//        } else if (field.getType() == int.class || field.getType() == Integer.class) {
//            result = new Integer(parameterValue);
//        } else if (field.getType() == long.class || field.getType() == Long.class) {
//            result = new Long(parameterValue);
//        } else if (field.getType() == float.class || field.getType() == Float.class) {
//            result = new Float(parameterValue);
//        } else if (field.getType() == double.class || field.getType() == Double.class) {
//            result = new Double(parameterValue);
//        } else if (field.getType() == BigInteger.class) {
//            result = new BigInteger(parameterValue);
//        } else if (field.getType() == BigDecimal.class) {
//            result = new BigDecimal(parameterValue);
//        } else if (field.getType() == java.sql.Date.class) {
//            result = DateUtils.parseSQLDate(parameterValue, "yyyy-MM-dd");
//        } else if (field.getType() == java.sql.Time.class) {
//            result = DateUtils.parse(parameterValue, "HH:mm:ss");
//        } else if (field.getType() == java.sql.Timestamp.class) {
//            result = DateUtils.parse(parameterValue, "yyyy-MM-dd HH:mm:ss");
//        } else if (field.getType() == java.util.Date.class) {
//            String pattern = "yyyy-MM-dd";
//            if (DateUtils.isUsePattern(parameterValue, "yyyy-MM-dd HH:mm:ss")) {
//                pattern = "yyyy-MM-dd HH:mm:ss";
//            }
//            result = DateUtils.parse(parameterValue, pattern);
//        } else {
//            throw new IllegalArgumentException("Field " + field.getName() +
//                    " of type " + field.getType() +
//                    " does not be supported now.");
//        }
//        return result;
//    }

    private static Object newInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static Object invokeGetter(Object bean, Field field) throws Exception {
        String methodName = "get" + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
        Method method = bean.getClass().getDeclaredMethod(methodName, new Class[0]);
        return method.invoke(bean, null);
    }

    private static void invokeSetter(Object bean, Field field, Object parameter) throws Exception {
        String methodName = "set" + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
        Class parameterType[] = {field.getType()};
        Method method = bean.getClass().getDeclaredMethod(methodName, parameterType);
        method.invoke(bean, new Object[]{parameter});
    }

    private static Properties getMappingProperties(Class clazz, boolean usePropertyFile) {
        Properties prop = (Properties) cache.get(clazz.getName());
        if (prop == null) {
            prop = new Properties();
            if (usePropertyFile) {
                String shortClassName = clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1);
                String propertyFile = shortClassName + ".properties";
                try {
                    prop.load(clazz.getResourceAsStream(propertyFile));
                } catch (IOException e) {
                    throw new IllegalStateException(e.getMessage());
                }
            } else {
                Field field[] = clazz.getDeclaredFields();
                for (int i = 0; i < field.length; i++) {
                    prop.put(field[i].getName(), field[i].getName());
                }
            }
            cache.put(clazz.getName(), prop);
        }
        return prop;
    }

    static String toString(Object pObj) {
        if (pObj == null) {
            return null;
        }
        Field[] fields = pObj.getClass().getDeclaredFields();
        int count = fields.length;
        String rtn = "";
        Object[] params = {};
        Class[] args = {};
        for (int i = 0; i < count; i++) {
            try {
                Field field = fields[i];
                String fieldName = field.getName();
                String ENTER = "\r\n";
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
                Method method = pObj.getClass().getMethod(methodName, args);
                Object returnValue = method.invoke(pObj, params);
                if (returnValue instanceof java.lang.String) {
                    rtn = rtn + fieldName + " = " + (String) returnValue + ENTER;
                } else if (returnValue instanceof java.lang.Integer) {
                    rtn = rtn + fieldName + " = " + ((Integer) returnValue).toString() + ENTER;
                } else if (returnValue instanceof java.lang.Double) {
                    rtn = rtn + fieldName + " = " + ((Double) returnValue).toString() + ENTER;
                } else if (returnValue instanceof java.lang.Float) {
                    rtn = rtn + fieldName + " = " + ((Float) returnValue).toString() + ENTER;
                } else if (returnValue instanceof java.util.Date) {
                    SimpleDateFormat formatter
                            = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    rtn = rtn + fieldName + " = " + formatter.format((java.util.Date) returnValue) + ENTER;
                } else if (returnValue instanceof java.sql.Date) {
                    SimpleDateFormat formatter
                            = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    rtn = rtn + fieldName + " = " + formatter.format(new java.util.Date(((java.sql.Date) returnValue).getTime())) + ENTER;

                } else if (returnValue instanceof java.sql.Timestamp) {
                    SimpleDateFormat formatter
                            = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    rtn = rtn + fieldName + " = " + formatter.format(new java.util.Date(((java.sql.Date) returnValue).getTime())) + ENTER;

                } else if (returnValue instanceof java.util.Collection) {
                    Collection rv = (Collection) returnValue;
                    Iterator it = rv.iterator();
                    int next = 0;
                    while (it.hasNext()) {
                        rtn = rtn + fieldName + "[" + next + "]  = " + it.next().toString() + ENTER;
                        next++;
                    }
                } else if (returnValue == null) {
                    rtn = rtn + fieldName + " = null " + ENTER;
                } else {
                    rtn = rtn + fieldName + "没有相对应的处理类型" + ENTER;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
       // System.out.println(rtn);
        return rtn;
    }

	
	/**
     * 功能：将mapList中存放的Map中所有的属性值映射到objectClass的对象中去
     */
	public static List getListByMap(List mapList, Class objectClass)
			throws Exception {
		//1、如果mapList为空则返回一个空的List对象。
		if (mapList.isEmpty()) {
			return new ArrayList();
		}
		List result=new ArrayList();
		//遍历mapList
		for (int i = 0; i < mapList.size(); i++) {
			if (!(mapList.get(i) instanceof Map)) {
				throw new Exception("参数List中的数据不是java.util.Map类型的，该方法不支持此种用法。");
			}
			//2.开始映射操作,首先取出objectClass中所有的属性字段。
			//3.将mapList中map对象的值取出，映射到JavaBean中去
			Map map=(Map)mapList.get(i);
			Object o = objectClass.newInstance();
			
			//4.映射属性。
			BeanUtils.populate(o,map);
			result.add(o);
		}
		return result;
	}
	
	
	
	/**
	 * 把Map中的值映射到bean属性中
	 * @param bean
	 * @param map
	 */
	public static void populate(Object bean, Map map){
		//这里只是做了异常的封装，在映射的过程中没有做任何处理
		try{
			org.apache.commons.beanutils.BeanUtils.populate(bean, map);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	

	/**
	 * 功能：设置指定属性
	 * 
	 * @param dest
	 *        目标对象
	 * @param attriname
	 *        属性名称
	 * @param value
	 *        属性的新的值
	 * @throws Exception
	 */
	public static void setProperty(Object dest, String attriname, Object value) {
		try {
			// 参数异常判断 by   2008-1-22
			if (dest == null || attriname == null || "".equals(attriname.trim())) {
				return;
			}

			if (dest instanceof Map) { // MAP类型对象
				Map map = (Map) dest;
				map.put(attriname, value);
			}
			else if (dest instanceof Properties && value instanceof String) {// Properties类型对象
				Properties prop = (Properties) dest;
				String v = (String) value;
				prop.setProperty(attriname, v);
			}
			else {
				org.apache.commons.beanutils.BeanUtils.setProperty(dest, attriname, value);
			}
		}
		catch (Exception ex) {
			throw new RuntimeException("设置指定属性失败。", ex);
		}
	}

	/**
	 * 获取对象属性值 处理三种类型，Map、Properties、和一般的JavaBean
	 * 
	 * @param attriname
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static Object getProperty(Object object, String attriname)
			throws Exception {
		try {
			// 参数异常判断   2008-1-22
			if (object == null || attriname == null || "".equals(attriname.trim())) {
				return null;
			}

			// 返回值
			String ro;

			if (object instanceof Map) {// MAP类型对象
				Map map = (Map) object;
				ro = (String) map.get(attriname);
			}
			else if (object instanceof Properties) {// Properties类型对象
				Properties prop = (Properties) object;
				ro = prop.getProperty(attriname);
			}
			else {// 其他JavaBean对象
				ro = org.apache.commons.beanutils.BeanUtils.getProperty(object, attriname);
			}

			return ro;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("获取源对象属性值失败", ex);
		}
	}
}

