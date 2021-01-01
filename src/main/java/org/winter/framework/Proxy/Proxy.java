package org.winter.framework.Proxy;

/**
 * @author wang
 * @Description
 * @Date 2020/12/31
 */
public interface Proxy {

    Object doProxy(ProxyChain chain) throws Throwable;
}
