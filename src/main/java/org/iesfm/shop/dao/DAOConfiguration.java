package org.iesfm.shop.dao;


import org.iesfm.shop.dao.inmemory.InMemoryArticleDAO;
import org.iesfm.shop.dao.inmemory.InMemoryClientDAO;
import org.iesfm.shop.dao.inmemory.InMemoryOrderDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DAOConfiguration {

    @Bean
    public ArticleDAO articleDAO() {
        return new InMemoryArticleDAO();
    }

    @Bean
    public ClientDAO clientDAO(){
        return new InMemoryClientDAO();
    }

    @Bean
    public OrderDAO orderDAO(){
        return new InMemoryOrderDAO();
    }


}
