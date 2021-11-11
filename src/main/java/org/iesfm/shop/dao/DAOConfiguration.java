package org.iesfm.shop.dao;


import org.iesfm.shop.dao.inmemory.InMemoryArticleDAO;
import org.iesfm.shop.dao.inmemory.InMemoryClientDAO;
import org.iesfm.shop.dao.inmemory.InMemoryOrderDAO;
import org.iesfm.shop.dao.jdbc.JDBCArticlesDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("application.properties")
public class DAOConfiguration {

    @Bean
    public ArticleDAO articleDAO(NamedParameterJdbcTemplate jdbc) {
        return new JDBCArticlesDAO(jdbc);
    }

    @Bean
    public ClientDAO clientDAO() {
        return new InMemoryClientDAO();
    }

    @Bean
    public OrderDAO orderDAO() {
        return new InMemoryOrderDAO();
    }

    @Bean
    public NamedParameterJdbcTemplate jdbc(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource(

            @Value("${database.url}") String url,
            @Value("${database.user}") String user,
            @Value("${database.password}") String password) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        return dataSource;
    }


}
