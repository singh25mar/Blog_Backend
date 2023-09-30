package com.BlogApplaction.backend.service.impl;

import com.BlogApplaction.backend.entities.Comment;
import com.BlogApplaction.backend.entities.Post;
import com.BlogApplaction.backend.exceptions.ResourceNotFoundException;
import com.BlogApplaction.backend.payload.CommentDto;
import com.BlogApplaction.backend.payload.PostResponse;
import com.BlogApplaction.backend.repository.CommentRepo;
import com.BlogApplaction.backend.repository.PostRepo;
import com.BlogApplaction.backend.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postReporepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto CreateComment(CommentDto commentDto, Integer postId) {

        Post post = this.postReporepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id ", postId));
        Comment commments= this.modelMapper.map(commentDto, Comment.class);
      commments.setPost(post);
        Comment save = this.commentRepo.save(commments);
        return this.modelMapper.map(save,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
    this.commentRepo.delete(comment);
    }
}
