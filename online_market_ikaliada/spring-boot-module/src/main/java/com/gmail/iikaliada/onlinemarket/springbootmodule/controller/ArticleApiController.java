package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ArticleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForNewsDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.ArticleNotFoundException;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArticleApiController {

    private final ArticleService articleService;

    @Autowired
    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<ArticleDTO> getArticles() {
        return articleService.getArticles();
    }

    @PostMapping("/articles/article")
    public ResponseEntity<String> addArticle(@Valid @RequestBody ArticleForNewsDTO articleForNewsDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.toString(),
                    "Seems like you entered not correct values. Try again!");
        }
        articleService.add(articleForNewsDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/articles/article/{id}")
    public ArticleForPageDTO getArticleById(@PathVariable("id") Long id) {
        try {
            return articleService.getArticleById(id);
        } catch (Exception e) {
            throw new ArticleNotFoundException(id);
        }
    }

    @DeleteMapping("/articles/article/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") Long id) {
        try {
            articleService.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST.toString(), "No article found with this id");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
