package org.iesfm.shop.controller;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ArticleController {

    private ArticleDAO articleDAO;

    public ArticleController(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles")
    public List<Article> list(@RequestParam(name = "tag") String tag) {
        if (tag == null) {
            return articleDAO.list();
        } else {
            return articleDAO.list(tag);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles/{id}")
    public Article getArticle(@PathVariable("id") int id) {

        Article article = articleDAO.get(id);

        if (article != null) {
            return article;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El articulo no existe");
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/articles")
    public void insert(@RequestBody Article article) {
        if (!articleDAO.insert(article)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El articulo ya existe");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/articles/{id}")
    public void update(@RequestBody Article article) {
        getArticle(article.getId());
        articleDAO.update(article);
    }


}
