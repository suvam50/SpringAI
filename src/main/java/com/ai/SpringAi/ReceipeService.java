package com.ai.SpringAi;

import org.springframework.ai.chat.model.*;
import org.springframework.ai.chat.prompt.*;
import org.springframework.ai.openai.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ReceipeService {

    private final ChatModel chatModel;

    public ReceipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions){
        var template= """
                I want to create a recipe using the following ingredients : {ingredients}.
                The cuisine type I prefer is {cuisine}.
                Please consider the following dietary restriction : {dietaryRestrictions}.
                Please provide me with a detailed recipe including title, list of ingredients and cooking instructions.
                """;
        PromptTemplate promptTemplate=new PromptTemplate(template);
        Map<String,Object>params=Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions
        );

        Prompt prompt=promptTemplate.create(params);

        OpenAiChatOptions chatOptions=OpenAiChatOptions.builder()
                .withModel("gpt-4o")
                .withTemperature(0.4)
                .build();

        return chatModel.call(new Prompt(prompt.getContents(),chatOptions)).getResult().getOutput().getContent();
    }
}
