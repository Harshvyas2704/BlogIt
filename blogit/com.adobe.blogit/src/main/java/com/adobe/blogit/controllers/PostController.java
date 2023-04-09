package com.adobe.blogit.controllers;

import com.adobe.blogit.payloads.ApiResponse;
import com.adobe.blogit.payloads.PostDto;
import com.adobe.blogit.repositories.PostRepo;
import com.adobe.blogit.servicies.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepo postRepo;

    @PostMapping("/posts/user/{userId}")
    public ResponseEntity<PostDto> createNewPost(@RequestBody PostDto post, @PathVariable Long userId) {

        PostDto newPost = postService.createPost(post, userId);

        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){

        PostDto getPost = this.postService.getPostById(postId);
        return new ResponseEntity<>(getPost, HttpStatus.OK);

    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId){

        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);

    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<PostDto> deletePost(@PathVariable Long postId){

        this.postService.deletePost(postId);
        return new ResponseEntity(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);

    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<PostDto> likePost(@PathVariable Long postId){

        PostDto postDto = this.postService.likePost(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);

    }

    @PostMapping("/posts/{postId}/unlike")
    public ResponseEntity<PostDto> unlikePost(@PathVariable Long postId){

        PostDto postDto = this.postService.disLikePost(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);

    }

    @GetMapping("/analytics/posts")
    public String getTotalNumberOfPosts(){
        return this.postService.totalNumbersOfPosts();
    }

    @GetMapping("/analytics/posts/top-liked")
    public ResponseEntity<List<PostDto>> getTop5LikedPosts(){

        List<PostDto> postDtos = this.postService.getTop5ByLikes();

        return new ResponseEntity<>(postDtos, HttpStatus.OK);

    }

}