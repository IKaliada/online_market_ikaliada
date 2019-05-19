package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ArticleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArticleApiController {

    private ArticleService articleService;

    @Autowired
    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<ArticleDTO> getArticles(@RequestParam(value = "page", defaultValue = "1") Integer pageSize) {
        return articleService.getArticle(pageSize);
    }

    @PostMapping("/articles/article")
    public ResponseEntity<String> addArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.add(articleDTO);
        return new ResponseEntity<>(articleDTO.toString(), HttpStatus.OK);
    }

    @GetMapping("/articles/article/{id}")
    public ResponseEntity<String> addArticle(@PathVariable("id") Long id) {
        articleService.getArticleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/articles/article/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
