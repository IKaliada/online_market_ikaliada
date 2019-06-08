package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Comment;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.CommentDTO;

import java.util.List;

public interface CommentConverter {

    List<CommentDTO> toCommentDTO(List<Comment> comment);
}
