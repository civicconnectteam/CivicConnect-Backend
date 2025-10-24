package com.example.cyber.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.cyber.model.Case;
import com.example.cyber.model.User;
import com.example.cyber.service.CaseService;
import com.example.cyber.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/case")
@CrossOrigin("*")
public class CaseController {

    private final CaseService caseService;
    private final UserService userService;
    private final Cloudinary cloudinary;

    public CaseController(CaseService caseService, UserService userService, Cloudinary cloudinary) {
        this.caseService = caseService;
        this.userService = userService;
        this.cloudinary = cloudinary;
    }

    @PostMapping("/add")
    public Case addCase(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) MultipartFile file
    ) {
        try {
            String imageUrl = null;

            if (file != null && !file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                imageUrl = (String) uploadResult.get("secure_url");
            }

            User user = userService.getUserById(userId);
            if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

            Case c = new Case();
            c.setTitle(title);
            c.setDescription(description);
            c.setLocation(location);
            c.setImageUrl(imageUrl);
            c.setUser(user);
            c.setStatus("Submitted");

            return caseService.addCase(c);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file to Cloudinary");
        }
    }

    @GetMapping("/user/{userId}")
    public List<Case> getUserCases(@PathVariable Long userId) {
        return caseService.getUserCases(userId);
    }

    @GetMapping("/all")
    public List<Case> getAllCases() {
        return caseService.getAllCases();
    }

    @PutMapping("/updateStatus/{caseId}")
    public Case updateCaseStatus(@PathVariable Long caseId, @RequestBody String newStatus) {
        return caseService.updateCaseStatus(caseId, newStatus.trim());
    }

    @DeleteMapping("/delete/{caseId}")
    public String deleteCase(@PathVariable Long caseId) {
        caseService.deleteCase(caseId);
        return "Case deleted successfully";
    }

    @PostMapping("/addReview/{caseId}")
    public Case addReview(@PathVariable Long caseId, @RequestBody ReviewRequest reviewRequest) {
        return caseService.updateCase(caseId, null, reviewRequest.getReview());
    }

    public static class ReviewRequest {
        private String review;
        public String getReview() { return review; }
        public void setReview(String review) { this.review = review; }
    }
}
