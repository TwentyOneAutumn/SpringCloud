package com.security.authentication.beans;

import cn.hutool.core.collection.CollUtil;
import com.security.authentication.Interface.AuthenticationAware;
import com.security.authentication.Interface.AuthenticationPostProcessor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import java.util.List;

/**
 * 默认权限校验增强器
 */
@Data
public class PermissionPostProcessor implements AuthenticationPostProcessor {

    /**
     * 权限Aware接口集合
     */
    private List<AuthenticationAware> awareList;

    /**
     * 重写 {@link AuthenticationPostProcessor#postProcess(Authentication)} 方法
     * 会自动调用所有的 {@link AuthenticationAware#verify(Authentication)} 方法对权限进行校验
     * @param authentication {@link Authentication} 权限对象
     * @throws PermissionVerifyException 权限验证异常
     */
    @Override
    public void postProcess(Authentication authentication) throws PermissionVerifyException {
        // 对aware实现集合进行判空
        if(CollUtil.isEmpty(awareList)){
            throw new PermissionVerifyException("The required AuthenticationAware Bean is undefined.");
        }
        // 循环调用所有aware实现的verify()方法进行验证
        awareList.forEach(aware -> aware.verify(authentication));
    }
}
