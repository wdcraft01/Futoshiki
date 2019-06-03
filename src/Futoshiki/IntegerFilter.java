package Futoshiki;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * IntegerFilter class provides a reusable filter for constraining
 * text input to integers. Based on code provided and discussed by Uwe
 * (Apr 5 '16 at 20:18) at https://stackoverflow.com/questions/7555564/...
 * what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
 * created: 6/2/19 by wdc
 * last modified: 6/2/19 by wdc - created basic class
 * @author Warren D. Craft (wdc)
 * @author Tyler H. Fenske (thf)
 */
public class IntegerFilter implements UnaryOperator<TextFormatter.Change> {
    private final static Pattern DIGIT_PATTERN = Pattern.compile("^$|^[1-7]${0,1}");

    @Override
    public TextFormatter.Change apply(TextFormatter.Change aT) {
        return DIGIT_PATTERN.matcher(aT.getText()).matches() ? aT : null;
    }
}
