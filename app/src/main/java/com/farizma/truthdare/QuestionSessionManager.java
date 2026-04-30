package com.farizma.truthdare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class QuestionSessionManager {

    public static final String MODE_TRUTH = "truth";
    public static final String MODE_DARE = "dare";

    private static final Random RANDOM = new Random();
    private static final Map<String, DeckState> DECKS = new HashMap<>();

    private QuestionSessionManager() {
    }

    public static synchronized String nextQuestion(String mode, List<String> questions) {
        if (questions == null || questions.isEmpty()) {
            return "No questions available.";
        }

        String signature = buildSignature(questions);
        DeckState deckState = DECKS.get(mode);
        if (deckState == null || !signature.equals(deckState.signature) || deckState.order.isEmpty()) {
            deckState = new DeckState(signature, createShuffledOrder(questions.size(), -1), 0, -1);
            DECKS.put(mode, deckState);
        }

        if (deckState.cursor >= deckState.order.size()) {
            deckState.order = createShuffledOrder(questions.size(), deckState.lastQuestionIndex);
            deckState.cursor = 0;
        }

        int questionIndex = deckState.order.get(deckState.cursor);
        deckState.cursor++;
        deckState.lastQuestionIndex = questionIndex;
        return questions.get(questionIndex);
    }

    private static List<Integer> createShuffledOrder(int size, int avoidFirstIndex) {
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            order.add(i);
        }

        Collections.shuffle(order, RANDOM);

        if (size > 1 && avoidFirstIndex >= 0 && order.get(0) == avoidFirstIndex) {
            int swapIndex = 1 + RANDOM.nextInt(size - 1);
            int tmp = order.get(0);
            order.set(0, order.get(swapIndex));
            order.set(swapIndex, tmp);
        }

        return order;
    }

    private static String buildSignature(List<String> questions) {
        StringBuilder builder = new StringBuilder();
        for (String question : questions) {
            builder.append(question).append("||");
        }
        return builder.toString();
    }

    private static class DeckState {
        String signature;
        List<Integer> order;
        int cursor;
        int lastQuestionIndex;

        DeckState(String signature, List<Integer> order, int cursor, int lastQuestionIndex) {
            this.signature = signature;
            this.order = order;
            this.cursor = cursor;
            this.lastQuestionIndex = lastQuestionIndex;
        }
    }
}
