package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ArticleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleController {

    @Value("${content.not.found.message}")
    String notFoundMessage;

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String getArticles(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        model.addAttribute("currentPage", currentPage);
        List<ArticleDTO> articles = articleService.getArticle(currentPage);
        int totalPage = articleService.getTotalPages();
        int size = 10;
        model.addAttribute("articles", articles);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", totalPage);
        if (currentPage > 1) {
            int previousPage = currentPage - 1;
            model.addAttribute("previousPage", previousPage);
        }
        if (currentPage < totalPage) {
            int nextPage = currentPage + 1;
            model.addAttribute("nextPage", nextPage);
        }
        int[] pages = new int[totalPage];
        for (int i = 0; i < totalPage; i++) {
            pages[i] = i + 1;
        }
        model.addAttribute("pages", pages);
        return "articles";
    }

    @GetMapping("/articles/article")
    public String getArticle(ArticleForPageDTO articleForPageDTO, Model model) {
        model.addAttribute("article", articleForPageDTO);
        return "article";
    }

    @PostMapping("/articles/article/{id}")
    public String showArticleById(@PathVariable("id") Long id, Model model) {
        ArticleForPageDTO article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article";
    }

    @PostMapping("/articles/article/find")
    public String findArticleById(@RequestParam("keyWord") String keyWord, Model model) {
        List<ArticleForPageDTO> articles = null;
        try {
            articles = articleService.getArticleByKeyWord(keyWord);
        } catch (Exception e) {
            model.addAttribute("notFoundMessage", notFoundMessage);
            return "articles";
        }
        model.addAttribute("articles", articles);
        return "articles";
    }
}
