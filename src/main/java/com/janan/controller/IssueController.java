package com.janan.controller;

import com.janan.model.Issue;
import com.janan.model.IssueDTO;
import com.janan.model.User;
import com.janan.request.IssueRequest;
import com.janan.response.MessageResponse;
import com.janan.service.IssueService;
import com.janan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue,
                                                @RequestHeader("Authorization") String jwt, String token) throws Exception {

        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());


        Issue createdIssue = issueService.createIssue(issue, tokenUser);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setId(createdIssue.getId());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setProjectID(createdIssue.getProjectID());
        issueDTO.setAssignee(createdIssue.getAssignee());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setTags(createdIssue.getTags());
        return ResponseEntity.ok(issueDTO);
    }


    @DeleteMapping("/{issueId}")
    public ResponseEntity<String> deleteIssue(@PathVariable Long issueId,
                                              @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse res=new MessageResponse();
        res.setMessage("issue deleted successfully");

        return ResponseEntity.ok(res.getMessage());
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
                                                @PathVariable Long userId) throws Exception {

        Issue issue=issueService.addUserToIssue(issueId, userId);

        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable Long issueId,
                                                   @PathVariable String status) throws Exception {
        Issue issue=issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
}


