
package com.es.domain;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class LogInfoIndexRecreatingEntry {

    /**
     * 索引名称
     */
    @NotBlank(message = "索引名称不能为空")
    private String indexName;
}
