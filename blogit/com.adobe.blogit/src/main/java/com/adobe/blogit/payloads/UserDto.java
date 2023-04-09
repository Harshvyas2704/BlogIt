package com.adobe.blogit.payloads;

import com.adobe.blogit.models.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long userId;
    private String name;

    private String email;

    private String bio;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer noOfPosts;

}
