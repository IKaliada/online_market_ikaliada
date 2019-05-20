package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ArticleRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Article;
import com.gmail.iikaliada.onlinemarket.servicemodule.ArticleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ArticleConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.exception.ConnectionServiceStateException;
import com.gmail.iikaliada.onlinemarket.servicemodule.exception.UserServiceTransactionRollbackedException;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.iikaliada.onlinemarket.repositorymodule.constant.LimitConstants.LIMIT;
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.ServiceConstants.CONNECTION_SERVICE_MESSAGE;
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.ServiceConstants.TRANSACTION_ROLLBACK_MESSAGE;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
    }

//    @Override
//    @Transactional
//    public List<ArticleDTO> getArticle(int currentPage) {
//        int offset = LIMIT * currentPage;
//        currentPage = currentPage - 1;
//        List<Article> articles = articleRepository.findAll(currentPage, offset);
//        return articles.stream()
//                .map(articleConverter::toArticleDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<ArticleDTO> getArticle(int currentPage) {
        try (Connection connection = articleRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Article> articles = articleRepository.getArticles(connection, currentPage);
                connection.commit();
                return articles.stream()
                        .map(articleConverter::toArticleDTO)
                        .collect(Collectors.toList());
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserServiceTransactionRollbackedException(TRANSACTION_ROLLBACK_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
    }

    @Override
    @Transactional
    public int getTotalPages() {
        int countOfEntities = articleRepository.getCountOfEntities();
        int amountRows = countOfEntities / LIMIT;
        int pagesNumber = 0;
        if (amountRows % LIMIT > 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }

    @Override
    public ArticleForPageDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id);
        return articleConverter.toArticleForPageDTO(article);
    }

    @Override
    public ArticleForPageDTO getArticleByKeyWord(String keyWord) {
        try (Connection connection = articleRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Article article = articleRepository.getArticleByKeyWord(connection, keyWord);
                ArticleForPageDTO articleForPageDTO = articleConverter.toArticleForPageDTO(article);
                connection.commit();
                return articleForPageDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ConnectionServiceStateException(TRANSACTION_ROLLBACK_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionServiceStateException(CONNECTION_SERVICE_MESSAGE);
        }
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
}
