package com.growstory.domain.board.dto;

import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class RequestBoardDto {

    @Getter
    public static class Post {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        public Post(String title, String content) {
            this.title = title;
            this.content = content;
        }

        //
//        @Nullable
//        private String imageUrl;
    }

    @Getter
    public static class Patch {

        private Long boardId;

        @Nullable
        private String title;

        @Nullable
        private String content;

        @Nullable
        private String imageUrl;

        @Nullable
        private String hashTag;
    }
}
