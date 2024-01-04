package com.splendor.display;

import javax.swing.JTextField;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * A JTextField implementation that can be used as a Readable stream.
 */
public class ReadableTextField extends JTextField implements Readable {

    private final BlockingQueue<Integer> queue =
        new LinkedBlockingQueue<Integer>();

    /**
     * Constructs a ReadableTextField with the specified number of columns.
     *
     * @param columns The number of columns in the text field.
     */
    public ReadableTextField(int columns) {
        super("> ", columns);
        super.addActionListener(event -> {
            synchronized (this.queue) {
                String text = super.getText();
                text = text.substring(2) + "\n";
                super.setText("> ");
                text.chars().forEach(this.queue::offer);
                this.queue.offer(-1);
                this.queue.notify();
            }
        });
    }

    /**
     * Reads characters from the internal queue into the provided CharBuffer.
     *
     * @param charBuffer The CharBuffer to read characters into.
     * @return The number of characters read.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public int read(CharBuffer charBuffer) throws IOException {
        try {
            synchronized (this.queue) {
                while (this.queue.isEmpty()) this.queue.wait();
                int number = 0;
                while (!this.queue.isEmpty()) {
                    char char_ = (char) this.queue.poll().intValue();
                    charBuffer.put(char_);
                    number++;
                }
                return number;
            }
        } catch (InterruptedException exception) {
            throw new IOException(exception);
        }
    }
}
