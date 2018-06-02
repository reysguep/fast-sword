package libgdxUtils.exceptions;

/**
 *
 * @author reysguep
 */
public class CommandException extends Exception {

    /**
     * Creates a new instance of <code>CommandException</code> without detail
     * message.
     */
    public CommandException() {
        super("Failed to call command.");
    }

    /**
     * Constructs an instance of <code>CommandException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CommandException(String msg, String chrName) {
        super("Failed to call command on *" + msg + "* section. (" + chrName + ")");
    }
}
