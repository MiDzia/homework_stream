package com.dziazhko.hw_stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PuttingIntoPractice {

    public static void main(String... args) {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

//  1. Найти все транзакции за 2011 год и отсортировать их по сумме (от меньшей к большей).

        List<Transaction> listTransactions = transactions.stream()
                .filter(Transaction -> Transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .toList();

// 2. Вывести список неповторяющихся городов, в которых работают трейдеры.

        List<String> map = transactions.stream()
                .map(Transaction::getTrader)
                .collect(Collectors.toSet())
                .stream()
                .map(Trader::getCity)
                .collect(Collectors.toMap(Function.identity(), value -> 1, Integer::sum))
                .entrySet()
                .stream()
                .filter(m -> m.getValue() == 1)
                .map(Map.Entry::getKey)
                .toList();

// 3. Найти всех трейдеров из Кембриджа и отсортировать их по именам.

        Set<Trader> listTradersFromCambridge = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().contains("Cambridge"))
                .collect(Collectors.toSet());

//  4. Вернуть строку со всеми именами трейдеров, отсортированными в алфавитном порядке.

        String str = transactions.stream()
                .map(tr -> tr.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining(" "));

//  5. Выяснить, существует ли хоть один трейдер из Милана.

        boolean isTraderFromMilan = transactions.stream()
                .anyMatch(o -> o.getTrader().getCity().contains("Milan"));

//  6. Вывести суммы всех транзакций трейдеров из Кембриджа.

        Integer sumOfTransaction = transactions.stream()
                .filter(tr -> tr.getTrader().getCity().contains("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::sum)
                .get();

//  7. Какова максимальная сумма среди всех транзакций?

        int transactionWithMaxSum = transactions.stream()
                .max(Comparator.comparing(Transaction::getValue))
                .get().getValue();

//  8. Найти транзакцию с минимальной суммой.

        Transaction transactionWithMinSum = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue))
                .get();

    }
}
