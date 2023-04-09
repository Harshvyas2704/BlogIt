package com.adobe.blogit.repositories;

import com.adobe.blogit.models.Post;
import com.adobe.blogit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("from User order by posts desc limit 2")
    public List<User> getTop5ActiveUsers();

}
