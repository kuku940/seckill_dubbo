package cn.xiaoyu.namespace;

import cn.xiaoyu.domain.Student;
import cn.xiaoyu.parser.PersonBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Roin zhang
 * @date 2018/10/18
 */
public class PersonNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        // 实现init方法，解析person标签
        registerBeanDefinitionParser("person", new PersonBeanDefinitionParser(Student.class, true));
    }
}
