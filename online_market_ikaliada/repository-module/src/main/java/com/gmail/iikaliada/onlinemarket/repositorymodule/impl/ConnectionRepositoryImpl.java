package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ConnectionRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.exception.IllegalDatabaseStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionRepositoryImpl implements ConnectionRepository {
    private static Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalDatabaseStateException("Database exception during getting connection");
        }
    }
}
