package ai;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ControllerClass {
	
	@FXML
	private Rectangle c0_Rectangle;
	@FXML
	private Rectangle c1_Rectangle;
	@FXML
	private Rectangle c2_Rectangle;
	@FXML
	private Rectangle c3_Rectangle;
	@FXML
	private Rectangle c4_Rectangle;
	@FXML
	private Rectangle c5_Rectangle;
	@FXML
	private Rectangle c6_Rectangle;
	@FXML
	private Rectangle c7_Rectangle;
	@FXML
	private Rectangle c8_Rectangle;
	@FXML
	private Rectangle c9_Rectangle;
	@FXML
	private Rectangle c10_Rectangle;
	@FXML
	private Rectangle c11_Rectangle;
	@FXML
	private Rectangle c12_Rectangle;
	@FXML
	private Rectangle c13_Rectangle;
	@FXML
	private Rectangle c14_Rectangle;
	@FXML
	private Rectangle c15_Rectangle;
	@FXML
	private Rectangle c16_Rectangle;
	@FXML
	private Rectangle c17_Rectangle;
	@FXML
	private Rectangle c18_Rectangle;
	@FXML
	private Rectangle c19_Rectangle;
	@FXML
	private ComboBox from_ComboBox;
	@FXML
	private ComboBox to_ComboBox;
	@FXML
	private RadioButton bfs_RadioButton;
	@FXML
	private RadioButton dfs_RadioButton;
	@FXML
	private RadioButton ast_RadioButton;
	@FXML
	private Button search_Button;
	@FXML
	private TextArea path_TextArea;
	@FXML
	private Label totalCost_Lable;
	@FXML
	private TextArea triverse_TextArea;
	@FXML
	private ToggleGroup radio_ToggleGroup;
	@FXML
	private Label exectionTime_Label;
	
	private long exectionTime;
	
	private Graph RomaniaMap=new Graph();
	private ArrayList<String> cities_list=new ArrayList<>
	(Arrays.asList(RomaniaMap.getCities()));
	
	public ControllerClass() 
	{

	}
	
	public void initialize() 
	{
		from_ComboBox.setItems(FXCollections.observableArrayList(RomaniaMap.getCities()));
		to_ComboBox.setItems(FXCollections.observableArrayList(RomaniaMap.getCities()));
		bfs_RadioButton.setSelected(true);
		search_Button.setOnAction(new searchHandler());
	}
	public void highlightPath() 
	{
		for (int v : RomaniaMap.getPathVertices()) {
			if(v==0)c0_Rectangle.setFill(Color.GREEN);
			if(v==1)c1_Rectangle.setFill(Color.GREEN);
			if(v==2)c2_Rectangle.setFill(Color.GREEN);
			if(v==3)c3_Rectangle.setFill(Color.GREEN);
			if(v==4)c4_Rectangle.setFill(Color.GREEN);
			if(v==5)c5_Rectangle.setFill(Color.GREEN);
			if(v==6)c6_Rectangle.setFill(Color.GREEN);
			if(v==7)c7_Rectangle.setFill(Color.GREEN);
			if(v==8)c8_Rectangle.setFill(Color.GREEN);
			if(v==9)c9_Rectangle.setFill(Color.GREEN);
			if(v==10)c10_Rectangle.setFill(Color.GREEN);
			if(v==11)c11_Rectangle.setFill(Color.GREEN);
			if(v==12)c12_Rectangle.setFill(Color.GREEN);
			if(v==13)c13_Rectangle.setFill(Color.GREEN);
			if(v==14)c14_Rectangle.setFill(Color.GREEN);
			if(v==15)c15_Rectangle.setFill(Color.GREEN);
			if(v==16)c16_Rectangle.setFill(Color.GREEN);
			if(v==17)c17_Rectangle.setFill(Color.GREEN);
			if(v==18)c18_Rectangle.setFill(Color.GREEN);
			if(v==19)c19_Rectangle.setFill(Color.GREEN);
			
		}
	}
	
	public void highlightDefault() 
	{
		c0_Rectangle.setFill(Color.WHITE);
		c1_Rectangle.setFill(Color.WHITE);
		c2_Rectangle.setFill(Color.WHITE);
		c3_Rectangle.setFill(Color.WHITE);
		c4_Rectangle.setFill(Color.WHITE);
		c5_Rectangle.setFill(Color.WHITE);
		c6_Rectangle.setFill(Color.WHITE);
		c7_Rectangle.setFill(Color.WHITE);
		c8_Rectangle.setFill(Color.WHITE);
		c9_Rectangle.setFill(Color.WHITE);
		c10_Rectangle.setFill(Color.WHITE);
		c11_Rectangle.setFill(Color.WHITE);
		c12_Rectangle.setFill(Color.WHITE);
		c13_Rectangle.setFill(Color.WHITE);
		c14_Rectangle.setFill(Color.WHITE);
		c15_Rectangle.setFill(Color.WHITE);
		c16_Rectangle.setFill(Color.WHITE);
		c17_Rectangle.setFill(Color.WHITE);
		c18_Rectangle.setFill(Color.WHITE);
		c19_Rectangle.setFill(Color.WHITE);
		
	}
	
	class searchHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent e) {
			
			try {
				if (!(from_ComboBox.getSelectionModel().isEmpty()||
						to_ComboBox.getSelectionModel().isEmpty())) {
					
					String from = (String)from_ComboBox.getValue();
					String to = (String)to_ComboBox.getValue();
					
					long startTime=System.nanoTime();
					if (bfs_RadioButton.isSelected()) {
						RomaniaMap.BFS(cities_list.indexOf(from),cities_list.indexOf(to));
					}else if (dfs_RadioButton.isSelected()) {
						RomaniaMap.DFS(cities_list.indexOf(from),cities_list.indexOf(to));
					}else {
						RomaniaMap.AST(cities_list.indexOf(from),cities_list.indexOf(to));
					}//inner-if
					
					exectionTime=0; //for reset
					long endTime= System.nanoTime();
					exectionTime=endTime-startTime;
					
					//for reset TextArea of path and triverse
					triverse_TextArea.setText("");
					highlightDefault();
					
					exectionTime_Label.setText("");//for reset
					exectionTime_Label.setText("Execution Time in nano is: "+exectionTime);
					
					totalCost_Lable.setText(""+RomaniaMap.getCost());
					path_TextArea.setText(RomaniaMap.getPath());
					triverse_TextArea.setText(RomaniaMap.getPathTraverse());
					highlightPath();
					
				}else{
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Field Selection Error");
					alert.setContentText("Please select all fields");
					alert.show();
				}//outer-if	
			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText(e2.getMessage());
				alert.show();
			}//try
			
		}//handle
		
	}//inner-class-handler
	
}//class
