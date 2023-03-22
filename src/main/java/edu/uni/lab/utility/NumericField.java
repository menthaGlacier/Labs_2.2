package edu.uni.lab.utility;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.text.DecimalFormat;
import java.text.ParseException;

public class NumericField extends TextField {

	public NumericField() {
		setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
			if (change.getControlNewText().isEmpty()) { return change; }
			DecimalFormat format = new DecimalFormat("0");
			try {
				if (!change.getText().isEmpty()) { format.parse(change.getText()); }
				return change;
			}
			catch (ParseException e) {
				return null;
			}
		}));
	}
}