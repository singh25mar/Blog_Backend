package com.BlogApplaction.backend.controllers;


import com.BlogApplaction.backend.payload.ApiResponce;
import com.BlogApplaction.backend.payload.CommentDto;
import com.BlogApplaction.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // create Comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createcomment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto createComment = this.commentService.CreateComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }


    // Delete Mapping
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponce> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponce>(new ApiResponce("Comment deleted successfully !!", true), HttpStatus.OK);
    }
}
