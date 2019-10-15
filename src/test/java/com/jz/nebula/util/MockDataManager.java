package com.jz.nebula.util;

//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//import org.springframework.util.StringUtils;

//import com.jz.nebula.entity.Role;

import com.jz.nebula.entity.User;

public class MockDataManager {

    public static Object getEntityWithFakeData(Class<?> p) {
        String clazzName = p.getName();
        Object resInstance = null;

        if (clazzName == User.class.getName()) {
            resInstance = getUserInstance();
        }

//		Field[] fields = p.getDeclaredFields();
//		System.out.println("******************");
//		for(Field field : fields) {
//			field.setAccessible(true);
//			try {
//				Method method = p.getDeclaredMethod("get" + StringUtils.capitalize(field.getName()));
//				if(method != null) {
//					System.out.println(field.getType());
//				}
//			} catch (NoSuchMethodException | SecurityException e) {
//			}
//		}
//		System.out.println("******************");
        return resInstance;
    }

    private static User getUserInstance() {
        User userInstance = new User();
        return userInstance;
    }

//	private static Role getRoleInstance() {
//		Role roleInstance = new Role();
//		return roleInstance;
//	}

}
