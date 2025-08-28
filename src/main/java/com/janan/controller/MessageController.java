package com.janan.controller;

import com.janan.model.Chat;
import com.janan.model.Message;
import com.janan.model.User;
import com.janan.request.CreateMessageRequest;
import com.janan.service.MessageService;
import com.janan.service.ProjectService;
import com.janan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request)
        throws Exception {

        User user = userService.findUserById(request.getSenderId());

        Chat chats=projectService.getProjectById(request.getProjectId()).getChat();

        if (chats == null) throw new Exception("chats not found");

        Message sendMessage = messageService.sendMessage(request.getSenderId(),
                request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sendMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable Long projectId)
            throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
