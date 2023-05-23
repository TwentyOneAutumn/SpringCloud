package com.security.config;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

public class RedisClientDetailsService extends JdbcClientDetailsService {
    public RedisClientDetailsService(DataSource dataSource) {
        super(dataSource);
        super.setSelectClientDetailsSql("");
        super.setFindClientDetailsSql("");
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }
}
