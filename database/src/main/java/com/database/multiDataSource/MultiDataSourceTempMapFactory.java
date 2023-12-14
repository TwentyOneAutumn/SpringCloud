package com.database.multiDataSource;

import lombok.Data;

import java.util.Map;

@Data
public class MultiDataSourceTempMapFactory {

    private Map<String,DataSourceTemplate> dataSourceMap;
}
