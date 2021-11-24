package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;


public class JDBCArticlesDAO implements ArticleDAO {

    private static final String INSERT_ARTICLE = "INSERT INTO Article (id, name, price) " +
            "VALUES (:id, :name, :price)";
    private static final String SELECT_ARTICLES = "SELECT * FROM Article ";
    private static final String SELECT_ARTICLE_BY_ID = "SELECT * FROM Article WHERE id=:id";
    private static final String SELECT_ARTICLES_BY_ARTICLES_ID = "SELECT name FROM Tag where article_id = :article_id ";
    private static final String SELECT_ARTICLES_BY_TAG =
            "SELECT article_id FROM Tag WHERE name = :tag";

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

        return new HashSet<String>(jdbc.query(
                SELECT_ARTICLES_BY_ARTICLES_ID,
                params,
                (rs, n) ->
                        rs.getString("name")
        ));

    }

    private List<Integer> articlesByTag(String tag) {
        Map<String, Object> params = new HashMap<>();
        params.put("tag", tag);

        return jdbc.query(
                SELECT_ARTICLES_BY_TAG,
                params,
                (rs, n) ->
                        rs.getInt("article_id"));
    }

    @Override
    public List<Article> list(String tag) {
        Map<String, Object> params = new HashMap<>();
        List<Article> articles = new ArrayList<>();
        for (int id : articlesByTag(tag)) {
            params.put("id", id);
            jdbc.query(
                    SELECT_ARTICLES,
                    params,
                    ARTICLE_ROW_MAPPER);
        }
        return articles;
    }

    @Override
    public Article get(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return jdbc.queryForObject(SELECT_ARTICLE_BY_ID, params, ARTICLE_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
