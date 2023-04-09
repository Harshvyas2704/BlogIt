package com.adobe.blogit.repositories;

import com.adobe.blogit.models.Post;
import com.adobe.blogit.payloads.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    @Query("from Post order by likes desc limit 5 ")
	public List<Post> getTop5LikedPosts();
}
