package com.lucasmoraist.tasks.repository.http;

import com.lucasmoraist.tasks.domain.dto.CommentDTO;
import com.lucasmoraist.tasks.domain.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "comments-ms")
public interface CommentClient {
    @RequestMapping(method = RequestMethod.POST, value = "/comments/{taskId}/comments")
    Long createComment(@PathVariable Long taskId, @RequestBody CommentDTO dto);
    @RequestMapping(method = RequestMethod.GET, value = "/comments")
    List<Comment> listAll();
}
