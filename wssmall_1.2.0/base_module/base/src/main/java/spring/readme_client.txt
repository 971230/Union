
1.client使用配置
    <context:component-scan base-package="zte.iservice.impl" />
    <bean id="apiContextHolder" class="com.ztesoft.api.spring.ApiContextHolder" lazy-init="false"/>


2.配置各种服务的dubbo配置
例：
  <dubbo:reference interface="zte.iservice.ICartServices" id="cartServices"/>


3.rop配置只需第二步





