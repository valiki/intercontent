package net.sunnycore.intercontent.util;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class IocUtil {

    public static final String SERVLET_CONTEXT_KEY = "s_c_thrlocal_k";
    
    public static ServletContext getServletContext() {
        return (ServletContext) ThreadLocalStorage.get(SERVLET_CONTEXT_KEY);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object getSpringBean(String name, Class clazz) {
        ServletContext context = getServletContext();
        if (context == null) {
            return null;
        }
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        try {
            return webApplicationContext.getBean(name, clazz);
        } catch (BeansException e) {
            String[] beanNamesForType = webApplicationContext.getBeanNamesForType(clazz);
            if (beanNamesForType != null && beanNamesForType.length > 0) {
                return webApplicationContext.getBean(beanNamesForType[0]);
            }
        }
        return null;
    }
    
}
