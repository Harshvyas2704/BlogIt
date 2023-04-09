package com.adobe.blogit.payloads;

import com.adobe.blogit.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Long postId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private int likes;
    private Long userId;

}