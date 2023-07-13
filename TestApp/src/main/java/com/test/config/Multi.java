package com.test.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Multi {
    DataSourceProperties test1;
    DataSource test2;
}
