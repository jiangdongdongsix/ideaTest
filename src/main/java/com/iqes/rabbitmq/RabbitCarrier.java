package com.iqes.rabbitmq;

/**
 * @author 54312
 * cloud端请求命令的载体
 */
public class RabbitCarrier {

    private String serviceName;
    private String methodName;
    private String parameter;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "RabbitCarrier{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameter='" + parameter + '\'' +
                '}';
    }
}
