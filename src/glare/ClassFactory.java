package glare;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassFactory 
{
	public static Object getBeanByName(String beanName)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("glare/Beans.xml");
		BeanFactory factory        = context;

		return factory.getBean(beanName);
	}
}
