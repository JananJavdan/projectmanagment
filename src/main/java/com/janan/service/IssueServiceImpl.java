package com.janan.service;

import com.janan.model.Issue;
import com.janan.model.Project;
import com.janan.model.User;
import com.janan.repository.IssueRepository;
import com.janan.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;


    @Override
    public Optional<Issue> getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if(issue.isPresent()) {
            return issue;
        }
        throw new Exception("No issues found with issued"+ issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issue, User user) throws Exception {
        Project project=projectService.getProjectById(issue.getProjectID());

        return null;
    }

    @Override
    public String deleteIssue(Long issueId, Long userId) throws Exception {
        return "";
    }

    @Override
    public Issue updateIssue(IssueRequest issue, Long issueId) throws Exception {
        return null;
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        return null;
    }
}
