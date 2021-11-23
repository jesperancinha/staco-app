# StaCo App Curious resolved errors

#### 1. Duplicated values

```shell
java.lang.IllegalArgumentException: Duplicate property values for [endpoint_url].
	at com.amazonaws.auth.profile.internal.BasicProfileConfigLoader$ProfilesConfigFileLoaderHelper.onProfileProperty(BasicProfileConfigLoader.java:169) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.internal.AbstractProfilesConfigFileScanner.run(AbstractProfilesConfigFileScanner.java:126) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.internal.BasicProfileConfigLoader$ProfilesConfigFileLoaderHelper.parseProfileProperties(BasicProfileConfigLoader.java:141) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.internal.BasicProfileConfigLoader.loadProfiles(BasicProfileConfigLoader.java:86) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.internal.BasicProfileConfigLoader.loadProfiles(BasicProfileConfigLoader.java:63) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.ProfilesConfigFile.loadProfiles(ProfilesConfigFile.java:204) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.ProfilesConfigFile.<init>(ProfilesConfigFile.java:145) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.ProfilesConfigFile.<init>(ProfilesConfigFile.java:133) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.internal.BasicProfileConfigFileLoader.getProfilesConfigFile(BasicProfileConfigFileLoader.java:71) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.auth.profile.internal.BasicProfileConfigFileLoader.getProfile(BasicProfileConfigFileLoader.java:55) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.retry.internal.RetryModeResolver.profile(RetryModeResolver.java:92) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.retry.internal.RetryModeResolver.resolveRetryMode(RetryModeResolver.java:83) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.retry.internal.RetryModeResolver.<init>(RetryModeResolver.java:46) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.retry.RetryPolicy.<clinit>(RetryPolicy.java:35) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.retry.PredefinedRetryPolicies.<clinit>(PredefinedRetryPolicies.java:30) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.ClientConfiguration.<clinit>(ClientConfiguration.java:89) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.ClientConfigurationFactory.getDefaultConfig(ClientConfigurationFactory.java:46) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientConfigurationFactory.getDefaultConfig(AmazonDynamoDBClientConfigurationFactory.java:31) ~[aws-java-sdk-dynamodb-1.12.113.jar:na]
	at com.amazonaws.ClientConfigurationFactory.getConfig(ClientConfigurationFactory.java:36) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.client.builder.AwsClientBuilder.resolveClientConfiguration(AwsClientBuilder.java:169) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.client.builder.AwsClientBuilder.access$000(AwsClientBuilder.java:54) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.client.builder.AwsClientBuilder$SyncBuilderParams.<init>(AwsClientBuilder.java:505) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.client.builder.AwsAsyncClientBuilder$AsyncBuilderParams.<init>(AwsAsyncClientBuilder.java:100) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.client.builder.AwsAsyncClientBuilder.getAsyncClientParams(AwsAsyncClientBuilder.java:90) ~[aws-java-sdk-core-1.11.951.jar:na]
	at com.amazonaws.client.builder.AwsAsyncClientBuilder.build(AwsAsyncClientBuilder.java:80) ~[aws-java-sdk-core-1.11.951.jar:na]
	at org.jesperancinha.enterprise.staco.ls.config.LsStaCoConfiguration.dynamoDbMapperAsync(Configuration.kt:53) ~[classes/:na]
	at org.jesperancinha.enterprise.staco.ls.config.LsStaCoConfiguration$$EnhancerBySpringCGLIB$$bb6934cd.CGLIB$dynamoDbMapperAsync$3(<generated>) ~[classes/:na]
	at org.jesperancinha.enterprise.staco.ls.config.LsStaCoConfiguration$$EnhancerBySpringCGLIB$$bb6934cd$$FastClassBySpringCGLIB$$345657a2.invoke(<generated>) ~[classes/:na]
	at org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:244) ~[spring-core-5.3.13.jar:5.3.13]
	at org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:331) ~[spring-context-5.3.13.jar:5.3.13]
	at org.jesperancinha.enterprise.staco.ls.config.LsStaCoConfiguration$$EnhancerBySpringCGLIB$$bb6934cd.dynamoDbMapperAsync(<generated>) ~[classes/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
	at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:653) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:638) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1352) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1195) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1380) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1300) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:944) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918) ~[spring-context-5.3.13.jar:5.3.13]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583) ~[spring-context-5.3.13.jar:5.3.13]
	at org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext.refresh(ReactiveWebServerApplicationContext.java:64) ~[spring-boot-2.6.0.jar:2.6.0]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:730) ~[spring-boot-2.6.0.jar:2.6.0]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:412) ~[spring-boot-2.6.0.jar:2.6.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:302) ~[spring-boot-2.6.0.jar:2.6.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1301) ~[spring-boot-2.6.0.jar:2.6.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1290) ~[spring-boot-2.6.0.jar:2.6.0]
	at org.jesperancinha.enterprise.staco.StampsAndCoinsLocalStackLauncherKt.main(StampsAndCoinsLocalStackLauncher.kt:78) ~[classes/:na]

2021-11-23 18:41:07.384  WARN 1639 --- [           main] s.a.a.p.internal.ProfileFileReader       : Ignoring profile 'plugins' on line 30 because it did not start with 'profile ' and it was not 'default'.
2021-11-23 18:41:08.031  INFO 1639 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint(s) beneath base path '/actuator'
2021-11-23 18:41:08.456  INFO 1639 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-11-23 18:41:08.491  INFO 1639 --- [           main] j.e.s.StampsAndCoinsLocalStackLauncherKt : Started StampsAndCoinsLocalStackLauncherKt in 16.346 seconds (JVM running for 22.108)
2021-11-23 18:41:08.603  INFO 1639 --- [           main] o.j.e.s.StampsAndCoinsLocalStackLauncher : Starting application with postgress user postgres and password admin. Shhhh! Do not tell this to anyone! It comes from Localstack!
Disconnected from the target VM, address: '127.0.0.1:57546', transport: 'socket'

Process finished with exit code 137 (interrupted by signal 9: SIGKILL)
```

The above error may happen if you `~/.aws/config` file contains misconfigurations. In my case, this was:

```properties
# [profile user1]
# aws_access_key_id = [accessKey1]
# aws_secret_access_key = [secretKey1]
region = eu-central-1
output = json
[plugins]
endpoint = awscli_plugin_endpoint
[profile local]
dynamodb =
    endpoint_url = http://localhost:4566
stack =
    endpoint_url = http://localhost:4566
s3 =
    endpoint_url = http://localhost:4566
```