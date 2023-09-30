package com.BlogApplaction.backend.service.impl;

import com.BlogApplaction.backend.entities.Category;
import com.BlogApplaction.backend.entities.Post;
import com.BlogApplaction.backend.entities.User;
import com.BlogApplaction.backend.exceptions.ResourceNotFoundException;
import com.BlogApplaction.backend.payload.PostDto;
import com.BlogApplaction.backend.payload.PostResponse;
import com.BlogApplaction.backend.repository.CategoryRepo;
import com.BlogApplaction.backend.repository.PostRepo;
import com.BlogApplaction.backend.repository.UserRepo;
import com.BlogApplaction.backend.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostImpl implements PostService {

    @Autowired
    private PostRepo repo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("Defult_Photo");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post save = repo.save(post);


        return this.modelMapper.map(save,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.repo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));
      //  Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
       // post.setCategory(category);
        Post save = this.repo.save(post);
        return this.modelMapper.map(save,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.repo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        this.repo.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }

        // we can also user ternay Operater instance of If elese staement
       // Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //________________________________________________________//

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> all1 = this.repo.findAll(p);
        List<Post> all = all1.getContent();
      //  List<Post> all = repo.findAll();
        List<PostDto> collect = all.stream().map((cat) -> this.modelMapper.map(cat, PostDto.class)).collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setContent(collect);
        response.setPageNumber(all1.getNumber());
        response.setPageSize(all1.getSize());
        response.setTotalElements(all1.getTotalElements());
        response.setTotalPages(all1.getTotalPages());
        response.setLastPage(all1.isLast());
        return response;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.repo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        PostDto map = this.modelMapper.map(post, PostDto.class);

        return map;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        List<Post> byCategory = this.repo.findByCategory(category);
        List<PostDto> collect = byCategory.stream().map((cat) -> this.modelMapper.map(cat, PostDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Post> byUser = this.repo.findByUser(user);

        List<PostDto> collect = byUser.stream().map((usr) -> this.modelMapper.map(usr, PostDto.class)).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = repo.searchByTitle(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
