package com.janan.service;

import com.janan.model.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueId, Long userId, String comment);

    void deleteComment(Long commentId, Long userId);

    List<Comment> findCommentByIssueId(Long issueId);
}
