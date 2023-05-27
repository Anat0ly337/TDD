package org.example;

public class TemplateGenerator {
    public String generate(String template, String... values) {
        validateTemplate(template);
        validateValues(values);

        for (int i = 0; i < values.length; i += 2) {
            String variable = values[i];
            String value = values[i + 1];
            template = replaceVariable(template, variable, value);
        }

        return template;
    }

    private void validateTemplate(String template) {
        if (template == null || template.isEmpty()) {
            throw new IllegalArgumentException("Template cannot be null or empty");
        }
    }

    private void validateValues(String[] values) {

        for (int i = 0; i < values.length; i++) {
            String variable = values[i];
            if (variable.isBlank()) {
                throw new MissingValueException("Variable not found in template: " + variable);
            }
        }
    }

    private String replaceVariable(String template, String variable, String value) {
        return template.replace("#{" + variable + "}", value);
    }
}
