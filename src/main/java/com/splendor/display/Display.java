package com.splendor.display;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
import java.lang.Math;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NavigationFilter;

import com.splendor.constants.Messages;


/**
 * The Display class represents a graphical user interface for displaying 
 * a game board and console output. It provides methods to interact with 
 * the user through input and output streams. The display consists of a 
 * game board, a console for textual output, and a text field for user input.
 *
 * The game board is represented by a JTextArea, and the console output is 
 * displayed in another JTextArea with scroll capabilities. User input is 
 * accepted through a JTextField.
 *
 * The class also provides methods for manipulating and formatting strings 
 * for display purposes.
 */
public class Display {

    private final static Integer UNICODE_ONE = 9312;
    private final static Integer UNICODE_TWENTY = 9331;
    private final static Integer UNICODE_THREE = 9450;
    private final static Color BACKGROUND = new Color(240, 240, 240);

    private final Font font;
    private final JFrame frame;
    private final JTextArea boardTextArea;
    private final JTextArea consoleTextArea;
    private final JTextField textField;

    public final Readable in;
    public final PrintStream out;
    public final PrintStream outBoard;

    private final int rowsBoard;
    private final int rowsConsole;
    private final int columns;

    /**
     * Constructs a new Display with the specified dimensions for the 
     * game board, console, and text field.
     *
     * @param rowsBoard The number of rows for the game board.
     * @param rowsConsole The number of rows for the console.
     * @param columns The number of columns for both the game board 
     *        and console.
     */
    public Display(int rowsBoard, int rowsConsole, int columns) {
        this.rowsBoard = rowsBoard;
        this.rowsConsole = rowsConsole;
        this.columns = columns;
        this.font = FontLoader.load();

        this.boardTextArea = this.board();
        this.consoleTextArea = this.console();
        this.textField = this.field();
        this.frame = this.frame();

        this.outBoard = new PrintStream(this.boardTextArea);
        this.out = new PrintStream(this.consoleTextArea);
        this.in = (Readable) this.textField;
    }

    /**
     * Creates and configures a JTextArea for displaying the game board.
     *
     * @return The configured JTextArea for the game board.
     */
    private JTextArea board() {
        final JTextArea board = new JTextArea(this.rowsBoard, this.columns);
        board.setEditable(false);
        board.setLineWrap(true);
        board.setFont(this.font);
        return board;
    }

    /**
     * Creates and configures a JTextArea for displaying console output.
     *
     * @return The configured JTextArea for console output.
     */
    private JTextArea console() {
        final JTextArea console = new JTextArea(rowsConsole, columns);
        console.setBackground(Display.BACKGROUND);
        console.setEditable(false);
        console.setLineWrap(true);
        console.setFont(this.font);
        final Border border = BorderFactory.createLineBorder(Color.BLACK);
        console.setBorder(border);
        final Caret caret = console.getCaret();
        final DefaultCaret consoleCaret = (DefaultCaret) caret; 
        consoleCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        return console;
    }

    /**
     * Creates and configures a ReadableTextField for user input.
     *
     * @return The configured ReadableTextField for user input.
     */
    private ReadableTextField field() {
        final ReadableTextField field = new ReadableTextField(columns);
        field.setBackground(Display.BACKGROUND);
        field.setFont(this.font);
        final NavigationFilter positionFilter = new CursorPositionFilter();
        field.setNavigationFilter(positionFilter);
        field.requestFocusInWindow();
        field.setCaretPosition(2);
        final DocumentFilter actionFilter = new CursorActionFilter();
        final Document document = field.getDocument();
        ((AbstractDocument) document).setDocumentFilter(actionFilter);
        return field;
    }

    /**
     * Creates and configures the main JFrame for the application window.
     *
     * @return The configured JFrame for the application window.
     */
    private JFrame frame() {
        final JFrame frame = new JFrame(Messages.TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Container container = frame.getContentPane();
        container.add(this.boardTextArea, BorderLayout.NORTH);
        final JScrollPane scrollPane = new JScrollPane(this.consoleTextArea);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(this.textField, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setMinimumSize(frame.getSize());
        return frame;
    }

    /**
     * Calculates the displayed length of a string by considering 
     * the presence of specific Unicode characters. Digits and certain 
     * Unicode characters are counted as two characters due to their 
     * extended width in display.
     *
     * @param string The input string to calculate the displayed 
     *        length for.
     * @return The displayed length of the string, considering 
     *         extended-width characters.
     */
    private static int displayedLength(String string) {
        int length = 0;
        for (int index = 0; index < string.length(); index++) {
            final int codePoint = string.codePointAt(index);
            final boolean isDigit =
                codePoint == Display.UNICODE_THREE ||
                (codePoint >= Display.UNICODE_ONE
                && codePoint <= Display.UNICODE_TWENTY);
            length += isDigit ? 2 : 1;
        }
        return length;
    }

    /**
     * Calculates the maximum displayed width among all strings 
     * in the given array.
     *
     * @param array The array of strings to calculate the maximum 
     *        displayed width for.
     * @return The maximum displayed width among all strings in the array.
     */
    private static int width(String[] array) {
        if (array == null || array.length == 0) return 0;
        return Arrays.stream(array)
            .mapToInt(line -> Display.displayedLength(line))
            .max().orElse(0);
    }

    /**
     * Returns the number of strings (height) in the given array.
     *
     * @param array The array of strings to determine the height of.
     * @return The number of strings (height) in the array.
     */
    private static int height(String[] array) {
        return array.length;
    }
    
    /**
     * Gets the number of columns in the game board.
     *
     * @return The number of columns in the game board.
     */
    public int getBoardColumns() {
        return this.boardTextArea.getColumns();
    }

    /**
     * Gets the number of rows in the game board.
     *
     * @return The number of rows in the game board.
     */
    public int getBoardRows() {
        return this.boardTextArea.getRows();
    }

    /**
     * Pads each string in the given array with a specified character to 
     * achieve the desired height and width.
     *
     * @param array The string array to pad.
     * @param char_ The character used for padding.
     * @param height The desired height of the padded array.
     * @param width The desired width of each string in the padded array.
     * @return A new string array with each string padded to the specified 
     *         height and width.
     */
    private static String[] pad(
            String[] array, char char_, int height, int width) {
        final String[] padded = new String[height];
        for (int index = 0; index < height; index++)
            if (index < array.length) {
                final int length = Display.displayedLength(array[index]);
                final int currentWidth = width - length;
                final String repeatedChar = (char_ + "").repeat(currentWidth);
                padded[index] = array[index] + repeatedChar;
            } else padded[index] = (char_ + "").repeat(width);
        return padded;
    }

    /**
     * Concatenates two string arrays vertically.
     *
     * @param firstArray The first string array to concatenate.
     * @param secondArray The second string array to concatenate.
     * @return The concatenated string array.
     */
    public static String[] concatStringArray(
            String[] firstArray, String[] secondArray) {
        return Display.concatStringArray(firstArray, secondArray, false);
    }

    /**
     * Concatenates two string arrays either horizontally or vertically.
     *
     * @param firstArray The first string array to concatenate.
     * @param secondArray The second string array to concatenate.
     * @param stacked If {@code true}, concatenates the arrays 
     *        vertically; otherwise, concatenates them horizontally.
     * @return The concatenated string array.
     */
    public static String[] concatStringArray(
            String[] firstArray, String[] secondArray, boolean stacked) {
        final int firstHeight = Display.height(firstArray);
        final int firstWidth = Display.width(firstArray);
        final int secondHeight = Display.height(secondArray);
        final int secondWidth = Display.width(secondArray);
        if (!stacked) {
            final int height = Math.max(firstHeight, secondHeight);
            firstArray = Display.pad(firstArray, ' ', height, firstWidth);
            secondArray = Display.pad(secondArray, ' ', height, secondWidth);
            final String[] array = new String[height];
            for (int index = 0; index < height; index++)
                array[index] = firstArray[index] + secondArray[index];
            return array;
        }
        final int width = Math.max(firstWidth, secondWidth);
        firstArray = Display.pad(firstArray, ' ', firstHeight, width);
        secondArray = Display.pad(secondArray, ' ', secondHeight, width);
        final String[] array = new String[firstHeight + secondHeight];
        System.arraycopy(firstArray, 0, array, 0, firstHeight);
        System.arraycopy(secondArray, 0, array, firstHeight, secondHeight);
        return array;
    }
    

    /**
     * Creates an empty string array with the specified number of rows and 
     * columns, filled with an empty space.
     *
     * @param rows The number of rows in the string array.
     * @param columns The number of columns in the string array.
     * @return An empty string array with the specified dimensions.
     */
    public static String[] emptyStringArray(int rows, int columns) {
        return Display.emptyStringArray(rows, columns, " ");
    }

    /**
     * Creates an empty string array with the specified number of rows and 
     * columns, filled with a specified string.
     *
     * @param rows The number of rows in the string array.
     * @param columns The number of columns in the string array.
     * @param string The string to fill the array with.
     * @return An empty string array with the specified dimensions.
     */
    public static String[] emptyStringArray(
            int rows, int columns, String string) {
        final String[] array = new String[rows];
        Arrays.fill(array, string.repeat(columns));
        return array;
    }

    /**
     * Closes the display by disposing of the JFrame.
     */
    public void close() {
        this.frame.dispose();
    }
}
