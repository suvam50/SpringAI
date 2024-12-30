package com.ai.SpringAi;

import jakarta.servlet.http.*;
import org.springframework.ai.image.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

@RestController
public class GenAIController {


    private final ChatService chatService;
    private final ImageService imageService;
    private final ReceipeService receipeService;

    public GenAIController(ChatService chatService, ImageService imageService, ReceipeService receipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.receipeService = receipeService;
    }

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt){
        return chatService.getResponse(prompt);
    }

    @GetMapping("/ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt){
        return chatService.getResponseOptions(prompt);
    }

    @GetMapping("/generate-image")
    public void generateImages(HttpServletResponse response, @RequestParam String prompt) throws IOException {

        ImageResponse imageResponse=imageService.generateImage(prompt);

        String url=imageResponse.getResult().getOutput().getUrl();

        response.sendRedirect(url);
    }

    @GetMapping("/generate-image-options")
    public List<String> generateImagesOptions(@RequestParam String prompt,
                                              @RequestParam(defaultValue = "hd") String quality,
                                              @RequestParam(defaultValue = "256" )Integer height,
                                              @RequestParam(defaultValue = "256")Integer width,
                                              @RequestParam(defaultValue = "1") Integer numberOfImages){

        ImageResponse imageResponse=imageService.generateImageOptions(prompt,quality,height,width,numberOfImages);

        List<String>imageUrls = imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();


//                .forEach(url -> {
//                    try {
//                        response.getOutputStream().write(url.getBytes());
//                        response.getOutputStream().flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
        return imageUrls;
    }

    @GetMapping("/generate-recipe")
    public String generateRecipe(@RequestParam String ingredients,
                                 @RequestParam(defaultValue = "any") String cuisine,
                                 @RequestParam(defaultValue = "") String dietaryRestriction)
    {
        return receipeService.createRecipe(ingredients,cuisine,dietaryRestriction);
    }
}
