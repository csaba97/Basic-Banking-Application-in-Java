package PT2018.demo.DemoProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import presentation.Controller;
import presentation.Model;
import presentation.View;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(view, model);
	}
}
