package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ArticleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForNewsDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.PaginationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    @Value("${content.not.found.message}")
    String notFoundMessage;
    @Value("${delete.comment.message}")
    String deleteCommentMessage;

    private final PaginationHandler pagination;
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService, PaginationHandler pagination) {
        this.articleService = articleService;
        this.pagination = pagination;
    }

    @GetMapping("/articles")
    public String getArticles(
            @RequestParam(name = "page", defaultValue = "1") Integer currentPage, Model model) {
        model.addAttribute("currentPage", currentPage);
        List<ArticleDTO> articles = articleService.getArticle(currentPage);
        model.addAttribute("articles", articles);
        int totalPage = articleService.getTotalPages();
        pagination.getPagination(currentPage, model, totalPage);
        return "articles";
    }

    @GetMapping("/articles/article/{id}")

    public String showArticleById(@PathVariable("id") Long id, Model model) {
        ArticleForPageDTO article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article";
    }

    @PostMapping("/articles/article/find")
    public String findArticleByKeyWord(@RequestParam("keyWord") String keyWord, Model model) {
        List<ArticleForPageDTO> articles = articleService.getArticleByKeyWord(keyWord);
        if (articles.size() == 0) {
            model.addAttribute("notFoundMessage", notFoundMessage);
            return "articles";
        }
        model.addAttribute("articles", articles);
        return "articles";
    }

    @PostMapping("/articles/article/delete")
    public String deleteArticleById(@Nullable @RequestParam("ids") Long[] ids,
                                    @RequestParam(name = "page", defaultValue = "1") Integer currentPage, Model model) {
        if (ids == null) {
            return "redirect:/articles";
        }
        List<Long> collect = Arrays.stream(ids).collect(Collectors.toList());
        try {
            articleService.deleteArticleById(collect);
        } catch (Exception e) {
            getArticles(currentPage, model);
            return "articles";
        }
        return "redirect:/articles";
    }

    @PostMapping("/articles/article/{id}/change")
    public String getPageArticleForSeller(@PathVariable Long id, Model model) {
        ArticleForPageDTO articleForPageDTO = articleService.getArticleById(id);
        model.addAttribute("article", articleForPageDTO);
        return "seller_article";
    }

    @PostMapping("/articles/article/saveChanges")
    public String updateArticle(@ModelAttribute("article") ArticleForPageDTO articleForPageDTO) {
        articleService.updateArticle(articleForPageDTO);
        return "redirect:/articles";
    }

    @PostMapping("/articles/article/deleteComment")
    public String deleteCommentById(@Nullable @RequestParam("ids") Long[] ids,
                                    RedirectAttributes attributes) {
        if (ids == null) {
            return "redirect:/articles/article";
        }
        List<Long> collect = Arrays.stream(ids).collect(Collectors.toList());
        articleService.deleteCommentById(collect);
        attributes.addFlashAttribute("deleteCommentMessage", deleteCommentMessage);
        return "redirect:/articles";
    }

    @GetMapping("/articles/new/article")
    public String getNewArticlePage(ArticleDTO articleDTO, Model model) {
        model.addAttribute("article", articleDTO);
        return "news";
    }

    @PostMapping("/articles/new/article")
    public String createNewArticle(@Valid @ModelAttribute("article") ArticleForNewsDTO articleForNewsDTO,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "news";
        }
        articleService.createNewArticle(articleForNewsDTO);
        return "redirect:/articles";
    }
}
