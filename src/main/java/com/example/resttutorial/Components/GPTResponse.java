package com.example.resttutorial.Components;

import java.util.List;

public class GPTResponse {

    private List<Choice> choices;

    // constructors, getters and setters


    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {

        private int index;
        private Message message;

        // constructors, getters and setters

        public void setMessage(Message message) {
            this.message = message;
        }

        public Message getMessage() {
            return message;
        }
    }
}

