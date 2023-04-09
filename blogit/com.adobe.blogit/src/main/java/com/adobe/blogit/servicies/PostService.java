package com.adobe.blogit.servicies;

import com.adobe.blogit.models.Post;
import com.adobe.blogit.payloads.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto post, Long userId);

    PostDto updatePost(PostDto post, Long postId);

    void deletePost(Long postId);

    PostDto getPostById(Long postId);

    PostDto likePost(Long postId);

    PostDto disLikePost(Long postId);

    String totalNumbersOfPosts();

    List<PostDto> getTop5ByLikes();

}
