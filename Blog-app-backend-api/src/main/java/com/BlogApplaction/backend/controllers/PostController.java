package com.BlogApplaction.backend.controllers;

import com.BlogApplaction.backend.config.AppConstants;
import com.BlogApplaction.backend.entities.Post;
import com.BlogApplaction.backend.payload.ApiResponce;
import com.BlogApplaction.backend.payload.PostDto;
import com.BlogApplaction.backend.payload.PostResponse;
import com.BlogApplaction.backend.repository.FileService;
import com.BlogApplaction.backend.service.PostService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PostController {


    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // creating Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId ){
        PostDto post = this.postService.createPost(postDto, userId, categoryId);

return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
    }

    // get Post By User

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getUserbyUserId(@PathVariable Integer userId){
        List<PostDto> postsByUser = this.postService.getPostsByUser(userId);

        return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
    }

    // Get Post By catagery

    @GetMapping("categry/{catId}/posts")
    public ResponseEntity<List<PostDto>> getByCatagery(@PathVariable Integer catId){
        List<PostDto> postsByCategory = this.postService.getPostsByCategory(catId);

        return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
    }

    // Get All Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getallPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ){
        PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
    }


    // get Post BY Post Id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId){
        PostDto postById = this.postService.getPostById(postId);
        return ResponseEntity.ok(postById);
    }


    // Post Delete

    @DeleteMapping("/posts/{postId}")
    public ApiResponce deletPost(@PathVariable Integer postId){
        this.postService.deletePost(postId);

        return new ApiResponce("Post Delete Successfully",true);
    }


    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto postDto1 = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(postDto1,HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
        List<PostDto> result = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
    }


    // Image Upload

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String s = this.fileService.UploadImage(path, image);
        postDto.setImageName(s);
        PostDto postDto1 = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(postDto1,HttpStatus.OK);

    }
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }

}
