package cn.xiaoyu.parser;

import cn.xiaoyu.domain.Student;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Roin zhang
 * @date 2018/10/18
 */
public class PersonBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    private final Class<?> beanClass;
    private final boolean required;


    public PersonBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    @Override
    protected Class getBeanClass(Element element) {
        return Student.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        //通过配置文件获取相应的值，设置到bean的属性中
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        String age = element.getAttribute("age");
        String address = element.getAttribute("address");
        String phone = element.getAttribute("phone");
        if (StringUtils.hasText(id)) {
            builder.addPropertyValue("id", id);
        }
        if (StringUtils.hasText(name)) {
            builder.addPropertyValue("name", name);
        }
        if (StringUtils.hasText(age)) {
            builder.addPropertyValue("age", age);
        }
        if (StringUtils.hasText(address)) {
            builder.addPropertyValue("address", address);
        }
        if (StringUtils.hasText(phone)) {
            builder.addPropertyValue("phone", phone);
        }
    }
}
