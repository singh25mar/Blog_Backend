package com.BlogApplaction.backend.service;

import com.BlogApplaction.backend.payload.CommentDto;

public interface CommentService {

    CommentDto CreateComment(CommentDto commentDto,Integer postId);

    void deleteComment(Integer commentId);
}
