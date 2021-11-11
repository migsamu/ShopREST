package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;


public class JDBCArticlesDAO implements ArticleDAO {

    private static final String SELECT_ARTICLES = "SELECT * FROM Article ";
    private static final String SELECT_ARTICLES_BY_ARTICLES_ID = "SELECT name FROM Tag where article_id = :article_id ";

    private final RowMapper<Article> ARTICLE_ROW_MAPPER =
            ((rs, rowNum) -> new Article(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    tagsByArticleId(rs.getInt("id"))
            )
            );

    protected NamedParameterJdbcTemplate jdbc;


    public JDBCArticlesDAO(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Article> list() {

        return jdbc.query(SELECT_ARTICLES, ARTICLE_ROW_MAPPER);
    }

    private Set<String> tagsByArticleId(int articleId) {

        Map<String, Object> params = new HashMap<>();
        params.put("article_id", articleId);

        return new HashSet<>(jdbc.query(SELECT_ARTICLES_BY_ARTICLES_ID, params, (rs, n) ->
                rs.getString("name")));

    }

    @Override
    public List<Article> list(String tag) {
        return null;
    }

    @Override
    public Article get(int id) {
        return null;
    }

    @Override
    public boolean insert(Article article) {
        return false;
    }

    @Override
    public boolean update(Article article) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
