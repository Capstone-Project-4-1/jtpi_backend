package com.example.jtpi_backend.controller;
import com.example.jtpi_backend.domain.SearchParameters;
import com.example.jtpi_backend.domain.dto.PassDetailDTO;
import com.example.jtpi_backend.domain.dto.PassSearchResultDTO;
import com.example.jtpi_backend.domain.dto.SlideShowPassDTO;
import com.example.jtpi_backend.service.PassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passes")
public class PassController {

    private static final Logger logger = LoggerFactory.getLogger(PassController.class);


    private final PassService passService;

    @Autowired
    public PassController(@Lazy PassService passService) {
        this.passService = passService;
    }


    //신규
    @GetMapping("/slideshow/new")
    public List<SlideShowPassDTO> getSlideShowNewPasses() {
        return passService.fetchSlideShowNewPasses();
    }
    //추천
    @GetMapping("/slideshow/recommended")
    public List<SlideShowPassDTO> getSlideShowRecommendedPasses() {
        return passService.fetchSlideShowRecommendedPasses();
    }
    //검색

    @PostMapping("/search")
    public List<PassSearchResultDTO> searchPasses(@RequestBody SearchParameters searchParams) {
        logger.info("Received search request with parameters: {}", searchParams);
        List<PassSearchResultDTO> results = passService.searchPasses(searchParams);

        if (results.isEmpty()) {
            logger.info("No search results found for parameters: {}", searchParams);
        } else {
            logger.info("Sending search response: {}", results);
        }
        return results;
    }

    @GetMapping("/{passId}")
    public PassDetailDTO getPassDetail(@PathVariable Integer passId) {

        return passService.fetchPassDetail(passId);
    }

    @PostMapping("/bookmark")
    public List<PassSearchResultDTO> getBookmarkResults(@RequestBody List<Integer> passIds) {
        return passService.fetchBookmarkResults(passIds);
    }
}
