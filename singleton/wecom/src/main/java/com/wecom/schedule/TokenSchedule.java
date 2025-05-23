package com.wecom.schedule;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.wecom.config.WeComConfig;
import com.wecom.config.RequestBuild;
import com.wecom.domain.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class TokenSchedule {

    /**
     * 定时刷新Token
     * 执行频率:每90分钟
     */
    @Scheduled(fixedRate = 110 * 60 * 1000)
    public void refreshToken(){
        try {
            TokenInfo pojo = RequestBuild.post(WeComConfig.WECOM_TOKEN_INFO, WeComConfig.TOKEN_PARAMS, TokenInfo.class);
            if(BeanUtil.isNotEmpty(pojo)){
                if(StrUtil.isNotBlank(pojo.getErrcode())){
                    throw new RuntimeException(pojo.getErrmsg());
                }else {
                    WeComConfig.TOKEN = pojo.getSuite_access_token();
                }
            }
        } catch (Exception exception){
            log.error("刷新Token异常:{}}",exception.getMessage());
        }
    }
}
