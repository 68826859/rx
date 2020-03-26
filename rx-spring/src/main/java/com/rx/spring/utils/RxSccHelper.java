package com.rx.spring.utils;

import com.rx.base.service.BaseService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//@Component
public class RxSccHelper/* implements BeanDefinitionRegistryPostProcessor */{
	private static List<Class<? extends BaseService>> baseClass = new ArrayList<>();

	public static List<Class<? extends BaseService>> getBaseClass() {
		return baseClass;
	}

	//@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	//@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		boolean useDefaultFilters = false;
		String basePackage = "com";
		ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(
				useDefaultFilters);
		beanScanner.addIncludeFilter(new AssignableTypeFilter(BaseService.class));
		Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);
		for (BeanDefinition beanDefinition : beanDefinitions) {
			String beanName = beanDefinition.getBeanClassName();
			try {
				Class<? extends BaseService> clazz = (Class<? extends BaseService>) Class.forName(beanName);
				baseClass.add(clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}