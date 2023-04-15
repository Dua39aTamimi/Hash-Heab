
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.DirectoryChooser;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {

	Hash hash;
	int max = 0;
	int total = 0;
static int size;
	int w = 0;
	GridPane gp = new GridPane();

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Label firstLabel = new Label("Please Enter the Size of Data");
			TextField firstText = new TextField();
			VBox firstVBox = new VBox(20);
			Button nextScene = new Button("Done");
			HBox firstHBox = new HBox(10);
			firstHBox.getChildren().addAll(firstText, nextScene);
			firstVBox.getChildren().addAll(firstLabel, firstHBox);
			firstVBox.setPadding(new Insets(50));
			Scene firstScene = new Scene(firstVBox, 310, 200);

			Label title = new Label("Welcome");
			title.setStyle("-fx-font-size:30px;-fx-text-fill:#bb8588");
			title.setPadding(new Insets(10, 0, 0, 190));
			VBox left = new VBox(20);
			left.setPadding(new Insets(45, 0, 0, 30));
			Button load = new Button("Load Files");
			load.setMaxWidth(Double.MAX_VALUE);
			Button search = new Button("Search");
			// search.setDisable(true);
			search.setMaxWidth(Double.MAX_VALUE);
			Button maxFreq = new Button("Max Frequancy");
			// maxFreq.setDisable(true);
			maxFreq.setMaxWidth(Double.MAX_VALUE);
			Button totalBabies = new Button("Add Baby");
			// totalBabies.setDisable(true);
			totalBabies.setMaxWidth(Double.MAX_VALUE);

			left.getChildren().addAll(load, search, maxFreq, totalBabies);

			root.setTop(title);
			root.setLeft(left);
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setInitialDirectory(new File("c:"));
			Label dir = new Label();
			VBox v = new VBox();
			ScrollPane sp = new ScrollPane();
			GridPane gp1 = new GridPane();
			gp1.add(new Label("File Name"), 0, 0);
			gp1.add(new Label("File Size"), 1, 0);
			gp1.setVgap(20);
			gp1.setHgap(10);
			gp1.setPadding(new Insets(10, 10, 10, 10));
			sp.setContent(gp1);
			sp.setMaxWidth(200);
			sp.setMaxHeight(300);
			v.getChildren().addAll(dir, sp);
			v.setPadding(new Insets(45, 0, 0, 30));
			load.setOnAction(e -> {
				File selectedDirectory = directoryChooser.showDialog(primaryStage);
				File files = new File(selectedDirectory.getAbsolutePath());
				File filesList[] = files.listFiles();
				dir.setText(selectedDirectory.getAbsolutePath());
				Scanner sc = null;
				int i = 0;
				for (File file : filesList) {

					try {
						sc = new Scanner(file);
					} catch (FileNotFoundException e1) {

						e1.printStackTrace();
					}
					gp1.add(new Label(file.getName()), 0, i + 1);
					gp1.add(new Label(file.length() + ""), 1, i + 1);
					while (sc.hasNextLine()) {
						String s = sc.nextLine();
						String[] line = s.split(",");

						char[] m = file.getName().toCharArray();
						String g = "" + m[m.length - 8] + m[m.length - 7] + m[m.length - 6] + m[m.length - 5];

						int year = Integer.parseInt(g);
						
						try {
							hash.insert(line[0] + "," + line[1], new Name(line[0], line[1].charAt(0)),
									new Frequency(year, Integer.parseInt(line[2])));
						} catch (ArrayIndexOutOfBoundsException exep) {
							Alert a = new Alert(AlertType.ERROR);
							a.setContentText("OOPS " + size + " is small /nPlease Try a bigger number");
							a.show();
							hash.empty();
							
							primaryStage.setScene(firstScene);
						}

					}
					i++;
				}
				root.setCenter(v);

			}

			);

			search.setOnAction(e -> {
				TextField nameField = new TextField();
				nameField.setPromptText("Enter the Baby Name.");
				ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList("Female", "Male"));
				Button got = new Button("get");
				HBox hbox = new HBox(10);
				hbox.getChildren().addAll(nameField, cb, got);
				Label nameLabel = new Label("Baby Name: ");
				Label genderLabel = new Label("Baby Gender: ");
				HBox h2 = new HBox(15);
				HBox h4 = new HBox(15);
				Label ye = new Label("Year");
				Label fr = new Label("Frequency");
				h4.getChildren().addAll(ye, fr);

				h2.getChildren().addAll(nameLabel, genderLabel);

				gp.setHgap(10);
				gp.setVgap(10);
				gp.setPadding(new Insets(10, 10, 10, 10));

				ScrollPane scrollPane = new ScrollPane();
				scrollPane.setMaxWidth(200);
				scrollPane.setMaxHeight(200);
				HBox line = new HBox(10);
				Button update = new Button("Update");
				Button delete = new Button("Delete");
				Button addFre = new Button("Add year & frequency");
				line.getChildren().addAll(update, delete, addFre);

				VBox vb = new VBox(10);
				vb.getChildren().addAll(hbox, h2, h4, scrollPane, line);
				vb.setPadding(new Insets(45, 0, 0, 30));
				root.setCenter(vb);

				got.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {

						String name;
						char g;
						if (cb.getValue().compareTo("Female") == 0)
							g = 'F';
						else
							g = 'M';
						name = nameField.getText() + "," + g;
						int r = hash.search(name);
						Node n;
						if (r != -1)
							n = hash.table[r];
						else
							n = null;
						if (n != null) {
							nameLabel.setText("Baby Name: " + n.name.name);
							if (n.name.gender == 'F')
								genderLabel.setText("Baby Gender: Female");
							else
								genderLabel.setText("Baby Gender: Male");
							int j = 0;
							Label[][] ls = new Label[2][n.heab.Heap.length];

							if (gp.getChildren().isEmpty()) {
								for (int i = 1; i < n.heab.Heap.length; i++) {

									if (n.heab.Heap[i] != null) {

										ls[0][j] = new Label("");
										ls[0][j].setText(n.heab.Heap[i].year + "");
										ls[1][j] = new Label("");
										ls[1][j].setText(n.heab.Heap[i].fre + "");

										gp.add(ls[0][j], 0, j + 1);
										gp.add(ls[1][j], 1, j + 1);
										j++;
									}

								}
							} else {
								j = 0;
								gp.getChildren().clear();
								for (int i = 1; i < n.heab.Heap.length; i++) {

									if (n.heab.Heap[i] != null) {
								
										ls[0][j] = new Label("");
										ls[0][j].setText(n.heab.Heap[i].year + "");
										ls[1][j] = new Label("");
										ls[1][j].setText(n.heab.Heap[i].fre + "");
										gp.add(ls[0][j], 0, j + 1);
										gp.add(ls[1][j], 1, j + 1);
										j++;
									}
								}

							}
							
							scrollPane.setContent(gp);

							delete.setOnAction(m -> {

								hash.delete(name);
								genderLabel.setText("Baby Gender:");
								nameLabel.setText("Baby Name: ");
								nameField.setText("");
								cb.setValue("");
								gp.getChildren().clear();

								Alert alertType2 = new Alert(AlertType.INFORMATION);
								alertType2.setContentText(name + " Deleted Successfully");
								alertType2.show();

							});
							addFre.setOnAction(m -> {
								Label addyearLabel = new Label("Enter the Birth year! ");
								TextField addyearTextField = new TextField();
								Label addfreLabel = new Label("Enter the Frequency of the name! ");
								TextField addfreTextField = new TextField();
								Button done2 = new Button("Add");
								GridPane addFGrid = new GridPane();
								addFGrid.add(addyearLabel, 0, 0);
								addFGrid.add(addyearTextField, 1, 0);
								addFGrid.add(addfreLabel, 0, 1);
								addFGrid.add(addfreTextField, 1, 1);
								addFGrid.add(done2, 1, 2);
								addFGrid.setHgap(20);
								addFGrid.setVgap(10);
								addFGrid.setPadding(new Insets(50));
								Scene k = new Scene(addFGrid, 500, 200);
								Stage newStage = new Stage();
								newStage.setScene(k);
								newStage.show();
								done2.setOnAction(u -> {
									hash.insert(n.name.name + "," + n.name.gender, n.name,
											new Frequency(Integer.parseInt(addyearTextField.getText()),
													Integer.parseInt(addfreTextField.getText())));

									newStage.close();
									Alert alertType = new Alert(AlertType.INFORMATION);
									alertType.setContentText(addyearTextField.getText() + " & "
											+ addfreTextField.getText() + " inserted to " + n.name.name + " Records");
									alertType.show();
									ls[0][ls[0].length + 1] = new Label(addyearTextField.getText());
									ls[1][ls[0].length + 1] = new Label(addfreTextField.getText());

									gp.add(ls[0][ls[0].length + 1], 0, ls[0].length + 1 + 1);
									gp.add(ls[1][ls[0].length + 1], 1, ls[0].length + 1 + 1);

								});
							});
							update.setOnAction(m -> {

								VBox v = new VBox(10);
								HBox hbo = new HBox(10);
								TextField unameTextField = new TextField();
								unameTextField.setText(n.name.name);
								ChoiceBox<String> cb3 = new ChoiceBox<String>(
										FXCollections.observableArrayList("Female", "Male"));
								if (n.name.gender == 'F')
									cb3.setValue("Female");
								else
									cb3.setValue("Male");
								hbo.getChildren().addAll(unameTextField, cb3);

								ScrollPane sp = new ScrollPane();
								GridPane gp1 = new GridPane();
								TextField[][] tf = new TextField[2][n.heab.Heap.length];
								for (int i = 1; i < n.heab.Heap.length; i++) {
									// d.add(temp.data);
									if (n.heab.Heap[i] != null) {
										tf[0][w] = new TextField(n.heab.Heap[i].year + "");
										tf[1][w] = new TextField(n.heab.Heap[i].fre + "");

										gp1.add(tf[0][w], 0, w + 1);
										gp1.add(tf[1][w], 1, w + 1);

										w++;
									}
								}
								gp1.setHgap(20);
								gp1.setVgap(10);
								sp.setContent(gp1);

								Button done2 = new Button("done");

								v.getChildren().addAll(hbo, sp, done2);
								v.setPadding(new Insets(20));
								Scene sce = new Scene(v, 500, 500);
								Stage ustage = new Stage();
								ustage.setScene(sce);
								ustage.show();
								done2.setOnAction(f -> {
									String na = unameTextField.getText();
									char gen;
									if (cb3.getValue().compareTo("Female") == 0)
										gen = 'F';
									else
										gen = 'M';
									hash.table[r].name.name = na;
									hash.table[r].name.gender = gen;
									hash.table[r].key = na + "," + gen;
									nameLabel.setText("Baby Name: " + n.name.name);
									if (n.name.gender == 'F') {
										genderLabel.setText("Baby Gender: Female");
										cb.setValue("Female");
									} else {
										genderLabel.setText("Baby Gender: Male");
										cb.setValue("Male");
									}
									nameField.setText(n.name.name);

									int y = 0;
									for (int i = 1; i < n.heab.Heap.length; i++) {
										if (n.heab.Heap[i] != null) {
											n.heab.Heap[i].year = Integer.parseInt(tf[0][i - 1].getText());
											n.heab.Heap[i].fre = Integer.parseInt(tf[1][i - 1].getText());

											ls[0][y].setText(n.heab.Heap[i].year + "");

											ls[1][y].setText(n.heab.Heap[i].fre + "");

											y++;
										}
									}
									ustage.close();
								});
							});

						} else {
							Alert alertType = new Alert(AlertType.ERROR);
							alertType.setContentText(name + " Not Found!!");
							alertType.show();
						}

					}
				});
			});

			Label maxNameFre = new Label("The Popular Name all over the years is:");
			HBox hb = new HBox(maxNameFre);

			hb.setPadding(new Insets(45, 0, 0, 30));
			maxFreq.setOnAction(e -> {
				root.setCenter(hb);

				int max = 0;

				Node maxNode = null;

				for (int i = 0; i < hash.table.length; i++) {

					if (hash.table[i] != null) {
						if (hash.table[i].heab.Heap[1] != null)
							if (hash.table[i].heab.Heap[1].fre > max) {
								max = hash.table[i].heab.Heap[1].fre;
								maxNode = hash.table[i];

							}
					}
				}
				maxNameFre.setText(maxNode.name.name + " Has a maximum of " + max + " in " + maxNode.heab.Heap[1].year);

			});

			Label addNameLabel = new Label("Enter the Baby Name! ");
			TextField addNameTextField = new TextField();
			Label addGenderLabel = new Label("Enter the Baby Gender! ");
			ChoiceBox<String> gb = new ChoiceBox<String>(FXCollections.observableArrayList("Female", "Male"));
			Button done = new Button("Done");
			GridPane addGrid = new GridPane();
			addGrid.add(addNameLabel, 0, 0);
			addGrid.add(addNameTextField, 1, 0);
			addGrid.add(addGenderLabel, 0, 1);
			addGrid.add(gb, 1, 1);
			addGrid.add(done, 1, 2);
			addGrid.setHgap(20);
			addGrid.setVgap(10);
			VBox ba = new VBox(10);
			ba.getChildren().addAll(addGrid);
			ba.setPadding(new Insets(45, 0, 0, 30));
			totalBabies.setOnAction(e -> {
				root.setCenter(ba);
			});

			done.setOnAction(e -> {
				String y = addNameTextField.getText();
				char g;
				if (gb.getValue().compareTo("Female") == 0)
					g = 'F';
				else
					g = 'M';
				Label addyearLabel = new Label("Enter the Birth year! ");
				TextField addyearTextField = new TextField();
				Label addfreLabel = new Label("Enter the Frequency of the name! ");
				TextField addfreTextField = new TextField();
				Button done2 = new Button("Add");
				GridPane addFGrid = new GridPane();
				addFGrid.add(addyearLabel, 0, 0);
				addFGrid.add(addyearTextField, 1, 0);
				addFGrid.add(addfreLabel, 0, 1);
				addFGrid.add(addfreTextField, 1, 1);
				addFGrid.add(done2, 1, 2);
				addFGrid.setHgap(20);
				addFGrid.setVgap(10);
				addFGrid.setPadding(new Insets(50));
				Scene m = new Scene(addFGrid, 500, 200);
				Stage newStage = new Stage();
				newStage.setScene(m);
				newStage.show();

				done2.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						hash.insert(y + "," + g, new Name(y, g),
								new Frequency(Integer.parseInt(addyearTextField.getText()),
										Integer.parseInt(addfreTextField.getText())));

						newStage.close();
						Alert alertType = new Alert(AlertType.INFORMATION);
						alertType.setContentText(y + " was successfully inserted into Records");
						alertType.show();
					}
				});

			});

			Scene scene = new Scene(root, 500, 470);

			nextScene.setOnAction(e -> {
				if (firstText.getText() != null) {
					 size = Integer.parseInt(firstText.getText());
					if (size > 0) {

						hash = new Hash(size);
						primaryStage.setScene(scene);

					}
				}

			});
			primaryStage.setScene(firstScene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
