package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.dto.ApiResult;
import com.aivle.fakedetecting.dto.RequestComment;
import com.aivle.fakedetecting.entity.Comment;
import com.aivle.fakedetecting.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public ApiResult<Boolean> postComment(@RequestBody RequestComment requestComment) throws Exception {
        commentService.postComment(requestComment);
        return ApiResult.success(true, "댓글 작성");
    }
}
