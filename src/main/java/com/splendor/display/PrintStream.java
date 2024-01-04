package com.splendor.display;

import javax.swing.JTextArea;


/**
 * A simple utility class for printing text to a JTextArea.
 */
public class PrintStream {

    private final JTextArea textArea;

    /**
     * Constructs a PrintStream with the specified JTextArea.
     *
     * @param textArea The JTextArea to which text will be printed.
     */
    public PrintStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Clears the content of the associated JTextArea.
     */
    public void clean() {
        this.textArea.setText("");
    }

    /**
     * Appends a newline character to the associated JTextArea.
     */
    public void newLine() {
        this.print("\n");
    }

    /**
     * Prints the specified objects to the associated JTextArea.
     *
     * @param objects The objects to be printed.
     */
    public void print(Object... objects) {
        for (Object object : objects)
            this.textArea.append(String.valueOf(object));
    }

    /**
     * Prints the specified objects followed by a newline character 
     * to the associated JTextArea.
     *
     * @param objects The objects to be printed.
     */
    public void println(Object... objects) {
        this.print(objects);
        this.newLine();
    }
}
