package com.example.manasfen.model.payload;

import java.util.Objects;

public record QuestionStatsPair(
        String question,
        Integer value
) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuestionStatsPair statsPair = (QuestionStatsPair) obj;
        return question.equals(statsPair.question);
    }
}
