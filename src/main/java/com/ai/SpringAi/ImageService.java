package com.ai.SpringAi;

import org.springframework.ai.image.*;
import org.springframework.ai.image.observation.*;
import org.springframework.ai.openai.*;
import org.springframework.stereotype.*;

@Service
public class ImageService {

    private final OpenAiImageModel openAiImageModel;


    public ImageService(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;

    }

    public ImageResponse generateImage(String prompt){
        OpenAiImageOptions imageOptions=new OpenAiImageOptions();
        imageOptions.setHeight(256);
        imageOptions.setWidth(256);
       ImageResponse imageResponse= openAiImageModel.call(new ImagePrompt(prompt,imageOptions)
//                new ImagePrompt(prompt,
//                        OpenAiImageOptions
//                                .builder()
//                                .withHeight(1024)
//                                .withWidth(1024)
//                                .build())
        );
       return imageResponse;
    }

    public ImageResponse generateImageOptions(String prompt,String quality,
                                              Integer height,Integer width,
                                              Integer numberOfImages){
        ImageResponse imageResponse= openAiImageModel.call(
                new ImagePrompt(prompt,
                        OpenAiImageOptions
                                .builder()
                                .withModel("dall-e-2")
                                .withN(numberOfImages)
                                .withQuality(quality)
                                .withHeight(height)
                                .withWidth(width)
                                .build())
        );
        return imageResponse;
    }


}
