package application;
	
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.text.Position;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField; 
import javafx.scene.control.Button; 



public class Main extends Application {
	int index =0;
	Person[] abArray;
	//Sizes
	final static int idSize = 4;
	final static int searchSize = 4;
	final static int nameSize = 32;
	final static int streetSize = 32;
	final static int citySize = 32;
	final static int genderSize=1;
	final static int zipSize =5;
	final static int recordSize =(idSize + searchSize + nameSize + streetSize + citySize + genderSize + zipSize);
	public RandomAccessFile raf;
	
	public int personBytes = (recordSize-searchSize)*2;
	public int idPosition=0;
	public int informationAreaCount = 7;
	
	//Text Fields

	TextField tfs[] = new TextField[informationAreaCount];
	String stringText = "";


	//Buttons
	Button buttons[] = new Button[8];
	String stringsButtons[] = new String[] {
			"Add",
			"First",
			"Next",
			"Previous",
			"Last",
			"UpdateByID",
			"SearchByID",
			"CleanTextFields",
			
	};

	//Labels
	Label lbs[] = new Label[informationAreaCount];
	
	String stringsLabel[] = new String[] {
			"ID",
			"Search/Update ID",
			"Name",
			"Street",
			"City",
			"Gender",
			"Zip"
			
	};
	
	
	
	
	public Main() {
		for (int i = 0; i < lbs.length; i++) {
			lbs[i] = new Label(stringsLabel[i]);
			tfs[i] = new TextField();
		}
		for (int i = 0; i < buttons.length; i++) {
		buttons[i] = new Button(stringsButtons[i]);
		}
		try {
			
			raf = new RandomAccessFile("address.dat", "rw");
			abArray = new Person[100];
		}
		catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			tfs[0].setPrefColumnCount(idSize);
			tfs[0].setEditable(false);
			tfs[1].setPrefColumnCount(searchSize);
			tfs[2].setPrefColumnCount(nameSize);
			tfs[3].setPrefColumnCount(streetSize);
			tfs[4].setPrefColumnCount(citySize);
			tfs[5].setPrefColumnCount(genderSize);
			tfs[6].setPrefColumnCount(zipSize);
			
			Alert alert = new Alert (AlertType.INFORMATION);
			alert.setTitle("Ýnformation Dialog");
			alert.setHeaderText("Look, an Information Dialog");
			
			GridPane p1 = new GridPane();
			p1.setAlignment(Pos.CENTER);
			p1.setMinHeight(150);
			p1.setMinWidth(650);
			p1.setHgap(5);
			p1.setVgap(5);
			
			p1.addRow(0, lbs[0]);
			
			HBox p2 = new HBox();
			p2.setSpacing(5);
			
			p2.getChildren().addAll(tfs[0],lbs[1],tfs[1]);
			p1.add(p2,1,0);

			for(int i =1; i < 4; i++) {
				
				p1.addRow(i,lbs[i+1]);
				p1.add(tfs[i+1],1,i);
			}
			
			
			HBox p3 = new HBox(8);
			p3.getChildren().addAll(tfs[4],lbs[5],tfs[5],lbs[6],tfs[6]);
			p1.add(p3, 1, 3);
			
			HBox p4 = new HBox(8);
			for (Button button : buttons) {
				p4.getChildren().add(button);
			}
			p4.setMinHeight(50);
			p1.add(p4, 1, 4);
		
			
			BorderPane borderPane = new BorderPane();
			borderPane.setCenter(p1);
			borderPane.setBottom(p4);
			p4.setAlignment(Pos.BASELINE_CENTER);
			Scene scene = new Scene(borderPane);
			primaryStage.setTitle("Address Book Project");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			try {
				if(raf.length()>0) {// file is not empty
					long currentPos = raf.getFilePointer();
					while (currentPos < raf.length()) {
					
						readFileFillArray(abArray,currentPos);
						currentPos=raf.getFilePointer();
				
					}
					ReadFileByPos(0);
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			buttons[0].setOnAction(e->{
				try {
					alert.setContentText("Record is added succesfully");
					alert.showAndWait();
					writeAddressToFile(raf.length());
					readFileFillArray(abArray, recordSize*2*(index));
					cleanTextFields();
					
				} catch (Exception ex) {
					
				}
				cleanTextFields();
			});
			buttons[1].setOnAction(e->{
				try {
					idPosition =0;
					ReadFileByPos(0);
				} catch (Exception ex) {
					
				}
			});
			buttons[2].setOnAction(e->{
				try {
					if (idPosition <= (raf.length()/personBytes)) {
						idPosition++;
					}System.out.println(raf.length());
					ReadFileByPos(idPosition*personBytes);
					
				} catch (Exception ex) {
					
				}
				
			});
			buttons[3].setOnAction(e->{
				try {
					if (idPosition >= 0) {
						idPosition--;
					}
					ReadFileByPos(idPosition*personBytes);
				} catch (Exception ex) {
					
				}
			});
			buttons[4].setOnAction(e->{
				try {
					idPosition =(int)(raf.length())/personBytes;
					ReadFileByPos(raf.length()-personBytes-2);
				} catch (Exception ex) {
					
				}
			});
			//TODO : Update 
			buttons[5].setOnAction(e->{
				try {
					FileOperations.writeFixedLengthStrings(tfs[1].getText(),searchSize,raf);
					writeAddressToFile((Integer.parseInt(tfs[1].getText())-1)*personBytes);
					readFileFillArray(abArray, recordSize*2*((Integer.parseInt(tfs[1].getText()))));
					alert.setContentText("Record is updated succesfully");
					alert.showAndWait();
					cleanTextFields();
					
				} catch (Exception ex) {
					
				}
				
			});
			buttons[6].setOnAction(e->{
				try {
					
					ReadFileByPos((Integer.parseInt(tfs[1].getText())-1)*personBytes);
				} catch (Exception ex) {
					
				}
			});
			buttons[7].setOnAction(e->{
				try {
					
					cleanTextFields();
				} catch (Exception ex) {
					
				}
			});
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	


	private void writeAddressToFile(long position) {
		try {
			System.out.println(position);
			raf.seek(position);
			raf.writeBytes(System.getProperty("line.separator"));
			FileOperations.writeFixedLengthStrings(Integer.toString((int)(position/personBytes)+1), idSize, raf);
			FileOperations.writeFixedLengthStrings(tfs[2].getText(), nameSize, raf);
			FileOperations.writeFixedLengthStrings(tfs[3].getText(), streetSize, raf);
			FileOperations.writeFixedLengthStrings(tfs[4].getText(), citySize, raf);
			FileOperations.writeFixedLengthStrings(tfs[5].getText(), genderSize, raf);
			FileOperations.writeFixedLengthStrings(tfs[6].getText(), zipSize, raf);
			

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void cleanTextFields() {
		for (int i = 0; i < tfs.length; i++)
			tfs[i].clear();
		
	}

	private void readFileFillArray(Person[] people, long position) throws IOException {
		
		raf.seek(position);
		raf.readLine();
		
		String id = FileOperations.readFixedLengthString(idSize, raf).trim();
		int intID = Integer.parseInt(id.toString());
		String name  =FileOperations.readFixedLengthString(nameSize, raf).trim();
		String street  =FileOperations.readFixedLengthString(streetSize, raf).trim();
		String city  =FileOperations.readFixedLengthString(citySize, raf).trim();
		String gender  =FileOperations.readFixedLengthString(genderSize, raf);
		String zip  =FileOperations.readFixedLengthString(zipSize, raf).trim();
		
		Person p = new Person(intID, name, street, city, zip, gender);
		people[index]=p;
		System.out.println(p);
		index++;
	}
	
	
	private void ReadFileByPos(long position) throws IOException {
		raf.seek(position);
		raf.readLine();
		
		String id  =FileOperations.readFixedLengthString(idSize, raf).trim();
		String name  =FileOperations.readFixedLengthString(nameSize, raf).trim();
		String street  =FileOperations.readFixedLengthString(streetSize, raf).trim();
		String city  =FileOperations.readFixedLengthString(citySize, raf).trim();
		String gender  =FileOperations.readFixedLengthString(genderSize, raf).trim();
		String zip  =FileOperations.readFixedLengthString(zipSize, raf).trim();
		
		tfs[0].setText(id);
		tfs[2].setText(name);
		tfs[3].setText(street);
		tfs[4].setText(city);
		tfs[5].setText(gender);
		tfs[6].setText(zip);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
