package com.janan.service;

import com.janan.model.Issue;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Optional<Issue> getIssueById(Long issueId) throws IssueException;

    List<Issue> getIssueByProjectId(Long projectId) throws ProjectException;

    Issue createIssue(IssueRequest issue,Long userid) throws ExecutionControl.UserException, IssueException, ProjectExeption;

    Optional<Issue> updateIssue(Long issueId, IssueRequest updateIssue,Long userid) throws IssueException, ExecutionControl.UserException;

    String deleteIssue(Long issueId,Long userId) throws ExecutionControl.UserException, IssueException;
}
