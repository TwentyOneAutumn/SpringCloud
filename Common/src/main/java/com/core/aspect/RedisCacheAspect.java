package com.core.aspect;

//import com.demo.Core.Interface.RedisCache;
import com.core.utils.RedisUtils;
        import org.aspectj.lang.annotation.Aspect;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Redis缓存切面类
 */
@Aspect
@Component
public class RedisCacheAspect {

    @Autowired
    RedisUtils redisUtils;

    /**
     * 切面配置，匹配所有被 @MyAnnotation 标注的方法
     */
//    @Pointcut("@annotation(com.demo.Core.Interface.RedisCache)")
//    public void RedisCachePointcut() {}

    /**
     * 后置增强
     */
//    @AfterReturning(value = "RedisCachePointcut()", returning = "returnData")
//    public void RedisCache(JoinPoint joinPoint, Object returnData){
//        // 获取切点方法对象
//        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//        // 获取方法对象
//        Method method = methodSignature.getMethod();
//        // 获取注解
//        RedisCache annotation = method.getAnnotation(RedisCache.class);
//        // 获取值
//        String value = annotation.value();
//        // 判断值是否合法
//        if(Arrays.asList(RedisTableName.TABLE_NAME_ARR).contains(value)){
//            // 清空缓存
//            redisUtils.refreshTableCache(value);
//        }else {
//            throw new RuntimeException("RedisCache注解Value异常");
//        }
//    }
}