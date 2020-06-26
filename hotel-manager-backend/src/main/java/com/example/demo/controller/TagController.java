package com.example.demo.controller;

import com.example.demo.entity.Tag;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tag")
public class TagController {


    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public ResponseEntity<List<Tag>> getTags(){
        return ResponseEntity.ok(
                tagRepository.findAll()
        );
    }

    @PostMapping
    public ResponseEntity<Tag> postTag(@RequestBody Tag tag){
        var newTag = tagRepository.save(tag);
        return ResponseEntity.ok(
                newTag
        );
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTag(@PathVariable long id){
        tagRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
