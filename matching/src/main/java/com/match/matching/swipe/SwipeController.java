package com.match.matching.swipe;

import com.match.matching.match.Match;
import com.match.matching.swipe.dto.SwipeRequest;
import com.match.matching.swipe.dto.SwipeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/swipes")
public class SwipeController {

    private final SwipeService swipeService;

    public SwipeController(SwipeService swipeService) {
        this.swipeService = swipeService;
    }

    @PostMapping
    public ResponseEntity<SwipeResponse> swipe(Principal principal, @RequestBody SwipeRequest request) {
        return ResponseEntity.ok(swipeService.processSwipe(principal.getName(), request));
    }

    @GetMapping("/matches")
    public ResponseEntity<List<Match>> getMatches(Principal principal) {
        return ResponseEntity.ok(swipeService.getUserMatches(principal.getName()));
    }
}