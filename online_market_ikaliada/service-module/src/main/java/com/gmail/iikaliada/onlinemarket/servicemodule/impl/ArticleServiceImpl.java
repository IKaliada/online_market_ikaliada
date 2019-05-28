package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ArticleRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.CommentRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Comment;
import com.gmail.iikaliada.onlinemarket.servicemodule.ArticleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ArticleConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForNewsDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForArticleDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Integer LIMIT_FOR_ARTICLE = 200;

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public ArticleServiceImpl(ArticleRepository articleRepository,
                              ArticleConverter articleConverter,
                              CommentRepository commentRepository,
                              UserService userService) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public List<ArticleDTO> getArticle(int currentPage) {
        int offset = ((LIMIT * currentPage));
        currentPage = LIMIT * (currentPage - 1);
        List<Article> articles = articleRepository.findAll(currentPage, offset);
        return articles.stream()
                .map(articleConverter::toArticleDTO)
                .peek(articleDTO -> {
                    if (articleDTO.getDescription().length() > LIMIT_FOR_ARTICLE) {
                        articleDTO.setDescription(articleDTO.getDescription().substring(0, LIMIT_FOR_ARTICLE));
                    }
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int getTotalPages() {
        int totalEntities = articleRepository.getCountOfEntities();
        return getPagesNumber(totalEntities);
    }

    @Override
    @Transactional
    public ArticleForPageDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id);
        return articleConverter.toArticleForPageDTO(article);
    }

    @Override
    @Transactional
    public List<ArticleForPageDTO> getArticleByKeyWord(String keyWord) {
        List<Article> articleByKeyWord = articleRepository.getArticleByKeyWord(keyWord);
        return articleByKeyWord.stream().map(articleConverter::toArticleForPageDTO).peek(articleDTO -> {
            if (articleDTO.getDescription().length() > LIMIT_FOR_ARTICLE) {
                articleDTO.setDescription(articleDTO.getDescription().substring(0, LIMIT_FOR_ARTICLE));
            }
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void add(ArticleDTO articleDTO) {
        Article article = articleConverter.fromArticleDTO(articleDTO);
        articleRepository.persist(article);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        articleRepository.removeById(id);
    }

    @Override
    @Transactional
    public void deleteArticleById(List<Long> ids) {
        for (Long id : ids) {
            articleRepository.removeById(id);
        }
    }

    @Override
    @Transactional
    public void updateArticle(ArticleForPageDTO articleForPageDTO) {
        Article article = articleRepository.findById(articleForPageDTO.getId());
        article.setArticle(articleForPageDTO.getArticle());
        article.setDescription(articleForPageDTO.getDescription());
        article.setDate(new Date());
        articleRepository.merge(article);
    }

    @Override
    @Transactional
    public void deleteCommentById(List<Long> ids) {
        for (Long id : ids) {
            Comment comment = commentRepository.findById(id);
            commentRepository.removeById(comment.getId());
        }
    }

    @Override
    @Transactional
    public void createNewArticle(ArticleForNewsDTO articleForNewsDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserForArticleDTO userForArticleDTO = userService.getUserForArticle(currentPrincipalName);
        articleForNewsDTO.setDate(new Date());
        articleForNewsDTO.setUserForArticleDTO(userForArticleDTO);
        Article article = articleConverter.fromArticleForNewsDTO(articleForNewsDTO);
        articleRepository.persist(article);

    }

    private int getPagesNumber(int totalEntities) {
        int pagesNumber = totalEntities / LIMIT;
        if (totalEntities % LIMIT > 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }
}
