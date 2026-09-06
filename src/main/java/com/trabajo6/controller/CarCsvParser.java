package com.trabajo6.controller;

import com.trabajo6.model.Car;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class CarCsvParser {
    private static final List<String> REQUIRED_COLUMNS = List.of("id", "marca", "modelo", "codigo", "anio", "numero", "color", "serie", "otro", "thunt");

    private CarCsvParser() { }

    static List<Car> parse(InputStream input) throws IOException {
        List<List<String>> rows = readRows(input);
        if (rows.isEmpty()) throw new IllegalArgumentException("El CSV está vacío");
        Map<String, Integer> columns = headerIndexes(rows.remove(0));
        List<Car> cars = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        for (int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
            List<String> row = rows.get(rowNumber);
            if (row.stream().allMatch(String::isBlank)) continue;
            int line = rowNumber + 2;
            int id = integer(value(row, columns, "id", line), "ID", line);
            if (id <= 0 || !ids.add(id)) throw new IllegalArgumentException("ID inválido o repetido en la línea " + line);
            Car car = new Car(id,
                    value(row, columns, "marca", line),
                    value(row, columns, "modelo", line),
                    value(row, columns, "codigo", line),
                    numberOrZero(value(row, columns, "anio", line)),
                    numberOrZero(value(row, columns, "numero", line)),
                    value(row, columns, "color", line),
                    value(row, columns, "serie", line),
                    value(row, columns, "otro", line),
                    value(row, columns, "thunt", line));
            cars.add(car);
        }
        if (cars.isEmpty()) throw new IllegalArgumentException("El CSV no contiene registros");
        return cars;
    }

    private static Map<String, Integer> headerIndexes(List<String> header) {
        Map<String, Integer> indexes = new HashMap<>();
        for (int i = 0; i < header.size(); i++) {
            String normalized = normalize(header.get(i));
            indexes.put(normalized, i);
            if ("ano".equals(normalized)) indexes.put("anio", i);
            if ("t-hunt".equals(normalized) || "t hunt".equals(normalized)) indexes.put("thunt", i);
        }
        for (String required : REQUIRED_COLUMNS) {
            if (!indexes.containsKey(required)) throw new IllegalArgumentException("Falta la columna obligatoria: " + required);
        }
        return indexes;
    }

    private static List<List<String>> readRows(InputStream input) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            StringBuilder record = new StringBuilder();
            boolean quoted = false;
            int character;
            while ((character = reader.read()) != -1) {
                char current = (char) character;
                if (current == '"') quoted = !quoted;
                if ((current == '\n' || current == '\r') && !quoted) {
                    if (current == '\r') {
                        reader.mark(1);
                        int next = reader.read();
                        if (next != '\n') reader.reset();
                    }
                    if (record.length() > 0) rows.add(parseRecord(record.toString()));
                    record.setLength(0);
                } else record.append(current);
            }
            if (record.length() > 0) rows.add(parseRecord(record.toString()));
        }
        if (!rows.isEmpty() && !rows.get(0).isEmpty()) rows.get(0).set(0, rows.get(0).get(0).replace("\uFEFF", ""));
        return rows;
    }

    private static List<String> parseRecord(String record) {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < record.length(); i++) {
            char current = record.charAt(i);
            if (current == '"') {
                if (quoted && i + 1 < record.length() && record.charAt(i + 1) == '"') { value.append('"'); i++; }
                else quoted = !quoted;
            } else if (current == ';' && !quoted) { values.add(value.toString().trim()); value.setLength(0); }
            else value.append(current);
        }
        values.add(value.toString().trim());
        return values;
    }

    private static String value(List<String> row, Map<String, Integer> columns, String name, int line) {
        Integer index = columns.get(name);
        if (index == null || index >= row.size()) throw new IllegalArgumentException("Falta el valor de " + name + " en la línea " + line);
        return row.get(index);
    }

    private static String required(String value, String name, int line) {
        if (value.isBlank()) throw new IllegalArgumentException("El campo " + name + " es obligatorio en la línea " + line);
        return value;
    }

    private static int integer(String value, String name, int line) {
        try { return Integer.parseInt(required(value, name, line)); }
        catch (NumberFormatException exception) { throw new IllegalArgumentException("El campo " + name + " no es numérico en la línea " + line); }
    }

    private static int integerOrZero(String value, String name, int line) { return value.isBlank() ? 0 : integer(value, name, line); }

    private static int numberOrZero(String value) {
        if (value.isBlank()) return 0;
        try { return Integer.parseInt(value); }
        catch (NumberFormatException exception) { return 0; }
    }

    private static String normalize(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("\\p{M}", "").trim().toLowerCase();
    }
}