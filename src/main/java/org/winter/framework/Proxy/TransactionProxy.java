package org.winter.framework.Proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.winter.framework.annotation.Transaction;
import org.winter.framework.helper.DatabaseHelper;

/**
 * @author wang
 * @Description
 * @Date 2020/12/31
 */
public class TransactionProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = ThreadLocal.withInitial(() -> false);

    @Override
    public Object doProxy(ProxyChain chain) throws Throwable {
        Object result;
        // 保证当前线程第一次执行到此处时开启事务
        boolean flag = FLAG_HOLDER.get();
        Method method = chain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                LOGGER.debug("begin transaction");
                result = chain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("rollback transaction");
                throw e;
            } finally {
                // 当前线程执行完事务后移除标志，当该线程执行下一个事务时又可以开启事务
                FLAG_HOLDER.remove();
            }
        } else {
            result = chain.doProxyChain();
        }
        return result;
    }
}
