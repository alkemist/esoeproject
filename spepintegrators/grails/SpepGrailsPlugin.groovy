import grails.spep.SpepUser
import grails.util.Environment
import org.springframework.aop.scope.ScopedProxyFactoryBean

class SpepGrailsPlugin {
	def version = 0.3
	def dependsOn = [:]

	def author = "Shaun Mangelsdorf"
	def authorEmail = "s.mangelsdorf@gmail.com"
	def title = "Integrates the SPEP filter with a Grails application"
	def description = '''\
Integrates the SPEP filter with a Grails application.
'''

	def documentation = "http://esoeproject.org/wiki/esoe/SPEP_Grails_Plugin"

	def pluginExcludes = [
		'grails-app/conf/hibernate/*',
		'grails-app/conf/spring/*',
		'grails-app/conf/DataSource.groovy',
		'grails-app/conf/UrlMappings.groovy',
		'grails-app/i18n/*',
		'test/**/*',
		'web-app/**/*'
	]

	
	def doWithSpring = {
		if (application.config.spep.enabled) {
			
			log.debug("SPEP: configuring user bean with name ${application.config.spep.beanName} of class ${application.config.spep.userClass.name}") 
			
			spepUserSessionBean(application.config.spep.userClass) {
				it.scope = "session"
				it.autowire = "byName"
			}

			"${application.config.spep.beanName}"(ScopedProxyFactoryBean) {
				targetBeanName = "spepUserSessionBean"
				proxyTargetClass = false
			}
		} else {
			log.info("SPEP: enabled is false")
		}
	}
   
	def doWithWebDescriptor = { xml ->
		if (application.config.spep.enabled) {
			xml.'filter' + {
				'filter' {
					'filter-class'("com.qut.middleware.spep.filter.SPEPFilter")
					'filter-name'("spep-grails-plugin-filter")
					'init-param' {
						'param-name'("spep-context")
						'param-value'("/spep")
					}
				}
			}
			xml.'filter-mapping' + {
				'filter-mapping' {
					'filter-name'("spep-grails-plugin-filter")
					'url-pattern'("/*")
				}
			}
		}
	}
}
