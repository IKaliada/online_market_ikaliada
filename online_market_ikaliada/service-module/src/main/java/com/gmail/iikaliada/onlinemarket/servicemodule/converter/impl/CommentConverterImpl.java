package com.gmail.iikaliada.onlinemarket.servicemodule.converter.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Comment;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.CommentConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.UserConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.CommentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentConverterImpl implements CommentConverter {

    private final UserConverter userConverter;

    public CommentConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public List<CommentDTO> toCommentDTO(List<Comment> comments) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setDate(comment.getDate());
            commentDTO.setUser(userConverter.toUserForUiDTO(comment.getUser()));
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    @Override
    public List<Comment> fromCommentDTO(List<CommentDTO> commentDTOList) {
        List<Comment> comments = new ArrayList<>();
        for (CommentDTO commentDTO : commentDTOList) {
            Comment comment = new Comment();
            comment.setId(commentDTO.getId());
            comment.setContent(commentDTO.getContent());
            comment.setDate(commentDTO.getDate());
            comment.setUser(userConverter.fromUserForUiDTO(commentDTO.getUser()));
            comments.add(comment);
        }
        return comments;
    }
}
