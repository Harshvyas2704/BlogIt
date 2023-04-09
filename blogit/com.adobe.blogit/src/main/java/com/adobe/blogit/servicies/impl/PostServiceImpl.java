package com.adobe.blogit.servicies.impl;

import com.adobe.blogit.exceptions.ResourceNotFoundException;
import com.adobe.blogit.models.Post;
import com.adobe.blogit.models.User;
import com.adobe.blogit.payloads.PostDto;
import com.adobe.blogit.payloads.UserDto;
import com.adobe.blogit.repositories.PostRepo;
import com.adobe.blogit.repositories.UserRepo;
import com.adobe.blogit.servicies.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserServiceImpl userService;
    @Override
    public PostDto createPost(PostDto newPost, Long userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));

        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setUpdatedAt(LocalDateTime.now());
        newPost.setUserId(userId);
//        UserDto userDto = this.userService.userToDto(user);
//        userDto.setNoOfPosts(userDto.getNoOfPosts() + 1);
//        userDto.setNoOfPosts(user.getPosts().size() + 1);
//        user.getPosts().add(newPost); // add the new post to the user's posts list
//        this.userRepo.save(user); // save the updated user object
        Post savedPost = this.dtoToPost(newPost);
        this.postRepo.save(savedPost);
        return this.postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto post, Long postId) {

        Post getPost = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));

        getPost.setContent(post.getContent());
        getPost.setUpdatedAt(LocalDateTime.now());
        Post updatedPost = this.postRepo.save(getPost);

        return this.postToDto(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        Post getPost = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));

        this.postRepo.delete(getPost);

    }

    @Override
    public PostDto getPostById(Long postId) {
        Post getPost = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));
        PostDto postDto = this.postToDto(getPost);
        return postDto;
    }

    @Override
    public PostDto likePost(Long postId) {

        Post getPost = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));

        getPost.setLikes(getPost.getLikes() + 1);
        this.postRepo.save(getPost);
        return this.postToDto(getPost);
    }

    @Override
    public PostDto disLikePost(Long postId) {

        Post getPost = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));

        if(getPost.getLikes() == 0){
            return this.postToDto(getPost);
        }

        getPost.setLikes(getPost.getLikes() - 1);
        this.postRepo.save(getPost);
        return this.postToDto(getPost);
    }

    @Override
    public String totalNumbersOfPosts() {
        return "Total Number of Posts are: " + postRepo.count();
    }

    @Override
    public List<PostDto> getTop5ByLikes() {

        List<Post> posts =  this.postRepo.getTop5LikedPosts();
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).
                collect(Collectors.toList());

        return postDtos;

    }


    public PostDto postToDto(Post post){
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    public Post dtoToPost(PostDto postDto){
        Post post = this.modelMapper.map(postDto, Post.class);
        return post;
    }

}
