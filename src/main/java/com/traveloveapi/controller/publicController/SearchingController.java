package com.traveloveapi.controller.publicController;

import com.traveloveapi.entity.searching.SearchingEntity;
import com.traveloveapi.repository.searching.SearchingRepository;
import com.traveloveapi.utility.SearchingSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/search")
public class SearchingController {
    final private SearchingRepository searchingRepository;

    @Tag(name = "SPRINT 4: User side")
    @GetMapping
    public List search(@RequestParam String input) {
        input = SearchingSupporter.sanitize(input);
        return searchingRepository.findByTitle(input, 0, 5);
    }

    @PostMapping
    @Operation(hidden = true)
    public SearchingEntity create(@RequestBody SearchingEntity req) {
        searchingRepository.save(req);
        return req;
    }
}
